package seedu.address.model.events;

import static java.util.Objects.requireNonNull;

/**
 * Describes the status of an appointment
 */
public class Status {
    public static final String APPROVED_MESS = "patient appointment was made";
    public static final String ACK_MESS = "patient is arrived";
    public static final String MISSED_MESS = "patient missed appointment, need to settle";
    public static final String SETTLE_MESS = "this missed appointment have been settled";
    public static final String CANCEL_MESS = "this appointment have been cancelled";

    /**
     * enum AppointmentStatuses to have the different status for appointments.
     */
    public enum AppointmentStatuses {
        APPROVED,
        CANCELLED,
        ACKNOWLEDGED,
        MISSED,
        SETTLED
    }

    private final AppointmentStatuses status;

    public Status(String status) {
        requireNonNull(status);
        this.status = AppointmentStatuses.valueOf(status.trim().toUpperCase());
    }

    public Status(AppointmentStatuses status) {
        requireNonNull(status);
        this.status = status;
    }

    public Status() {
        this.status = AppointmentStatuses.APPROVED;
    }

    /**
     * checks if the string status is the valid one.
     *
     * @param test which the string status.
     * @return true if string status is the valid one.
     */
    public static boolean isValidStatus(String test) {
        String toMatch = test.trim().toUpperCase();
        for (AppointmentStatuses state : AppointmentStatuses.values()) {
            if (state.toString().equals(toMatch)) {
                return true;
            }
        }

        return false;
    }

    public String getStatusMess() {
        switch (status) {

        case APPROVED:
            return APPROVED_MESS;
        case ACKNOWLEDGED:
            return ACK_MESS;
        case MISSED:
            return MISSED_MESS;
        case SETTLED:
            return SETTLE_MESS;
        default:
            return "status is wrong";
        }
    }

    public AppointmentStatuses getSta() {
        return this.status;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Status;
    }

    /**
     * checks if the current status is ACKNOWLEDGED.
     *
     * @return true if status is ACKNOWLEDGED.
     */
    public boolean isAcked() {
        return status.equals(AppointmentStatuses.ACKNOWLEDGED);
    }

    @Override
    public String toString() {
        return this.status.toString();
    }
}
