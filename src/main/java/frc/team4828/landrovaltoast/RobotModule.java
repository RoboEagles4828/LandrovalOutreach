package frc.team4828.landrovaltoast;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import jaci.openrio.toast.core.StateTracker;
import jaci.openrio.toast.core.command.CommandBus;
import jaci.openrio.toast.core.thread.Heartbeat;
import jaci.openrio.toast.core.thread.HeartbeatListener;
import jaci.openrio.toast.lib.log.Logger;
import jaci.openrio.toast.lib.module.IterativeModule;
import jaci.openrio.toast.lib.module.ModuleConfig;
import jaci.openrio.toast.lib.registry.Registrar;
import jaci.openrio.toast.lib.state.RobotState;

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
    }

    public void refreshConstants() {

    }

    @Override
    public void robotInit() {
        logger = new Logger("Landroval", Logger.ATTR_DEFAULT);
        config = new ModuleConfig("RobotConfig.conf");
        autonChoice = Auton.valueOf(config.getString("autonomousDefault","TERRAIN"));

        //TODO: USB Implementation
        //TODO: command bus (the possibilities are endless)
        //TODO: state tracker (working, no use yet)
        //TODO: mess with heartbeats (working, use for prints like camera pos)

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
