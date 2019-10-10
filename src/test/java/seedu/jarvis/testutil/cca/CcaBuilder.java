package seedu.jarvis.testutil.cca;

import seedu.jarvis.model.cca.Cca;
import seedu.jarvis.model.cca.CcaType;
import seedu.jarvis.model.cca.EquipmentList;
import seedu.jarvis.model.cca.Name;

/**
 * A utility class to help with building Person objects.
 */
public class CcaBuilder {

    public static final String DEFAULT_NAME = "Canoeing";
    public static final String DEFAULT_CCATYPE = "sport";

    private Name name;
    private CcaType ccaType;
    private EquipmentList equipmentList;

    public CcaBuilder() {
        name = new Name(DEFAULT_NAME);
        ccaType = new CcaType(DEFAULT_CCATYPE);
        equipmentList = new EquipmentList();
    }

    /**
     * Initializes the CcaBuilder with the data of {@code ccaToCopy}.
     */
    public CcaBuilder(Cca ccaToCopy) {
        name = ccaToCopy.getName();
        ccaType = ccaToCopy.getCcaType();
        equipmentList = ccaToCopy.getEquipmentList();
    }

    /**
     * Sets the {@code Name} of the {@code Cca} that we are building.
     */
    public CcaBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code CcaType} of the {@code Cca} that we are building.
     *
     * @param ccaType
     * @return
     */
    public CcaBuilder withType(String ccaType) {
        this.ccaType = new CcaType(ccaType);
        return this;
    }

    /**
     * Sets the {@code EquipmentList} of the {@code Cca} that we are building.
     *
     * @return
     */
    public CcaBuilder withEquipmentList() {
        this.equipmentList = new EquipmentList();
        return this;
    }

    public Cca build() {
        return new Cca(name, ccaType, equipmentList);
    }

}
