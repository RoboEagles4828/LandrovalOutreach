package frc.team4828.landrovaltoast;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import jaci.openrio.toast.lib.registry.Registrar;

/**
 * <h1>Shooter</h1>
 * Todo: write a concise description of what the shooter does
 */
public class Shooter {
    private CANTalon verticalMotor, horizontalMotor;
    private CANTalon masterMotor, slaveMotor;
    private Servo servo1, servo2;

    private double spinUpDelay, maxRotationSpeed, maxFlipSpeed;
    private double servo1Max, servo2Min, servo2Max, servo1Min;


    /**
     * @param vm Vertical motor CAN address
     * @param hm Horizontal motor CAN address
     * @param mm Main shooting motor CAN address
     * @param sm Slave shooting motor CAN address
     * @param s1 Pusher servo one PWM port
     * @param s2 Pusher servo two PWM port
     */
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
        refreshEncoder();
        // TODO: add command bus option to re-zero so we don't have to power cycle
        // TODO: designate command bus conventions for debugging for all classes
        refreshPID();
    }

    /**
     * Pulls PID values from RobotConfig.conf
     * (applies to both horizontal and vertical shooter motors)
     */
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

    /**
     * Zeroes the encoders on the horizontal and vertical shooter motors
     */
    public void refreshEncoder() {
        verticalMotor.setPosition(0);
        horizontalMotor.setPosition(0);
    }

    /**
     * Turns the shooter left or right
     * @param speed Value between -1 (max speed left) and 1 (max speed right)
     */
    public void rotate(double speed){
        horizontalMotor.set(horizontalMotor.getPosition()+speed*maxRotationSpeed);
    }

    /**
     * Gives the horizontal motor a target position to move to
     * @param pos Encoder position
     */
    public void setRotation(int pos){
        horizontalMotor.set(pos);
    }

    /**
     * Flips the shooter up or down
     * @param speed Value between -1 (max speed up) and 1 (max speed down)
     */
    public void flip(double speed){
        verticalMotor.set(verticalMotor.getPosition()+speed*maxFlipSpeed);
    }

    /**
     * Gives the vertical motor a target position to move to
     * @param pos Encoder position
     */
    public void setPosition(int pos){
        horizontalMotor.set(pos);
    }

    /**
     * Spins the flywheels
     * @param speed Value between -1 (full power intake) and 1 (full power shot)
     */
    public void setSpeed(double speed){
        masterMotor.set(speed);
    }

    /**
     * <b>Shooting routine</b>
     * <ol>
     *     <li>Lock shooter's vertical position</li>
     *     <li>Intake briefly to settle the ball</li>
     *     <li>Spin up flywheels and wait for them to reach max speed</li>
     *     <li>Extend tandem pusher servos and wait for them to push the ball into flywheels</li>
     *     <li>Stop flywheels and retract pusher servos</li>
     * </ol>
     * @param speed Power of the shot in range [0.5,1]
     */
    public void shoot(double speed){
        verticalMotor.set(verticalMotor.getPosition());
        setSpeed(-speed);
        Timer.delay(.15);
        setSpeed(speed);
        Timer.delay(spinUpDelay);
        servo1.set(servo1Max);
        servo2.set(servo2Max);
        Timer.delay(.75);
        setSpeed(0);
        servo1.set(servo1Min);
        servo2.set(servo2Min);
    }

}
