package training.supportbank.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static training.supportbank.utils.Utils.instantFromDate;

import java.io.StringReader;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TransactionDBTest {
    private final String testCsv = "Date,From,To,Narrative,Amount\n" + 
        "01/01/2014,Jon A,Sarah T,Pokemon Training,7.8\n" + 
        "04/01/2014,Stephen S,Tim L,Lunch,4.37\n";

    @ParameterizedTest
    @MethodSource("instancesToTest")
    public void initialise(TransactionDB database) {
        StringReader reader = new StringReader(testCsv);
        int count = database.initialise(reader);
        assertEquals(2, count);
    }

    @ParameterizedTest
    @MethodSource("instancesToTest")
    public void getTransactions(TransactionDB database) {
        initialise(database);
        List<Transaction> transactions = database.getTransactions();
        Transaction jonA = transactions.get(0);
        assertEquals(instantFromDate("01/01/2014"), jonA.getDate());
        assertEquals("Jon A", jonA.getFrom());
        assertEquals("Sarah T", jonA.getTo());
        assertEquals("Pokemon Training", jonA.getNarrative());
        assertEquals(780, jonA.getAmountBasisPoints());
        assertEquals("7.80", jonA.getAmount());

        Transaction stephenS = transactions.get(1);
        assertEquals(instantFromDate("04/01/2014"), stephenS.getDate());
        assertEquals("Stephen S", stephenS.getFrom());
        assertEquals("Tim L", stephenS.getTo());
        assertEquals("Lunch", stephenS.getNarrative());
        assertEquals(437, stephenS.getAmountBasisPoints());
        assertEquals("4.37", stephenS.getAmount());
    }

    @ParameterizedTest
    @MethodSource("instancesToTest")
    public void getSpecificTransactions(TransactionDB database) {
        initialise(database);
        List<Transaction> jonATransactions = database.getTransactions("Jon A");
        assertEquals(1, jonATransactions.size());
        Transaction jonA = jonATransactions.get(0);
        assertEquals(instantFromDate("01/01/2014"), jonA.getDate());
        assertEquals("Jon A", jonA.getFrom());
        assertEquals("Sarah T", jonA.getTo());
        assertEquals("Pokemon Training", jonA.getNarrative());
        assertEquals(780, jonA.getAmountBasisPoints());
        assertEquals("7.80", jonA.getAmount());
    }

    public static Stream<Arguments> instancesToTest() {
        return Stream.of(
            Arguments.of(new TransactionDBMemory())
        );
    }
}
