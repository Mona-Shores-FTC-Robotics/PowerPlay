package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.ObjectClasses.ButtonConfig;
import org.firstinspires.ftc.teamcode.ObjectClasses.DriveTrain;

@Autonomous(name = "AUTO_SCORE_AND_PARK_OTS")
public class AUTO_SCORE_AND_PARK_OTS extends LinearOpMode {

    int Signal;

    int whole_tile_dis = 50;
    int half_tile_dis = whole_tile_dis / 2;
    int quarter_tile_dis = half_tile_dis / 2;
    int eighth_tile_dis = quarter_tile_dis / 2;

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
            ButtonConfig.ConfigureAllianceColor(this);
            ButtonConfig.ConfigureStartingLocation(this);
            telemetry.addData("Alliance Color ", ButtonConfig.allianceColorString);
            telemetry.addData("Starting Location ", ButtonConfig.startingLocationString);
            telemetry.update();
            //Use Webcam to find out Signal and store in Signal variable
            Signal = 3;
        }

        //Autonomous Routine Example

        //back up to straghten robot
        MecDrive.encoderDrive(.4, -10, -10, this);
        //drive forward
        MecDrive.encoderDrive(.4, whole_tile_dis * 2 + eighth_tile_dis, whole_tile_dis * 2 + eighth_tile_dis, this);
        //drive left to score on the high pole
        MecDrive.strafeDrive(.4, -(half_tile_dis * ButtonConfig.allianceLocationFactor), -(half_tile_dis * ButtonConfig.allianceColor), this);

        if (Signal == 3) {
            //Go right
            MecDrive.strafeDrive(.3, 75, 75, this);
        } else if (Signal == 1) {
            //Go left
            MecDrive.strafeDrive(.3, -20, -20, this);
        }
        //stays there
        else if (Signal == 2) {
            MecDrive.strafeDrive(.3, 30, 30, this);


        }


    }
}




