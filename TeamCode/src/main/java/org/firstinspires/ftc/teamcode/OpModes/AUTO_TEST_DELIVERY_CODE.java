package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.ARM_CENTER_INTAKE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.ARM_LEFT_OUTTAKE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.ARM_RIGHT_OUTTAKE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.DriveTrain.LOW_SPEED;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FIVE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.QUARTER_TILE_DISTANCE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.SIXTEENTH_TILE_DISTANCE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.THIRTYSECOND_TILE_DISTANCE;

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


@Autonomous(name = "AUTO_SCORE_ONE_AND_PAR_test_deliver")
public class AUTO_TEST_DELIVERY_CODE extends LinearOpMode {

    int Signal;
    DriveTrain MecDrive = new DriveTrain(this);
    ButtonConfig BConfig = new ButtonConfig(this);

    Intake ServoIntake = new Intake();
    AprilTagVision Vision = new AprilTagVision();
    Claw ServoClaw = new Claw();
    Lift Lift = new Lift(this);
    Arm ServoArm = new Arm(Lift, ServoIntake, ServoClaw, this);
    Gyro Gyro = new Gyro(this);

    private final ElapsedTime runtime = new ElapsedTime();


    Gamepad currentGamepad1 = new Gamepad();
    Gamepad previousGamepad1 = new Gamepad();
    Gamepad currentGamepad2 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad();

    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initializing");
        telemetry.update();

        MecDrive.init(hardwareMap);
        ServoIntake.init(hardwareMap);
        ServoClaw.init(hardwareMap);
        Lift.init(hardwareMap);
        ServoArm.init(hardwareMap);

        Vision.init(hardwareMap);
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

            //Use Webcam to find out Signal using April Tags and save in currentSignal
            Vision.CheckForAprilTags(this);

            // User sets starting location left or right, and confirms selection with a button press
            // LEFT is a multiplier of 1, RIGHT is a multiplier of -1
            BConfig.ConfigureStartingPosition(currentGamepad1.dpad_left, previousGamepad1.dpad_left,
                    currentGamepad1.dpad_right, previousGamepad1.dpad_right,
                    currentGamepad1.b, previousGamepad1.b);

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

            sleep(20);
        }

        runtime.reset();
        Gyro.init(hardwareMap);

        //Use this code to just test if we can score by strafing into a pole

        telemetry.addData("Signal is ", Signal);
        telemetry.addData("Selected Starting Position ", ButtonConfig.currentStartPosition);
        telemetry.update();

        Lift.StartLifting(MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL);
        while (opModeIsActive() && (Lift.alreadyLifting)) {
            Lift.ContinueLifting();
        }

        if ((ButtonConfig.currentStartPosition == ButtonConfig.StartingPosition.RIGHT_SIDE)) {
            ServoArm.setPosition(ARM_RIGHT_OUTTAKE);
        } else ServoArm.setPosition(ARM_LEFT_OUTTAKE);

        //Strafe close to High Pole
        Lift.StartLifting(HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL);
        MecDrive.startStrafeDrive(LOW_SPEED, -(QUARTER_TILE_DISTANCE + SIXTEENTH_TILE_DISTANCE+THIRTYSECOND_TILE_DISTANCE) * ButtonConfig.startPositionMultiplier);
        while (opModeIsActive() && MecDrive.alreadyStrafing == true) {
            MecDrive.ContinueStrafing();
        }

        //If on the right side, need to back off just a little
        /*
        if (ButtonConfig.currentStartPosition == ButtonConfig.StartingPosition.RIGHT_SIDE) {
            MecDrive.startStrafeDrive(LOW_SPEED, (SIXTEENTH_TILE_DISTANCE) * ButtonConfig.startPositionMultiplier);
            while (opModeIsActive() && MecDrive.alreadyStrafing == true) {
                MecDrive.ContinueStrafing();
            }
        }
        */

        sleep(200);
        //Open claw to drop cone
        ServoClaw.toggleClaw();
        sleep(400);

            //Strafe away from High Pole
            MecDrive.startStrafeDrive(LOW_SPEED, QUARTER_TILE_DISTANCE * ButtonConfig.startPositionMultiplier);
            while (opModeIsActive() && MecDrive.alreadyStrafing == true) {
                MecDrive.ContinueStrafing();
            }

            //close the claw
            ServoClaw.toggleClaw();
            ServoArm.setPosition(ARM_CENTER_INTAKE);
            Lift.StartLifting(FIVE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL);

      }
    }



