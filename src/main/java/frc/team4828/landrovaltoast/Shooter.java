package frc.team4828.landrovaltoast;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import jaci.openrio.toast.lib.registry.Registrar;

public class Shooter { //test floobitz comment
    private CANTalon verticalMotor, horizontalMotor;
    private CANTalon masterMotor, slaveMotor;
    private Servo servo1, servo2;

    private double spinUpDelay, maxRotationSpeed, maxFlipSpeed;
    private double servo1Max, servo2Min, servo2Max, servo1Min;

    public Shooter(int vm, int hm, int mm, int sm, int s1, int s2) {
        verticalMotor = Registrar.canTalon(vm);
        horizontalMotor = Registrar.canTalon(hm);
        masterMotor = Registrar.canTalon(mm);
        slaveMotor = Registrar.canTalon(sm);
        servo1 = new Servo(s1);
        servo2 = new Servo(s2);

        spinUpDelay = RobotModule.config.getDouble("constants.shooter.spinUpDelay",0);
        maxRotationSpeed = RobotModule.config.getDouble("constants.shooter.horizontal.speed",0);
        maxFlipSpeed = RobotModule.config.getDouble("constants.shooter.vertical.speed",0);
        servo1Max = RobotModule.config.getDouble("constants.shooter.servo.1.max",0);
        servo1Min = RobotModule.config.getDouble("constants.shooter.servo.1.min",0);
        servo2Max = RobotModule.config.getDouble("constants.shooter.servo.2.max",0);
        servo2Min = RobotModule.config.getDouble("constants.shooter.servo.2.min",0);

        slaveMotor.changeControlMode(CANTalon.TalonControlMode.Follower);
        slaveMotor.set(masterMotor.getDeviceID());
        slaveMotor.reverseOutput(true);
        horizontalMotor.changeControlMode(CANTalon.TalonControlMode.Position);
        horizontalMotor.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder); // TODO: check if we even have an encoder on that motor
        horizontalMotor.enableForwardSoftLimit(true);
        horizontalMotor.enableReverseSoftLimit(true);
        horizontalMotor.setForwardSoftLimit(RobotModule.config.getInt("constants.shooter.horizontal.maxPos",0));
        horizontalMotor.setReverseSoftLimit(RobotModule.config.getInt("constants.shooter.horizontal.minPos",0));
        verticalMotor.changeControlMode(CANTalon.TalonControlMode.Position);
        verticalMotor.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder); // TODO: verify the type of encoder we have
        verticalMotor.enableForwardSoftLimit(true);
        verticalMotor.enableReverseSoftLimit(true);
        verticalMotor.setForwardSoftLimit(RobotModule.config.getInt("constants.shooter.vertical.maxPos",0));
        verticalMotor.setReverseSoftLimit(RobotModule.config.getInt("constants.shooter.vertical.minPos",0));
        refreshEncoder();  //Zeros the encoder position
        // TODO: add command bus option to re-zero so we don't have to power cycle
        // TODO: designate command bus conventions for debugging for all classes
        refreshPID();
    }

    public void refreshPID() {
        verticalMotor.setP(RobotModule.config.getDouble("constants.shooter.vertical.P", 0.0));
        verticalMotor.setI(RobotModule.config.getDouble("constants.shooter.vertical.I", 0.0));
        verticalMotor.setD(RobotModule.config.getDouble("constants.shooter.vertical.D", 0.0));
        horizontalMotor.setP(RobotModule.config.getDouble("constants.shooter.horizontal.P", 0.0));
        horizontalMotor.setI(RobotModule.config.getDouble("constants.shooter.horizontal.I", 0.0));
        horizontalMotor.setD(RobotModule.config.getDouble("constants.shooter.horizontal.D", 0.0));
        //TODO: tune feed-forward in addition to PID values for proper vertical control
        //motor.setF();
    }

    public void refreshEncoder() {
        verticalMotor.setPosition(0);
        horizontalMotor.setPosition(0);
    }

    public void rotate(double speed){
        horizontalMotor.set(horizontalMotor.getPosition()+speed*maxRotationSpeed);
    }

    public void setRotation(int pos){
        horizontalMotor.set(pos);
    }

    public void flip(double speed){
        verticalMotor.set(verticalMotor.getPosition()+speed*maxFlipSpeed);
    }

    public void setPosition(int pos){
        horizontalMotor.set(pos);
    }

    public void setSpeed(double speed){
        masterMotor.set(speed);
    }

    public void shoot(double speed){
        verticalMotor.set(verticalMotor.getPosition()); //Locks the shooter's vertical position
        setSpeed(-speed);
        Timer.delay(.15); //Intake for a short time to settle the ball
        setSpeed(speed);
        Timer.delay(spinUpDelay); //Time it takes for wheels to get to intended speed
        servo1.set(servo1Max);
        servo2.set(servo2Max);
        Timer.delay(.75); //Time it takes for servos to fully extend
        servo1.set(servo1Min);
        servo2.set(servo2Min);
    }

}
