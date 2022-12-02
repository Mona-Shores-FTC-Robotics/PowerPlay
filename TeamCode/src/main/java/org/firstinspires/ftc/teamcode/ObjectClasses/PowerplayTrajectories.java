package org.firstinspires.ftc.teamcode.ObjectClasses;

import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.armState.ARM_CENTER;
import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.armState.ARM_FRONT;
import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.armState.ARM_LEFT;
import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.armState.ARM_RIGHT;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FIVE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FOUR_CONE_STACK_INTAKE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FULL_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.HALF_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.LOW_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.ONE_CONE_INTAKE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.QUARTER_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.THREE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.TWO_CONE_STACK_INTAKE_HEIGHT_ENC_VAL;

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

    public static Vector2d HIGH_JUNCTION_X4 = new Vector2d(FULL_TILE_DISTANCE_DRIVE, -3);
    public static Vector2d HIGH_JUNCTION_X2 = new Vector2d(-FULL_TILE_DISTANCE_DRIVE, -3);

    public static Pose2d RIGHT_SIDE_LEFT_TILE_D4 = new Pose2d(HALF_TILE_DISTANCE_DRIVE, -HALF_TILE_DISTANCE_DRIVE,Math.toRadians(90));
    public static Pose2d RIGHT_SIDE_MIDDLE_TILE_D5 = new Pose2d(HALF_TILE_DISTANCE_DRIVE+FULL_TILE_DISTANCE_DRIVE, -HALF_TILE_DISTANCE_DRIVE,Math.toRadians(90));
    public static Pose2d RIGHT_SIDE_RIGHT_TILE_D6 = new Pose2d(HALF_TILE_DISTANCE_DRIVE+(FULL_TILE_DISTANCE_DRIVE*2), -HALF_TILE_DISTANCE_DRIVE,Math.toRadians(180));

    public static Pose2d LEFT_SIDE_LEFT_TILE_D1 = new Pose2d(-(HALF_TILE_DISTANCE_DRIVE+(FULL_TILE_DISTANCE_DRIVE*2)), -HALF_TILE_DISTANCE_DRIVE,Math.toRadians(180));
    public static Pose2d LEFT_SIDE_MIDDLE_TILE_D2 = new Pose2d( -(HALF_TILE_DISTANCE_DRIVE+FULL_TILE_DISTANCE_DRIVE), -HALF_TILE_DISTANCE_DRIVE,Math.toRadians(90));
    public static Pose2d LEFT_SIDE_RIGHT_TILE_D3 =  new Pose2d( -HALF_TILE_DISTANCE_DRIVE, -HALF_TILE_DISTANCE_DRIVE,Math.toRadians(90));

    public static Pose2d RIGHT_LINEUP_TILE = new Pose2d(FULL_TILE_DISTANCE_DRIVE+HALF_TILE_DISTANCE_DRIVE+QUARTER_TILE_DISTANCE_DRIVE, -12, Math.toRadians(180));
    public static Pose2d LEFT_LINEUP_TILE = new Pose2d(-(FULL_TILE_DISTANCE_DRIVE+HALF_TILE_DISTANCE_DRIVE+QUARTER_TILE_DISTANCE_DRIVE), -12, Math.toRadians(0));

    public static Pose2d coneStackPose;

    public static Vector2d RIGHT_LINEUP_TILE_VECTOR = new Vector2d(FULL_TILE_DISTANCE_DRIVE+HALF_TILE_DISTANCE_DRIVE, -12);


    public static Vector2d RIGHT_CONE_STACK_RIGHT = new Vector2d(60, -12);
    public static Vector2d LEFT_CONE_STACK_LEFT = new Vector2d(-60, -12);
    public static Vector2d RIGHT_CONE_STACK_END_OF_LINE = new Vector2d(47.2, -12);
    public static Vector2d LEFT_CONE_STACK_END_OF_LINE = new Vector2d(-47.2, -12);
    public static Pose2d RIGHT_CONE_STACK_LINE = new Pose2d(45, -12, Math.toRadians(180));
    public static Pose2d LEFT_CONE_STACK_LINE = new Pose2d(-45, -12, Math.toRadians(0));

    public static Pose2d RIGHT_CONE_STACK_POSE = new Pose2d(60, -12,Math.toRadians(180));
    public static Pose2d LEFT_CONE_STACK_POSE = new Pose2d(-60, -12,Math.toRadians(0));

    public static Pose2d startPose;
    public static Pose2d currentPose;
    public static Vector2d coneStack;
    public static Vector2d coneStackEndOfLine;
    public static Pose2d coneStackLine;
    public static Vector2d startingJunction;
    public static double startingJunctionHeight;
    public static double startingJunctionArm;
    public static Vector2d firstJunction;
    public static double firstJunctionHeight;
    public static double firstJunctionArm;
    public static Vector2d secondJunction;
    public static double secondJunctionHeight;
    public static double secondJunctionArm;
    public static Vector2d thirdJunction;
    public static double thirdJunctionHeight;
    public static double thirdJunctionArm;
    public static Vector2d fourthJunction;
    public static double fourthJunctionHeight;
    public static double fourthJunctionArm;
    public static Vector2d fifthJunction;
    public static double fifthJunctionHeight;
    public static double fifthJunctionArm;
    public static Boolean startPosRIGHT = true;
    public static Pose2d endAutoPosition;

    public static int numberSignal = 1;

    // Lift@Deliver
    public static double STARTING_CONE_LIFT_TIME = .1;
    // Rotate Arm
    public static double STARTING_CONE_ARM_TIME = 1.11;
    // Lift@Dunk
    public static double STARTING_CONE_DUNK_TIME = 2.4;
    // Open Claw
    public static double STARTING_CONE_DELIVER_TIME = STARTING_CONE_DUNK_TIME + .1;
    // Lift@Undunk
    public static double STARTING_CONE_UNDUNK_TIME = STARTING_CONE_DELIVER_TIME + .1;


    // Arm Center, Lift@Pickup, Claw Easy, Intake On;
    public static double FIRST_CONE_APPROACH_TIME = 4.2;
    // Intake Off, Close Claw, Lift@+200
    public static double FIRST_CONE_GRAB_TIME = 5.62;
    // Lift@Deliver, Rotate Arm
    public static double FIRST_CONE_DELIVERY_SETUP_TIME = FIRST_CONE_GRAB_TIME + 1.1;
    // Lift@Dunk
    public static double FIRST_CONE_DUNK_TIME = FIRST_CONE_DELIVERY_SETUP_TIME + 1.1;
    // Open Claw
    public static double FIRST_CONE_DELIVER_TIME = FIRST_CONE_DUNK_TIME + .2;
    // Lift@Undunk
    public static double FIRST_CONE_UNDUNK_TIME = FIRST_CONE_DELIVER_TIME + .2;


    public static double SECOND_CONE_APPROACH_TIME = 8.8;
    public static double SECOND_CONE_GRAB_TIME = SECOND_CONE_APPROACH_TIME + 1.5;
    public static double SECOND_CONE_DELIVERY_SETUP_TIME = SECOND_CONE_GRAB_TIME + 1.1;
    public static double SECOND_CONE_DUNK_TIME = SECOND_CONE_DELIVERY_SETUP_TIME + 1.1;
    public static double SECOND_CONE_DELIVER_TIME = SECOND_CONE_DUNK_TIME + .2;
    public static double SECOND_CONE_UNDUNK_TIME = SECOND_CONE_DELIVER_TIME + .2;

    public static double THIRD_CONE_APPROACH_TIME = 12.8;
    public static double THIRD_CONE_GRAB_TIME = THIRD_CONE_APPROACH_TIME + 1.5;
    public static double THIRD_CONE_DELIVERY_SETUP_TIME = THIRD_CONE_GRAB_TIME + 1.1;
    public static double THIRD_CONE_DUNK_TIME = THIRD_CONE_DELIVERY_SETUP_TIME + 1.1;
    public static double THIRD_CONE_DELIVER_TIME = THIRD_CONE_DUNK_TIME + .2;
    public static double THIRD_CONE_UNDUNK_TIME = THIRD_CONE_DELIVER_TIME + .2;

    public static double FOURTH_CONE_APPROACH_TIME = 16.8;
    public static double FOURTH_CONE_GRAB_TIME = FOURTH_CONE_APPROACH_TIME + 1.5;
    public static double FOURTH_CONE_DELIVERY_SETUP_TIME = FOURTH_CONE_GRAB_TIME + 1.1;
    public static double FOURTH_CONE_DUNK_TIME = FOURTH_CONE_DELIVERY_SETUP_TIME + 1.1;
    public static double FOURTH_CONE_DELIVER_TIME = FOURTH_CONE_DUNK_TIME + .2;
    public static double FOURTH_CONE_UNDUNK_TIME = FOURTH_CONE_DELIVER_TIME + .2;

    public static double FIFTH_CONE_APPROACH_TIME = 20.8;
    public static double FIFTH_CONE_GRAB_TIME = FIFTH_CONE_APPROACH_TIME + 1.5;
    public static double FIFTH_CONE_DELIVERY_SETUP_TIME = FIFTH_CONE_GRAB_TIME + 1.1;
    public static double FIFTH_CONE_DUNK_TIME = FIFTH_CONE_DELIVERY_SETUP_TIME + 1.1;
    public static double FIFTH_CONE_DELIVER_TIME = FIFTH_CONE_DUNK_TIME + .2;
    public static double FIFTH_CONE_UNDUNK_TIME = FIFTH_CONE_DELIVER_TIME + .2;

    public static double END_RESET_TIME = 24;
    public static Pose2d lineUpPose;

    public void MakeTrajectories() {

        if (startPosRIGHT) {
            startPose = new Pose2d(35, -60.3, Math.toRadians(90));
            coneStackLine = RIGHT_CONE_STACK_LINE;
            coneStack = RIGHT_CONE_STACK_RIGHT;
            coneStackPose = RIGHT_CONE_STACK_POSE;
            lineUpPose = RIGHT_LINEUP_TILE;
            coneStackEndOfLine = RIGHT_CONE_STACK_END_OF_LINE;
            startingJunction = MEDIUM_JUNCTION_Y4;
            startingJunctionHeight = MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            startingJunctionArm = Arm.ARM_LEFT_OUTTAKE;
            firstJunction = HIGH_JUNCTION_X4;
            firstJunctionHeight = HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            firstJunctionArm = Arm.ARM_RIGHT_OUTTAKE;
            secondJunction = HIGH_JUNCTION_X4;
            secondJunctionHeight = HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            secondJunctionArm = Arm.ARM_RIGHT_OUTTAKE;
            thirdJunction = HIGH_JUNCTION_X4;
            thirdJunctionHeight = HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            thirdJunctionArm = Arm.ARM_RIGHT_OUTTAKE;
            fourthJunction = HIGH_JUNCTION_X4;
            fourthJunctionHeight = HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            fourthJunctionArm = Arm.ARM_RIGHT_OUTTAKE;
            fifthJunction = HIGH_JUNCTION_X4;
            fifthJunctionHeight = HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            fifthJunctionArm = Arm.ARM_RIGHT_OUTTAKE;

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
            startPose = new Pose2d(-35, -60.3, Math.toRadians(90));
            coneStackLine = LEFT_CONE_STACK_LINE;
            coneStack = LEFT_CONE_STACK_LEFT;
            lineUpPose = LEFT_LINEUP_TILE;
            coneStackEndOfLine = LEFT_CONE_STACK_END_OF_LINE;
            startingJunction = MEDIUM_JUNCTION_Y2;
            startingJunctionArm = Arm.ARM_RIGHT_OUTTAKE;
            startingJunctionHeight = MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            firstJunction = LOW_JUNCTION_Y1;
            firstJunctionHeight = LOW_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            firstJunctionArm = Arm.ARM_RIGHT_OUTTAKE;
            secondJunction = LOW_JUNCTION_Y1;
            secondJunctionHeight = LOW_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            secondJunctionArm = Arm.ARM_RIGHT_OUTTAKE;
            thirdJunction = LOW_JUNCTION_Y1;
            thirdJunctionHeight = LOW_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            thirdJunctionArm = Arm.ARM_RIGHT_OUTTAKE;
            fourthJunction = LOW_JUNCTION_Y1;
            fourthJunctionHeight = LOW_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            fourthJunctionArm = Arm.ARM_RIGHT_OUTTAKE;
            fifthJunction = HIGH_JUNCTION_X2;
            fifthJunctionHeight = LOW_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            fifthJunctionArm =  Arm.ARM_FRONT_OUTTAKE;

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

        //First Sequence drives to the Medium Junction, delivers cone, and drives to cone stack line
        trajSeq1 = MecDrive.trajectorySequenceBuilder(startPose)
                .addTemporalMarker(STARTING_CONE_LIFT_TIME, () ->{
                    Lift.StartLifting(startingJunctionHeight, Arm);
                })
                .UNSTABLE_addTemporalMarkerOffset(.8, () -> {
                    Arm.setPosition(startingJunctionArm);
                })
                    // deliver to medium junction
                .splineToConstantHeading(startingJunction,Math.toRadians(180))
                    // ---------move arm+lift to medium junction height------------
                .waitSeconds(.200)
                .UNSTABLE_addTemporalMarkerOffset(-.4, () -> {
                    Lift.StartLifting(startingJunctionHeight - 325, Arm);
                })
                .UNSTABLE_addTemporalMarkerOffset(-.3, () -> {
                    Claw.openClaw();
                })
                .UNSTABLE_addTemporalMarkerOffset(0, () ->{
                    Lift.StartLifting(startingJunctionHeight, Arm);
                })
                .setTangent(Math.toRadians(0))
                    //dive near cone stack line
                .splineToSplineHeading(lineUpPose, Math.toRadians(0))
                    //drive to cone stack
                .splineToSplineHeading(coneStackPose, Math.toRadians(0))
                    // --------Lift to 5 cone stack+intake----------
                .waitSeconds(.200)
                    // drive backwards
                .setReversed(false)
                    // deliver to high junction
                .splineToConstantHeading(firstJunction, Math.toRadians(90))
                    // --------move arm+lift to high junction--------
                .waitSeconds(.200)
                    // drive backwards
                .setReversed(true)
                .setTangent(Math.toRadians(270))
                    //drive to end of cone stack line
                .splineToConstantHeading(coneStackEndOfLine, Math.toRadians(0))
                    //go to cone stack
                .splineToConstantHeading(coneStack, Math.toRadians(0))
                    //// --------Lift to 5 cone stack+intake-----------
                .waitSeconds(.200)
                .setReversed(false)
                    // deliver to high junction again
                .splineToConstantHeading(secondJunction, Math.toRadians(90))
                // --------move arm+lift to high junction--------
                .build();


    }
}

