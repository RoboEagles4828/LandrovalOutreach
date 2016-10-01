package frc.team4828.landrovaltoast;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import jaci.openrio.toast.core.StateTracker;
import jaci.openrio.toast.core.command.CommandBus;
import jaci.openrio.toast.core.io.Storage;
import jaci.openrio.toast.core.thread.Heartbeat;
import jaci.openrio.toast.core.thread.HeartbeatListener;
import jaci.openrio.toast.lib.log.Logger;
import jaci.openrio.toast.lib.module.IterativeModule;
import jaci.openrio.toast.lib.module.ModuleConfig;
import jaci.openrio.toast.lib.registry.Registrar;
import jaci.openrio.toast.lib.state.RobotState;

import java.io.File;

public class RobotModule extends IterativeModule {

    @Override
    public String getModuleName() {
        return "LandrovalToast";
    }

    @Override
    public String getModuleVersion() {
        return "0.1.0";
    }

    public static Logger logger;
    public static ModuleConfig config;
    private Joystick primaryJoy, secondaryJoy;
    private Shooter shooter;
    private Loader loader;
    private Climber climber;
    private Drive worldChampionDrive;
    private Victor blocker;
    private DigitalInput blockerHall;

    private enum Auton {SPYBOT, TERRAIN, LOWBAR;}
    private Auton autonChoice;

    public void refreshPorts() {

        //TODO: find a way to reset registrar so we can rebind ports through code/config file/command bus
        /*
        Jackie: this could be one way to do it, however tedious:
        First, make motors (Talon myTalon;)
        Then:
        talon = Registrar.talon(myConfigFile.getString("port/talon1","9000"));
        Repeat for each or use a for loop on an array (Can you do that in java? You can in Python)
        */
    }

    public void refreshConstants() {

    }

    @Override
    public void robotInit() {
        logger = new Logger("Landroval", Logger.ATTR_DEFAULT);
        config = new ModuleConfig("RobotConfig.conf");
        autonChoice = Auton.valueOf(config.getString("autonomousDefault","TERRAIN"));

        //TODO: USB Implementation
        // Jackie: These are my tests with USB, doesn't work yet. Comment out if spits out errors.
        Storage.highestPriority("TestConf.conf");

//        File usbFile = Storage.("TestConf.conf");
//        ModuleConfig usbConfig = new ModuleConfig(usbFile);
//        logger.info("Hi! My name is " + usbConfig.getObject("filename"));

        //TODO: command bus (the possibilities are endless)
        // Commands
        CommandBus.registerCommand(new TestCommand.MyCommand());

        //TODO: state tracker (working, no use yet)

        // Heartbeat
        // Heartbeat.add(skipped -> RobotModule.logger.info("Beep"));

        refreshConstants();
        refreshPorts();
    }

    @Override
    public void autonomousPeriodic() {
        switch(autonChoice) {
            case LOWBAR:
                //TODO: add legacy lowbar auton from when we didn't have blocker
                break;
            case TERRAIN:
                //TODO: add autohack
                break;
            case SPYBOT:
                //shooter.shoot(getautonshotpowerfromconfig);
                break;
            default:
                break;
        }
    }


    @Override
    public void teleopPeriodic() {

    }

}
