package mams.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mams.logic.commands.CommandTestUtil;
import mams.model.Mams;
import mams.model.student.Student;


/**
 * A utility class containing a list of {@code Student} objects to be used in tests.
 */
public class TypicalStudents {

    public static final Student ALICE = new StudentBuilder().withName("Alice Pauline")
            .withMatricId("A0156273R").withPrevMods("CS2030, CS1231")
            .withCredits("20")
            .withTags("friends").build();
    public static final Student BENSON = new StudentBuilder().withName("Benson Meier")
            .withMatricId("A01527367W")
            .withPrevMods("CS2030, CS1231").withCredits("20")
            .withTags("owesMoney", "friends").build();
    public static final Student CARL = new StudentBuilder().withName("Carl Kurz").withCredits("20")
            .withPrevMods("CS2030, CS1231").withMatricId("A01527367W").build();
    public static final Student DANIEL = new StudentBuilder().withName("Daniel Meier").withCredits("20")
            .withPrevMods("CS2030, CS1231").withMatricId("A01527367W").withTags("friends").build();
    public static final Student ELLE = new StudentBuilder().withName("Elle Meyer").withCredits("20")
            .withPrevMods("CS2030, CS1231").withMatricId("A01527367W").build();
    public static final Student FIONA = new StudentBuilder().withName("Fiona Kunz").withCredits("20")
            .withPrevMods("CS2030, CS1231").withMatricId("A01527367W").build();
    public static final Student GEORGE = new StudentBuilder().withName("George Best").withCredits("20")
            .withPrevMods("CS2030, CS1231").withMatricId("A01884932E").build();

    // Manually added
    public static final Student HOON = new StudentBuilder().withName("Hoon Meier").withCredits("20")
            .withPrevMods("CS2030, CS1231").withMatricId("A01527367W").build();
    public static final Student IDA = new StudentBuilder().withName("Ida Mueller").withCredits("20")
            .withPrevMods("CS2030, CS1231").withMatricId("A012362987E").build();

    // Manually added - Student's details found in {@code CommandTestUtil}
    public static final Student AMY = new StudentBuilder()
            .withName(CommandTestUtil.VALID_NAME_AMY).withCredits(CommandTestUtil.VALID_CREDITS_AMY)
            .withPrevMods(CommandTestUtil.VALID_PREVMODS_AMY)
            .withMatricId(CommandTestUtil.VALID_MATRICID_AMY).withTags(CommandTestUtil.VALID_TAG_FRIEND).build();
    public static final Student BOB = new StudentBuilder()
            .withName(CommandTestUtil.VALID_NAME_BOB).withCredits(CommandTestUtil.VALID_CREDITS_BOB)
            .withPrevMods(CommandTestUtil.VALID_PREVMODS_BOB)
            .withMatricId(CommandTestUtil.VALID_MATRICID_BOB)
            .withTags(CommandTestUtil.VALID_TAG_HUSBAND, CommandTestUtil.VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalStudents() {} // prevents instantiation

    /**
     * Returns an {@code Mams} with all the typical students.
     */
    public static Mams getTypicalMams() {
        Mams ab = new Mams();
        for (Student student : getTypicalStudents()) {
            ab.addStudent(student);
        }
        return ab;
    }

    public static List<Student> getTypicalStudents() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

}
