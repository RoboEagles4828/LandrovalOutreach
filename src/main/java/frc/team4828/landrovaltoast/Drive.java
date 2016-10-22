package frc.team4828.landrovaltoast;

import edu.wpi.first.wpilibj.CANTalon;
import jaci.openrio.toast.lib.registry.Registrar;

import java.lang.reflect.Array;
import java.util.ArrayList;

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
    public int[] getPorts() {
        int[] list = new int[4];
        list[0] = frontLeft.getDeviceID();
        list[1] = backLeft.getDeviceID();
        list[2] = frontRight.getDeviceID();
        list[3] = backRight.getDeviceID();

        return list;
    }
}
