package training.supportbank;

import java.io.FileNotFoundException;
import java.io.FileReader;

import training.supportbank.model.TransactionDB;
import training.supportbank.model.TransactionDBMemory;

public class Main {
    private static final String OPERATION_LIST = "list";
    public static void main(String args[]) throws FileNotFoundException {
        if (args.length != 2) {
            System.out.println("Usage: <appname> List [All|<Account>]");
            return;
        }
        String operation = args[0];
        String parameter = args[1];
        TransactionDB database = new TransactionDBMemory();
        FileReader reader = new FileReader("Transactions2014.csv");
        database.initialise(reader);

        if (operation.equalsIgnoreCase(OPERATION_LIST)) {
            if (parameter.equalsIgnoreCase("all")) {
                database.getTransactions().stream().forEach(System.out::println);
            } else {
                database.getTransactions(parameter).stream().forEach(System.out::println);
            }
        }
    }
}
