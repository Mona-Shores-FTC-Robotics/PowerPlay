package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.ObjectClasses.ButtonConfig;
import org.firstinspires.ftc.teamcode.ObjectClasses.DriveTrain;
import org.firstinspires.ftc.teamcode.ObjectClasses.OpenCVWebcam;

@Autonomous(name = "AutoOp")
public class AutoOp extends LinearOpMode {

    int Signal;

    DriveTrain MecDrive = new DriveTrain();
    OpenCVWebcam Vision = new OpenCVWebcam();
    ButtonConfig ButtonConfig = new ButtonConfig();

    @Override

    public void runOpMode() {

        MecDrive.init(hardwareMap);
        Vision.init(hardwareMap);
        ButtonConfig.init();

        while (!isStarted()) {
            ButtonConfig.ConfigureAllianceColor(this);
            ButtonConfig.ConfigureStartingLocation( this);
            telemetry.addData("Alliance Color ", ButtonConfig.allianceColorString);
            telemetry.addData("Starting Location ", ButtonConfig.startingLocationString);
            telemetry.update();
            //Use Webcam to find out Signal and store in Signal variable
            Signal = 1;
        }

        //Autonomous Routine Example
        //MecDrive.encoderDrive(.3, 10, 10, this);
        //MecDrive.strafeDrive(.3, -10, -10, this);

        MecDrive.turnDegrees(-90,this);

        MecDrive.turnToAngle(45,this);

    }
}



