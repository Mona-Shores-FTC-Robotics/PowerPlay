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
    public PipeDetectionPipeline pipeDetectionPipeline;

    public enum Signal {LEFT, MIDDLE, RIGHT}
    public Signal currentSignal = Signal.MIDDLE;

    static final double FEET_PER_METER = 3.28084;

    // Lens intrinsics
    // UNITS ARE PIXELS
    // NOTE: this calibration is for the C920 webcam at 800x448.
    // You will need to do your own calibration for other configurations!
    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;

    // UNITS ARE METERS
    double tagsize = 0.166;

    int ID_TAG_FOR_LEFT = 0;
    int ID_TAG_FOR_MIDDLE = 1;
    int ID_TAG_FOR_RIGHT = 2;

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
        activeOpMode.telemetry.setMsTransmissionInterval(50);
        ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

        if(currentDetections.size() != 0)
            {
                boolean tagFound = false;
                for(AprilTagDetection tag : currentDetections)
                {
                    if(tag.id == ID_TAG_FOR_LEFT || tag.id == ID_TAG_FOR_MIDDLE || tag.id == ID_TAG_FOR_RIGHT )
                    {
                        tagOfInterest = tag;
                        tagFound = true;
                        break;
                    }
                }

                if(tagFound)
                {
                    activeOpMode.telemetry.addLine("Tag of interest is in sight!\n\nLocation data:");
                    tagToTelemetry(tagOfInterest, activeOpMode);
                }
                else
                {
                   activeOpMode.telemetry.addLine("Don't see tag of interest :(");

                    if(tagOfInterest == null)
                    {
                       activeOpMode.telemetry.addLine("(The tag has never been seen)");
                    }
                    else
                    {
                      activeOpMode.telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                        tagToTelemetry(tagOfInterest, activeOpMode);
                    }
                }

            }
            else
            {
              activeOpMode.telemetry.addLine("Don't see tag of interest :(");

                if(tagOfInterest == null)
                {
                   activeOpMode.telemetry.addLine("(The tag has never been seen)");
                }
                else
                {
                   activeOpMode.telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                    tagToTelemetry(tagOfInterest, activeOpMode);
                }

            }
        activeOpMode.telemetry.update();
    }

    public void SetSignal(LinearOpMode activeOpMode)
    {
        if(tagOfInterest != null)
        {
           activeOpMode.telemetry.addLine("Tag snapshot:\n");
            tagToTelemetry(tagOfInterest, activeOpMode);
           activeOpMode.telemetry.update();
        }
        else
        {
            activeOpMode.telemetry.addLine("No tag snapshot available, it was never sighted during the init loop :(");
            activeOpMode.telemetry.update();
        }

        /* Actually do something useful */
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
    }

    void tagToTelemetry(AprilTagDetection detection, LinearOpMode activeOpMode)
    {
        activeOpMode.telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
        activeOpMode.telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x*FEET_PER_METER));
        activeOpMode.telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y*FEET_PER_METER));
        activeOpMode.telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z*FEET_PER_METER));
        activeOpMode.telemetry.addLine(String.format("Rotation Yaw: %.2f degrees", Math.toDegrees(detection.pose.yaw)));
        activeOpMode.telemetry.addLine(String.format("Rotation Pitch: %.2f degrees", Math.toDegrees(detection.pose.pitch)));
        activeOpMode.telemetry.addLine(String.format("Rotation Roll: %.2f degrees", Math.toDegrees(detection.pose.roll)));
    }
}
