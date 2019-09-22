package seedu.address.testutil;

import java.util.ArrayList;

import seedu.address.model.module.Module;
import seedu.address.model.tutorial.Tutorial;


/**
 * A utility class containing a list of {@code Module} objects to be used in tests.
 */
public class TypicalModules {

    public static final String VALID_MODCODE_CS2103 = "CS2103";
    public static final String VALID_MODCODE_CS1101S = "CS1101S";

    public static final Module CS2103 = new ModuleBuilder().withModCode("CS2103")
            .withTutorials(new ArrayList<Tutorial>()).build();
    public static final Module CS1101S = new ModuleBuilder().withModCode("CS1101S")
            .withTutorials(new ArrayList<Tutorial>()).build();

    private TypicalModules() {} // prevents instantiation
}
