/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Generic constructor for all bot setups.
 */
public class Bot {

    public static String DRIVETRAIN = "drivetrain";
    public static String PNEUMATICS = "pneumatics";
    public static String ACTUATOR = "actuator";
    public static String BUCKET = "bucket";
    public static String SUCK = "suck";
    public static String PANEL = "panel";
    public static String ACTUATOR_LIMITER = "limiter";
    public static String POTENTIOMETER = "potentiometer";
    private Map<String, Subsystem> subsystems = new HashMap<>();

    public Bot() {

    }

    public Bot(HashMap<String, Subsystem> subsystems) {
        this.subsystems.putAll(subsystems);
    }

    public void addSubsystem(String key, Subsystem subsystem) {
        this.subsystems.put(key, subsystem);
    }

    public Subsystem getSubsystem(String key) throws Exception {
        Subsystem subsystem = this.subsystems.get(key);
        if(subsystem != null) 
            return subsystem;
        else
            throw new Exception("subsystem does not exist.");
    }

    public boolean hasSubsystem(String key) {
        return this.subsystems.containsKey(key);
    }
}