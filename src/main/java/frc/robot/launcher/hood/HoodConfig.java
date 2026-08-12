package frc.robot.launcher.hood;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class HoodConfig {
    public static final TalonFXConfiguration hoodConfig = new TalonFXConfiguration();

    static {
        hoodConfig.CurrentLimits.StatorCurrentLimit = 80.0;
        hoodConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        hoodConfig.MotorOutput.Inverted =
                InvertedValue.Clockwise_Positive; // TODO: find inverted value
        hoodConfig.Feedback.SensorToMechanismRatio = HoodConst.HOOD_GEAR_RATIO;
    }
}
