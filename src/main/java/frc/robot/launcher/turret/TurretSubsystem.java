package frc.robot.launcher.turret;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.MetersPerSecondPerSecond;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.Rotations;

import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.FieldConst;

public class TurretSubsystem extends SubsystemBase {
    public static Angle targetYaw = Degrees.of(0.0);
    private final TalonFX motor = new TalonFX(TurretConst.MOTOR_ID, TurretConst.LAUNCHER_BUS);
    private final CANcoder encoder = new CANcoder(TurretConst.ENCODER_ID, TurretConst.LAUNCHER_BUS);
    public boolean enabled = true;

    public TurretSubsystem() {
        motor.getConfigurator().apply(TurretConfig.turretConfig);
        encoder.getConfigurator().apply(TurretConfig.encoderConfig);
    }

    public Angle currentTurretYaw() {
        return motor.getPosition().getValue();
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
        double currentYaw = currentTurretYaw().in(Rotations);
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

    public void calibrateYaw(Angle guessYaw) {
        if (guessYaw.in(Rotations) > TurretConst.MAX_ANGLE.in(Rotations)
                || guessYaw.in(Rotations) < TurretConst.MIN_ANGLE.in(Rotations)) {
            return;
        }
        double encoderGuessedPosition =
                guessYaw.in(Rotations) * TurretConst.ENCODER_TO_MECHANISM_RATIO;
        double encoderCurrentPosition = encoder.getPosition().getValueAsDouble();
        double roundedOffset = Math.round(encoderGuessedPosition - encoderCurrentPosition);
        double minOffset =
                (TurretConst.MIN_ANGLE.in(Rotations) * TurretConst.ENCODER_TO_MECHANISM_RATIO)
                        - encoderGuessedPosition;
        double maxOffset =
                (TurretConst.MAX_ANGLE.in(Rotations) * TurretConst.ENCODER_TO_MECHANISM_RATIO)
                        - encoderGuessedPosition;
        double clampedOffset =
                MathUtil.clamp(roundedOffset, Math.ceil(minOffset), Math.floor(maxOffset));
        encoder.setPosition(clampedOffset + encoderCurrentPosition);
    }

    public double getYawError() {
        return targetYaw.in(Rotations) - currentTurretYaw().in(Rotations);
    }

    public boolean withinTolerance() {
        if (getYawError() < TurretConfig.yawTolerance.in(Rotations)
                && getYawError() > -TurretConfig.yawTolerance.in(Rotations)) {
            return true;
        } else {
            return false;
        }
    }

    public Angle calculateYaw(Angle curRotation, Distance robotX, Distance robotY) {
        Distance x = robotX.minus(FieldConst.HUB_X);
        Distance y = robotY.minus(FieldConst.HUB_Y);
        return Rotations.of(Math.atan2(y.in(Meters), x.in(Meters)) - curRotation.in(Radians));
    }

    public LinearVelocity calculateLaunchSpeed(Angle pitch, Distance robotX, Distance robotY) {
        Distance x = robotX.minus(FieldConst.HUB_X);
        Distance y = robotY.minus(FieldConst.HUB_Y);
        Angle theta = Degrees.of(90).minus(pitch);
        return MetersPerSecond.of(
                x.in(Meters)
                        / Math.cos(theta.in(Radians))
                        * Math.sqrt(
                                FieldConst.GRAVITY.in(MetersPerSecondPerSecond)
                                        / (2
                                                * (x.in(Meters) * Math.tan(theta.in(Radians))
                                                        - y.in(Meters)))));
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addBooleanProperty(
                "turret status (boolean)",
                () -> (enabled),
                (check) -> {
                    if (check) {
                        enabled = true;
                    } else {
                        enabled = false;
                    }
                });
        builder.addDoubleProperty(
                "curret yaw (deg)",
                () -> currentTurretYaw().in(Degrees),
                (guessYaw) -> calibrateYaw(Degrees.of(guessYaw)));
        builder.addDoubleProperty(
                "target yaw (deg)",
                () -> targetYaw.in(Degrees),
                (angle) -> moveRawYaw(Degrees.of(angle)));
        builder.addDoubleProperty("yaw error (deg)", () -> getYawError(), null);
        builder.addBooleanProperty("within tolerance", this::withinTolerance, null);
    }
}
