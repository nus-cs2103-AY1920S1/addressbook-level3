package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

import seedu.address.model.common.ReferenceId;
import seedu.address.model.queue.Room;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class RoomList {

    private final UniqueRoomList listOfRooms;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        listOfRooms = new UniqueRoomList();
    }

    public RoomList() {

    }

    public RoomList(RoomList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data of this {@code RoomList} with {@code newData}.
     */
    public void resetData(RoomList newData) {
        requireNonNull(newData);
        setRooms(newData.getRoomList());
    }

    /**
     * Adds a room.
     */
    public void addRoom(ReferenceId id) {
        listOfRooms.add(new Room(id));
    }

    public void addRoom(ReferenceId id, int index) {
        listOfRooms.add(new Room(id), index);
    }

    /**
     * Removes a room.
     */
    public void removeRoom(ReferenceId target) {
        listOfRooms.remove(new Room(target));
    }

    /**
     * Returns the reference id of the current patient
     */
    public ReferenceId getCurrentlyServed(int index) {
        return listOfRooms.get(index).getCurrentPatient().get();
    }

    /**
     * Removes the current patient
     */
    public void removeCurrentPatient(int index) {
        listOfRooms.get(index).removeCurrentPatient();
    }

    /**
     * Serves the next patient
     */
    public void serve(int index, ReferenceId id) {
        listOfRooms.get(index).serve(id);
    }

    /**
     * Replaces the data of {@code listOfRooms}
     */
    public void setRooms(List<Room> rooms) {
        this.listOfRooms.setPersons(listOfRooms);
    }

    public boolean hasRoom(ReferenceId doctorReferenceId) {
        return listOfRooms.contains(new Room(doctorReferenceId));
    }

    //// util methods

    @Override
    public String toString() {
        return listOfRooms.asUnmodifiableObservableList().size() + " rooms";
        // TODO: refine later
    }

    public ObservableList<Room> getRoomList() {
        return listOfRooms.asUnmodifiableObservableList();
    }

    @Override
    public int hashCode() {
        return listOfRooms.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RoomList // instanceof handles nulls
                && listOfRooms.equals(((RoomList) other).listOfRooms));
    }
}
