package frc.robot.launcher.turret;

import static edu.wpi.first.units.Units.Degrees;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;

import edu.wpi.first.units.measure.Angle;

public class TurretConfig {
    public static final TalonFXConfiguration turretConfig = new TalonFXConfiguration();
    public static final CANcoderConfiguration encoderConfig = new CANcoderConfiguration();

    public static final Angle yawTolerance = Degrees.of(3);

    static {
        turretConfig.CurrentLimits.StatorCurrentLimit = 30;
        turretConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        turretConfig.MotorOutput.Inverted =
                InvertedValue.Clockwise_Positive; // TODO: Find inverted value
        turretConfig.Feedback.withFeedbackSensorSource(FeedbackSensorSourceValue.RemoteCANcoder);
        turretConfig.Feedback.SensorToMechanismRatio = TurretConst.ENCODER_TO_MECHANISM_RATIO;
        turretConfig.Feedback.FeedbackRemoteSensorID = TurretConst.ENCODER_ID;
    }

    static {
        encoderConfig.MagnetSensor.MagnetOffset = -0.444;
        encoderConfig.MagnetSensor.SensorDirection =
                SensorDirectionValue.Clockwise_Positive; // TODO: Find Sensor Direction Value
    }
}
