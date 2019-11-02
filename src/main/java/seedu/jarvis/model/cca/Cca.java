package seedu.jarvis.model.cca;

import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.jarvis.model.cca.ccaprogress.CcaMilestone;
import seedu.jarvis.model.cca.ccaprogress.CcaMilestoneList;
import seedu.jarvis.model.cca.ccaprogress.CcaProgress;
import seedu.jarvis.model.cca.exceptions.CcaProgressAlreadySetException;
import seedu.jarvis.model.cca.exceptions.MaxProgressNotSetException;

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

    /**
     * Gets the current progress percentage of the Cca.
     */
    public double getCcaProgressPercentage() throws MaxProgressNotSetException {
        return ccaProgress.getCcaProgressPercentage();
    }

    /**
     * Gets the current {@code CcaMilestone}.
     */
    public CcaMilestone getCurrentCcaMilestone() {
        return ccaProgress.getCurrentCcaMilestone();
    }

    /**
     * Gets the backing equipment list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Equipment> getFilteredEquipmentList() {
        return equipmentList.asUnmodifiableObservableList();
    }

    /**
     * Gets {@code CcaCurrentProgress} as an int.
     */
    public int getCcaCurrentProgressAsInt() {
        return ccaProgress.getCcaCurrentProgressAsInt();
    }

    /**
     * Returns true if the cca progresslist is empty.
     *
     * @return true if the cca progresslist is empty.
     */
    public boolean ccaMilestoneListIsEmpty() {

        return ccaProgress.ccaMilestoneListIsEmpty();
    }

    /**
     * Adds a progresslist to the {@code CcaProgress}.
     */
    public void addProgress(CcaMilestoneList ccaMilestoneList) {
        if (!ccaMilestoneListIsEmpty()) {
            throw new CcaProgressAlreadySetException();
        }
        ccaProgress.setMilestones(ccaMilestoneList);
    }

    /**
     * Increments the progress of the {@code Cca} by 1 {@code Milestone}.
     */
    public void increaseProgress() {
        ccaProgress.increaseProgress();
    }

    /**
     * Checks if the CcaProgress is already set.
     */
    public boolean containsProgress() {
        return !ccaProgress.ccaMilestoneListIsEmpty();
    }

    /**
     * Checks if the CcaProgress is at max.
     */
    public boolean progressAtMaxIncrement() {
        return ccaProgress.progressAtMax();
    }

    /**
     * Removes the {@code toRemoveCcaMilestoneList} from this Cca.
     */
    public void removeCcaMilestoneList(CcaMilestoneList toRemoveCcaMilestoneList) {
        ccaProgress.setMilestones(new CcaMilestoneList());
    }

    /**
     * Returns true of ccaProgress is at the minimum level.
     */
    public boolean ccaProgressAtMinLevel() {
        return ccaProgress.progressAtMin();
    }

    /**
     * Decreases the progress of the {@code Cca}.
     */
    public void decreaseProgress() {
        ccaProgress.decreaseProgress();
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
                .append(". Type: ")
                .append(getCcaType());
        return builder.toString();
    }
}
