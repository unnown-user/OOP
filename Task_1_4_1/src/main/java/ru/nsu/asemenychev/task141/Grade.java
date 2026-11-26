package ru.nsu.asemenychev.task141;

/**
 * Запись о полученной оценке.
 */
public class Grade {

    private final String subject;
    private final GradeValue value;
    private final ControlType type;

    /**
     * Оценки в зачетной книжке.
     */
    public enum GradeValue {
        EXCELLENT(5),
        GOOD(4),
        SATISFACTORY(3),
        UNSATISFACTORY(2);

        private final int value;

        /**
         * Создает оценку с числовым значением.
         *
         * @param value числовое значение оценки
         */
        GradeValue(int value) {
            this.value = value;
        }

        /**
         * Возвращает числовое значение оценки.
         *
         * @return числовое значение
         */
        public int getVal() {
            return value;
        }
    }

    /**
     * Вид контрольного мероприятия.
     */
    public enum ControlType {
        EXAM,
        DIFF_PASS,
        CREDIT
    }

    /**
     * Создает запись об оценке.
     *
     * @param subject название предмета
     * @param value   полученная оценка
     * @param type    вид контроля
     */
    public Grade(String subject, GradeValue value, ControlType type) {
        this.value = value;
        this.subject = subject;
        this.type = type;
    }

    /**
     * Возвращает название предмета.
     *
     * @return название предмета
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Возвращает оценку.
     *
     * @return оценка
     */
    public GradeValue getValue() {
        return value;
    }

    /**
     * Возвращает вид контроля.
     *
     * @return вид контроля
     */
    public ControlType getType() {
        return type;
    }
}
