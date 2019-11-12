package seedu.address.model.card;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ExpiryUtil;
import seedu.address.model.tag.Tag;

/**
 * Represents a Card in the card book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Card {

    private final Description description;
    private final CardNumber cardNumber;
    private final Cvc cvc;
    private final ExpiryDate expiryDate;

    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Card(Description description, CardNumber cardNumber, Cvc cvc, ExpiryDate expiryDate, Set<Tag> tags) {
        requireAllNonNull(description, cardNumber, cvc, expiryDate, tags);
        this.description = description;
        this.cardNumber = cardNumber;
        this.cvc = cvc;
        this.expiryDate = expiryDate;
        this.tags.addAll(tags);
    }

    public Card(Description description) {
        requireAllNonNull(description);
        this.description = description;
        this.cardNumber = null;
        this.expiryDate = null;
        this.cvc = null;
    }

    public Description getDescription() {
        return description;
    }

    public CardNumber getCardNumber() {
        return cardNumber;
    }

    public Cvc getCvc() {
        return cvc;
    }

    public ExpiryDate getExpiryDate() {
        return expiryDate;
    }

    /**
     * Returns the non-encrypted CardNumber
     * @return non-encrypted CardNumber
     */
    public String getNonEncryptedCardNumber() {
        return cardNumber.getNonEncryptedCardNumber();
    }

    /**
     * Returns the non-encrypted CardNumber without dashes
     * @return non-encrypted CardNumber without dashes
     */
    public String getCardNumberWithoutDashes() {
        return cardNumber.getCardNumberWithoutDashes();
    }

    /**
     * Returns the non-encrypted CardNumber
     * @return non-encrypted CardNumber
     */
    public String getNonEncryptedCvc() {
        return cvc.getNonEncryptedCvc();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns the non-encrypted Card
     * returns non-encrypted Card
     */
    public String toNonAsterixString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" Card Number: ")
                .append(getNonEncryptedCardNumber())
                .append(" CVC: ")
                .append(getNonEncryptedCvc())
                .append(" Expiry: ")
                .append(getExpiryDate())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Returns true if the card has expired.
     * @return true if the card's expiry date is over.
     */
    public boolean hasExpired() {
        return ExpiryUtil.getMonthToExp(expiryDate.toString()) < 0;
    }

    /**
     * Returns true if the card is expiring.
     * @return true if the card's expiry date is reaching in less than 3 months.
     */
    public boolean isExpiring() {
        return !this.hasExpired() && ExpiryUtil.getMonthToExp(expiryDate.toString()) < 3;
    }

    /**
     * Returns true if both cards have the same identity and data fields.
     * This defines a stronger notion of equality between two cards.
     */
    public boolean isSameCard(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Card)) {
            return false;
        }

        Card otherCard = (Card) other;
        return otherCard.getNonEncryptedCardNumber().equals(getNonEncryptedCardNumber());
    }

    /**
     * Returns true if both cards of the same description have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two cards.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        return other != null
                && ((Card) other).getDescription().equals(getDescription());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, cardNumber, cvc, expiryDate, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" Card Number ")
                .append(getCardNumber())
                .append(" CVC: ")
                .append(getCvc())
                .append(" Expiry: ")
                .append(getExpiryDate())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
