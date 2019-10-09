package seedu.address.model.queue;

import javafx.collections.ObservableList;
import seedu.address.model.QueueList;
import seedu.address.model.RoomList;
import seedu.address.model.common.ReferenceId;
import seedu.address.model.person.Person;
import seedu.address.model.queue.exceptions.QueueException;

import java.lang.ref.Reference;

import static java.util.Objects.requireNonNull;

public class QueueManager {
    private QueueList queueList;
    private RoomList roomList;

    public QueueManager() {
        this.queueList = new QueueList();
        this.roomList = new RoomList();
    }

    public QueueManager(QueueManager toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    public void resetData(QueueManager newData) {
        requireNonNull(newData);
        queueList.setIds(newData.getReferenceIdList());
        roomList.setRooms(newData.getRoomList());
    }



    /**
     * Serve the next patient in queue when a patient leaves a room
     *
     * @param index of the room which a patient left
     */
    public void serveNext(int index) {
        ReferenceId id = queueList.getFirst();
        queueList.poll();
        roomList.serve(index, id);
    }

    public void addPatient(ReferenceId id) {
        queueList.addPatient(id);
    }

    public void removePatient(ReferenceId id) {
        queueList.removePatient(id);
    }

    public void removePatient(int index) {
        queueList.removePatient(index);
    }

    public void poll() {
        queueList.removePatient(0);
    }

    public void addRoom(ReferenceId id) {
        if(id.equals(null)) {
            throw new QueueException("Invalid person");
        }
        roomList.addRoom(new Room(id));
    }

    public boolean hasId(ReferenceId id) {
        return queueList.hasId(id);
    }

    public void removeRoom(int index) {
        roomList.removeRoom(index);
    }

    public ReferenceId getCurrentlyServed(int index) {
        return roomList.getCurrentlyServed(index);
    }

    public ObservableList<ReferenceId> getReferenceIdList() {
        return queueList.getReferenceIdList();
    }

    public ObservableList<Room> getRoomList() {
        return roomList.getRoomList();
    }

    public int getSizeOfQueue() {
        return queueList.size();
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QueueManager // instanceof handles nulls
                && queueList.equals(((QueueManager) other).queueList)
                && roomList.equals(((QueueManager) other).roomList));
    }

}
