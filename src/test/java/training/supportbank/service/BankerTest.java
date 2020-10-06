package training.supportbank.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import training.supportbank.model.TransactionDB;
import training.supportbank.model.TransactionDBMemory;

public class BankerTest {
    private final String singleDebtCsv = "Date,From,To,Narrative,Amount\n" + 
        "01/01/2014,Jon A,Sarah T,Pokemon Training,7.8\n";
    private final String opposingDebtCsv = "Date,From,To,Narrative,Amount\n" + 
        "01/01/2014,Jon A,Sarah T,Pokemon Training,8\n" +
        "01/01/2014,Sarah T,Jon A,Pokemon Training,4\n";
    private final String threeWayDebtCsv = "Date,From,To,Narrative,Amount\n" + 
        "01/01/2014,Jon A,Sarah T,Pokemon Training,8\n" +
        "01/01/2014,Sarah T,Bob,Pokemon Training,4\n" +
        "01/01/2014,Bob,Jon A,Pokemon Training,2\n";

    private TransactionDB database;
    private Banker banker = new Banker();

    @BeforeEach
    public void setup() {
        database = new TransactionDBMemory();
    }

    @Test
    public void singleDebt() {
        StringReader reader = new StringReader(singleDebtCsv);
        database.initialise(reader);
        Map<String, Integer> accounts = banker.getAllAccounts(database);
        assertEquals(-780, accounts.get("Jon A"));
        assertEquals(780, accounts.get("Sarah T"));
    }

    @Test
    public void opposingDebt() {
        StringReader reader = new StringReader(opposingDebtCsv);
        database.initialise(reader);
        Map<String, Integer> accounts = banker.getAllAccounts(database);
        assertEquals(-400, accounts.get("Jon A"));
        assertEquals(400, accounts.get("Sarah T"));
    }

    @Test
    public void threeWayDebt() {
        StringReader reader = new StringReader(threeWayDebtCsv);
        database.initialise(reader);
        Map<String, Integer> accounts = banker.getAllAccounts(database);
        assertEquals(-600, accounts.get("Jon A"));
        assertEquals(400, accounts.get("Sarah T"));
        assertEquals(200, accounts.get("Bob"));
    }
}
