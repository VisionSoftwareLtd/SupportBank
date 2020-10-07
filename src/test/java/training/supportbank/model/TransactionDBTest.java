package training.supportbank.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static training.supportbank.utils.Utils.localDateFromString;

import java.io.StringReader;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TransactionDBTest {
    private final String testCsv = "Date,From,To,Narrative,Amount\n" + 
        "01/01/2014,Jon A,Sarah T,Pokemon Training,7.8\n" + 
        "04/01/2014,Stephen S,Tim L,Lunch,4.37\n" +
        "07/01/2014,Stephen S,Jon A,Cup of tea,2.6\n";

    @ParameterizedTest
    @MethodSource("instancesToTest")
    public void initialise(TransactionDB database) {
        StringReader reader = new StringReader(testCsv);
        int count = database.initialise(reader);
        assertEquals(3, count);
    }

    @ParameterizedTest
    @MethodSource("instancesToTest")
    public void getTransactions(TransactionDB database) {
        initialise(database);
        List<Transaction> transactions = database.getTransactions();
        Transaction jonA = transactions.get(0);
        assertEquals(localDateFromString("01/01/2014"), jonA.getDate());
        assertEquals("Jon A", jonA.getFrom());
        assertEquals("Sarah T", jonA.getTo());
        assertEquals("Pokemon Training", jonA.getNarrative());
        assertEquals(780, jonA.getAmountBasisPoints());
        assertEquals("7.80", jonA.getAmount());

        Transaction stephenS = transactions.get(1);
        assertEquals(localDateFromString("04/01/2014"), stephenS.getDate());
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
        assertEquals(2, jonATransactions.size());
        Transaction jonAfrom = jonATransactions.get(0);
        assertEquals(localDateFromString("01/01/2014"), jonAfrom.getDate());
        assertEquals("Jon A", jonAfrom.getFrom());
        assertEquals("Sarah T", jonAfrom.getTo());
        assertEquals("Pokemon Training", jonAfrom.getNarrative());
        assertEquals(780, jonAfrom.getAmountBasisPoints());
        assertEquals("7.80", jonAfrom.getAmount());

        Transaction jonAto = jonATransactions.get(1);
        assertEquals(localDateFromString("07/01/2014"), jonAto.getDate());
        assertEquals("Stephen S", jonAto.getFrom());
        assertEquals("Jon A", jonAto.getTo());
        assertEquals("Cup of tea", jonAto.getNarrative());
        assertEquals(260, jonAto.getAmountBasisPoints());
        assertEquals("2.60", jonAto.getAmount());
    }

    @ParameterizedTest
    @MethodSource("instancesToTest")
    public void getNonExistantTransactions(TransactionDB database) {
        initialise(database);
        List<Transaction> jonATransactions = database.getTransactions("NON EXISTANT");
        assertEquals(0, jonATransactions.size());
    }

    public static Stream<Arguments> instancesToTest() {
        return Stream.of(
            Arguments.of(new TransactionDBMemory())
        );
    }
}
