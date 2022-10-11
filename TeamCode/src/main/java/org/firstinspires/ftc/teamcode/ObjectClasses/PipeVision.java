package org.firstinspires.ftc.teamcode.ObjectClasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

public class PipeVision {

    public OpenCvCamera camera;
    public PipeDetectionPipeline pipeDetectionPipeline;
    LinearOpMode activeOpMode;
    DriveTrain MecDrive;

    public final ElapsedTime runtime = new ElapsedTime();

    public PipeVision(LinearOpMode activeOpMode, DriveTrain MecDrive){
        this.activeOpMode = activeOpMode;
        this.MecDrive = MecDrive;
    }

    public void init(HardwareMap ahwMap) {
        int cameraMonitorViewId = ahwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", ahwMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(ahwMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        pipeDetectionPipeline = new PipeDetectionPipeline(activeOpMode.telemetry);

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(800,448, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {

            }
        });
    }

    public void SeekPole() {
        MecDrive.strafe = 0;
        MecDrive.drive = 0;
        activeOpMode.telemetry.setMsTransmissionInterval(50);
        runtime.reset();
        while (runtime.seconds() < 4 && activeOpMode.opModeIsActive()) {
            if (pipeDetectionPipeline.isPoleCenter()) {
                //stop moving
                activeOpMode.telemetry.addLine("YELLOW PIPE CENTERED");
                activeOpMode.telemetry.update();
                break;
            }
            else if (pipeDetectionPipeline.isPoleLeft()) {
                //strafe left
                MecDrive.strafe = -.2;
            } else if (pipeDetectionPipeline.isPoleRight()) {
                //strafe right
                MecDrive.strafe = .2;
            }
            else {
                activeOpMode.telemetry.addLine("UH OH DON'T SEE ANY YELLOW PIPES");
                activeOpMode.telemetry.update();
                break;
            }
            MecDrive.MecanumDrive();
        }
        MecDrive.turn = 0;
        MecDrive.strafe = 0;
        MecDrive.drive = 0;
        MecDrive.MecanumDrive();
    }
}
