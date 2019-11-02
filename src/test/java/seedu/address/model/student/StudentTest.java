package seedu.address.model.student;

import org.junit.jupiter.api.Test;
import seedu.address.testutil.student.NameBuilder;
import seedu.address.testutil.student.StudentBuilder;
import seedu.address.testutil.student.TypicalStudents;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentTest {
    private static final String nameOne = "Delevin Teo";
    private static final String nameTwo = "Ralph Laur";
    private static final String nameThree = "Polo G";
    private static final String nameFour = "Madden Law";


    @Test
    public void sameName_consideredEquals() {
        assertTrue((new StudentBuilder().withName(new NameBuilder().withName(nameOne).build()).withMark(false).build()).equals(
                (new StudentBuilder().withName(new NameBuilder().withName(nameOne).build()).withMark(false).build())
        ));
        assertTrue((new StudentBuilder().withName(new NameBuilder().withName(nameTwo).build()).withMark(false).build()).equals(
                (new StudentBuilder().withName(new NameBuilder().withName(nameTwo).build()).withMark(false).build())
        ));
        assertTrue((new StudentBuilder().withName(new NameBuilder().withName(nameThree).build()).withMark(true).build()).equals(
                (new StudentBuilder().withName(new NameBuilder().withName(nameThree).build()).withMark(true).build())
        ));
        assertTrue((new StudentBuilder().withName(new NameBuilder().withName(nameFour).build()).withMark(true).build()).equals(
                (new StudentBuilder().withName(new NameBuilder().withName(nameFour).build()).withMark(false).build())
        ));
    }

    @Test
    public void differentNameSameTags_consideredUnique() {
        assertFalse((new StudentBuilder().withName(new NameBuilder().withName(nameOne).build()).withTags(TypicalStudents.tagSetOne)
                .withMark(false).build()).equals(
                (new StudentBuilder().withName(new NameBuilder().withName(nameTwo).build()).withTags(TypicalStudents.tagSetOne)
                        .withMark(false).build())
        ));
        assertFalse((new StudentBuilder().withName(new NameBuilder().withName(nameOne).build()).withTags(TypicalStudents.tagSetTwo)
                .withMark(false).build()).equals(
                (new StudentBuilder().withName(new NameBuilder().withName(nameTwo).build()).withTags(TypicalStudents.tagSetTwo)
                        .withMark(false).build())
        ));
        assertFalse((new StudentBuilder().withName(new NameBuilder().withName(nameThree).build()).withTags(TypicalStudents.tagSetThree)
                .withMark(false).build()).equals(
                (new StudentBuilder().withName(new NameBuilder().withName(nameFour).build()).withTags(TypicalStudents.tagSetThree)
                        .withMark(false).build())
        ));
        assertFalse((new StudentBuilder().withName(new NameBuilder().withName(nameTwo).build()).withTags(TypicalStudents.tagSetFour)
                .withMark(false).build()).equals(
                (new StudentBuilder().withName(new NameBuilder().withName(nameThree).build()).withTags(TypicalStudents.tagSetFour)
                        .withMark(false).build())
        ));
    }

    @Test
    public void differentNameSameMarkStatus_consideredUnique(){
        assertFalse((new StudentBuilder().withName(new NameBuilder().withName(nameOne).build()).withMark(false).build()).equals(
                (new StudentBuilder().withName(new NameBuilder().withName(nameTwo).build()).withMark(false).build())
        ));
        assertFalse((new StudentBuilder().withName(new NameBuilder().withName(nameTwo).build()).withMark(true).build()).equals(
                (new StudentBuilder().withName(new NameBuilder().withName(nameThree).build()).withMark(true).build())
        ));
        assertFalse((new StudentBuilder().withName(new NameBuilder().withName(nameThree).build()).withMark(false).build()).equals(
                (new StudentBuilder().withName(new NameBuilder().withName(nameFour).build()).withMark(false).build())
        ));
        assertFalse((new StudentBuilder().withName(new NameBuilder().withName(nameFour).build()).withMark(true).build()).equals(
                (new StudentBuilder().withName(new NameBuilder().withName(nameOne).build()).withMark(true).build())
        ));
    }


}



