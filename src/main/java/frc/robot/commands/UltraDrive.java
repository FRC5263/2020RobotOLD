/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Bot;
import frc.robot.subsystems.DriveTrainSubsystem;

public class UltraDrive extends Command {

  double power;
  Bot robot;
  private DriveTrainSubsystem drivetrain;
  boolean finishEarly = false;


  public UltraDrive(Bot robot, String subsystemName, double power) {
    this.robot = robot;
    this.power = power;

    try{
      this.drivetrain = (DriveTrainSubsystem) this.robot.getSubsystem(Bot.DRIVETRAIN);
    }catch(Exception e){
      this.finishEarly = true;
    }
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    drivetrain.sonicSetAutomatic();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
