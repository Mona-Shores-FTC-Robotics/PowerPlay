package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static final double FULL_TILE_DISTANCE_DRIVE= 23.5;
    public static final double HALF_TILE_DISTANCE_DRIVE = FULL_TILE_DISTANCE_DRIVE /2;
    public static final double QUARTER_TILE_DISTANCE_DRIVE = HALF_TILE_DISTANCE_DRIVE /2;
    public static final double EIGHTH_TILE_DISTANCE_DRIVE = QUARTER_TILE_DISTANCE_DRIVE /2;
    public static final double SIXTEENTH_TILE_DISTANCE_DRIVE = EIGHTH_TILE_DISTANCE_DRIVE /2;
    public static final double THIRTYSECOND_TILE_DISTANCE_DRIVE = SIXTEENTH_TILE_DISTANCE_DRIVE /2;
    public static final double SIXTYFOURTH_TILE_DISTANCE_DRIVE = THIRTYSECOND_TILE_DISTANCE_DRIVE /2;

    public static Vector2d MEDIUM_JUNCTION_Y4 = new Vector2d(FULL_TILE_DISTANCE_DRIVE+3, -FULL_TILE_DISTANCE_DRIVE);
    public static Vector2d MEDIUM_JUNCTION_Y2 = new Vector2d(-(FULL_TILE_DISTANCE_DRIVE+3), -FULL_TILE_DISTANCE_DRIVE);

    public static Vector2d LOW_JUNCTION_Y5 = new Vector2d(FULL_TILE_DISTANCE_DRIVE*2, -(FULL_TILE_DISTANCE_DRIVE-3));
    public static Vector2d LOW_JUNCTION_Y1 = new Vector2d(-(FULL_TILE_DISTANCE_DRIVE*2), -(FULL_TILE_DISTANCE_DRIVE-3));

    public static Vector2d HIGH_JUNCTION_X4 = new Vector2d(FULL_TILE_DISTANCE_DRIVE, 0);
    public static Vector2d HIGH_JUNCTION_X2 = new Vector2d(-FULL_TILE_DISTANCE_DRIVE, 0);

    public static Pose2d RIGHT_SIDE_LEFT_TILE_D4 = new Pose2d(HALF_TILE_DISTANCE_DRIVE, -HALF_TILE_DISTANCE_DRIVE,Math.toRadians(90));
    public static Pose2d RIGHT_SIDE_MIDDLE_TILE_D5 = new Pose2d(HALF_TILE_DISTANCE_DRIVE+FULL_TILE_DISTANCE_DRIVE, -HALF_TILE_DISTANCE_DRIVE,Math.toRadians(90));
    public static Pose2d RIGHT_SIDE_RIGHT_TILE_D6 = new Pose2d(HALF_TILE_DISTANCE_DRIVE+(FULL_TILE_DISTANCE_DRIVE*2), -HALF_TILE_DISTANCE_DRIVE,Math.toRadians(180));

    public static Pose2d LEFT_SIDE_LEFT_TILE_D1 = new Pose2d(-(HALF_TILE_DISTANCE_DRIVE+(FULL_TILE_DISTANCE_DRIVE*2)), -HALF_TILE_DISTANCE_DRIVE,Math.toRadians(180));
    public static Pose2d LEFT_SIDE_MIDDLE_TILE_D2 = new Pose2d( -(HALF_TILE_DISTANCE_DRIVE+FULL_TILE_DISTANCE_DRIVE), -HALF_TILE_DISTANCE_DRIVE,Math.toRadians(90));
    public static Pose2d LEFT_SIDE_RIGHT_TILE_D3 =  new Pose2d( -HALF_TILE_DISTANCE_DRIVE, -HALF_TILE_DISTANCE_DRIVE,Math.toRadians(90));


    public static Vector2d RIGHT_CONE_STACK_RIGHT = new Vector2d(60, -12);
    public static Vector2d LEFT_CONE_STACK_LEFT = new Vector2d(-60, -12);
    public static Vector2d RIGHT_CONE_STACK_END_OF_LINE = new Vector2d(47.2, -12);
    public static Vector2d LEFT_CONE_STACK_END_OF_LINE = new Vector2d(-47.2, -12);
    public static Pose2d RIGHT_CONE_STACK_LINE = new Pose2d(45, -12, Math.toRadians(180));
    public static Pose2d LEFT_CONE_STACK_LINE = new Pose2d(-45, -12, Math.toRadians(0));


    public static Vector2d RIGHT_LINEUP_TILE_VECTOR = new Vector2d(FULL_TILE_DISTANCE_DRIVE+HALF_TILE_DISTANCE_DRIVE, -12);

    public static Pose2d RIGHT_LINEUP_TILE = new Pose2d(FULL_TILE_DISTANCE_DRIVE*2, -12, Math.toRadians(180));
    public static Pose2d LEFT_LINEUP_TILE = new Pose2d(-(FULL_TILE_DISTANCE_DRIVE+HALF_TILE_DISTANCE_DRIVE+QUARTER_TILE_DISTANCE_DRIVE), -12, Math.toRadians(0));

    public static Pose2d coneStackPose;


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
    public static double STARTING_CONE_ARM_TIME = STARTING_CONE_LIFT_TIME + 1.1;
    // Lift@Dunk
    public static double STARTING_CONE_DUNK_TIME = STARTING_CONE_ARM_TIME + 1.1;
    // Open Claw
    public static double STARTING_CONE_DELIVER_TIME = STARTING_CONE_DUNK_TIME + .1;
    // Lift@Undunk
    public static double STARTING_CONE_UNDUNK_TIME = STARTING_CONE_DELIVER_TIME + .1;


    // Arm Center, Lift@Pickup, Claw Easy, Intake On;
    public static double FIRST_CONE_APPROACH_TIME = 4.8;
    // Intake Off, Close Claw, Lift@+200
    public static double FIRST_CONE_GRAB_TIME = FIRST_CONE_APPROACH_TIME + 1.5;
    // Lift@Deliver, Rotate Arm
    public static double FIRST_CONE_DELIVERY_SETUP_TIME = FIRST_CONE_GRAB_TIME + 1.1;
    // Lift@Dunk
    public static double FIRST_CONE_DUNK_TIME = FIRST_CONE_DELIVERY_SETUP_TIME + 1;
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

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        if (startPosRIGHT) {
            startPose = new Pose2d(38, -60.3, Math.toRadians(90));
            coneStackLine = RIGHT_CONE_STACK_LINE;
            coneStack = RIGHT_CONE_STACK_RIGHT;
            coneStackEndOfLine = RIGHT_CONE_STACK_END_OF_LINE;
            startingJunction = MEDIUM_JUNCTION_Y4;

            firstJunction = LOW_JUNCTION_Y5;

            secondJunction = LOW_JUNCTION_Y5;

            thirdJunction = LOW_JUNCTION_Y5;

            fourthJunction = LOW_JUNCTION_Y5;

            fifthJunction = LOW_JUNCTION_Y5;


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
            startingJunction = MEDIUM_JUNCTION_Y2;
            firstJunction = LOW_JUNCTION_Y1;

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

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width`
                .setConstraints(40, 30, Math.toRadians(254.96620790491366), Math.toRadians(60), 17.96)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(startPose)
                                .splineToConstantHeading(startingJunction,Math.toRadians(180))
                                .waitSeconds(.200)
                                .setTangent(Math.toRadians(0))
                                .splineToSplineHeading(RIGHT_LINEUP_TILE, Math.toRadians(0))
                                .splineToSplineHeading(coneStackPose, Math.toRadians(0))
                                .waitSeconds(.200)
                                .setReversed(false)
                                .splineToConstantHeading(firstJunction, Math.toRadians(90))
                                .waitSeconds(.200)
                                .setReversed(true)
                                .splineToConstantHeading(RIGHT_LINEUP_TILE_VECTOR, Math.toRadians(0))
                                .splineToConstantHeading(coneStack, Math.toRadians(0))
                                .waitSeconds(.200)
                                .setReversed(false)
                                .splineToConstantHeading(secondJunction, Math.toRadians(90))
                                .addTemporalMarker(STARTING_CONE_LIFT_TIME, () -> {
                                    //Lift.StartLifting(startingJunctionHeight, Arm);
                                })
                                .addTemporalMarker(STARTING_CONE_ARM_TIME, () -> {
                                    //Arm.setPosition(startingJunctionArm);
                                })
                                .addTemporalMarker(STARTING_CONE_DUNK_TIME, () -> {
                                    //Lift.StartLifting(startingJunctionHeight - 300, Arm);
                                })
                                .addTemporalMarker(STARTING_CONE_DELIVER_TIME, () -> {
                                    //Claw.openClaw();
                                })
                                .addTemporalMarker(STARTING_CONE_UNDUNK_TIME, () -> {
                                    //Lift.StartLifting(startingJunctionHeight, Arm);
                                })
                                .addTemporalMarker(FIRST_CONE_APPROACH_TIME, () -> {
//                                    Arm.setPosition(org.firstinspires.ftc.teamcode.ObjectClasses.Arm.ARM_CENTER_INTAKE);
//                                    Lift.StartLifting(FIVE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL, Arm);
//                                    Claw.setEasyIntake();
//                                    Intake.turnIntakeOn();
                                })
                                .addTemporalMarker(FIRST_CONE_GRAB_TIME, () -> {
//                                    Intake.turnIntakeOff();
//                                    Claw.closeClaw();
//                                    Lift.StartLifting(firstJunctionHeight, Arm);
                                })
                                .addTemporalMarker(FIRST_CONE_DELIVERY_SETUP_TIME, () -> {
//                                    Arm.setPosition(firstJunctionArm);
                                })
                                .addTemporalMarker(FIRST_CONE_DUNK_TIME, () -> {
//                                    Lift.StartLifting(firstJunctionHeight - 300, Arm);
                                })
                                .addTemporalMarker(FIRST_CONE_DELIVER_TIME, () -> {
//                                    Claw.openClaw();
                                })
                                .addTemporalMarker(FIRST_CONE_UNDUNK_TIME, () -> {
//                                    Lift.StartLifting(firstJunctionHeight, Arm);
                                })
                                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}

// 31 SECONDS
//  drive.trajectorySequenceBuilder(startPose)
//                                .forward(FULL_TILE_DISTANCE_DRIVE+HALF_TILE_DISTANCE_DRIVE)
//                                .strafeTo(firstJunction)
//                                .waitSeconds(.200)
//                                .lineToSplineHeading(coneStackLine)
//                                .lineTo(coneStack)
//                                .waitSeconds(.200)
//                                .lineTo(coneStackEndOfLine)
//                                .strafeTo(secondJunction)
//                                .lineTo(coneStackEndOfLine)
//                                .lineTo(coneStack)
//                                .waitSeconds(.200)
//                                .lineTo(coneStackEndOfLine)
//                                .strafeTo(thirdJunction)
//                                .lineTo(coneStackEndOfLine)
//                                .lineTo(coneStack)
//                                .waitSeconds(.200)
//                                .lineTo(coneStackEndOfLine)
//                                .strafeTo(fourthJunction)
//                                .lineTo(coneStackEndOfLine)
//                                .lineTo(coneStack)
//                                .waitSeconds(.200)
//                                .lineTo(coneStackEndOfLine)
//                                .splineToSplineHeading(new Pose2d(fifthJunction, Math.toRadians(135)), Math.toRadians(135))
//                                .back(15)
//                                .lineToLinearHeading(new Pose2d(coneStackEndOfLine, Math.toRadians(180)))

