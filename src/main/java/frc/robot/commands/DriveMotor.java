/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Bot;
import frc.robot.helpers.CommandFinishedFunction;
import frc.robot.subsystems.MotorSubsystem;

public class DriveMotor extends Command {

  Bot robot;
  double power;
  double seconds;
  CommandFinishedFunction function;
  MotorSubsystem subsystem; 
  boolean finishEarly = false;

  public DriveMotor(Bot robot, String subsystemName, double power, double seconds) {
    this.robot = robot;
    this.power = power;
    this.seconds = seconds;
    try {
      this.subsystem = (MotorSubsystem) this.robot.getSubsystem(subsystemName);
    } catch ( Exception e) {
      finishEarly = true;
    }
  }

  public DriveMotor(Bot robot, String subsystemName, double power, CommandFinishedFunction function, double timeoutSeconds) {
    this.robot = robot;
    this.power = power;
    this.function = function;
    this.seconds = timeoutSeconds;
    try {
      this.subsystem = (MotorSubsystem) this.robot.getSubsystem(subsystemName);
    } catch ( Exception e) {
      finishEarly = true;
    }
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    this.subsystem.powerMotor(this.power);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    if(function != null) {
      return function.isFinished(robot) || isTimedOut() || finishEarly;
    } else {
      return isTimedOut() || finishEarly;
    }
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    if(this.subsystem != null){
      this.subsystem.powerMotor(0.0);
    }
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    if(this.subsystem != null){
      this.subsystem.powerMotor(0.0);
    }
  }
}
