package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.ObjectClasses.DriveTrain;
import org.firstinspires.ftc.teamcode.ObjectClasses.OpenCVWebcam;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


/**
 * Example OpMode. Demonstrates use of gyro, color sensor, encoders, and telemetry.
 *
 */
@Autonomous(name = "AutoOp")
public class AutoOp extends LinearOpMode {

    DcMotor BL, FL, FR, BR;
    BNO055IMU imu;
    private ElapsedTime runtime = new ElapsedTime();

    final double TICKS_PER_REV = 400.6;
    final double DRIVE_GEAR_REDUCTION = 1;
    final double WHEEL_DIAMETER_INCHES = 3.93701;
    double COUNTS_PER_INCH = (TICKS_PER_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);

    int allianceColor;
    int startingLocation;

    String allianceColorString = "Blue";
    String startingLocationString = "Row 2 Near Audience";

    int Signal;
    DriveTrain MecDrive = new DriveTrain();
    OpenCVWebcam Vision = new OpenCVWebcam();

    List<Auto> autoList = Arrays.asList(Auto.values());
    Iterator i = autoList.iterator();
    Auto currentAuto = (Auto)i.next();

    @Override

    public void runOpMode() {

        MecDrive.init(hardwareMap);
        Vision.init(hardwareMap);

        FL = hardwareMap.dcMotor.get("LFDrive");
        FR = hardwareMap.dcMotor.get("RFDrive");
        BL = hardwareMap.dcMotor.get("LBDrive");
        BR = hardwareMap.dcMotor.get("RBDrive");

        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FR.setDirection(DcMotor.Direction.REVERSE);
        BR.setDirection(DcMotor.Direction.REVERSE);

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(new BNO055IMU.Parameters());

        while (!isStarted()) {
            telemetry.addData("Alliance Color ", allianceColorString);
            telemetry.addData("Starting Location ", startingLocationString);
            telemetry.addData("Autonomous Routine ", currentAuto);
            telemetry.update();
            //if (gamepad1.dpad_down)

        }

        //Read Signal
        //Use Webcam to find out Signal and store in Signal variable
        Signal = 1;

        //Backup into wall for alignment

        // call selected autonomous routine
        //switch(currentAuto) {
        //    case JUSTPARK:
        //        autoJustPark();
        //        break;
        // }

    }

        public void encoderDrive(double speed, int leftInches, int rightInches) {
        if (opModeIsActive()) {

            int newLeftFrontTarget = (int) (leftInches * COUNTS_PER_INCH);
            int newRightFrontTarget = (int) (rightInches * COUNTS_PER_INCH);
            int newLeftBackTarget = (int) (leftInches * COUNTS_PER_INCH);
            int newRightBackTarget = (int) (rightInches * COUNTS_PER_INCH);

            FL.setTargetPosition(newLeftFrontTarget);
            FR.setTargetPosition(newRightFrontTarget);
            BL.setTargetPosition(newLeftBackTarget);
            BR.setTargetPosition(newRightBackTarget);

            FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();
            FR.setPower(Math.abs(speed));
            FL.setPower(Math.abs(speed));
            BL.setPower(Math.abs(speed));
            BR.setPower(Math.abs(speed));

            while (opModeIsActive() &&
                    (runtime.seconds() < 5) &&
                    (FR.isBusy() && FL.isBusy() && BL.isBusy() && BR.isBusy())) {
                telemetry.addData("Encoder BL", FL.getCurrentPosition());
                telemetry.addData("Encoder FR", FR.getCurrentPosition());
                telemetry.addData("Encoder BL", BR.getCurrentPosition());
                telemetry.addData("Encoder BR", BL.getCurrentPosition());

                telemetry.addData("Encoder Target", newLeftFrontTarget);

                telemetry.update();
            }

            FR.setPower(0);
            FL.setPower(0);
            BR.setPower(0);
            BL.setPower(0);

            FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            sleep(250);

        }

    }

        public enum Auto {
            VISIONTURN, JUSTPARK, SCOREONEANDPARK, SCORESIXPARK, JUSTPARKTIME;
        }

    }

