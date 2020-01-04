/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Bot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.DigitalSignalSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.MotorSubsystem;
import frc.robot.subsystems.PneumaticsSubsystem;
import frc.robot.subsystems.PotentiometerSubsystem;

import java.util.HashMap;
import java.util.Map;

//import hardware
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.VictorSP;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Storage of Different Bot configurations.
 */
public class Bots {

    // private static DigitalInput jumper;

    /**
     * Configures robot hardware based on DIO Jumper (9) No Jumper Connection =
     * Competition Robot Jumper Connected = Test Bot One
     * 
     * @return corresponding Bot of Rio Jumper
     */
    public static Bot getBotByHardware() {

        // true = OPEN, false = CLOSED (connected)
        DigitalInput dio9 = new DigitalInput(9);
        Boolean dio9Connected = !dio9.get();
        dio9.close();
        DigitalInput dio8 = new DigitalInput(8);
        Boolean dio8Connected = !dio8.get();
        dio8.close();

        if (dio9Connected) {
            System.out.println("running on Test Bot One Hardware");
            SmartDashboard.putString("Hardware Configuration", "Test Bot One");
            return createTestBotOne();
        } else if (dio8Connected) {
            System.out.println("running on Test Bench Hardware");
            SmartDashboard.putString("Hardware Configuration", "Test Bench Robot");
            return createTestBench();
        } else {
            System.out.println("running on Competiton Robot Hardware");
            SmartDashboard.putString("Hardware Configuration", "Competition Robot");
            return createCompetitionBot();
        }

    }

    public static Bot createCompetitionBot() {
        return new Bot(new HashMap<String, Subsystem>() {
            {
                put(Bot.DRIVETRAIN,
                        new DriveTrainSubsystem(new SpeedControllerGroup(new WPI_TalonSRX(2), new WPI_TalonSRX(3)),
                                new SpeedControllerGroup(new WPI_TalonSRX(4), new WPI_TalonSRX(5)), new Encoder(2, 3),
                                new Encoder(0, 1),
                                new Ultrasonic(4, 5),
                                new AHRS(SPI.Port.kMXP),
                                null));
                put(Bot.ACTUATOR, new MotorSubsystem(new WPI_TalonSRX(6)));
                put(Bot.BUCKET, new MotorSubsystem(new WPI_TalonSRX(7)));
                put(Bot.SUCK, new MotorSubsystem(new WPI_VictorSPX(8)));
                put(Bot.PANEL, new MotorSubsystem(new VictorSP(9)));
                put(Bot.PNEUMATICS, new PneumaticsSubsystem(new DoubleSolenoid(1, 0, 1)));
                put(Bot.ACTUATOR_LIMITER, new DigitalSignalSubsystem(new DigitalInput(6)));
                put(Bot.POTENTIOMETER, new PotentiometerSubsystem(new AnalogPotentiometer(0, )));
            }
        });
    }

    public static Bot createTestBotOne() {
        return new Bot(new HashMap<String, Subsystem>() {
            {
                put(Bot.DRIVETRAIN,
                        new DriveTrainSubsystem(new Spark(0), new Spark(1), new Encoder(0, 1), new Encoder(2, 3), new Ultrasonic(4, 5),// new
                                                                                                                        // Ultrasonic(1,
                                                                                                                        // 0),
                                new AHRS(SPI.Port.kMXP), new Servo(2)));
                put(Bot.ACTUATOR, new MotorSubsystem(new WPI_VictorSPX(8)));
            }
        });
    }

    public static Bot createTestBench() {
        return new Bot(new HashMap<String, Subsystem>() {
            {
                put(Bot.DRIVETRAIN, new DriveTrainSubsystem(null, null, null, null, null, null, null));
            }
        });
    }

}
