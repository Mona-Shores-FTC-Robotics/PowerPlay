package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.CONE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FIVE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FOUR_CONE_STACK_INTAKE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FULL_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.LOW_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.QUARTER_TILE_DISTANCE_DRIVE;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.ObjectClasses.AprilTagVision;
import org.firstinspires.ftc.teamcode.ObjectClasses.Arm;
import org.firstinspires.ftc.teamcode.ObjectClasses.ButtonConfig;
import org.firstinspires.ftc.teamcode.ObjectClasses.Claw;
import org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants;
import org.firstinspires.ftc.teamcode.ObjectClasses.Intake;
import org.firstinspires.ftc.teamcode.ObjectClasses.Lift;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;


@Autonomous(name = "MEEPMEEP")
public class MEEPMEEPTEST extends LinearOpMode {


    AprilTagVision Vision = new AprilTagVision();
    ButtonConfig BConfig = new ButtonConfig(this);
    Claw ServoClaw = new Claw();
    Intake ServoIntake = new Intake(ServoClaw, this);
    Lift Lift = new Lift(this);
    Arm ServoArm = new Arm(Lift, ServoIntake, ServoClaw, this);

    Gamepad currentGamepad1 = new Gamepad();
    Gamepad previousGamepad1 = new Gamepad();
    Gamepad currentGamepad2 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad();

    @Override
    public void runOpMode() {
        SampleMecanumDrive MecDrive = new SampleMecanumDrive(hardwareMap);
        telemetry.addData("Status", "Initializing");
        telemetry.update();

        Vision.init(hardwareMap);
        Lift.init(hardwareMap);
        ServoIntake.init(hardwareMap);
        ServoClaw.init(hardwareMap);
        ServoArm.init(hardwareMap);
        BConfig.init();

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        sleep(500);

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

            sleep(20);
        }
        Vision.currentSignal = AprilTagVision.Signal.LEFT;
        telemetry.addData("Signal is ", Vision.currentSignal);
        telemetry.addData("Selected Starting Position ", ButtonConfig.currentStartPosition);
        telemetry.addData("Status", "Run Time: " + getRuntime());
        telemetry.update();

        Pose2d startPose = new Pose2d(38, -60.3, Math.toRadians(90));
        MecDrive.setPoseEstimate(startPose);

        TrajectorySequence trajSeq = MecDrive.trajectorySequenceBuilder(startPose)
                .lineTo(new Vector2d(37.4, -38.8))
                .splineToConstantHeading(new Vector2d(24.5, -24.5), Math.toRadians(90))
                .waitSeconds(4)
                .lineToSplineHeading(new Pose2d(45, -12, Math.toRadians(180)))
                .addTemporalMarker(.1, () -> {
                                    Lift.StartLifting(GameConstants.MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL, ServoArm);
                                })
                                .addTemporalMarker(1, () ->{
                                    ServoArm.setArmState(Arm.armState.ARM_LEFT, null);
                                })
                                .addTemporalMarker(2.4, () ->{
                                    Lift.StartLifting(MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL-300, ServoArm);
                                })
                                .addTemporalMarker(2.8, () ->{
                                    ServoClaw.openClaw();
                                })
                                .addTemporalMarker(3, () ->{
                                    Lift.StartLifting(MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL, ServoArm);
                                })
                                .addTemporalMarker(6, () ->{
                                    ServoArm.setArmState(Arm.armState.ARM_CENTER, null);
                                })
                                .build();
        MecDrive.followTrajectorySequence(trajSeq);

    }
}




