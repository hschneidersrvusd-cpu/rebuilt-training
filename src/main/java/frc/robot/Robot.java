// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import frc.robot.drivetrain.CommandSwerveDrivetrain;
import frc.robot.drivetrain.DriveConfig;
import frc.robot.drivetrain.TunerConstants;
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
    private final CommandSwerveDrivetrain drivetrain =
            new CommandSwerveDrivetrain(
                    TunerConstants.DrivetrainConstants,
                    TunerConstants.FrontLeft,
                    TunerConstants.FrontRight,
                    TunerConstants.BackLeft,
                    TunerConstants.BackRight);

    public Robot() {
        initDashboard();
        initBindings();
    }

    public SwerveRequest getSwerveRequest(double xThrottle, double yThrottle, double rotThrottle) {
        if (!DriveConfig.enabled) {
            return new SwerveRequest.Idle();
        } else {
            SwerveRequest.FieldCentric swerveRequest = new SwerveRequest.FieldCentric();
            return swerveRequest
                    .withDeadband(DriveConfig.LINEAR_SPEED_DEADBAND)
                    .withRotationalDeadband(DriveConfig.ANGULAR_SPEED_DEADBAND)
                    .withDriveRequestType(DriveRequestType.Velocity)
                    .withVelocityX(DriveConfig.MAX_SPEED.times(xThrottle))
                    .withVelocityY(DriveConfig.MAX_SPEED.times(yThrottle))
                    .withRotationalRate(DriveConfig.MAX_ROTATION_SPEED.times(rotThrottle));
        }
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
        drivetrain.setDefaultCommand(
                drivetrain.applyRequest(
                        () ->
                                getSwerveRequest(
                                        -controller.getLeftY(),
                                        -controller.getLeftX(),
                                        -controller.getRightX())));
    }

    // public Command automaticTargeting() {
    //     return Commands.defer(
    //                 return Commands.parallel(
    //                         CommandsUtil.asDefault(
    //                                 Commands.runOnce(
    //                                         () -> hood.calculateYaw())),
    //                         CommandsUtil.asDefault(
    //                                 hood.runOnce(
    //                                         () -> turret.calculateLaunchSpeed())));
    // }

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
