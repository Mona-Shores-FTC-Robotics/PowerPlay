package org.firstinspires.ftc.teamcode.ObjectClasses;

import static java.lang.Math.signum;

import com.qualcomm.robotcore.util.ElapsedTime;

public class TurnPIDController2 {

    private ElapsedTime timer = new ElapsedTime();
    private double m_kP = 0;
    private double m_kI = 0;
    private double m_kD = 0;
    private double m_kF = 0;
    public double m_target =0;
    public double degreeError;
    public double percentError;
    public double output;

    private double m_lastDegreeError;
    double integralSum = 0;

    public TurnPIDController2(double target, double kP, double kI, double kD, double kF) {
        m_kP = kP;
        m_kI = kI;
        m_kD = kD;
        m_kF = kF;

        m_target = target;
        degreeError = target;
        percentError = target;
        timer.reset();
    }

    public double update(double currentState) {
        degreeError = m_target - currentState;
        percentError = .8 * (degreeError/90);

        //integralSum += percent_error * timer.seconds();
        //double derivative = (percent_error - m_lastDegreeError) / timer.seconds();
        //m_lastDegreeError = percent_error;
        //timer.reset();

        //output = (percent_error * m_kP) + (derivative*m_kD) + (integralSum*m_kI) + signum(percent_error)*(m_kF);
        output = (percentError * m_kP) + signum(percentError)*(m_kF);
        return output;

    }

}
