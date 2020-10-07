package training.supportbank.service;

import static training.supportbank.utils.Utils.basisPointsToAmount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import training.supportbank.model.Transaction;
import training.supportbank.model.TransactionDB;

public class Banker {
    public void listAll(TransactionDB database) {
        Map<String, Integer> account = getAllAccounts(database);
        List<String> sortedNames = account.keySet().stream().sorted().collect(Collectors.toList());
        for (String name : sortedNames) {
            System.out.println(formatAccountDetails(name, account.get(name)));
        }
    }

    public static String formatAccountDetails(String name, int amountBasisPoints) {
        return String.format("%10s: £%s", name, basisPointsToAmount(amountBasisPoints));
    }

    public Map<String, Integer> getAllAccounts(TransactionDB database) {
        Map<String, Integer> account = new HashMap<>();
        for (Transaction transaction : database.getTransactions()) {
            String from = transaction.getFrom();
            String to = transaction.getTo();
            addIfDoesntExist(account, from);
            addIfDoesntExist(account, to);

            int amountBasisPoints = transaction.getAmountBasisPoints();
            account.put(from, account.get(from) - amountBasisPoints);
            account.put(to, account.get(to) + amountBasisPoints);
        }
        return account;
    }

    private void addIfDoesntExist(Map<String, Integer> debts, String name) {
        if (!debts.keySet().contains(name)) {
            debts.put(name, 0);
        }
    }
}
