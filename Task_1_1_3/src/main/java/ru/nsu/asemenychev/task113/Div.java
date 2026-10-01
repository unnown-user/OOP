package ru.nsu.asemenychev.task113;

/**
 * Частное двух выражений.
 */
public class Div extends BinaryOperation {

    /**
     * @param left  делимое
     * @param right делитель
     */
    public Div(Expression left, Expression right) {
        super(left, right, "/");
    }

    @Override
    protected int apply(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Деление на ноль");
        }
        return a / b;
    }

    @Override
    public Expression derivative(String variable) {
        // (f/g)' = (f'*g - f*g') / g^2
        return new Div(
                new Sub(
                        new Mul(left.derivative(variable), right),
                        new Mul(left, right.derivative(variable))
                ),
                new Mul(right, right)
        );
    }

    @Override
    public Expression simplify() {
        Expression l = left.simplify();
        Expression r = right.simplify();

        if (isNumber(l, 0)) {
            return new Number(0);
        }
        if (isNumber(r, 1)) {
            return l;
        }

        Integer lv = asNumber(l);
        Integer rv = asNumber(r);
        if (lv != null && rv != null) {
            if (rv == 0) {
                throw new ArithmeticException("Деление на ноль");
            }
            return new Number(lv / rv);
        }
        return new Div(l, r);
    }
}