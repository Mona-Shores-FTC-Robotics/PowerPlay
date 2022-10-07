package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.ObjectClasses.ButtonConfig;
import org.firstinspires.ftc.teamcode.ObjectClasses.DriveTrain;

//This import lets us reference our constants without having to use the GameConstants class name
import static org.firstinspires.ftc.teamcode.ObjectClasses.DriveTrain.HIGH_SPEED;
import static org.firstinspires.ftc.teamcode.ObjectClasses.DriveTrain.LOW_SPEED;
import static org.firstinspires.ftc.teamcode.ObjectClasses.DriveTrain.MED_SPEED;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.EIGHTH_TILE_DISTANCE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.FULL_TILE_DISTANCE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.HALF_TILE_DISTANCE;
import static org.firstinspires.ftc.teamcode.ObjectClasses.GameConstants.QUARTER_TILE_DISTANCE;

@Autonomous(name = "AUTO_JUST_PARK_MJL")
public class AUTO_MJL_JUST_PARK extends LinearOpMode {

    // Variable to store the Signal
    int Signal;
    DriveTrain MecDrive = new DriveTrain();
    ButtonConfig ButtonConfig = new ButtonConfig();

    @Override

    public void runOpMode() {
        telemetry.addData("Status", "Initializing");
        telemetry.update();

        MecDrive.init(hardwareMap);

        ButtonConfig.init();

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        sleep(1000);

        while (!isStarted()) {
            //Use Webcam to find out Signal and store in Signal variable
            Signal = 1;

            // Let the user set alliance color and starting location variables for use in code
            ButtonConfig.ConfigureAllianceColor(this);
            ButtonConfig.ConfigureStartingLocation( this);
            telemetry.addData("Alliance Color ", ButtonConfig.allianceColorString);
            telemetry.addData("Starting Location ", ButtonConfig.startingLocationString);
            telemetry.update();


        }

        //Drive backwards into wall to make sure we are aligned
        MecDrive.encoderDrive(.4, -10, -10, this);

        //Drive forward 2 tiles plus a little bit more to get into position for deciding where to park
        MecDrive.encoderDrive(.4, FULL_TILE_DISTANCE*2+EIGHTH_TILE_DISTANCE, FULL_TILE_DISTANCE*2+EIGHTH_TILE_DISTANCE, this);


        //Decide where to park
        //if Signal is 1 then  strafe one tile to the left to park
        if(Signal == 1) {
            MecDrive.strafeDrive(.3, -FULL_TILE_DISTANCE, -FULL_TILE_DISTANCE, this);
        }

        //if Signal is 2 then do nothing because already at the parking spot
        else if (Signal == 2) {
        }

        //if Signal is 3 then strafe one tile to the right
         else if (Signal == 3){
                MecDrive.strafeDrive(.3, FULL_TILE_DISTANCE, FULL_TILE_DISTANCE, this);
        }
    }
}



