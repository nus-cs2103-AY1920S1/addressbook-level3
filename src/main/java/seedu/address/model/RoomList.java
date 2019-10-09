package seedu.address.model;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.model.common.ReferenceId;
import seedu.address.model.person.Person;
import seedu.address.model.queue.Room;

import java.util.List;

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

    public RoomList() {}

    public RoomList(RoomList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    public void resetData(RoomList newData) {
        requireNonNull(newData);

        setRooms(newData.getRoomList());
    }

    public void addRoom(Room room) {
        listOfRooms.add(room);
    }

    public void removeRoom(int index) {
        listOfRooms.remove(index);
    }

    public ReferenceId getCurrentlyServed(int index) {
        return listOfRooms.get(index).getCurrentPatient().get();
    }

    public void serve(int index, ReferenceId id) {
        listOfRooms.get(index).serve(id);
    }

    public void setRooms(List<Room> rooms) {
        this.listOfRooms.setPersons(listOfRooms);
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
