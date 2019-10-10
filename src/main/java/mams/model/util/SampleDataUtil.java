package mams.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import mams.model.Mams;
import mams.model.ReadOnlyMams;
import mams.model.student.Email;
import mams.model.student.MatricId;
import mams.model.student.Name;
import mams.model.student.Credits;
import mams.model.student.Student;

import mams.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Mams} with sample data.
 */
public class SampleDataUtil {
    public static Student[] getSampleStudents() {
        return new Student[] {
            new Student(new Name("Alex Yeoh"), new Credits("20"), new Email("alexyeoh@example.com"),
                new MatricId("A0180000R"),
                getTagSet("friends")),
            new Student(new Name("Bernice Yu"), new Credits("20"), new Email("berniceyu@example.com"),
                new MatricId("A01455353y"),
                getTagSet("colleagues", "friends")),
            new Student(new Name("Charlotte Oliveiro"), new Credits("20"), new Email("charlotte@example.com"),
                new MatricId("A0199239U"),
                getTagSet("neighbours")),
            new Student(new Name("David Li"), new Credits("20"), new Email("lidavid@example.com"),
                new MatricId("A01423223T"),
                getTagSet("family")),
            new Student(new Name("Irfan Ibrahim"), new Credits("20"), new Email("irfan@example.com"),
                new MatricId("A12039123S"),
                getTagSet("classmates")),
            new Student(new Name("Roy Balakrishnan"), new Credits("20"), new Email("royb@example.com"),
                new MatricId("A015923848U"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyMams getSampleMams() {
        Mams sampleAb = new Mams();
        for (Student sampleStudent : getSampleStudents()) {
            sampleAb.addStudent(sampleStudent);
        }
        return sampleAb;
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
