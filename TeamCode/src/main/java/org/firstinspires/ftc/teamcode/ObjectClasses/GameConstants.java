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


    public static final double CONE_HEIGHT_ENC_VAL = 150;
    public static final double CONE_CLEARANCE_HEIGHT_ENC_VAL = CONE_HEIGHT_ENC_VAL / 2;
    public static final double CONE_GRIP_HEIGHT_ENC_VAL = 75;

    public static final double HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL = 1480;
    public static final double MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL = 1142;
    public static final double LOW_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL = 708;
    public static final double GROUND_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL = 131;
    public static final double TERMINAL_SCORE_HEIGHT_ENC_VAL = 0;

    public static final double ONE_CONE_INTAKE_HEIGHT_ENC_VAL = 0;
    public static final double TWO_CONE_STACK_INTAKE_HEIGHT_ENC_VAL = ONE_CONE_INTAKE_HEIGHT_ENC_VAL + CONE_GRIP_HEIGHT_ENC_VAL;
    public static final double THREE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL = TWO_CONE_STACK_INTAKE_HEIGHT_ENC_VAL + CONE_GRIP_HEIGHT_ENC_VAL;
    public static final double FOUR_CONE_STACK_INTAKE_HEIGHT_ENC_VAL = THREE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL + CONE_GRIP_HEIGHT_ENC_VAL;
    public static final double FIVE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL = FOUR_CONE_STACK_INTAKE_HEIGHT_ENC_VAL + CONE_GRIP_HEIGHT_ENC_VAL;
}
