//@@author wongsm7
package seedu.address.model.queue;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;

import seedu.address.model.ReferenceId;
import seedu.address.model.common.UniqueElementList;
import seedu.address.model.person.UniqueReferenceIdList;

/**
 * Manages the queue and rooms.
 */
public class QueueManager {
    private UniqueReferenceIdList queueList;
    private UniqueElementList<Room> roomList;

    public QueueManager() {
        this.queueList = new UniqueReferenceIdList();
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

    public void addPatient(ReferenceId id) {
        queueList.add(id);
    }

    public void addPatient(int index, ReferenceId id) {
        queueList.add(index, id);
    }

    public void removePatient(ReferenceId id) {
        queueList.remove(id);
    }

    public void removePatient(int index) {
        queueList.remove(index);
    }

    public boolean hasIdInQueue(ReferenceId id) {
        return queueList.contains(id);
    }

    /**
     * Checks if a patient is being served
     *
     * @param id of the patient being served
     * @return boolean which indicates whether the patient is being served
     */
    public boolean hasIdInRooms(ReferenceId id) {
        for (Room room : roomList) {
            if (room.hasPatientBeingServed()) {
                ReferenceId patientId = room.getCurrentPatient().get();
                if (patientId.equals(id)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a doctor is on duty
     *
     * @param id of the doctor on duty
     * @return boolean which indicates whether the doctor is on duty
     */
    public boolean isDoctorOnDuty(ReferenceId id) {
        for (Room room : roomList) {
            if (room.getDoctor().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public void setPatientInQueue(ReferenceId target, ReferenceId editedId) {
        queueList.setPerson(target, editedId);
    }


    public void addRoom(Room room) {
        roomList.add(room);
    }

    public void removeRoom(Room target) {
        roomList.remove(target);
    }

    public boolean hasRoom(Room room) {
        return roomList.contains(room);
    }

    public ObservableList<ReferenceId> getReferenceIdList() {
        return queueList.asUnmodifiableObservableList();
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
