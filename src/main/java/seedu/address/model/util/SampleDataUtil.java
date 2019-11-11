package seedu.address.model.util;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.Notebook;
import seedu.address.model.ReadOnlyNotebook;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentDeadline;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.classroom.Classroom;
import seedu.address.model.lesson.ClassName;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Time;
import seedu.address.model.student.Address;
import seedu.address.model.student.Email;
import seedu.address.model.student.MedicalCondition;
import seedu.address.model.student.Name;
import seedu.address.model.student.ParentPhone;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Classroom} with sample data.
 */
public class SampleDataUtil {

    public static Student[] getSampleStudents4E7() {
        return new Student[] {
            new Student(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@gmail.com"),
                    new ParentPhone("98103329"), new Address("Blk 30 Geylang Street 29, #06-40"),
                    new MedicalCondition("Sinus"), getTagSet("English REP")),
            new Student(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@gmail.com"),
                    new ParentPhone("88210392"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new MedicalCondition("Asthma"), getTagSet("Math REP")),
            new Student(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@gmail.com"),
                    new ParentPhone("91836192"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new MedicalCondition("Heart Attack"), getTagSet("Misbehaves a lot")),
            new Student(new Name("David Li"), new Phone("91031282"), new Email("lidavid@gmail.com"),
                    new ParentPhone("88839201"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new MedicalCondition("Pimples"), getTagSet("Likes to go toilet")),
            new Student(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@hotmail.com"),
                    new ParentPhone("98011257"), new Address("Blk 47 Tampines Street 20, #17-35"),
                    new MedicalCondition("NIL"), getTagSet("Cannot sit next to Alex")),
            new Student(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@msn.com"),
                    new ParentPhone("98710394"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new MedicalCondition("Arthritis"), getTagSet("Class Monitor"))
        };
    }

    public static Student[] getSampleStudents2E6() {
        return new Student[] {
            new Student(new Name("Alexandra Chin"), new Phone("93810385"), new Email("alexandrachin@hotmail.com"),
                    new ParentPhone("93185033"), new Address("Blk 39 Hougang Street 16, #06-20"),
                    new MedicalCondition("NIL"), getTagSet("PE REP")),
            new Student(new Name("Benjamin Xu"), new Phone("93260711"), new Email("benjaminxu@gmail.com"),
                    new ParentPhone("95821112"), new Address("Blk 16 Jurong West Gardens, #07-18"),
                    new MedicalCondition("Asthma"), getTagSet()),
            new Student(new Name("Crystal Tan"), new Phone("98802194"), new Email("crystal@gmail.com"),
                    new ParentPhone("93333333"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new MedicalCondition("Asthma"), getTagSet())
        };
    }

    public static Student[] getSampleStudents3E8() {
        return new Student[] {
            new Student(new Name("Cindy Chan"), new Phone("83019928"), new Email("cindychan@gmail.com"),
                    new ParentPhone("98019211"), new Address("Blk 14 Tampinese Street 69, #12-40"),
                    new MedicalCondition("NIL"), getTagSet("Math REP")),
            new Student(new Name("Dominic Ng"), new Phone("98128401"), new Email("domdom@hotmail.com"),
                    new ParentPhone("99112830"), new Address("Blk 14 Jurong West Avenue 3, #06-18"),
                    new MedicalCondition("Asthma"), getTagSet()),
            new Student(new Name("Genna Lim"), new Phone("91830511"), new Email("gennarox@gmail.com"),
                    new ParentPhone("91830511"), new Address("Blk 16 Yishun Street 14, #13-04"),
                    new MedicalCondition("Sinus"), getTagSet("Likes to eat her pencil")),
            new Student(new Name("Sandra Sim"), new Phone("91120009"), new Email("sandra@gmail.com"),
                    new ParentPhone("81125252"), new Address("Blk 801 Clementi Street 16, #02-46"),
                    new MedicalCondition("Allergic to nuts"), getTagSet("Likes to eat nuts")),
            new Student(new Name("Xavier Lim"), new Phone("91820112"), new Email("xavierdaman@gmail.com"),
                    new ParentPhone("98019304"), new Address("Blk 69 Jurong East Street 2, #14-65"),
                    new MedicalCondition("NIL"), getTagSet("Cannot sit next to Sandra"))
        };
    }

    public static Classroom[] getSampleClassrooms() {
        return new Classroom[] {
                getSampleClassroom4E7(), getSampleClassroom2E6(), getSampleClassroom3E8()
        };
    }

    public static Classroom getSampleClassroom4E7() {
        Classroom sampleClassroom = new Classroom();
        sampleClassroom.setClassroomName("4E7");
        for (Student sampleStudent : getSampleStudents4E7()) {
            sampleClassroom.addStudent(sampleStudent);
        }
        for (Assignment sampleAssignment : getSampleAssignments4E7()) {
            sampleClassroom.addAssignment(sampleAssignment);
        }
        return sampleClassroom;
    }

    public static Classroom getSampleClassroom2E6() {
        Classroom sampleClassroom = new Classroom();
        sampleClassroom.setClassroomName("2E6");
        for (Student sampleStudent : getSampleStudents2E6()) {
            sampleClassroom.addStudent(sampleStudent);
        }
        for (Assignment sampleAssignment : getSampleAssignments2E6()) {
            sampleClassroom.addAssignment(sampleAssignment);
        }
        return sampleClassroom;
    }

    public static Classroom getSampleClassroom3E8() {
        Classroom sampleClassroom = new Classroom();
        sampleClassroom.setClassroomName("3E8");
        for (Student sampleStudent : getSampleStudents3E8()) {
            sampleClassroom.addStudent(sampleStudent);
        }
        for (Assignment sampleAssignment : getSampleAssignments3E8()) {
            sampleClassroom.addAssignment(sampleAssignment);
        }
        return sampleClassroom;
    }

    public static Lesson[] getSampleLessons() {
        Calendar monStartCalendar = Calendar.getInstance();
        monStartCalendar.set(2020, 0, 6, 12, 0);
        Calendar monEndCalendar = Calendar.getInstance();
        monEndCalendar.set(2020, 0, 6, 13, 0);
        Calendar wedStartCalendar = Calendar.getInstance();
        wedStartCalendar.set(2020, 0, 8, 12, 0);
        Calendar wedEndCalendar = Calendar.getInstance();
        wedEndCalendar.set(2020, 0, 8, 13, 0);
        Calendar friStartCalendar = Calendar.getInstance();
        friStartCalendar.set(2020, 0, 10, 12, 0);
        Calendar friEndCalendar = Calendar.getInstance();
        friEndCalendar.set(2020, 0, 10, 13, 0);
        Calendar monStartCalendar2 = Calendar.getInstance();
        monStartCalendar2.set(2020, 0, 6, 15, 0);
        Calendar monEndCalendar2 = Calendar.getInstance();
        monEndCalendar2.set(2020, 0, 6, 17, 0);
        Calendar tueStartCalendar = Calendar.getInstance();
        tueStartCalendar.set(2020, 0, 7, 8, 0);
        Calendar tueEndCalendar = Calendar.getInstance();
        tueEndCalendar.set(2020, 0, 7, 9, 0);
        Calendar thuStartCalendar = Calendar.getInstance();
        thuStartCalendar.set(2020, 0, 9, 11, 0);
        Calendar thuEndCalendar = Calendar.getInstance();
        thuEndCalendar.set(2020, 0, 9, 12, 30);
        Calendar satStartCalendar = Calendar.getInstance();
        satStartCalendar.set(2020, 0, 11, 8, 0);
        Calendar satEndCalendar = Calendar.getInstance();
        satEndCalendar.set(2020, 0, 12, 20, 30);
        return new Lesson[] {
            new Lesson(new Time(monStartCalendar), new Time(monEndCalendar), new ClassName("4E7 Math")),
            new Lesson(new Time(wedStartCalendar), new Time(wedEndCalendar), new ClassName("2E6 English")),
            new Lesson(new Time(friStartCalendar), new Time(friEndCalendar), new ClassName("3E8 Math")),
            new Lesson(new Time(monStartCalendar2), new Time(monEndCalendar2), new ClassName("3E8 English")),
            new Lesson(new Time(tueStartCalendar), new Time(tueEndCalendar), new ClassName("2E6 Math")),
            new Lesson(new Time(thuStartCalendar), new Time(thuEndCalendar), new ClassName("4E7 English")),
            new Lesson(new Time(satStartCalendar), new Time(satEndCalendar), new ClassName("4E7 Camp"))
        };
    }

    public static ReadOnlyNotebook getSampleNotebook() {
        Notebook sampleNotebook = new Notebook();
        for (Classroom sampleClassroom : getSampleClassrooms()) {
            sampleNotebook.addClassroom(sampleClassroom);
        }
        for (Lesson sampleLesson : getSampleLessons()) {
            sampleNotebook.addLesson(sampleLesson);
        }
        return sampleNotebook;
    }

    public static Assignment[] getSampleAssignments4E7() {
        return new Assignment[] {
            new Assignment(new AssignmentName("Math Assignment 1"), new AssignmentDeadline("23/11/2019 1800")),
            new Assignment(new AssignmentName("English Assignment 3"), new AssignmentDeadline("29/11/2019 2359")),
            new Assignment(new AssignmentName("Math Tutorial 3"), new AssignmentDeadline("01/12/2019 1400")),
            new Assignment(new AssignmentName("English Essay 1"), new AssignmentDeadline("02/12/2019 2359"))
        };
    }

    public static Assignment[] getSampleAssignments2E6() {
        return new Assignment[] {
            new Assignment(new AssignmentName("Math Worksheet 2"), new AssignmentDeadline("23/11/2019 1800")),
            new Assignment(new AssignmentName("English Worksheet 1"), new AssignmentDeadline("18/11/2019 2359")),
            new Assignment(new AssignmentName("Math Assignment 1"), new AssignmentDeadline("19/11/2019 1400"))
        };
    }

    public static Assignment[] getSampleAssignments3E8() {
        return new Assignment[] {
            new Assignment(new AssignmentName("Math Assignment 3"), new AssignmentDeadline("23/11/2019 1900")),
            new Assignment(new AssignmentName("English Essay 4"), new AssignmentDeadline("29/11/2019 2359")),
            new Assignment(new AssignmentName("English Tutorial 2"), new AssignmentDeadline("19/11/2019 1400"))
        };
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
