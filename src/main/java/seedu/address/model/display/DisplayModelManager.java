package seedu.address.model.display;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.GmapsModelManager;
import seedu.address.model.TimeBook;
import seedu.address.model.display.locationdata.ClosestCommonLocationData;
import seedu.address.model.display.schedulewindow.FreeSchedule;
import seedu.address.model.display.schedulewindow.FreeTimeslot;
import seedu.address.model.display.schedulewindow.PersonSchedule;
import seedu.address.model.display.schedulewindow.PersonTimeslot;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplay;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;
import seedu.address.model.display.schedulewindow.WeekSchedule;
import seedu.address.model.display.sidepanel.GroupDisplay;
import seedu.address.model.display.sidepanel.PersonDisplay;
import seedu.address.model.display.sidepanel.SidePanelDisplay;
import seedu.address.model.display.sidepanel.SidePanelDisplayType;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.mapping.Role;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.User;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.schedule.Event;
import seedu.address.model.person.schedule.Schedule;
import seedu.address.model.person.schedule.Timeslot;
import seedu.address.model.person.schedule.Venue;
import seedu.address.ui.util.ColorGenerator;

/**
 * Handler for all display models.
 */
public class DisplayModelManager {

    private static final int DAYS_OF_A_WEEK = 7;
    private static final int WEEKS_OF_A_MONTH = 4;

    private static final int FREE_TIMESLOT_TRHESHOLD = 15;
    private static final LocalTime SCHEDULE_START_TIME = LocalTime.of(8, 0);
    private static final LocalTime SCHEDULE_END_TIME = LocalTime.of(20, 0);

    private GmapsModelManager gmapsModelManager;
    private ScheduleWindowDisplay scheduleWindowDisplay;
    private SidePanelDisplay sidePanelDisplay;

    public DisplayModelManager(GmapsModelManager gmapsModelManager) {
        this.gmapsModelManager = gmapsModelManager;
    }

    /**
     * Returns the current state of the schedule's display.
     */
    public ScheduleWindowDisplayType getState() {
        return scheduleWindowDisplay.getScheduleWindowDisplayType();
    }

    /**
     * Updates with a schedule of a person specified by name.
     */
    public void updateDisplayWithPerson(Name name, LocalDateTime time,
                                        ScheduleWindowDisplayType type,
                                        TimeBook timeBook) {

        try {
            Person person = timeBook.getPersonList().findPerson(name);
            updateScheduleWindowDisplay(person, time, type);
        } catch (PersonNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates with a schedule of the user.
     */
    public void updateDisplayWithUser(LocalDateTime time, ScheduleWindowDisplayType type, TimeBook timeBook) {
        User user = timeBook.getPersonList().getUser();
        updateScheduleWindowDisplay(user, time, type);
    }

    /**
     * Update with a schedule of a group.
     */
    public void updateDisplayWithGroup(GroupName groupName,
                                       LocalDateTime now,
                                       ScheduleWindowDisplayType type,
                                       TimeBook timeBook) {

        try {
            Group group = timeBook.getGroupList().findGroup(groupName);
            GroupDisplay groupDisplay = new GroupDisplay(group);

            ArrayList<PersonId> personIds = timeBook
                    .getPersonToGroupMappingList()
                    .findPersonsOfGroup(group.getGroupId());

            ArrayList<Person> persons = new ArrayList<>();

            persons.add(timeBook.getPersonList().getUser());

            for (PersonId personId : personIds) {
                persons.add(timeBook.getPersonList().findPerson(personId));
            }

            updateScheduleWindowDisplay(persons, groupDisplay, now, type);

        } catch (GroupNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update with a schedule of an ArrayList of Persons.
     */
    public void updateDisplayWithPersons(ArrayList<Person> persons,
                                         LocalDateTime now,
                                         ScheduleWindowDisplayType type,
                                         TimeBook timeBook) {

        GroupDisplay groupDisplay = new GroupDisplay(persons);
        updateScheduleWindowDisplay(persons, groupDisplay, now, type);

    }

    /**
     * Updates the detail window display.
     */
    public void updateScheduleWindowDisplay(ScheduleWindowDisplay scheduleWindowDisplay) {
        this.scheduleWindowDisplay = scheduleWindowDisplay;
    }

    /**
     * Update to a Group schedule.
     */
    private void updateScheduleWindowDisplay(ArrayList<Person> persons,
                                             GroupDisplay groupDisplay,
                                             LocalDateTime now,
                                             ScheduleWindowDisplayType type) {

        ArrayList<FreeSchedule> freeScheduleForMonth = new ArrayList<>();
        ArrayList<PersonSchedule> personSchedules = new ArrayList<>();

        //Add all schedules.
        for (int i = 0; i < persons.size(); i++) {
            Person person = persons.get(i);
            Role role = Role.emptyRole();

            if (!type.equals(ScheduleWindowDisplayType.GROUP)) {
                return;
            }

            PersonSchedule personSchedule = generatePersonSchedule(
                    now,
                    person,
                    role,
                    ColorGenerator.generateColor(i),
                    true);
            personSchedules.add(personSchedule);
        }

        for (int h = 0; h < 4; h++) {
            final int finalH = h;
            FreeSchedule freeSchedule = generateFreeSchedule(personSchedules
                    .stream().map(sch -> sch.getScheduleDisplay().get(finalH))
                    .collect(Collectors.toCollection(ArrayList::new)), now);
            freeScheduleForMonth.add(freeSchedule);
        }

        ScheduleWindowDisplay scheduleWindowDisplay =
                new ScheduleWindowDisplay(personSchedules, freeScheduleForMonth, groupDisplay, type);
        updateScheduleWindowDisplay(scheduleWindowDisplay);
    }

    /**
     * Update to a Person schedule.
     */
    private void updateScheduleWindowDisplay(Person person, LocalDateTime time, ScheduleWindowDisplayType type) {
        ArrayList<PersonSchedule> personSchedules = new ArrayList<>();

        assert (type.equals(ScheduleWindowDisplayType.PERSON)
                || type.equals(ScheduleWindowDisplayType.HOME));

        PersonSchedule personSchedule = generatePersonSchedule(
                time,
                person,
                Role.emptyRole(),
                null,
                false);

        personSchedules.add(personSchedule);

        ScheduleWindowDisplay scheduleWindowDisplay = new ScheduleWindowDisplay(personSchedules, type);
        updateScheduleWindowDisplay(scheduleWindowDisplay);
    }

    /**
     * Getter method to retrieve detail window display.
     */
    public ScheduleWindowDisplay getScheduleWindowDisplay() {
        return scheduleWindowDisplay;
    }

    /**
     * Getter method to retrieve side panel display.
     */
    public SidePanelDisplay getSidePanelDisplay() {
        return sidePanelDisplay;
    }

    /**
     * Generates the PersonSchedule of a Person.
     */
    private PersonSchedule generatePersonSchedule(LocalDateTime now,
                                                  Person person,
                                                  Role role,
                                                  String color,
                                                  boolean isInGroup) {

        ArrayList<WeekSchedule> scheduleDisplay = new ArrayList<>();
        for (int week = 0; week < WEEKS_OF_A_MONTH; week++) {
            scheduleDisplay.add(generateWeekSchedule(now.plusDays(week * DAYS_OF_A_WEEK), person, color, isInGroup));
        }
        return new PersonSchedule(new PersonDisplay(person, role), scheduleDisplay);
    }

    /**
     * Generates the WeekSchedule of a Person.
     */
    private WeekSchedule generateWeekSchedule(LocalDateTime date, Person person, String color, boolean isInGroup) {

        HashMap<DayOfWeek, ArrayList<PersonTimeslot>> scheduleDisplay = new HashMap<>();

        Schedule personSchedule = person.getSchedule();
        ArrayList<Event> events = personSchedule.getEvents();

        for (int day = 1; day <= DAYS_OF_A_WEEK; day++) {
            scheduleDisplay.put(DayOfWeek.of(day), new ArrayList<>());
        }

        for (int e = 0; e < events.size(); e++) {
            Event currentEvent = events.get(e);
            String eventName = currentEvent.getEventName();

            String selectedColor;
            if (isInGroup) {
                selectedColor = color;
            } else {
                selectedColor = ColorGenerator.generateColor(e);

            }

            ArrayList<Timeslot> timeslots = currentEvent.getTimeslots();
            for (int t = 0; t < timeslots.size(); t++) {
                Timeslot currentTimeslot = timeslots.get(t);

                LocalDateTime currentStartTime = currentTimeslot.getStartTime();
                LocalDateTime currentEndTime = currentTimeslot.getEndTime();
                Venue currentVenue = currentTimeslot.getVenue();

                //Checks to see if the currentStartTime is within the upcoming 7 days.
                if (date.toLocalDate().plusDays(DAYS_OF_A_WEEK).isAfter(currentStartTime.toLocalDate())
                        && date.toLocalDate().minusDays(1).isBefore(currentStartTime.toLocalDate())
                        && (SCHEDULE_START_TIME.isBefore(currentStartTime.toLocalTime())
                        || SCHEDULE_START_TIME.compareTo(currentStartTime.toLocalTime()) == 0)
                        && SCHEDULE_END_TIME.isAfter(currentStartTime.toLocalTime())) {

                    PersonTimeslot timeslot = new PersonTimeslot(
                            eventName,
                            currentStartTime.toLocalDate(),
                            currentStartTime.toLocalTime(),
                            currentEndTime.toLocalTime()
                                    .isAfter(SCHEDULE_END_TIME) ? SCHEDULE_END_TIME : currentEndTime.toLocalTime(),
                            currentVenue,
                            selectedColor,
                            isInGroup,
                            gmapsModelManager.closestLocationData(
                                    new ArrayList<>(List.of(currentVenue.getVenue())))
                    );

                    scheduleDisplay.get(currentStartTime.getDayOfWeek()).add(timeslot);
                }
            }
        }

        for (int i = 1; i < DAYS_OF_A_WEEK; i++) {
            scheduleDisplay.get(DayOfWeek.of(i)).sort(
                    Comparator.comparing(PersonTimeslot::getStartTime));
        }


        return new WeekSchedule(scheduleDisplay);
    }

    /**
     * Generates a free schedule from a list of person schedules.
     */
    private FreeSchedule generateFreeSchedule(ArrayList<WeekSchedule> personSchedules,
                                              LocalDateTime now) {

        HashMap<DayOfWeek, ArrayList<FreeTimeslot>> freeSchedule = new HashMap<>();

        // Used to allocate an ID for each FreeTimeslot
        int freeTimeslotIdCounter = 1;

        int currentDay = now.getDayOfWeek().getValue();
        for (int i = currentDay; i <= DAYS_OF_A_WEEK + currentDay - 1; i++) {

            int day = ((i - 1) % 7) + 1;
            freeSchedule.put(DayOfWeek.of(day), new ArrayList<>());

            LocalTime currentTime = SCHEDULE_START_TIME;
            ArrayList<String> lastVenues = new ArrayList<>();

            // initialize last venues to null for each person
            for (int j = 0; j < personSchedules.size(); j++) {
                lastVenues.add(null);
            }

            boolean isClash;
            LocalTime newFreeTimeslotStartTime = null;

            while (true) {

                isClash = false;

                ArrayList<String> currentLastVenues = new ArrayList<>(lastVenues);

                // loop through each person
                for (int j = 0; j < personSchedules.size(); j++) {
                    ArrayList<PersonTimeslot> timeslots = personSchedules.get(j).get(DayOfWeek.of(day));

                    // loop through each timeslot
                    for (int k = 0; k < timeslots.size(); k++) {

                        // record the latest venue for each clash
                        if (timeslots.get(k).isClash(currentTime)) {
                            isClash = true;
                            currentLastVenues.set(j, timeslots.get(k).getVenue().toString());
                            break;
                        }
                    }
                }

                if (!isClash) {
                    if (newFreeTimeslotStartTime == null) {
                        newFreeTimeslotStartTime = currentTime;
                    }
                    lastVenues = new ArrayList<>(currentLastVenues);

                } else {
                    if (newFreeTimeslotStartTime != null) {

                        if (newFreeTimeslotStartTime.until(currentTime, MINUTES) >= FREE_TIMESLOT_TRHESHOLD) {
                            freeSchedule.get(DayOfWeek.of(day))
                                    .add(generateFreeTimeslot(
                                            freeTimeslotIdCounter,
                                            lastVenues,
                                            newFreeTimeslotStartTime,
                                            currentTime));

                            freeTimeslotIdCounter++;
                        }
                        newFreeTimeslotStartTime = null;
                    }
                    lastVenues = new ArrayList<>(currentLastVenues);
                }

                if (currentTime.equals(SCHEDULE_END_TIME)) {
                    if (!isClash) {
                        if (newFreeTimeslotStartTime.until(currentTime, MINUTES) >= FREE_TIMESLOT_TRHESHOLD) {
                            freeSchedule.get(DayOfWeek.of(day))
                                    .add(generateFreeTimeslot(
                                            freeTimeslotIdCounter,
                                            lastVenues,
                                            newFreeTimeslotStartTime,
                                            currentTime));
                            freeTimeslotIdCounter++;
                        }
                    }
                    break;
                }
                currentTime = currentTime.plusMinutes(1);
            }
        }
        return new FreeSchedule(freeSchedule);
    }

    /**
     * Helper method to generate a FreeTimeslot.
     */
    private FreeTimeslot generateFreeTimeslot(int id,
                                              ArrayList<String> lastVenues,
                                              LocalTime startTime,
                                              LocalTime endTime) {

        ArrayList<String> freeTimeslotLastVenues = new ArrayList<>();
        for (String string : lastVenues) {
            if (string != null) {
                freeTimeslotLastVenues.add(string);
            }
        }

        ClosestCommonLocationData closestCommonLocationData =
                gmapsModelManager.closestLocationData(freeTimeslotLastVenues);

        return new FreeTimeslot(
                id,
                freeTimeslotLastVenues,
                closestCommonLocationData,
                startTime,
                endTime);
    }

    /**
     * Updates the side panel display.
     *
     * @param sidePanelDisplay to be updated
     */
    public void updateSidePanelDisplay(SidePanelDisplay sidePanelDisplay) {
        this.sidePanelDisplay = sidePanelDisplay;
    }

    /**
     * Updates the side panel display of type.
     *
     * @param type     of side panel display to be updated
     * @param timeBook data
     */
    public void updateSidePanelDisplay(SidePanelDisplayType type, TimeBook timeBook) {

        SidePanelDisplay sidePanelDisplay;

        ArrayList<PersonDisplay> displayPersons = new ArrayList<>();
        ArrayList<GroupDisplay> displayGroups = new ArrayList<>();

        ArrayList<Person> persons = timeBook.getPersonList().getPersons();
        ArrayList<Group> groups = timeBook.getGroupList().getGroups();

        for (int i = 0; i < persons.size(); i++) {
            displayPersons.add(new PersonDisplay(persons.get(i)));
        }

        for (int i = 0; i < groups.size(); i++) {
            displayGroups.add(new GroupDisplay(groups.get(i)));
        }

        sidePanelDisplay = new SidePanelDisplay(displayPersons, displayGroups, type);
        updateSidePanelDisplay(sidePanelDisplay);
    }

}
