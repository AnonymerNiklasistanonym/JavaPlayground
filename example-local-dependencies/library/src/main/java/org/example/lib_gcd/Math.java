package org.example.lib_gcd;

/**
 * Computing the least common multiple and the greatest common divisor.
 *
 */
public class Math {
    /**
     * Retrieve the greatest common divisor (GCD) of two non-zero integer values.
     *
     * Example: The GCD of 18 and 12 is 6.
     *
     * @param a The first non-zero integer value
     * @param b The second non-zero integer value
     * @return The GCD of a and b
     */
    public static long getGcd(long a, long b) {
        if (a == 0 && b == 0) {
            return 1;
        }  else {
            a = java.lang.Math.abs(a); // More efficient than if (a < 0) {a = -a;}
            b = java.lang.Math.abs(b);
            if (a == 0) { // b != 0, see condition before
                return b;
            } else if (b == 0) {
                return a;
            } else {
                // Now we actually implement Euclid's algorithm by following
                // http://www.math.rutgers.edu/~greenfie/gs2004/euclid.html

                if (a < b) { // Swap the values of a and b if required
                    final long tmp = a; // Save a's value temporarily
                    a = b;
                    b = tmp;
                }
                while (0 != b) {
                    long r = a % b;
                    a = b;
                    b = r;
                }
                return a;
            }
        }
    }
    /**
     * The least common multiple of two given non-zero values.
     *
     * Example: The least common multiple of 10 and 6 is 30.
     *
     * @param a The first value
     * @param b The second value
     * @return  The common multiple of two given values.
     */
    public static long getLeastCommonMultiple(long a, long b) {
        final long gcd = getGcd(a, b);
        if (1 == gcd) {
            return a * b;
        } else {
            return (a / gcd) * b;
        }
    }
}

