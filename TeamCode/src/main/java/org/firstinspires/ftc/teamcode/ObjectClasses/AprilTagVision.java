package org.firstinspires.ftc.teamcode.ObjectClasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

public class AprilTagVision {

    public OpenCvCamera camera;
    public AprilTagDetectionPipeline aprilTagDetectionPipeline;

    public enum Signal {LEFT, MIDDLE, RIGHT}

    //Set default to MIDDLE in case something goes wrong with vision
    public Signal currentSignal = Signal.MIDDLE;

    // NOTE: this calibration is for the C920 webcam at 800x448.
    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;

    // UNITS ARE METERS
    double tagsize = 0.166;

    int ID_TAG_FOR_LEFT = 0;
    int ID_TAG_FOR_MIDDLE = 1;
    int ID_TAG_FOR_RIGHT = 2;

    int cameraMonitorViewId;

    public AprilTagDetection tagOfInterest = null;

    public void init(HardwareMap ahwMap) {

        int cameraMonitorViewId = ahwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", ahwMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(ahwMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);
        camera.setPipeline(aprilTagDetectionPipeline);

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

    public void CheckForAprilTags(LinearOpMode activeOpMode)
    {
        ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

        if(currentDetections.size() != 0)
            {
                for(AprilTagDetection tag : currentDetections)
                {
                    if(tag.id == ID_TAG_FOR_LEFT || tag.id == ID_TAG_FOR_MIDDLE || tag.id == ID_TAG_FOR_RIGHT )
                    {
                        tagOfInterest = tag;

                        if(tagOfInterest == null)
                        {
                            currentSignal = Signal.MIDDLE;
                        }
                        else
                        {
                            if (tagOfInterest.id == ID_TAG_FOR_LEFT) {
                                currentSignal = Signal.LEFT;
                            }
                            else if (tagOfInterest.id == ID_TAG_FOR_MIDDLE) {
                                currentSignal = Signal.MIDDLE;
                            }
                            else if (tagOfInterest.id == ID_TAG_FOR_RIGHT) {
                                currentSignal = Signal.RIGHT;
                            }
                        }
                        break;
                    }
                }
            }
    }
}
