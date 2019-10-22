package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
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

    public static Person[] getSamplePersons() {
        return new Person[]{};
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    public static ReadOnlyQuestions getSampleQuestionList() {
        SavedQuestions savedQuestions = new SavedQuestions();
        OpenEndedQuestion oeq = new OpenEndedQuestion("Example question.", "Sample answer.");
        savedQuestions.addQuestion(oeq);
        return savedQuestions;
    }

    public static ReadOnlyQuizzes getSampleQuizList() {
        SavedQuizzes savedQuizzes = new SavedQuizzes();
        return savedQuizzes;
    }

    public static ReadOnlyStudentRecord getSampleStudents() {
        StudentRecord studentRecord = new StudentRecord();
        Student student = new Student(new seedu.address.model.student.Name("SampleStudent"));
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
