package training.supportbank.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static training.supportbank.utils.Utils.amountToBasisPoints;
import static training.supportbank.utils.Utils.basisPointsToAmount;
import static training.supportbank.utils.Utils.localDateFromString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class UtilsTest {
    @ParameterizedTest
    @ValueSource(strings = { "123.45", "1.20", "3.4" })
    public void validAmount(String amount) {
        int basisPoints = amountToBasisPoints(amount);
        double value = Double.parseDouble(amount);
        assertEquals(value, (double) (basisPoints) / 100);
    }

    @ParameterizedTest
    @ValueSource(strings = { "123.45.789", "foobar" })
    public void invalidAmount(String amount) {
        int basisPoints = amountToBasisPoints(amount);
        assertEquals(0, basisPoints);
    }

    @ParameterizedTest
    @CsvSource(value = { "12345,'123.45'", "120,'1.20'", "34,'0.34'", "2,'0.02'" })
    public void validBasisPoints(int basisPoints, String expected) {
        String amount = basisPointsToAmount(basisPoints);
        assertEquals(expected, amount);
    }

    @ParameterizedTest
    @ValueSource(strings = { "01/01/2020", "30/09/2014", "31/12/1969" })
    public void validDate(String date) {
        LocalDate localDate = localDateFromString(date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        assertEquals(LocalDate.parse(date, formatter), localDate);
    }

    @ParameterizedTest
    @ValueSource(strings = { "32/01/2020", "-1/09/2014", "penguin" })
    public void invalidDate(String date) {
        LocalDate localDate = localDateFromString(date);
        assertEquals(LocalDate.MIN, localDate);
    }
}
