package frc.robot;

import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.MetersPerSecondPerSecond;

import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.LinearAcceleration;

public class FieldConst {
    public static final Distance HUB_X = Inches.of(182.11);
    public static final Distance HUB_Y = Inches.of(158.84);
    public static final LinearAcceleration GRAVITY = MetersPerSecondPerSecond.of(-9.8);
}
