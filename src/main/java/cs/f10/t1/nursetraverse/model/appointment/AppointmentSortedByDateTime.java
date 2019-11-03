package cs.f10.t1.nursetraverse.model.appointment;

import java.time.LocalDateTime;
import java.util.Comparator;

public class AppointmentSortedByDateTime implements Comparator<Appointment> {
    @Override
    public int compare(Appointment apptFirst, Appointment apptSecond) {

        LocalDateTime apptFirstDateTime = apptFirst.getStartDateTime().dateTime;
        LocalDateTime apptSecondDateTime = apptSecond.getStartDateTime().dateTime;

        if (apptFirstDateTime.isBefore(apptSecondDateTime)) {
            return -1;
        } else if (apptFirstDateTime.isAfter(apptSecondDateTime)) {
            return 1;
        } else {
            return 1;
        }
    }
}
