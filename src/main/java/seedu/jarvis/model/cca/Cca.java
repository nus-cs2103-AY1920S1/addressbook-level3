package seedu.jarvis.model.cca;

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

    /**
     * Every field must be present and not null.
     */
    public Cca(CcaName name, CcaType ccaType, EquipmentList equipmentList) {
        requireAllNonNull(name, ccaType, equipmentList);
        this.name = name;
        this.ccaType = ccaType;
        this.equipmentList = equipmentList;
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
                && otherCca.getEquipmentList().equals(getEquipmentList());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, ccaType, equipmentList);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(getCcaType())
                .append(getEquipmentList());
        return builder.toString();
    }

}
