package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.CONE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FULL_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FULL_TILE_DISTANCE_STRAFE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.HALF_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.QUARTER_TILE_DISTANCE_DRIVE;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.ObjectClasses.AprilTagVision;
import org.firstinspires.ftc.teamcode.ObjectClasses.Arm;
import org.firstinspires.ftc.teamcode.ObjectClasses.ButtonConfig;
import org.firstinspires.ftc.teamcode.ObjectClasses.Claw;
import org.firstinspires.ftc.teamcode.ObjectClasses.Intake;
import org.firstinspires.ftc.teamcode.ObjectClasses.Lift;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;


@Autonomous(name = "AUTO_JUST_PARK")
public class AUTO_JUST_PARK extends LinearOpMode {


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

            sleep(20);
        }
        Vision.currentSignal = AprilTagVision.Signal.LEFT;
        telemetry.addData("Signal is ", Vision.currentSignal);
        telemetry.addData("Selected Starting Position ", ButtonConfig.currentStartPosition);
        telemetry.addData("Status", "Run Time: " + getRuntime());
        telemetry.update();

        Pose2d startPose = new Pose2d(10,-8, Math.toRadians(90));
        MecDrive.setPoseEstimate(new Pose2d());

        Trajectory SignalOne  = MecDrive.trajectoryBuilder(startPose)
                .forward(FULL_TILE_DISTANCE_DRIVE*2+QUARTER_TILE_DISTANCE_DRIVE)
                .build();


        //Drive forward 2 tiles plus a little bit more to get into position for deciding where to park
        Lift.StartLifting(CONE_HEIGHT_ENC_VAL, ServoArm);


        //if current Signal is the LEFT april tag then park on robot's left
        if (Vision.currentSignal == AprilTagVision.Signal.LEFT) {
            //Park on left
            MecDrive.followTrajectory(SignalOne);
        }

        //if current Signal is the MIDDLE april tag then park in middle
        else if (Vision.currentSignal == AprilTagVision.Signal.MIDDLE) {
            //Park in middle
            }

        //if current Signal is the RIGHT april tag then park on robot's right
        else if (Vision.currentSignal == AprilTagVision.Signal.RIGHT) {
            //Park on right
            MecDrive.followTrajectory(SignalOne);
            }

            telemetry.addData("Status", "Run Time: " + getRuntime());
            telemetry.update();
            }
        }




