package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.ARM_LEFT_OUTTAKE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.ARM_RIGHT_OUTTAKE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.armState;
import static org.firstinspires.ftc.teamcode.ObjectClasses.DriveTrain.MED_SPEED;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.CONE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.EIGHTH_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FIVE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FOUR_CONE_STACK_INTAKE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FULL_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.HALF_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.HALF_TILE_DISTANCE_STRAFE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.ONE_CONE_INTAKE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.QUARTER_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.THIRTYSECOND_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.THREE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.TWO_CONE_STACK_INTAKE_HEIGHT_ENC_VAL;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.ObjectClasses.AprilTagVision;
import org.firstinspires.ftc.teamcode.ObjectClasses.Arm;
import org.firstinspires.ftc.teamcode.ObjectClasses.ButtonConfig;
import org.firstinspires.ftc.teamcode.ObjectClasses.Claw;
import org.firstinspires.ftc.teamcode.ObjectClasses.DriveTrain;
import org.firstinspires.ftc.teamcode.ObjectClasses.Gyro;
import org.firstinspires.ftc.teamcode.ObjectClasses.Intake;
import org.firstinspires.ftc.teamcode.ObjectClasses.Lift;

@Autonomous(name = "AUTO_SCORE_6_AND_PARK")
public class AUTO_SCORE_6_AND_PARK extends LinearOpMode {

    DriveTrain MecDrive = new DriveTrain(this);
    ButtonConfig BConfig = new ButtonConfig(this);
    AprilTagVision Vision = new AprilTagVision();
    Intake ServoIntake = new Intake();
    Claw ServoClaw = new Claw();
    Lift Lift = new Lift(this);
    Arm ServoArm = new Arm(Lift, ServoIntake, ServoClaw, this);
    Gyro Gyro = new Gyro(this);

    public final ElapsedTime runtime = new ElapsedTime();

    Gamepad currentGamepad1 = new Gamepad();
    Gamepad previousGamepad1 = new Gamepad();
    Gamepad currentGamepad2 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad();

    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initializing");
        telemetry.update();
        Vision.init(hardwareMap);
        MecDrive.init(hardwareMap);
        ServoArm.init(hardwareMap);
        ServoIntake.init(hardwareMap);
        ServoClaw.init(hardwareMap);
        Lift.init(hardwareMap);
        BConfig.init();

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        sleep(1000);

        while (!isStarted()) {

            //save current and previous gamepad values for one loop
            previousGamepad1 = BConfig.copy(currentGamepad1);
            currentGamepad1 = BConfig.copy(gamepad1);

            previousGamepad2 = BConfig.copy(currentGamepad2);
            currentGamepad2 = BConfig.copy(gamepad2);

            //Use Webcam to find out Signal using April Tags
            Vision.CheckForAprilTags(this);

            // User sets starting location left or right, and confirms selection with a button press
            // LEFT is a multiplier of 1, RIGHT is a multiplier of -1
            BConfig.ConfigureStartingPosition( currentGamepad1.dpad_left, previousGamepad1.dpad_left,
                    currentGamepad1.dpad_right, previousGamepad1.dpad_right,
                    currentGamepad1.b,          previousGamepad1.b);

            telemetry.addData("Signal", "Signal(%s), Number(%s)", Vision.currentSignal, Vision.currentSignal);
            telemetry.addLine(" ");
            telemetry.addLine("Select Starting Position with D-pad");
            telemetry.addData("Current Starting Position ", ButtonConfig.currentStartPosition);
            if (ButtonConfig.confirmStartingPositionSelection == false) {
                telemetry.addData("Unlocked", "Press B to lock selection");
            } else {
                telemetry.addData("Locked", "Press B to unlock selection");
            }
            telemetry.update();
        }

       //BEGINNING OF AUTO
        runtime.reset();
        Gyro.init(hardwareMap);

        telemetry.addData("Signal is ", Vision.currentSignal);
        telemetry.addData("Selected Starting Position ", ButtonConfig.currentStartPosition);
        telemetry.update();

        //Drive Forward
        Lift.StartLifting(MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL, ServoArm);
        MecDrive.startEncoderDrive(MED_SPEED, FULL_TILE_DISTANCE_DRIVE*2+HALF_TILE_DISTANCE_DRIVE);
        while (opModeIsActive() && (MecDrive.alreadyDriving)) {
            MecDrive.ContinueDriving();
            Lift.ContinueLifting();
        }

        //Rotate
        if ((ButtonConfig.currentStartPosition == ButtonConfig.StartingPosition.RIGHT_SIDE))
        {
            MecDrive.turnToPID(90, Gyro);
            while (opModeIsActive() && MecDrive.alreadyPIDTurning == true) {
                MecDrive.ContinuePIDTurning(Gyro);
            }
        }
        else
        {
            MecDrive.turnToPID(-90, Gyro);
            while (opModeIsActive() && MecDrive.alreadyPIDTurning == true) {
                MecDrive.ContinuePIDTurning(Gyro);
            }
        }

        //Drive in Front of High Pole
        MecDrive.startEncoderDrive(MED_SPEED, HALF_TILE_DISTANCE_DRIVE);
        Lift.StartLifting(HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL, ServoArm);
        while (opModeIsActive() && (MecDrive.alreadyDriving)) {
            MecDrive.ContinueDriving();
            Lift.ContinueLifting();
        }

        //Strafe close to High Pole
        MecDrive.startStrafeDrive(MED_SPEED, -(HALF_TILE_DISTANCE_STRAFE) * ButtonConfig.startPositionMultiplier);
        if ((ButtonConfig.currentStartPosition == ButtonConfig.StartingPosition.RIGHT_SIDE)) {
            ServoArm.setPosition(ARM_RIGHT_OUTTAKE);
        } else ServoArm.setPosition(ARM_LEFT_OUTTAKE);
        while (opModeIsActive() && MecDrive.alreadyStrafing == true) {
            MecDrive.ContinueStrafing();
            Lift.ContinueLifting();
        }

        sleep(100);

        //Open claw to drop cone
        ServoClaw.openClaw();

        sleep(100);

        //Strafe away from High Pole
        MecDrive.startStrafeDrive(MED_SPEED, (HALF_TILE_DISTANCE_STRAFE) * ButtonConfig.startPositionMultiplier);
        while (opModeIsActive() && MecDrive.alreadyStrafing == true) {
            MecDrive.ContinueStrafing();
        }

        int coneDeliveryTracker = 1;
        int coneStackTracker = 5;

        telemetry.addData("Cones:", "Stack(%s)/Delivered(%s)", coneStackTracker, coneDeliveryTracker);
        telemetry.addData("Current Lift Height", Lift.liftMotor.getCurrentPosition());
        telemetry.addData("Status", "Run Time: " + runtime);
        telemetry.addData("Current Angle", Gyro.getAngle());
        telemetry.update();

        while (coneStackTracker > 1 && runtime.seconds() < 20)
        {
            //turn to 90
            MecDrive.turnToPID(-90* ButtonConfig.startPositionMultiplier, Gyro);
             while (opModeIsActive() && (MecDrive.alreadyPIDTurning)) {
                MecDrive.ContinuePIDTurning(Gyro);
            }

            //close claw to intake position
            ServoClaw.setEasyIntake(ServoArm);

            //move turret to pickup position
            ServoArm.setArmState(armState.ARM_CENTER);

            //lower lift to correct cone stack intake height
            switch (coneStackTracker) {
                case 5: {
                    Lift.StartLifting(FIVE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL, ServoArm);
                    break;
                }
                case 4: {
                    Lift.StartLifting(FOUR_CONE_STACK_INTAKE_HEIGHT_ENC_VAL, ServoArm);
                    break;
                }
                case 3: {
                    Lift.StartLifting(THREE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL, ServoArm);
                    break;
                }
                case 2: {
                    Lift.StartLifting(TWO_CONE_STACK_INTAKE_HEIGHT_ENC_VAL, ServoArm);
                    break;
                }
                case 1: {
                    Lift.StartLifting(ONE_CONE_INTAKE_HEIGHT_ENC_VAL, ServoArm);
                    break;
                }
            }

            //Drive near cone stack while setting lift to correct height
            MecDrive.startEncoderDrive(MED_SPEED, -(FULL_TILE_DISTANCE_DRIVE+THIRTYSECOND_TILE_DISTANCE_DRIVE));
            while (opModeIsActive() && (MecDrive.alreadyDriving)) {
                MecDrive.ContinueDriving();
                Lift.ContinueLifting();
                telemetry.addData("Cones:", "Stack(%s)/Delivered(%s)", coneStackTracker, coneDeliveryTracker);
                telemetry.addData("Current Lift Height", Lift.liftMotor.getCurrentPosition());
                telemetry.addData("Status", "Run Time: " + runtime);
                telemetry.addData("Current Angle", Gyro.getAngle());
                telemetry.update();
            }

            //turn to 90
            MecDrive.turnToPID(-90* ButtonConfig.startPositionMultiplier, Gyro);
            while (opModeIsActive() && (MecDrive.alreadyPIDTurning)) {
                MecDrive.ContinuePIDTurning(Gyro);
                Lift.ContinueLifting();
            }

            MecDrive.ColorStrafe(MED_SPEED, MecDrive.activeOpMode);

            //turn on intake to grab cone
            ServoIntake.toggleIntake();

            //Drive near cone stack while setting lift to correct height
            MecDrive.startEncoderDrive(MED_SPEED, -HALF_TILE_DISTANCE_DRIVE+EIGHTH_TILE_DISTANCE_DRIVE);
            while (opModeIsActive() && ( MecDrive.alreadyDriving)) {
                MecDrive.ContinueDriving();
                telemetry.addData("Cones:", "Stack(%s)/Delivered(%s)", coneStackTracker, coneDeliveryTracker);
                telemetry.addData("Current Lift Height", Lift.liftMotor.getCurrentPosition());
                telemetry.addData("Status", "Run Time: " + runtime);
                telemetry.addData("Current Angle", Gyro.getAngle());
                telemetry.update();
            }

            //wait for the intake to work
            sleep(200);

            //Close claw
            ServoClaw.closeClaw();

            //turn off intake now that cone is grabbed
            ServoIntake.turnIntakeOff();

            coneStackTracker = coneStackTracker - 1;

            telemetry.addData("Cones:", "Stack(%s)/Delivered(%s)", coneStackTracker, coneDeliveryTracker);
            telemetry.addData("Current Lift Height", Lift.liftMotor.getCurrentPosition());
            telemetry.addData("Status", "Run Time: " + runtime);
            telemetry.addData("Current Angle", Gyro.getAngle());
            telemetry.update();

            //raise lift to above five cone starting stack height
            Lift.StartLifting(Lift.liftMotor.getCurrentPosition() + CONE_HEIGHT_ENC_VAL, ServoArm);
            while (opModeIsActive() && Lift.alreadyLifting == true) {
                Lift.ContinueLifting();
                Lift.StartLifting(FIVE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL + (2 * CONE_HEIGHT_ENC_VAL), ServoArm);
                telemetry.addData("Cones:", "Stack(%s)/Delivered(%s)", coneStackTracker, coneDeliveryTracker);
                telemetry.addData("Current Lift Height", Lift.liftMotor.getCurrentPosition());
                telemetry.addData("Status", "Run Time: " + runtime);
                telemetry.addData("Current Angle", Gyro.getAngle());
                telemetry.update();
            }

            //Drive toward middle of field after cone has been lifted off the stack
            MecDrive.startEncoderDrive(MED_SPEED, QUARTER_TILE_DISTANCE_DRIVE);
            while (opModeIsActive() && MecDrive.alreadyDriving == true) {
                MecDrive.ContinueDriving();
            }

            //turn to 90
            MecDrive.turnToPID(-90 * ButtonConfig.startPositionMultiplier, Gyro);
            while (opModeIsActive() && (MecDrive.alreadyPIDTurning)) {
                MecDrive.ContinuePIDTurning(Gyro);
            }

            //Drive toward middle of field after cone has been lifted off the stack
            Lift.StartLifting(HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL, ServoArm);
            MecDrive.startEncoderDrive(MED_SPEED,FULL_TILE_DISTANCE_DRIVE+QUARTER_TILE_DISTANCE_DRIVE);
            while (opModeIsActive() && MecDrive.alreadyDriving == true) {
                MecDrive.ContinueDriving();
                Lift.ContinueLifting();
                }

            //move turret to deliver position
            if(ButtonConfig.startPositionMultiplier == -1){
                ServoArm.setPosition(ARM_RIGHT_OUTTAKE);}
            else if (ButtonConfig.startPositionMultiplier == 1){
                ServoArm.setPosition(ARM_LEFT_OUTTAKE);}

            //Strafe to the high pole while raising lift to height to deliver to High Junction
            MecDrive.startStrafeDrive(MED_SPEED,-HALF_TILE_DISTANCE_STRAFE * ButtonConfig.startPositionMultiplier);
            while (opModeIsActive() && (MecDrive.alreadyStrafing)) {
                MecDrive.ContinueStrafing();
                Lift.ContinueLifting();
                telemetry.addData("Cones:", "Stack(%s)/Delivered(%s)", coneStackTracker, coneDeliveryTracker);
                telemetry.addData("Current Lift Height", Lift.liftMotor.getCurrentPosition());
                telemetry.addData("Status", "Run Time: " + runtime);
                telemetry.addData("Current Angle", Gyro.getAngle());
                telemetry.update();
            }

            //drop off cone
            ServoClaw.openClaw();
            coneDeliveryTracker = coneDeliveryTracker +1;

            telemetry.addData("Cones:", "Stack(%s)/Delivered(%s)", coneStackTracker, coneDeliveryTracker);
            telemetry.addData("Current Lift Height", Lift.liftMotor.getCurrentPosition());
            telemetry.addData("Status", "Run Time: " + runtime);
            telemetry.addData("Current Angle", Gyro.getAngle());
            telemetry.update();

            //wait for cone to be released
            sleep(350);

            //strafe away from the high pole
            MecDrive.startStrafeDrive(MED_SPEED,HALF_TILE_DISTANCE_STRAFE * ButtonConfig.startPositionMultiplier);
            while (opModeIsActive() && MecDrive.alreadyStrafing == true) {
                MecDrive.ContinueStrafing();
            }

            telemetry.addData("Cones:", "Stack(%s)/Delivered(%s)", coneStackTracker, coneDeliveryTracker);
            telemetry.addData("Current Lift Height", Lift.liftMotor.getCurrentPosition());
            telemetry.addData("Status", "Run Time: " + runtime);
            telemetry.update();
        }

        ServoArm.setArmState(armState.ARM_CENTER);
        ServoClaw.toggleClaw();
        Lift.StartLifting(ONE_CONE_INTAKE_HEIGHT_ENC_VAL, ServoArm);

        //Park code
        if (Vision.currentSignal == AprilTagVision.Signal.LEFT) {
            MecDrive.startEncoderDrive(MED_SPEED, (FULL_TILE_DISTANCE_DRIVE * ButtonConfig.startPositionMultiplier)-HALF_TILE_DISTANCE_DRIVE);
        } else if (Vision.currentSignal == AprilTagVision.Signal.MIDDLE) {
            MecDrive.startEncoderDrive(MED_SPEED, -HALF_TILE_DISTANCE_DRIVE);
        } else if (Vision.currentSignal == AprilTagVision.Signal.RIGHT) {
            MecDrive.startEncoderDrive(MED_SPEED, (-FULL_TILE_DISTANCE_DRIVE * ButtonConfig.startPositionMultiplier) -HALF_TILE_DISTANCE_DRIVE);
        }
        while (opModeIsActive() && (MecDrive.alreadyDriving || Lift.alreadyLifting)) {
            MecDrive.ContinueDriving();
            Lift.ContinueLifting();
        }

        MecDrive.turnToPID(0, Gyro);
        while (opModeIsActive() && (MecDrive.alreadyPIDTurning)) {
            MecDrive.ContinuePIDTurning(Gyro);
        }

        MecDrive.startEncoderDrive(MED_SPEED, -HALF_TILE_DISTANCE_DRIVE+QUARTER_TILE_DISTANCE_DRIVE);
        while (opModeIsActive() && MecDrive.alreadyDriving == true) {
            MecDrive.ContinueDriving();
        }

        telemetry.addData("Cones:", "Stack(%s)/Delivered(%s)", coneStackTracker, coneDeliveryTracker);
        telemetry.addData("Current Lift Height", Lift.liftMotor.getCurrentPosition());
        telemetry.addData("Status", "Run Time: " + runtime);
        telemetry.update();
        sleep(2000);
    }
}



