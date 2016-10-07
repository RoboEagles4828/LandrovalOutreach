package frc.team4828.landrovaltoast;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Tester {
    @Test
    public void test() {
        System.out.println("You're currently testing Tester!");
        TestClass test = new TestClass();
        int returned = test.testTester(3);
        assertEquals(6, returned);
    }
}