/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * Add your docs here.
 */
public class PneumaticsSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private DoubleSolenoid solenoid;

  public PneumaticsSubsystem(DoubleSolenoid doubleSolenoid) {
    this.solenoid = doubleSolenoid;
    this.solenoid.set(Value.kOff);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void setDirectionForward() {
    if(this.solenoid != null){
      this.solenoid.set(Value.kForward);
    }
  }

  public void setDirectionReverse() {
    if(this.solenoid != null){
      this.solenoid.set(Value.kReverse);
    }
  }

  public void setSolenoidOff() {
    if(this.solenoid != null){
      this.solenoid.set(Value.kOff);
    }
  }
}
