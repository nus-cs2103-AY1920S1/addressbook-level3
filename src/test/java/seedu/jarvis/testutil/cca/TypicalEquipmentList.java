package seedu.jarvis.testutil.cca;

import static seedu.jarvis.testutil.cca.TypicalEquipments.GUITAR;
import static seedu.jarvis.testutil.cca.TypicalEquipments.PADDLE;

import seedu.jarvis.model.cca.EquipmentList;

/**
 * A utility class containing a list of {@code EquipmentList} objects to be used in tests.
 */
public class TypicalEquipmentList {

    public static final EquipmentList CANOEING_EQUIPMENT_LIST = buildCanoeingEquipmentList();
    public static final EquipmentList GUITAR_ENSEMBLE_EQUIPMENT_LIST = buildGuitarEnsembleEquipmentList();

    private TypicalEquipmentList() {}; //prevents instantiation

    /**
     * Builds and returns a typical{@code EquipmentList} for the canoeing {@code Cca}.
     */
    public static EquipmentList buildCanoeingEquipmentList() {
        EquipmentList canoeingEquipmentList = new EquipmentList();
        canoeingEquipmentList.addEquipment(PADDLE);
        return canoeingEquipmentList;
    }

    /**
     * Builds and returns a typical{@code EquipmentList} for the guitar ensemble {@code Cca}.
     */
    public static EquipmentList buildGuitarEnsembleEquipmentList() {
        EquipmentList guitarEquipmentList = new EquipmentList();
        guitarEquipmentList.addEquipment(GUITAR);
        return guitarEquipmentList;
    }
}
