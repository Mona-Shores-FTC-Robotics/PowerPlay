package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.ARM_CENTER_INTAKE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.ARM_LEFT_OUTTAKE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.ARM_RIGHT_OUTTAKE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.armState;
import static org.firstinspires.ftc.teamcode.ObjectClasses.DriveTrain.LOW_SPEED;
import static org.firstinspires.ftc.teamcode.ObjectClasses.DriveTrain.MED_SPEED;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.CONE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.EIGHTH_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.EIGHTH_TILE_DISTANCE_STRAFE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FIVE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FOUR_CONE_STACK_INTAKE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FULL_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.HALF_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.LOW_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.ONE_CONE_INTAKE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.QUARTER_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.QUARTER_TILE_DISTANCE_STRAFE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.SIXTEENTH_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.THIRTYSECOND_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.THREE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.TWO_CONE_STACK_INTAKE_HEIGHT_ENC_VAL;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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

@Autonomous(name = "AUTO_SCORE_3_AND_PARK_SAFE")
public class AUTO_SCORE_3_AND_PARK_SAFE extends LinearOpMode {

    DriveTrain MecDrive = new DriveTrain(this);
    ButtonConfig BConfig = new ButtonConfig(this);
    AprilTagVision Vision = new AprilTagVision();
    Claw ServoClaw = new Claw();
    Intake ServoIntake = new Intake(ServoClaw, this);
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

            telemetry.addData("Signal", "Signal(%s), Number(%s)", Vision.currentSignal, Vision.currentSignalNumber);
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
        //13s to score cone on high goal and get to cone stack line
        //10s to get cone from stack and score on low
        //3s to park middle
        //
        //5pts + 3pts + 20pts = 28pt auto
        //5 + 5 + 5 = 35pt auto
        //4*4 + 20 - best auto so far

        //Target: 35pt Auto (1 starting + 3 from stack)
        //Target: 121 TeleOp (18 from Substation + 10 from stacks)
        //Target: 25 End Game (5 junctions + 1 beacon)

        runtime.reset();
        Gyro.init(hardwareMap);

        telemetry.addData("Signal is ", Vision.currentSignal);
        telemetry.addData("Selected Starting Position ", ButtonConfig.currentStartPosition);
        telemetry.update();

        //Drive Forward - 3s (3s)
        Lift.StartLifting(MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL, ServoArm);
        MecDrive.startEncoderDrive(MED_SPEED, (FULL_TILE_DISTANCE_DRIVE + HALF_TILE_DISTANCE_DRIVE) + EIGHTH_TILE_DISTANCE_DRIVE);
        while (opModeIsActive() && (Lift.alreadyLifting || MecDrive.alreadyDriving)) {
            MecDrive.ContinueDriving();
            Lift.ContinueLifting();
        }

        //Strafe close to Mid Pole - 3s (9s)
        MecDrive.startStrafeDrive(LOW_SPEED, (QUARTER_TILE_DISTANCE_DRIVE) * ButtonConfig.startPositionMultiplier);
        if ((ButtonConfig.currentStartPosition == ButtonConfig.StartingPosition.RIGHT_SIDE)) {
            ServoArm.setArmState(armState.ARM_LEFT, null);
        } else ServoArm.setArmState(armState.ARM_RIGHT, null);
        while (opModeIsActive() && MecDrive.alreadyStrafing == true) {
            MecDrive.ContinueStrafing();
        }

        //dunk cone - 1s (10s)
        Lift.StartLifting(Lift.liftMotor.getCurrentPosition()-325, ServoArm);
        while (opModeIsActive() && (Lift.alreadyLifting)) {
            Lift.ContinueLifting();
        }

        //Open claw and wait a moment to drop cone - .5s (10.5s)
        ServoClaw.openClaw();
        sleep(200);

        //lift before moving - .5s (11s)
        Lift.StartLifting(MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL, ServoArm);
        while (opModeIsActive() && (Lift.alreadyLifting)) {
            Lift.ContinueLifting();
        }

        //Strafe away from mid Pole - 1s (12s)
        MecDrive.startStrafeDrive(MED_SPEED, -(QUARTER_TILE_DISTANCE_STRAFE) * ButtonConfig.startPositionMultiplier);
        while (opModeIsActive() && MecDrive.alreadyStrafing == true) {
            MecDrive.ContinueStrafing();
        }

        //drive to center of tile
        MecDrive.startEncoderDrive(MED_SPEED, (QUARTER_TILE_DISTANCE_DRIVE + QUARTER_TILE_DISTANCE_DRIVE));
        while (opModeIsActive() && MecDrive.alreadyDriving == true) {
            MecDrive.ContinueDriving();
        }

        //Rotate - 1s (4s)
        if ((ButtonConfig.currentStartPosition == ButtonConfig.StartingPosition.RIGHT_SIDE))
        {
            MecDrive.turnToPID(90, Gyro);
        }
        else
        {
            MecDrive.turnToPID(-90, Gyro);
        }
        while (opModeIsActive() && MecDrive.alreadyPIDTurning == true) {
            MecDrive.ContinuePIDTurning(Gyro);
        }



        int coneDeliveryTracker = 1;
        int coneStackTracker = 5;

        telemetry.addData("Cones:", "Stack(%s)/Delivered(%s)", coneStackTracker, coneDeliveryTracker);
        telemetry.addData("Current Lift Height", Lift.liftMotor.getCurrentPosition());
        telemetry.addData("Status", "Run Time: " + runtime);
        telemetry.addData("Current Angle", Gyro.getAngle());
        telemetry.update();

        //Drive to the cone stack line - 2s (14s)
        ServoArm.setPosition(ARM_CENTER_INTAKE);
        Lift.StartLifting(FIVE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL, ServoArm);
        MecDrive.startEncoderDrive(.5, -(HALF_TILE_DISTANCE_DRIVE + EIGHTH_TILE_DISTANCE_DRIVE));
        while (opModeIsActive() && (MecDrive.alreadyDriving)) {
            MecDrive.ContinueDriving();
            Lift.ContinueLifting();
            telemetry.addData("Cones:", "Stack(%s)/Delivered(%s)", coneStackTracker, coneDeliveryTracker);
            telemetry.addData("Current Lift Height", Lift.liftMotor.getCurrentPosition());
            telemetry.addData("Status", "Run Time: " + runtime);
            telemetry.addData("Current Angle", Gyro.getAngle());
            telemetry.update();
        }

        //Find the cone stack line - 0s (14s)
        MecDrive.lineFollow(LOW_SPEED, this, Gyro);

        while (coneStackTracker > 3)
        {
            //turn to 90 - 0s (14s)
            MecDrive.turnToPID(-90* ButtonConfig.startPositionMultiplier, Gyro);
            while (opModeIsActive() && (MecDrive.alreadyPIDTurning)) {
                MecDrive.ContinuePIDTurning(Gyro);
            }

            //turn to 90 - 0s (14s)
            //close claw for next intake
            ServoClaw.setEasyIntake();

            ServoArm.setPosition(ARM_CENTER_INTAKE);

            //lower lift to correct cone stack intake height - //turn to 90 - 0s (14s)
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

            //Drive near cone stack while setting lift to correct height - 1s (15s)
            MecDrive.startEncoderDrive(MED_SPEED, -(EIGHTH_TILE_DISTANCE_DRIVE+SIXTEENTH_TILE_DISTANCE_DRIVE));
            while (opModeIsActive() && (MecDrive.alreadyDriving)) {
                MecDrive.ContinueDriving();
                Lift.ContinueLifting();
                telemetry.addData("Cones:", "Stack(%s)/Delivered(%s)", coneStackTracker, coneDeliveryTracker);
                telemetry.addData("Current Lift Height", Lift.liftMotor.getCurrentPosition());
                telemetry.addData("Status", "Run Time: " + runtime);
                telemetry.addData("Current Angle", Gyro.getAngle());
                telemetry.update();
            }

            //turn on intake to grab cone - 0s (15s)
            ServoIntake.turnIntakeOn();

            //Drive near cone stack with intake on - 1s (16s)
            MecDrive.startEncoderDrive(LOW_SPEED, -(QUARTER_TILE_DISTANCE_DRIVE));
            while (opModeIsActive() && ( MecDrive.alreadyDriving)) {
                MecDrive.ContinueDriving();
                telemetry.addData("Cones:", "Stack(%s)/Delivered(%s)", coneStackTracker, coneDeliveryTracker);
                telemetry.addData("Current Lift Height", Lift.liftMotor.getCurrentPosition());
                telemetry.addData("Status", "Run Time: " + runtime);
                telemetry.addData("Current Angle", Gyro.getAngle());
                telemetry.update();
            }

            //turn off intake now that cone is grabbed - 0s (16s)
            ServoIntake.turnIntakeOff();

            //close claw to grab cone -  0s (16s)
            ServoClaw.closeClaw();

            coneStackTracker = coneStackTracker - 1;

            telemetry.addData("Cones:", "Stack(%s)/Delivered(%s)", coneStackTracker, coneDeliveryTracker);
            telemetry.addData("Current Lift Height", Lift.liftMotor.getCurrentPosition());
            telemetry.addData("Status", "Run Time: " + runtime);
            telemetry.addData("Current Angle", Gyro.getAngle());
            telemetry.update();

            //raise lift to above five cone starting stack height -  1s (17s)
            Lift.StartLifting(Lift.liftMotor.getCurrentPosition() + CONE_HEIGHT_ENC_VAL, ServoArm);
            while (opModeIsActive() && Lift.alreadyLifting == true) {
                Lift.ContinueLifting();
                telemetry.addData("Cones:", "Stack(%s)/Delivered(%s)", coneStackTracker, coneDeliveryTracker);
                telemetry.addData("Current Lift Height", Lift.liftMotor.getCurrentPosition());
                telemetry.addData("Status", "Run Time: " + runtime);
                telemetry.addData("Current Angle", Gyro.getAngle());
                telemetry.update();
            }

            //Drive toward middle of field after cone has been lifted off the stack -  1s (18s)
            MecDrive.startEncoderDrive(MED_SPEED, HALF_TILE_DISTANCE_DRIVE+ 1);
            Lift.StartLifting(LOW_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL, ServoArm);
            while (opModeIsActive() && MecDrive.alreadyDriving == true) {
                MecDrive.ContinueDriving();
                Lift.ContinueLifting();
            }

            //turn to 90 - 0s (18s)
            MecDrive.turnToPID(-90 * ButtonConfig.startPositionMultiplier, Gyro);
            while (opModeIsActive() && (MecDrive.alreadyPIDTurning)) {
                MecDrive.ContinuePIDTurning(Gyro);
            }

            //move turret to deliver position - 0s (18s)
            if(ButtonConfig.startPositionMultiplier == 1){
                ServoArm.setPosition(ARM_RIGHT_OUTTAKE);}
            else if (ButtonConfig.startPositionMultiplier == -1){
                ServoArm.setPosition(ARM_LEFT_OUTTAKE);}

            //Strafe to the LOW pole- 2s (20s)
            MecDrive.startStrafeDrive(MED_SPEED,(QUARTER_TILE_DISTANCE_STRAFE+SIXTEENTH_TILE_DISTANCE_DRIVE) * ButtonConfig.startPositionMultiplier);
            while (opModeIsActive() && (MecDrive.alreadyStrafing)) {
                MecDrive.ContinueStrafing();
                Lift.ContinueLifting();
                telemetry.addData("Cones:", "Stack(%s)/Delivered(%s)", coneStackTracker, coneDeliveryTracker);
                telemetry.addData("Current Lift Height", Lift.liftMotor.getCurrentPosition());
                telemetry.addData("Status", "Run Time: " + runtime);
                telemetry.addData("Current Angle", Gyro.getAngle());
                telemetry.update();
            }

            //dunk cone - 1s (21s)
            Lift.StartLifting(Lift.liftMotor.getCurrentPosition()-300, ServoArm);
            while (opModeIsActive() && (Lift.alreadyLifting)) {
                Lift.ContinueLifting();
            }

            //drop off cone - 0s (21s)
            ServoClaw.openClaw();
            sleep(200);

            //lift before moving - 1s (22s)
            Lift.StartLifting(LOW_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL, ServoArm);
            while (opModeIsActive() && (Lift.alreadyLifting)) {
                Lift.ContinueLifting();
            }

            coneDeliveryTracker = coneDeliveryTracker +1;

            telemetry.addData("Cones:", "Stack(%s)/Delivered(%s)", coneStackTracker, coneDeliveryTracker);
            telemetry.addData("Current Lift Height", Lift.liftMotor.getCurrentPosition());
            telemetry.addData("Status", "Run Time: " + runtime);
            telemetry.addData("Current Angle", Gyro.getAngle());
            telemetry.update();

            //strafe away from the LOW pole - 1s (23s)
            MecDrive.startStrafeDrive(MED_SPEED,-(QUARTER_TILE_DISTANCE_STRAFE) * ButtonConfig.startPositionMultiplier);
            while (opModeIsActive() && MecDrive.alreadyStrafing == true) {
                MecDrive.ContinueStrafing();
            }

            telemetry.addData("Cones:", "Stack(%s)/Delivered(%s)", coneStackTracker, coneDeliveryTracker);
            telemetry.addData("Current Lift Height", Lift.liftMotor.getCurrentPosition());
            telemetry.addData("Status", "Run Time: " + runtime);
            telemetry.update();
        }

        //turn to 90 - 0s (23s)
        MecDrive.turnToPID(-90 * ButtonConfig.startPositionMultiplier, Gyro);
        while (opModeIsActive() && (MecDrive.alreadyPIDTurning)) {
            MecDrive.ContinuePIDTurning(Gyro);
        }

        ServoArm.setArmState(armState.ARM_CENTER, ONE_CONE_INTAKE_HEIGHT_ENC_VAL);
        ServoClaw.setEasyIntake();

        //Park code
        if (ButtonConfig.currentStartPosition == ButtonConfig.StartingPosition.LEFT_SIDE){


            if (Vision.currentSignal == AprilTagVision.Signal.LEFT) {
                MecDrive.startEncoderDrive(MED_SPEED, -(FULL_TILE_DISTANCE_DRIVE * ButtonConfig.startPositionMultiplier)
                        + HALF_TILE_DISTANCE_DRIVE);
                while (opModeIsActive() && (MecDrive.alreadyDriving || Lift.alreadyLifting)) {
                    MecDrive.ContinueDriving();
                    Lift.ContinueLifting();
                }
            }


            // Park LEFT SIDE - MIDDLE LOCATION - 4s (27s)
            else if (Vision.currentSignal == AprilTagVision.Signal.MIDDLE) {
                MecDrive.startEncoderDrive(MED_SPEED, HALF_TILE_DISTANCE_DRIVE + SIXTEENTH_TILE_DISTANCE_DRIVE);
                while (opModeIsActive() && (MecDrive.alreadyDriving || Lift.alreadyLifting)) {
                    MecDrive.ContinueDriving();
                    Lift.ContinueLifting();
                }
                //turn to 0
                MecDrive.turnToPID(0, Gyro);
                while (opModeIsActive() && (MecDrive.alreadyPIDTurning)) {
                    MecDrive.ContinuePIDTurning(Gyro);
                }
                MecDrive.startEncoderDrive(MED_SPEED, -HALF_TILE_DISTANCE_DRIVE);
                while (opModeIsActive() && (MecDrive.alreadyDriving)) {
                    MecDrive.ContinueDriving();
                }

            } else if (Vision.currentSignal == AprilTagVision.Signal.RIGHT) {
                MecDrive.startEncoderDrive(MED_SPEED, (FULL_TILE_DISTANCE_DRIVE * ButtonConfig.startPositionMultiplier)
                        +HALF_TILE_DISTANCE_DRIVE + EIGHTH_TILE_DISTANCE_DRIVE);
                while (opModeIsActive() && (MecDrive.alreadyDriving || Lift.alreadyLifting)) {
                    MecDrive.ContinueDriving();
                    Lift.ContinueLifting();
                }
                //turn to 0
                MecDrive.turnToPID(0, Gyro);
                while (opModeIsActive() && (MecDrive.alreadyPIDTurning)) {
                    MecDrive.ContinuePIDTurning(Gyro);
                }
                MecDrive.startEncoderDrive(MED_SPEED, -HALF_TILE_DISTANCE_DRIVE);
                while (opModeIsActive() && (MecDrive.alreadyDriving)) {
                    MecDrive.ContinueDriving();
                }
            }

        }
        else {
            if (Vision.currentSignal == AprilTagVision.Signal.LEFT) {
                MecDrive.startEncoderDrive(MED_SPEED, -(FULL_TILE_DISTANCE_DRIVE * ButtonConfig.startPositionMultiplier)
                        + HALF_TILE_DISTANCE_DRIVE + EIGHTH_TILE_DISTANCE_DRIVE);
                while (opModeIsActive() && (MecDrive.alreadyDriving || Lift.alreadyLifting)) {
                    MecDrive.ContinueDriving();
                    Lift.ContinueLifting();
                }
                //turn to 0
                MecDrive.turnToPID(0, Gyro);
                while (opModeIsActive() && (MecDrive.alreadyPIDTurning)) {
                    MecDrive.ContinuePIDTurning(Gyro);
                }
                MecDrive.startEncoderDrive(MED_SPEED, -HALF_TILE_DISTANCE_DRIVE);
                while (opModeIsActive() && (MecDrive.alreadyDriving)) {
                    MecDrive.ContinueDriving();
                }
            } else if (Vision.currentSignal == AprilTagVision.Signal.MIDDLE) {
                MecDrive.startEncoderDrive(MED_SPEED, HALF_TILE_DISTANCE_DRIVE + SIXTEENTH_TILE_DISTANCE_DRIVE);
                while (opModeIsActive() && (MecDrive.alreadyDriving || Lift.alreadyLifting)) {
                    MecDrive.ContinueDriving();
                    Lift.ContinueLifting();
                }
                //turn to 0
                MecDrive.turnToPID(0, Gyro);
                while (opModeIsActive() && (MecDrive.alreadyPIDTurning)) {
                    MecDrive.ContinuePIDTurning(Gyro);
                }
                MecDrive.startEncoderDrive(MED_SPEED, -HALF_TILE_DISTANCE_DRIVE);
                while (opModeIsActive() && (MecDrive.alreadyDriving)) {
                    MecDrive.ContinueDriving();
                }
            } else if (Vision.currentSignal == AprilTagVision.Signal.RIGHT) {
                MecDrive.startEncoderDrive(MED_SPEED, (FULL_TILE_DISTANCE_DRIVE * ButtonConfig.startPositionMultiplier)
                        +HALF_TILE_DISTANCE_DRIVE);
                while (opModeIsActive() && (MecDrive.alreadyDriving || Lift.alreadyLifting)) {
                    MecDrive.ContinueDriving();
                    Lift.ContinueLifting();
                }
            }
        }


;

        telemetry.addData("Cones:", "Stack(%s)/Delivered(%s)", coneStackTracker, coneDeliveryTracker);
        telemetry.addData("Current Lift Height", Lift.liftMotor.getCurrentPosition());
        telemetry.addData("Status", "Run Time: " + runtime);
        telemetry.update();
        sleep(350); // ORIGALY 750
    }
}