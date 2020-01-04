package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Bot;
import frc.robot.subsystems.DriveTrainSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Rotate extends Command {

	double current;
	double difference;
	double angle;
	double power;
    boolean isFinished = false;
    private Bot robot;
    private DriveTrainSubsystem drivetrain;
    private boolean finishEarly = false;
    private double encoderTarget;

	public Rotate(Bot robot, double angle, double power, double seconds) {
		this.robot = robot;
        try {
            this.drivetrain = (DriveTrainSubsystem) this.robot.getSubsystem(Bot.DRIVETRAIN);
        } catch (Exception e) {
            this.finishEarly = true;
        }
        setTimeout(seconds);
		this.angle = angle;
		this.power = power;
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);

	}



	// Called just before this Command runs the first time
	protected void initialize() {
	// DriveTrain.sharedInstance().reset();
	isFinished = false;
	}

	// Positive is to the right and negative is to the left
	

	// Called repeatedly when this Command is scheduled to run
	protected void execute() { 
		current = drivetrain.getRotation();
		difference = angle - current;
		if(difference > 0){
			// Method below means turn right
			drivetrain.drive(power,-power);
		}
		if(difference < 0){
			// Method below means turn left
			drivetrain.drive(-power, power);
			
		}
		if(Math.abs(difference) < 5){ 
			drivetrain.drive(0, 0);
			isFinished = true;
		}
		SmartDashboard.putNumber("gyro: ", drivetrain.getRotation());

	}




	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		System.out.println("Rotation ran");
		return isFinished || isTimedOut() || finishEarly;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}