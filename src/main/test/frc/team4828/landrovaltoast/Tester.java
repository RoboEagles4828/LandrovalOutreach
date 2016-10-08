package frc.team4828.landrovaltoast;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
@RunWith(JUnit4.class)
public class Tester {
    @Test
    public void test() {
        System.out.println("You're currently testing Tester!");
        TestClass test = new TestClass();
        int returned = test.testTester(3);
        System.out.println("Number: " + returned);
        assertEquals(6, returned);
    }
}