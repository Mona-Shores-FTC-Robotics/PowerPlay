package org.firstinspires.ftc.teamcode.ObjectClasses;

import com.qualcomm.robotcore.util.ElapsedTime;

public class TurnPIDController2 {
    private double m_lastDegreesLeftToTurn;
    double integralSum = 0;
    private ElapsedTime timer = new ElapsedTime();
    private double m_kP = 0;
    private double m_kI = 0;
    private double m_kD = 0;
    private double m_kF = 0;
    public double m_target =0;
    public double m_degreesLeftToTurn;

    public TurnPIDController2(double target, double kP, double kI, double kD, double kF) {
        m_kP = kP;
        m_kI = kI;
        m_kD = kD;
        m_kF = kF;

        m_target = target;
        m_degreesLeftToTurn = target;
        m_lastDegreesLeftToTurn = target;

        timer.reset();
    }

    public double update(double currentState) {
        m_degreesLeftToTurn = m_target - currentState;
        integralSum += m_degreesLeftToTurn * timer.seconds();
        double derivative = (m_degreesLeftToTurn - m_lastDegreesLeftToTurn) / timer.seconds();
        m_lastDegreesLeftToTurn = m_degreesLeftToTurn;
        timer.reset();
        double output = (m_degreesLeftToTurn * m_kP) + (derivative*m_kD) + (integralSum*m_kI) + (m_kF);
        return output;
    }

}
