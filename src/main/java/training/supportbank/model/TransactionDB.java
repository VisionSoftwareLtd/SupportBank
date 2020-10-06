package training.supportbank.model;

import java.io.Reader;
import java.util.List;

public interface TransactionDB {
    int initialise(Reader csvReader);
    List<Transaction> getTransactions();
    List<Transaction> getTransactions(String account);
}
