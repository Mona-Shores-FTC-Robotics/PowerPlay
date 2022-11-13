package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.ARM_CENTER_INTAKE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.ARM_LEFT_OUTTAKE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.ARM_RIGHT_OUTTAKE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.armState;
import static org.firstinspires.ftc.teamcode.ObjectClasses.DriveTrain.HIGH_SPEED;
import static org.firstinspires.ftc.teamcode.ObjectClasses.DriveTrain.LOW_SPEED;
import static org.firstinspires.ftc.teamcode.ObjectClasses.DriveTrain.MED_SPEED;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.CONE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.EIGHTH_TILE_DISTANCE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FIVE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FOUR_CONE_STACK_INTAKE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FULL_TILE_DISTANCE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.HALF_TILE_DISTANCE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.ONE_CONE_INTAKE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.QUARTER_TILE_DISTANCE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.SIXTEENTH_TILE_DISTANCE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.THIRTYSECOND_TILE_DISTANCE;
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

    int Signal;
    DriveTrain MecDrive = new DriveTrain(this);
    ButtonConfig BConfig = new ButtonConfig(this);
    AprilTagVision Vision = new AprilTagVision();

    Intake ServoIntake = new Intake();
    Claw ServoClaw = new Claw();
    Lift Lift = new Lift(this);
    Arm ServoArm = new Arm(Lift);
    Gyro Gyro = new Gyro(this);

    public final ElapsedTime runtime = new ElapsedTime();
    public final ElapsedTime liftDelay = new ElapsedTime();
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

            telemetry.addData("Signal is ", Vision.currentSignal);
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

        telemetry.addData("Signal is ", Signal);
        telemetry.addData("Selected Starting Position ", ButtonConfig.currentStartPosition);
        telemetry.update();

        //Drive Forward
        Lift.StartLifting(400);
        MecDrive.startEncoderDrive(HIGH_SPEED , (FULL_TILE_DISTANCE*2)+QUARTER_TILE_DISTANCE, (FULL_TILE_DISTANCE*2)+QUARTER_TILE_DISTANCE);
        while (opModeIsActive() && MecDrive.alreadyDriving == true) {
            MecDrive.ContinueDriving();
        }

        //Drive Backwards
        MecDrive.startEncoderDrive(HIGH_SPEED, -(EIGHTH_TILE_DISTANCE+EIGHTH_TILE_DISTANCE), -(EIGHTH_TILE_DISTANCE+EIGHTH_TILE_DISTANCE));
        while (opModeIsActive() && MecDrive.alreadyDriving == true) {
            MecDrive.ContinueDriving();
        }

        //Rotate
        if ((ButtonConfig.currentStartPosition == org.firstinspires.ftc.teamcode.ObjectClasses.ButtonConfig.StartingPosition.RIGHT_SIDE))
        {
            MecDrive.turnTo(90, Gyro);
            while (opModeIsActive() && MecDrive.alreadyTurning == true) {
                MecDrive.ContinueTurning(Gyro);
            }
        }
        else
        {
            MecDrive.turnTo(-90, Gyro);
            while (opModeIsActive() && MecDrive.alreadyTurning == true) {
                MecDrive.ContinueTurning(Gyro);
            }
        }

        //Drive in Front of High Pole
        MecDrive.startEncoderDrive(MED_SPEED,           HALF_TILE_DISTANCE,
                                                        HALF_TILE_DISTANCE);
        Lift.StartLifting(HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL);
        while (opModeIsActive() && MecDrive.alreadyDriving == true) {
            MecDrive.ContinueDriving();
            Lift.ContinueLifting();
        }

        sleep(1000);

        //Strafe close to High Pole
        MecDrive.startStrafeDrive(LOW_SPEED, -(QUARTER_TILE_DISTANCE+EIGHTH_TILE_DISTANCE)* ButtonConfig.startPositionMultiplier, -(QUARTER_TILE_DISTANCE+EIGHTH_TILE_DISTANCE)*ButtonConfig.startPositionMultiplier);
        if ((ButtonConfig.currentStartPosition == org.firstinspires.ftc.teamcode.ObjectClasses.ButtonConfig.StartingPosition.RIGHT_SIDE)) {
            ServoArm.setPosition(ARM_RIGHT_OUTTAKE);
        } else ServoArm.setPosition(ARM_LEFT_OUTTAKE);
        while (opModeIsActive() && MecDrive.alreadyStrafing == true) {
            MecDrive.ContinueStrafing();
        }


        //Back off just a little
        MecDrive.startStrafeDrive(LOW_SPEED, (SIXTEENTH_TILE_DISTANCE)* ButtonConfig.startPositionMultiplier, (SIXTEENTH_TILE_DISTANCE)*ButtonConfig.startPositionMultiplier);
        while (opModeIsActive() && MecDrive.alreadyStrafing == true) {
            MecDrive.ContinueStrafing();
        }

        sleep(1000);

        //Open claw to drop cone
        ServoClaw.toggleClaw();

        sleep (1000);

        //Strafe away from High Pole(
        MecDrive.startStrafeDrive(LOW_SPEED, (QUARTER_TILE_DISTANCE+SIXTEENTH_TILE_DISTANCE)* ButtonConfig.startPositionMultiplier,
                (QUARTER_TILE_DISTANCE+SIXTEENTH_TILE_DISTANCE)* ButtonConfig.startPositionMultiplier);
        while (opModeIsActive() && MecDrive.alreadyStrafing == true) {
            MecDrive.ContinueStrafing();
        }

        sleep(1000);
        int coneDeliveryTracker = 1;
        int coneStackTracker = 5;

        telemetry.addData("Cones:", "Stack(%s)/Delivered(%s)", coneStackTracker, coneDeliveryTracker);
        telemetry.addData("Current Lift Height", Lift.liftMotor.getCurrentPosition());
        telemetry.addData("Status", "Run Time: " + runtime);
        telemetry.addData("Current Angle", Gyro.getAngle());
        telemetry.update();



        while (coneStackTracker > 1)
        {
            //turn to 90
            MecDrive.turnTo(90* ButtonConfig.startPositionMultiplier, Gyro);

            //close claw for next intake
            ServoClaw.toggleClaw();

            //move turret to pickup position
            ServoArm.setPosition(ARM_CENTER_INTAKE);

            //lower lift to correct cone stack intake height
            switch (coneStackTracker) {
                case 5: {
                    Lift.StartLifting(FIVE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL);
                    break;
                }
                case 4: {
                    Lift.StartLifting(FOUR_CONE_STACK_INTAKE_HEIGHT_ENC_VAL);
                    break;
                }
                case 3: {
                    Lift.StartLifting(THREE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL);
                    break;
                }
                case 2: {
                    Lift.StartLifting(TWO_CONE_STACK_INTAKE_HEIGHT_ENC_VAL);
                    break;
                }
                case 1: {
                    Lift.StartLifting(ONE_CONE_INTAKE_HEIGHT_ENC_VAL);
                    break;
                }
            }

            //Drive near cone stack while setting lift to correct height
            MecDrive.startEncoderDrive(LOW_SPEED,  -(FULL_TILE_DISTANCE+QUARTER_TILE_DISTANCE),
                                                    -(FULL_TILE_DISTANCE+QUARTER_TILE_DISTANCE));
            while (opModeIsActive() && (Lift.alreadyLifting || MecDrive.alreadyDriving)) {
                MecDrive.ContinueDriving();
                Lift.ContinueLifting();
                telemetry.addData("Cones:", "Stack(%s)/Delivered(%s)", coneStackTracker, coneDeliveryTracker);
                telemetry.addData("Current Lift Height", Lift.liftMotor.getCurrentPosition());
                telemetry.addData("Status", "Run Time: " + runtime);
                telemetry.addData("Current Angle", Gyro.getAngle());
                telemetry.update();
            }


            //turn to 90
            MecDrive.turnTo(90* ButtonConfig.startPositionMultiplier, Gyro);

            sleep(1000);

            MecDrive.ColorStrafe(LOW_SPEED, this);

            sleep(1000);

            MecDrive.startStrafeDrive(LOW_SPEED,     -(SIXTEENTH_TILE_DISTANCE * ButtonConfig.startPositionMultiplier),
                                                    -(SIXTEENTH_TILE_DISTANCE * ButtonConfig.startPositionMultiplier));
            while (opModeIsActive() && (MecDrive.alreadyStrafing)) {
                MecDrive.ContinueStrafing();
                telemetry.addData("Cones:", "Stack(%s)/Delivered(%s)", coneStackTracker, coneDeliveryTracker);
                telemetry.addData("Current Lift Height", Lift.liftMotor.getCurrentPosition());
                telemetry.addData("Status", "Run Time: " + runtime);
                telemetry.addData("Current Angle", Gyro.getAngle());
                telemetry.update();
            }


            //Drive near cone stack while setting lift to correct height
            MecDrive.startEncoderDrive(LOW_SPEED,  -(QUARTER_TILE_DISTANCE),
                                                    -(QUARTER_TILE_DISTANCE));
            while (opModeIsActive() && ( MecDrive.alreadyDriving)) {
                MecDrive.ContinueDriving();
                telemetry.addData("Cones:", "Stack(%s)/Delivered(%s)", coneStackTracker, coneDeliveryTracker);
                telemetry.addData("Current Lift Height", Lift.liftMotor.getCurrentPosition());
                telemetry.addData("Status", "Run Time: " + runtime);
                telemetry.addData("Current Angle", Gyro.getAngle());
                telemetry.update();
            }

            //turn on intake to grab cone
            ServoIntake.toggleIntake();

            //wait for the intake to work
            sleep(1000);

            coneStackTracker = coneStackTracker - 1;

            telemetry.addData("Cones:", "Stack(%s)/Delivered(%s)", coneStackTracker, coneDeliveryTracker);
            telemetry.addData("Current Lift Height", Lift.liftMotor.getCurrentPosition());
            telemetry.addData("Status", "Run Time: " + runtime);
            telemetry.addData("Current Angle", Gyro.getAngle());
            telemetry.update();

            //turn off intake now that cone is grabbed
            ServoIntake.toggleIntake();

            //raise lift to above five cone starting stack height
            Lift.StartLifting(FIVE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL + (2* CONE_HEIGHT_ENC_VAL));
            while (opModeIsActive() && Lift.alreadyLifting == true) {
                Lift.ContinueLifting();
                telemetry.addData("Cones:", "Stack(%s)/Delivered(%s)", coneStackTracker, coneDeliveryTracker);
                telemetry.addData("Current Lift Height", Lift.liftMotor.getCurrentPosition());
                telemetry.addData("Status", "Run Time: " + runtime);
                telemetry.addData("Current Angle", Gyro.getAngle());
                telemetry.update();
            }

            //start the liftDelay timer so we can lift partway through next leg of driving
            liftDelay.reset();
            //Drive toward middle of field after cone has been lifted off the stack
            MecDrive.startEncoderDrive(LOW_SPEED,   (EIGHTH_TILE_DISTANCE),
                                                    (EIGHTH_TILE_DISTANCE));
            while (opModeIsActive() && MecDrive.alreadyDriving == true) {
                MecDrive.ContinueDriving();

            }
            //turn to 90
            MecDrive.turnTo(90* org.firstinspires.ftc.teamcode.ObjectClasses.ButtonConfig.startPositionMultiplier, Gyro);

            //Drive toward middle of field after cone has been lifted off the stack
            MecDrive.startEncoderDrive(HIGH_SPEED,   (FULL_TILE_DISTANCE+QUARTER_TILE_DISTANCE),
                                                    (FULL_TILE_DISTANCE+QUARTER_TILE_DISTANCE));
            while (opModeIsActive() && MecDrive.alreadyDriving == true) {
                MecDrive.ContinueDriving();
                if (liftDelay.seconds() > 2){
                    Lift.StartLifting(HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL);
                }
            }

            //move turret to deliver position
            if(ButtonConfig.startPositionMultiplier == -1){
                ServoArm.setPosition(ARM_RIGHT_OUTTAKE);}
            else if (ButtonConfig.startPositionMultiplier == 1){
                ServoArm.setPosition(ARM_LEFT_OUTTAKE);}

            //Strafe to the high pole while raising lift to height to deliver to High Junction
            MecDrive.startStrafeDrive(LOW_SPEED,     -(QUARTER_TILE_DISTANCE * ButtonConfig.startPositionMultiplier),
                                                     -(QUARTER_TILE_DISTANCE * ButtonConfig.startPositionMultiplier));
            while (opModeIsActive() && (MecDrive.alreadyStrafing)) {
                MecDrive.ContinueStrafing();
                telemetry.addData("Cones:", "Stack(%s)/Delivered(%s)", coneStackTracker, coneDeliveryTracker);
                telemetry.addData("Current Lift Height", Lift.liftMotor.getCurrentPosition());
                telemetry.addData("Status", "Run Time: " + runtime);
                telemetry.addData("Current Angle", Gyro.getAngle());
                telemetry.update();
            }

            //drop off cone
            ServoClaw.toggleClaw();
            coneDeliveryTracker = coneDeliveryTracker +1;

            telemetry.addData("Cones:", "Stack(%s)/Delivered(%s)", coneStackTracker, coneDeliveryTracker);
            telemetry.addData("Current Lift Height", Lift.liftMotor.getCurrentPosition());
            telemetry.addData("Status", "Run Time: " + runtime);
            telemetry.addData("Current Angle", Gyro.getAngle());
            telemetry.update();

            //wait for cone to be released
            sleep(350);

            //strafe away from the high pole
            MecDrive.startStrafeDrive(MED_SPEED,   (QUARTER_TILE_DISTANCE * ButtonConfig.startPositionMultiplier),
                                                    (QUARTER_TILE_DISTANCE * ButtonConfig.startPositionMultiplier));
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
        Lift.StartLifting(ONE_CONE_INTAKE_HEIGHT_ENC_VAL);

        //Park code
        if (Vision.currentSignal == AprilTagVision.Signal.LEFT) {
            MecDrive.startEncoderDrive(MED_SPEED, ((FULL_TILE_DISTANCE * ButtonConfig.startPositionMultiplier)-HALF_TILE_DISTANCE), ((FULL_TILE_DISTANCE* ButtonConfig.startPositionMultiplier) - HALF_TILE_DISTANCE));
        } else if (Vision.currentSignal == AprilTagVision.Signal.MIDDLE) {
            MecDrive.startEncoderDrive(MED_SPEED, -HALF_TILE_DISTANCE, -HALF_TILE_DISTANCE);
        } else if (Vision.currentSignal == AprilTagVision.Signal.RIGHT) {
            MecDrive.startEncoderDrive(MED_SPEED, ((-FULL_TILE_DISTANCE * ButtonConfig.startPositionMultiplier) -HALF_TILE_DISTANCE), ((-FULL_TILE_DISTANCE * ButtonConfig.startPositionMultiplier) -HALF_TILE_DISTANCE));
        }
        while (opModeIsActive() && (MecDrive.alreadyDriving || Lift.alreadyLifting)) {
            MecDrive.ContinueDriving();
            Lift.ContinueLifting();
        }

        MecDrive.turnTo(0, Gyro);
        while (opModeIsActive() && (MecDrive.alreadyTurning)) {
            MecDrive.ContinueTurning(Gyro);
        }

        MecDrive.startEncoderDrive(MED_SPEED, -HALF_TILE_DISTANCE+QUARTER_TILE_DISTANCE, -HALF_TILE_DISTANCE+QUARTER_TILE_DISTANCE);
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



