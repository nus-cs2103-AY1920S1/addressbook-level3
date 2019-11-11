package seedu.jarvis.model.cca;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.jarvis.model.cca.exceptions.DuplicateEquipmentException;
import seedu.jarvis.model.cca.exceptions.EquipmentNotFoundException;

/**
 * Represents a list of equipment that each Cca requires.
 */
public class EquipmentList {

    private ObservableList<Equipment> internalEquipmentList = FXCollections.observableArrayList();
    private final ObservableList<Equipment> internalUnmodifiableEquipmentList =
            FXCollections.unmodifiableObservableList(internalEquipmentList);

    /**
     * Default constructor to be added to a CCA when Jarvis is started.
     */
    public EquipmentList() {
    }

    public EquipmentList(ObservableList<Equipment> toBeCopied) {
        requireNonNull(toBeCopied);
        this.internalEquipmentList = toBeCopied;
    }

    /**
     * Returns the internal equipment list {@code internalEquipmentList} of the {@code EquipmentList} object.
     */
    public ObservableList<Equipment> getInternalEquipmentList() {
        return internalEquipmentList;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Equipment> asUnmodifiableObservableList() {
        return internalUnmodifiableEquipmentList;
    }

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Equipment toCheck) {
        requireNonNull(toCheck);
        return internalEquipmentList.stream().anyMatch(toCheck::isSameEquipment);
    }

    /**
     * Adds an equipment to the equipment list.
     *
     * @param equipment
     */
    public void addEquipment(Equipment equipment) {
        requireNonNull(equipment);
        if (internalEquipmentList.contains(equipment)) {
            throw new DuplicateEquipmentException();
        }

        internalEquipmentList.add(equipment);
    }

    /**
     * Updates the equipment name.
     *
     * @param toBeUpdatedEquipment
     * @param updatedEquipment
     */
    public void updateEquipment(Equipment toBeUpdatedEquipment, Equipment updatedEquipment) {
        requireAllNonNull(toBeUpdatedEquipment, updatedEquipment);
        int toBeUpdatedEquipmentIndex = internalEquipmentList.indexOf(toBeUpdatedEquipment);
        if (toBeUpdatedEquipmentIndex == -1) {
            throw new EquipmentNotFoundException();
        }

        internalEquipmentList.set(toBeUpdatedEquipmentIndex, updatedEquipment);
    }

    /**
     * Removes equipment from the equipment list.
     *
     * @param toBeRemovedEquipment
     */
    public void removeEquipment(Equipment toBeRemovedEquipment) {
        requireNonNull(toBeRemovedEquipment);
        if (!internalEquipmentList.contains(toBeRemovedEquipment)) {
            throw new EquipmentNotFoundException();
        }

        internalEquipmentList.remove(toBeRemovedEquipment);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EquipmentList // instanceof handles nulls
                && internalEquipmentList.equals(((EquipmentList) other).internalEquipmentList));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the equipment in the equipment list: ");
        for (Equipment eq : internalEquipmentList) {
            sb.append(eq);
            sb.append("\n");
        }
        return sb.toString();
    }

}
