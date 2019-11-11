package seedu.jarvis.testutil.cca;

import seedu.jarvis.model.cca.Cca;
import seedu.jarvis.model.cca.CcaName;
import seedu.jarvis.model.cca.CcaType;
import seedu.jarvis.model.cca.Equipment;
import seedu.jarvis.model.cca.EquipmentList;
import seedu.jarvis.model.cca.ccaprogress.CcaProgress;

/**
 * A utility class to help with building Person objects.
 */
public class CcaBuilder {

    public static final String DEFAULT_NAME = "Canoeing";
    public static final String DEFAULT_CCATYPE = "sport";
    public static final String DEFAULT_EQUIPMENT = "paddle";

    private CcaName name;
    private CcaType ccaType;
    private EquipmentList equipmentList;
    private CcaProgress ccaProgress;

    public CcaBuilder() {
        name = new CcaName(DEFAULT_NAME);
        ccaType = new CcaType(DEFAULT_CCATYPE);
        equipmentList = new EquipmentList();
        equipmentList.addEquipment(new Equipment(DEFAULT_EQUIPMENT));
        ccaProgress = new CcaProgress();
    }

    /**
     * Initializes the CcaBuilder with the data of {@code ccaToCopy}.
     */
    public CcaBuilder(Cca ccaToCopy) {
        name = ccaToCopy.getName();
        ccaType = ccaToCopy.getCcaType();
        equipmentList = ccaToCopy.getEquipmentList();
        ccaProgress = ccaToCopy.getCcaProgress();
    }

    /**
     * Sets the {@code CcaName} of the {@code Cca} that we are building.
     */
    public CcaBuilder withName(String name) {
        this.name = new CcaName(name);
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
    public CcaBuilder withEquipmentList(EquipmentList equipmentList) {
        this.equipmentList = equipmentList;
        return this;
    }

    /**
     * Sets an empty {@code EquipmentList} for the {@code Cca} that we are building.
     *
     * @return a CcaBuilder object.
     */
    public CcaBuilder withEquipmentList() {
        this.equipmentList = new EquipmentList();
        return this;
    }

    /**
     * Sets an empty {@code CcaProgress} for the {@code Cca} that we are building.
     *
     * @return a CcaBuilder object.
     */
    public CcaBuilder withCcaProgress() {
        this.ccaProgress = new CcaProgress();
        return this;
    }

    /**
     * Sets the specified {@code CcaProgress} for the {@code Cca} that we are building.
     *
     * @return a CcaBuilder object.
     */
    public CcaBuilder withCcaProgress(CcaProgress ccaProgress) {
        this.ccaProgress = ccaProgress;
        return this;
    }

    public Cca build() {
        return new Cca(name, ccaType, equipmentList, ccaProgress);
    }

}
