/*
 * $Id$
 *
 * Copyright 2013 Valentyn Kolesnikov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.calc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**.
 * @author Valentyn Kolesnikov
 * @version $Revision$ $Date$
 */
public class CalcTest {
    private Calc calc;

    @Before
    public void setUp() {
        calc = new Calc();
    }

    @Test
    public void add() throws Exception {
        Method method = calc.getClass().getDeclaredMethod("keyDetect", new Class[] { java.awt.event.ActionEvent.class });
        method.setAccessible(true);
        Method methodFCalc = calc.getClass().getDeclaredMethod("fCalc", new Class[] { java.lang.String.class });
        methodFCalc.setAccessible(true);
        method.invoke(calc, new java.awt.event.ActionEvent("source", 1, "3"));
        method.invoke(calc, new java.awt.event.ActionEvent("source", 1, ","));
        method.invoke(calc, new java.awt.event.ActionEvent("source", 1, "1"));
        method.invoke(calc, new java.awt.event.ActionEvent("source", 1, "1"));
        methodFCalc.invoke(calc, "-");
        method.invoke(calc, new java.awt.event.ActionEvent("source", 1, "3"));
        method.invoke(calc, new java.awt.event.ActionEvent("source", 1, ","));
        method.invoke(calc, new java.awt.event.ActionEvent("source", 1, "1"));
        methodFCalc.invoke(calc, "=");
        Field field = calc.getClass().getDeclaredField("jTextField1");
        field.setAccessible(true);
        javax.swing.JTextPane jTextField1 = (javax.swing.JTextPane) field.get(calc);
        Assert.assertEquals("Value must be 0,01, but " + jTextField1.getText(),
            "0,01", jTextField1.getText());
    }

    @Test
    public void sqrt() throws Exception {
        Method method = calc.getClass().getDeclaredMethod("keyDetect", new Class[] { java.awt.event.ActionEvent.class });
        method.setAccessible(true);
        Method methodFCalc = calc.getClass().getDeclaredMethod("fCalc", new Class[] { java.lang.String.class });
        methodFCalc.setAccessible(true);
        method.invoke(calc, new java.awt.event.ActionEvent("source", 1, "9"));
        methodFCalc.invoke(calc, "sqrt");
        Field field = calc.getClass().getDeclaredField("jTextField1");
        field.setAccessible(true);
        javax.swing.JTextPane jTextField1 = (javax.swing.JTextPane) field.get(calc);
        Assert.assertEquals("Value must be 3, but " + jTextField1.getText(),
            "3", jTextField1.getText());
    }

}
