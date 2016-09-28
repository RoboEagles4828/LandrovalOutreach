package frc.team4828.landrovaltoast;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Victor;
import jaci.openrio.toast.lib.registry.Registrar;

public class Climber {
    private CANTalon leftMain, rightMain;
    private Victor leftAlt, rightAlt;

    public Climber(int lm, int rm, int la, int ra){
        leftMain = Registrar.canTalon(lm);
        leftMain = Registrar.canTalon(rm);
        leftMain = Registrar.canTalon(la);
        leftMain = Registrar.canTalon(ra);
    }

}
