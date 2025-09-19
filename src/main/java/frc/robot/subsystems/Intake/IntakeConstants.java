// The Unlicense ;
package frc.robot.subsystems.Intake;

import static edu.wpi.first.units.Units.Degrees;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;

import edu.wpi.first.units.measure.Angle;

/** Add your docs here. */
public class IntakeConstants {

    public static final double kPivotRatio = (9d/1d) * (50d/22d) * (48d/12d);

    public static final int kRollerMotorID = 29;
    public static final int kPivotMotorID = 27;

    public static final Angle kPivotDownAngle = Degrees.of(-42);
    public static final Angle kStowAngle = Degrees.of(50);

    public static final TalonFXConfiguration kRollerMotorConfigs = new TalonFXConfiguration()
        .withCurrentLimits(new CurrentLimitsConfigs()
            .withStatorCurrentLimit(90)
            .withSupplyCurrentLimit(120)
        )
    ;

    public static final TalonFXConfiguration kPivotMotorConfigs = new TalonFXConfiguration()
        .withCurrentLimits(new CurrentLimitsConfigs()
            .withStatorCurrentLimit(50)
            .withSupplyCurrentLimit(120)
        )
        .withSlot0(new Slot0Configs()
            .withKP(0)
            .withKD(0)
            .withKS(3)
            .withKV(0)
            .withKG(10)
            .withKA(0)
        )
        .withMotionMagic(new MotionMagicConfigs()
            .withMotionMagicAcceleration(50)   
            .withMotionMagicCruiseVelocity(50)
        )
        .withOpenLoopRamps(new OpenLoopRampsConfigs()
            .withVoltageOpenLoopRampPeriod(1)
        )
        .withFeedback(new FeedbackConfigs()
            .withSensorToMechanismRatio(kPivotRatio)
        )
        .withMotorOutput(
            new MotorOutputConfigs()
            .withInverted(InvertedValue.Clockwise_Positive)
        )
    ;

}
