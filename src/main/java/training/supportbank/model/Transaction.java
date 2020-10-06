package training.supportbank.model;

import static training.supportbank.utils.Utils.amountToBasisPoints;

import lombok.Data;

@Data
public class Transaction {
    private String date;
    private String from;
    private String to;
    private String narrative;
    private int amountBasisPoints;

    public Transaction(String date, String from, String to, String narrative, String amount) {
        this.date = date;
        this.from = from;
        this.to = to;
        this.narrative = narrative;
        this.amountBasisPoints = amountToBasisPoints(amount);
    }

    public double getAmount() {
        return (double)amountBasisPoints / 100;
    }
}