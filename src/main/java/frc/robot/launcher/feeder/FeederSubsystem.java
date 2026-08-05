package frc.robot.launcher.feeder;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.spindexer.SpindexerConst;

public class FeederSubsystem extends SubsystemBase {
    private final TalonFX motor = new TalonFX(SpindexerConst.MOTOR_ID);

    public void setMotorSpeed(double speed) {
        motor.set(speed);
    }

    /** Sets the motor speed to 0 */
    public void stop() {
        setMotorSpeed(0.0);
    }

    /** Sets the motor speed to the set speed */
    public void start() {
        setMotorSpeed(FeederConfig.FEEDER_SPEED);
    }

    /** Sets the motor speed to the set reverse speed */
    public void reverse() {
        setMotorSpeed(FeederConfig.R_FEEDER_SPEED);
    }

    /** Gets the current motor speed */
    public double getCurrentSpeed() {
        return motor.get();
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("motor speed (frac)", this::getCurrentSpeed, this::setMotorSpeed);
    }
}
