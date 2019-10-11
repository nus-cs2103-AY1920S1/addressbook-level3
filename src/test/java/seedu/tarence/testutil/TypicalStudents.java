package seedu.tarence.testutil;

import static seedu.tarence.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_MATRIC_AMY;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_MODULE_AMY;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_MODULE_BOB;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_NUSNET_AMY;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_TUTORIAL_NAME_AMY;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_TUTORIAL_NAME_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.tarence.model.Application;
import seedu.tarence.model.student.Student;

/**
 * A utility class containing a list of {@code Student} objects to be used in tests.
 */
public class TypicalStudents {

    public static final Student ALICE = new StudentBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com").withMatricNum("A0123456X").withNusnetId("e0123456")
            .build();
    public static final Student BENSON = new StudentBuilder().withName("Benson Meier")
            .withEmail("johnd@example.com").withMatricNum("A1234560A").withNusnetId("e1234560")
            .build();
    public static final Student CARL = new StudentBuilder().withName("Carl Kurz")
            .withEmail("heinz@example.com").withMatricNum("A2345601A").withNusnetId("e2345601")
            .build();
    public static final Student DANIEL = new StudentBuilder().withName("Daniel Meier")
            .withEmail("cornelia@example.com").withMatricNum("A3456012A").withNusnetId("e3456012")
            .build();
    public static final Student ELLE = new StudentBuilder().withName("Elle Meyer")
            .withEmail("werner@example.com").withMatricNum("A4560123A").withNusnetId("e4560123")
            .build();
    public static final Student FIONA = new StudentBuilder().withName("Fiona Kunz")
            .withEmail("lydia@example.com").withMatricNum("A5601234A").withNusnetId("e5601234")
            .build();
    public static final Student GEORGE = new StudentBuilder().withName("George Best")
            .withEmail("anna@example.com").withMatricNum("A6012345A").withNusnetId("e6012345")
            .build();

    // Manually added
    public static final Student HOON = new StudentBuilder().withName("Hoon Meier")
            .withEmail("stefan@example.com").withMatricNum("A6543210A").withNusnetId("e6543210")
            .build();
    public static final Student IDA = new StudentBuilder().withName("Ida Mueller")
            .withEmail("hans@example.com").withMatricNum("A5432106A").withNusnetId("e5432106")
            .build();

    // Manually added - Student's details found in {@code CommandTestUtil}
    public static final Student AMY = new StudentBuilder().withName(VALID_NAME_AMY).withEmail(VALID_EMAIL_AMY)
                .withMatricNum(VALID_MATRIC_AMY).withNusnetId(VALID_NUSNET_AMY)
                .withModCode(VALID_MODULE_AMY).withTutName(VALID_TUTORIAL_NAME_AMY).build();
    public static final Student BOB = new StudentBuilder().withName(VALID_NAME_BOB).withEmail(VALID_EMAIL_BOB)
                .withModCode(VALID_MODULE_BOB).withTutName(VALID_TUTORIAL_NAME_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalStudents() {} // prevents instantiation

    /**
     * Returns an {@code Application} with all the typical students.
     */
    public static Application getTypicalApplication() {
        Application ab = new Application();
        for (Student student : getTypicalStudents()) {
            ab.addStudent(student);
        }
        return ab;
    }

    public static List<Student> getTypicalStudents() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
