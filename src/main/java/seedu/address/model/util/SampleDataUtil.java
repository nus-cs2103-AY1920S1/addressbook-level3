package seedu.address.model.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.event.Event;
import seedu.address.model.event.EventRecord;
import seedu.address.model.event.ReadOnlyEvents;
import seedu.address.model.event.RecurrenceType;
import seedu.address.model.question.OpenEndedQuestion;
import seedu.address.model.question.ReadOnlyQuestions;
import seedu.address.model.question.SavedQuestions;
import seedu.address.model.quiz.ReadOnlyQuizzes;
import seedu.address.model.quiz.SavedQuizzes;
import seedu.address.model.student.ReadOnlyStudentRecord;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentRecord;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static ReadOnlyQuestions getSampleQuestionList() {
        SavedQuestions savedQuestions = new SavedQuestions();
        OpenEndedQuestion oeq = new OpenEndedQuestion("Example question.", "Sample answer.");
        savedQuestions.addQuestion(oeq);
        return savedQuestions;
    }

    public static ReadOnlyEvents getSampleEventsList() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(3);
        String eventName = "Sample Event";
        String colorCategory = "group01";
        String uniqueIdentifier = "njoyassistant";
        Event event = new Event(eventName, startTime, endTime, colorCategory, uniqueIdentifier, RecurrenceType.NONE);
        ArrayList<Event> eventList = new ArrayList<>();
        eventList.add(event);
        EventRecord sampleEventRecord = new EventRecord(eventList);
        return sampleEventRecord;
    }

    public static ReadOnlyQuizzes getSampleQuizList() {
        SavedQuizzes savedQuizzes = new SavedQuizzes();
        return savedQuizzes;
    }

    public static ReadOnlyStudentRecord getSampleStudents() {
        StudentRecord studentRecord = new StudentRecord();
        Set<Tag> studentTags = new HashSet<>();
        Student student = new Student(new seedu.address.model.student.Name("SampleStudent"), studentTags);
        studentRecord.addStudent(student);
        return studentRecord;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
