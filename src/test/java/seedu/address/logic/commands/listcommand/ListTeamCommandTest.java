package seedu.address.logic.commands.listcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.model.Model;
import seedu.address.model.entity.Team;
import seedu.address.stub.ModelManagerStub;
import seedu.address.testutil.TypicalTeams;

public class ListTeamCommandTest {

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
        for (Team p : TypicalTeams.getTypicalTeams()) {
            model.addTeam(p);
            expectedModel.addTeam(p);
        }
    }

    @Test
    public void execute_emptyTeamList_displayDefaultMessage() {
        Model emptyModel = new ModelManagerStub();
        // Collect console output of ListTeamCommand of emptyModel
        System.setOut(modelPrintStream);
        new ListTeamCommand().execute(emptyModel);
        String output = modelOut.toString();

        String expectedOutput = ListTeamCommand.MESSAGE_NO_TEAM + NEW_LINE;

        // Test and reset OutStream
        assertEquals(expectedOutput, output);
        modelOut.reset();
    }

    @Test
    public void execute_twoEqualModelsToBeListed_showsSameList() {
        // Collect console output of ListTeamCommand of model
        System.setOut(modelPrintStream);
        new ListTeamCommand().execute(model);
        String output1 = modelOut.toString();
        // Collect console output of ListTeamCommand of expectedModel
        System.setOut(expectedPrintStream);
        new ListTeamCommand().execute(expectedModel);
        String output2 = expectedOut.toString();
        // Test and reset OutStream
        assertEquals(output1, output2);
        modelOut.reset();
        expectedOut.reset();
    }

}
