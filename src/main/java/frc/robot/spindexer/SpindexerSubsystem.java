package frc.robot.spindexer;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SpindexerSubsystem extends SubsystemBase {
    private final TalonFX motor = new TalonFX(SpindexerConst.MOTOR_ID);

    public SpindexerSubsystem() {
        motor.getConfigurator().apply(SpindexerConfig.motorConfig);
    }

    /** Sets the speed of the spindexer motor in a fraction of the max speed */
    public void moveMotorSpeed(double speed) {
        motor.set(speed);
    }

    /** Runs the spindexer motor toward the feeder */
    public void start() {
        moveMotorSpeed(SpindexerConfig.startMotorSpeed);
    }

    /** Stops the spindexer motor */
    public void stop() {
        moveMotorSpeed(0.0);
    }

    public double getMotorSpeed() {
        return motor.get();
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("motor speed (frac)", this::getMotorSpeed, this::moveMotorSpeed);
        builder.addDoubleProperty(
                "motor velocity (rps)", () -> motor.getVelocity().getValueAsDouble(), null);
    }
}
