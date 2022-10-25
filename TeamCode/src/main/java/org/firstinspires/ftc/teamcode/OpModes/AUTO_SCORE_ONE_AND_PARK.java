package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.teamcode.ObjectClasses.DriveTrain.LOW_SPEED;
import static org.firstinspires.ftc.teamcode.ObjectClasses.DriveTrain.MED_SPEED;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.EIGHTH_TILE_DISTANCE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FULL_TILE_DISTANCE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.HALF_TILE_DISTANCE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.QUARTER_TILE_DISTANCE;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.ObjectClasses.AprilTagVision;
import org.firstinspires.ftc.teamcode.ObjectClasses.Arm;
import org.firstinspires.ftc.teamcode.ObjectClasses.ButtonConfig;
import org.firstinspires.ftc.teamcode.ObjectClasses.Claw;
import org.firstinspires.ftc.teamcode.ObjectClasses.DriveTrain;
import org.firstinspires.ftc.teamcode.ObjectClasses.Intake;
import org.firstinspires.ftc.teamcode.ObjectClasses.Lift;


@Autonomous(name = "AUTO_SCORE_ONE_AND_PARK")
public class AUTO_SCORE_ONE_AND_PARK extends LinearOpMode {

    DriveTrain MecDrive = new DriveTrain();
    AprilTagVision Vision = new AprilTagVision();
    ButtonConfig ButtonConfig = new ButtonConfig(this);
    Arm ServoArm = new Arm();
    Intake ServoIntake = new Intake();
    Claw ServoClaw = new Claw();
    Lift Lift = new Lift();

    public final ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initializing");
        telemetry.update();

        MecDrive.init(hardwareMap);
        Vision.init(hardwareMap);
        ButtonConfig.init();
        ServoArm.init(hardwareMap);
        ServoIntake.init(hardwareMap);
        ServoClaw.init(hardwareMap);
        //Lift.init(hardwareMap);
        //Lift.moveLift(ONE_CONE_INTAKE_HEIGHT_MM, this);

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        sleep(1000);

        while (!isStarted()) {
            //Use Webcam to check for April Tags
            Vision.CheckForAprilTags(this);
            ButtonConfig.ConfigureAllianceColor();
            ButtonConfig.ConfigureStartingPosition();

            telemetry.addData("Selected Alliance Color ", ButtonConfig.currentAllianceColor);
            telemetry.addData("Selected Starting Position ", ButtonConfig.currentStartPosition);
            telemetry.addData("Current Signal is ", Vision.currentSignal);
            telemetry.update();
            sleep(20);
        }

        runtime.reset();
        Vision.SetSignal(this);
        telemetry.addData("Selected Alliance Color ", ButtonConfig.currentAllianceColor);
        telemetry.addData("Selected Starting Position ", ButtonConfig.currentStartPosition);
        telemetry.addData("Final Signal is ", Vision.currentSignal);
        telemetry.update();

        //align to the wall and calibrate gyro
        MecDrive.encoderDrive(MED_SPEED, -QUARTER_TILE_DISTANCE, -QUARTER_TILE_DISTANCE, this);
        MecDrive.calibrateGyro(this);

        //Drive Forward
        MecDrive.encoderDrive(LOW_SPEED, (FULL_TILE_DISTANCE*2)+EIGHTH_TILE_DISTANCE, (FULL_TILE_DISTANCE*2)+EIGHTH_TILE_DISTANCE, this);

        //Strafe in Front of High Pole
        MecDrive.strafeDrive(LOW_SPEED, -(HALF_TILE_DISTANCE * ButtonConfig.allianceColorAndLocationFactor), -(HALF_TILE_DISTANCE * ButtonConfig.allianceColorAndLocationFactor), this);

        //Drive close to High Pole
        MecDrive.encoderDrive(LOW_SPEED, EIGHTH_TILE_DISTANCE, EIGHTH_TILE_DISTANCE, this);

        //Open claw to drop cone
        ServoClaw.toggleClaw();
        sleep(250);
        ServoClaw.toggleClaw();

        //Back away from High Pole
        MecDrive.encoderDrive(LOW_SPEED, -EIGHTH_TILE_DISTANCE, -EIGHTH_TILE_DISTANCE, this);

        //Park after placing cone
        if (Vision.currentSignal == AprilTagVision.Signal.LEFT) {
            MecDrive.strafeDrive(MED_SPEED, (-FULL_TILE_DISTANCE + (HALF_TILE_DISTANCE * ButtonConfig.allianceColorAndLocationFactor)), (-FULL_TILE_DISTANCE + (HALF_TILE_DISTANCE * ButtonConfig.allianceColorAndLocationFactor)), this);
        } else if (Vision.currentSignal == AprilTagVision.Signal.MIDDLE) {
            MecDrive.strafeDrive(MED_SPEED, (HALF_TILE_DISTANCE* ButtonConfig.allianceColorAndLocationFactor), (HALF_TILE_DISTANCE * ButtonConfig.allianceColorAndLocationFactor), this);
        } else if (Vision.currentSignal == AprilTagVision.Signal.RIGHT) {
            MecDrive.strafeDrive(MED_SPEED, (FULL_TILE_DISTANCE + (HALF_TILE_DISTANCE * ButtonConfig.allianceColorAndLocationFactor)), (FULL_TILE_DISTANCE + (HALF_TILE_DISTANCE * ButtonConfig.allianceColorAndLocationFactor)), this);
        }

        telemetry.addData("Status", "Run Time: " + runtime);
        telemetry.update();
        sleep(6000);

    }
}



