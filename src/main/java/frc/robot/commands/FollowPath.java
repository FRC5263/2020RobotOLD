/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Bot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.DriveTrainSubsystem;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
import edu.wpi.first.wpilibj.Notifier;

public class FollowPath extends Command { 

  private Bot robot;
  private DriveTrainSubsystem drivetrain;
  private boolean finishEarly = false;

  private boolean isFinished = false;

  private EncoderFollower m_left_follower;
  private EncoderFollower m_right_follower;
  
  private Notifier m_follower_notifier;


  private static final int k_ticks_per_rev = 360;
  private static final double k_wheel_diameter = 6.0;
  private static final double k_max_velocity = 100;

  private static final String k_path_name = "TestDylan";

  public FollowPath(Bot robot) {
    this.robot = robot;
    try {
        this.drivetrain = (DriveTrainSubsystem) this.robot.getSubsystem(Bot.DRIVETRAIN);
    } catch (Exception e) {
        this.finishEarly = true;
    }
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {;

    drivetrain.resetCompass();
    drivetrain.resetEncoders();


    //REVIEW: MAKE SURE TO FIX THIS CODE WITH PATHFINDER 3.1 !!! SWAP LEFT AND RIGHT
    Trajectory left_trajectory = PathfinderFRC.getTrajectory(k_path_name + ".right");
    Trajectory right_trajectory = PathfinderFRC.getTrajectory(k_path_name + ".left");

    m_left_follower = new EncoderFollower(left_trajectory);
    m_right_follower = new EncoderFollower(right_trajectory);

    m_left_follower.configureEncoder(drivetrain.getLeftEncoder(), k_ticks_per_rev, k_wheel_diameter);
    // You must tune the PID values on the following line!
    m_left_follower.configurePIDVA(0.5, 0.0, 0.0, 1 / k_max_velocity, 0);

    m_right_follower.configureEncoder(drivetrain.getRightEncoder(), k_ticks_per_rev, k_wheel_diameter);
    // You must tune the PID values on the following line!
    m_right_follower.configurePIDVA(0.5, 0.0, 0.0, 1 / k_max_velocity, 0);
    
    m_follower_notifier = new Notifier(this::followPath);
    m_follower_notifier.startPeriodic(left_trajectory.get(0).dt);
  }

  private void followPath() {
    if (m_left_follower.isFinished() || m_right_follower.isFinished()) {
      m_follower_notifier.stop();
      drivetrain.drive(0, 0);
      isFinished = true;
    } else {
      double left_speed = m_left_follower.calculate(drivetrain.getLeftEncoder());
      SmartDashboard.putNumber("left speed", left_speed);
      double right_speed = m_right_follower.calculate(drivetrain.getRightEncoder());
      SmartDashboard.putNumber("right speed", right_speed);
      double heading = drivetrain.getRotation();
      SmartDashboard.putNumber("heading", heading);
      double desired_heading = Pathfinder.r2d(m_left_follower.getHeading());
      SmartDashboard.putNumber("desired heading", desired_heading);
      double heading_difference = Pathfinder.boundHalfDegrees(desired_heading - heading);
      SmartDashboard.putNumber("heading difference", heading_difference);
      double turn =  0.8 * (-1.0/80.0) * heading_difference;
      SmartDashboard.putNumber("turn", turn);
      SmartDashboard.putNumber("left drive", left_speed + turn);
      SmartDashboard.putNumber("right drive", right_speed - turn);

      drivetrain.drive(left_speed + turn, right_speed - turn);
    }
  }


  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return finishEarly || isFinished;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {

    m_follower_notifier.stop();
    this.drivetrain.drive(0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {

    m_follower_notifier.stop();    
    this.drivetrain.drive(0, 0);

  }
}
