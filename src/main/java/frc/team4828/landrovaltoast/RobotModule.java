package frc.team4828.landrovaltoast;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import jaci.openrio.toast.core.command.CommandBus;
import jaci.openrio.toast.lib.log.Logger;
import jaci.openrio.toast.lib.module.IterativeModule;
import jaci.openrio.toast.lib.module.ModuleConfig;
import jaci.openrio.toast.lib.registry.Registrar;

import java.util.Arrays;

public class RobotModule extends IterativeModule {

    @Override
    public String getModuleName() {
        return "Landroval";
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


    public void refreshConstants() {

    }

    @Override
    public void robotInit() {
        logger = new Logger("Landroval", Logger.ATTR_DEFAULT);
        config = new ModuleConfig("run/toast/config/RobotConfig.conf");
        autonChoice = Auton.valueOf(config.getString("autonomousDefault", "TERRAIN"));

        //TODO: command bus (the possibilities are endless)
        CommandBus.registerCommand(new Commands.MyCommand());

        blocker = Registrar.victor(config.getInt("ports.blockerMotor", 0));
        blockerHall = Registrar.digitalInput(config.getInt("ports.blockerHall", 0));
        refreshConstants();

        worldChampionDrive = new Drive(1,2,3,4);
    }

    @Override
    public void autonomousPeriodic() {
        switch (autonChoice) {
            case LOWBAR:
                //TODO: add legacy lowbar auton from when we didn't have blocker
                break;
            case TERRAIN:
                //TODO: add autohack
                break;
            case SPYBOT:
                shooter.shoot(config.getDouble("autonShootSpeed", 0));
                break;
            default:
                break;
        }
    }


    @Override
    public void teleopPeriodic() {

    }

}