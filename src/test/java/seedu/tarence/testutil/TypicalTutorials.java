package seedu.tarence.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.tarence.model.Application;
import seedu.tarence.model.tutorial.Tutorial;

/**
 * A utility class containing a list of {@code Tutorial} objects to be used in tests.
 */
public class TypicalTutorials {

    public static final Tutorial CS1020_LAB01 = new TutorialBuilder().withModCode("CS1020")
            .withTutName("Lab 1").build();
    public static final Tutorial CS2040_TUT02 = new TutorialBuilder().withModCode("CS2040")
            .withTutName("Tutorial 2").build();
    public static final Tutorial CS1101S_LAB04 = new TutorialBuilder().withModCode("CS1101S")
            .withTutName("Lab 4").build();
    public static final Tutorial CS1231_TUT10 = new TutorialBuilder().withModCode("CS1231")
            .withTutName("Tutorial 10").build();
    public static final Tutorial CS2100_LAB02 = new TutorialBuilder().withModCode("CS2100")
            .withTutName("Lab 2").build();
    public static final Tutorial CS2103_TUT14 = new TutorialBuilder().withModCode("CS2103")
            .withTutName("Tutorial 14").build();
    public static final Tutorial CS3230_LAB03 = new TutorialBuilder().withModCode("CS3230")
            .withTutName("Lab 3").build();

    private TypicalTutorials() {} // prevents instantiation

    /**
     * Returns an {@code Application} with all the typical tutorials.
     */
    public static Application getTypicalApplication() {
        Application ta = new Application();
        for (Tutorial tutorial : getTypicalTutorial()) {
            ta.addTutorial(tutorial);
        }
        return ta;
    }

    public static List<Tutorial> getTypicalTutorial() {
        return new ArrayList<>(Arrays.asList(CS1020_LAB01, CS1101S_LAB04, CS1231_TUT10, CS2040_TUT02,
                CS2100_LAB02, CS2103_TUT14, CS3230_LAB03));
    }
}
