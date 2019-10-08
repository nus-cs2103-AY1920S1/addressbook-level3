package seedu.jarvis.testutil.cca;

import seedu.jarvis.model.cca.Cca;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalCcas {

    public static final Cca CANOEING = new CcaBuilder().withName("Canoeing")
            .build();

    public static final Cca GUITAR_ENSEMBLE = new CcaBuilder().withName("Guitar Ensemble")
            .build();

    private TypicalCcas() {} // prevents instantiation
}
