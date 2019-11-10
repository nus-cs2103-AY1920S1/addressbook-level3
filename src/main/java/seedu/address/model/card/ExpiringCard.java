package seedu.address.model.card;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Set;

import seedu.address.commons.util.ExpiryUtil;
import seedu.address.model.tag.Tag;


/**
 * Represents an Expiring Card.
 */
public class ExpiringCard extends Card {

    private static DateTimeFormatter dateTimeFormat = new DateTimeFormatterBuilder()
            .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
            .appendPattern("MM/yy")
            .toFormatter();

    private int monthToExp;

    private ExpiringCard(Description description, CardNumber cardNumber,
                         Cvc cvc, ExpiryDate expiryDate, Set<Tag> tags, int monthToExp) {
        super(description, cardNumber, cvc, expiryDate, tags);
        this.monthToExp = monthToExp;
    }

    /**
     * Get extended ExpiringCard object from a {@code Card}
     */
    public static ExpiringCard of(Card card) {
        int monthsToExpiry = ExpiryUtil.getMonthToExp(card.getExpiryDate().toString());
        return new ExpiringCard(card.getDescription(), card.getCardNumber(), card.getCvc(),
                card.getExpiryDate(), card.getTags(), monthsToExpiry);
    }


    public Card getCard() {
        return this;
    }

    public int getMonthToExp() {
        return monthToExp;
    }
}
