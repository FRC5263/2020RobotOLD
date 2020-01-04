/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class MotorSubsystem extends Subsystem {

  SpeedController motor;
  double scalarPower = 1.0;

  public MotorSubsystem(SpeedController motor) {
    this.motor = motor;
  }

  public MotorSubsystem(SpeedController motor, double scalarPower) {
    this.motor = motor;
    this.scalarPower = scalarPower;
  }

  public void powerMotor(double power) {
    if (this.motor != null)
      this.motor.set(power);
  }

  //untested
  public void curvePowerMotor(double power){
    double adjustedPower = Math.pow(Math.abs(power), (1.0 / 3.0));
    if (power < 0) {
        adjustedPower *= -1;
    }
    this.motor.set(adjustedPower);
  }

  

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
