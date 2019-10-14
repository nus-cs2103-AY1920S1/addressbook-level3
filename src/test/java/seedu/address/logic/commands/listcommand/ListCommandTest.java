package seedu.address.logic.commands.listcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.model.Model;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Team;
import seedu.address.stub.ModelManagerStub;
import seedu.address.testutil.TypicalMentors;
import seedu.address.testutil.TypicalParticipants;
import seedu.address.testutil.TypicalTeams;

public class ListCommandTest {

    private static final String NEW_LINE = System.lineSeparator();

    private Model model;
    private Model expectedModel;
    private final ByteArrayOutputStream modelOut = new ByteArrayOutputStream();
    private final ByteArrayOutputStream expectedOut = new ByteArrayOutputStream();
    private final PrintStream modelPrintStream = new PrintStream(modelOut);
    private final PrintStream expectedPrintStream = new PrintStream(expectedOut);

    @BeforeEach
    public void setUp() throws AlfredException {
        model = new ModelManagerStub();
        expectedModel = new ModelManagerStub();
        for (Mentor m : TypicalMentors.getTypicalMentors()) {
            model.addMentor(m);
            expectedModel.addMentor(m);
        }
        for (Participant p : TypicalParticipants.getTypicalParticipants()) {
            model.addParticipant(p);
            expectedModel.addParticipant(p);
        }
        for (Team t : TypicalTeams.getTypicalTeams()) {
            model.addTeam(t);
            expectedModel.addTeam(t);
        }
    }

    @Test
    public void execute_emptyEntityLists_displayDefaultMessage() {
        Model emptyModel = new ModelManagerStub();
        // Collect console output of ListCommand of emptyModel
        System.setOut(modelPrintStream);
        new ListCommand().execute(emptyModel);
        String output = modelOut.toString();

        String expectedOutput = String.join(
                NEW_LINE,
                ListCommand.MESSAGE_NO_TEAM,
                ListCommand.MESSAGE_NO_MENTOR,
                ListCommand.MESSAGE_NO_PARTICIPANT,
                ""
        );

        // Test and reset OutStream
        assertEquals(expectedOutput, output);
        modelOut.reset();
    }

    @Test
    public void execute_defaultParameter_showsSameList() {
        // Collect console output of ListCommand of model
        System.setOut(modelPrintStream);
        new ListCommand().execute(model);
        String output1 = modelOut.toString();
        // Collect console output of ListCommand of expectedModel
        System.setOut(expectedPrintStream);
        new ListCommand().execute(expectedModel);
        String output2 = expectedOut.toString();
        // Test and reset OutStream
        assertEquals(output1, output2);
        modelOut.reset();
        expectedOut.reset();
    }

    @Test
    public void execute_shouldShowConnection_showsSameList() {
        // Collect console output of ListCommand of model
        System.setOut(modelPrintStream);
        new ListCommand(true).execute(model);
        String output1 = modelOut.toString();
        // Collect console output of ListCommand of expectedModel
        System.setOut(expectedPrintStream);
        new ListCommand(true).execute(expectedModel);
        String output2 = expectedOut.toString();
        // Test and reset OutStream
        assertEquals(output1, output2);
        modelOut.reset();
        expectedOut.reset();
    }

    @Test
    public void execute_shouldNotShowConnection_showsSameListAsDefaultParameter() {
        // Collect console output of ListCommand of model
        System.setOut(modelPrintStream);
        new ListCommand(false).execute(model);
        String output1 = modelOut.toString();
        // Collect console output of ListCommand of expectedModel
        System.setOut(expectedPrintStream);
        new ListCommand().execute(model);
        String output2 = expectedOut.toString();
        // Test and reset OutStream
        assertEquals(output1, output2);
        modelOut.reset();
        expectedOut.reset();
    }

}
