package seedu.system.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.system.model.Data;
import seedu.system.model.competition.Competition;

/**
 * A utility class containing a list of {@code Competition} objects to be used in tests.
 */
public class TypicalCompetitions {

    public static final Competition NUS_OPEN = new CompetitionBuilder().withName("NUS Powerlifting Open 2019")
        .withStartDate("08/05/2019").withEndDate("08/09/2019").build();

    private TypicalCompetitions() {} // prevents instantiation

    /**
     * Returns an {@code Data} with all the typical competitions.
     */
    public static Data<Competition> getTypicalCompetitionData() {
        Data competitions = new Data();
        for (Competition competition : getTypicalCompetitions()) {
            competitions.addUniqueElement(competition);
        }
        return competitions;
    }

    public static List<Competition> getTypicalCompetitions() {
        return new ArrayList<>(Arrays.asList(NUS_OPEN));
    }
}
