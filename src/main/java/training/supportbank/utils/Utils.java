package training.supportbank.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {
    private Utils() {}

    public static int amountToBasisPoints(String amount) {
		try {
			double amountDouble = Double.parseDouble(amount);
			return (int)(amountDouble * 100);
		} catch (NumberFormatException e) {
			log.error(String.format("Invalid amount: %s", amount));
			return 0;
		}
	}

	public static String basisPointsToAmount(int basisPoints) {
		return String.format("%.2f", (float)(basisPoints) / 100);
	}

	public static LocalDate localDateFromString(String date) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
			return LocalDate.parse(date, formatter);
		} catch (DateTimeParseException e) {
			log.error(String.format("Invalid date: %s", date));
			return LocalDate.MIN;
		}
	}
}
