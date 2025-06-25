package org.example;

import org.example.lib_gcd.Math;

/**
 * Representing Fractions
 *
 */
public class Fraction {

    public static void main(String[] args) {
        // Input
        final Fraction
                twoThird = new Fraction(2, 3),          // Construct a fraction object (2/3)
                threeSeven = new Fraction(3, 7);        // Construct a fraction object (3/7)

        // Computed results
        final Fraction
                sum = twoThird.add(threeSeven),         // (2/3) + (3/7)
                product = twoThird.mult(threeSeven);    // (2/3) * (3/7)

        System.out.println("(2/3) + (3/7) = (23/21) = " + sum.getValue());
        System.out.println("(2/3) * (3/7) = (2/7) = " + product.getValue());
    }

    long numerator,denominator;


    /**
     * @param numerator The fraction's numerator.
     * @param denominator The fraction's denominator.
     */
    public Fraction(long numerator, long denominator) {
        final long gcd = Math.getGcd(numerator, denominator);
        if (1 < gcd)  {
            setNumerator(numerator / gcd);
            setDenominator(denominator / gcd);
        }
    }
    /**
     * @return The fraction's decimal value e.g. 1/3 = 0.3333...
     */
    public double getValue(){
        return ((double) numerator) / denominator;
    }
    /**
     * @return The fraction's numerator
     */
    public long getNumerator() {
        return numerator;
    }
    /**
     * @param numerator Set the fraction's numerator.
     */
    public void setNumerator(long numerator) {
        this.numerator = numerator;
    }
    /**
     * @return
     *       The fraction's denominator value
     */
    public long getDenominator() {
        return denominator;
    }
    /**
     *
     * @param denominator Set the fraction's denominator
     */
    public void setDenominator(long denominator) {
        this.denominator = denominator;
    }

    /**
     * Add a second fraction to the current instance.
     *
     * @param f A second fraction.
     *
     * @return The sum of the current instance and f according to:
     *   \[ {a\over b} + {x\over y} = {{ay + bx}\over by} \]
     */
    public Fraction add(Fraction f) {

        final long gcd = Math.getGcd(denominator, f.denominator);

        return new Fraction( numerator * (f.denominator / gcd) + (denominator / gcd) * f.numerator,
                (denominator / gcd) * f.denominator);
    }
    /**
     * Multiply a second fraction by this instance
     * @param f Building the product with this second fraction.
     *
     * @return The result of multiplying the current instance by f according to:
     *   \[ {a\over b} \cdot {x\over y} = {{ax}\over by} \]
     */
    public Fraction mult(Fraction f) {
        final Fraction f1 = new Fraction(f.numerator, denominator),
                f2 = new Fraction(numerator, f.denominator);

        return new Fraction(f1.numerator * f2.numerator,
                f1.denominator * f2.denominator);
    }
}
