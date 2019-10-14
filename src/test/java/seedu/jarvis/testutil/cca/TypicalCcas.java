package seedu.jarvis.testutil.cca;

import static seedu.jarvis.testutil.cca.TypicalEquipmentList.CANOEING_EQUIPMENT_LIST;
import static seedu.jarvis.testutil.cca.TypicalEquipmentList.GUITAR_ENSEMBLE_EQUIPMENT_LIST;

import seedu.jarvis.model.cca.Cca;


/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalCcas {

    public static final Cca CANOEING = new CcaBuilder()
            .withName("Canoeing")
            .withType("sport")
            .withEquipmentList(CANOEING_EQUIPMENT_LIST)
            .build();

    public static final Cca GUITAR_ENSEMBLE = new CcaBuilder()
            .withName("Guitar Ensemble")
            .withType("performingArt")
            .withEquipmentList(GUITAR_ENSEMBLE_EQUIPMENT_LIST)
            .build();

    private TypicalCcas() {} // prevents instantiation
}
