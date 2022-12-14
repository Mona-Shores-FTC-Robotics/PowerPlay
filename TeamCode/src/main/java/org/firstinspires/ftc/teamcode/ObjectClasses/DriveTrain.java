package org.firstinspires.ftc.teamcode.ObjectClasses;/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.EIGHTH_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FULL_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FULL_TILE_DISTANCE_STRAFE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.HALF_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.HALF_TILE_DISTANCE_STRAFE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.LOW_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.QUARTER_TILE_DISTANCE_DRIVE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.QUARTER_TILE_DISTANCE_STRAFE;

import static java.lang.Math.abs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class DriveTrain {

    //DriveTrain Constants
    public static final double STARTING_DRIVE_MULTIPLIER = .5;
    public double MINMULT = .5;
    public double MAXMULT = 1;

    //motor and wheel parameters
    final double TICKS_PER_REV = 537.7;
    final double DRIVE_GEAR_REDUCTION = 1;
    final double WHEEL_DIAMETER_INCHES = 3.93701;
    double COUNTS_PER_INCH = (TICKS_PER_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);

    //Speed Constants
    public static final double LOW_SPEED = .2;
    public static final double MED_SPEED = .6;
    public static final double HIGH_SPEED = .9;
    public static final double STARTING_RAMP_VALUE = .03;
    public static final double RAMP_INCREMENT = .02;

    public int TURNING_TIMEOUT_IN_SECONDS = 2;

    /* Public OpMode members. */
    public DcMotor LFDrive = null;
    public DcMotor RFDrive = null;
    public DcMotor LBDrive = null;
    public DcMotor RBDrive = null;
    public double leftFrontPower = 0;
    public double rightFrontPower = 0;
    public double leftBackPower = 0;
    public double rightBackPower = 0;
    public double drive = 0;
    public double strafe = 0;
    public double turn = 0;


    public ColorSensor colorSensor;

    public double topSpeed;
    public double multiplier = STARTING_DRIVE_MULTIPLIER;
    public double ramp = STARTING_RAMP_VALUE;

    //state machine members
    public boolean alreadyDriving = false;
    public boolean alreadyStrafing = false;
    public boolean visionStrafing = false;
    public boolean alreadyLineFollowing = false;
    public boolean autoDeliver1 = false;
    public boolean autoDeliver2 = false;

    int encoderTargetLineFollow;

    public LinearOpMode activeOpMode;
    HardwareMap hwMap = null;

    //Turn Related Variables
    public double degreesLeftToTurn;
    public double targetAngleInDegrees;
    public TurnPIDController2 pid = new TurnPIDController2(0, 0, 0, 0, 0);
    public boolean alreadyTurning = false;
    public boolean alreadyPIDTurning = false;
    public double targetAngle;

    private final ElapsedTime drivePeriod = new ElapsedTime();
    private final ElapsedTime strafePeriod = new ElapsedTime();
    public final ElapsedTime colorTimer = new ElapsedTime();
    public final ElapsedTime turningTimer = new ElapsedTime();



    public enum autoDeliverStates {
        FIRST_STEP,
        START_AUTOMATIC_DELIVER1,
        START_AUTOMATIC_DELIVER2,
        DRIVE_FROM_ALLIANCE_SUBSTATION,
        STRAFE_TO_POLE,
        DELIVER_CONE,
        STRAFE_AWAY_FROM_POLE,
        DRIVE_TO_ALLIANCE_SUBSTATION,
        INTAKE_CONE,
        DRIVE_TO_OUTSIDE_ALLIANCE_SUBSTATION,
        END_AUTOMATIC_DELIVER
    }

    public boolean manualDriving = false;

    public autoDeliverStates currentAutomaticTask;

    /* Constructor */
    public DriveTrain(LinearOpMode mode) {
        activeOpMode = mode;
    }

    /* Initialize Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        LFDrive = ahwMap.get(DcMotor.class, "LFDrive");
        RFDrive = ahwMap.get(DcMotor.class, "RFDrive");
        LBDrive = ahwMap.get(DcMotor.class, "LBDrive");
        RBDrive = ahwMap.get(DcMotor.class, "RBDrive");

        LFDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RFDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LBDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RBDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        LFDrive.setDirection(DcMotor.Direction.REVERSE);
        RFDrive.setDirection(DcMotor.Direction.FORWARD);
        LBDrive.setDirection(DcMotor.Direction.REVERSE);
        RBDrive.setDirection(DcMotor.Direction.FORWARD);

        LFDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RFDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LBDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RBDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Set all motors to zero power
        LFDrive.setPower(0);
        RFDrive.setPower(0);
        LBDrive.setPower(0);
        RBDrive.setPower(0);

        colorSensor = hwMap.colorSensor.get("color_sensor");
    }

    public void CheckManualDriveControls(float driveStick, float strafeStick, float turnStick, float fineTuneLeftTurn, float fineTuneRightTurn) {
        //Driver controls have first priority
        if (driveStick != 0 || Math.abs(strafeStick) > .2 || turnStick != 0 || fineTuneLeftTurn > 0 || fineTuneRightTurn > 0) {
            alreadyStrafing = false;
            alreadyDriving = false;
            alreadyTurning = false;
            alreadyPIDTurning = false;
            visionStrafing = false;
            manualDriving = true;
            autoDeliver1 = false;
            autoDeliver2 = false;

            //Strafe deadzone - don't strafe unless stick is pushed to at least .2 or -.2
            if (Math.abs(strafeStick) <= .2) {
                strafeStick = 0;
            }
            drive = -driveStick; //-1.0 to 1.0
            strafe = strafeStick; //-1.0 to 1.0
            turn = -turnStick; //-1.0 to 1.0

            //Use fine tune values from triggers instead of right stick if Driver is pressing one of them
            if (fineTuneLeftTurn > .1) {
                turn = fineTuneLeftTurn * .3; //-1.0 to 1.0
            } else if (fineTuneRightTurn > .1) {
                turn = -fineTuneRightTurn * .3;
            }
            MecanumDrive();
        }
    }

    public void CheckDpadDriveControls(boolean dpad_up, boolean dpad_right, boolean dpad_down, boolean dpad_left,
                                       boolean lastDpad_up, boolean lastDpad_right, boolean lastDpad_down, boolean lastDpad_left,
                                       boolean changeFunction) {
        if (dpad_right && !lastDpad_right) {
            if (changeFunction) {
                startStrafeDrive(HIGH_SPEED, FULL_TILE_DISTANCE_STRAFE);
            } else {
                startStrafeDrive(HIGH_SPEED, HALF_TILE_DISTANCE_STRAFE);
            }
        } else if (dpad_down && !lastDpad_down) {
            if (changeFunction) {
                startEncoderDrive(HIGH_SPEED, -FULL_TILE_DISTANCE_DRIVE);
            } else {
                startEncoderDrive(HIGH_SPEED, -HALF_TILE_DISTANCE_DRIVE);
            }
        } else if (dpad_left && !lastDpad_left) {
            if (changeFunction) {
                startStrafeDrive(MED_SPEED, -FULL_TILE_DISTANCE_STRAFE);
            } else {
                startStrafeDrive(MED_SPEED, -HALF_TILE_DISTANCE_STRAFE);
            }
        } else if (dpad_up && !lastDpad_up) {
            if (changeFunction) {
                startEncoderDrive(HIGH_SPEED, FULL_TILE_DISTANCE_DRIVE);
            } else {
                startEncoderDrive(HIGH_SPEED, HALF_TILE_DISTANCE_DRIVE);
            }
        }
    }


    public void CheckVisionStrafing(boolean button, boolean lastButton) {
        if (button && lastButton) {
            visionStrafing = true;
        }
    }

    public void CheckAutoTowardAllianceSubstation(boolean button, boolean lastButton) {
        if (button && lastButton) {
            //move from alliance substation to scoring position
            startEncoderDrive(MED_SPEED, -(FULL_TILE_DISTANCE_DRIVE*2 + HALF_TILE_DISTANCE_DRIVE + EIGHTH_TILE_DISTANCE_DRIVE));
        }
    }

    public void CheckAutoFromAllianceStation(boolean button, boolean lastButton) {
        if (button && lastButton) {
            //move from alliance substation to scoring position
            startEncoderDrive(MED_SPEED, (FULL_TILE_DISTANCE_DRIVE*2 + HALF_TILE_DISTANCE_DRIVE + EIGHTH_TILE_DISTANCE_DRIVE));
        }
    }



    public void CheckSquareTurning(boolean button1, boolean lastButton1, boolean button2, boolean lastButton2, boolean button3, boolean lastButton3, Gyro Gyro) {
        if (button1 && !lastButton1) {
            turnToPID(-90, Gyro);
        } else if (button2 && !lastButton2) {
            turnToPID(90, Gyro);
        } else if (button3 && !lastButton3) {
            turnToPID(0, Gyro);
        }
    }

    public void CheckAutoDeliver(boolean selectButton, boolean lastSelectButton,
                                 boolean startButton, boolean lastStartButton) {
        if (selectButton && !lastSelectButton) {
            autoDeliver1 = true;
            currentAutomaticTask = autoDeliverStates.START_AUTOMATIC_DELIVER1;
        }
        if (startButton && !lastStartButton) {
            autoDeliver2 = true;
            currentAutomaticTask = autoDeliverStates.START_AUTOMATIC_DELIVER2;
        }
    }

    public void ContinueAutomaticTasks(Gyro Gyro, PipeVision AutoVision, Arm ServoArm, Lift Lift, Claw ServoClaw, Intake ServoIntake) {
        if (visionStrafing) {
            VisionStrafing(AutoVision);
        } else if (alreadyDriving) {
            ContinueDriving();
        } else if (alreadyStrafing) {
            ContinueStrafing();
        } else if (alreadyTurning) {
            ContinueTurning(Gyro);
        } else if (alreadyPIDTurning) {
            ContinuePIDTurning(Gyro);
        } else if (autoDeliver1) {
            auto_deliver1(ServoArm, Lift, ServoClaw, ServoIntake);
        } else if (autoDeliver2) {
            auto_deliver2(ServoArm, Lift, ServoClaw, ServoIntake);
        }
    }

    //have this here just in case we have to take vision out of the code so we can still call this method
    public void ContinueAutomaticTasks(Gyro Gyro, Arm ServoArm, Lift Lift, Claw ServoClaw, Intake ServoIntake) {
        if (alreadyTurning) {
            ContinueTurning(Gyro);
        } else if (alreadyPIDTurning) {
            ContinuePIDTurning(Gyro);
        } else if (alreadyDriving) {
            ContinueDriving();
        } else if (alreadyStrafing) {
            ContinueStrafing();
        } else if (autoDeliver1) {
            auto_deliver1(ServoArm, Lift, ServoClaw, ServoIntake);
        } else if (autoDeliver2) {
            auto_deliver2(ServoArm, Lift, ServoClaw, ServoIntake);
        }
    }

    public void CheckNoManualDriveControls(float driveStick, float strafeStick, float turnStick, float fineTuneLeftTurn, float fineTuneRightTurn) {
        if (driveStick == 0 && strafeStick == 0 && turnStick == 0 && fineTuneLeftTurn < .1 && fineTuneRightTurn < .1 &&
                !visionStrafing && !alreadyDriving && !alreadyStrafing && !alreadyPIDTurning && !alreadyTurning && !autoDeliver1 && !autoDeliver2) {
            drive = 0;
            strafe = 0;
            turn = 0;
            MecanumDrive();
            manualDriving = false;
        }
    }

    private void RotateClosestRightAngleToLeft(Gyro Gyro) {
        double currentAngle = Gyro.getAbsoluteAngle();


        if (currentAngle < 0 && currentAngle > -85) {
            turnToPID(0, Gyro);
        }

        if (currentAngle >= 5 && currentAngle < 90) {
            turnToPID(90, Gyro);
        }

        if (currentAngle < -90 && currentAngle >= -175) {
            turnToPID(-90, Gyro);
        }

        if (currentAngle >= 95 && currentAngle < 180) {
            turnToPID(180, Gyro);
        }

    }

    private void RotateClosestRightAngleToRight(Gyro Gyro) {
        double currentAngle = Gyro.getAbsoluteAngle();

        if (currentAngle < -5 && currentAngle >= -90) {
            turnToPID(-90, Gyro);
        }

        if (currentAngle < -95 && currentAngle >= -180) {
            turnToPID(-180, Gyro);
        }

        if (currentAngle < 175 && currentAngle >= 90) {
            turnToPID(90, Gyro);
        }

        if (currentAngle < 85 && currentAngle >= 0) {
            turnToPID(0, Gyro);
        }

    }

    public void VisionStrafing(PipeVision AutoVision) {
        activeOpMode.telemetry.addLine("Seeking POLE with VISION");
        if (AutoVision.pipeDetectionPipeline.isPoleCenter()) {
            //stop moving
            activeOpMode.telemetry.addLine("PIPE CENTER");
            turn = 0;
            drive = 0;
            strafe = 0;
            MecanumDrive();
            visionStrafing = false;
        } else if (AutoVision.pipeDetectionPipeline.isPoleLeft()) {
            //strafe left
            activeOpMode.telemetry.addLine("PIPE LEFT");
            turn = 0;
            drive = 0;
            strafe = .2;
            MecanumDrive();
        } else if (AutoVision.pipeDetectionPipeline.isPoleRight()) {
            //strafe right
            activeOpMode.telemetry.addLine("PIPE RIGHT");
            turn = 0;
            drive = 0;
            strafe = .2;
            MecanumDrive();
        } else {
            activeOpMode.telemetry.addLine("UH OH DON'T SEE PIPE");
        }
    }

    //Set power to all motors
    public void setAllPower(double p) {
        setMotorPower(p, p, p, p);
    }

    public void setMotorPower(double lF, double rF, double lB, double rB) {
        LFDrive.setPower(lF * multiplier);
        RFDrive.setPower(rF * multiplier);
        LBDrive.setPower(lB * multiplier);
        RBDrive.setPower(rB * multiplier);
    }

    public void MecanumDrive() {
        LFDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RFDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        LBDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RBDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Put Mecanum Drive math and motor commands here.
        double dPercent = Math.abs(drive) / (Math.abs(drive) + Math.abs(strafe) + Math.abs(turn));
        double sPercent = Math.abs(strafe) / (Math.abs(drive) + Math.abs(turn) + Math.abs(strafe));
        double tPercent = Math.abs(turn) / (Math.abs(drive) + Math.abs(turn) + Math.abs(strafe));

        rightFrontPower = (drive * dPercent) + (-strafe * sPercent) + (turn * tPercent);
        rightBackPower = (drive * dPercent) + (strafe * sPercent) + (turn * tPercent);
        leftFrontPower = (drive * dPercent) + (strafe * sPercent) + (-turn * tPercent);
        leftBackPower = (drive * dPercent) + (-strafe * sPercent) + (-turn * tPercent);

        if (!Double.isNaN(leftFrontPower) && !Double.isNaN(rightFrontPower) && !Double.isNaN(leftBackPower) && !Double.isNaN(rightBackPower)) {
            LFDrive.setPower(leftFrontPower);
            RFDrive.setPower(rightFrontPower);
            LBDrive.setPower(leftBackPower);
            RBDrive.setPower(rightBackPower);
        } else {
            LFDrive.setPower(0);
            RFDrive.setPower(0);
            LBDrive.setPower(0);
            RBDrive.setPower(0);
        }

    }

    public void startEncoderDrive(double speed, double leftInches, double rightInches) {
        topSpeed = speed;
        if (activeOpMode.opModeIsActive() && alreadyDriving == false) {

            LFDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RFDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            LBDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RBDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            LFDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RFDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            LBDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RBDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            int newLeftFrontTarget = (int) (leftInches * COUNTS_PER_INCH);
            int newRightFrontTarget = (int) (rightInches * COUNTS_PER_INCH);
            int newLeftBackTarget = (int) (leftInches * COUNTS_PER_INCH);
            int newRightBackTarget = (int) (rightInches * COUNTS_PER_INCH);

            LFDrive.setTargetPosition(newLeftFrontTarget);
            RFDrive.setTargetPosition(newRightFrontTarget);
            LBDrive.setTargetPosition(newLeftBackTarget);
            RBDrive.setTargetPosition(newRightBackTarget);

            LFDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RFDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LBDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RBDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //track how much time we have been driving
            drivePeriod.reset();

            //reset starting ramp value
            ramp = STARTING_RAMP_VALUE;

            RFDrive.setPower(abs(ramp));
            LFDrive.setPower(abs(ramp));
            LBDrive.setPower(abs(ramp));
            RBDrive.setPower(abs(ramp));

            //we are now driving
            alreadyDriving = true;
        }
    }

    public void startEncoderDrive(double speed, double distanceInches) {
        topSpeed = speed;
        if (activeOpMode.opModeIsActive() && alreadyDriving == false) {

            LFDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RFDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            LBDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RBDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            LFDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RFDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            LBDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RBDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            int newLeftFrontTarget = (int) (distanceInches * COUNTS_PER_INCH);
            int newRightFrontTarget = (int) (distanceInches * COUNTS_PER_INCH);
            int newLeftBackTarget = (int) (distanceInches * COUNTS_PER_INCH);
            int newRightBackTarget = (int) (distanceInches * COUNTS_PER_INCH);

            LFDrive.setTargetPosition(newLeftFrontTarget);
            RFDrive.setTargetPosition(newRightFrontTarget);
            LBDrive.setTargetPosition(newLeftBackTarget);
            RBDrive.setTargetPosition(newRightBackTarget);

            LFDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RFDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LBDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RBDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //track how much time we have been driving
            drivePeriod.reset();

            //reset starting ramp value
            ramp = STARTING_RAMP_VALUE;

            RFDrive.setPower(abs(ramp));
            LFDrive.setPower(abs(ramp));
            LBDrive.setPower(abs(ramp));
            RBDrive.setPower(abs(ramp));

            //we are now driving
            alreadyDriving = true;
        }
    }

    public void ContinueDriving() {
        if (activeOpMode.opModeIsActive() &&
                alreadyDriving == true &&
                (RFDrive.isBusy() || LFDrive.isBusy() || LBDrive.isBusy() || RBDrive.isBusy())) {
            RFDrive.setPower(abs(ramp));
            LFDrive.setPower(abs(ramp));
            LBDrive.setPower(abs(ramp));
            RBDrive.setPower(abs(ramp));
            if (ramp < topSpeed) {
                ramp = ramp + RAMP_INCREMENT;
            } else if (ramp > topSpeed) {
                ramp = ramp - RAMP_INCREMENT;
            }
        } else {
            alreadyDriving = false;
            setAllPower(0);

        }
    }


    public void startStrafeDrive(double speed, double leftInches, double rightInches) {
        topSpeed = speed;
        if (activeOpMode.opModeIsActive() && alreadyStrafing == false) {
            LFDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RFDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            LBDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RBDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            LFDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RFDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            LBDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RBDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            int newLeftFrontTarget = (int) (leftInches * COUNTS_PER_INCH);
            int newRightFrontTarget = (int) (rightInches * COUNTS_PER_INCH);
            int newLeftBackTarget = (int) (leftInches * COUNTS_PER_INCH);
            int newRightBackTarget = (int) (rightInches * COUNTS_PER_INCH);

            LFDrive.setTargetPosition(newLeftFrontTarget);
            RFDrive.setTargetPosition(-newRightFrontTarget);
            LBDrive.setTargetPosition(-newLeftBackTarget);
            RBDrive.setTargetPosition(newRightBackTarget);

            LFDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RFDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LBDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RBDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            strafePeriod.reset();

            ramp = .1;

            RFDrive.setPower(abs(ramp));
            LFDrive.setPower(abs(ramp));
            LBDrive.setPower(abs(ramp));
            RBDrive.setPower(abs(ramp));

            alreadyStrafing = true;
        }
    }

    public void startStrafeDrive(double speed, double distanceInches) {
        topSpeed = speed;
        if (activeOpMode.opModeIsActive() && alreadyStrafing == false) {
            LFDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RFDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            LBDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RBDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            LFDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RFDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            LBDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RBDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            int newLeftFrontTarget = (int) (distanceInches * COUNTS_PER_INCH);
            int newRightFrontTarget = (int) (distanceInches * COUNTS_PER_INCH);
            int newLeftBackTarget = (int) (distanceInches * COUNTS_PER_INCH);
            int newRightBackTarget = (int) (distanceInches * COUNTS_PER_INCH);

            LFDrive.setTargetPosition(newLeftFrontTarget);
            RFDrive.setTargetPosition(-newRightFrontTarget);
            LBDrive.setTargetPosition(-newLeftBackTarget);
            RBDrive.setTargetPosition(newRightBackTarget);

            LFDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RFDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LBDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RBDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            strafePeriod.reset();

            ramp = .1;

            RFDrive.setPower(abs(ramp));
            LFDrive.setPower(abs(ramp));
            LBDrive.setPower(abs(ramp));
            RBDrive.setPower(abs(ramp));

            alreadyStrafing = true;
        }
    }


    public void ContinueStrafing() {
        if (activeOpMode.opModeIsActive() &&
                alreadyStrafing == true &&
                (RFDrive.isBusy() || LFDrive.isBusy() || LBDrive.isBusy() || RBDrive.isBusy())) {
            RFDrive.setPower(abs(ramp));
            LFDrive.setPower(abs(ramp));
            LBDrive.setPower(abs(ramp));
            RBDrive.setPower(abs(ramp));
            if (ramp < topSpeed) {
                ramp = ramp + RAMP_INCREMENT;
            } else if (ramp > topSpeed) {
                ramp = ramp - RAMP_INCREMENT;
            }
        } else {
            alreadyStrafing = false;
            setAllPower(0);
        }
    }


    //auto deliver to the left side high pole
    //maybe have a modifier for distance to select a pole?
    public void auto_deliver1(Arm arm, Lift lift, Claw claw, Intake intake) {

        //set the arm for the auto delivery depending on if we are in the LEFT or RIGHT starting position
        if (currentAutomaticTask == autoDeliverStates.START_AUTOMATIC_DELIVER1) {

            currentAutomaticTask = autoDeliverStates.FIRST_STEP;
        }
        //Drive to the alliance substation
        else if (currentAutomaticTask == autoDeliverStates.FIRST_STEP) {
            currentAutomaticTask = autoDeliverStates.DRIVE_TO_ALLIANCE_SUBSTATION;
            startEncoderDrive(MED_SPEED, -(FULL_TILE_DISTANCE_DRIVE + HALF_TILE_DISTANCE_DRIVE + QUARTER_TILE_DISTANCE_DRIVE));
        }

        //intake a cone
        else if (currentAutomaticTask == autoDeliverStates.DRIVE_TO_ALLIANCE_SUBSTATION && !alreadyDriving ||
                (currentAutomaticTask == autoDeliverStates.INTAKE_CONE && intake.currentIntakeState != Intake.intakeState.INTAKE_OFF)) {
            currentAutomaticTask = autoDeliverStates.INTAKE_CONE;
            intake.AutoDeliverIntakeToggle();
        }

        //drive away from alliance substation
        else if (currentAutomaticTask == autoDeliverStates.INTAKE_CONE && intake.currentIntakeState == Intake.intakeState.INTAKE_OFF) {
            currentAutomaticTask = autoDeliverStates.DRIVE_FROM_ALLIANCE_SUBSTATION;
            startEncoderDrive(MED_SPEED, (FULL_TILE_DISTANCE_DRIVE + HALF_TILE_DISTANCE_DRIVE + EIGHTH_TILE_DISTANCE_DRIVE));
            lift.StartLifting(LOW_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL, arm);

        }

        // Rotate arm, strafe toward the pole, lift to correct height
        else if (currentAutomaticTask == autoDeliverStates.DRIVE_FROM_ALLIANCE_SUBSTATION && !alreadyDriving && !lift.alreadyLifting) {

            if (ButtonConfig.startPositionMultiplier == -1) {
                arm.setPosition(Arm.ARM_LEFT_OUTTAKE);
            } else {
                arm.setPosition(Arm.ARM_RIGHT_OUTTAKE);
            }

            startStrafeDrive(MED_SPEED, QUARTER_TILE_DISTANCE_STRAFE * ButtonConfig.startPositionMultiplier);

            lift.StartLifting(HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL, arm);

            currentAutomaticTask = autoDeliverStates.STRAFE_TO_POLE;

        }
        //release cone
        else if (currentAutomaticTask == autoDeliverStates.STRAFE_TO_POLE && !alreadyStrafing && !lift.alreadyLifting) {
            currentAutomaticTask = autoDeliverStates.DELIVER_CONE;
            claw.AutoDeliverClawTogggle();
        }

        //strafe away
        else if (currentAutomaticTask == autoDeliverStates.DELIVER_CONE && claw.currentClawState == Claw.clawStates.CLAW_CLOSED) {
            currentAutomaticTask = autoDeliverStates.STRAFE_AWAY_FROM_POLE;
            startStrafeDrive(MED_SPEED, -QUARTER_TILE_DISTANCE_STRAFE * ButtonConfig.startPositionMultiplier);
        }

        //drive to just outside the alliance substation
        else if (currentAutomaticTask == autoDeliverStates.STRAFE_AWAY_FROM_POLE) {
            currentAutomaticTask = autoDeliverStates.DRIVE_TO_OUTSIDE_ALLIANCE_SUBSTATION;
            startEncoderDrive(MED_SPEED, -(FULL_TILE_DISTANCE_DRIVE));
        }

        //end auto deliver
        else if (currentAutomaticTask == autoDeliverStates.DRIVE_TO_OUTSIDE_ALLIANCE_SUBSTATION) {
            currentAutomaticTask = autoDeliverStates.END_AUTOMATIC_DELIVER;
            autoDeliver1 = false;
        }
    }

    //auto deliver to the right side high pole
    //maybe have a modifier for distance to select a pole?
    public void auto_deliver2(Arm arm, Lift lift, Claw claw, Intake intake) {

        //set the arm for the auto delivery depending on if we are in the LEFT or RIGHT starting position
        if (currentAutomaticTask == autoDeliverStates.START_AUTOMATIC_DELIVER2) {
            currentAutomaticTask = autoDeliverStates.FIRST_STEP;
        }
        //Drive to the alliance substation
        else if (currentAutomaticTask == autoDeliverStates.FIRST_STEP) {
            currentAutomaticTask = autoDeliverStates.DRIVE_TO_ALLIANCE_SUBSTATION;
            startEncoderDrive(MED_SPEED, -(FULL_TILE_DISTANCE_DRIVE + HALF_TILE_DISTANCE_DRIVE + QUARTER_TILE_DISTANCE_DRIVE));
        }

        //intake a cone
        else if (currentAutomaticTask == autoDeliverStates.DRIVE_TO_ALLIANCE_SUBSTATION && !alreadyDriving ||
                (currentAutomaticTask == autoDeliverStates.INTAKE_CONE && intake.currentIntakeState != Intake.intakeState.INTAKE_OFF)) {
            currentAutomaticTask = autoDeliverStates.INTAKE_CONE;
            intake.AutoDeliverIntakeToggle();
        }

        //drive away from alliance substation
        else if (currentAutomaticTask == autoDeliverStates.INTAKE_CONE && intake.currentIntakeState == Intake.intakeState.INTAKE_OFF) {
            currentAutomaticTask = autoDeliverStates.DRIVE_FROM_ALLIANCE_SUBSTATION;
            startEncoderDrive(MED_SPEED, (FULL_TILE_DISTANCE_DRIVE * 2 + HALF_TILE_DISTANCE_DRIVE + EIGHTH_TILE_DISTANCE_DRIVE));
            lift.StartLifting(LOW_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL, arm);
        }

        // Rotate arm, strafe toward the pole, lift to correct height
        else if (currentAutomaticTask == autoDeliverStates.DRIVE_FROM_ALLIANCE_SUBSTATION && !alreadyDriving && !lift.alreadyLifting) {
            if (ButtonConfig.startPositionMultiplier == 1) {
                arm.setPosition(Arm.ARM_LEFT_OUTTAKE);
            } else {
                arm.setPosition(Arm.ARM_RIGHT_OUTTAKE);
            }

            startStrafeDrive(MED_SPEED, -QUARTER_TILE_DISTANCE_DRIVE * ButtonConfig.startPositionMultiplier, -QUARTER_TILE_DISTANCE_DRIVE * ButtonConfig.startPositionMultiplier);

            lift.StartLifting(HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL, arm);

            currentAutomaticTask = autoDeliverStates.STRAFE_TO_POLE;

        }
        //release cone
        else if (currentAutomaticTask == autoDeliverStates.STRAFE_TO_POLE && !alreadyStrafing && !lift.alreadyLifting) {
            currentAutomaticTask = autoDeliverStates.DELIVER_CONE;
            claw.AutoDeliverClawTogggle();
        }

        //strafe away
        else if (currentAutomaticTask == autoDeliverStates.DELIVER_CONE && claw.currentClawState == Claw.clawStates.CLAW_CLOSED) {
            currentAutomaticTask = autoDeliverStates.STRAFE_AWAY_FROM_POLE;
            startStrafeDrive(MED_SPEED, QUARTER_TILE_DISTANCE_DRIVE * ButtonConfig.startPositionMultiplier, QUARTER_TILE_DISTANCE_DRIVE * ButtonConfig.startPositionMultiplier);
        }

        //drive to just outside the alliance substation
        else if (currentAutomaticTask == autoDeliverStates.STRAFE_AWAY_FROM_POLE) {
            currentAutomaticTask = autoDeliverStates.DRIVE_TO_OUTSIDE_ALLIANCE_SUBSTATION;
            startEncoderDrive(HIGH_SPEED, -(FULL_TILE_DISTANCE_DRIVE * 2), -(FULL_TILE_DISTANCE_DRIVE * 2));
        }

        //end auto deliver
        else if (currentAutomaticTask == autoDeliverStates.DRIVE_TO_OUTSIDE_ALLIANCE_SUBSTATION) {
            currentAutomaticTask = autoDeliverStates.END_AUTOMATIC_DELIVER;
            autoDeliver2 = false;
        }
    }

    public void turn(double degrees, Gyro Gyro) {
        alreadyTurning = true;
        Gyro.resetAngle();
        targetAngleInDegrees = degrees;
        degreesLeftToTurn = degrees;

    }

    public void ContinueTurning(Gyro Gyro) {
        if (abs(degreesLeftToTurn) > 1) {
            double motorPower = (degreesLeftToTurn < 0 ? -.5 : .5);
            setMotorPower(-motorPower, motorPower, -motorPower, motorPower);
            degreesLeftToTurn = targetAngleInDegrees - Gyro.getAngle();
        } else {
            alreadyTurning = false;
            setAllPower(0);
        }
    }

    public void turnTo(double degrees, Gyro Gyro) {
        double absoluteAngle = Gyro.getAbsoluteAngle();
        targetAngle = degrees - absoluteAngle;

        if (targetAngle > 180) {
            targetAngle -= 360;
        } else if (targetAngle < -180) {
            targetAngle += 360;
        }
        turn(targetAngle, Gyro);
    }

    public void turnToPID(double degrees, Gyro Gyro) {
        double absoluteAngle = Gyro.getAbsoluteAngle();
        targetAngle = degrees - absoluteAngle;
        turnPID(targetAngle, Gyro);
    }

    public void turnPID(double degrees, Gyro Gyro) {
        Gyro.resetAngle();
        double kF = .1;
        LFDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RFDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        LBDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RBDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pid = new TurnPIDController2(degrees, 6, 0, 0, kF);
        alreadyPIDTurning = true;
        turningTimer.reset();
    }

    public void ContinuePIDTurning(Gyro Gyro) {
        if (Math.abs(pid.percent_error) > .01 && turningTimer.seconds() < TURNING_TIMEOUT_IN_SECONDS) {
            double motorPower = pid.update(Gyro.getAngle());
            setMotorPower(-motorPower, motorPower, -motorPower, motorPower);
        } else {
            alreadyPIDTurning = false;
            setAllPower(0);
        }
    }


    public void colorDrive(double speed, int allianceColor, LinearOpMode activeOpMode) {

        if (allianceColor == 1) {
            while (activeOpMode.opModeIsActive() && colorSensor.blue() < 230 && colorSensor.red() > 50) {
                setAllPower(speed);
                activeOpMode.telemetry.addData("Color", "R %d  G %d  B %d", colorSensor.red(), colorSensor.green(), colorSensor.blue());
                activeOpMode.telemetry.update();
            }

        } else if (allianceColor == -1) {
            while (activeOpMode.opModeIsActive() && colorSensor.red() < 230 && colorSensor.blue() > 50) {
                setAllPower(speed);
                activeOpMode.telemetry.addData("Color", "R %d  G %d  B %d", colorSensor.red(), colorSensor.green(), colorSensor.blue());
                activeOpMode.telemetry.update();
            }

        }

        setAllPower(0);
        activeOpMode.telemetry.addData("Color", "R %d  G %d  B %d", colorSensor.red(), colorSensor.green(), colorSensor.blue());
        activeOpMode.telemetry.update();

    }

    public void ColorStrafe(double speed, LinearOpMode activeOpMode) {

        activeOpMode.telemetry.addLine("Seeking LINE with Color Sensor");
        activeOpMode.telemetry.addData("Color", "R %d  G %d  B %d", colorSensor.red(), colorSensor.green(), colorSensor.blue());
        activeOpMode.telemetry.update();

        colorTimer.reset();

        //if don't currently see blue or red, then strafe left and right to find the line
        while (activeOpMode.opModeIsActive() &&
                (colorSensor.blue() < 230 && colorSensor.red() > 50) &&
                (colorSensor.red() < 230 && colorSensor.blue() > 50) &&
                colorTimer.seconds() < .8) {

            // Color is not red or blue
            //strafe left for .4 seconds to the left, if still no red or blue line then strafe to right
            if (colorTimer.seconds() < .3) {
                turn = 0;
                drive = 0;
                strafe = speed * ButtonConfig.startPositionMultiplier;
                MecanumDrive();
            } else if (colorTimer.seconds() > .5) {
                turn = 0;
                drive = 0;
                strafe = -speed * ButtonConfig.startPositionMultiplier;
                MecanumDrive();
            }
        }
        turn = 0;
        drive = 0;
        strafe = 0;
        MecanumDrive();

        //once we find the line, drive backwards until we don't see the color
        while ( activeOpMode.opModeIsActive() &&
                !((colorSensor.blue() < 230 && colorSensor.red() > 50) && (colorSensor.red() < 230 && colorSensor.blue() > 50))){
                turn = 0;
                drive = -speed;
                strafe = 0;
                MecanumDrive();
            }
        }

    public double line_follow_error;
    public double percent_line_follow_error;

    public void lineFollow(double speed, LinearOpMode activeOpMode, Gyro gyro) {

        colorTimer.reset();
        if (!alreadyLineFollowing) {
            alreadyLineFollowing = true;
            LFDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RFDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            LBDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RBDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            LFDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RFDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            LBDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RBDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            while (activeOpMode.opModeIsActive() &&
                    (colorSensor.red() < 190 && colorSensor.blue() < 250) &&
                    colorTimer.seconds() < 1.3) {

                activeOpMode.telemetry.addData("Color", "R %d  G %d  B %d", colorSensor.red(), colorSensor.green(), colorSensor.blue());
                activeOpMode.telemetry.addData("Reflected Light", "Alpha %d", colorSensor.alpha());
                activeOpMode.telemetry.addData("line follow error", "%.3f", line_follow_error);
                activeOpMode.telemetry.addData("line follow error", "%.3f", percent_line_follow_error);
                activeOpMode.telemetry.addData("Enc Target", encoderTargetLineFollow);
                activeOpMode.telemetry.addData("Encoders", "LF(%s), RF(%s)", LFDrive.getCurrentPosition(), RFDrive.getCurrentPosition());
                activeOpMode.telemetry.addData("Encoders", "LB(%s), RB(%s)", LBDrive.getCurrentPosition(), RBDrive.getCurrentPosition());
                activeOpMode.telemetry.update();

                // Color is not red or blue
                //strafe left for .4 seconds to the left, if still no red or blue line then strafe to right
                if (colorTimer.seconds() < .5) {
                    turn = 0;
                    drive = 0;
                    strafe = -speed;
                    MecanumDrive();
                } else if (colorTimer.seconds() > .5) {
                    turn = 0;
                    drive = 0;
                    strafe = speed;
                    MecanumDrive();
                }
            }
            turn = 0;
            drive = 0;
            strafe = 0;
            MecanumDrive();
        }

        if (activeOpMode.opModeIsActive() && (colorSensor.red() > 200 || colorSensor.blue() > 340)) {
            //line_follow_error = line_follow_target - colorSensor.green();
            //percent_line_follow_error = line_follow_error / (range);

            //double correction = (percent_line_follow_error * Kp);
            drive = speed;

        } else {
            alreadyLineFollowing = false;
            drive = 0;
        }
        turn = 0;
        strafe = 0;
        MecanumDrive();
        activeOpMode.telemetry.addData("Color", "R %d  G %d  B %d", colorSensor.red(), colorSensor.green(), colorSensor.blue());
        activeOpMode.telemetry.addData("Reflected Light", "Alpha %d", colorSensor.alpha());
        activeOpMode.telemetry.addData("line follow error", "%.3f", line_follow_error);
        activeOpMode.telemetry.addData("line follow error", "%.3f", percent_line_follow_error);
        activeOpMode.telemetry.addData("Enc Target", encoderTargetLineFollow);
        activeOpMode.telemetry.addData("Encoders", "LF(%s), RF(%s)", LFDrive.getCurrentPosition(), RFDrive.getCurrentPosition());
        activeOpMode.telemetry.addData("Encoders", "LB(%s), RB(%s)", LBDrive.getCurrentPosition(), RBDrive.getCurrentPosition());
        activeOpMode.telemetry.update();
    }
}


