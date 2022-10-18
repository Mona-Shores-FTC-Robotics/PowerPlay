package org.firstinspires.ftc.teamcode.ObjectClasses;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//potatoes
@Disabled
public class OverTheTopArm {

    public DcMotor OverArm = null;
    public double OverArmPower = 0;


    // Change number values
    final double TICKS_PER_REV = 288;
    final double OverArm_GEAR_REDUCTION = 1;
    final double ArmDiameter = 22.75;
    double COUNTS_PER_INCH = (TICKS_PER_REV * OverArm_GEAR_REDUCTION) / (ArmDiameter * 3.1415);
    int encoderTicks;

    public static final double HighTorque = 0;
    public static final double LowTorque = 0;

    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        OverArm = ahwMap.get(DcMotor.class, "OverArm");
        OverArm.setDirection(DcMotor.Direction.FORWARD);
        OverArm.setPower(0);
        OverArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        encoderTicks = OverArm.getCurrentPosition();

    }

    public void encoderOverArm (LinearOpMode activeOpMode) {
        encoderTicks = OverArm.getCurrentPosition();
        activeOpMode.telemetry.addData("Encoder OverArm", encoderTicks);
        activeOpMode.telemetry.update();

        OverArm.setTargetPosition(216);

        if (encoderTicks <= 120) {

        }
        else if (encoderTicks < 168) {

        }
        else if (encoderTicks == 168 || encoderTicks > 216) {

        }


    }

}
