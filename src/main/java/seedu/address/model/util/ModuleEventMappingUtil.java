package seedu.address.model.util;

import static java.util.Objects.requireNonNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import seedu.address.logic.commands.exceptions.ModuleToEventMappingException;
import seedu.address.model.module.Exam;
import seedu.address.model.module.Holidays;
import seedu.address.model.module.Lesson;
import seedu.address.model.module.LessonNo;
import seedu.address.model.module.LessonType;
import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;
import seedu.address.model.module.SemesterNo;
import seedu.address.model.module.Weeks;
import seedu.address.model.module.WeeksType;
import seedu.address.model.person.schedule.Event;
import seedu.address.model.person.schedule.Timeslot;
import seedu.address.model.person.schedule.Venue;

/**
 * Add an an NUSMods timetable to a person's schedule.
 */
public class ModuleEventMappingUtil {
    public static final String MESSAGE_MISSING_LESSONS = "missing class numbers!";
    public static final String MESSAGE_INVALID_LESSONS = "invalid class number!";

    /**
     * Converts a {@code Module} to an {@code Event}.
     * @return an Event based on an NUS module
     */
    public static Event mapModuleToEvent(Module module, LocalDate startAcadSemDate, SemesterNo semesterNo,
             Map<LessonType, LessonNo> lessonTypesNosMap, Holidays holidays) throws ModuleToEventMappingException {
        requireNonNull(module);
        requireNonNull(startAcadSemDate);
        requireNonNull(semesterNo);
        requireNonNull(lessonTypesNosMap);
        requireNonNull(holidays);

        Semester semester = module.getSemester(semesterNo);
        ArrayList<Lesson> lessons = new ArrayList<>();
        ArrayList<Timeslot> timeslots = new ArrayList<>();

        if (lessonTypesNosMap.isEmpty()) { //no lesson types-numbers given
            throw new ModuleToEventMappingException(MESSAGE_MISSING_LESSONS);
        }

        // Get all lessons using the lesson types-numbers map.
        for (Map.Entry<LessonType, LessonNo> entry : lessonTypesNosMap.entrySet()) {
            LessonType lessonType = entry.getKey();
            LessonNo lessonNo = entry.getValue();
            List<Lesson> lessonsFound = semester.findLessons(lessonType, lessonNo);
            if (lessonsFound.isEmpty()) { // lessonType & lessonNo does not match any of the module's lessons.
                throw new ModuleToEventMappingException(MESSAGE_INVALID_LESSONS);
            }
            lessons.addAll(lessonsFound);
        }

        for (Lesson lesson : lessons) {
            timeslots.addAll(generateLessonTimeslots(lesson, startAcadSemDate, holidays));
        }

        // Add timeslot for exam if there is one.
        if (semester.getExam().isPresent()) {
            timeslots.add(generateExamTimeslot(semester.getExam().get()));
        }

        return new Event(module.getModuleCode().toString(), timeslots);
    }

    /**
     * Generate a {@code Timeslot} for Exam.
     * @param exam Exam to use for generating a {@code Timeslot}.
     * @return a {@code Timeslot} based on Exam.
     */
    public static Timeslot generateExamTimeslot(Exam exam) {
        LocalDateTime examDate = exam.getExamDate();
        int examDuration = exam.getExamDuration();
        Venue emptyVenue = new Venue(""); //empty cause exam venue is not captured in NUSMods
        return new Timeslot(examDate, examDate.plusMinutes(examDuration), emptyVenue);
    }

    /**
     * Generate all timeslots for the lesson, taking into account start date of semester and holidays.
     * @param lesson Lesson to use for generating timeslots.
     * @param startAcadSemDate LocalDate representing start date of the academic semester.
     * @param holidays Holidays object containing holiday dates.
     * @return list of timeslots generated for a lesson.
     */
    private static List<Timeslot> generateLessonTimeslots(Lesson lesson, LocalDate startAcadSemDate,
                                                         Holidays holidays) {
        Weeks weeks = lesson.getWeeks();
        DayOfWeek lessonDay = lesson.getDay();
        LocalTime lessonStartTime = lesson.getStartTime();
        LocalTime lessonEndTime = lesson.getEndTime();
        LocalDate firstLessonDate = weeks.getStartDate();
        LocalDateTime firstLessonStart;
        LocalDateTime firstLessonEnd;
        List<Integer> weekNumbers;

        // Get the corresponding firstLessonStart, firstLessonEnd and weekNumbers depending on WeeksType.
        if (weeks.getType() == WeeksType.WEEK_NUMBERS) {
            firstLessonStart = LocalDateTime.of(startAcadSemDate, lessonStartTime).with(lessonDay);
            firstLessonEnd = LocalDateTime.of(startAcadSemDate, lessonEndTime).with(lessonDay);
            weekNumbers = weeks.getWeekNumbers();
        } else if (weeks.getType() == WeeksType.START_END_WEEK_NUMBERS) {
            firstLessonStart = LocalDateTime.of(firstLessonDate, lessonStartTime).with(lessonDay);
            firstLessonEnd = LocalDateTime.of(firstLessonDate, lessonEndTime).with(lessonDay);
            weekNumbers = weeks.getWeekNumbers();
        } else {
            assert weeks.getType() == WeeksType.START_END_WEEK_INTERVAL;
            firstLessonStart = LocalDateTime.of(firstLessonDate, lessonStartTime).with(lessonDay);
            firstLessonEnd = LocalDateTime.of(firstLessonDate, lessonEndTime).with(lessonDay);
            weekNumbers = generateWeekNumbersFromWeekInterval(weeks, lessonDay, firstLessonDate);
        }

        Venue venue = new Venue(lesson.getVenue().toString());
        List<LocalDate> holidayDates = holidays.getHolidayDates();
        return createLessonTimeslots(venue, holidayDates, firstLessonStart, firstLessonEnd, weekNumbers);
    }

    /**
     * Create list of timeslots given the required information.
     * @param venue Venue to use for creating each timeslot.
     * @param holidayDates List of dates to filter out when creating timeslots.
     * @param firstLessonStart LocalDateTime of start of first lesson.
     * @param firstLessonEnd LocalDateTime of end of first lesson.
     * @param weekNumbers List of integers indicating week numbers.
     * @return List of lesson timeslots.
     */
    private static List<Timeslot> createLessonTimeslots(Venue venue, List<LocalDate> holidayDates,
            LocalDateTime firstLessonStart, LocalDateTime firstLessonEnd, List<Integer> weekNumbers) {
        List<Timeslot> timeslots = new ArrayList<>();
        for (int weekNo : weekNumbers) {
            LocalDateTime timeslotStart = firstLessonStart.plusWeeks(weekNo - 1);
            LocalDateTime timeslotEnd = firstLessonEnd.plusWeeks(weekNo - 1);

            // If weekNo > 6, +1 to account for recess week.
            if (weekNo > 6) {
                timeslotStart = timeslotStart.plusWeeks(1);
                timeslotEnd = timeslotEnd.plusWeeks(1);
            }

            // Don't generate timeslot on holidays.
            final LocalDateTime finalTimeslotStart = timeslotStart; // variable used in lambda should be final.
            boolean isHoliday = holidayDates.stream().anyMatch(d -> finalTimeslotStart.toLocalDate().isEqual(d));
            if (isHoliday) {
                continue;
            }

            Timeslot ts = new Timeslot(timeslotStart, timeslotEnd, venue);
            timeslots.add(ts);
        }
        return timeslots;
    }

    /**
     * Generates weekNumbers using weekInterval.
     * @param weeks Weeks object.
     * @param lessonDay DayOfWeek of lesson.
     * @param firstLessonDate Date of first lesson.
     * @return list of week numbers.
     */
    private static List<Integer> generateWeekNumbersFromWeekInterval(Weeks weeks, DayOfWeek lessonDay,
                                                                     LocalDate firstLessonDate) {
        List<Integer> weekNumbers = new ArrayList<>();
        LocalDate tempDate = firstLessonDate.with(lessonDay);
        LocalDate lastLessonDate = weeks.getEndDate();
        int weekInterval = weeks.getWeekInterval();
        int curr = 1;
        while (!tempDate.isAfter(lastLessonDate)) {
            weekNumbers.add(curr);
            curr += weekInterval;
            tempDate = tempDate.plusWeeks(weekInterval);
        }
        return weekNumbers;
    }
}
