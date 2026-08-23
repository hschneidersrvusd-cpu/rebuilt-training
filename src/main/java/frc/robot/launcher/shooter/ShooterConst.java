package frc.robot.launcher.shooter;

import static edu.wpi.first.units.Units.RotationsPerSecond;

import com.ctre.phoenix6.CANBus;

import edu.wpi.first.units.measure.AngularVelocity;

public class ShooterConst {
    public static final CANBus LAUNCHER_BUS = new CANBus("launcher");
    public static final int MOTOR_ID = -1; // TODO: Find motor ID
    public static final AngularVelocity M_ANGULAR_VELOCITY =
            RotationsPerSecond.of(5); // TODO: Find Max Angular Velocity
}
