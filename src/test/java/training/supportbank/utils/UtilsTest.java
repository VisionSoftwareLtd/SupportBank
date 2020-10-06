package training.supportbank.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class UtilsTest {
    @ParameterizedTest
    @ValueSource(strings = {"123.45", "1.20", "3.4"})
    public void validAmount(String amount) {
        int basisPoints = Utils.amountToBasisPoints(amount);
        double value = Double.parseDouble(amount);
        assertEquals(value, (double)(basisPoints) / 100);
    }

    @ParameterizedTest
    @ValueSource(strings = {"123.45.789", "foobar"})
    public void invalidAmount(String amount) {
        assertThrows(NumberFormatException.class, () -> Utils.amountToBasisPoints(amount));
    }
}
