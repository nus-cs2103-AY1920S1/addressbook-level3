package seedu.address.logic.commands.listcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.model.Model;
import seedu.address.model.entity.Participant;
import seedu.address.stub.ModelManagerStub;
import seedu.address.testutil.TypicalParticipants;

public class ListParticipantCommandTest {

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
        for (Participant p : TypicalParticipants.getTypicalParticipants()) {
            model.addParticipant(p);
            expectedModel.addParticipant(p);
        }
    }

    @Test
    public void execute_emptyParticipantList_displayDefaultMessage() {
        Model emptyModel = new ModelManagerStub();
        // Collect console output of ListParticipantCommand of emptyModel
        System.setOut(modelPrintStream);
        new ListParticipantCommand().execute(emptyModel);
        String output = modelOut.toString();

        String expectedOutput = ListParticipantCommand.MESSAGE_NO_PARTICIPANT + NEW_LINE;

        // Test and reset OutStream
        assertEquals(expectedOutput, output);
        modelOut.reset();
    }

    @Test
    public void execute_twoEqualModelsToBeListed_showsSameList() {
        // Collect console output of ListParticipantCommand of model
        System.setOut(modelPrintStream);
        new ListParticipantCommand().execute(model);
        String output1 = modelOut.toString();
        // Collect console output of ListParticipantCommand of expectedModel
        System.setOut(expectedPrintStream);
        new ListParticipantCommand().execute(expectedModel);
        String output2 = expectedOut.toString();
        // Test and reset OutStream
        assertEquals(output1, output2);
        modelOut.reset();
        expectedOut.reset();
    }

}
