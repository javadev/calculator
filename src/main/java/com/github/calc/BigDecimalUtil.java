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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * BigDecimal utilities.
 *
 * @author Valentyn Kolesnikov
 * @version $Revision$ $Date$
 */
public final class BigDecimalUtil {
    private static final int SCALE = 18;
    public static long ITER = 1000;
    public static MathContext context = new MathContext( 100 );
    private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;
    public static BigDecimal PI_DIV_180
        = new BigDecimal("3.1415926535897932384626433832795").divide(BigDecimal.valueOf(180), 32, BigDecimal.ROUND_HALF_UP);
    public static BigDecimal PI_DIV_200
        = new BigDecimal("3.1415926535897932384626433832795").divide(BigDecimal.valueOf(200), 32, BigDecimal.ROUND_HALF_UP);

    private BigDecimalUtil() {
    }

    /**
     * Compute the square root of x to a given scale, x >= 0.
     * Use Newton's algorithm.
     * @param x the value of x
     * @return the result value
     */
    public static BigDecimal sqrt(BigDecimal x) {
        // Check that x >= 0.
        if (x.signum() < 0) {
            throw new ArithmeticException("x < 0");
        }

        // n = x*(10^(2*SCALE))
        BigInteger n = x.movePointRight(SCALE << 1).toBigInteger();

        // The first approximation is the upper half of n.
        int bits = (n.bitLength() + 1) >> 1;
        BigInteger ix = n.shiftRight(bits);
        BigInteger ixPrev;

        // Loop until the approximations converge
        // (two successive approximations are equal after rounding).
        do {
            ixPrev = ix;

            // x = (x + n/x)/2
            ix = ix.add(n.divide(ix)).shiftRight(1);

            Thread.yield();
        } while (ix.compareTo(ixPrev) != 0);

        return new BigDecimal(ix, SCALE);
    }

    public static BigDecimal ln(BigDecimal x) {
        if (x.equals(BigDecimal.ONE)) {
            return BigDecimal.ZERO;
        }

        x = x.subtract(BigDecimal.ONE);
        BigDecimal ret = new BigDecimal(ITER + 1);
        for (long i = ITER; i >= 0; i--) {
        BigDecimal N = new BigDecimal(i / 2 + 1).pow(2);
            N = N.multiply(x, context);
            ret = N.divide(ret, context);

            N = new BigDecimal(i + 1);
            ret = ret.add(N, context);

        }

        ret = x.divide(ret, context);
        return ret;
    }

    public static BigDecimal cosine(BigDecimal x) {

        BigDecimal currentValue = BigDecimal.ONE;
        BigDecimal lastVal      = currentValue.add(BigDecimal.ONE);
        BigDecimal xSquared     = x.multiply(x);
        BigDecimal numerator    = BigDecimal.ONE;
        BigDecimal denominator  = BigDecimal.ONE;
        int        i            = 0;

        while (lastVal.compareTo(currentValue) != 0) {
            lastVal = currentValue;

            int z = 2 * i + 2;

            denominator = denominator.multiply(BigDecimal.valueOf(z));
            denominator = denominator.multiply(BigDecimal.valueOf(z - 1));
            numerator   = numerator.multiply(xSquared);

            BigDecimal term = numerator.divide(denominator, SCALE + 5, ROUNDING_MODE);

            if (i % 2 != 0) {
                currentValue = currentValue.add(term);
            } else {
                currentValue = currentValue.subtract(term);
            }
            i++;
        }

        return currentValue;
    }

    public static BigDecimal sine(BigDecimal x) {
        BigDecimal lastVal      = x.add(BigDecimal.ONE);
        BigDecimal currentValue = x;
        BigDecimal xSquared     = x.multiply(x);
        BigDecimal numerator    = x;
        BigDecimal denominator  = BigDecimal.ONE;
        int        i            = 0;

        while (lastVal.compareTo(currentValue) != 0) {
            lastVal = currentValue;

            int z = 2 * i + 3;

            denominator = denominator.multiply(BigDecimal.valueOf(z));
            denominator = denominator.multiply(BigDecimal.valueOf(z - 1));
            numerator   = numerator.multiply(xSquared);

            BigDecimal term = numerator.divide(denominator, SCALE + 5, ROUNDING_MODE);

            if (i % 2 != 0) {
                currentValue = currentValue.add(term);
            } else {
                currentValue = currentValue.subtract(term);
            }

            i++;
        }
        return currentValue;
    }

    public static BigDecimal tangent(BigDecimal x) {

        BigDecimal sin = sine(x);
        BigDecimal cos = cosine(x);

        return sin.divide(cos, SCALE, BigDecimal.ROUND_HALF_UP);
    }
    
    public static BigDecimal log10(BigDecimal b) {
        final int NUM_OF_DIGITS = SCALE + 2;
            // need to add one to get the right number of dp
            //  and then add one again to get the next number
            //  so I can round it correctly.
    
        MathContext mc = new MathContext(NUM_OF_DIGITS, RoundingMode.HALF_EVEN);
        //special conditions:
        // log(-x) -> exception
        // log(1) == 0 exactly;
        // log of a number lessthan one = -log(1/x)
        if(b.signum() <= 0) {
                throw new ArithmeticException("log of a negative number! (or zero)");
            }
        else if(b.compareTo(BigDecimal.ONE) == 0) {
                return BigDecimal.ZERO;
            } else if(b.compareTo(BigDecimal.ONE) < 0) {
                return (log10((BigDecimal.ONE).divide(b,mc))).negate();
            }
    
        StringBuilder sb = new StringBuilder();
        //number of digits on the left of the decimal point
        int leftDigits = b.precision() - b.scale();
    
        //so, the first digits of the log10 are:
        sb.append(leftDigits - 1).append(".");
    
        //this is the algorithm outlined in the webpage
        int n = 0;
        while (n < NUM_OF_DIGITS) {
            b = (b.movePointLeft(leftDigits - 1)).pow(10, mc);
            leftDigits = b.precision() - b.scale();
            sb.append(leftDigits - 1);
            n++;
        }
    
        BigDecimal ans = new BigDecimal(sb.toString());
    
        //Round the number to the correct number of decimal places.
        ans = ans.round(new MathContext(ans.precision() - ans.scale() + SCALE, RoundingMode.HALF_EVEN));
        return ans;
    }
    
    public static BigDecimal cuberoot(BigDecimal b) {
        // Specify a math context with 40 digits of precision.

        MathContext mc = new MathContext(40);

        BigDecimal x = new BigDecimal("1", mc);

        // Search for the cube root via the Newton-Raphson loop. Output each // successive iteration's value.

        for (int i = 0; i < ITER; i++) {
            x = x.subtract(x.pow(3, mc).subtract(b, mc).divide(new BigDecimal("3", mc).multiply(x.pow(2, mc), mc), mc), mc);
        }
        return x;
    }
}
