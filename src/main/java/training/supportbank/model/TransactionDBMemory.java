package training.supportbank.model;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class TransactionDBMemory implements TransactionDB {
	private List<Transaction> database;

	@Override
	public int initialise(Reader csvReader) {
		Iterable<CSVRecord> records;
		try {
			records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvReader);
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		database = new LinkedList<>();
		for (CSVRecord record : records) {
			String date = record.get("Date");
			String from = record.get("From");
			String to = record.get("To");
			String narrative = record.get("Narrative");
			String amount = record.get("Amount");
			database.add(new Transaction(date, from, to, narrative, amount));
		}
		return database.size();
	}

	@Override
	public List<Transaction> getTransactions() {
		return database;
	}

	@Override
	public List<Transaction> getTransactions(String account) {
		return database.stream().filter((trans) -> trans.getFrom().equals(account)).collect(Collectors.toList());
	}
}