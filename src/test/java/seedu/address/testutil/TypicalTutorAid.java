package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDANCE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDANCE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARTICIPATION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARTICIPATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PICTURE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PICTURE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESULT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RESULT_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TutorAid;
import seedu.address.model.commands.CommandObject;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} and {@code CommandObject} objects to be used in tests.
 */
public class TypicalTutorAid {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withPicture("alice.jpg").withClassId("Tutorial 7")
            .withAttendance("9").withResult("12").withParticipation("100").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withPicture("benson.jpg").withClassId("Tutorial 99")
            .withAttendance("19").withResult("13").withParticipation("10").build();

    public static final CommandObject ADDER = new CommandObjectBuilder().withCommandWord("adder")
            .withCommandAction("add").build();
    public static final CommandObject DELETER = new CommandObjectBuilder().withCommandWord("deleter")
            .withCommandAction("delete").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY)
            .withPicture(VALID_PICTURE_AMY)
            .withClassId(VALID_CLASSID_AMY)
            .withResult(VALID_RESULT_AMY)
            .withParticipation(VALID_PARTICIPATION_AMY)
            .withAttendance(VALID_ATTENDANCE_AMY)
            .build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB)
            .withPicture(VALID_PICTURE_BOB)
            .withClassId(VALID_CLASSID_BOB)
            .withResult(VALID_RESULT_BOB)
            .withParticipation(VALID_PARTICIPATION_BOB)
            .withAttendance(VALID_ATTENDANCE_BOB)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalTutorAid() {} // prevents instantiation


    /**
     * Returns an {@code TutorAid} with all the typical persons and CommandObjects
     */
    public static TutorAid getTypicalTutorAid() {
        TutorAid ta = new TutorAid();
        for (Person person : getTypicalPersons()) {
            ta.addPerson(person);
        }
        for (CommandObject command : getTypicalCommands()) {
            ta.addCommand(command);
        }
        return ta;
    }


    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON));
    }
    public static List<CommandObject> getTypicalCommands() {
        return new ArrayList<>(Arrays.asList(ADDER, DELETER));
    }
}
