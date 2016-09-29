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

        //not sure if we need motor.enableControl();
        slaveMotor.changeControlMode(CANTalon.TalonControlMode.Follower);
        slaveMotor.set(masterMotor.getDeviceID());
        slaveMotor.reverseOutput(true);
        verticalMotor.changeControlMode(CANTalon.TalonControlMode.Position);
        verticalMotor.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder); // TODO: verify the type of encoder we have
        refreshEncoder();  //Zeros the encoder position
        // TODO: add command bus option to re-zero so we dont have to power cycle
        refreshPID();
        /*
        * soft limits can be easily set with:
        * enableForwardSoftLimit(true), enableReverseSoftLimit(true)
        * setForwardSoftLimit(int pos), setReverseSoftLimit(int pos)
        * */
    }

    public void refreshPID() {
        verticalMotor.setP(RobotModule.config.getDouble("constants.shooter.P", 0.0));
        verticalMotor.setI(RobotModule.config.getDouble("constants.shooter.I", 0.0));
        verticalMotor.setD(RobotModule.config.getDouble("constants.shooter.D", 0.0));
    }

    public void refreshEncoder(){
        verticalMotor.setPosition(0);
    }

}
