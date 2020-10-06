package training.supportbank.utils;

public class Utils {
    private Utils() {}

    public static int amountToBasisPoints(String amount) {
		double amountDouble = Double.parseDouble(amount);
		return (int)(amountDouble * 100);
	}
}
