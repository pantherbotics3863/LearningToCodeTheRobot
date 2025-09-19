// All rights reserved by Pantherbotics

package frc.robot.subsystems.Intake;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.controls.MotionMagicTorqueCurrentFOC;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.measure.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakePivot extends SubsystemBase {

  private boolean m_hasBeenZeroed = false;
  private final TalonFX m_motor = new TalonFX(IntakeConstants.kPivotMotorID);
  private final MotionMagicTorqueCurrentFOC m_positionRequest = new MotionMagicTorqueCurrentFOC(0);

  public IntakePivot() {
    m_motor.getConfigurator().apply(IntakeConstants.kPivotMotorConfigs);
  }


  public Command setPosition(Angle angle) {
    return this.runOnce(()->{
      if (m_hasBeenZeroed){
        m_motor.setControl(
          m_positionRequest
        );
      }
    }).andThen(Commands.idle())
    ;
  }


  public Command zero(){
    
    return this.runEnd(()->
      m_motor.setControl(new VoltageOut(-3)) 
    , ()->{
      if (m_motor.getTorqueCurrent().getValue().lt(Amps.of(-30))){
        m_motor.setPosition(IntakeConstants.kPivotDownAngle);
        m_hasBeenZeroed = true;
        System.out.println("ZEROED!!!!!!   :)");
      }
    }
    ).until(()->m_motor.getTorqueCurrent().getValue().lt(Amps.of(-30)))
    
    ;
  
  }

}
