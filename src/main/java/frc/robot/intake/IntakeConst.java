package frc.robot.intake;

import static edu.wpi.first.units.Units.Degrees;

import edu.wpi.first.units.measure.Angle;

public class IntakeConst {
    public static final int DEPLOY_ID = -1;
    public static final int ROLLER_ID = -1;

    public static final double GEAR_RATIO = 96.0;
    public static final Angle MIN_ANGLE = Degrees.of(0.0); // Min angle is up
    public static final Angle MAX_ANGLE = Degrees.of(128.26); // Max angle is down
}
