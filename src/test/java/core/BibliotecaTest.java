package core;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.GregorianCalendar;

class BibliotecaTest {

    @Test
    void calculaDevolucaoUsesTodayAndAdjustsWeekendCorrectly() {
        GregorianCalendar today = new GregorianCalendar();

        GregorianCalendar expected = (GregorianCalendar) today.clone();

        if (expected.get(GregorianCalendar.DAY_OF_WEEK) < GregorianCalendar.MONDAY) {
            expected.add(GregorianCalendar.DAY_OF_YEAR, 1);
        } else if (expected.get(GregorianCalendar.DAY_OF_WEEK) > GregorianCalendar.FRIDAY) {
            expected.add(GregorianCalendar.DAY_OF_YEAR, 2);
        } else {
            expected.add(GregorianCalendar.WEEK_OF_YEAR, 1);
        }

        expected.set(GregorianCalendar.HOUR, 23);
        expected.set(GregorianCalendar.MINUTE, 59);
        expected.set(GregorianCalendar.SECOND, 59);
        expected.set(GregorianCalendar.MILLISECOND, 0);

        GregorianCalendar actual = Biblioteca.calculaDevolucao(1);

        assertEquals(
                expected.get(GregorianCalendar.YEAR),
                actual.get(GregorianCalendar.YEAR));
        assertEquals(
                expected.get(GregorianCalendar.MONTH),
                actual.get(GregorianCalendar.MONTH));
        assertEquals(
                expected.get(GregorianCalendar.DAY_OF_MONTH),
                actual.get(GregorianCalendar.DAY_OF_MONTH));
        assertEquals(
                expected.get(GregorianCalendar.HOUR),
                actual.get(GregorianCalendar.HOUR));
        assertEquals(
                expected.get(GregorianCalendar.MINUTE),
                actual.get(GregorianCalendar.MINUTE));
        assertEquals(
                expected.get(GregorianCalendar.SECOND),
                actual.get(GregorianCalendar.SECOND));
    }
}
