package training.supportbank;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.Scanner;

import training.supportbank.model.TransactionDB;
import training.supportbank.model.TransactionDBMemory;
import training.supportbank.service.Banker;

public class Main {
    private static final String OPERATION_LIST = "list";
    private static TransactionDB database;
    public static void main(String args[]) throws FileNotFoundException {
        System.out.println("Initialising database...");
        database = new TransactionDBMemory();
//        FileReader reader = new FileReader("DodgyTransactions2015.csv");
         FileReader reader = new FileReader("Transactions2014.csv");
        int count = database.initialise(reader);
        System.out.println(String.format("Read %d items from csv file", count));

        if (args.length != 2) {
            System.out.println("Usage: <appname> List [All|<Account>]");
            System.out.println("e.g. <appname> List \"Jon A\"");
            System.out.println("Alternatively, use the console here.  Enter 'Quit' to quit.");
            useConsole();
            return;
        }
        String operation = args[0];
        String parameter = args[1];
        doOperation(operation, parameter);
    }

    private static void useConsole() {
        String lineRead;
        Scanner scanner = new Scanner(System.in);
        String operation;
        String parameter;
        do {
            System.out.println("Enter command:");
            lineRead = scanner.nextLine();
            if (lineRead.indexOf(" ") == -1) {
                operation = lineRead;
                parameter = "";
            } else {
                operation = lineRead.substring(0, lineRead.indexOf(" "));
                parameter = lineRead.substring(lineRead.indexOf(" ") + 1);
            }
            doOperation(operation, parameter);
        } while (!lineRead.equalsIgnoreCase("quit") && !lineRead.equalsIgnoreCase("q"));
        scanner.close();
    }

    private static void doOperation(String operation, String parameter) {
        Banker banker = new Banker();
        if (operation.equalsIgnoreCase(OPERATION_LIST)) {
            if (parameter.equalsIgnoreCase("all")) {
                banker.listAll(database);
            } else {
                Map<String, Integer> allAccounts = banker.getAllAccounts(database);
                if (allAccounts.containsKey(parameter)) {
                    database.getTransactions(parameter).stream().forEach(System.out::println);
                    System.out.println(Banker.formatAccountDetails(parameter, allAccounts.get(parameter)));
                } else {
                    System.out.println(String.format("No such name: %s", parameter));
                }
            }
        }
    }
}
