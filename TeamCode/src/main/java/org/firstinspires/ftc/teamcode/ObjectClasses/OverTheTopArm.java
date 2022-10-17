package org.firstinspires.ftc.teamcode.ObjectClasses;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Disabled
public class OverTheTopArm {

    public DcMotor OverArm = null;
    public double OverArmPower = 0;


    // Change number values
    final double TICKS_PER_REV = 288;
    final double OverArm_GEAR_REDUCTION = 1;
    final double ArmDiameter = 22.75;
    double COUNTS_PER_INCH = (TICKS_PER_REV * OverArm_GEAR_REDUCTION) / (ArmDiameter * 3.1415);

    public static final double HighTorgue = 0;
    public static final double LowTorgue = 0;

    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        OverArm = ahwMap.get(DcMotor.class, "OverArm");
        OverArm.setDirection(DcMotor.Direction.FORWARD);
        OverArm.setPower(0);

    }

    public void encoderOverArm(double speed, int leftInches, int rightInches, LinearOpMode activeOpMode) {
        activeOpMode.telemetry.addData("Encoder OverArm", OverArm.getCurrentPosition());
        activeOpMode.telemetry.update();
        if (TICKS_PER_REV < 120 || TICKS_PER_REV == 120) {

        }
        else if (TICKS_PER_REV > 120 & TICKS_PER_REV < 168) {

        }
        else if (TICKS_PER_REV == 168 || TICKS_PER_REV > 216) {

        }


    }

}
