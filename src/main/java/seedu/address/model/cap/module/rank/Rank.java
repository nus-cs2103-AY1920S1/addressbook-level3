package seedu.address.model.cap.module.rank;

import static java.util.Objects.requireNonNull;

import seedu.address.model.cap.module.DegreeClassification;

/**
 * Represents an degree classification.
 * Guarantees: immutable.
 */
public abstract class Rank {

    private final DegreeClassification degreeClassification;
    private String title;

    /**
     * Constructs a {@code Phone}.
     *
     * @param degreeClassification A valid phone number.
     */
    public Rank(DegreeClassification degreeClassification, String title) {
        requireNonNull(degreeClassification, title);
        this.degreeClassification = degreeClassification;
        this.title = title;
    }

    public abstract String getRankImageFilePath();

    public DegreeClassification getDegreeClassification() {
        return degreeClassification;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Rank // instanceof handles nulls
                && this.degreeClassification.equals(((Rank) other).getDegreeClassification())); // state check
    }

    @Override
    public String toString() {
        return degreeClassification.toString();
    }
}
