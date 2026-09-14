package frc.robot.drivetrain;

import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.LinearVelocity;

public class DriveConfig {
    public static final int controllerPort = 0;
    public static boolean enabled = true;
    public static final LinearVelocity MAX_SPEED = MetersPerSecond.of(3);
    public static final AngularVelocity MAX_ROTATION_SPEED = RotationsPerSecond.of(0.75);
    public static final double GENERAL_DEADBAND = 0.1;
    public static final LinearVelocity LINEAR_SPEED_DEADBAND = MAX_SPEED.times(GENERAL_DEADBAND);
    public static final AngularVelocity ANGULAR_SPEED_DEADBAND =
            MAX_ROTATION_SPEED.times(GENERAL_DEADBAND);
}
