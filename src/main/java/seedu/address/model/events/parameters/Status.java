package seedu.address.model.events.parameters;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Describes the status of an appointment
 */
public class Status {
    public static final String APPROVED_MESS = "Patient appointment was made";
    public static final String ACK_MESS = "Patient has arrived";
    public static final String MISSED_MESS = "Patient missed appointment, need to settle";
    public static final String SETTLE_MESS = "This missed appointment have been settled";
    public static final String CANCEL_MESS = "This appointment have been cancelled";
    public static final String MESSAGE_CONSTRAINTS = "A status can only be one of the following: "
            + Arrays.stream(AppointmentStatuses.values()).map(v -> v.toString()).collect(Collectors.joining(", "));

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
        if (o == this) {
            return true;
        } else if (o instanceof Status) {
            Status otherStatus = (Status) o;
            return otherStatus.getSta().equals(this.getSta());
        } else if (o instanceof AppointmentStatuses) {
            return getSta().equals((AppointmentStatuses) o);
        }

        return false;
    }

    /**
     * checks if the current status is ACKNOWLEDGED.
     *
     * @return true if status is ACKNOWLEDGED.
     */
    public boolean isAcked() {
        return status.equals(AppointmentStatuses.ACKNOWLEDGED);
    }

    public boolean isMissed() {
        return status.equals(AppointmentStatuses.MISSED);
    }

    public boolean isSettled() {
        return status.equals(AppointmentStatuses.SETTLED);
    }

    public boolean isApproved() {
        return status.equals(AppointmentStatuses.APPROVED);
    }

    @Override
    public String toString() {
        return this.status.toString();
    }
}
