package org.firstinspires.ftc.teamcode.ObjectClasses;

import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.EIGHTH_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FIVE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FOUR_CONE_STACK_INTAKE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FULL_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.HALF_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.ONE_CONE_INTAKE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.QUARTER_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.SIXTEENTH_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.THREE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.TWO_CONE_STACK_INTAKE_HEIGHT_ENC_VAL;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.constraints.AngularVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.MinVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TranslationalVelocityConstraint;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import java.util.Arrays;

public class PowerplayTrajectories {

    SampleMecanumDrive MecDrive;
    Claw Claw;
    Intake Intake;
    Arm Arm;
    Lift Lift;
    AprilTagVision Vision;

    public TrajectorySequence trajSeqSixMedStart;
    public TrajectorySequence trajSeqSixMed1;
    public TrajectorySequence trajSeqSixMed2;
    public TrajectorySequence trajSeqSixMed3;
    public TrajectorySequence trajSeqSixMed4;
    public TrajectorySequence trajSeqSixMedPark;

    public PowerplayTrajectories(SampleMecanumDrive drive, Lift lift, Claw claw, Intake intake, Arm arm, AprilTagVision vision) {
        MecDrive = drive;
        Lift = lift;
        Claw = claw;
        Intake = intake;
        Arm = arm;
        Vision = vision;
    }

    public static Vector2d MEDIUM_JUNCTION_Y4 = new Vector2d(FULL_TILE_DISTANCE_DRIVE + 2.1, -(FULL_TILE_DISTANCE_DRIVE-.6));
    public static Vector2d MEDIUM_JUNCTION_Y4_WITH_CONE = new Vector2d(FULL_TILE_DISTANCE_DRIVE+.1, -1*(FULL_TILE_DISTANCE_DRIVE-4.2)); //TODO: this might need to be adjusted

    public static Vector2d MEDIUM_JUNCTION_Y2 = new Vector2d(-(FULL_TILE_DISTANCE_DRIVE + 4), -(FULL_TILE_DISTANCE_DRIVE+1.5));
    public static Vector2d MEDIUM_JUNCTION_Y2_WITH_CONE = new Vector2d(-1*(FULL_TILE_DISTANCE_DRIVE), -1*(FULL_TILE_DISTANCE_DRIVE-4));

    public static Vector2d LOW_JUNCTION_Y5 = new Vector2d(47.2, -23.6);
    public static Vector2d LOW_JUNCTION_Y1 = new Vector2d(-47.2, -23.6);

    public static Vector2d HIGH_JUNCTION_X4 = new Vector2d(FULL_TILE_DISTANCE_DRIVE+3, -3);
    public static Vector2d HIGH_JUNCTION_X4_WITH_CONE = new Vector2d(FULL_TILE_DISTANCE_DRIVE+4, -4);

    public static Vector2d HIGH_JUNCTION_X2 = new Vector2d(-(FULL_TILE_DISTANCE_DRIVE+3),-3);
    public static Vector2d HIGH_JUNCTION_X2_WITH_CONE = new Vector2d(-(FULL_TILE_DISTANCE_DRIVE+4), -4);

    public static Pose2d RIGHT_SIDE_LEFT_TILE_D4 = new Pose2d(HALF_TILE_DISTANCE_DRIVE, -1*HALF_TILE_DISTANCE_DRIVE,Math.toRadians(180));
    public static Pose2d RIGHT_SIDE_MIDDLE_TILE_D5 = new Pose2d(HALF_TILE_DISTANCE_DRIVE+FULL_TILE_DISTANCE_DRIVE, -1*HALF_TILE_DISTANCE_DRIVE,Math.toRadians(180));
    public static Pose2d RIGHT_SIDE_RIGHT_TILE_D6 = new Pose2d(HALF_TILE_DISTANCE_DRIVE+(FULL_TILE_DISTANCE_DRIVE*2), -1*HALF_TILE_DISTANCE_DRIVE,Math.toRadians(180));

    public static Pose2d LEFT_SIDE_LEFT_TILE_D1 = new Pose2d(-1*(HALF_TILE_DISTANCE_DRIVE+FULL_TILE_DISTANCE_DRIVE*2), -1*HALF_TILE_DISTANCE_DRIVE,Math.toRadians(0));
    public static Pose2d LEFT_SIDE_MIDDLE_TILE_D2 = new Pose2d( -1*(HALF_TILE_DISTANCE_DRIVE+FULL_TILE_DISTANCE_DRIVE), -1*HALF_TILE_DISTANCE_DRIVE,Math.toRadians(0));
    public static Pose2d LEFT_SIDE_RIGHT_TILE_D3 =  new Pose2d( -1*HALF_TILE_DISTANCE_DRIVE, -1*(HALF_TILE_DISTANCE_DRIVE),Math.toRadians(0));

    public static Vector2d RIGHT_CONE_STACK_RIGHT = new Vector2d(60, -HALF_TILE_DISTANCE_DRIVE);
    public static Vector2d LEFT_CONE_STACK_LEFT = new Vector2d(-60, -HALF_TILE_DISTANCE_DRIVE);

    public static Pose2d RIGHT_CONE_STACK_POSE = new Pose2d(FULL_TILE_DISTANCE_DRIVE*2+SIXTEENTH_TILE_DISTANCE_DRIVE, -HALF_TILE_DISTANCE_DRIVE, Math.toRadians(180)); //TODO: trying sixteenth here maybe?
    public static Pose2d LEFT_CONE_STACK_POSE = new Pose2d(-(FULL_TILE_DISTANCE_DRIVE*2+EIGHTH_TILE_DISTANCE_DRIVE), -HALF_TILE_DISTANCE_DRIVE, Math.toRadians(0));

    public static Vector2d RIGHT_CONE_STACK_END_OF_LINE = new Vector2d(FULL_TILE_DISTANCE_DRIVE+HALF_TILE_DISTANCE_DRIVE, -HALF_TILE_DISTANCE_DRIVE);
    public static Vector2d LEFT_CONE_STACK_END_OF_LINE = new Vector2d(-1*(FULL_TILE_DISTANCE_DRIVE+HALF_TILE_DISTANCE_DRIVE), -HALF_TILE_DISTANCE_DRIVE);

    public static Vector2d RIGHT_CONE_STACK_MIDDLE_OF_LINE = new Vector2d(FULL_TILE_DISTANCE_DRIVE*2+SIXTEENTH_TILE_DISTANCE_DRIVE, -HALF_TILE_DISTANCE_DRIVE);
    public static Vector2d LEFT_CONE_STACK_MIDDLE_OF_LINE = new Vector2d(-1*(FULL_TILE_DISTANCE_DRIVE*2+SIXTEENTH_TILE_DISTANCE_DRIVE), -HALF_TILE_DISTANCE_DRIVE);

    public static Pose2d RIGHT_CONE_STACK_LINE = new Pose2d(45, -HALF_TILE_DISTANCE_DRIVE, Math.toRadians(180));
    public static Pose2d LEFT_CONE_STACK_LINE = new Pose2d(-45, -HALF_TILE_DISTANCE_DRIVE, Math.toRadians(0));

    public static Vector2d RIGHT_STAGING_SPOT = new Vector2d(FULL_TILE_DISTANCE_DRIVE+HALF_TILE_DISTANCE_DRIVE+EIGHTH_TILE_DISTANCE_DRIVE, -(HALF_TILE_DISTANCE_DRIVE));
    public static Vector2d LEFT_STAGING_SPOT = new Vector2d(-1*(FULL_TILE_DISTANCE_DRIVE+HALF_TILE_DISTANCE_DRIVE+EIGHTH_TILE_DISTANCE_DRIVE), -(HALF_TILE_DISTANCE_DRIVE));

    public static Pose2d startPose;
    public static Pose2d currentPose;
    public static Vector2d coneStack;
    public static Pose2d coneStackPose;
    public static Vector2d coneStackEndOfLine;
    public static Vector2d coneStackMiddleOfLine;
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
    public static double relativeEndAutoPosition;
    public static double STARTING_CONE_LIFT_TIME = 0;
    public static double startingJunctionTangent;
    public static double coneStackHeading;
    public static double firstJunctionHeading;
    public static Vector2d stagingSpot;
    public static double lineupHeading;
    public static double deliveryHeading;
    public static double stagingHeading;
    public static double relativeEndAutoHeading;

    public void MakeTrajectoriesStart() {

        if (ButtonConfig.currentStartPosition == ButtonConfig.StartingPosition.RIGHT_SIDE) {
            PowerplayTrajectories.startPose = new Pose2d(FULL_TILE_DISTANCE_DRIVE + QUARTER_TILE_DISTANCE_DRIVE + EIGHTH_TILE_DISTANCE_DRIVE, -58.1, Math.toRadians(90));
            coneStackLine = RIGHT_CONE_STACK_LINE;
            coneStackPose = RIGHT_CONE_STACK_POSE;
            coneStack = RIGHT_CONE_STACK_MIDDLE_OF_LINE;
            coneStackEndOfLine = RIGHT_CONE_STACK_END_OF_LINE;

            coneStackHeading = Math.toRadians(0);
            startingJunctionTangent = Math.toRadians(180);
            firstJunctionHeading = Math.toRadians(225);
            lineupHeading =Math.toRadians(60); //TODO: this was like 215 or something before, I think 45 or 60 should be right
            deliveryHeading = Math.toRadians(45);
            stagingHeading = Math.toRadians(180);
            relativeEndAutoHeading = Math.toRadians(-45);

            stagingSpot = RIGHT_STAGING_SPOT;

            startingJunction = MEDIUM_JUNCTION_Y4;
            startingJunctionHeight = MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            startingJunctionArm = Arm.ARM_LEFT_OUTTAKE;
            firstJunction = MEDIUM_JUNCTION_Y4_WITH_CONE;
            firstJunctionHeight = MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            firstJunctionArm = Arm.ARM_FRONT_OUTTAKE;
            secondJunction = MEDIUM_JUNCTION_Y4_WITH_CONE;
            secondJunctionHeight = MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            secondJunctionArm = Arm.ARM_FRONT_OUTTAKE;
            thirdJunction = MEDIUM_JUNCTION_Y4_WITH_CONE;
            thirdJunctionHeight = MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            thirdJunctionArm = Arm.ARM_FRONT_OUTTAKE;
            fourthJunction = MEDIUM_JUNCTION_Y4_WITH_CONE;
            fourthJunctionHeight = MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            fourthJunctionArm = Arm.ARM_FRONT_OUTTAKE;
            fifthJunction = MEDIUM_JUNCTION_Y4_WITH_CONE;
            fifthJunctionHeight = MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            fifthJunctionArm = Arm.ARM_FRONT_OUTTAKE;

        } else {
            PowerplayTrajectories.startPose = new Pose2d(-(FULL_TILE_DISTANCE_DRIVE + QUARTER_TILE_DISTANCE_DRIVE + EIGHTH_TILE_DISTANCE_DRIVE), -58.1, Math.toRadians(90));
            coneStackLine = LEFT_CONE_STACK_LINE;
            coneStackEndOfLine = LEFT_CONE_STACK_END_OF_LINE;
            coneStackPose = LEFT_CONE_STACK_POSE;
            coneStackHeading = Math.toRadians(180);
            startingJunctionTangent = Math.toRadians(0);
            firstJunctionHeading = Math.toRadians(315);
            lineupHeading = Math.toRadians(135);
            deliveryHeading = Math.toRadians(135);
            stagingHeading = Math.toRadians(0);
            stagingSpot = LEFT_STAGING_SPOT;
            relativeEndAutoHeading = Math.toRadians(45);

            startingJunction = MEDIUM_JUNCTION_Y2;
            startingJunctionArm = Arm.ARM_RIGHT_OUTTAKE;
            startingJunctionHeight = MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            firstJunction = MEDIUM_JUNCTION_Y2_WITH_CONE;
            firstJunctionHeight = MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            firstJunctionArm = Arm.ARM_FRONT_OUTTAKE;
            secondJunction = MEDIUM_JUNCTION_Y2_WITH_CONE;
            secondJunctionHeight = MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            secondJunctionArm = Arm.ARM_FRONT_OUTTAKE;
            thirdJunction = MEDIUM_JUNCTION_Y2_WITH_CONE;
            thirdJunctionHeight = MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            thirdJunctionArm = Arm.ARM_FRONT_OUTTAKE;
            fourthJunction = MEDIUM_JUNCTION_Y2_WITH_CONE;
            fourthJunctionHeight = MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            fourthJunctionArm = Arm.ARM_FRONT_OUTTAKE;

            fifthJunction = MEDIUM_JUNCTION_Y2_WITH_CONE;
            fifthJunctionHeight = MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            fifthJunctionArm = Arm.ARM_FRONT_OUTTAKE;

        }

        //----DELIVERY 6 CONES TO MEDIUM JUNCTION TRAJECTORIES---//
        //----STARTING CONE DELIVERY SEQUENCE-------//
        TrajectoryVelocityConstraint velConstraint = new MinVelocityConstraint(Arrays.asList(
                new TranslationalVelocityConstraint(35),
                new AngularVelocityConstraint(1)
        ));
        trajSeqSixMedStart = MecDrive.trajectorySequenceBuilder(startPose)
                .setVelConstraint(velConstraint)
                .addTemporalMarker(STARTING_CONE_LIFT_TIME, () -> {
                                    Lift.StartLifting(startingJunctionHeight, Arm);
                })
                .UNSTABLE_addTemporalMarkerOffset(.8, () -> {
                                    Arm.setPosition(startingJunctionArm);
                })
                .splineToConstantHeading(startingJunction, startingJunctionTangent)
                .waitSeconds(.200)
                .UNSTABLE_addTemporalMarkerOffset(-.2, () -> {
                                    Lift.StartLifting(startingJunctionHeight - 350, Arm);
                })
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
                                    Claw.openClaw();
                })
                .UNSTABLE_addTemporalMarkerOffset(.4, () -> {
                    Arm.setPosition(org.firstinspires.ftc.teamcode.ObjectClasses.Arm.ARM_CENTER_INTAKE);
                })
                .UNSTABLE_addTemporalMarkerOffset(.8, () -> {
                    Lift.StartLifting(FIVE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL, Arm);
                    Claw.setEasyIntake();
                    Intake.turnIntakeOn();
                })
                .setReversed(false)
                .setTangent(lineupHeading) //TODO: this might need to be adjusted
                .splineToLinearHeading(new Pose2d(stagingSpot, stagingHeading), coneStackHeading)
                .lineToLinearHeading(coneStackPose)
                .build();
    }

    public void MakeTrajectories1() {
        //---FIRST CONE FROM CONE STACK DELIVERY SEQUENCE-------//
        trajSeqSixMed1 = MecDrive.trajectorySequenceBuilder(currentPose)
                .back(8)
                .waitSeconds(.2)
                .UNSTABLE_addTemporalMarkerOffset(-.2, () -> {
                    Intake.turnIntakeOff();
                    Claw.closeClaw();
                })
                .UNSTABLE_addTemporalMarkerOffset(-.1, () -> {
                    Lift.StartLifting(firstJunctionHeight, Arm);
                })
                .UNSTABLE_addTemporalMarkerOffset(.4, () -> {
                    Arm.setPosition(firstJunctionArm);
                })
                .setReversed(false)
                .splineToSplineHeading(new Pose2d(firstJunction, firstJunctionHeading), firstJunctionHeading)
                .waitSeconds(.300)
                .UNSTABLE_addTemporalMarkerOffset(-.3, () -> {
                    Lift.StartLifting(firstJunctionHeight - 350, Arm);
                })
                .UNSTABLE_addTemporalMarkerOffset(-.1, () -> {
                    Claw.openClaw();
                })
                .UNSTABLE_addTemporalMarkerOffset(.4, () -> {
                    Arm.setPosition(org.firstinspires.ftc.teamcode.ObjectClasses.Arm.ARM_CENTER_INTAKE);
                })
                .UNSTABLE_addTemporalMarkerOffset(.8, () -> {
                    Lift.StartLifting(FOUR_CONE_STACK_INTAKE_HEIGHT_ENC_VAL, Arm);
                    Claw.setEasyIntake();
                    Intake.turnIntakeOn();
                })
                .setReversed(false)
                .setTangent(deliveryHeading)
                .splineToSplineHeading(coneStackPose, coneStackHeading)
                .build();
    }

    public void MakeTrajectories2() {
        //---FIRST CONE FROM CONE STACK DELIVERY SEQUENCE-------//
        trajSeqSixMed2 = MecDrive.trajectorySequenceBuilder(currentPose)
                .back(7.5)
                .waitSeconds(.2)
                .UNSTABLE_addTemporalMarkerOffset(-.2, () -> {
                    Intake.turnIntakeOff();
                    Claw.closeClaw();
                })
                .UNSTABLE_addTemporalMarkerOffset(-.1, () -> {
                    Lift.StartLifting(firstJunctionHeight, Arm);
                })
                .UNSTABLE_addTemporalMarkerOffset(.4, () -> {
                    Arm.setPosition(firstJunctionArm);
                })
                .setReversed(false)
                .splineToSplineHeading(new Pose2d(firstJunction, firstJunctionHeading), firstJunctionHeading)
                .waitSeconds(.300)
                .UNSTABLE_addTemporalMarkerOffset(-.3, () -> {
                    Lift.StartLifting(firstJunctionHeight - 350, Arm);
                })
                .UNSTABLE_addTemporalMarkerOffset(-.1, () -> {
                    Claw.openClaw();
                })
                .UNSTABLE_addTemporalMarkerOffset(.4, () -> {
                    Arm.setPosition(org.firstinspires.ftc.teamcode.ObjectClasses.Arm.ARM_CENTER_INTAKE);
                })
                .UNSTABLE_addTemporalMarkerOffset(.8, () -> {
                    Lift.StartLifting(THREE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL, Arm);
                    Claw.setEasyIntake();
                    Intake.turnIntakeOn();
                })
                .setReversed(false)
                .setTangent(deliveryHeading)
                .splineToSplineHeading(coneStackPose, coneStackHeading)
                .build();
    }

    public void MakeTrajectories3() {
        //---FIRST CONE FROM CONE STACK DELIVERY SEQUENCE-------//
        trajSeqSixMed3 = MecDrive.trajectorySequenceBuilder(currentPose)
                .back(7.5)
                .waitSeconds(.200)
                .UNSTABLE_addTemporalMarkerOffset(-.2, () -> {
                    Intake.turnIntakeOff();
                    Claw.closeClaw();
                })
                .UNSTABLE_addTemporalMarkerOffset(-.1, () -> {
                    Lift.StartLifting(firstJunctionHeight, Arm);
                })
                .UNSTABLE_addTemporalMarkerOffset(.4, () -> {
                    Arm.setPosition(firstJunctionArm);
                })
                .setReversed(false)
                .splineToSplineHeading(new Pose2d(firstJunction, firstJunctionHeading), firstJunctionHeading)
                .waitSeconds(.300)
                .UNSTABLE_addTemporalMarkerOffset(-.3, () -> {
                    Lift.StartLifting(firstJunctionHeight - 350, Arm);
                })
                .UNSTABLE_addTemporalMarkerOffset(-.1, () -> {
                    Claw.openClaw();
                })
                .UNSTABLE_addTemporalMarkerOffset(.4, () -> {
                    Arm.setPosition(org.firstinspires.ftc.teamcode.ObjectClasses.Arm.ARM_CENTER_INTAKE);
                })
                .UNSTABLE_addTemporalMarkerOffset(.8, () -> {
                    Lift.StartLifting(TWO_CONE_STACK_INTAKE_HEIGHT_ENC_VAL, Arm);
                    Claw.setEasyIntake();
                    Intake.turnIntakeOn();
                })
                .setReversed(false)
                .setTangent(deliveryHeading)
                .splineToSplineHeading(coneStackPose, coneStackHeading)
                .build();
    }

    public void MakeTrajectories4() {
        //---FIRST CONE FROM CONE STACK DELIVERY SEQUENCE-------//
        trajSeqSixMed4 = MecDrive.trajectorySequenceBuilder(currentPose)
                .back(7.5)
                .waitSeconds(.2)
                .UNSTABLE_addTemporalMarkerOffset(-.2, () -> {
                    Intake.turnIntakeOff();
                    Claw.closeClaw();
                })
                .UNSTABLE_addTemporalMarkerOffset(-.1, () -> {
                    Lift.StartLifting(firstJunctionHeight, Arm);
                })
                .UNSTABLE_addTemporalMarkerOffset(.4, () -> {
                    Arm.setPosition(firstJunctionArm);
                })
                .setReversed(false)
                .splineToSplineHeading(new Pose2d(firstJunction, firstJunctionHeading), firstJunctionHeading)
                .waitSeconds(.300)
                .UNSTABLE_addTemporalMarkerOffset(-.3, () -> {
                    Lift.StartLifting(firstJunctionHeight - 350, Arm);
                })
                .UNSTABLE_addTemporalMarkerOffset(-.1, () -> {
                    Claw.openClaw();
                })
                .UNSTABLE_addTemporalMarkerOffset(.4, () -> {
                    Arm.setPosition(org.firstinspires.ftc.teamcode.ObjectClasses.Arm.ARM_CENTER_INTAKE);
                })
                .UNSTABLE_addTemporalMarkerOffset(.8, () -> {
                    Lift.StartLifting(ONE_CONE_INTAKE_HEIGHT_ENC_VAL, Arm);
                    Claw.setEasyIntake();
                    Intake.turnIntakeOn();
                })
                .setReversed(false)
                .setTangent(deliveryHeading)
                .splineToSplineHeading(coneStackPose, coneStackHeading)
                .build();
    }

    public void MakeTrajectoriesPark() {

        if (ButtonConfig.currentStartPosition == ButtonConfig.StartingPosition.RIGHT_SIDE) {

            if (Vision.currentSignal == AprilTagVision.Signal.LEFT) {
                PowerplayTrajectories.endAutoPosition = RIGHT_SIDE_LEFT_TILE_D4;
                relativeEndAutoPosition = FULL_TILE_DISTANCE_DRIVE;
            } else if (Vision.currentSignal == AprilTagVision.Signal.MIDDLE) {
                PowerplayTrajectories.endAutoPosition = RIGHT_SIDE_MIDDLE_TILE_D5;
                relativeEndAutoPosition = SIXTEENTH_TILE_DISTANCE_DRIVE;
            } else if (Vision.currentSignal == AprilTagVision.Signal.RIGHT) {
                PowerplayTrajectories.endAutoPosition = RIGHT_SIDE_RIGHT_TILE_D6;
                relativeEndAutoPosition = -FULL_TILE_DISTANCE_DRIVE;
            }

        } else {

            if (Vision.currentSignal == AprilTagVision.Signal.LEFT) {
                PowerplayTrajectories.endAutoPosition = LEFT_SIDE_LEFT_TILE_D1;
                relativeEndAutoPosition = -FULL_TILE_DISTANCE_DRIVE;
            } else if (Vision.currentSignal == AprilTagVision.Signal.MIDDLE) {
                PowerplayTrajectories.endAutoPosition = LEFT_SIDE_MIDDLE_TILE_D2;
                relativeEndAutoPosition = SIXTEENTH_TILE_DISTANCE_DRIVE;
            } else if (Vision.currentSignal == AprilTagVision.Signal.RIGHT) {
                PowerplayTrajectories.endAutoPosition = LEFT_SIDE_RIGHT_TILE_D3;
                relativeEndAutoPosition = FULL_TILE_DISTANCE_DRIVE;
            }
        }
        //----FIFTH CONE FROM CONE STACK DELIVERY SEQUENCE-------//
        //----AND PARKING-----//
        trajSeqSixMedPark = MecDrive.trajectorySequenceBuilder(currentPose)
                .back(7.5)
                .waitSeconds(.400)
                .UNSTABLE_addTemporalMarkerOffset(-.4, () -> {
                    Intake.turnIntakeOff();
                    Claw.closeClaw();
                })
                .UNSTABLE_addTemporalMarkerOffset(-.2, () -> {
                    Lift.StartLifting(firstJunctionHeight, Arm);
                })
                .setReversed(false)
                .splineToSplineHeading(new Pose2d(fifthJunction, firstJunctionHeading), firstJunctionHeading)
                .UNSTABLE_addTemporalMarkerOffset(-1, () -> {
                    Arm.setPosition(fifthJunctionArm);
                })
                .waitSeconds(.300)
                .UNSTABLE_addTemporalMarkerOffset(-.3, () -> {
                    Lift.StartLifting(firstJunctionHeight - 350, Arm);
                })
                .UNSTABLE_addTemporalMarkerOffset(-.1, () -> {
                    Claw.openClaw();
                })
                .UNSTABLE_addTemporalMarkerOffset(.3, () -> {
                    Arm.setPosition(org.firstinspires.ftc.teamcode.ObjectClasses.Arm.ARM_CENTER_INTAKE);
                })
                .UNSTABLE_addTemporalMarkerOffset(.8, () -> {
                    Lift.StartLifting(ONE_CONE_INTAKE_HEIGHT_ENC_VAL, Arm);
                    Claw.setEasyIntake();
                    Intake.turnIntakeOn();
                })
                //.back(7)
                //.setReversed(true)
                //.splineToLinearHeading(endAutoPosition, coneStackHeading)

                .back(10)
                .setReversed(true)
                .turn(relativeEndAutoHeading)
                .forward(relativeEndAutoPosition)
                .build();
    }
}


