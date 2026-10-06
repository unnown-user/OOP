package ru.nsu.asemenychev.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Тесты для класса {@link ExpressionParser}.
 */
class ExpressionParserTest {

    @Test
    void parserBasic() {
        assertEquals("(3+(2*x))", ExpressionParser.parse("(3+(2*x))").toString());
    }

    @Test
    void parserNumber() {
        assertEquals("42", ExpressionParser.parse("42").toString());
    }

    @Test
    void parserVariable() {
        assertEquals("x", ExpressionParser.parse("x").toString());
    }

    @Test
    void parserMultiLetterVariable() {
        assertEquals("alpha", ExpressionParser.parse("alpha").toString());
    }

    @Test
    void parserWithSpaces() {
        assertEquals("(3+(2*x))", ExpressionParser.parse("( 3 + ( 2 * x ) )").toString());
    }

    @Test
    void parserRoundTrip() {
        Expression e = ExpressionParser.parse("((a+b)*(c-d))");
        assertEquals("((a+b)*(c-d))", e.toString());
    }

    @Test
    void parserThrowsOnMissingClosingParen() {
        assertThrows(IllegalArgumentException.class, () -> ExpressionParser.parse("(3+2*4/6-1"));
    }

    @Test
    void parserThrowsOnMissingOpeningParen() {
        assertThrows(IllegalArgumentException.class, () -> ExpressionParser.parse("3+2*4/6-1)"));
    }
}