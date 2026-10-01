package ru.nsu.asemenychev.task113;

/**
 * Сумма двух выражений.
 */
public class Add extends BinaryOperation {

    /**
     * @param left  левое слагаемое
     * @param right правое слагаемое
     */
    public Add(Expression left, Expression right) {
        super(left, right, "+");
    }

    @Override
    protected int apply(int a, int b) {
        return a + b;
    }

    @Override
    public Expression derivative(String variable) {
        return new Add(left.derivative(variable), right.derivative(variable));
    }

    @Override
    public Expression simplify() {
        Expression l = left.simplify();
        Expression r = right.simplify();

        if (isNumber(l, 0)) {
            return r;
        }
        if (isNumber(r, 0)) {
            return l;
        }

        Integer lv = asNumber(l);
        Integer rv = asNumber(r);
        if (lv != null && rv != null) {
            return new Number(lv + rv);
        }
        return new Add(l, r);
    }
}