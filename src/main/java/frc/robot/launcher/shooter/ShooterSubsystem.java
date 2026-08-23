package frc.robot.launcher.shooter;

import static edu.wpi.first.units.Units.RotationsPerSecond;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {
    private static final TalonFX rightLeaderMotor =
            new TalonFX(ShooterConst.MOTOR_ID, ShooterConst.LAUNCHER_BUS);
    private static final TalonFX leftFollowerMotor =
            new TalonFX(ShooterConst.MOTOR_ID, ShooterConst.LAUNCHER_BUS);
    private static AngularVelocity targetVelocity;
    private static boolean enabled = true;

    public ShooterSubsystem() {
        rightLeaderMotor.getConfigurator().apply(ShooterConfig.motorConfig);
        leftFollowerMotor.getConfigurator().apply(ShooterConfig.motorConfig);
        leftFollowerMotor.setControl(
                new Follower(rightLeaderMotor.getDeviceID(), MotorAlignmentValue.Opposed));
    }

    public void moveAngularVelocity(AngularVelocity velocity) {
        if (enabled) {
            targetVelocity = velocity;
            rightLeaderMotor.setControl(new MotionMagicVelocityVoltage(targetVelocity));
        }
    }

    public void stop() {
        targetVelocity = RotationsPerSecond.of(0);
        rightLeaderMotor.setControl(new MotionMagicVelocityVoltage(targetVelocity));
    }

    public AngularVelocity getAngularVelocity() {
        return rightLeaderMotor.getVelocity().getValue();
    }

    public AngularVelocity getTargetAngularVelocity() {
        return targetVelocity;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty(
                "angular velocity (rps)", () -> getAngularVelocity().in(RotationsPerSecond), null);
        builder.addDoubleProperty(
                "target angular velocity (rps)",
                () -> getTargetAngularVelocity().in(RotationsPerSecond),
                (velocity) -> moveAngularVelocity(RotationsPerSecond.of(velocity)));
        builder.addBooleanProperty(
                "enabled",
                () -> enabled,
                (enable) -> {
                    if (enable) enabled = true;
                    else enabled = false;
                });
    }
}
