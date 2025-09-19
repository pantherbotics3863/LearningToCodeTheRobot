// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Intake;

import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeRollers extends SubsystemBase {

  private final TalonFX m_motor = new TalonFX(IntakeConstants.kRollerMotorID);
  private final VoltageOut m_voltageOut = new VoltageOut(0);

  public IntakeRollers() {
    m_motor.getConfigurator().apply(IntakeConstants.kRollerMotorConfigs);
  }

  public Command runVoltage(Voltage voltage) {
    return this.runOnce(() -> m_motor.setControl(m_voltageOut.withOutput(voltage)))
    .andThen(Commands.idle())
    .withName("runVoltage")
    ;
  }


}
