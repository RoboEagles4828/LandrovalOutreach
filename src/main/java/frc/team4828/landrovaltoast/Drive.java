package frc.team4828.landrovaltoast;

import edu.wpi.first.wpilibj.CANTalon;
import jaci.openrio.toast.lib.registry.Registrar;

public class Drive {
    public CANTalon frontLeft;
    public CANTalon backLeft;
    public CANTalon frontRight;
    public CANTalon backRight;

    public Drive(int fl, int bl, int fr, int br) {
        frontLeft = Registrar.canTalon(fl);
        backLeft = Registrar.canTalon(bl);
        frontRight = Registrar.canTalon(fr);
        backRight = Registrar.canTalon(br);
    }

}
