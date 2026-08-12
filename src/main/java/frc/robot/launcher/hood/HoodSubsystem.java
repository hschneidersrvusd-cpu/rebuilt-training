package frc.robot.launcher.hood;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Rotations;

import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HoodSubsystem extends SubsystemBase {
    private final TalonFX motor = new TalonFX(HoodConst.MOTOR_ID, HoodConst.LAUNCHER_BUS);
    private Angle targetPitch;

    public HoodSubsystem() {
        motor.getConfigurator().apply(HoodConfig.hoodConfig);
        motor.setPosition(HoodConst.MIN_ANGLE);
    }

    /** Moves hood to the desired angle */
    public void moveHoodPitch(Angle angle) {
        targetPitch =
                Rotations.of(
                        MathUtil.clamp(
                                angle.in(Rotations),
                                HoodConst.MIN_ANGLE.in(Rotations),
                                HoodConst.MAX_ANGLE.in(Rotations)));
        motor.setControl(new MotionMagicVoltage(targetPitch));
    }

    /** Brings the hood to the lowest position */
    public void stow() {
        moveHoodPitch(HoodConst.MIN_ANGLE);
    }

    /** Gets the current pitch (angle) of the hood */
    public Angle getCurrentPitch() {
        return motor.getPosition().getValue();
    }

    public Angle getTargetPitch() {
        return targetPitch;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty(
                "pitch (deg)",
                () -> getCurrentPitch().in(Degrees),
                (angle) -> moveHoodPitch(Degrees.of(angle)));
        builder.addDoubleProperty(
                "target pitch (deg)",
                () -> getTargetPitch().in(Degrees),
                (angle) -> moveHoodPitch(Degrees.of(angle)));
    }
}
