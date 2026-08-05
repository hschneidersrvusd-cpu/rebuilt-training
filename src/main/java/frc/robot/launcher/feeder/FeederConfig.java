package frc.robot.launcher.feeder;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class FeederConfig {
    public static final double FEEDER_SPEED = 1; // TODO: Find feeder speed
    public static final double R_FEEDER_SPEED = -1; // TODO: Find reverse feeder speed

    public static final TalonFXConfiguration feederConfig = new TalonFXConfiguration();

    static {
        feederConfig.CurrentLimits.StatorCurrentLimit = FeederConst.STATOR_CURRENT_LIMIT;
        feederConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        // Feeding fuel into launcher is positive, spitting fuel out if negative
        feederConfig.MotorOutput.Inverted =
                InvertedValue.Clockwise_Positive; // TODO: Find inverted value
    }
}
