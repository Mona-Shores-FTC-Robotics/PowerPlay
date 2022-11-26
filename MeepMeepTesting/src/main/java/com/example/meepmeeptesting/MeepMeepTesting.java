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

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(40, 30, Math.toRadians(254.96620790491366), Math.toRadians(60), 17.96)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(38, -60.3, Math.toRadians(90)))
                                .lineTo(new Vector2d(37.4, -38.8))
                                .splineToConstantHeading(new Vector2d(24.5, -24.5), Math.toRadians(90))
                                .waitSeconds(1)
                                .lineToSplineHeading(new Pose2d(45, -12, Math.toRadians(180)))
                                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}