package seedu.address.model.queue;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;

import seedu.address.model.ReferenceId;
import seedu.address.model.common.UniqueElementList;

/**
 * Manages the queue and rooms.
 */
public class QueueManager {
    private QueueList queueList;
    private UniqueElementList<Room> roomList;

    public QueueManager() {
        this.queueList = new QueueList();
        this.roomList = new UniqueElementList<>();
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
        roomList.setAll(newData.getRoomList());
    }


    /**
     * Serve the next patient in queue when a patient leaves a room
     *
     * @param index of the room which a patient left
     */
    public void serveNext(int index) {



        ReferenceId id = queueList.getFirst();
        queueList.poll();
        //roomList.serve(index, id);
    }

    /**
     * Enqueue back the patient which was allocated to a room
     *
     * @param index of the room which a patient was allocated
     */
    public void undoServeNext(int index) {
        //ReferenceId id = roomList.get(index).getCurrentPatient();
        //queueList.addPatient(0, id);
        //roomList.remove(index);
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

    public void addRoom(Room room) {
        roomList.add(room);
    }

    public boolean hasId(ReferenceId id) {
        return queueList.hasId(id);
    }

    public void removeRoom(Room target) {
        roomList.remove(target);
    }

    public boolean hasRoom(Room room) {
        return roomList.contains(room);
    }

    public ReferenceId getCurrentlyServed(int index) {
        return roomList.get(index).getCurrentPatient().get();
    }

    public void setRoom(Room target, Room editedRoom) {
        requireNonNull(editedRoom);

        roomList.set(target, editedRoom);
    }

    public ObservableList<ReferenceId> getReferenceIdList() {
        return queueList.getReferenceIdList();
    }

    public ObservableList<Room> getRoomList() {
        return roomList.asUnmodifiableObservableList();
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
