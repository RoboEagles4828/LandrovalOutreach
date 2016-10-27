package frc.team4828.landrovaltoast;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.GenericHID;
import jaci.openrio.toast.lib.module.ModuleConfig;
import jaci.openrio.toast.lib.module.ToastModule;
import jaci.openrio.toast.lib.registry.Registrar;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Drive {
    public CANTalon frontLeft;
    public CANTalon backLeft;
    public CANTalon frontRight;
    public CANTalon backRight;
//    private static ModuleConfig config = new ModuleConfig("run/toast/config/RobotConfig.conf");
//    protected static final int KMAXNUMBEROFMOTORS = config.getInt("worldChampionDrive.kMaxNumberOfMotors", 0);
    public boolean inverseControls = false;


    public Drive(int fl, int bl, int fr, int br) {
        frontLeft = Registrar.canTalon(fl);
        backLeft = Registrar.canTalon(bl);
        frontRight = Registrar.canTalon(fr);
        backRight = Registrar.canTalon(br);
    }
    public int[] getPorts() {
        int[] list = new int[4];
        list[0] = frontLeft.getDeviceID();
        list[1] = backLeft.getDeviceID();
        list[2] = backRight.getDeviceID();
        list[3] = frontRight.getDeviceID();

    public enum Direction {
        FORWARD, BACKWARD, LEFT, RIGHT, SPINLEFT, SPINRIGHT;
    }

    public enum Rotation {
        LEFT90, RIGHT90;
    }

    protected static double limit(double num) {
        if (num > 1.0) {
            return 1.0;
        }
        if (num < -1.0) {
            return -1.0;
        }
        return num;
    }

    public void stop() {
        frontLeft.set(0);
        backLeft.set(0);
        frontRight.set(0);
        backRight.set(0);
    }

    public static class MotorType {
        public final int value;
        static final int kFrontLeft_val = 0;
        static final int kFrontRight_val = 1;
        static final int kbackLeft_val = 2;
        static final int kbackRight_val = 3;

        public static final MotorType kFrontLeft = new MotorType(kFrontLeft_val);
        public static final MotorType kFrontRight = new MotorType(kFrontRight_val);
        public static final MotorType kbackLeft = new MotorType(kbackLeft_val);
        public static final MotorType kbackRight = new MotorType(kbackRight_val);

        private MotorType(int value) {
            this.value = value;
        }
    }

//    protected static void normalize(double wheelSpeeds[]) {
//        double maxMagnitude = Math.abs(wheelSpeeds[0]);
//        int i;
//        for (i = 1; i < KMAXNUMBEROFMOTORS; i++) {
//            double temp = Math.abs(wheelSpeeds[i]);
//            if (maxMagnitude < temp)
//                maxMagnitude = temp;
//        }
//        if (maxMagnitude > 1.0) {
//            for (i = 0; i < KMAXNUMBEROFMOTORS; i++) {
//                wheelSpeeds[i] = wheelSpeeds[i] / maxMagnitude;
//            }
//        }
//    }

    public void tankDrive(GenericHID leftStick, GenericHID rightStick) {
        if (leftStick == null || rightStick == null) {
            throw new NullPointerException("Null HID provided");
        }
        double mult = (((-leftStick.getThrottle()+1)/2)*.99);
        //double mult = 1;
        tankDrive(-leftStick.getY() * mult, -rightStick.getY() * mult, true);
    }

    public void tankDrive(double leftValue, double rightValue, boolean squaredInputs) {
        leftValue = limit(leftValue);
        rightValue = limit(rightValue);
        if (squaredInputs) {
            if (leftValue >= 0.0) {
                leftValue = (leftValue * leftValue);
            } else {
                leftValue = -(leftValue * leftValue);
            }
            if (rightValue >= 0.0) {
                rightValue = (rightValue * rightValue);
            } else {
                rightValue = -(rightValue * rightValue);
            }
        }
        setLeftRightMotorOutputs(leftValue, rightValue);
    }

    public void setLeftRightMotorOutputs(double leftOutput, double rightOutput) {
        if (backLeft == null || backRight == null) {
            throw new NullPointerException("Null motor provided");
        }
        if (frontLeft != null) {
            frontLeft.set(limit(leftOutput));
        }
        backLeft.set(limit(leftOutput));
        if (frontRight != null) {
            frontRight.set(-limit(rightOutput));
        }
        backRight.set(-limit(rightOutput));
    }

    public void arcadeDrive(GenericHID stick) {
        this.arcadeDrive(stick, true);
    }

    private static final double RAMP_RATE = 0.05;
    private static final double RANGE = .70;
    private double ramp(double stickVal, double current){
        if(stickVal > RANGE){
            if(current < RANGE)
                current = RANGE;
            else{
                current += RAMP_RATE;
                if(current > stickVal)
                    current = stickVal;
            }
        } else if (stickVal < -RANGE){
            if(current > -RANGE)
                current = -RANGE;
            else{
                current -= RAMP_RATE;
                if(current < stickVal)
                    current = stickVal;
            }
        } else
            current = stickVal;
        return current;
    }

    private double ramp(double stickVal, double current, double rampRate, double range){
        if(stickVal > range){
            if(current < range)
                current = range;
            else{
                current += rampRate;
                if(current > stickVal)
                    current = stickVal;
            }
        } else if (stickVal < -range){
            if(current > -range)
                current = -range;
            else{
                current -= rampRate;
                if(current < stickVal)
                    current = stickVal;
            }
        } else
            current = stickVal;
        return current;
    }

    private double currentY = 0;
    private double currentX = 0;
    public void arcadeDriveRamp(GenericHID stick, boolean squaredInputs) {
        //currentY = ramp(stick.getY(), currentY);
        currentY = ramp(stick.getY(), currentY);
        currentX = ramp(stick.getX(), currentX);
        arcadeDrive(currentY, currentX, squaredInputs);
    }

    public void arcadeDriveRamp(GenericHID stick){
        arcadeDriveRamp(stick, true);
    }

    public void arcadeDrive(GenericHID stick, boolean squaredInputs) {
        arcadeDrive(stick.getY(), stick.getX(), squaredInputs);
    }

    public void arcadeDrive(double moveValue, double rotateValue, boolean squaredInputs) {
        double leftMotorSpeed;
        double rightMotorSpeed;
        moveValue = limit(moveValue);
        rotateValue = limit(rotateValue);

        if (inverseControls) {
            moveValue = -moveValue;
        }

        if (squaredInputs) {
            if (moveValue >= 0.0) {
                moveValue = (moveValue * moveValue);
            } else {
                moveValue = -(moveValue * moveValue);
            }
            if (rotateValue >= 0.0) {
                rotateValue = (rotateValue * rotateValue);
            } else {
                rotateValue = -(rotateValue * rotateValue);
            }
        }
        if (moveValue > 0.0) {
            if (rotateValue > 0.0) {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = Math.max(moveValue, rotateValue);
            } else {
                leftMotorSpeed = Math.max(moveValue, -rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            }
        } else {
            if (rotateValue > 0.0) {
                leftMotorSpeed = -Math.max(-moveValue, rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            } else {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
            }
        }
        setLeftRightMotorOutputs(leftMotorSpeed, rightMotorSpeed);
    }

//    public void mecanumDrive_Polar(double magnitude, double direction, double rotation) {
//        magnitude = limit(magnitude) * Math.sqrt(2.0);
//        double dirInRad = (direction + 45.0) * Math.PI / 180.0;
//        double cosD = Math.cos(dirInRad);
//        double sinD = Math.sin(dirInRad);
//        double wheelSpeeds[] = new double[KMAXNUMBEROFMOTORS];
//        wheelSpeeds[MotorType.kFrontLeft_val] = (sinD * magnitude + rotation);
//        wheelSpeeds[MotorType.kFrontRight_val] = (cosD * magnitude - rotation);
//        wheelSpeeds[MotorType.kbackLeft_val] = (cosD * magnitude + rotation);
//        wheelSpeeds[MotorType.kbackRight_val] = (sinD * magnitude - rotation);
//        normalize(wheelSpeeds);
//        frontLeft.set(wheelSpeeds[MotorType.kFrontLeft_val]);
//        frontRight.set(wheelSpeeds[MotorType.kFrontRight_val]);
//        backLeft.set(wheelSpeeds[MotorType.kbackLeft_val]);
//        backRight.set(wheelSpeeds[MotorType.kbackRight_val]);
//    }

//    public void rotateToAngle(double angle, AnalogGyro gyro, Robot r) {
//        if (gyro.getAngle() % 360 > angle) {
//            while (gyro.getAngle() % 360 > angle && r.isAutonomous){
//                move(Direction.SPINLEFT, 0.5);
//                //System.out.println("Angle: " + angle + "   Gyro: " + gyro.getAngle());
//            }
//            while (gyro.getAngle() % 360 < angle && r.isAutonomous()){
//                move(Direction.SPINRIGHT, 0.5);
//                //System.out.println("Angle: " + angle + "   Gyro: " + gyro.getAngle());
//            }
//        } else if (gyro.getAngle() % 360 < angle) {
//            while (gyro.getAngle() % 360 < angle && r.isAutonomous()){
//                move(Direction.SPINRIGHT, 0.5);
//                //System.out.println("Angle: " + angle + "   Gyro: " + gyro.getAngle());
//            }
//            while (gyro.getAngle() % 360 > angle && r.isAutonomous()){
//                move(Direction.SPINLEFT, 0.5);
//                //System.out.println("Angle: " + angle + "   Gyro: " + gyro.getAngle());
//            }
//        }
//        stop();
//    }

    public void rotateToAngle(double angle, AnalogGyro gyro) {
        if (gyro.getAngle() % 360 > angle) {
            while (gyro.getAngle() % 360 > angle){
                move(Direction.SPINRIGHT, 0.3);
                //System.out.println("Angle: " + angle + "   Gyro: " + gyro.getAngle());
            }
            while (gyro.getAngle() % 360 < angle){
                move(Direction.SPINLEFT, 0.15);
                //System.out.println("Angle: " + angle + "   Gyro: " + gyro.getAngle());
            }
        } else if (gyro.getAngle() % 360 < angle) {
            while (gyro.getAngle() % 360 < angle){
                move(Direction.SPINLEFT, 0.3);
                //System.out.println("Angle: " + angle + "   Gyro: " + gyro.getAngle());
            }
            while (gyro.getAngle() % 360 > angle){
                move(Direction.SPINRIGHT, 0.15);
                //System.out.println("Angle: " + angle + "   Gyro: " + gyro.getAngle());
            }
        }
        stop();
    }

    // public void driveRotations(int rotations, double speed) {
    // int encStart = backLeft.getEncPosition();
    // double encIncNeeded = rotations * 1440;
    // while (backLeft.getEncPosition() < encStart + encIncNeeded
    // && backLeft.getEncPosition() > encStart - encIncNeeded) {
    // move(Direction.FORWARD, speed);
    // System.out.println(backLeft.getEncPosition());
    // }
    // stop();
    // }
    //
    // // encoders = 1440 per rotation
    // private final static double PULSE_PER_90 = 1.0D;
    //
    // public void rotate(Rotation rotation, double speed) {
    // int encStart = backLeft.getEncPosition();
    // double encIncNeeded = PULSE_PER_90;
    // if (rotation == Rotation.LEFT90) {
    // while (frontRight.getEncPosition() < encStart + encIncNeeded)
    // move(Direction.SPINLEFT, speed);
    // stop();
    // } else if (rotation == Rotation.RIGHT90) {
    // while (backLeft.getEncPosition() < encStart + encIncNeeded)
    // move(Direction.SPINRIGHT, speed);
    // stop();
    // }
    // }
    //
    private final static double PULSE_PER_INCH = 43D;

//    public void move(Direction direction, double speed, double inches, Robot r) {
//        int encStart = backLeft.getEncPosition();
//        if (inches <= 0)
//            System.out.println("Distance requested < 0 inches; put in a valid parameter");
//        else {
//            double encIncNeeded = PULSE_PER_INCH * inches;
//            while (backLeft.getEncPosition() < encStart + encIncNeeded
//                    && backLeft.getEncPosition() > encStart - encIncNeeded && r.isAutonomous()){
//                //System.out.println("rl: " + backLeft.getEncPosition());
//                move(direction, speed);
//            }
//            stop();
//        }
//    }

    public void autoHack() {
        frontLeft.set(0.7);
        frontRight.set(-0.7);
        backLeft.set(0.7);
        backRight.set(-0.7);
    }

    public void move(Direction direction, double speed) {
        if (direction == Direction.FORWARD) {
            frontLeft.set(-speed);
            frontRight.set(speed);
            backLeft.set(-speed);
            backRight.set(speed);
        } else if (direction == Direction.BACKWARD) {
            frontLeft.set(speed);
            frontRight.set(-speed);
            backLeft.set(speed);
            backRight.set(-speed);
        } else if (direction == Direction.LEFT) {
            frontLeft.set(speed);
            frontRight.set(speed);
            backLeft.set(-speed);
            backRight.set(-speed);
        } else if (direction == Direction.RIGHT) {
            frontLeft.set(-speed);
            frontRight.set(-speed);
            backLeft.set(speed);
            backRight.set(speed);
        } else if (direction == Direction.SPINRIGHT) {
            frontLeft.set(-speed);
            frontRight.set(-speed);
            backLeft.set(-speed);
            backRight.set(-speed);
        } else if (direction == Direction.SPINLEFT) {
            frontLeft.set(speed);
            frontRight.set(speed);
            backLeft.set(speed);
            backRight.set(speed);
        }
    }
    
    public int[] getPorts() {
        int[] list = new int[4];
        list[0] = frontLeft.getDeviceID();
        list[1] = backLeft.getDeviceID();
        list[2] = frontRight.getDeviceID();
        list[3] = backRight.getDeviceID();
        return list;
    }
}
