package seedu.address.model.queue;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;

import seedu.address.model.QueueList;
import seedu.address.model.RoomList;
import seedu.address.model.common.ReferenceId;

/**
 * Manages the queue and rooms.
 */
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

    /**
     * Resets the existing data of this {@code QueueManager} with {@code newData}.
     */
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

    /**
     * Enqueue back the patient which was allocated to a room
     *
     * @param index of the room which a patient was allocated
     */
    public void undoServeNext(int index) {
        ReferenceId id = roomList.getCurrentlyServed(index);
        queueList.addPatient(0, id);
        roomList.removeCurrentPatient(index);
    }

    public void addPatient(ReferenceId id) {
        queueList.addPatient(id);
    }

    public void addPatient(int index, ReferenceId id) {
        queueList.addPatient(index, id);
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
        roomList.addRoom(id);
    }

    public void addRoomToIndex(ReferenceId doctorReferenceId, int indexOfRoom) {
        roomList.addRoom(doctorReferenceId, indexOfRoom);
    }

    public boolean hasId(ReferenceId id) {
        return queueList.hasId(id);
    }

    public void removeRoom(ReferenceId target) {
        roomList.removeRoom(target);
    }

    public boolean hasRoom(ReferenceId doctorReferenceId) {
        return roomList.hasRoom(doctorReferenceId);
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
