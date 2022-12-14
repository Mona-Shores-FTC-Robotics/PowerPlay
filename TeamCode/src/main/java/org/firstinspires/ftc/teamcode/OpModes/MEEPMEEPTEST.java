package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.ObjectClasses.AprilTagVision;
import org.firstinspires.ftc.teamcode.ObjectClasses.Arm;
import org.firstinspires.ftc.teamcode.ObjectClasses.ButtonConfig;
import org.firstinspires.ftc.teamcode.ObjectClasses.Claw;
import org.firstinspires.ftc.teamcode.ObjectClasses.Intake;
import org.firstinspires.ftc.teamcode.ObjectClasses.Lift;
import org.firstinspires.ftc.teamcode.ObjectClasses.PowerplayTrajectories;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;


@Autonomous(name = "AUTO_SCORE_5_AND_PARK - RUN ME")
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
        PowerplayTrajectories PowerTraj = new PowerplayTrajectories(MecDrive, Lift, ServoClaw, ServoIntake, ServoArm, Vision);
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
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
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
        resetRuntime();

        telemetry.addData("Signal is ", Vision.currentSignal);
        telemetry.addData("Selected Starting Position ", ButtonConfig.currentStartPosition);
        telemetry.addData("Status", "Run Time: " + getRuntime());
        telemetry.update();

        PowerTraj.MakeTrajectoriesStart();
        MecDrive.setPoseEstimate(PowerplayTrajectories.startPose);

        //first cone
        MecDrive.followTrajectorySequence(PowerTraj.trajSeqSixMedStart);
        MecDrive.findLine(MecDrive);
        PowerTraj.MakeTrajectories1();

        //second cone
        MecDrive.followTrajectorySequence(PowerTraj.trajSeqSixMed1);
        MecDrive.findLine(MecDrive);
        PowerTraj.MakeTrajectories2();
//        //third cone
        MecDrive.followTrajectorySequence(PowerTraj.trajSeqSixMed2);
        MecDrive.findLine(MecDrive);
        PowerTraj.MakeTrajectories3();
//        //fourth cone
        MecDrive.followTrajectorySequence(PowerTraj.trajSeqSixMed3);
        MecDrive.findLine(MecDrive);
        //PowerTraj.MakeTrajectories4();
//        //fifth cone
//        MecDrive.followTrajectorySequence(PowerTraj.trajSeqSixMedRepeat);
//        MecDrive.findLine(MecDrive);
        PowerTraj.MakeTrajectoriesPark();
        //final cone and park
        MecDrive.followTrajectorySequence(PowerTraj.trajSeqSixMedPark);

        telemetry.addData("Signal is ", Vision.currentSignal);
        telemetry.addData("Selected Starting Position ", ButtonConfig.currentStartPosition);
        telemetry.addData("Status", "Run Time: " + getRuntime());
        telemetry.update();
    }
}




