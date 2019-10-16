package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.inventory.UniqueInventoryList;
import seedu.address.model.member.Member;
import seedu.address.model.member.UniqueMemberList;

import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.member.Member;
import seedu.address.model.member.UniqueMemberList;
import seedu.address.model.mapping.Mapping;
import seedu.address.model.mapping.UniqueMappingList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameTask comparison)
 */
public class ProjectDashboard implements ReadOnlyProjectDashboard {

    private final UniqueTaskList tasks;
    private final UniqueMemberList members;
    private final UniqueInventoryList inventories;
    private final UniqueMappingList mappings;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        tasks = new UniqueTaskList();
        members = new UniqueMemberList();
        inventories = new UniqueInventoryList();
        mappings = new UniqueMappingList();
    }

    public ProjectDashboard() {}

    /**
     * Creates an ProjectDashboard using the Persons in the {@code toBeCopied}
     */
    public ProjectDashboard(ReadOnlyProjectDashboard toBeCopied) {
        this();
        resetData(toBeCopied);
    }


    //// list overwrite operations

    /**
     * Replaces the contents of the task list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    public void setTasks(List<Task> tasks) {
        this.tasks.setTasks(tasks);
    }

    public void setMembers(List<Member> members) {
        this.members.setMembers(members);
    }

    public void setMappings(List<Mapping> mappings) {
        for (Mapping mapping : mappings) {
            this.mappings.add(mapping);
        }
    }

    /**
     * Resets the existing data of this {@code ProjectDashboard} with {@code newData}.
     * Replaces the contents of the inventory list with {@code inventories}.
     */
    public void setInventories(List<Inventory>inventories) {
        this.inventories.setInventories(inventories);
    }

    /**
     * Resets the existing data of this {@code ProjectDashboard} with {@code newData}.
     */
    public void resetData(ReadOnlyProjectDashboard newData) {
        requireNonNull(newData);

        setTasks(newData.getTaskList());
        setInventories(newData.getInventoryList());
        setMembers(newData.getMemberList());
        setMappings(newData.getMappingList());
    }


    //// task-level operations

    /**
     * Replaces the given task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the address book.
     * The task identity of {@code editedTask} must not be the same as another existing task in the address book.
     */
    public void setTask(Task target, Task editedTask) {
        requireNonNull(editedTask);

        tasks.setTask(target, editedTask);
    }

    /**
     * Adds a task to the address book.
     * The task must not already exist in the address book.
     */
    public void addTask(Task p) {
        tasks.add(p);
    }

    /**
     * Removes {@code key} from this {@code ProjectDashboard}.
     * {@code key} must exist in the address book.
     */
    public void removeTask(Task key) {
        tasks.remove(key);
    }

    /**
     * Returns true if a task with the same identity as {@code task} exists in the address book.
     */
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return tasks.contains(task);
    }

    //// inventory-level operations
    /**
     * Returns true if a inventory with the same identity as {@code inventory} exists in the dashboard.
     */
    public boolean hasInventory(Inventory inventory) {
        requireNonNull(inventory);
        return inventories.contains(inventory);
    }

    /**
     * Adds a inventory to the dashboard.
     * The inventory must not already exist in the dashboard.
     */
    public void addInventory(Inventory inventory) {
        inventories.add(inventory);
    }

    /**
     * Removes {@code key} from this {@code ProjectDashboard}.
     * {@code key} must exist in the dashboard.
     */
    public void removeInventory(Inventory target) {
        inventories.remove(target);
    }

    //// util methods

    public void addMapping(Mapping mapping) {
        mappings.add(mapping);
    }

    public void removeMapping(Mapping mapping) {
        mappings.remove(mapping);
    }

    public boolean hasMapping(Mapping mapping) {
        requireNonNull(mapping);
        return mappings.contains(mapping);
    }
    @Override
    public String toString() {
        return tasks.asUnmodifiableObservableList().size() + " tasks";
        // TODO: refine later
    }

    @Override
    public ObservableList<Task> getTaskList() {
        return tasks.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Inventory> getInventoryList() {
        return inventories.asUnmodifiableList();
    }

    @Override
    public ObservableList<Mapping> getMappingList() {
        return mappings.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProjectDashboard // instanceof handles nulls
                && tasks.equals(((ProjectDashboard) other).tasks));
    }

    /*@Override
    public int hashCode() {
        return tasks.hashCode();
    }*/

    //// list overwrite operations

    //// member-level operations

    /**
     * Returns true if a task with the same identity as {@code task} exists in the address book.
     */
    public boolean hasMember(Member member) {
        requireNonNull(member);
        return members.contains(member);
    }

    /**
     * Adds a task to the address book.
     * The task must not already exist in the address book.
     */
    public void addMember(Member member) {
        members.add(member);
    }

    /**
     * Replaces the given task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the address book.
     * The task identity of {@code editedTask} must not be the same as another existing task in the address book.
     */
    public void setMember(Member target, Member editedMember) {
        requireNonNull(editedMember);

        members.setMember(target, editedMember);
    }

    /**
     * Removes {@code key} from this {@code ProjectDashboard}.
     * {@code key} must exist in the address book.
     */
    public void removeMember(Member key) {
        members.remove(key);
    }

    //// util methods

    /*Override
    public String toString() {
        return members.asUnmodifiableObservableList().size() + " members";
        // TODO: refine later
    }*/

    @Override
    public ObservableList<Member> getMemberList() {
        return members.asUnmodifiableObservableList();
    }

    /*@Override
    public int hashCode() {
        return members.hashCode();
    }*/
}
