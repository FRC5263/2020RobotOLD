/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DigitalInput;
/**
 * Add your docs here.
 */
public class DigitalSignalSubsystem extends Subsystem {

  DigitalInput device;

  public DigitalSignalSubsystem (DigitalInput device) {
    this.device = device;
  }

  public boolean getDigitalSignal(){
    if(this.device != null)
      return this.device.get();
    else 
      return false;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
