package org.firstinspires.ftc.teamcode.ObjectClasses;

import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.armState.ARM_CENTER;
import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.armState.ARM_LEFT;
import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.armState.ARM_RIGHT;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FIVE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FULL_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.HALF_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.QUARTER_TILE_DISTANCE_DRIVE;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

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
    public TrajectorySequence trajSeq3;


    public PowerplayTrajectories(SampleMecanumDrive drive, Lift lift, Claw claw, Intake intake, Arm arm) {
        MecDrive = drive;
        Lift = lift;
        Claw = claw;
        Intake = intake;
        Arm = arm;
    }

    public static Vector2d MEDIUM_JUNCTION_Y4 = new Vector2d(FULL_TILE_DISTANCE_DRIVE, -FULL_TILE_DISTANCE_DRIVE);
    public static Vector2d MEDIUM_JUNCTION_Y2 = new Vector2d(-FULL_TILE_DISTANCE_DRIVE, -FULL_TILE_DISTANCE_DRIVE);

    public static Vector2d LOW_JUNCTION_Y5 = new Vector2d(47.2, -23.6);
    public static Vector2d LOW_JUNCTION_Y1 = new Vector2d(-47.2, -23.6);

    public static Vector2d HIGH_JUNCTION_X4 = new Vector2d(FULL_TILE_DISTANCE_DRIVE, 0);
    public static Vector2d HIGH_JUNCTION_X2 = new Vector2d(-FULL_TILE_DISTANCE_DRIVE, 0);

    public static Pose2d RIGHT_SIDE_LEFT_TILE_D4 = new Pose2d(HALF_TILE_DISTANCE_DRIVE, -HALF_TILE_DISTANCE_DRIVE,Math.toRadians(90));
    public static Pose2d RIGHT_SIDE_MIDDLE_TILE_D5 = new Pose2d(HALF_TILE_DISTANCE_DRIVE+FULL_TILE_DISTANCE_DRIVE, -HALF_TILE_DISTANCE_DRIVE,Math.toRadians(90));
    public static Pose2d RIGHT_SIDE_RIGHT_TILE_D6 = new Pose2d(HALF_TILE_DISTANCE_DRIVE+(FULL_TILE_DISTANCE_DRIVE*2), -HALF_TILE_DISTANCE_DRIVE,Math.toRadians(180));

    public static Pose2d LEFT_SIDE_LEFT_TILE_D1 = new Pose2d(-(HALF_TILE_DISTANCE_DRIVE+(FULL_TILE_DISTANCE_DRIVE*2)), -HALF_TILE_DISTANCE_DRIVE,Math.toRadians(180));
    public static Pose2d LEFT_SIDE_MIDDLE_TILE_D2 = new Pose2d( -(HALF_TILE_DISTANCE_DRIVE+FULL_TILE_DISTANCE_DRIVE), -HALF_TILE_DISTANCE_DRIVE,Math.toRadians(90));
    public static Pose2d LEFT_SIDE_RIGHT_TILE_D3 =  new Pose2d( -HALF_TILE_DISTANCE_DRIVE, -HALF_TILE_DISTANCE_DRIVE,Math.toRadians(90));


    public static Vector2d RIGHT_CONE_STACK_RIGHT = new Vector2d(68, -12);
    public static Vector2d LEFT_CONE_STACK_LEFT = new Vector2d(-68, -12);
    public static Vector2d RIGHT_CONE_STACK_END_OF_LINE = new Vector2d(47.2, -12);
    public static Vector2d LEFT_CONE_STACK_END_OF_LINE = new Vector2d(-47.2, -12);
    public static Pose2d RIGHT_CONE_STACK_LINE = new Pose2d(45, -12, Math.toRadians(180));
    public static Pose2d LEFT_CONE_STACK_LINE = new Pose2d(-45, -12, Math.toRadians(0));

    public static Pose2d startPose;
    public static Pose2d currentPose;
    public static Vector2d coneStack;
    public static Vector2d coneStackEndOfLine;
    public static Pose2d coneStackLine;
    public static Vector2d firstJunction;
    public static double firstJunctionHeight;
    public static Arm.armState firstJunctionArm;
    public static Vector2d secondJunction;
    public static double secondJunctionHeight;
    public static Vector2d thirdJunction;
    public static double thirdJunctionHeight;
    public static Vector2d fourthJunction;
    public static double fourthJunctionHeight;
    public static Vector2d fifthJunction;
    public static double fifthJunctionHeight;
    public static Vector2d sixthJunction;
    public static double sixthJunctionHeight;
    public static Boolean startPosRIGHT = true;
    public static Pose2d endAutoPosition;

    public static int numberSignal = 1;

    public void MakeTrajectories() {

        if (startPosRIGHT) {
            startPose = new Pose2d(38, -60.3, Math.toRadians(90));
            coneStackLine = RIGHT_CONE_STACK_LINE;
            coneStack = RIGHT_CONE_STACK_RIGHT;
            coneStackEndOfLine = RIGHT_CONE_STACK_END_OF_LINE;
            firstJunction = MEDIUM_JUNCTION_Y4;
            firstJunctionArm = ARM_LEFT;
            secondJunction = LOW_JUNCTION_Y5;
            thirdJunction = LOW_JUNCTION_Y5;
            fourthJunction = LOW_JUNCTION_Y5;
            fifthJunction = HIGH_JUNCTION_X4;

            if (numberSignal == 1)
            {
                endAutoPosition = RIGHT_SIDE_LEFT_TILE_D4;
            } else if (numberSignal == 2)
            {
                endAutoPosition = RIGHT_SIDE_MIDDLE_TILE_D5;
            } else if (numberSignal ==3)
            {
                endAutoPosition = RIGHT_SIDE_RIGHT_TILE_D6;
            }

        } else
        {
            startPose = new Pose2d(-38, -60.3, Math.toRadians(90));
            coneStackLine = LEFT_CONE_STACK_LINE;
            coneStack = LEFT_CONE_STACK_LEFT;
            coneStackEndOfLine = LEFT_CONE_STACK_END_OF_LINE;
            firstJunction = MEDIUM_JUNCTION_Y2;
            firstJunctionArm = ARM_RIGHT;
            secondJunction = LOW_JUNCTION_Y1;
            thirdJunction = LOW_JUNCTION_Y1;
            fourthJunction = LOW_JUNCTION_Y1;
            fifthJunction = HIGH_JUNCTION_X2;

            if (numberSignal == 1)
            {
                endAutoPosition = LEFT_SIDE_LEFT_TILE_D1;
            } else if (numberSignal == 2)
            {
                endAutoPosition = LEFT_SIDE_MIDDLE_TILE_D2;
            } else if (numberSignal ==3)
            {
                endAutoPosition = LEFT_SIDE_RIGHT_TILE_D3;
            }
        }


        trajSeq1 = MecDrive.trajectorySequenceBuilder(startPose)
                .forward(FULL_TILE_DISTANCE_DRIVE+HALF_TILE_DISTANCE_DRIVE)
                .build();

        //First Sequence drives to the Medium Junction, delivers cone, and drives to cone stack line
        trajSeq3 = MecDrive.trajectorySequenceBuilder(startPose)
                .forward(FULL_TILE_DISTANCE_DRIVE+HALF_TILE_DISTANCE_DRIVE)
                .strafeTo(firstJunction)
                .waitSeconds(.200)
                .lineToSplineHeading(coneStackLine)
                .addTemporalMarker(.1, () -> {
                    Lift.StartLifting(firstJunctionHeight, Arm);
                })
                .addTemporalMarker(1.2, () -> {
                    Arm.setArmState(firstJunctionArm, null);
                })
                .addTemporalMarker(2.9, () -> {
                    Lift.StartLifting(firstJunctionHeight - 300, Arm);
                })
                .addTemporalMarker(3.0, () -> {
                    Claw.openClaw();
                })
                .addTemporalMarker(3.2, () -> {
                    Lift.StartLifting(firstJunctionHeight, Arm);
                })
                .addTemporalMarker(5, () -> {
                    Arm.setArmState(ARM_CENTER, null);
                    Lift.StartLifting(FIVE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL, Arm);
                    Claw.setEasyIntake();
                    Intake.turnIntakeOn();
                })
                .addTemporalMarker(7.1, () -> {
                    //Intake.turnIntakeOff();
                    Claw.closeClaw();
                    Lift.StartLifting(FIVE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL+200, Arm);
                })
                .addTemporalMarker(8.6, () -> {
                    Lift.StartLifting(secondJunctionHeight, Arm);
                    Arm.setArmState(ARM_LEFT, secondJunctionHeight);
                })
                .addTemporalMarker(9.6, () -> {
                    Lift.StartLifting(firstJunctionHeight - 300, Arm);
                })
                .addTemporalMarker(9.7, () -> {
                    Claw.openClaw();
                })
                .build();

        trajSeq2 = MecDrive.trajectorySequenceBuilder(coneStackLine)
                .addDisplacementMarker(() -> {
                    //Intake.turnIntakeOn();
                    Claw.setEasyIntake();
                    Arm.setArmState(ARM_CENTER, FIVE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL);
                    Lift.StartLifting(FIVE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL, Arm);
                })
                .lineTo(coneStack)
                .waitSeconds(.200)
                .splineToConstantHeading(secondJunction, Math.toRadians(270))
                .waitSeconds(.200)
                .splineToConstantHeading(coneStack, Math.toRadians(0))
                .waitSeconds(.200)
                .splineToConstantHeading(thirdJunction, Math.toRadians(270))
                .waitSeconds(.200)
                .splineToConstantHeading(coneStack, Math.toRadians(0))
                .waitSeconds(.200)
                .splineToConstantHeading(fourthJunction, Math.toRadians(270))
                .waitSeconds(.200)
                .splineToConstantHeading(coneStack, Math.toRadians(0))
                .waitSeconds(.200)
                .splineToSplineHeading(new Pose2d(fifthJunction, Math.toRadians(135)), Math.toRadians(135))
                .back(15)
                .lineToLinearHeading(endAutoPosition)
                .build();

    }
}

