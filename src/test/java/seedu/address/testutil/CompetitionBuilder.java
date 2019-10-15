package seedu.address.testutil;

import java.util.Date;

import seedu.address.model.competition.Competition;
import seedu.address.model.person.Name;

/**
 * A utility class to help with building Competition objects.
 */
public class CompetitionBuilder {

    public static final String DEFAULT_NAME = "NUS Powerlifting Open";
    public static final Date DEFAULT_START_DATE = new Date();
    public static final Date DEFAULT_END_DATE = new Date();

    private Name name;
    private Date startDate;
    private Date endDate;

    public CompetitionBuilder() {
        name = new Name(DEFAULT_NAME);
        startDate = DEFAULT_START_DATE;
        endDate = DEFAULT_END_DATE;
    }

    /**
     * Initializes the CompetitionBuilder with the data of {@code competitionToCopy}.
     */
    public CompetitionBuilder(Competition competitionToCopy) {
        name = competitionToCopy.getName();
        startDate = competitionToCopy.getStartDate();
        endDate = competitionToCopy.getEndDate();
    }

    /**
     * Sets the {@code Name} of the {@code Competition} that we are building.
     */
    public CompetitionBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code StartDate} of the {@code Competition} that we are building.
     */
    public CompetitionBuilder withStartDate(String dateString) {
        this.startDate = new Date(dateString);
        return this;
    }

    /**
     * Sets the {@code EndDate} of the {@code Competition} that we are building.
     */
    public CompetitionBuilder withEndDate(String dateString) {
        this.endDate = new Date(dateString);
        return this;
    }

    public Competition build() {
        return new Competition(name, startDate, endDate);
    }

}

