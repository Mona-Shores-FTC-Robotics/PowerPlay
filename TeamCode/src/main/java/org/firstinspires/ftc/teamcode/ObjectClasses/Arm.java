package org.firstinspires.ftc.teamcode.ObjectClasses;

import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Arm {

    public static final double ARM_CENTER_INTAKE = 0.63;
    public static final double ARM_LEFT_OUTTAKE = .95;
    public static final double ARM_RIGHT_OUTTAKE = .33;
    public static final double ARM_FRONT_OUTTAKE = 0;
    public static final double ARM_NEAR_FRONT= .15;

    public static final double HEIGHT_FOR_PREVENTING_ARM_ROTATION = 650;
    public static final double SAFE_HEIGHT_FOR_ALLOWING_ARM_ROTATION = 800;

    //we had this value at 1.2 yesterday, trying .7 to see if still gets arm centered in time
    public static final double SECONDS_TO_CENTER_ARM_BEFORE_LIFT_LOWER = .7;

    public Servo arm;
    public armState currentArmState;
    public enum armState {  ARM_LEFT, ARM_CENTER, ARM_RIGHT, ARM_FRONT,
                            ARM_CENTERED_MOVE_LIFT_TO_INTAKE,
                            OPEN_CLAW_CENTER_ARM_LOWER_LIFT_INTAKE_ON,
                            INTAKE_OFF_CLOSE_CLAW_LIFT_MAX_HEIGHT_ARM_FRONT}
    public Lift lift;
    public Intake intake;
    public Claw claw;
    public ElapsedTime liftTimer = new ElapsedTime();
    public LinearOpMode activeOpMode;


    private double targetLiftPositionAfterArmRotation;

    public Arm (Lift m_Lift, Intake m_ServoIntake, Claw m_claw, LinearOpMode mode)
    {
       lift = m_Lift;
       intake = m_ServoIntake;
       claw = m_claw;
       activeOpMode = mode;
    }

    public void init(HardwareMap ahwMap){

    arm = ahwMap.servo.get("turret_servo");
    //set arm at intake position
    arm.setPosition(ARM_CENTER_INTAKE);
    currentArmState = armState.ARM_CENTER;
    }

    public void init2(HardwareMap ahwMap) {
        arm = ahwMap.servo.get("turret_servo");
        //set arm at intake position
        arm.setPosition(ARM_NEAR_FRONT);
        currentArmState = armState.ARM_FRONT;

    }

    public void CheckArm(Boolean armLeftCurrentButton, Boolean armLeftPreviousButton,
                         Boolean armCenterCurrentButton, Boolean armCenterPreviousButton,
                         Boolean armRightCurrentButton, Boolean armRightPreviousButton,
                         Boolean armFrontCurrentButton, Boolean armFrontPreviousButton){
        if (armLeftCurrentButton && !armLeftPreviousButton) {
            setPosition(ARM_LEFT_OUTTAKE);
        } else if (armCenterCurrentButton && !armCenterPreviousButton) {
            setPosition(ARM_CENTER_INTAKE);
        } else if (armRightCurrentButton && !armRightPreviousButton) {
            setPosition(ARM_RIGHT_OUTTAKE);
        } else if (armFrontCurrentButton && !armFrontPreviousButton) {
            setPosition(ARM_FRONT_OUTTAKE);
        }
    }

    public void AdvancedCheckArm(Boolean armLeftCurrentButton, Boolean armLeftPreviousButton,
                                 Boolean armCenterCurrentButton, Boolean armCenterPreviousButton,
                                 Boolean armRightCurrentButton, Boolean armRightPreviousButton,
                                 Boolean armFrontCurrentButton, Boolean armFrontPreviousButton,
                                 Boolean autoIntakeCurrentButton, Boolean autoIntakePreviousButton,
                                 Boolean autoOuttakeCurrentButton, Boolean autoOuttakePreviousButton){
        if (armLeftCurrentButton && !armLeftPreviousButton) {
            currentArmState = armState.ARM_LEFT;
            setArmState(currentArmState);
        } else if (armCenterCurrentButton && !armCenterPreviousButton) {
            currentArmState = armState.ARM_CENTER;
            setArmState(currentArmState);
        } else if (armRightCurrentButton && !armRightPreviousButton) {
            currentArmState = armState.ARM_RIGHT;
            setArmState(currentArmState);
        } else if (armFrontCurrentButton && !armFrontPreviousButton) {
            currentArmState = armState.ARM_FRONT;
            setArmState(currentArmState);
        } else if (autoIntakeCurrentButton && !autoIntakePreviousButton){
            currentArmState = armState.OPEN_CLAW_CENTER_ARM_LOWER_LIFT_INTAKE_ON;
            setArmState(currentArmState);
        } else if (autoOuttakeCurrentButton && !autoOuttakePreviousButton) {
            currentArmState = armState.INTAKE_OFF_CLOSE_CLAW_LIFT_MAX_HEIGHT_ARM_FRONT;
            setArmState(currentArmState);
        }
        else if (
                    currentArmState == armState.ARM_LEFT ||
                    currentArmState == armState.ARM_RIGHT ||
                    currentArmState == armState.ARM_FRONT||
                    currentArmState == armState.ARM_CENTERED_MOVE_LIFT_TO_INTAKE){
            setArmState(currentArmState);
        }
    }

    public void setPosition(double position) {
        arm.setPosition(position);
    }

    public void setArmState(armState targetState) {
        if (targetState == armState.ARM_CENTER) {
            //if the arm position isn't in the intake position already, then we need to set the lift timer so that we wait for a moment to center the arm before lowering the lift
            if (currentArmState!= armState.ARM_CENTER) {
                liftTimer.reset();
            }
            //Center the Arm
            arm.setPosition(ARM_CENTER_INTAKE);
            //Set the targetState so the lift will be lowered to the intake position on a future loop
            currentArmState = armState.ARM_CENTERED_MOVE_LIFT_TO_INTAKE;
        }

        else if (targetState == armState.OPEN_CLAW_CENTER_ARM_LOWER_LIFT_INTAKE_ON) {

            //Open the claw
            if (claw.currentClawState == Claw.clawStates.CLAW_CLOSED) {
                claw.setEasyIntake();
            }

            //might need to add a small delay after opening the claw to give it a moment before we start moving
            activeOpMode.sleep(250);

            //if the arm position isn't in the intake position already, then we need to set the lift timer so that we wait for a moment to center the arm before lowering the lift
            if (currentArmState!=armState.ARM_CENTER) {
                liftTimer.reset();
            }

            //Center the Arm
            arm.setPosition(ARM_CENTER_INTAKE);

            //Start the Intake
            if (intake.currentIntakeState == Intake.intakeState.INTAKE_OFF) {
                intake.toggleIntake();
            }

            //Set the targetState so the lift will be lowered to the intake position on a future loop
            currentArmState = armState.ARM_CENTERED_MOVE_LIFT_TO_INTAKE;
        }

        //Check if the lift is too low and trying to rotate to a non-center position, then move the lift to a safe height before we rotate the arm
        else if (lift.liftMotor.getCurrentPosition() < HEIGHT_FOR_PREVENTING_ARM_ROTATION &&
                currentArmState !=armState.ARM_CENTERED_MOVE_LIFT_TO_INTAKE &&
                !lift.alreadyLifting) {

            //Start raising the lift to the high junction height
            if (targetState == armState.INTAKE_OFF_CLOSE_CLAW_LIFT_MAX_HEIGHT_ARM_FRONT)
            {
                if (intake.currentIntakeState == Intake.intakeState.INTAKE_ON) {
                    intake.toggleIntake();
                }

                //Close the claw
                if (claw.currentClawState == Claw.clawStates.CLAW_EASY_INTAKE ||
                        claw.currentClawState == Claw.clawStates.CLAW_OPEN) {
                    claw.toggleClaw();
                }

                lift.StartLifting(HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL, this);
            }
             //Start raising the lift to a safe height that is well above the height for preventing arm rotation
             else {
                lift.StartLifting(SAFE_HEIGHT_FOR_ALLOWING_ARM_ROTATION, this);
            }
            lift.alreadyLifting = true;
        }
        //next three else statements check whether we are at a safe lift height and then rotates the arm
        else if (   targetState==armState.ARM_LEFT &&
                    lift.liftMotor.getCurrentPosition() >= HEIGHT_FOR_PREVENTING_ARM_ROTATION ) {
            arm.setPosition(ARM_LEFT_OUTTAKE);
            currentArmState = armState.ARM_LEFT;
        } else if ( targetState == armState.ARM_RIGHT &&
                    lift.liftMotor.getCurrentPosition() >= HEIGHT_FOR_PREVENTING_ARM_ROTATION ) {
            arm.setPosition(ARM_RIGHT_OUTTAKE);
            currentArmState = armState.ARM_RIGHT;
        } else if ( targetState==armState.ARM_FRONT &&
                    lift.liftMotor.getCurrentPosition() >= HEIGHT_FOR_PREVENTING_ARM_ROTATION) {
            arm.setPosition(ARM_FRONT_OUTTAKE);
            currentArmState = armState.ARM_FRONT;
        } else if ( targetState == armState.INTAKE_OFF_CLOSE_CLAW_LIFT_MAX_HEIGHT_ARM_FRONT &&
                    lift.liftMotor.getCurrentPosition() >= HEIGHT_FOR_PREVENTING_ARM_ROTATION){
            if (lift.liftMotor.getTargetPosition() != HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL){
                lift.StartLifting(HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL, this);
            }
            arm.setPosition(ARM_FRONT_OUTTAKE);
            currentArmState = armState.ARM_FRONT;
        }

        //Lower the lift if the arm is centered and enough time has passed
        else if (targetState == armState.ARM_CENTERED_MOVE_LIFT_TO_INTAKE && liftTimer.seconds() > SECONDS_TO_CENTER_ARM_BEFORE_LIFT_LOWER) {
            currentArmState = armState.ARM_CENTER;
            lift.StartLifting(targetLiftPositionAfterArmRotation, this);
        }
    }


    public void centerArmBeforeRotation(armState targetState, double target) {
        if (targetState == armState.ARM_CENTER) {
            //if the arm position isn't in the intake position already, then we need to set the lift timer so that we wait for a moment to center the arm before lowering the lift
            if (currentArmState!= armState.ARM_CENTER) {
                liftTimer.reset();
                targetLiftPositionAfterArmRotation = target;
            }
            //Center the Arm
            arm.setPosition(ARM_CENTER_INTAKE);
            //Set the targetState so the lift will be lowered to the intake position on a future loop once enough time to ensure the arm is centered has passed
            currentArmState = armState.ARM_CENTERED_MOVE_LIFT_TO_INTAKE;
        }
    }
}

