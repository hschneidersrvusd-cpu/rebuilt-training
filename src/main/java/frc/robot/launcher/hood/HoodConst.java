package frc.robot.launcher.hood;

import static edu.wpi.first.units.Units.Degrees;

import com.ctre.phoenix6.CANBus;

import edu.wpi.first.units.measure.Angle;

public class HoodConst {
    public static final CANBus LAUNCHER_BUS = new CANBus("launcher");
    public static final Angle MIN_ANGLE = Degrees.of(16.394); // MIN_ANGLE is down
    public static final Angle MAX_ANGLE = Degrees.of(115); // MAX_ANGLE is up
    public static final double HOOD_GEAR_RATIO = 24.0 / 1;
    public static final int MOTOR_ID = -1;
}
