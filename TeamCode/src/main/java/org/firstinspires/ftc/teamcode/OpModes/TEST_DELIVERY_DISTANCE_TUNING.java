package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.ARM_CENTER_INTAKE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.ARM_LEFT_OUTTAKE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.ARM_RIGHT_OUTTAKE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.HALF_TILE_DISTANCE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.ONE_CONE_INTAKE_HEIGHT_ENC_VAL;

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


@Autonomous(name = "TEST_DELIVERY_DISTANCE_TUNING")
public class TEST_DELIVERY_DISTANCE_TUNING extends LinearOpMode {

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


    int delay_before_claw_open = 200;
    int delay_after_claw_open = 200;

    Gamepad currentGamepad1 = new Gamepad();
    Gamepad previousGamepad1 = new Gamepad();
    Gamepad currentGamepad2 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad();

    double test_stafe_into_pole_distance = HALF_TILE_DISTANCE;
    double test_speed;
    int delivery_side = 1; //1 is right side, -1 is left side

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
            telemetry.addLine("This program is to tune cone delivery");
            telemetry.update();
        }

        ServoClaw.toggleClaw(); // open the claw to start
        runtime.reset();
        Gyro.init(hardwareMap);

        //Use this code to tune the speed and distance to deliver a cone onto a pole
        while (opModeIsActive()) {

            //Store the previous loop's gamepad values.
            previousGamepad1 = BConfig.copy(currentGamepad1);
            previousGamepad2 = BConfig.copy(currentGamepad2);

            //Store the gamepad values to be used for this iteration of the loop.
            currentGamepad1 = BConfig.copy(gamepad1);
            currentGamepad2 = BConfig.copy(gamepad2);

            //Change speed by .05 power increments
            if (currentGamepad1.dpad_up && !previousGamepad1.dpad_up) {
                test_speed += .05;
                if (test_speed >= 1) test_speed = 1;
            }

            if (currentGamepad1.dpad_down && !previousGamepad1.dpad_down) {
                test_speed -= .05;
                if (test_speed <= 0) test_speed = 0;
            }

            //Change distance in .25 inch increments
            if (currentGamepad1.dpad_right && !previousGamepad1.dpad_right) {
                test_stafe_into_pole_distance += .25;
                if (test_stafe_into_pole_distance >= 30) test_stafe_into_pole_distance = 30;
            }

            if (currentGamepad1.dpad_left && !previousGamepad1.dpad_left) {
                test_stafe_into_pole_distance -= .25;
                if (test_stafe_into_pole_distance <= 5) test_stafe_into_pole_distance = 5;
            }

            //Change delivery side with left stick
            if (currentGamepad1.left_stick_x > .1 && previousGamepad1.left_stick_x < .1) {
                //deliver to the left side
                delivery_side = -1;
            }

            if (currentGamepad1.left_stick_x < -.1 && previousGamepad1.left_stick_x > -.1) {
                //deliver to the right side
                delivery_side = 1;
            }

            //Change delay before opening claw with right stick (L/R)
            if (currentGamepad1.right_stick_x > .1 && previousGamepad1.right_stick_x < .1) {
                //deliver to the left side
                delay_before_claw_open += 50;
                if (delay_before_claw_open >= 1000) delay_before_claw_open = 1000;

            }

            if (currentGamepad1.right_stick_x < -.1 && previousGamepad1.right_stick_x > -.1) {
                //deliver to the right side
                delay_before_claw_open -= 50;
                if (delay_before_claw_open <= 50) delay_before_claw_open = 50;
            }

            //Change delay after opening claw with right stick (L/R)
            if (currentGamepad1.right_stick_y > .1 && previousGamepad1.right_stick_y < .1) {
                //deliver to the left side
                delay_after_claw_open += 50;
                if (delay_after_claw_open >= 1000) delay_after_claw_open = 1000;

            }

            if (currentGamepad1.right_stick_y < -.1 && previousGamepad1.right_stick_y > -.1) {
                //deliver to the right side
                delay_after_claw_open -= 50;
                if (delay_after_claw_open <= 50) delay_after_claw_open = 50;
            }

            //reset to try again
            //close claw on cone, raise lift to a starting height
            if (currentGamepad1.x && !previousGamepad1.x) {
                ServoClaw.toggleClaw();
                Lift.StartLifting(MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL);
            }

            if (currentGamepad1.a && !previousGamepad1.a) {
                //1 = RIGHT SIDE
                //-1 = LEFT SIDE
                Deliver_and_Return(delivery_side);
            }

            telemetry.addLine("Use D-Pad to change test speed (up/down) and test strafe distance into pole (left/right)");
            telemetry.addLine("Change delivery side with left stick (left/right)");
            telemetry.addLine("Change delay before opening claw (left/right) and delay after opening claw (up/down) in milliseconds");
            telemetry.addLine("Press X button to close claw and raise lift to safe driving height");
            telemetry.addLine("Press A button to strafe test_distance into pole and drop off cone at test_speed");
            telemetry.addData("Test Strafe Distance", test_stafe_into_pole_distance);
            telemetry.addData("Test Speed", test_speed);
            telemetry.addData("Side: 1 is right, -1 is left", delivery_side);
            telemetry.addData("Delay before opening claw", delay_before_claw_open);
            telemetry.addData("Delay after opening claw", delay_after_claw_open);
            telemetry.update();

        }
    }

    public void Deliver_and_Return(int side) {
        if (side==1) {
            ServoArm.setPosition(ARM_RIGHT_OUTTAKE);
        } else ServoArm.setPosition(ARM_LEFT_OUTTAKE);

        //Strafe close to High Pole
        Lift.StartLifting(HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL);
        MecDrive.startStrafeDrive(test_speed, test_stafe_into_pole_distance*side);
        while (opModeIsActive() && MecDrive.alreadyStrafing == true) {
            MecDrive.ContinueStrafing();
        }

        sleep(delay_before_claw_open);
        //Open claw to drop cone
        ServoClaw.toggleClaw();
        sleep(delay_after_claw_open);

        //Strafe away from High Pole
        MecDrive.startStrafeDrive(test_speed, -test_stafe_into_pole_distance*side);
        while (opModeIsActive() && MecDrive.alreadyStrafing == true) {
            MecDrive.ContinueStrafing();
        }

        ServoArm.setPosition(ARM_CENTER_INTAKE);
        Lift.StartLifting(ONE_CONE_INTAKE_HEIGHT_ENC_VAL);
    }
}



