package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.teamcode.ObjectClasses.DriveTrain.LOW_SPEED;
import static org.firstinspires.ftc.teamcode.ObjectClasses.DriveTrain.MED_SPEED;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FULL_TILE_DISTANCE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.SIXTEENTH_TILE_DISTANCE;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.ObjectClasses.AprilTagVision;
import org.firstinspires.ftc.teamcode.ObjectClasses.ButtonConfig;
import org.firstinspires.ftc.teamcode.ObjectClasses.Claw;
import org.firstinspires.ftc.teamcode.ObjectClasses.DriveTrain;
import org.firstinspires.ftc.teamcode.ObjectClasses.Intake;


@Autonomous(name = "AUTO_JUST_PARK")
public class AUTO_JUST_PARK extends LinearOpMode {

    DriveTrain MecDrive = new DriveTrain(this);
    AprilTagVision Vision = new AprilTagVision();
    ButtonConfig ButtonConfig = new ButtonConfig(this);
    Claw ServoClaw = new Claw();
    Intake ServoIntake = new Intake();

    Gamepad currentGamepad2 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initializing");
        telemetry.update();

        MecDrive.init(hardwareMap);
        Vision.init(hardwareMap);
        ButtonConfig.init();
        ServoIntake.init(hardwareMap);
        ServoClaw.init(hardwareMap);

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        sleep(500);

        while (!isStarted()) {
            //Use Webcam to find out Signal using April Tags
            Vision.CheckForAprilTags(this);
            Vision.SetSignal(this);

            // Let the user set alliance color and starting location variables for use in code
            ButtonConfig.ConfigureAllianceColor();
            ButtonConfig.ConfigureStartingPosition();

            telemetry.addData("Vision Signal ", Vision.currentSignal);
            telemetry.addData("Alliance Color ", ButtonConfig.currentAllianceColor);
            telemetry.addData("Starting Position ", ButtonConfig.currentStartPosition);
            telemetry.update();

            //Store the previous loop's gamepad values.
            previousGamepad2 = ButtonConfig.copy(currentGamepad2);

            //Store the gamepad values to be used for this iteration of the loop.
            currentGamepad2 = ButtonConfig.copy(gamepad2);

            //Let the second gamepad control the claw and intake during init so the starting cone can be easily loaded
            ServoIntake.CheckIntake(currentGamepad2.x, previousGamepad2.x);
            ServoClaw.CheckClaw(currentGamepad2.a, previousGamepad2.a);

            sleep(20);
        }

        Vision.SetSignal(this);

        telemetry.addData("Signal is ", Vision.currentSignal);
        telemetry.addData("Signal is ", Vision.tagOfInterest.id);
        telemetry.addData("Selected Alliance Color ", ButtonConfig.currentAllianceColor);
        telemetry.addData("Selected Starting Position ", ButtonConfig.currentStartPosition);
        telemetry.addData("Status", "Run Time: " + getRuntime());
        telemetry.update();

        //Drive forward 2 tiles plus a little bit more to get into position for deciding where to park
        MecDrive.startEncoderDrive(LOW_SPEED, FULL_TILE_DISTANCE * 2 + SIXTEENTH_TILE_DISTANCE, FULL_TILE_DISTANCE * 2 + SIXTEENTH_TILE_DISTANCE);
        while (opModeIsActive() && MecDrive.alreadyDriving == true) {
            MecDrive.ContinueDriving();
        }

        //Decide where to park
        //if current Signal is the LEFT april tag then park on robot's left
        if (Vision.currentSignal == AprilTagVision.Signal.LEFT) {
            //Park on left
            MecDrive.startStrafeDrive(MED_SPEED, -FULL_TILE_DISTANCE, -FULL_TILE_DISTANCE);
            while (opModeIsActive() && MecDrive.alreadyStrafing == true) {
                MecDrive.ContinueStrafing();
            }
        }

        //if current Signal is the MIDDLE april tag then park in middle
        else if (Vision.currentSignal == AprilTagVision.Signal.MIDDLE) {
            //Park in middle
            }

        //if current Signal is the RIGHT april tag then park on robot's right
        else if (Vision.currentSignal == AprilTagVision.Signal.RIGHT) {
            //Park on right
            MecDrive.startStrafeDrive(MED_SPEED, FULL_TILE_DISTANCE, FULL_TILE_DISTANCE);
            while (opModeIsActive() && MecDrive.alreadyStrafing == true) {
                    MecDrive.ContinueStrafing();
            }

            telemetry.addData("Status", "Run Time: " + getRuntime());
            telemetry.update();
            }
        }
    }




