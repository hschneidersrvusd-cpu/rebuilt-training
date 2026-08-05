package frc.robot.intake;

import static edu.wpi.first.units.Units.Degrees;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class IntakeConfig {
    public static final TalonFXConfiguration deployMotorConfig = new TalonFXConfiguration();
    public static final TalonFXConfiguration rollerMotorConfig = new TalonFXConfiguration();

    public static final double ROLLERS_SPEED = 0.5;
    public static final double ROLLERS_SPEED_BACK =
            -0.5; // Tune the speed of the rollers moving backwards

    static {
        deployMotorConfig.CurrentLimits.StatorCurrentLimit = 80.0;
        deployMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        // deploying intake is positive, stowing intake is negative
        deployMotorConfig.MotorOutput.Inverted =
                InvertedValue.Clockwise_Positive; // TODO: find inverted value

        rollerMotorConfig.CurrentLimits.StatorCurrentLimit = 80.0;
        rollerMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        // Sucking up fuel is positive, spitting out fuel is negative
        rollerMotorConfig.MotorOutput.Inverted =
                InvertedValue.Clockwise_Positive; // TODO: find inverted value

        deployMotorConfig.Feedback.SensorToMechanismRatio = IntakeConst.GEAR_RATIO;

        deployMotorConfig.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
        deployMotorConfig.SoftwareLimitSwitch.ForwardSoftLimitThreshold =
                IntakeConst.MAX_ANGLE.in(Degrees);

        deployMotorConfig.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
        deployMotorConfig.SoftwareLimitSwitch.ReverseSoftLimitThreshold =
                IntakeConst.MIN_ANGLE.in(Degrees);
    }
}
