package ru.nsu.asemenychev.task113;

/**
 * Разность двух выражений.
 */
public class Sub extends BinaryOperation {

    /**
     * Создаёт разность двух выражений.
     *
     * @param left  уменьшаемое
     * @param right вычитаемое
     */
    public Sub(Expression left, Expression right) {
        super(left, right, "-");
    }

    @Override
    protected int apply(int a, int b) {
        return a - b;
    }

    @Override
    public Expression derivative(String variable) {
        return new Sub(left.derivative(variable), right.derivative(variable));
    }

    @Override
    public Expression simplify() {
        Expression l = left.simplify();
        Expression r = right.simplify();

        if (isNumber(r, 0)) {
            return l;
        }
        if (l.toString().equals(r.toString())) {
            return new Number(0);
        }

        Integer lv = asNumber(l);
        Integer rv = asNumber(r);
        if (lv != null && rv != null) {
            return new Number(lv - rv);
        }
        return new Sub(l, r);
    }
}