package org.firstinspires.ftc.teamcode.ObjectClasses;

public final class GameConstants {

    private GameConstants() {
        // No need to instantiate the class, we can hide its constructor
    }

    public static final double FULL_TILE_DISTANCE = 25;
    public static final double HALF_TILE_DISTANCE = FULL_TILE_DISTANCE /2;
    public static final double QUARTER_TILE_DISTANCE = HALF_TILE_DISTANCE /2;
    public static final double EIGHTH_TILE_DISTANCE = QUARTER_TILE_DISTANCE /2;
    public static final double SIXTEENTH_TILE_DISTANCE = EIGHTH_TILE_DISTANCE /2;
    public static final double THIRTYSECOND_TILE_DISTANCE = SIXTEENTH_TILE_DISTANCE /2;
    public static final double SIXTYFOURTH_TILE_DISTANCE = THIRTYSECOND_TILE_DISTANCE /2;

    public static final double CONE_HEIGHT_ENC_VAL = 500;
    public static final double CONE_CLEARANCE_HEIGHT_ENC_VAL = CONE_HEIGHT_ENC_VAL / 2;
    public static final double CONE_GRIP_HEIGHT_ENC_VAL = 75;

    public static final double HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL = 3050;
    public static final double MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL = 2250;
    public static final double LOW_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL = 1600;
    public static final double GROUND_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL = 150;
    public static final double TERMINAL_SCORE_HEIGHT_ENC_VAL = 0;

    public static final double ONE_CONE_INTAKE_HEIGHT_ENC_VAL = 0;
    public static final double TWO_CONE_STACK_INTAKE_HEIGHT_ENC_VAL = 175;
    public static final double THREE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL = 325;
    public static final double FOUR_CONE_STACK_INTAKE_HEIGHT_ENC_VAL = 500;
    public static final double FIVE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL = 625;
}
