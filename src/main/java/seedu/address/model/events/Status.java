package seedu.address.model.events;

//TODO: Stub models for now

/**
 * Describes the status of an appointment
 */
public class Status {
}

    WAITING("patient is waiting now"),
    MISSED("patient is waiting now"),
    ACK("patient is arrived"),
    SETTLE("patient is "),
    CANCELLED("patient appointment has been cancelled"),
    NEW("default status"),
    APPROVED("patient appointment was made");

    private String statusMess;

    Status(String status) {
        this.statusMess = status;
    }

    public String getStatusMess() {
        return statusMess;
    }
    }
