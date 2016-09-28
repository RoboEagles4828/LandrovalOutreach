package frc.team4828.landrovaltoast;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import jaci.openrio.toast.lib.registry.Registrar;

public class Shooter {
    private CANTalon verticalMotor, horizontalMotor;
    private CANTalon masterMotor, slaveMotor;
    private DigitalInput centerHall;
    private Servo servo1, servo2;

    public Shooter(int vm, int hm, int mm, int sm, int ch, int s1, int s2) {
        verticalMotor = Registrar.canTalon(vm);
        horizontalMotor = Registrar.canTalon(hm);
        masterMotor = Registrar.canTalon(mm);
        slaveMotor = Registrar.canTalon(sm);
        centerHall = Registrar.digitalInput(ch);
        servo1 = new Servo(s1);
        servo2 = new Servo(s2);
        refreshPID();
    }

    public void refreshPID() {
        verticalMotor.setP(RobotModule.config.getDouble("constants.shooter.P", 0.0));
        verticalMotor.setI(RobotModule.config.getDouble("constants.shooter.I", 0.0));
        verticalMotor.setD(RobotModule.config.getDouble("constants.shooter.D", 0.0));
    }

}
