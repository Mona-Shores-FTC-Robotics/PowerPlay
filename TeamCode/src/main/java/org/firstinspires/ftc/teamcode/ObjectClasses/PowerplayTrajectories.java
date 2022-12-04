package org.firstinspires.ftc.teamcode.ObjectClasses;

import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.armState.ARM_CENTER;
import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.armState.ARM_FRONT;
import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.armState.ARM_LEFT;
import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.armState.ARM_RIGHT;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.EIGHTH_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FIVE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FOUR_CONE_STACK_INTAKE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FULL_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.HALF_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.HALF_TILE_DISTANCE_STRAFE;
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
    AprilTagVision Vision;
    public TrajectorySequence trajSeq1;
    public TrajectorySequence trajSeq2;
    public TrajectorySequence trajSeq3;


    public PowerplayTrajectories(SampleMecanumDrive drive, Lift lift, Claw claw, Intake intake, Arm arm, AprilTagVision vision) {
        MecDrive = drive;
        Lift = lift;
        Claw = claw;
        Intake = intake;
        Arm = arm;
        Vision = vision;
    }

    public static Vector2d MEDIUM_JUNCTION_Y4 = new Vector2d(FULL_TILE_DISTANCE_DRIVE+1, -FULL_TILE_DISTANCE_DRIVE);
    public static Vector2d MEDIUM_JUNCTION_Y2 = new Vector2d(-(FULL_TILE_DISTANCE_DRIVE+1), -(FULL_TILE_DISTANCE_DRIVE+3));

    public static Vector2d LOW_JUNCTION_Y5 = new Vector2d(47.2, -23.6);
    public static Vector2d LOW_JUNCTION_Y1 = new Vector2d(-47.2, -23.6);

    public static Vector2d HIGH_JUNCTION_X4 = new Vector2d(FULL_TILE_DISTANCE_DRIVE+1.5, -1.5 );
    public static Vector2d HIGH_JUNCTION_X4_WITH_CONE = new Vector2d(FULL_TILE_DISTANCE_DRIVE+2.5, -2.5);
    public static Vector2d HIGH_JUNCTION_X2 = new Vector2d(-(FULL_TILE_DISTANCE_DRIVE+1.5),-1.5);
    public static Vector2d HIGH_JUNCTION_X2_WITH_CONE = new Vector2d(-(FULL_TILE_DISTANCE_DRIVE+2.5), -2.5);

    public static Pose2d RIGHT_SIDE_LEFT_TILE_D4 = new Pose2d(HALF_TILE_DISTANCE_DRIVE, -1*HALF_TILE_DISTANCE_DRIVE,Math.toRadians(90));
    public static Pose2d RIGHT_SIDE_MIDDLE_TILE_D5 = new Pose2d(HALF_TILE_DISTANCE_DRIVE+FULL_TILE_DISTANCE_DRIVE, -1*HALF_TILE_DISTANCE_DRIVE,Math.toRadians(90));
    public static Pose2d RIGHT_SIDE_RIGHT_TILE_D6 = new Pose2d(HALF_TILE_DISTANCE_DRIVE+(FULL_TILE_DISTANCE_DRIVE*2), -1*HALF_TILE_DISTANCE_DRIVE,Math.toRadians(180));

    public static Pose2d LEFT_SIDE_LEFT_TILE_D1 = new Pose2d(-1*(HALF_TILE_DISTANCE_DRIVE+FULL_TILE_DISTANCE_DRIVE*2), -1*HALF_TILE_DISTANCE_DRIVE,Math.toRadians(180));
    public static Pose2d LEFT_SIDE_MIDDLE_TILE_D2 = new Pose2d( -1*(HALF_TILE_DISTANCE_DRIVE+FULL_TILE_DISTANCE_DRIVE), -1*HALF_TILE_DISTANCE_DRIVE,Math.toRadians(90));
    public static Pose2d LEFT_SIDE_RIGHT_TILE_D3 =  new Pose2d( -1*HALF_TILE_DISTANCE_DRIVE, -1*HALF_TILE_DISTANCE_DRIVE,Math.toRadians(90));

    public static Vector2d RIGHT_STRAFE_TILE = new Vector2d (FULL_TILE_DISTANCE_DRIVE+HALF_TILE_DISTANCE_DRIVE, -(HALF_TILE_DISTANCE_DRIVE));
    public static Vector2d LEFT_STRAFE_TILE = new Vector2d (-1*(FULL_TILE_DISTANCE_DRIVE+HALF_TILE_DISTANCE_DRIVE), -(HALF_TILE_DISTANCE_DRIVE+4));

    public static Vector2d RIGHT_CONE_STACK_RIGHT = new Vector2d(60, -9);
    public static Vector2d LEFT_CONE_STACK_LEFT = new Vector2d(-60, -9);
    public static Pose2d RIGHT_CONE_STACK_POSE = new Pose2d(FULL_TILE_DISTANCE_DRIVE*2+HALF_TILE_DISTANCE_DRIVE, -10, Math.toRadians(180));
    public static Pose2d LEFT_CONE_STACK_POSE = new Pose2d(-(FULL_TILE_DISTANCE_DRIVE*2+HALF_TILE_DISTANCE_DRIVE), -10, Math.toRadians(0));
    public static Vector2d RIGHT_CONE_STACK_END_OF_LINE = new Vector2d(FULL_TILE_DISTANCE_DRIVE+HALF_TILE_DISTANCE_DRIVE, -HALF_TILE_DISTANCE_STRAFE);
    public static Vector2d LEFT_CONE_STACK_END_OF_LINE = new Vector2d(-1*(FULL_TILE_DISTANCE_DRIVE+HALF_TILE_DISTANCE_DRIVE), -HALF_TILE_DISTANCE_DRIVE);
    public static Pose2d RIGHT_CONE_STACK_LINE = new Pose2d(45, -9, Math.toRadians(180));
    public static Pose2d LEFT_CONE_STACK_LINE = new Pose2d(-45, -9, Math.toRadians(0));

    public static Pose2d startPose;
    public static Pose2d currentPose;
    public static Vector2d coneStack;
    public static Pose2d coneStackPose;
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
    public static Boolean startPosRIGHT = false;
    public static Pose2d endAutoPosition;

    public static int numberSignal = 1;

    // Lift@Deliver
    public static double STARTING_CONE_LIFT_TIME = 0;
    // Rotate Arm
    public static double STARTING_CONE_ARM_TIME = STARTING_CONE_LIFT_TIME + 1.2;
    // Lift@Dunk
    public static double STARTING_CONE_DUNK_TIME = STARTING_CONE_ARM_TIME + 1.8;
    // Open Claw
    public static double STARTING_CONE_DELIVER_TIME = STARTING_CONE_DUNK_TIME + .3;
    // Lift@Undunk
    public static double STARTING_CONE_UNDUNK_TIME = STARTING_CONE_DELIVER_TIME + .2;


    // Arm Center, Lift@Pickup, Claw Easy, Intake On;
    public static double FIRST_CONE_APPROACH_TIME = 0;
    // Intake Off, Close Claw, Lift@+200
    public static double FIRST_CONE_GRAB_TIME = FIRST_CONE_APPROACH_TIME + 1.5;
    // Lift@Deliver, Rotate Arm
    public static double FIRST_CONE_DELIVERY_SETUP_TIME = FIRST_CONE_GRAB_TIME + 1.1;
    // Lift@Dunk
    public static double FIRST_CONE_DUNK_TIME = FIRST_CONE_DELIVERY_SETUP_TIME + 1.1;
    // Open Claw
    public static double FIRST_CONE_DELIVER_TIME = FIRST_CONE_DUNK_TIME + .2;
    // Lift@Undunk
    public static double FIRST_CONE_UNDUNK_TIME = FIRST_CONE_DELIVER_TIME + .2;


    public static double SECOND_CONE_APPROACH_TIME = 4.8;
    public static double SECOND_CONE_GRAB_TIME = SECOND_CONE_APPROACH_TIME + 1.5;
    public static double SECOND_CONE_DELIVERY_SETUP_TIME = SECOND_CONE_GRAB_TIME + 1.1;
    public static double SECOND_CONE_DUNK_TIME = SECOND_CONE_DELIVERY_SETUP_TIME + 1.1;
    public static double SECOND_CONE_DELIVER_TIME = SECOND_CONE_DUNK_TIME + .2;
    public static double SECOND_CONE_UNDUNK_TIME = SECOND_CONE_DELIVER_TIME + .2;

    public static double THIRD_CONE_APPROACH_TIME = 8.7;
    public static double THIRD_CONE_GRAB_TIME = THIRD_CONE_APPROACH_TIME + 1.5;
    public static double THIRD_CONE_DELIVERY_SETUP_TIME = THIRD_CONE_GRAB_TIME + 1.1;
    public static double THIRD_CONE_DUNK_TIME = THIRD_CONE_DELIVERY_SETUP_TIME + 1.1;
    public static double THIRD_CONE_DELIVER_TIME = THIRD_CONE_DUNK_TIME + .2;
    public static double THIRD_CONE_UNDUNK_TIME = THIRD_CONE_DELIVER_TIME + .2;

    public static double FOURTH_CONE_APPROACH_TIME = 12.8;
    public static double FOURTH_CONE_GRAB_TIME = FOURTH_CONE_APPROACH_TIME + 1.5;
    public static double FOURTH_CONE_DELIVERY_SETUP_TIME = FOURTH_CONE_GRAB_TIME + 1.1;
    public static double FOURTH_CONE_DUNK_TIME = FOURTH_CONE_DELIVERY_SETUP_TIME + 1.1;
    public static double FOURTH_CONE_DELIVER_TIME = FOURTH_CONE_DUNK_TIME + .2;
    public static double FOURTH_CONE_UNDUNK_TIME = FOURTH_CONE_DELIVER_TIME + .2;

    public static double FIFTH_CONE_APPROACH_TIME = 17.1;
    public static double FIFTH_CONE_GRAB_TIME = FIFTH_CONE_APPROACH_TIME + 1.5;
    public static double FIFTH_CONE_DELIVERY_SETUP_TIME = FIFTH_CONE_GRAB_TIME + 1.1;
    public static double FIFTH_CONE_DUNK_TIME = FIFTH_CONE_DELIVERY_SETUP_TIME + 1.1;
    public static double FIFTH_CONE_DELIVER_TIME = FIFTH_CONE_DUNK_TIME + .2;
    public static double FIFTH_CONE_UNDUNK_TIME = FIFTH_CONE_DELIVER_TIME + .2;

    public static double END_RESET_TIME = 24;
    public static double startingJunctionTangent;
    public static double coneStackHeading;
    public static double firstJunctionHeading;


    public static Vector2d strafeTile;

    public void MakeTrajectories() {

        if (ButtonConfig.currentStartPosition == ButtonConfig.StartingPosition.RIGHT_SIDE) {
            startPose = new Pose2d(FULL_TILE_DISTANCE_DRIVE+HALF_TILE_DISTANCE_DRIVE, -62, Math.toRadians(90));
            coneStackLine = RIGHT_CONE_STACK_LINE;
            coneStackPose = RIGHT_CONE_STACK_POSE;
            coneStackEndOfLine = RIGHT_CONE_STACK_END_OF_LINE;
            strafeTile = RIGHT_STRAFE_TILE;


            coneStackHeading = Math.toRadians(0);
            startingJunctionTangent = Math.toRadians(180);
            firstJunctionHeading = Math.toRadians(140);

            startingJunction = MEDIUM_JUNCTION_Y4;
            startingJunctionHeight = MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            startingJunctionArm = Arm.ARM_LEFT_OUTTAKE;
            firstJunction = HIGH_JUNCTION_X4;
            firstJunctionHeight = HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            firstJunctionArm = Arm.ARM_FRONT_OUTTAKE;
            secondJunction = HIGH_JUNCTION_X4_WITH_CONE;
            secondJunctionHeight = HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            secondJunctionArm = Arm.ARM_FRONT_OUTTAKE;
            thirdJunction = HIGH_JUNCTION_X4_WITH_CONE;
            thirdJunctionHeight = HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            thirdJunctionArm = Arm.ARM_FRONT_OUTTAKE;
            fourthJunction = HIGH_JUNCTION_X4_WITH_CONE;
            fourthJunctionHeight = HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            fourthJunctionArm = Arm.ARM_FRONT_OUTTAKE;
            fifthJunction = HIGH_JUNCTION_X4_WITH_CONE;
            fifthJunctionHeight = HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            fifthJunctionArm = Arm.ARM_FRONT_OUTTAKE;

            if (Vision.currentSignal == AprilTagVision.Signal.LEFT) {
                endAutoPosition = RIGHT_SIDE_LEFT_TILE_D4;
            } else if (Vision.currentSignal == AprilTagVision.Signal.MIDDLE) {
                endAutoPosition = RIGHT_SIDE_MIDDLE_TILE_D5;
            } else if (Vision.currentSignal == AprilTagVision.Signal.RIGHT ) {
                endAutoPosition = RIGHT_SIDE_RIGHT_TILE_D6;
            }

        } else {
            startPose = new Pose2d(-(FULL_TILE_DISTANCE_DRIVE+HALF_TILE_DISTANCE_DRIVE), -62, Math.toRadians(90));
            coneStackLine = LEFT_CONE_STACK_LINE;
            coneStackEndOfLine = LEFT_CONE_STACK_END_OF_LINE;
            coneStackPose = LEFT_CONE_STACK_POSE;
            coneStackHeading = Math.toRadians(180);
            startingJunctionTangent = Math.toRadians(0);
            firstJunctionHeading = Math.toRadians(55);
            strafeTile = LEFT_STRAFE_TILE;

            startingJunction = MEDIUM_JUNCTION_Y2;
            startingJunctionArm = Arm.ARM_RIGHT_OUTTAKE;
            startingJunctionHeight = MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            firstJunction = HIGH_JUNCTION_X2;
            firstJunctionHeight = HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            firstJunctionArm = Arm.ARM_FRONT_OUTTAKE;;
            secondJunction = HIGH_JUNCTION_X2_WITH_CONE;
            secondJunctionHeight = HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            secondJunctionArm = Arm.ARM_FRONT_OUTTAKE;
            thirdJunction = HIGH_JUNCTION_X2_WITH_CONE;
            thirdJunctionHeight = HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            thirdJunctionArm = Arm.ARM_FRONT_OUTTAKE;
            fourthJunction = HIGH_JUNCTION_X2_WITH_CONE;
            fourthJunctionHeight = HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            fourthJunctionArm = Arm.ARM_FRONT_OUTTAKE;;
            fifthJunction = HIGH_JUNCTION_X2_WITH_CONE;
            fifthJunctionHeight = HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            fifthJunctionArm = Arm.ARM_FRONT_OUTTAKE;;

            if (Vision.currentSignal == AprilTagVision.Signal.LEFT) {
                endAutoPosition = LEFT_SIDE_LEFT_TILE_D1;
            } else if (Vision.currentSignal == AprilTagVision.Signal.MIDDLE) {
                endAutoPosition = LEFT_SIDE_MIDDLE_TILE_D2;
            } else if (Vision.currentSignal == AprilTagVision.Signal.RIGHT) {
                endAutoPosition = LEFT_SIDE_RIGHT_TILE_D3;
            }

        }

        //First Sequence drives to the Medium Junction, delivers cone, and drives to cone stack line
        trajSeq1 = MecDrive.trajectorySequenceBuilder(startPose)

                //----STARTING CONE ROBOT MANEUVERS-------//
                .addTemporalMarker(STARTING_CONE_LIFT_TIME, () -> {
                    Lift.StartLifting(startingJunctionHeight, Arm);
                })
                .UNSTABLE_addTemporalMarkerOffset(.8, () -> {
                    Arm.setPosition(startingJunctionArm);
                })
                .splineToConstantHeading(startingJunction, startingJunctionTangent)
                .waitSeconds(.400)
                .UNSTABLE_addTemporalMarkerOffset(-.4, () -> {
                    Lift.StartLifting(startingJunctionHeight - 325, Arm);
                })
               .UNSTABLE_addTemporalMarkerOffset(-.3, () -> {
                    Claw.openClaw();
                })
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    Lift.StartLifting(startingJunctionHeight, Arm);
                })

                //---FIRST CONE ROBOT MANEUVERS-------//
                .strafeTo(strafeTile)
                .lineToLinearHeading(new Pose2d(coneStackEndOfLine, startingJunctionTangent))
                .UNSTABLE_addTemporalMarkerOffset(-.2, () -> {
                    Arm.setPosition(org.firstinspires.ftc.teamcode.ObjectClasses.Arm.ARM_CENTER_INTAKE);
                    Lift.StartLifting(FIVE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL, Arm);
                    Claw.setEasyIntake();
                    Intake.turnIntakeOn();
                })
                .setReversed(true)
                .splineToSplineHeading(coneStackPose, coneStackHeading)
                .waitSeconds(.200)
                .UNSTABLE_addTemporalMarkerOffset(-.2, () -> {
                    Intake.turnIntakeOff();
                    Claw.closeClaw();
                    Lift.StartLifting(firstJunctionHeight, Arm);
                })
                .setReversed(false)
                .splineToSplineHeading(new Pose2d(firstJunction, firstJunctionHeading), firstJunctionHeading)
                .UNSTABLE_addTemporalMarkerOffset(-2, () -> {
                    Arm.setPosition(firstJunctionArm);
                })
                .waitSeconds(.600)
                .UNSTABLE_addTemporalMarkerOffset(-.6, () -> {
                    Lift.StartLifting(firstJunctionHeight - 325, Arm);
                })
                .UNSTABLE_addTemporalMarkerOffset(-.4, () -> {
                    Claw.openClaw();
                })
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    Lift.StartLifting(firstJunctionHeight, Arm);
                })

                //----SECOND CONE ROBOT MANEUVERS-------//
                .setReversed(true)
                .UNSTABLE_addTemporalMarkerOffset(.4, () -> {
                    Arm.setPosition(org.firstinspires.ftc.teamcode.ObjectClasses.Arm.ARM_CENTER_INTAKE);
                    Lift.StartLifting(FOUR_CONE_STACK_INTAKE_HEIGHT_ENC_VAL, Arm);
                    Claw.setEasyIntake();
                    Intake.turnIntakeOn();
                })
                .splineToSplineHeading(coneStackPose, coneStackHeading)
                .waitSeconds(.200)
                .UNSTABLE_addTemporalMarkerOffset(-.2, () -> {
                    Intake.turnIntakeOff();
                    Claw.closeClaw();
                    Lift.StartLifting(secondJunctionHeight, Arm);
                })
                .setReversed(false)
                .UNSTABLE_addTemporalMarkerOffset(.8, () -> {
                    Arm.setPosition(secondJunctionArm);
                })
                .splineToSplineHeading(new Pose2d(secondJunction, firstJunctionHeading), firstJunctionHeading)
                .waitSeconds(.400)
                .UNSTABLE_addTemporalMarkerOffset(-.4, () -> {
                    Lift.StartLifting(secondJunctionHeight - 325, Arm);
                })
                .UNSTABLE_addTemporalMarkerOffset(-.3, () -> {
                    Claw.openClaw();
                })
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    Lift.StartLifting(secondJunctionHeight, Arm);
                })

                //----THIRD CONE ROBOT MANEUVERS-------//
                .setReversed(true)
                .UNSTABLE_addTemporalMarkerOffset(.4, () -> {
                    Arm.setPosition(org.firstinspires.ftc.teamcode.ObjectClasses.Arm.ARM_CENTER_INTAKE);
                    Lift.StartLifting(THREE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL, Arm);
                    Claw.setEasyIntake();
                    Intake.turnIntakeOn();
                })
                .splineToSplineHeading(coneStackPose, coneStackHeading)
                .waitSeconds(.200)
                .UNSTABLE_addTemporalMarkerOffset(-.2, () -> {
                    Intake.turnIntakeOff();
                    Claw.closeClaw();
                    Lift.StartLifting(thirdJunctionHeight, Arm);
                })
                .setReversed(false)
                .UNSTABLE_addTemporalMarkerOffset(.8, () -> {
                    Arm.setPosition(thirdJunctionArm);
                })
                .splineToSplineHeading(new Pose2d(thirdJunction, firstJunctionHeading), firstJunctionHeading)
                .waitSeconds(.400)
                .UNSTABLE_addTemporalMarkerOffset(-.4, () -> {
                    Lift.StartLifting(thirdJunctionHeight - 325, Arm);
                })
                .UNSTABLE_addTemporalMarkerOffset(-.3, () -> {
                    Claw.openClaw();
                })
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                    Lift.StartLifting(thirdJunctionHeight, Arm);
                })

                //----PARKING-------//
                .UNSTABLE_addTemporalMarkerOffset(.4, () -> {
                    Arm.setPosition(org.firstinspires.ftc.teamcode.ObjectClasses.Arm.ARM_CENTER_INTAKE);
                    Lift.StartLifting(ONE_CONE_INTAKE_HEIGHT_ENC_VAL, Arm);
                    Claw.setEasyIntake();
                })
                .back(4)
                .lineToLinearHeading(endAutoPosition)
                .build();
    }
}

