package seedu.address.model.events;

/**
 * Describes the status of an appointment
 */
public enum Status {

    APPROVED("patient appointment was made"),
    ACK("patient is arrived"),
    WAITING("patient comes for appointment but is waiting now"),
    MISSED("patient missed appointment, need to settle"),
    SETTLE("patient does not come for appointment but is settled "),
    CANCELLED("patient appointment has been cancelled");

    private String statusMess;

    Status(String status) {
        this.statusMess = status;
    }

    public String getStatusMess() {
        return statusMess;
    }
}
