import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SpindexerSubsystem extends SubsystemBase {
    private final TalonFX motor = new TalonFX(-1);
    private static final TalonFXConfiguration motorConfig = new TalonFXConfiguration();

    static {
        motorConfig.CurrentLimits.StatorCurrentLimit = 80.0;
        motorConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        // Positive motor speed moves the fuel towards the feeder
        motorConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
    }

    public SpindexerSubsystem() {
        motor.getConfigurator().apply(motorConfig);
    }

    public void moveMotorSpeed(double speed) {
        motor.set(speed);
    }

    public void start() {
        moveMotorSpeed(0.5);
    }

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
