// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.intake.IntakeSubsystem;
import frc.robot.launcher.feeder.FeederSubsystem;
import frc.robot.launcher.hood.HoodSubsystem;
import frc.robot.launcher.shooter.ShooterSubsystem;
import frc.robot.launcher.turret.TurretSubsystem;
import frc.robot.spindexer.SpindexerSubsystem;

public class Robot extends TimedRobot {
    private final CommandXboxController controller = new CommandXboxController(0);
    private final SpindexerSubsystem spindexer = new SpindexerSubsystem();
    private final IntakeSubsystem intake = new IntakeSubsystem();
    private final FeederSubsystem feeder = new FeederSubsystem();
    private final HoodSubsystem hood = new HoodSubsystem();
    private final ShooterSubsystem shooter = new ShooterSubsystem();
    private final TurretSubsystem turret = new TurretSubsystem();

    public Robot() {
        initDashboard();
        initBindings();
    }

    public void initDashboard() {
        SmartDashboard.putData("Spindexer", spindexer);
        SmartDashboard.putData("Intake", intake);
        SmartDashboard.putData("Feeder", feeder);
        SmartDashboard.putData("Hood", hood);
        SmartDashboard.putData("Shooter", shooter);
        SmartDashboard.putData("Turret", turret);
    }

    public void initBindings() {
        controller.leftBumper().whileTrue(spindexer.runOnce(spindexer::start));
        controller.povDown().onTrue(intake.runOnce(intake::deploy));
        controller.povUp().onTrue(intake.runOnce(intake::stow));
    }

    public Command automaticTargeting() {
        return Commands.defer(
                    return Commands.parallel(
                            CommandsUtil.asDefault(
                                    Commands.runOnce(
                                            () -> hood.calculateYaw())),
                            CommandsUtil.asDefault(
                                    hood.runOnce(
                                            () -> turret.calculateLaunchSpeed())));
    }

    @Override
    public void robotInit() {}

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {}

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void teleopInit() {}

    @Override
    public void teleopPeriodic() {}

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    @Override
    public void testInit() {}

    @Override
    public void testPeriodic() {}

    @Override
    public void simulationInit() {}

    @Override
    public void simulationPeriodic() {}
}
