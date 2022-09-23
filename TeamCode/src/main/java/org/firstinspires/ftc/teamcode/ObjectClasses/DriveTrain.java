/* Copyright (c) 2017 FIRST. All rights reserved.
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

package org.firstinspires.ftc.teamcode.ObjectClasses;

import static java.lang.Math.abs;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a Pushbot.
 * See PushbotTeleopTank_Iterative and others classes starting with "Pushbot" for usage examples.
 *
 */

public class DriveTrain
{
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
    public Orientation lastAngles = new Orientation();
    public double currAngle = 0.0;

    BNO055IMU imu;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private final ElapsedTime period  = new ElapsedTime();

    //motor and wheel parameters
    final double TICKS_PER_REV = 537.7;
    final double DRIVE_GEAR_REDUCTION = 1;
    final double WHEEL_DIAMETER_INCHES = 3.93701;
    double COUNTS_PER_INCH = (TICKS_PER_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);


    /* Constructor */
    public DriveTrain(){
    }

    /* Initialize Hardware interfaces */

    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        LFDrive  = ahwMap.get(DcMotor.class, "LFDrive");
        RFDrive = ahwMap.get(DcMotor.class, "RFDrive");
        LBDrive  = ahwMap.get(DcMotor.class, "LBDrive");
        RBDrive = ahwMap.get(DcMotor.class, "RBDrive");

        LFDrive.setDirection(DcMotor.Direction.FORWARD);
        RFDrive.setDirection(DcMotor.Direction.REVERSE);
        LBDrive.setDirection(DcMotor.Direction.FORWARD);
        RBDrive.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to zero power
        LFDrive.setPower(0);
        RFDrive.setPower(0);
        LBDrive.setPower(0);
        RBDrive.setPower(0);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu = hwMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);


      }

    //Set power to all motors
    public void setAllPower(double p){setMotorPower(p,p,p,p);}

    public void setMotorPower(double lF,double rF,double lB,double rB){
        LFDrive.setPower(lF);
        RFDrive.setPower(rF);
        LBDrive.setPower(lB);
        RBDrive.setPower(rB);
    }
    public void MecanumDrive(){

        // Put Mecanum Drive math and motor commands here.

        double dPercent = abs(drive) / (abs(drive) + abs(strafe) + abs(turn));
        double sPercent = abs(strafe) / (abs(drive) + abs(turn) + abs(strafe));
        double tPercent = abs(turn) / (abs(drive) + abs(turn) + abs(strafe));

        rightFrontPower  = (drive * dPercent) + (-strafe * sPercent) + (-turn * tPercent);
        rightBackPower   = (drive * dPercent) + (strafe * sPercent) + (-turn * tPercent);
        leftFrontPower   = (drive * dPercent) + (strafe * sPercent) + (turn * tPercent);
        leftBackPower    = (drive * dPercent) + (-strafe * sPercent) + (turn * tPercent);

        // Send calculated power to wheels
        LFDrive.setPower(leftFrontPower);
        RFDrive.setPower(rightFrontPower);
        LBDrive.setPower(leftBackPower);
        RBDrive.setPower(rightBackPower);
    }

        public void encoderDrive(double speed, int leftInches, int rightInches, LinearOpMode activeOpMode) {

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

            period.reset();
            RFDrive.setPower(abs(speed));
            LFDrive.setPower(abs(speed));
            LBDrive.setPower(abs(speed));
            RBDrive.setPower(abs(speed));

            while (activeOpMode.opModeIsActive() &&
                    (period.seconds() < 5) &&
                    (RFDrive.isBusy() && LFDrive.isBusy() && LBDrive.isBusy() && RBDrive.isBusy())) {
                activeOpMode.telemetry.addData("Encoder BL", LFDrive.getCurrentPosition());
                activeOpMode.telemetry.addData("Encoder FR", RFDrive.getCurrentPosition());
                activeOpMode.telemetry.addData("Encoder BL", LBDrive.getCurrentPosition());
                activeOpMode.telemetry.addData("Encoder BR", RBDrive.getCurrentPosition());

                activeOpMode.telemetry.addData("Encoder Target", newLeftFrontTarget);

                activeOpMode.telemetry.update();
            }

            RFDrive.setPower(0);
            LFDrive.setPower(0);
            RBDrive.setPower(0);
            LBDrive.setPower(0);

            LFDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RFDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            LBDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RBDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            LFDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RFDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            LBDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RBDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            activeOpMode.sleep(250);
        }

    public void strafeDrive(double speed, int leftInches, int rightInches, LinearOpMode activeOpMode) {

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

        period.reset();
        RFDrive.setPower(abs(speed));
        LFDrive.setPower(abs(speed));
        LBDrive.setPower(abs(speed));
        RBDrive.setPower(abs(speed));

        while (activeOpMode.opModeIsActive() &&
                (period.seconds() < 5) &&
                (RFDrive.isBusy() && LFDrive.isBusy() && LBDrive.isBusy() && RBDrive.isBusy())) {
            activeOpMode.telemetry.addData("Encoder BL", LFDrive.getCurrentPosition());
            activeOpMode.telemetry.addData("Encoder FR", RFDrive.getCurrentPosition());
            activeOpMode.telemetry.addData("Encoder BL", LBDrive.getCurrentPosition());
            activeOpMode.telemetry.addData("Encoder BR", RBDrive.getCurrentPosition());

            activeOpMode.telemetry.addData("Encoder Target", newLeftFrontTarget);

            activeOpMode.telemetry.update();
        }

        RFDrive.setPower(0);
        LFDrive.setPower(0);
        RBDrive.setPower(0);
        LBDrive.setPower(0);

        LFDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RFDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LBDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RBDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        LFDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RFDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LBDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RBDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        activeOpMode.sleep(250);
    }


        public void resetAngle() {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES); //imu is the internal gyro and things
        currAngle = 0;
    }


    public void turnDegrees(int targetAngle, LinearOpMode activeOpMode) {

        strafe = 0;
        drive = 0;
        turn = .4;
        if (targetAngle < 0) {turn = turn*(-1);}
        MecanumDrive();

        currAngle = 0;
        while ( (activeOpMode.opModeIsActive() &&
                (abs(currAngle) < abs(targetAngle))))
            {
                lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES); //imu is the internal gyro and things
                currAngle = lastAngles.firstAngle;

            activeOpMode.telemetry.addData("Current Angle", currAngle);
            activeOpMode.telemetry.addData("Target Angle", targetAngle);
            activeOpMode.telemetry.update();
        }
        strafe = 0;
        drive = 0;
        turn = 0;
        MecanumDrive();
    }

    public void turnToAngle(int targetAngle, LinearOpMode activeOpMode) {

        imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES); //imu is the internal gyro and things
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES); //imu is the internal gyro and things
        double difference =  lastAngles.firstAngle - targetAngle;
        strafe = 0;
        drive = 0;
        turn = .4;
        if (difference < 0) {turn = turn*(-1);}

        MecanumDrive();

        currAngle = 0;
        while ( (activeOpMode.opModeIsActive() &&
                (abs(currAngle) < abs(difference))))
        {
            lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES); //imu is the internal gyro and things
            currAngle = lastAngles.firstAngle;

            activeOpMode.telemetry.addData("Current Angle", currAngle);
            activeOpMode.telemetry.addData("Target Angle", difference);
            activeOpMode.telemetry.update();
        }
        strafe = 0;
        drive = 0;
        turn = 0;
        MecanumDrive();
    }


 }

