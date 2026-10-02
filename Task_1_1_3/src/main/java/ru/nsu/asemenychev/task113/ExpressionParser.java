package ru.nsu.asemenychev.task113;

/**
 * Разборщик выражений из строки.
 * Каждое бинарное выражение записывается в скобках: "(3+(2*x))".
 * Пробелы игнорируются.
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
        Expression result = parser.parseExpression();
        parser.skipWhitespace();
        if (parser.pos < input.length()) {
            throw new IllegalArgumentException("Лишние символы на позиции " + parser.pos);
        }
        return result;
    }

    private Expression parseExpression() {
        skipWhitespace();
        if (peek() == '(') {
            return parseBinary();
        }
        return parseAtomic();
    }

    private Expression parseBinary() {
        expect('(');
        Expression left = parseExpression();
        skipWhitespace();
        char op = next();
        Expression right = parseExpression();
        skipWhitespace();
        expect(')');

        return switch (op) {
            case '+' -> new Add(left, right);
            case '-' -> new Sub(left, right);
            case '*' -> new Mul(left, right);
            case '/' -> new Div(left, right);
            default -> throw new IllegalArgumentException("Неизвестный оператор: " + op);
        };
    }

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

    private char next() {
        if (pos >= input.length()) {
            throw new IllegalArgumentException("Неожиданный конец строки");
        }
        return input.charAt(pos++);
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