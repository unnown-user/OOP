package ru.nsu.asemenychev.task113;

/**
 * Разборщик математических выражений из строки.
 *
 * <p>Поддерживает два стиля записи:
 * <ul>
 *     <li>со скобками вокруг каждой операции — {@code (3+(2*x))};</li>
 *     <li>без скобок, с обычным математическим приоритетом — {@code 3+2*x}.</li>
 * </ul>
 * Пробелы игнорируются. Приоритет операций: {@code * /} выше, чем {@code + -}.
 */
public class ExpressionParser {

    private final String input;
    private int pos;

    private ExpressionParser(String input) {
        this.input = input;
        this.pos = 0;
    }

    /**
     * Разбирает строку в выражение.
     *
     * @param input строка вида "(3+(2*x))"
     * @return построенное выражение
     * @throws IllegalArgumentException при некорректном вводе
     */
    public static Expression parse(String input) {
        ExpressionParser parser = new ExpressionParser(input);
        Expression result = parser.parseAdditive();
        parser.skipWhitespace();
        if (parser.pos < input.length()) {
            throw new IllegalArgumentException("Лишние символы на позиции " + parser.pos);
        }
        return result;
    }

    /**
     * Разбирает сумму и разность: {@code term (('+' | '-') term)*}.
     */
    private Expression parseAdditive() {
        Expression left = parseMultiplicative();
        while (true) {
            skipWhitespace();
            char c = peek();
            if (c != '+' && c != '-') {
                break;
            }
            pos++;
            Expression right = parseMultiplicative();
            left = (c == '+') ? new Add(left, right) : new Sub(left, right);
        }
        return left;
    }

    /**
     * Разбирает произведение и частное: {@code factor (('*' | '/') factor)*}.
     */
    private Expression parseMultiplicative() {
        Expression left = parseFactor();
        while (true) {
            skipWhitespace();
            char c = peek();
            if (c != '*' && c != '/') {
                break;
            }
            pos++;
            Expression right = parseFactor();
            left = (c == '*') ? new Mul(left, right) : new Div(left, right);
        }
        return left;
    }

    /**
     * Разбирает множитель: скобочную группу, константу или переменную.
     */
    private Expression parseFactor() {
        skipWhitespace();
        if (peek() == '(') {
            pos++;
            Expression inner = parseAdditive();
            skipWhitespace();
            expect(')');
            return inner;
        }
        return parseAtomic();
    }

    /**
     * Разбирает константу или переменную.
     */
    private Expression parseAtomic() {
        skipWhitespace();
        StringBuilder sb = new StringBuilder();
        while (pos < input.length()) {
            char c = input.charAt(pos);
            if (c == '+' || c == '-' || c == '*' || c == '/'
                    || c == '(' || c == ')' || Character.isWhitespace(c)) {
                break;
            }
            sb.append(c);
            pos++;
        }

        String token = sb.toString();
        if (token.isEmpty()) {
            throw new IllegalArgumentException("Ожидался токен на позиции " + pos);
        }

        if (isNumeric(token)) {
            return new Number(Integer.parseInt(token));
        }
        return new Variable(token);
    }

    private boolean isNumeric(String s) {
        if (s.isEmpty()) {
            return false;
        }
        int start = s.charAt(0) == '-' ? 1 : 0;
        if (s.length() == start) {
            return false;
        }
        for (int i = start; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private char peek() {
        return pos < input.length() ? input.charAt(pos) : '\0';
    }

    private void expect(char c) {
        skipWhitespace();
        if (pos >= input.length() || input.charAt(pos) != c) {
            throw new IllegalArgumentException("Ожидался '" + c + "' на позиции " + pos);
        }
        pos++;
    }

    private void skipWhitespace() {
        while (pos < input.length() && Character.isWhitespace(input.charAt(pos))) {
            pos++;
        }
    }
}