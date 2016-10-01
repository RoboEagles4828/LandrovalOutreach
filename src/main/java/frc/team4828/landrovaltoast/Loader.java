package frc.team4828.landrovaltoast;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Victor;
import jaci.openrio.toast.lib.registry.Registrar;

public class Loader {
    private CANTalon verticalMotor;
    private Victor intakeMotor;

    private double maxFlipSpeed;

    public Loader(int im, int mm) {
        intakeMotor = Registrar.victor(im);
        verticalMotor = Registrar.canTalon(mm);

        maxFlipSpeed = RobotModule.config.getInt("constants.loader.speed", 0);

        verticalMotor.changeControlMode(CANTalon.TalonControlMode.Position);
        verticalMotor.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder); // TODO: check the encoder on that motor
        verticalMotor.enableForwardSoftLimit(true);
        verticalMotor.enableReverseSoftLimit(true);
        verticalMotor.setForwardSoftLimit(RobotModule.config.getInt("constants.loader.maxPos", 0));
        verticalMotor.setReverseSoftLimit(RobotModule.config.getInt("constants.loader.minPos", 0));
        refreshPID(); //TODO: tune with feed-forward
    }

    public void refreshPID() {
        verticalMotor.setP(RobotModule.config.getDouble("constants.loader.P", 0.0));
        verticalMotor.setI(RobotModule.config.getDouble("constants.loader.I", 0.0));
        verticalMotor.setD(RobotModule.config.getDouble("constants.loader.D", 0.0));
    }

    public void flip(double speed) {
        verticalMotor.set(verticalMotor.getPosition() + speed * maxFlipSpeed);
    }

    public void setPosition(int pos) {
        verticalMotor.set(pos);
    }

    public void setSpeed(double speed) {
        intakeMotor.set(speed);
    }
}
