package seedu.jarvis.model.cca;

import seedu.jarvis.model.cca.ccaprogress.CcaProgress;
import seedu.jarvis.model.cca.ccaprogress.CcaProgressList;
import seedu.jarvis.model.cca.exceptions.CcaProgressAlreadySetException;

import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Cca in the Jarvis parser.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Cca {

    // Identity fields
    private final CcaName name;
    private final CcaType ccaType;

    // Data fields
    private final EquipmentList equipmentList;
    private final CcaProgress ccaProgress;

    /**
     * Every field must be present and not null.
     */
    public Cca(CcaName name, CcaType ccaType, EquipmentList equipmentList, CcaProgress ccaProgress) {
        requireAllNonNull(name, ccaType, equipmentList);
        this.name = name;
        this.ccaType = ccaType;
        this.equipmentList = equipmentList;
        this.ccaProgress = ccaProgress;
    }

    /**
     * Constructor to be used when Cca is first added.
     */
    public Cca(CcaName name, CcaType ccaType, EquipmentList equipmentList) {
        requireAllNonNull(name, ccaType, equipmentList);
        this.name = name;
        this.ccaType = ccaType;
        this.equipmentList = equipmentList;
        this.ccaProgress = new CcaProgress();
    }

    public CcaName getName() {
        return name;
    }

    public CcaType getCcaType() {
        return ccaType;
    }

    public EquipmentList getEquipmentList() {
        return equipmentList;
    }

    public CcaProgress getCcaProgress() {
        return ccaProgress;
    }

    public boolean ccaProgressListIsEmpty() {
        return ccaProgress.ccaProgressListIsEmpty();
    }

    public void addProgress(CcaProgressList ccaProgressList) {
        if (!ccaProgressListIsEmpty()) {
            throw new CcaProgressAlreadySetException();
        }
        ccaProgress.setMilestones(ccaProgressList);
    }

    /**
     * Returns true if both ccas of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two ccas.
     */
    public boolean isSameCca(Cca otherCca) {
        if (otherCca == this) {
            return true;
        }

        return otherCca != null
                && otherCca.getName().equals(getName())
                && otherCca.getCcaType().equals(getCcaType());
    }

    /**
     * Returns true if both ccas have the same identity and data fields.
     * This defines a stronger notion of equality between two ccas.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Cca)) {
            return false;
        }

        Cca otherCca = (Cca) other;
        return otherCca.getName().equals(getName())
                && otherCca.getCcaType().equals(getCcaType())
                && otherCca.getEquipmentList().equals(getEquipmentList())
                && otherCca.getCcaProgress().equals(getCcaProgress());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, ccaType, equipmentList, ccaProgress);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("-")
                .append(getCcaType())
                .append(". ")
                .append(getEquipmentList());
        return builder.toString();
    }

}
