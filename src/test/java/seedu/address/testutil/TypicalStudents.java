package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MATRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MATRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;

import seedu.address.model.student.Student;

/**
 * A utility class containing a list of {@code Student} objects to be used in tests.
 */
public class TypicalStudents {

    public static final Student ALICE = new StudentBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com").withMatric("A0123456A")
            .build();
    public static final Student BENSON = new StudentBuilder().withName("Benson Meier")
            .withEmail("johnd@example.com").withMatric("A1234560A").build();
    public static final Student CARL = new StudentBuilder().withName("Carl Kurz")
            .withEmail("heinz@example.com").withMatric("A1234560A").build();
    public static final Student DANIEL = new StudentBuilder().withName("Daniel Meier")
            .withEmail("cornelia@example.com").withMatric("A2345601A").build();
    public static final Student ELLE = new StudentBuilder().withName("Elle Meyer")
            .withEmail("werner@example.com").withMatric("A3456012A").build();
    public static final Student FIONA = new StudentBuilder().withName("Fiona Kunz")
            .withEmail("lydia@example.com").withMatric("A4560123A").build();
    public static final Student GEORGE = new StudentBuilder().withName("George Best")
            .withEmail("anna@example.com").withMatric("A5601234A").build();

    // Manually added
    public static final Student HOON = new StudentBuilder().withName("Hoon Meier")
            .withEmail("stefan@example.com").withMatric("A5601234A").build();
    public static final Student IDA = new StudentBuilder().withName("Ida Mueller")
            .withEmail("hans@example.com").withMatric("A5601234A").build();

    // Manually added - Student's details found in {@code CommandTestUtil}
    public static final Student AMY = new StudentBuilder().withName(VALID_NAME_AMY).withEmail(VALID_EMAIL_AMY)
                .withMatric(VALID_MATRIC_AMY).build();
    public static final Student BOB = new StudentBuilder().withName(VALID_NAME_BOB).withEmail(VALID_EMAIL_BOB)
                .withMatric(VALID_MATRIC_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalStudents() {} // prevents instantiation
}
