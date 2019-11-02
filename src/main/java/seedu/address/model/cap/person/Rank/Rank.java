package seedu.address.model.cap.person.Rank;

import static java.util.Objects.*;

import seedu.address.model.cap.person.*;

/**
 * Represents an Academic year of a NUS student.
 * Guarantees: immutable.
 */
public abstract class Rank {

    private final DegreeClassification degreeClassification;
    private String title;
    double MINIMUM_CAP = 0.0;
    double MAXIMUM_CAP = 5.0;

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
