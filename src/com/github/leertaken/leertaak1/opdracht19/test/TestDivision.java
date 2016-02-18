package com.github.leertaken.leertaak1.opdracht19.test;

import com.github.leertaken.leertaak1.opdracht19.multiformat.Calculator;
import com.github.leertaken.leertaak1.opdracht19.multiformat.FormatException;
import com.github.leertaken.leertaak1.opdracht19.multiformat.NumberBaseException;
import junit.framework.TestCase;

/**
 * Created by Hp user on 17-2-2016.
 */
public class TestDivision extends TestCase {

    public TestDivision(String arg0) {
        super(arg0);
    }

    public void testOperations() {

        Calculator calc = new Calculator();

        try {
            calc.addOperand("3");
            assertEquals("0.0", calc.firstOperand());
            assertEquals("3.0", calc.secondOperand());

            calc.addOperand("0");
            assertEquals("3.0", calc.firstOperand());
            assertEquals("0.0", calc.secondOperand());

            calc.divide();
            assertEquals("0.0", calc.firstOperand());
            assertEquals("0.0", calc.secondOperand());
        } catch (FormatException | NumberBaseException e) {
            fail("Unexpected format exception");
        }
    }
}


