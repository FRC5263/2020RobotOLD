package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Bot;
import frc.robot.subsystems.DriveTrainSubsystem;

/**
 *
 */
public class DriveTo extends Command {

    private Bot robot;
    private DriveTrainSubsystem drivetrain;
    private boolean finishEarly = false;
    private double encoderTarget;
    private double direction = 0;
    private boolean isFinished = false;
    private double leftCorrection = 0.0;
    private double rightCorrection = 0.0;
    private boolean driveByAngle = false;
    private double initialAngle;
    double driveDistanceFeet;
    double power;
    double seconds;


    /**
     * Creates a new drive function, with a angle to correct to
     * 
     * @param robot                 The robot that will be driven
     * @param driveDistanceFeet     Distance that you want the robot to drive
     * @param power                 The power at which the robot will drive
     * @param angle                 The angle you wish to correct to while driving
     * @param seconds               After x number of seconds this function will timeout          
     */
    public DriveTo(Bot robot, double driveDistanceFeet, double power, double angle, double seconds) {
        this.robot = robot;
        try {
            this.drivetrain = (DriveTrainSubsystem) this.robot.getSubsystem(Bot.DRIVETRAIN);
        } catch (Exception e) {
            this.finishEarly = true;
        }
        this.driveDistanceFeet = driveDistanceFeet;
        this.power = power;
        this.initialAngle = angle;
        this.driveByAngle = true;
        this.seconds = seconds;

        setTimeout(seconds);
    }

    /**
     * Create a new drive to object 
     * 
     * @param robot             The robot that will be driven
     * @param driveDistanceFeet Distance that you want the robot to drive 
     * @param power             The power at which the robot will drive 
     * @param seconds           After x number of seconds this function will timeout
     */
    public DriveTo(Bot robot, double driveDistanceFeet, double power, double seconds) {
        this.robot = robot;
        try {
            this.drivetrain = (DriveTrainSubsystem) this.robot.getSubsystem(Bot.DRIVETRAIN);
        } catch (Exception e) {
            this.finishEarly = true;
        }
        this.driveDistanceFeet = driveDistanceFeet;
        this.power = power;
        this.driveByAngle = false;
        this.seconds = seconds;

        setTimeout(seconds);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        this.drivetrain.resetEncoders();
        isFinished = false;
        if (!driveByAngle) {
            initialAngle = this.drivetrain.getRotation();
        }
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        // Converts Feet to inches values
        encoderTarget = driveDistanceFeet * 12;
        // if target is negative, target is negative

        if (encoderTarget >= 0) {
            direction = 1.0;
        } else {
            direction = -1.0;
        }
        // then direction is negative

        encoderTarget = direction * (driveDistanceFeet * 12);
        // -1 * negative = positive
   
        // DriveTrain.getLeftEncoder()   
        if (this.drivetrain.getLeftEncoderInches() * direction < encoderTarget) {
            this.drivetrain.drive((direction * power) + leftCorrection,
                    (direction * power) + rightCorrection);
        } else {
            this.drivetrain.drive(0.0, 0.0);
            isFinished = true;
        }

        // leftCorrection = -1 * ((DriveTrain.getLeftEncoderInches() -
        // DriveTrain.getRightEncoderInches()) / 10);
        // rightCorrection = 1 * ((DriveTrain.getLeftEncoderInches() -
        // DriveTrain.getRightEncoderInches()) / 10);

        leftCorrection = -1 * ((this.drivetrain.getRotation() - initialAngle) / 80);
        rightCorrection = 1 * ((this.drivetrain.getRotation() - initialAngle) / 80);

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
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
