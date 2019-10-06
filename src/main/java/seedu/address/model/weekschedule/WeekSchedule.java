package seedu.address.model.weekschedule;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.schedule.Event;
import seedu.address.model.person.schedule.Timeslot;
import seedu.address.model.person.schedule.Venue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.DAYS;

public class WeekSchedule {

    private final LocalTime STARTTIME = LocalTime.of(00,00);
    private final LocalTime ENDTIME = LocalTime.of(23,59);

    private String weekScheduleName;

    private ArrayList<DaySchedule> week;

    public WeekSchedule(String weekScheduleName, LocalDateTime now, ArrayList<Person> persons) {

        this.weekScheduleName = weekScheduleName;
        LocalDate currentDate = now.toLocalDate();
        week = new ArrayList<>();

        for(int day = 0; day < 7; day++) {
            week.add(new DaySchedule(currentDate));

            for(int p = 0; p < persons.size(); p ++) {
                Person currentPerson = persons.get(p);
                Name name = currentPerson.getName();

                ArrayList<Event> events = currentPerson.getSchedule().getEvents();
                for(int e = 0; e < events.size(); e++) {
                    Event currentEvent = events.get(e);
                    String eventName = currentEvent.getEventName();

                    ArrayList<Timeslot> timeslots = currentEvent.getTimeslots();
                    for(int t = 0; t < timeslots.size(); t++) {
                        Timeslot currentTimeslot = timeslots.get(t);

                        LocalDateTime currentStartTime = currentTimeslot.getStartTime();
                        LocalDateTime currentEndTime = currentTimeslot.getEndTime();
                        Venue currentVenue = currentTimeslot.getVenue();

                        LocalDate currentStartDate = currentStartTime.toLocalDate();
                        LocalDate currentEndDate = currentEndTime.toLocalDate();

                        int startDateDifference = (int) DAYS.between(currentDate, currentStartDate);
                        int endDateDifference = (int) DAYS.between(currentDate, currentEndDate);

                        if(startDateDifference == 0 && endDateDifference == 0) {
                            DayTimeslot timeslot = new DayTimeslot(
                                    name,
                                    eventName,
                                    currentStartTime.toLocalTime(),
                                    currentEndTime.toLocalTime(),
                                    currentVenue
                            );
                            week.get(day).addTimeslot(timeslot);
                        } else if (startDateDifference == 0 && endDateDifference > 0) {
                            DayTimeslot timeslot = new DayTimeslot(
                                    name,
                                    eventName,
                                    currentStartTime.toLocalTime(),
                                    ENDTIME,
                                    currentVenue
                            );
                            week.get(day).addTimeslot(timeslot);
                        } else if (startDateDifference < 0 && endDateDifference == 0) {
                            DayTimeslot timeslot = new DayTimeslot(
                                    name,
                                    eventName,
                                    STARTTIME,
                                    currentEndTime.toLocalTime(),
                                    currentVenue
                            );
                            week.get(day).addTimeslot(timeslot);
                        } else if (startDateDifference < 0 && endDateDifference > 0) {
                            DayTimeslot timeslot = new DayTimeslot(
                                    name,
                                    eventName,
                                    STARTTIME,
                                    ENDTIME,
                                    currentVenue
                            );
                            week.get(day).addTimeslot(timeslot);
                        }
                    }
                }
            }

            currentDate.plusDays(1);
        }

    }



}
