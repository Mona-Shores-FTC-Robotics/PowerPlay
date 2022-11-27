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

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        if (startPosRIGHT) {
            startPose = new Pose2d(38, -60.3, Math.toRadians(90));
            //startPose = RIGHT_CONE_STACK_LINE;
            coneStackLine = RIGHT_CONE_STACK_LINE;
            coneStack = RIGHT_CONE_STACK_RIGHT;
            coneStackEndOfLine = RIGHT_CONE_STACK_END_OF_LINE;
            firstJunction = MEDIUM_JUNCTION_Y4;
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
            //startPose = LEFT_CONE_STACK_LINE;
            coneStackLine = LEFT_CONE_STACK_LINE;
            coneStack = LEFT_CONE_STACK_LEFT;
            coneStackEndOfLine = LEFT_CONE_STACK_END_OF_LINE;
            firstJunction = MEDIUM_JUNCTION_Y2;
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
                        drive.trajectorySequenceBuilder(coneStackLine)
                                //.forward(FULL_TILE_DISTANCE_DRIVE+HALF_TILE_DISTANCE_DRIVE)
                                //.strafeTo(firstJunction)
                                //.waitSeconds(.200)
                                //.lineToSplineHeading(coneStackLine)
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

