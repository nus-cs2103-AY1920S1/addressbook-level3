//package seedu.address.model.events;
//
//import seedu.address.model.common.DoctorReferenceId;
//import seedu.address.model.common.PatientReferenceId;
//
//import static java.util.Objects.requireNonNull;
//import static seedu.address.commons.util.AppUtil.checkArgument;
//
///**
// * Represents Appointments for patients in the address book.
// */
//public class Appointment extends Event {
//
//    private static int APP_PERIOD = 20;
//    private Status appointmentStatus;
//
//    /**
//     * Every field must be present and not null.
//     *
//     * @param patientId
//     * @param doctorId
//     * @param timing
//     */
//    public Appointment(PatientReferenceId patientId, DoctorReferenceId doctorId, Timing timing) {
//        super(patientId, doctorId, timing);
//        this.appointmentStatus = Status.APPROVED;
//    }
//
//    /**
//     * Returns true if appointment is cancelled.
//     */
//    public boolean isCancelled() {
//        return appointmentStatus == Status.CANCELLED;
//    }
//
//}
