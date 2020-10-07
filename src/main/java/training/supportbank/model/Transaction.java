package training.supportbank.model;

import static training.supportbank.utils.Utils.amountToBasisPoints;
import static training.supportbank.utils.Utils.basisPointsToAmount;
import static training.supportbank.utils.Utils.localDateFromString;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Transaction {
    private LocalDate date;
    private String from;
    private String to;
    private String narrative;
    private int amountBasisPoints;

    public Transaction(String date, String from, String to, String narrative, String amount) {
        this.date = localDateFromString(date);
        this.from = from;
        this.to = to;
        this.narrative = narrative;
        this.amountBasisPoints = amountToBasisPoints(amount);
    }

    public String getAmount() {
        return basisPointsToAmount(amountBasisPoints);
    }
}