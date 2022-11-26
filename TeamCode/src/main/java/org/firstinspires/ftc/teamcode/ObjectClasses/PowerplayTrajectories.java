package org.firstinspires.ftc.teamcode.ObjectClasses;

import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.armState.ARM_CENTER;
import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.armState.ARM_LEFT;
import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.armState.ARM_RIGHT;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.HALF_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.QUARTER_TILE_DISTANCE_DRIVE;

import android.widget.Button;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

public class PowerplayTrajectories {

    SampleMecanumDrive MecDrive;
    Claw Claw;
    Intake Intake;
    Arm Arm;
    Lift Lift;
    public TrajectorySequence trajSeq1;
    public TrajectorySequence trajSeq2;

    public PowerplayTrajectories(SampleMecanumDrive drive, Lift lift, Claw claw, Intake intake, Arm arm) {
        MecDrive = drive;
        Lift = lift;
        Claw = claw;
        Intake = intake;
        Arm = arm;
    }

    public Vector2d MEDIUM_JUNCTION_Y4 = new Vector2d(-24.5, -24.5);
    public Vector2d MEDIUM_JUNCTION_W2 = new Vector2d(24.5, -24.5);
    public Pose2d CONE_STACK_RIGHT = new Pose2d(45, -12, Math.toRadians(180));
    public Pose2d CONE_STACK_LEFT = new Pose2d(-45, -12, Math.toRadians(0));

    public Pose2d startPose;
    public Pose2d coneStack;
    public Vector2d firstJunction;
    public Arm.armState firstJunctionArm;
    public double firstJunctionHeight;
    public Vector2d secondJunction;
    public Arm.armState secondJunctionArm;
    public double secondJunctionHeight;
    public Vector2d thirdJunction;
    public Arm.armState thirdJunctionArm;
    public double thirdJunctionHeight;
    public Vector2d fourthJunction;
    public Arm.armState fourthJunctionArm;
    public double fourthJunctionHeight;
    public Vector2d fifthJunction;
    public Arm.armState fifthJunctionArm;
    public double fifthJunctionHeight;
    public Vector2d sixthJunction;
    public Arm.armState sixthJunctionArm;
    public double sixthJunctionHeight;

    public void MakeTrajectories() {

        if (ButtonConfig.currentStartPosition == ButtonConfig.StartingPosition.RIGHT_SIDE) {
            startPose = new Pose2d(38, -60.3, Math.toRadians(90));
            coneStack = CONE_STACK_RIGHT;
            firstJunction = MEDIUM_JUNCTION_W2;
            firstJunctionArm = ARM_LEFT;
            firstJunctionHeight = MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;

        } else
        {
            startPose = new Pose2d(-38, -60.3, Math.toRadians(90));
            coneStack = CONE_STACK_LEFT;
            firstJunction = MEDIUM_JUNCTION_Y4;
            firstJunctionArm = ARM_RIGHT;
            firstJunctionHeight = MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
        }

        //First Sequence drives to the Medium Junction, delivers cone, and drives to cone stack line
        trajSeq1 = MecDrive.trajectorySequenceBuilder(startPose)
                .forward(HALF_TILE_DISTANCE_DRIVE+QUARTER_TILE_DISTANCE_DRIVE)
                .splineToConstantHeading(firstJunction, Math.toRadians(90))
                .waitSeconds(1)
                .lineToSplineHeading(coneStack)
                .addTemporalMarker(.1, () -> {
                    Lift.StartLifting(firstJunctionHeight, Arm);
                })
                .addTemporalMarker(1, () -> {
                    Arm.setArmState(firstJunctionArm, null);
                })
                .addTemporalMarker(2.4, () -> {
                    Lift.StartLifting(firstJunctionHeight - 300, Arm);
                })
                .addTemporalMarker(2.8, () -> {
                    Claw.openClaw();
                })
                .addTemporalMarker(3, () -> {
                    Lift.StartLifting(firstJunctionHeight, Arm);
                })
                .addTemporalMarker(4, () -> {
                    Arm.setArmState(ARM_CENTER, null);
                })
                .build();
    }
}
