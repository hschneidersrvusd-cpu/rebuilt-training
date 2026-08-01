package frc.robot.intake;

import static edu.wpi.first.units.Units.Degrees;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

    private final TalonFX deployMotor = new TalonFX(IntakeConst.DEPLOY_ID);
    private final TalonFX rollerMotor = new TalonFX(IntakeConst.ROLLER_ID);

    public IntakeSubsystem() {
        deployMotor.getConfigurator().apply(IntakeConfig.deployMotorConfig);
        rollerMotor.getConfigurator().apply(IntakeConfig.rollerMotorConfig);

        deployMotor.setPosition(IntakeConst.MIN_ANGLE.in(Degrees));
    }
}
