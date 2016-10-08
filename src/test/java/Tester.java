import frc.team4828.landrovaltoast.Drive;
import frc.team4828.landrovaltoast.TestClass;
import jaci.openrio.toast.lib.registry.Registrar;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.*;

/**
 * This class is used for testing things on the robot.
 */

@RunWith(JUnit4.class)
public class Tester {
    @Test
    public void sampleTest() {
        Logger.getLogger("InfoLog").log(Level.INFO, "You're currently testing Tester!");
        TestClass test = new TestClass();
        int returned = test.testTester(3);
        Logger.getLogger("InfoLog").log(Level.INFO, "Number: " + returned);
        assertEquals(6, returned);
    }
    @Test
    public void testDrive() {
        int[] expected = {1,2,3,4};
        Drive drive = new Drive(1,2,3,4);
        int[] info = drive.getPorts();
        assertArrayEquals(expected, info);
    }
}
