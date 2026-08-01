package frc.robot.spindexer;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class SpindexerConfig {
    public static final TalonFXConfiguration motorConfig = new TalonFXConfiguration();
    public static final double currentLimit = 80.0;

    /** The speed that the motor runs at */
    public static final double startMotorSpeed = 0.5; // TODO: tune the motor speed

    static {
        motorConfig.CurrentLimits.StatorCurrentLimit = 80.0;
        motorConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        // Positive motor speed moves the fuel towards the feeder
        motorConfig.MotorOutput.Inverted =
                InvertedValue.Clockwise_Positive; // TODO: find inverted value
    }
}
