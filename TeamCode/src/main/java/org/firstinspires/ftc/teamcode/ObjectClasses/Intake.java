package org.firstinspires.ftc.teamcode.ObjectClasses;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {

    public boolean intakeOn;
    public Servo intake1;
    public Servo intake2;

    public void init(HardwareMap ahwMap) {
        intake1 = ahwMap.servo.get("intake1_servo");
        intake2 = ahwMap.servo.get("intake2_servo");

        //set intake positions
        intake1.setPosition(.5);
        intake2.setPosition(.5);
        intakeOn = false;
    }

    public void toggleIntake() {

        if (intakeOn) {
            intake1.setPosition(.5);
            intake2.setPosition(.5);
            intakeOn= false;
        }
        else {
            intake1.setPosition(0);
            intake2.setPosition(1);
            intakeOn = true;
        }
    }
}
