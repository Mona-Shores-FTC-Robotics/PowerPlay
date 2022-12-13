package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

//6 Medium
public class StartingLeg {

    public static final double FULL_TILE_DISTANCE_DRIVE= 23.5;
    public static final double HALF_TILE_DISTANCE_DRIVE = FULL_TILE_DISTANCE_DRIVE /2;
    public static final double QUARTER_TILE_DISTANCE_DRIVE = HALF_TILE_DISTANCE_DRIVE /2;
    public static final double EIGHTH_TILE_DISTANCE_DRIVE = QUARTER_TILE_DISTANCE_DRIVE /2;
    public static final double SIXTEENTH_TILE_DISTANCE_DRIVE = EIGHTH_TILE_DISTANCE_DRIVE /2;
    public static final double THIRTYSECOND_TILE_DISTANCE_DRIVE = SIXTEENTH_TILE_DISTANCE_DRIVE /2;
    public static final double SIXTYFOURTH_TILE_DISTANCE_DRIVE = THIRTYSECOND_TILE_DISTANCE_DRIVE /2;

    public static final double CONE_HEIGHT_ENC_VAL = 500;
    public static final double HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL = 2150;
    public static final double MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL = 1550;
    public static final double LOW_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL = 980;
    //public static final double GROUND_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL = 250;

    public static final double ONE_CONE_INTAKE_HEIGHT_ENC_VAL = 0;
    public static final double TWO_CONE_STACK_INTAKE_HEIGHT_ENC_VAL = 110;
    public static final double THREE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL = 170;
    public static final double FOUR_CONE_STACK_INTAKE_HEIGHT_ENC_VAL = 230;
    public static final double FIVE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL = 320;


    public static Vector2d MEDIUM_JUNCTION_Y4 = new Vector2d(FULL_TILE_DISTANCE_DRIVE + 5.5, -FULL_TILE_DISTANCE_DRIVE);
    public static Vector2d MEDIUM_JUNCTION_Y4_WITH_CONE = new Vector2d(FULL_TILE_DISTANCE_DRIVE+6, -1*(FULL_TILE_DISTANCE_DRIVE-6));

    public static Vector2d MEDIUM_JUNCTION_Y2 = new Vector2d(-(FULL_TILE_DISTANCE_DRIVE + 5.5), -(FULL_TILE_DISTANCE_DRIVE));
    public static Vector2d MEDIUM_JUNCTION_Y2_WITH_CONE = new Vector2d(-1*(FULL_TILE_DISTANCE_DRIVE+6), -1*(FULL_TILE_DISTANCE_DRIVE-6));

    public static Vector2d LOW_JUNCTION_Y5 = new Vector2d(47.2, -23.6);
    public static Vector2d LOW_JUNCTION_Y1 = new Vector2d(-47.2, -23.6);

    public static Vector2d HIGH_JUNCTION_X4 = new Vector2d(FULL_TILE_DISTANCE_DRIVE+3, -3);
    public static Vector2d HIGH_JUNCTION_X4_WITH_CONE = new Vector2d(FULL_TILE_DISTANCE_DRIVE+4, -4);

    public static Vector2d HIGH_JUNCTION_X2 = new Vector2d(-(FULL_TILE_DISTANCE_DRIVE+3),-3);
    public static Vector2d HIGH_JUNCTION_X2_WITH_CONE = new Vector2d(-(FULL_TILE_DISTANCE_DRIVE+4), -4);

    public static Pose2d RIGHT_SIDE_LEFT_TILE_D4 = new Pose2d(HALF_TILE_DISTANCE_DRIVE, -1*HALF_TILE_DISTANCE_DRIVE,Math.toRadians(90));
    public static Pose2d RIGHT_SIDE_MIDDLE_TILE_D5 = new Pose2d(HALF_TILE_DISTANCE_DRIVE+FULL_TILE_DISTANCE_DRIVE, -1*HALF_TILE_DISTANCE_DRIVE,Math.toRadians(90));
    public static Pose2d RIGHT_SIDE_RIGHT_TILE_D6 = new Pose2d(HALF_TILE_DISTANCE_DRIVE+(FULL_TILE_DISTANCE_DRIVE*2), -1*HALF_TILE_DISTANCE_DRIVE,Math.toRadians(180));

    public static Pose2d LEFT_SIDE_LEFT_TILE_D1 = new Pose2d(-1*(HALF_TILE_DISTANCE_DRIVE+FULL_TILE_DISTANCE_DRIVE*2), -1*HALF_TILE_DISTANCE_DRIVE,Math.toRadians(0));
    public static Pose2d LEFT_SIDE_MIDDLE_TILE_D2 = new Pose2d( -1*(HALF_TILE_DISTANCE_DRIVE+FULL_TILE_DISTANCE_DRIVE), -1*HALF_TILE_DISTANCE_DRIVE,Math.toRadians(90));
    public static Pose2d LEFT_SIDE_RIGHT_TILE_D3 =  new Pose2d( -1*HALF_TILE_DISTANCE_DRIVE, -1*HALF_TILE_DISTANCE_DRIVE,Math.toRadians(90));


    public static Vector2d RIGHT_CONE_STACK_RIGHT = new Vector2d(60, -HALF_TILE_DISTANCE_DRIVE);
    public static Vector2d LEFT_CONE_STACK_LEFT = new Vector2d(-60, -HALF_TILE_DISTANCE_DRIVE);
    public static Pose2d RIGHT_CONE_STACK_POSE = new Pose2d(FULL_TILE_DISTANCE_DRIVE*2+QUARTER_TILE_DISTANCE_DRIVE, -HALF_TILE_DISTANCE_DRIVE, Math.toRadians(180));
    public static Pose2d LEFT_CONE_STACK_POSE = new Pose2d(-(FULL_TILE_DISTANCE_DRIVE*2+QUARTER_TILE_DISTANCE_DRIVE), -HALF_TILE_DISTANCE_DRIVE, Math.toRadians(0));
    public static Vector2d RIGHT_CONE_STACK_END_OF_LINE = new Vector2d(FULL_TILE_DISTANCE_DRIVE+HALF_TILE_DISTANCE_DRIVE, -HALF_TILE_DISTANCE_DRIVE);
    public static Vector2d LEFT_CONE_STACK_END_OF_LINE = new Vector2d(-1*(FULL_TILE_DISTANCE_DRIVE+HALF_TILE_DISTANCE_DRIVE), -HALF_TILE_DISTANCE_DRIVE);

    public static Vector2d RIGHT_CONE_STACK_MIDDLE_OF_LINE = new Vector2d(FULL_TILE_DISTANCE_DRIVE*2+SIXTEENTH_TILE_DISTANCE_DRIVE, -HALF_TILE_DISTANCE_DRIVE);
    public static Vector2d LEFT_CONE_STACK_MIDDLE_OF_LINE = new Vector2d(-1*(FULL_TILE_DISTANCE_DRIVE*2+SIXTEENTH_TILE_DISTANCE_DRIVE), -HALF_TILE_DISTANCE_DRIVE);

    public static Pose2d RIGHT_CONE_STACK_LINE = new Pose2d(45, -HALF_TILE_DISTANCE_DRIVE, Math.toRadians(180));
    public static Pose2d LEFT_CONE_STACK_LINE = new Pose2d(-45, -HALF_TILE_DISTANCE_DRIVE, Math.toRadians(0));

    public static Vector2d RIGHT_STAGING_SPOT = new Vector2d(FULL_TILE_DISTANCE_DRIVE+HALF_TILE_DISTANCE_DRIVE+EIGHTH_TILE_DISTANCE_DRIVE, -(HALF_TILE_DISTANCE_DRIVE+THIRTYSECOND_TILE_DISTANCE_DRIVE));
    public static Vector2d LEFT_STAGING_SPOT = new Vector2d(-1*(FULL_TILE_DISTANCE_DRIVE+HALF_TILE_DISTANCE_DRIVE+EIGHTH_TILE_DISTANCE_DRIVE), -(HALF_TILE_DISTANCE_DRIVE+THIRTYSECOND_TILE_DISTANCE_DRIVE));


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

    public static StartingPosition currentStartPosition;

    public enum StartingPosition {
        LEFT_SIDE,
        RIGHT_SIDE,
        NOT_SET_YET
    }

    public enum Signal {LEFT, MIDDLE, RIGHT}

    //Set default to MIDDLE in case something goes wrong with vision
    public static Signal currentSignal;

    public static Vector2d stagingSpot;
    public static double lineupHeading;

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        currentStartPosition = StartingPosition.LEFT_SIDE;
        currentSignal = Signal.LEFT;

        if (currentStartPosition == StartingPosition.RIGHT_SIDE) {
            startPose = new Pose2d(FULL_TILE_DISTANCE_DRIVE+QUARTER_TILE_DISTANCE_DRIVE+EIGHTH_TILE_DISTANCE_DRIVE, -62, Math.toRadians(90));
            coneStackLine = RIGHT_CONE_STACK_LINE;
            coneStackPose = RIGHT_CONE_STACK_POSE;
            coneStack = RIGHT_CONE_STACK_MIDDLE_OF_LINE;
            coneStackEndOfLine = RIGHT_CONE_STACK_END_OF_LINE;
            coneStackMiddleOfLine = RIGHT_CONE_STACK_MIDDLE_OF_LINE;
            stagingSpot = RIGHT_STAGING_SPOT;

            coneStackHeading = Math.toRadians(0);
            startingJunctionTangent = Math.toRadians(180);
            firstJunctionHeading = Math.toRadians(225);
            lineupHeading =Math.toRadians(0);

            startingJunction = MEDIUM_JUNCTION_Y4;
            startingJunctionHeight = MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;

            firstJunction = MEDIUM_JUNCTION_Y4_WITH_CONE;
            firstJunctionHeight = MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;

            secondJunction = MEDIUM_JUNCTION_Y4_WITH_CONE;
            secondJunctionHeight = MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;

            thirdJunction = MEDIUM_JUNCTION_Y4_WITH_CONE;
            thirdJunctionHeight = MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;

            fourthJunction = MEDIUM_JUNCTION_Y4_WITH_CONE;
            fourthJunctionHeight = MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;

            fifthJunction = MEDIUM_JUNCTION_Y4_WITH_CONE;
            fifthJunctionHeight = MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;

            //startingJunctionArm = Arm.ARM_LEFT_OUTTAKE;
            //firstJunctionArm = Arm.ARM_FRONT_OUTTAKE;
            //secondJunctionArm = Arm.ARM_FRONT_OUTTAKE;
            //thirdJunctionArm = Arm.ARM_FRONT_OUTTAKE;
            //fourthJunctionArm = Arm.ARM_FRONT_OUTTAKE;
            //fifthJunctionArm = Arm.ARM_FRONT_OUTTAKE;

            if (currentSignal == Signal.LEFT) {
                endAutoPosition = RIGHT_SIDE_LEFT_TILE_D4;
            } else if (currentSignal == Signal.MIDDLE) {
                endAutoPosition = RIGHT_SIDE_MIDDLE_TILE_D5;
            } else if (currentSignal == Signal.RIGHT ) {
                endAutoPosition = RIGHT_SIDE_RIGHT_TILE_D6;
            }

        } else {
            startPose = new Pose2d(-(FULL_TILE_DISTANCE_DRIVE+QUARTER_TILE_DISTANCE_DRIVE+EIGHTH_TILE_DISTANCE_DRIVE), -62, Math.toRadians(90));
            coneStackLine = LEFT_CONE_STACK_LINE;
            coneStackEndOfLine = LEFT_CONE_STACK_END_OF_LINE;
            coneStackMiddleOfLine = LEFT_CONE_STACK_MIDDLE_OF_LINE;
            coneStackPose = LEFT_CONE_STACK_POSE;
            coneStackHeading = Math.toRadians(180);
            startingJunctionTangent = Math.toRadians(0);
            firstJunctionHeading = Math.toRadians(315);
            lineupHeading =Math.toRadians(135);


            stagingSpot = LEFT_STAGING_SPOT;

            startingJunction = MEDIUM_JUNCTION_Y2;

            startingJunctionHeight = MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
            firstJunction = MEDIUM_JUNCTION_Y2_WITH_CONE;
            firstJunctionHeight = MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;

            secondJunction = MEDIUM_JUNCTION_Y2_WITH_CONE;
            secondJunctionHeight = MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;

            thirdJunction = MEDIUM_JUNCTION_Y2_WITH_CONE;
            thirdJunctionHeight = MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;

            fourthJunction = MEDIUM_JUNCTION_Y2_WITH_CONE;
            fourthJunctionHeight = MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;

            fifthJunction = MEDIUM_JUNCTION_Y2_WITH_CONE;
            fifthJunctionHeight = MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;

//            startingJunctionArm = Arm.ARM_RIGHT_OUTTAKE;
//            firstJunctionArm = Arm.ARM_FRONT_OUTTAKE;
//            secondJunctionArm = Arm.ARM_FRONT_OUTTAKE;
//            thirdJunctionArm = Arm.ARM_FRONT_OUTTAKE;
//            fourthJunctionArm = Arm.ARM_FRONT_OUTTAKE;
//            fifthJunctionArm = Arm.ARM_FRONT_OUTTAKE;

            if (currentSignal == Signal.LEFT) {
                endAutoPosition = LEFT_SIDE_LEFT_TILE_D1;
            } else if (currentSignal == Signal.MIDDLE) {
                endAutoPosition = LEFT_SIDE_MIDDLE_TILE_D2;
            } else if (currentSignal == Signal.RIGHT) {
                endAutoPosition = LEFT_SIDE_RIGHT_TILE_D3;
            }
        }

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width`
                .setDimensions(15, 15.125)
                .setConstraints(40, 40, Math.toRadians(254.96620790491366), Math.toRadians(60), 17.96)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(startPose)
                                .addTemporalMarker(STARTING_CONE_LIFT_TIME, () -> {
//                                    Lift.StartLifting(startingJunctionHeight, Arm);
                                })
                                .UNSTABLE_addTemporalMarkerOffset(.8, () -> {
//                                    Arm.setPosition(startingJunctionArm);
                                })
                                .splineToConstantHeading(startingJunction, startingJunctionTangent)
                                .waitSeconds(.400)
                                .UNSTABLE_addTemporalMarkerOffset(-.4, () -> {
//                                    Lift.StartLifting(startingJunctionHeight - 325, Arm);
                                })
                                .UNSTABLE_addTemporalMarkerOffset(-.25, () -> {
//                                    Claw.openClaw();
                                })
                                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                                    Lift.StartLifting(startingJunctionHeight, Arm);
                                })
                                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
//                                    Arm.setPosition(org.firstinspires.ftc.teamcode.ObjectClasses.Arm.ARM_CENTER_INTAKE);
//                                    Lift.StartLifting(FIVE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL, Arm);
//                                    Claw.setEasyIntake();
//                                    Intake.turnIntakeOn();
                                })
                                .setReversed(false)
                                .setTangent(lineupHeading)
                                .splineToLinearHeading(new Pose2d(stagingSpot, Math.toRadians(0)), coneStackHeading)
                                .lineToLinearHeading(coneStackPose)
                                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}