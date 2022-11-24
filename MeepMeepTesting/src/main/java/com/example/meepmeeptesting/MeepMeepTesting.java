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
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(37, -61.5, Math.toRadians(90)))
                                .lineTo(new Vector2d(33, -24))
                                .addTemporalMarker(.1, () -> {
                                 //startLifting
                                })
                                .addTemporalMarker(1, () ->{
                                 //rotate arm
                                })
                                .waitSeconds(.5)
                                .addDisplacementMarker(() -> {
                                    //lower lift
                                    //drop cone
                                    //lift to 5 cone height
                                })
                                .splineToSplineHeading(new Pose2d(54, -12, Math.toRadians(180)), Math.toRadians(5))
                                .addDisplacementMarker(() -> {
                                    //intakeOn
                                })
                                .lineToLinearHeading(new Pose2d(62, -12, Math.toRadians(180)))
                                .addDisplacementMarker(() -> {
                                   //raise lift to pickup cone
                                })
                                .waitSeconds(.3)
                                .splineToConstantHeading(new Vector2d(48, -14), Math.toRadians(-45))
                                .addDisplacementMarker(() -> {
                                    //lower lift
                                    //drop cone
                                    //lift to 4 cone height
                                })
                                .waitSeconds(.3)
                                .splineToConstantHeading(new Vector2d(62, -12), Math.toRadians(-20))
                                .addDisplacementMarker(() -> {
                                    //intakeOn
                                    //raise lift to pickup cone
                                })
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}