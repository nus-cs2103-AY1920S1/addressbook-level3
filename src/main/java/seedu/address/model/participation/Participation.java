package seedu.address.model.participation;

import seedu.address.model.competition.Competition;
import seedu.address.model.person.Person;

/**
 * Represents a {@link Person}'s participation in a {@link Competition}.
 * Guarantees: immutable; person-competition pair is unique.
 */
public class Participation {
    private final Person person;
    private final Competition competition;

    public Participation(Person person, Competition competition) {
        this.person = person;
        this.competition = competition;
    }

    public Person getPerson() {
        return person;
    }

    public Competition getCompetition() {
        return competition;
    }

    /**
     * Returns true if both participations have the same identity and data fields.
     * This defines a stronger notion of equality between two participations.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Participation)) {
            return false;
        }

        Participation otherParticipation = (Participation) other;
        return otherParticipation.getPerson().equals(getPerson())
                && otherParticipation.getCompetition().equals(getCompetition());
    }
}
