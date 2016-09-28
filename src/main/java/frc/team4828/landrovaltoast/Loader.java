package frc.team4828.landrovaltoast;

import edu.wpi.first.wpilibj.CANTalon;
import jaci.openrio.toast.lib.registry.Registrar;

public class Loader {
    private CANTalon intakeMotor, mainMotor;

    public Loader(int im, int mm){
        intakeMotor = Registrar.canTalon(im);
        mainMotor = Registrar.canTalon(mm);
    }
}
