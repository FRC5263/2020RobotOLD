/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//import hardware
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Ultrasonic;
import com.kauailabs.navx.frc.AHRS;

//import libraries
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Servo;

/**
 * Subsystem for controlling drive wheels and drive-related sensors.
 */
public class DriveTrainSubsystem extends Subsystem {

    private DifferentialDrive drivetrain;
    private SpeedController leftMotors;
    private SpeedController rightMotors;
    private AHRS navx;
    private Ultrasonic sonic;
    private Encoder leftEncoder;
    private Encoder rightEncoder;
    private Servo servo;

    // constants
    private final static double wheelDiameterInches = 8.0;
    private final static double encoderClicksPerRevolution = 360;
    // inches the ultrasonic is mounted from the front of the robot
    private final static double ultrasonicOffset = 13;

    public DriveTrainSubsystem(SpeedController leftDrive, SpeedController rightDrive, Encoder leftEncoder,
            Encoder rightEncoder, Ultrasonic ultrasonic, AHRS navx, Servo servo) {
        this.servo = servo;
        this.leftMotors = leftDrive;
        this.rightMotors = rightDrive;
        this.leftEncoder = leftEncoder;
        this.rightEncoder = rightEncoder;
        this.sonic = ultrasonic;
        this.navx = navx;
        this.servo = servo;
        System.out.println("Initalized DriveTrainSubsystem");
         if (this.leftMotors != null && this.rightMotors != null)
           this.drivetrain = new DifferentialDrive(this.leftMotors, this.rightMotors);
        else
            this.drivetrain = null;
    }

    @Override
    public void initDefaultCommand() {
    }

    public Double getServo(){
        if(servo != null){
        return servo.getAngle();
        }
        return -1.0;
    }

    public void setRotation(int angle){
        System.out.println("SHOULD BE WORKING??? DriveTrain");
        servo.setAngle(angle);
    }

    public double getRotation() {
        if (navx != null)
            return navx.getAngle();
        else
            return -1;
    }

    public int getLeftEncoder() {
        if (leftEncoder != null)
            return leftEncoder.get();
        else
            return -1;
    }

    public int getRightEncoder() {
        if (rightEncoder != null)
            return rightEncoder.get();
        else
            return -1;
    }

	/*
	 *                  1 revolution    pi * wheel diameter inches
	 * encoder clicks * ------------ * ---------------------------- = inches traveled
	 *                    # clicks             1 revolution
	 */
    public double getLeftEncoderInches() {
        return getLeftEncoder() * (1 / encoderClicksPerRevolution) * (Math.PI * wheelDiameterInches);
    }

    public double getRightEncoderInches() {
        return getRightEncoder() * (1 / encoderClicksPerRevolution) * (Math.PI * wheelDiameterInches);
    }

    public void resetEncoders() {
        if (leftEncoder != null)
            leftEncoder.reset();
        if (rightEncoder != null)
            rightEncoder.reset();
    }

    public void drive(double leftPower, double rightPower) {
        if (this.drivetrain != null)
            drivetrain.tankDrive(curveInput(leftPower), curveInput(rightPower));
    }

    public void arcadeDrive(double speed, double rotation) {
        if (this.drivetrain != null)
            drivetrain.arcadeDrive(curveInput(speed), curveInput(rotation));
    }

    private double curveInput(double input) {
        double adjustedInput = Math.pow(Math.abs(input), (1.0 / 3.0));
        if (input < 0) {
            adjustedInput *= -1;
        }
        return adjustedInput;
    }

    public void resetCompass() {
        if (navx != null)
            navx.reset();
    }

    public double getSonicDistance() {
        if (sonic != null)
            return sonic.getRangeInches() - ultrasonicOffset;
        else
            return -1;
    }

    public double returnUltrasonicVal (){  
        if(sonic != null)
            return sonic.getRangeInches();
        else 
            return -1;
    }

    public void sonicSetAutomatic(){
        if(sonic != null)
            sonic.setAutomaticMode(true); 
    }
    
    public void putServoOnDash(){
        SmartDashboard.putNumber("Servo", getServo());
    }

    public void putEncodersOnDash() {
        if (this.leftEncoder != null)
            SmartDashboard.putNumber("Left Encoder Value               ", getLeftEncoder());
        else
            SmartDashboard.putString("Left Encoder", "disconnected");
        if (this.rightEncoder != null)
            SmartDashboard.putNumber("Right Encoder Value              ", getRightEncoder());
        else
            SmartDashboard.putString("Left Encoder", "disconnected");
    }

    public void putCompassOnDash() {
        if(navx != null)
            SmartDashboard.putNumber("Gyroscopic angle in degrees      ", getRotation());
        else
            SmartDashboard.putString("Compass", "disconnected");
    }

    public void putUltrasonicOnDash() {
        if(sonic != null)
            SmartDashboard.putNumber("Ultrasonic: ", getSonicDistance());
        else 
            SmartDashboard.putString("Ultrasonic:", "disconnected");
    }

}
