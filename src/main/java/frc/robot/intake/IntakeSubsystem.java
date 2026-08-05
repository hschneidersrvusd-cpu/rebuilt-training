package frc.robot.intake;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Rotations;

import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

    private final TalonFX deployMotor = new TalonFX(IntakeConst.DEPLOY_ID);
    private final TalonFX rollerMotor = new TalonFX(IntakeConst.ROLLER_ID);

    public IntakeSubsystem() {
        deployMotor.getConfigurator().apply(IntakeConfig.deployMotorConfig);
        rollerMotor.getConfigurator().apply(IntakeConfig.rollerMotorConfig);

        deployMotor.setPosition(IntakeConst.MIN_ANGLE.in(Degrees));
    }

    public void moveRollerSpeed(double speed) {
        rollerMotor.set(speed);
    }

    public void rollersOn() {
        moveRollerSpeed(IntakeConfig.ROLLERS_SPEED);
    }

    public void rollersOff() {
        moveRollerSpeed(0.0);
    }

    public void rollersReverse() {
        moveRollerSpeed(IntakeConfig.ROLLERS_SPEED_BACK);
    }

    public void moveAngle(Angle angle) {
        Angle targetAngle =
                Rotations.of(
                        MathUtil.clamp(
                                angle.in(Rotations),
                                IntakeConst.MIN_ANGLE.in(Rotations),
                                IntakeConst.MAX_ANGLE.in(Rotations)));
        deployMotor.setControl(new MotionMagicVoltage(targetAngle));
    }

    public void moveDown() {
        moveAngle(IntakeConst.MAX_ANGLE);
    }

    public void moveUp() {
        moveAngle(IntakeConst.MIN_ANGLE);
    }

    public void deploy() {
        moveDown();
        rollersOn();
    }

    public void stow() {
        moveUp();
        rollersOff();
    }

    public Angle getCurrentAngle() {
        return deployMotor.getPosition().getValue();
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty(
                "angle (deg)",
                () -> getCurrentAngle().in(Degrees),
                (angle) -> moveAngle(Degrees.of(angle)));
    }
}
