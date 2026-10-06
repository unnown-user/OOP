package ru.nsu.asemenychev.task113;

/**
 * Произведение двух выражений.
 */
public class Mul extends BinaryOperation {

    /**
     * Создаёт произведение двух выражений.
     *
     * @param left  левый множитель
     * @param right правый множитель
     */
    public Mul(Expression left, Expression right) {
        super(left, right, "*");
    }

    @Override
    protected int apply(int a, int b) {
        return a * b;
    }

    @Override
    public Expression derivative(String variable) {
        // (f*g)' = f'*g + f*g'
        return new Add(
                new Mul(left.derivative(variable), right),
                new Mul(left, right.derivative(variable))
        );
    }

    @Override
    public Expression simplify() {
        Expression l = left.simplify();
        Expression r = right.simplify();

        if (isNumber(l, 0) || isNumber(r, 0)) {
            return new Number(0);
        }
        if (isNumber(l, 1)) {
            return r;
        }
        if (isNumber(r, 1)) {
            return l;
        }

        Integer lv = asNumber(l);
        Integer rv = asNumber(r);
        if (lv != null && rv != null) {
            return new Number(lv * rv);
        }
        return new Mul(l, r);
    }
}