package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.ARM_INTAKE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.Arm.ARM_LEFT_OUTTAKE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.DriveTrain.HIGH_SPEED;
import static org.firstinspires.ftc.teamcode.ObjectClasses.DriveTrain.MED_SPEED;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FULL_TILE_DISTANCE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.HALF_TILE_DISTANCE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.HIGH_CONE_JUNCTION_SCORE_HEIGHT_MM;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.ONE_CONE_INTAKE_HEIGHT_MM;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.QUARTER_TILE_DISTANCE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.SIXTEENTH_TILE_DISTANCE;

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
    ButtonConfig ButtonConfig = new ButtonConfig();
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
            ButtonConfig.ConfigureAllianceColor(this);
            ButtonConfig.ConfigureStartingLocation( this);

            telemetry.addData("Alliance Color ", ButtonConfig.allianceColorString);
            telemetry.addData("Starting Location ", ButtonConfig.startingLocationString);
            telemetry.update();
        }

        runtime.reset();
        Vision.SetSignal(this);
        telemetry.addData("Signal is ", Vision.currentSignal);
        telemetry.update();


        //align to the wall and calibrate gyro
        MecDrive.encoderDrive(MED_SPEED, -QUARTER_TILE_DISTANCE, -QUARTER_TILE_DISTANCE, this);
        MecDrive.calibrateGyro(this);

        //drive to line up with the cone stack
        MecDrive.encoderDrive(HIGH_SPEED, (FULL_TILE_DISTANCE*2 + SIXTEENTH_TILE_DISTANCE), (FULL_TILE_DISTANCE*2 + SIXTEENTH_TILE_DISTANCE), this);
        MecDrive.turnTo(-90, this);

        //drive toward middle of field
        MecDrive.encoderDrive(HIGH_SPEED, -(HALF_TILE_DISTANCE), -(HALF_TILE_DISTANCE), this);

        //rotate turret to deliver to High Junction
        //this code won't work if high junction is on the left.
        ServoArm.setPosition(ARM_LEFT_OUTTAKE);

        //raise lift to height to deliver to High Junction
        //Lift.moveLift(HIGH_CONE_JUNCTION_SCORE_HEIGHT_MM, this);

        //strafe to the high pole
        MecDrive.strafeDrive(HIGH_SPEED, -(QUARTER_TILE_DISTANCE), -(QUARTER_TILE_DISTANCE), this);

        // Open claw to release cone
        ServoClaw.toggleClaw();
        int coneDeliveryTracker = 1;

        //Give cone a moment to release;
        sleep(100);

        //Close claw for intaking next cone
        ServoClaw.toggleClaw();

        //strafe away from the high pole
        MecDrive.strafeDrive(HIGH_SPEED, (QUARTER_TILE_DISTANCE), (QUARTER_TILE_DISTANCE), this);


        //Park code

        //Decide where to park
        //if current Signal is the LEFT april tag then park on robot's left
        if(Vision.currentSignal == AprilTagVision.Signal.LEFT) {
            //Park on left
            MecDrive.encoderDrive(HIGH_SPEED, -(HALF_TILE_DISTANCE),-(HALF_TILE_DISTANCE),this);
        }

        //if current Signal is the MIDDLE april tag then park in middle
        else if (Vision.currentSignal == AprilTagVision.Signal.MIDDLE ) {
            //Park in middle
            MecDrive.encoderDrive(HIGH_SPEED, (HALF_TILE_DISTANCE),(HALF_TILE_DISTANCE),this);
        }

        //if current Signal is the RIGHT april tag then park on robot's right
        else if (Vision.currentSignal == AprilTagVision.Signal.RIGHT ){
            //Park on right
            MecDrive.encoderDrive(HIGH_SPEED, (FULL_TILE_DISTANCE*2),(FULL_TILE_DISTANCE*2),this);
        }

       //telemetry.addData("Current Lift Height", Lift.liftMotor.getCurrentPosition());
        telemetry.addData("# of Cones Delivered During Auto", coneDeliveryTracker);
        telemetry.addData("Status", "Run Time: " + runtime);
        telemetry.update();
        sleep(6000);

    }
}



