package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.AcadYear;
import seedu.address.model.module.Lesson;
import seedu.address.model.module.LessonNo;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.NusModsShareLink;
import seedu.address.model.module.SemesterNo;
import seedu.address.model.module.Weeks;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.schedule.Event;
import seedu.address.model.person.schedule.Timeslot;
import seedu.address.model.person.schedule.Venue;
import seedu.address.websocket.NusModApi;

/**
 * Add an NUS module's timetable to a person's schedule.
 */
public class AddNusModsCommand extends Command {

    public static final String COMMAND_WORD = "addmods";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_NAME + "PERSON_NAME "
            + PREFIX_LINK + "NUSMODS_SHARE_LINK\n"
            + "Example Link: " + NusModsShareLink.EXAMPLE;

    public static final String MESSAGE_SUCCESS = "Added NUS modules to person's schedule: \n\n";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Unable to find person";
    public static final String MESSAGE_MODULE_NOT_FOUND = "Unable to get all module details";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-M-d");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmm");
    private static final DateTimeFormatter DT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-M-d HHmm");
    private static final int WEEK_LENGTH = 7;

    private final Name name;
    private final NusModsShareLink link;

    public AddNusModsCommand(Name name, NusModsShareLink link) {
        requireNonNull(name);
        requireNonNull(link);

        this.name = name;
        this.link = link;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        AcadYear acadYear = model.getAppSettings().getAcadYear();

        // find person with name
        ObservableList<Person> personList = model.getFilteredPersonList();
        Person person = null;
        for (Person p : personList) {
            if (p.getName().equals(name)) {
                person = p;
                break;
            }
        }
        if (person == null) {
            return new CommandResult(MESSAGE_PERSON_NOT_FOUND);
        }


        String startAcadSemDateString = model.getAcadSemesterStartDateString(acadYear, link.semesterNo);
        List<String> holidayDateStrings = model.getHolidayDateStrings();

        // translate module to event
        ArrayList<Event> eventsToAdd = new ArrayList<>();
        for (Map.Entry<ModuleCode, List<LessonNo>> entry : link.moduleLessonsMap.entrySet()) {
            ModuleCode moduleCode = entry.getKey();
            Module module = model.getModuleList().findModule(moduleCode);

            // call api if module not in internal storage and save it if found
            if (module == null) {
                NusModApi api = new NusModApi(acadYear);
                JSONObject obj = api.getModule(moduleCode);
                if (obj == null) {
                    return new CommandResult(MESSAGE_MODULE_NOT_FOUND);
                } else {
                    module = new Module(obj);
                    model.addModule(module);
                }
            }

            Event e = createEvent(module, startAcadSemDateString, link.semesterNo,
                    entry.getValue(), holidayDateStrings);
            eventsToAdd.add(e);
        }

        for (Event event : eventsToAdd) {
            person.addEvent(event);
        }

        return new CommandResult(MESSAGE_SUCCESS + person.getSchedule());
    }

    /**
     * Converts a {@code Module} to an {@code Event}.
     * @return an Event based on an NUS module
     */
    public Event createEvent(Module module, String startAcadSemDateString, SemesterNo semesterNo,
                             List<LessonNo> lessonNos, List<String> holidayDateStrings) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        for (LessonNo lessonNo : lessonNos) {
            lessons.addAll(module.getSemester(semesterNo).findLessons(lessonNo));
        }

        ArrayList<Timeslot> timeslots = new ArrayList<>();
        for (Lesson lesson : lessons) {
            timeslots.addAll(generateTimeslots(lesson, startAcadSemDateString, holidayDateStrings));
        }

        return new Event(module.getModuleCode().toString(), timeslots);
    }

    /**
     * Generate all timeslots for the lesson, taking into account of holidays.
     */
    public List<Timeslot> generateTimeslots(Lesson lesson, String startAcadSemDateString,
                                            List<String> holidayDateStrings) {
        // TODO: SLAP
        List<Timeslot> timeslots = new ArrayList<>();

        Venue venue = new Venue(lesson.getVenue().toString());

        List<LocalDate> holidayDates = holidayDateStrings.stream()
                .map(s -> LocalDate.parse(s, DATE_FORMATTER))
                .collect(Collectors.toList());

        Weeks weeks = lesson.getWeeks();

        if (weeks.getType() == 1) {
            String semStartDateStartTimeString = startAcadSemDateString + " " + lesson.getStartTime().toString();
            String semStartDateEndTimeString = startAcadSemDateString + " " + lesson.getEndTime().toString();
            LocalDateTime semStartDateStartTime = LocalDateTime.parse(
                    semStartDateStartTimeString, DT_FORMATTER);
            LocalDateTime semStartDateEndTime = LocalDateTime.parse(
                    semStartDateEndTimeString, DT_FORMATTER);

            for (int weekNo : weeks.getWeekNumbers()) {
                LocalDateTime timeslotStart = semStartDateStartTime.plusDays(WEEK_LENGTH * (weekNo - 1));
                LocalDateTime timeslotEnd = semStartDateEndTime.plusDays(WEEK_LENGTH * (weekNo - 1));

                boolean isHoliday = holidayDates.stream().anyMatch(d -> timeslotStart.toLocalDate().isEqual(d));
                if (isHoliday) {
                    continue;
                }

                Timeslot ts = new Timeslot(timeslotStart, timeslotEnd, venue);
                timeslots.add(ts);
            }
        } else if (weeks.getType() == 2) {
            String lessonStartDateStartTimeString = weeks.getStartDateString() + " " + lesson.getStartTime().toString();
            String lessonStartDateEndTimeString = weeks.getStartDateString() + " " + lesson.getEndTime().toString();
            LocalDateTime lessonStartDateStartTime = LocalDateTime.parse(
                    lessonStartDateStartTimeString, DT_FORMATTER);
            LocalDateTime lessonStartDateEndTime = LocalDateTime.parse(
                    lessonStartDateEndTimeString, DT_FORMATTER);

            for (int weekNo : weeks.getWeekNumbers()) {
                LocalDateTime timeslotStart = lessonStartDateStartTime.plusDays(WEEK_LENGTH * (weekNo - 1));
                LocalDateTime timeslotEnd = lessonStartDateEndTime.plusDays(WEEK_LENGTH * (weekNo - 1));

                boolean isHoliday = holidayDates.stream().anyMatch(d -> timeslotStart.toLocalDate().isEqual(d));
                if (isHoliday) {
                    continue;
                }

                Timeslot ts = new Timeslot(timeslotStart, timeslotEnd, venue);
                timeslots.add(ts);
            }
        } else {
            assert true : weeks.getType() == 3;
            LocalDate lessonStartDate = LocalDate.parse(weeks.getStartDateString(), DATE_FORMATTER);
            LocalDate lessonEndDate = LocalDate.parse(weeks.getEndDateString(), DATE_FORMATTER);
            LocalDate tempDate = lessonStartDate;
            int weekInterval = weeks.getWeekInterval();

            while (tempDate.isBefore(lessonEndDate) || tempDate.isEqual(lessonEndDate)) {
                LocalTime lessonStartTime = LocalTime.parse(lesson.getStartTime().toString(), TIME_FORMATTER);
                LocalTime lessonEndTime = LocalTime.parse(lesson.getEndTime().toString(), TIME_FORMATTER);

                LocalDateTime timeslotStart = LocalDateTime.of(tempDate, lessonStartTime);
                LocalDateTime timeslotEnd = LocalDateTime.of(tempDate, lessonEndTime);

                tempDate = tempDate.plusDays(WEEK_LENGTH * weekInterval);

                boolean isHoliday = holidayDates.stream().anyMatch(d -> timeslotStart.toLocalDate().isEqual(d));
                if (isHoliday) {
                    continue;
                }

                Timeslot ts = new Timeslot(timeslotStart, timeslotEnd, venue);
                timeslots.add(ts);
            }
        }

        return timeslots;
    }

    @Override
    public boolean equals(Command command) {
        return false;
    }
}
