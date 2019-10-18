package seedu.address.model;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;
import seedu.address.model.util.ObjectType;

/**
 * An {@code AddressBook} which maintains a stateful list of persons and policies.
 * Useful for undo/redo functions.
 */
public class StatefulAddressBook extends AddressBook {

    private final List<ObservableList<Person>> statefulPersonList;
    private final List<ObservableList<Policy>> statefulPolicyList;
    private final List<ObjectType> objectTypeList;
    private int currentPersonStatePointer;
    private int currentPolicyStatePointer;
    private int currentStatePointer;

    /**
     * @param addressBook Address book to initialise the stateful address book with
     */
    public StatefulAddressBook(ReadOnlyAddressBook addressBook) {
        super(addressBook);

        this.currentPersonStatePointer = 0;
        this.currentPolicyStatePointer = 0;
        this.currentStatePointer = 0;
        this.objectTypeList = new ArrayList<>();
        this.statefulPersonList = new ArrayList<>();
        this.statefulPolicyList = new ArrayList<>();
        statefulPersonList.add(addressBook.getPersonList());
        statefulPolicyList.add(addressBook.getPolicyList());
    }

    /**
     * Saves a copy of the current person list at the end of {@code statefulPersonList}.
     */
    public void commitPerson() {
        removePersonStateAfterCurrentPointer();
        statefulPersonList.add(getPersonList());
        objectTypeList.add(ObjectType.PERSON);
        currentPersonStatePointer++;
        currentStatePointer++;
    }

    /**
     * Saves a copy of the current policy list at the end of {@code statefulPolicyList}.
     */
    public void commitPolicy() {
        removePolicyStateAfterCurrentPointer();
        statefulPolicyList.add(getPolicyList());
        objectTypeList.add(ObjectType.POLICY);
        currentPolicyStatePointer++;
        currentStatePointer++;
    }

    // TODO: Add undo exception.
    /**
     * Restores our address book to a previous state, depending on whether a person or a policy was modified.
     */
    public void undo() {
        currentStatePointer--;
        if (objectTypeList.get(currentStatePointer) == ObjectType.PERSON) {
            currentPersonStatePointer--;
            setPersons(statefulPersonList.get(currentPersonStatePointer));
        } else if (objectTypeList.get(currentStatePointer) == ObjectType.PERSON) {
            currentPolicyStatePointer--;
            setPolicies(statefulPolicyList.get(currentPolicyStatePointer));
        }
    }

    // TODO: Add redo exception.
    /**
     * Restores our address book to its previously undone state, depending on whether a person or policy was undone.
     */
    public void redo() {
        currentStatePointer++;
        if (objectTypeList.get(currentStatePointer) == ObjectType.PERSON) {
            currentPersonStatePointer++;
            setPersons(statefulPersonList.get(currentPersonStatePointer));
        } else if (objectTypeList.get(currentStatePointer) == ObjectType.PERSON) {
            currentPolicyStatePointer++;
            setPolicies(statefulPolicyList.get(currentPolicyStatePointer));
        }
    }

    /**
     * Checks whether an undo is possible in the address book.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Checks whether a redo is possible in the address book.
     */
    public boolean canRedo() {
        return currentStatePointer < objectTypeList.size() - 1;
    }

    @Override
    public boolean equals(Object obj) {
        // if the same object
        if (obj == this) {
            return true;
        }

        // if different kinds of objects
        if (!(obj instanceof StatefulAddressBook)) {
            return false;
        }

        StatefulAddressBook other = (StatefulAddressBook) obj;
        return super.equals(other)
                && statefulPersonList.equals(other.statefulPersonList)
                && statefulPolicyList.equals(other.statefulPolicyList)
                && objectTypeList.equals(other.objectTypeList)
                && currentStatePointer == other.currentStatePointer
                && currentPolicyStatePointer == other.currentPolicyStatePointer
                && currentPersonStatePointer == other.currentPersonStatePointer;
    }

    private void removePersonStateAfterCurrentPointer() {
        statefulPersonList.subList(currentPersonStatePointer + 1, statefulPersonList.size()).clear();
        objectTypeList.subList(currentStatePointer + 1, objectTypeList.size()).clear();
    }

    private void removePolicyStateAfterCurrentPointer() {
        statefulPolicyList.subList(currentPolicyStatePointer + 1, statefulPolicyList.size()).clear();
        objectTypeList.subList(currentStatePointer + 1, objectTypeList.size()).clear();
    }

}
