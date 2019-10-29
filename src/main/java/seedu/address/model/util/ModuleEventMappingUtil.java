package seedu.address.model.util;

import static java.util.Objects.requireNonNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.exceptions.ModuleToEventMappingException;
import seedu.address.model.module.Exam;
import seedu.address.model.module.Holidays;
import seedu.address.model.module.Lesson;
import seedu.address.model.module.LessonNo;
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
    public static final String MESSAGE_MISSING_LESSONS = "missing class numbers in input!";
    public static final String MESSAGE_INVALID_LESSONS = "invalid class number in input!";
    private static final int WEEK_LENGTH = 7;


    /**
     * Converts a {@code Module} to an {@code Event}.
     * @return an Event based on an NUS module
     */
    public static Event mapModuleToEvent(Module module, LocalDate startAcadSemDate, SemesterNo semesterNo,
                  List<LessonNo> lessonNos, Holidays holidays) throws ModuleToEventMappingException {
        requireNonNull(module);
        requireNonNull(startAcadSemDate);
        requireNonNull(semesterNo);
        requireNonNull(lessonNos);
        requireNonNull(holidays);

        Semester semester = module.getSemester(semesterNo);
        ArrayList<Lesson> lessons = new ArrayList<>();
        ArrayList<Timeslot> timeslots = new ArrayList<>();

        if (lessonNos.isEmpty()) { //no lesson numbers given
            throw new ModuleToEventMappingException(MESSAGE_MISSING_LESSONS);
        }
        for (LessonNo lessonNo : lessonNos) {
            List<Lesson> lessonsFound = semester.findLessons(lessonNo);
            if (lessonsFound.isEmpty()) { //module does not have a matching lesson number
                throw new ModuleToEventMappingException(MESSAGE_INVALID_LESSONS);
            }
            lessons.addAll(lessonsFound);
        }

        // Add timeslots for lessons and exam
        for (Lesson lesson : lessons) {
            timeslots.addAll(generateLessonTimeslots(lesson, startAcadSemDate, holidays));
        }
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
     * @param lesson Lesson to use for generating Timeslots.
     * @param startAcadSemDate LocalDate representing start date of the academic semester.
     * @param holidays Holidays object containing holiday dates.
     * @return
     */
    private static List<Timeslot> generateLessonTimeslots(Lesson lesson, LocalDate startAcadSemDate,
                                                         Holidays holidays) {
        List<Timeslot> timeslots = new ArrayList<>();
        Venue venue = new Venue(lesson.getVenue().toString());
        List<LocalDate> holidayDates = holidays.getHolidayDates();
        Weeks weeks = lesson.getWeeks();
        DayOfWeek lessonDay = lesson.getDay();
        LocalTime lessonStartTime = lesson.getStartTime();
        LocalTime lessonEndTime = lesson.getEndTime();
        LocalDate firstLessonDate = weeks.getStartDate();
        LocalDateTime firstLessonStart;
        LocalDateTime firstLessonEnd;
        List<Integer> weekNumbers;

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
            //generate weekNumbers with weekInterval
            weekNumbers = new ArrayList<>();
            LocalDate tempDate = firstLessonDate.with(lessonDay);
            LocalDate lastLessonDate = weeks.getEndDate();
            int weekInterval = weeks.getWeekInterval();
            int curr = 1;
            while (!tempDate.isAfter(lastLessonDate)) {
                weekNumbers.add(curr);
                curr += weekInterval;
                tempDate = tempDate.plusDays(WEEK_LENGTH * weekInterval);
            }
        }
        //generate timeslots
        for (int weekNo : weekNumbers) {
            LocalDateTime timeslotStart = firstLessonStart.plusDays(WEEK_LENGTH * (weekNo - 1));
            LocalDateTime timeslotEnd = firstLessonEnd.plusDays(WEEK_LENGTH * (weekNo - 1));

            boolean isHoliday = holidayDates.stream().anyMatch(d -> timeslotStart.toLocalDate().isEqual(d));
            if (isHoliday) {
                continue;
            }

            Timeslot ts = new Timeslot(timeslotStart, timeslotEnd, venue);
            timeslots.add(ts);
        }

        return timeslots;
    }
}
