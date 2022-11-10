package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.ARM_CENTER_INTAKE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.ARM_LEFT_OUTTAKE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.ARM_RIGHT_OUTTAKE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.ButtonConfig.AllianceColor;
import static org.firstinspires.ftc.teamcode.ObjectClasses.ButtonConfig.StartPosition;
import static org.firstinspires.ftc.teamcode.ObjectClasses.DriveTrain.LOW_SPEED;
import static org.firstinspires.ftc.teamcode.ObjectClasses.DriveTrain.MED_SPEED;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.EIGHTH_TILE_DISTANCE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FIVE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FULL_TILE_DISTANCE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.HALF_TILE_DISTANCE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.ONE_CONE_INTAKE_HEIGHT_ENC_VAL;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.QUARTER_TILE_DISTANCE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.SIXTEENTH_TILE_DISTANCE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.THIRTYSECOND_TILE_DISTANCE;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.ObjectClasses.AprilTagVision;
import org.firstinspires.ftc.teamcode.ObjectClasses.Arm;
import org.firstinspires.ftc.teamcode.ObjectClasses.ButtonConfig;
import org.firstinspires.ftc.teamcode.ObjectClasses.Claw;
import org.firstinspires.ftc.teamcode.ObjectClasses.DriveTrain;
import org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants;
import org.firstinspires.ftc.teamcode.ObjectClasses.Gyro;
import org.firstinspires.ftc.teamcode.ObjectClasses.Intake;
import org.firstinspires.ftc.teamcode.ObjectClasses.Lift;


@Autonomous(name = "AUTO_SCORE_ONE_AND_PARK")
public class AUTO_SCORE_ONE_AND_PARK extends LinearOpMode {

    int Signal;
    DriveTrain MecDrive = new DriveTrain(this);
    ButtonConfig ButtonConfig = new ButtonConfig(this);

    Intake ServoIntake = new Intake();
    AprilTagVision Vision = new AprilTagVision();
    Claw ServoClaw = new Claw();
    Lift Lift = new Lift(this);
    Arm ServoArm = new Arm(Lift);
    Gyro Gyro = new Gyro(this);

    private final ElapsedTime runtime = new ElapsedTime();

    Gamepad currentGamepad2 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad();

    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initializing");
        telemetry.update();

        MecDrive.init(hardwareMap);
        ServoArm.init(hardwareMap);
        ServoIntake.init(hardwareMap);
        ServoClaw.init(hardwareMap);
        Lift.init(hardwareMap);

        Vision.init(hardwareMap);
        ButtonConfig.init();

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        sleep(1000);

        while (!isStarted()) {
            //Use Webcam to find out Signal and store in Signal variable
            Vision.CheckForAprilTags(this);
            ButtonConfig.ConfigureAllianceColor();
            ButtonConfig.ConfigureStartingPosition();

            telemetry.addData("Signal is ", Vision.currentSignal);
            telemetry.addData("Alliance Color ", ButtonConfig.currentAllianceColor);
            telemetry.addData("Starting Position ", ButtonConfig.currentStartPosition);
            telemetry.addData("Status", "Run Time: " + getRuntime());
            telemetry.update();

            previousGamepad2 = ButtonConfig.copy(currentGamepad2);
            currentGamepad2 = ButtonConfig.copy(gamepad2);

            ServoClaw.CheckClaw(currentGamepad2.a, previousGamepad2.a);
            ServoIntake.CheckIntake(currentGamepad2.x, previousGamepad2.x);

            sleep(20);
        }

        runtime.reset();
        Gyro.init(hardwareMap);
        Vision.SetSignal(this);

        //Vision.SetSignal(this);
        telemetry.addData("Signal is ", Signal);
        telemetry.addData("Selected Alliance Color ", ButtonConfig.currentAllianceColor);
        telemetry.addData("Selected Starting Position ", ButtonConfig.currentStartPosition);
        telemetry.update();

        //Drive Forward
        Lift.StartLifting(400);
        MecDrive.startEncoderDrive(MED_SPEED, (FULL_TILE_DISTANCE*2+HALF_TILE_DISTANCE)+QUARTER_TILE_DISTANCE, (FULL_TILE_DISTANCE*2+HALF_TILE_DISTANCE)+QUARTER_TILE_DISTANCE);
        while (opModeIsActive() && MecDrive.alreadyDriving == true) {
            MecDrive.ContinueDriving();
        }

        //Drive Backwards
        MecDrive.startEncoderDrive(MED_SPEED, -(QUARTER_TILE_DISTANCE+HALF_TILE_DISTANCE), -(QUARTER_TILE_DISTANCE+HALF_TILE_DISTANCE));
        while (opModeIsActive() && MecDrive.alreadyDriving == true) {
            MecDrive.ContinueDriving();
        }

        //Rotate
        if ((ButtonConfig.currentAllianceColor == AllianceColor.BLUE && ButtonConfig.currentStartPosition == StartPosition.ROW_2) ||
            (ButtonConfig.currentAllianceColor == AllianceColor.RED && ButtonConfig.currentStartPosition == StartPosition.ROW_5))
            {
                MecDrive.turnTo(90, Gyro);
                while (opModeIsActive() && MecDrive.alreadyTurning == true) {
                    MecDrive.ContinueTurning(Gyro);
                }
            }
        else
            {
                MecDrive.turnTo(-90, Gyro);
                while (opModeIsActive() && MecDrive.alreadyTurning == true) {
                    MecDrive.ContinueTurning(Gyro);
                }
            }

        //Drive in Front of High Pole
        MecDrive.startEncoderDrive(LOW_SPEED, HALF_TILE_DISTANCE, HALF_TILE_DISTANCE);
        Lift.StartLifting(HIGH_CONE_JUNCTION_SCORE_HEIGHT_ENC_VAL);
        while (opModeIsActive() && MecDrive.alreadyDriving == true) {
            MecDrive.ContinueDriving();
            Lift.ContinueLifting();
        }

        sleep(1000);

        //Strafe close to High Pole
        MecDrive.startStrafeDrive(LOW_SPEED, (QUARTER_TILE_DISTANCE+EIGHTH_TILE_DISTANCE)* ButtonConfig.allianceColorAndLocationFactor, (QUARTER_TILE_DISTANCE+EIGHTH_TILE_DISTANCE)*ButtonConfig.allianceColorAndLocationFactor);
        if ((ButtonConfig.currentAllianceColor == AllianceColor.BLUE && ButtonConfig.currentStartPosition == StartPosition.ROW_2) ||
                (ButtonConfig.currentAllianceColor == AllianceColor.RED && ButtonConfig.currentStartPosition == StartPosition.ROW_5)) {
            ServoArm.setPosition(ARM_RIGHT_OUTTAKE);
        } else ServoArm.setPosition(ARM_LEFT_OUTTAKE);
        while (opModeIsActive() && MecDrive.alreadyStrafing == true) {
            MecDrive.ContinueStrafing();
        }

        //Back off just a little
        MecDrive.startStrafeDrive(LOW_SPEED, -(SIXTEENTH_TILE_DISTANCE+THIRTYSECOND_TILE_DISTANCE)* ButtonConfig.allianceColorAndLocationFactor, -(SIXTEENTH_TILE_DISTANCE+THIRTYSECOND_TILE_DISTANCE)*ButtonConfig.allianceColorAndLocationFactor);
        while (opModeIsActive() && MecDrive.alreadyStrafing == true) {
            MecDrive.ContinueStrafing();
        }

        sleep(1000);

        //Open claw to drop cone
        ServoClaw.toggleClaw();
        sleep (1000);

        //Strafe away from High Pole
        MecDrive.startStrafeDrive(LOW_SPEED, -QUARTER_TILE_DISTANCE* ButtonConfig.allianceColorAndLocationFactor,
                                            -QUARTER_TILE_DISTANCE* ButtonConfig.allianceColorAndLocationFactor);
        while (opModeIsActive() && MecDrive.alreadyStrafing == true) {
            MecDrive.ContinueStrafing();
        }

        //close the claw
        ServoClaw.toggleClaw();
        ServoArm.setPosition(ARM_CENTER_INTAKE);
        Lift.StartLifting(FIVE_CONE_STACK_INTAKE_HEIGHT_ENC_VAL);

        //Park after placing cone
        if (Vision.currentSignal == AprilTagVision.Signal.LEFT) {
            MecDrive.startEncoderDrive(LOW_SPEED, ((FULL_TILE_DISTANCE * ButtonConfig.allianceColorAndLocationFactor)-HALF_TILE_DISTANCE), ((FULL_TILE_DISTANCE* ButtonConfig.allianceColorAndLocationFactor) - HALF_TILE_DISTANCE));
        } else if (Vision.currentSignal == AprilTagVision.Signal.MIDDLE) {
            MecDrive.startEncoderDrive(LOW_SPEED, -HALF_TILE_DISTANCE, -HALF_TILE_DISTANCE);
        } else if (Vision.currentSignal == AprilTagVision.Signal.RIGHT) {
            MecDrive.startEncoderDrive(LOW_SPEED, ((-FULL_TILE_DISTANCE * ButtonConfig.allianceColorAndLocationFactor) -HALF_TILE_DISTANCE), ((-FULL_TILE_DISTANCE * ButtonConfig.allianceColorAndLocationFactor) -HALF_TILE_DISTANCE));
        }
        while (opModeIsActive() && MecDrive.alreadyDriving == true) {
            MecDrive.ContinueDriving();
        }


        telemetry.addData("Signal is ", Signal);
        telemetry.addData("Selected Alliance Color ", ButtonConfig.currentAllianceColor);
        telemetry.addData("Selected Starting Position ", ButtonConfig.currentStartPosition);
        telemetry.addData("Status", "Run Time: " + runtime);
        telemetry.update();
    }
}



