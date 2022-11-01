package org.firstinspires.ftc.teamcode.ObjectClasses;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Arm {

    public static final double ARM_CENTER_INTAKE = 0.5;
    public static final double ARM_LEFT_OUTTAKE = 1;
    public static final double ARM_RIGHT_OUTTAKE = 0;
    public Servo arm;

    public armState currentArmState;
    public enum armState {ARM_LEFT, ARM_CENTER, ARM_RIGHT}

    public void init(HardwareMap ahwMap) {
        arm = ahwMap.servo.get("turret_servo");
        //set arm at intake position
        arm.setPosition(ARM_CENTER_INTAKE);
        currentArmState = armState.ARM_CENTER;
    }

    public void setArmState(armState state)
    {
        if (state == armState.ARM_LEFT){
            arm.setPosition(ARM_LEFT_OUTTAKE);
        } else if (state == armState.ARM_CENTER){
            arm.setPosition(ARM_CENTER_INTAKE);
        } else if (state == armState.ARM_RIGHT){
            arm.setPosition(ARM_RIGHT_OUTTAKE);
        }
        currentArmState = state;
    }

    public void CheckArm(Boolean armLeftCurrentButton, Boolean armLeftPreviousButton,
                         Boolean armCenterCurrentButton, Boolean armCenterPreviousButton,
                         Boolean armRightCurrentButton, Boolean armRightPreviousButton){
        if (armLeftCurrentButton && !armLeftPreviousButton) {
            setArmState(armState.ARM_LEFT);
        } else if (armCenterCurrentButton && !armCenterPreviousButton) {
            setArmState(armState.ARM_CENTER);
        } else if (armRightCurrentButton && !armRightPreviousButton) {
            setArmState(armState.ARM_RIGHT);
        }
    }
}
