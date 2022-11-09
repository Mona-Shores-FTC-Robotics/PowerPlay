package org.firstinspires.ftc.teamcode.ObjectClasses;

import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.CONE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FIVE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FOUR_CONE_STACK_INTAKE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.GROUND_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.LOW_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.ONE_CONE_INTAKE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.THREE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.TWO_CONE_STACK_INTAKE_HEIGHT_ENC_VAL;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Lift {

    //lift power parameters
    final double ABOVE_THRESHOLD_POWER = .8;
    final double ENCODER_THRESHOLD = 100;
    final double BELOW_THRESHOLD_POWER = 0;
    final int MAX_LIFT_HEIGHT = 1480;
    final int MIN_LIFT_HEIGHT = 0;
    final double LIFT_TARGET_MULTIPLIER = 10;

    LinearOpMode activeOpMode;
    public DcMotor liftMotor = null;
    public boolean alreadyLifting = false;
    public int newLiftTarget;

    public enum liftJunctionStates { HIGH_CONE_JUNCTION_SCORE_HEIGHT, MEDIUM_CONE_JUNCTION_SCORE_HEIGHT,
                            LOW_CONE_JUNCTION_SCORE_HEIGHT, GROUND_CONE_JUNCTION_SCORE_HEIGHT,
                            CONE_INTAKE_HEIGHT}

    public enum liftConeStackStates {   ONE_CONE_INTAKE_HEIGHT, TWO_CONE_STACK_INTAKE_HEIGHT,
                                        THREE_CONE_STACK_INTAKE_HEIGHT, FOUR_CONE_STACK_INTAKE_HEIGHT,
                                        FIVE_CONE_STACK_INTAKE_HEIGHT}

    public Lift.liftJunctionStates currentLiftJunctionState;
    public Lift.liftConeStackStates currentLiftConeStackState;

    public Lift(LinearOpMode mode){
        activeOpMode = mode;
    }

    public void init(HardwareMap ahwMap) {
        // Define and Initialize Motor
        liftMotor  = ahwMap.get(DcMotor.class, "lift_motor");
        liftMotor.setDirection(DcMotor.Direction.REVERSE);
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setPower(0);
        currentLiftJunctionState = liftJunctionStates.CONE_INTAKE_HEIGHT;
        currentLiftConeStackState = liftConeStackStates.ONE_CONE_INTAKE_HEIGHT;
    }

    public void StartLifting(double targetHeightEncVal) {
        if (activeOpMode.opModeIsActive()) {
            //begin lifting
            newLiftTarget = (int) (targetHeightEncVal);
            liftMotor.setTargetPosition(newLiftTarget);
            liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //lower the motor slowly if the target is below 500
            if (newLiftTarget < ENCODER_THRESHOLD) {
                liftMotor.setPower(0);
                currentLiftJunctionState = liftJunctionStates.CONE_INTAKE_HEIGHT;
                currentLiftConeStackState = liftConeStackStates.ONE_CONE_INTAKE_HEIGHT;

            } else {
                liftMotor.setPower(ABOVE_THRESHOLD_POWER);
            }
            alreadyLifting = true;
        }
    }

    public void ContinueLifting() {
        if (liftMotor.isBusy() == true) {
            //keep lifting because liftMotor is still trying to reach the target
        } else if (liftMotor.isBusy() == false) {
            //lift has reached target
            alreadyLifting = false;
        }
    }

    public void ManualLift(double liftTarget) {
        alreadyLifting = false;
        newLiftTarget = (int) ((liftTarget*LIFT_TARGET_MULTIPLIER) + newLiftTarget);
        if (liftTarget >0 && newLiftTarget > MAX_LIFT_HEIGHT) {newLiftTarget = MAX_LIFT_HEIGHT;}
        if (liftTarget <0 && newLiftTarget < MIN_LIFT_HEIGHT) {newLiftTarget = MIN_LIFT_HEIGHT;}
        liftMotor.setTargetPosition(newLiftTarget);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if (liftTarget < 0 && newLiftTarget < 100) {
            liftMotor.setPower(BELOW_THRESHOLD_POWER);
        } else if (liftTarget >0) {
            liftMotor.setPower(ABOVE_THRESHOLD_POWER);
        }
    }

    public void LowerLiftOneJunctionStage() {
        if (currentLiftJunctionState == liftJunctionStates.HIGH_CONE_JUNCTION_SCORE_HEIGHT) {
            currentLiftJunctionState = liftJunctionStates.MEDIUM_CONE_JUNCTION_SCORE_HEIGHT;
            StartLifting(MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL);
        } else if (currentLiftJunctionState == liftJunctionStates.MEDIUM_CONE_JUNCTION_SCORE_HEIGHT) {
            currentLiftJunctionState = liftJunctionStates.LOW_CONE_JUNCTION_SCORE_HEIGHT;
            StartLifting(LOW_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL);
        } else if (currentLiftJunctionState == liftJunctionStates.LOW_CONE_JUNCTION_SCORE_HEIGHT) {
            currentLiftJunctionState = liftJunctionStates.GROUND_CONE_JUNCTION_SCORE_HEIGHT;
            StartLifting(GROUND_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL);
        } else if (currentLiftJunctionState == liftJunctionStates.GROUND_CONE_JUNCTION_SCORE_HEIGHT) {
            currentLiftJunctionState = liftJunctionStates.CONE_INTAKE_HEIGHT;
            StartLifting(ONE_CONE_INTAKE_HEIGHT_ENC_VAL);
        } else if (currentLiftJunctionState == liftJunctionStates.CONE_INTAKE_HEIGHT) {
            currentLiftJunctionState = liftJunctionStates.CONE_INTAKE_HEIGHT;
            StartLifting(ONE_CONE_INTAKE_HEIGHT_ENC_VAL);
        }
    }

    public void RaiseLiftOneJunctionStage() {
        if (currentLiftJunctionState == liftJunctionStates.HIGH_CONE_JUNCTION_SCORE_HEIGHT) {
            currentLiftJunctionState = liftJunctionStates.HIGH_CONE_JUNCTION_SCORE_HEIGHT;
            StartLifting(HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL);
        } else if (currentLiftJunctionState == liftJunctionStates.MEDIUM_CONE_JUNCTION_SCORE_HEIGHT) {
            currentLiftJunctionState = liftJunctionStates.HIGH_CONE_JUNCTION_SCORE_HEIGHT;
            StartLifting(HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL);
        } else if (currentLiftJunctionState == liftJunctionStates.LOW_CONE_JUNCTION_SCORE_HEIGHT) {
            currentLiftJunctionState = liftJunctionStates.MEDIUM_CONE_JUNCTION_SCORE_HEIGHT;
            StartLifting(MEDIUM_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL);
        } else if (currentLiftJunctionState == liftJunctionStates.GROUND_CONE_JUNCTION_SCORE_HEIGHT) {
            currentLiftJunctionState = liftJunctionStates.LOW_CONE_JUNCTION_SCORE_HEIGHT;
            StartLifting(LOW_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL);
        } else if (currentLiftJunctionState == liftJunctionStates.CONE_INTAKE_HEIGHT) {
            currentLiftJunctionState = liftJunctionStates.GROUND_CONE_JUNCTION_SCORE_HEIGHT;
            StartLifting(GROUND_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL);
        }
    }

    public void LowerLiftOneConeStackStage() {
        if (currentLiftConeStackState == liftConeStackStates.FIVE_CONE_STACK_INTAKE_HEIGHT) {
            currentLiftConeStackState = liftConeStackStates.FOUR_CONE_STACK_INTAKE_HEIGHT;
            StartLifting(FOUR_CONE_STACK_INTAKE_HEIGHT_ENC_VAL);
        } else if (currentLiftConeStackState == liftConeStackStates.FOUR_CONE_STACK_INTAKE_HEIGHT) {
            currentLiftConeStackState = liftConeStackStates.THREE_CONE_STACK_INTAKE_HEIGHT;
            StartLifting(THREE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL);
        } else if (currentLiftConeStackState == liftConeStackStates.THREE_CONE_STACK_INTAKE_HEIGHT) {
            currentLiftConeStackState = liftConeStackStates.TWO_CONE_STACK_INTAKE_HEIGHT;
            StartLifting(TWO_CONE_STACK_INTAKE_HEIGHT_ENC_VAL);
        } else if (currentLiftConeStackState == liftConeStackStates.TWO_CONE_STACK_INTAKE_HEIGHT) {
            currentLiftConeStackState = liftConeStackStates.ONE_CONE_INTAKE_HEIGHT;
            StartLifting(ONE_CONE_INTAKE_HEIGHT_ENC_VAL);
        } else if (currentLiftConeStackState == liftConeStackStates.ONE_CONE_INTAKE_HEIGHT) {
            currentLiftConeStackState = liftConeStackStates.ONE_CONE_INTAKE_HEIGHT;
            StartLifting(ONE_CONE_INTAKE_HEIGHT_ENC_VAL);
        }
    }

    public void RaiseLiftOneConeStackStage() {
        if (currentLiftConeStackState == liftConeStackStates.ONE_CONE_INTAKE_HEIGHT) {
            currentLiftConeStackState = liftConeStackStates.TWO_CONE_STACK_INTAKE_HEIGHT;
            StartLifting(TWO_CONE_STACK_INTAKE_HEIGHT_ENC_VAL);
        } else if (currentLiftConeStackState == liftConeStackStates.TWO_CONE_STACK_INTAKE_HEIGHT) {
            currentLiftConeStackState = liftConeStackStates.THREE_CONE_STACK_INTAKE_HEIGHT;
            StartLifting(THREE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL);
        } else if (currentLiftConeStackState == liftConeStackStates.THREE_CONE_STACK_INTAKE_HEIGHT) {
            currentLiftConeStackState = liftConeStackStates.FOUR_CONE_STACK_INTAKE_HEIGHT;
            StartLifting(FOUR_CONE_STACK_INTAKE_HEIGHT_ENC_VAL);
        } else if (currentLiftConeStackState == liftConeStackStates.FOUR_CONE_STACK_INTAKE_HEIGHT) {
            currentLiftConeStackState = liftConeStackStates.FIVE_CONE_STACK_INTAKE_HEIGHT;
            StartLifting(FIVE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL);
        } else if (currentLiftConeStackState == liftConeStackStates.FIVE_CONE_STACK_INTAKE_HEIGHT) {
            currentLiftConeStackState = liftConeStackStates.FIVE_CONE_STACK_INTAKE_HEIGHT;
            StartLifting(FIVE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL);
        }
    }

    public void CheckLift(Boolean liftStageDownCurrentButton, Boolean liftStageDownPreivousButton,
                          Boolean liftStageUpCurrentButton, Boolean liftStageUpPreviousButton,
                          double manualLiftTargetChange) {
        if (manualLiftTargetChange != 0) {
            ManualLift(-manualLiftTargetChange);
        } else if (liftStageDownCurrentButton && !liftStageDownPreivousButton) {
            StartLifting(ONE_CONE_INTAKE_HEIGHT_ENC_VAL);
        } else if (liftStageUpCurrentButton && !liftStageUpPreviousButton) {
            StartLifting(HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL);
        } else if (alreadyLifting) {
            ContinueLifting();
        }
    }

    public void AdvancedCheckLift(  Boolean liftStageDownCurrentButton, Boolean liftStageDownPreivousButton,
                                    Boolean liftStageUpCurrentButton, Boolean liftStageUpPreviousButton,
                                    Boolean modifierButton,
                                    double manualLiftTargetChange) {
        if (manualLiftTargetChange != 0) {
            ManualLift(-manualLiftTargetChange);
        } else if (liftStageDownCurrentButton && !liftStageDownPreivousButton) {
            if (!modifierButton) {
                LowerLiftOneJunctionStage();
            } else if (modifierButton) {
                LowerLiftOneConeStackStage();
            }
        } else if (liftStageUpCurrentButton && !liftStageUpPreviousButton) {
            if (!modifierButton) {
                RaiseLiftOneJunctionStage();
            } else if (modifierButton) {
                RaiseLiftOneConeStackStage();
            }
        } else if (alreadyLifting) {
            ContinueLifting();
        }
    }



}
