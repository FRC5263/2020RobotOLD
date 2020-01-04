/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {	
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
	
	public Joystick driverGamepad = new Joystick(0);
	
	public Joystick operatorGamepad = new Joystick(1);

	public Joystick extraGamepad = new Joystick(2);
	
	Button rightBumper = new JoystickButton(operatorGamepad, 6);
	
	public enum ButtonName{
		A(1), B(2), X(3), Y(4), LB(5), RB(6), BACK(7), START(8), LSTICK(9), RSTICK(10);
		
		public final int value;
		
		private ButtonName(int value) {
			this.value = value;
		}
	}

	  //  * Axis 0 = Left Stick X Axis 1 = Left Stick Y Axis 4 - Right Stick X Axis 5 -
    //  * Right Stick Y
	public enum AxisName{
		LEFTSTICKX(0), LEFTSTICKY(1), LEFTTRIGGER(2), RIGHTTRIGGER(3), RIGHTSTICKX(4), RIGHTSTICKY(5);

		public final int value;
		private AxisName(int value) {
			this.value = value;
		}
	}

	public boolean getExtraButton(ButtonName buttonName) {
		return extraGamepad.getRawButton(buttonName.value);
	}

	public boolean getOperatorButton(ButtonName buttonName) {
		return operatorGamepad.getRawButton(buttonName.value);
	}
	
	public boolean getDriverButton(ButtonName buttonName) {
		return driverGamepad.getRawButton(buttonName.value);
	  }

	public double getDriverAxis(AxisName axisName) {
		return driverGamepad.getRawAxis(axisName.value);
	}

	public double getOperatorAxis(AxisName axisName) {
		return operatorGamepad.getRawAxis(axisName.value);
	}

	public double getExtraAxis(AxisName axisName){
		return extraGamepad.getRawAxis(axisName.value);
	}

	public int getDriverPOV() {
		return driverGamepad.getPOV();
	}

	public int getOperatorPOV() {
		return operatorGamepad.getPOV();
	}
  
	public OI() {
	}
}
