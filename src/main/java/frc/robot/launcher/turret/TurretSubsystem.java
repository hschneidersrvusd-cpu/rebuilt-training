package frc.robot.launcher.turret;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Rotations;

import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TurretSubsystem extends SubsystemBase {
    public static Angle targetYaw = Degrees.of(0.0);
    private final TalonFX motor = new TalonFX(TurretConst.MOTOR_ID, TurretConst.LAUNCHER_BUS);
    private final CANcoder encoder = new CANcoder(TurretConst.ENCODER_ID, TurretConst.LAUNCHER_BUS);
    public final boolean enabled = true;

    public TurretSubsystem() {
        motor.getConfigurator().apply(TurretConfig.turretConfig);
        encoder.getConfigurator().apply(TurretConfig.encoderConfig);
    }

    public double currentTurretYaw() {
        return motor.getPosition().getValue().in(Rotations);
    }

    public void moveRawYaw(Angle angle) {
        if (enabled) {
            targetYaw =
                    Rotations.of(
                            MathUtil.clamp(
                                    angle.in(Rotations),
                                    TurretConst.MIN_ANGLE.in(Rotations),
                                    TurretConst.MAX_ANGLE.in(Rotations)));
            motor.setControl(new MotionMagicVoltage(targetYaw));
        }
    }

    public void stow() {
        moveRawYaw((TurretConst.STOW_YAW));
    }

    public void moveYaw(Angle yaw) {
        double currentYaw = currentTurretYaw();
        double desiredYaw = yaw.in(Rotations);
        double relCurrentvalue = desiredYaw - currentYaw;
        double minRelYaw = TurretConst.MIN_ANGLE.in(Rotations) - desiredYaw;
        double maxRelYaw = TurretConst.MAX_ANGLE.in(Rotations) - desiredYaw;
        moveRawYaw(
                Rotations.of(
                        desiredYaw
                                + MathUtil.clamp(
                                        Math.round(relCurrentvalue),
                                        Math.ceil(minRelYaw),
                                        Math.floor(maxRelYaw))));
    }
}
