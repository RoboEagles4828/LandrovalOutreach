package frc.team4828.landrovaltoast;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Victor;
import jaci.openrio.toast.lib.registry.Registrar;

/**
 * Created by josep on 9/27/2016.
 * Project: LandrovalToast
 */
public class Climber {
    public CANTalon rightClimb;
    public Victor leftStable, rightStable, leftClimb;

    // What the hell are these for???
    private static final double climberUpSpeed = 1;
    private static final double climberDownSpeed = -1;

    public Climber() {
        leftStable = Registrar.victor(1); // Insert Port
        rightStable = Registrar.victor(2); // Insert Port
        rightClimb = Registrar.canTalon(3); // Insert Port
        leftClimb = Registrar.victor(4); // Insert Port
    }
    public void setup() {
        // TODO Auto-generated method stub

    }

    public void leftClimberUp() {
        leftClimb.set(-climberUpSpeed);
    }

    public void leftClimberDown() {
        leftClimb.set(-climberDownSpeed);

    }

    public void leftClimberStop() {
        leftClimb.set(0);
    }

    public void rightClimberUp() {
        rightClimb.set(climberUpSpeed);
    }

    public void rightClimberDown() {
        rightClimb.set(climberDownSpeed);
    }

    public void rightClimberStop() {
        rightClimb.set(0);
    }
    public void rightStableUp() {
        rightStable.set(climberUpSpeed);
    }

    public void rightStableDown() {
        rightStable.set(climberDownSpeed);
    }

    public void rightStableStop() {
        rightStable.set(0);
    }
    public void leftStableUp() {
        leftStable.set(climberUpSpeed);

    }

    public void leftStableDown() {
        leftStable.set(climberDownSpeed);

    }

    public void leftStableStop() {
        leftStable.set(0);
    }

}
