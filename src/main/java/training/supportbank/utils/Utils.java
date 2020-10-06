package training.supportbank.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utils {
    private Utils() {}

    public static int amountToBasisPoints(String amount) {
		double amountDouble = Double.parseDouble(amount);
		return (int)(amountDouble * 100);
	}

	public static LocalDate instantFromDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
		return LocalDate.parse(date, formatter);
	}
}
