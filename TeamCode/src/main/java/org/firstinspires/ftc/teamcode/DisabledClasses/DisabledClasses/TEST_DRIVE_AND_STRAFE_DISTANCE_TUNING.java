package org.firstinspires.ftc.teamcode.DisabledClasses.DisabledClasses;

import static org.firstinspires.ftc.teamcode.ObjectClasses.DriveTrain.LOW_SPEED;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FULL_TILE_DISTANCE_STRAFE;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
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

@Disabled
@TeleOp(name = "TEST_DRIVE_AND_STRAFE_DISTANCE_TUNING")
public class TEST_DRIVE_AND_STRAFE_DISTANCE_TUNING extends LinearOpMode {

    int Signal;
    DriveTrain MecDrive = new DriveTrain(this);
    ButtonConfig BConfig = new ButtonConfig(this);


    AprilTagVision Vision = new AprilTagVision();
    Claw ServoClaw = new Claw();
    Intake ServoIntake = new Intake(ServoClaw, this);
    Lift Lift = new Lift(this);
    Arm ServoArm = new Arm(Lift, ServoIntake, ServoClaw, this);
    Gyro Gyro = new Gyro(this);

    double test_distance = FULL_TILE_DISTANCE_STRAFE;
    double test_speed = LOW_SPEED;

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
        Gyro.init(hardwareMap);
        Vision.init(hardwareMap);
        BConfig.init();

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        sleep(1000);

        while (!isStarted()) {
            telemetry.addLine("This program is just for tuning driving and strafing distance accuracy");
            telemetry.update();
            sleep(20);
        }

        runtime.reset();
        Gyro.init(hardwareMap);

        while (opModeIsActive()) {

            //Store the previous loop's gamepad values.
            previousGamepad1 = BConfig.copy(currentGamepad1);
            previousGamepad2 = BConfig.copy(currentGamepad2);

            //Store the gamepad values to be used for this iteration of the loop.
            currentGamepad1 = BConfig.copy(gamepad1);
            currentGamepad2 = BConfig.copy(gamepad2);

            //Change speed by .05 power increments
            if (currentGamepad1.dpad_up && !previousGamepad1.dpad_up){
                test_speed += .05;
                if (test_speed >= 1) test_speed =1;
            }

            if (currentGamepad1.dpad_down && !previousGamepad1.dpad_down){
                test_speed -= .05;
                if (test_speed <= 0) test_speed =0;
            }

            //Change distance in .25 inch increments
            if (currentGamepad1.dpad_right && !previousGamepad1.dpad_right){
                test_distance += .25;
                if (test_distance >= 50) test_distance =50;
            }

            if (currentGamepad1.dpad_left && !previousGamepad1.dpad_left){
                test_distance -= .25;
                if (test_distance <= 5) test_distance = 5;
            }

            if (currentGamepad1.y && !previousGamepad1.y) {
                //Use this code to test driving a tile distance
                //Might want to test using ORs instead of ANDs for the encoder finish distance
                MecDrive.startEncoderDrive(test_speed, test_distance);
            }

            if (currentGamepad1.a && !previousGamepad1.a) {
                //Use this code to test driving a tile distance
                //Might want to test using ORs instead of ANDs for the encoder finish distance
                MecDrive.startEncoderDrive(test_speed, -test_distance);
            }

            if (currentGamepad1.b && !previousGamepad1.b) {
                //Use this code to test strafing a tile distance
                //Might want to test using ORs instead of ANDs for the encoder finish distance
                MecDrive.startStrafeDrive(test_speed, test_distance);
            }


            if (currentGamepad1.x && !previousGamepad1.x) {
                //Use this code to test strafing a tile distance
                //Might want to test using ORs instead of ANDs for the encoder finish distance
                MecDrive.startStrafeDrive(test_speed, -test_distance);
            }

            //Driver manual controls - if any of these are non-zero, all automatic tasks are halted
            MecDrive.CheckManualDriveControls(  currentGamepad1.left_stick_y, currentGamepad1.left_stick_x, currentGamepad1.right_stick_x,
                    currentGamepad1.left_trigger, currentGamepad1.right_trigger);


            //Automated tasks (driving, turning, strafing, vision strafing, auto deliver)
            MecDrive.ContinueAutomaticTasks(Gyro, ServoArm, Lift, ServoClaw, ServoIntake);

            telemetry.addLine("Use D-Pad to change test speed (left/right) and test distance (up/down)");
            telemetry.addLine("Press Y/A button to drive forward/back test_distance at test_speed");
            telemetry.addLine("Press X/B button to strafe left/right test_distance at test_speed");
            telemetry.addData("Test Distance", test_distance);
            telemetry.addData("Test Speed", test_speed);

            telemetry.addData("Encoders", "LF(%s), RF(%s)", MecDrive.LFDrive.getCurrentPosition(), MecDrive.RFDrive.getCurrentPosition());
            telemetry.addData("Encoders", "LB(%s), RB(%s)", MecDrive.LBDrive.getCurrentPosition(), MecDrive.RBDrive.getCurrentPosition());

            telemetry.update();
        }
      }
    }



