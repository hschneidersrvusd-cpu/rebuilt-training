package frc.robot.launcher.turret;

import static edu.wpi.first.units.Units.Degrees;

import com.ctre.phoenix6.CANBus;

import edu.wpi.first.units.measure.Angle;

public class TurretConst {
    public static final CANBus LAUNCHER_BUS = new CANBus("launcher");
    public static final int MOTOR_ID = -1;
    public static final int ENCODER_ID = -1;
    public static final Angle MAX_ANGLE = Degrees.of(180.0);
    public static final Angle MIN_ANGLE = Degrees.of(-180.0);
    public static final Angle STOW_YAW = Degrees.of(0.0);
    public static final double ENCODER_TO_MECHANISM_RATIO = 8.5;
}
