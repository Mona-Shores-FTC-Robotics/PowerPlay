package org.firstinspires.ftc.teamcode.OpModes;

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

//This import lets us reference our constants without having to use the GameConstants class name
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.*;
import static org.firstinspires.ftc.teamcode.ObjectClasses.DriveTrain.*;


@Autonomous(name = "AUTO_JUST_PARK")
public class AUTO_JUST_PARK extends LinearOpMode {

    // Variable to store the Signal

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
        sleep(500);

        while (!isStarted()) {
            //Use Webcam to find out Signal using April Tags
            Vision.CheckForAprilTags(this);

            // Let the user set alliance color and starting location variables for use in code
            ButtonConfig.ConfigureAllianceColor();
            ButtonConfig.ConfigureStartingPosition();
            telemetry.addData("Alliance Color ", ButtonConfig.currentAllianceColor);
            telemetry.addData("Starting Position ", ButtonConfig.currentStartPosition);
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

        //Drive backwards into wall to make sure we are aligned
        MecDrive.encoderDrive(MED_SPEED, -QUARTER_TILE_DISTANCE, -QUARTER_TILE_DISTANCE, this);

        //Drive forward 2 tiles plus a little bit more to get into position for deciding where to park
        MecDrive.encoderDrive(MED_SPEED, FULL_TILE_DISTANCE*2+EIGHTH_TILE_DISTANCE, FULL_TILE_DISTANCE*2+EIGHTH_TILE_DISTANCE, this);

        //Decide where to park
        //if current Signal is the LEFT april tag then park on robot's left
        if(Vision.currentSignal == AprilTagVision.Signal.LEFT) {
            //Park on left
            MecDrive.strafeDrive(.3, -FULL_TILE_DISTANCE, -FULL_TILE_DISTANCE, this);
        }

        //if current Signal is the MIDDLE april tag then park in middle
        else if (Vision.currentSignal == AprilTagVision.Signal.MIDDLE ) {
            //Park in middle
        }

        //if current Signal is the RIGHT april tag then park on robot's right
        else if (Vision.currentSignal == AprilTagVision.Signal.RIGHT ){
            //Park on right
            MecDrive.strafeDrive(.3, FULL_TILE_DISTANCE, FULL_TILE_DISTANCE, this);
        }

        telemetry.addData("Status", "Run Time: " + runtime);
        telemetry.update();

    }
}



