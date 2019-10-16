package seedu.address.logic.commands.viewcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalIds.ID_FIRST_PARTICIPANT;
import static seedu.address.testutil.TypicalIds.ID_SECOND_PARTICIPANT;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Participant;
import seedu.address.stub.ModelManagerStub;
import seedu.address.testutil.ParticipantBuilder;

public class ViewParticipantCommandTest {

    private static final String NEW_LINE = System.lineSeparator();

    private Model modelOneParticipant;
    private Model expectedOneParticipant;
    private Participant participantToView;
    private final ByteArrayOutputStream modelOut = new ByteArrayOutputStream();
    private final ByteArrayOutputStream expectedOut = new ByteArrayOutputStream();
    private final PrintStream modelPrintStream = new PrintStream(modelOut);
    private final PrintStream expectedPrintStream = new PrintStream(expectedOut);

    @BeforeEach
    public void setup() throws AlfredException {
        this.participantToView = new ParticipantBuilder().build();
        modelOneParticipant = new ModelManagerStub();
        modelOneParticipant.addParticipant(participantToView);
        expectedOneParticipant = new ModelManagerStub();
        expectedOneParticipant.addParticipant(participantToView);
    }

    @Test
    public void execute_emptyModel_throwCommandException() {
        Model emptyModel = new ModelManagerStub();
        Executable viewParticipantCommand = () ->
                new ViewParticipantCommand(ID_FIRST_PARTICIPANT).execute(emptyModel);
        assertThrows(
                CommandException.class,
                viewParticipantCommand,
                ViewParticipantCommand.MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX
        );
    }

    @Test
    public void execute_invalidId_throwCommandException() {
        Executable viewParticipantCommand = () ->
                new ViewParticipantCommand(ID_SECOND_PARTICIPANT).execute(modelOneParticipant);
        assertThrows(
                CommandException.class,
                viewParticipantCommand,
                ViewParticipantCommand.MESSAGE_INVALID_PARTICIPANT_DISPLAYED_INDEX
        );
    }

    @Test
    public void execute_validParameters_success() throws CommandException {
        // Collect console output of ViewParticipantCommand of modelOneParticipant
        System.setOut(modelPrintStream);
        new ViewParticipantCommand(this.participantToView.getId()).execute(modelOneParticipant);
        String output = modelOut.toString();
        // Configure correct output
        String expectedOutput = new StringBuilder()
                .append(String.format("Viewing %s%s", this.participantToView.getName(), NEW_LINE))
                .append(String.format("\t%s%s", this.participantToView.toString(), NEW_LINE))
                .toString();
        // Test and reset OutputStream
        assertEquals(expectedOutput, output);
        modelOut.reset();
    }

    @Test
    public void execute_modelWithSameParticipant_sameOutput() throws CommandException {
        // Collect console output of ViewParticipantCommand of modelOneParticipant
        System.setOut(modelPrintStream);
        new ViewParticipantCommand(this.participantToView.getId()).execute(modelOneParticipant);
        String output1 = modelOut.toString();
        // Collect console output of ViewParticipantCommand of expectedOneParticipant
        System.setOut(expectedPrintStream);
        new ViewParticipantCommand(this.participantToView.getId()).execute(expectedOneParticipant);
        String output2 = expectedOut.toString();
        // Test and reset OutputStreams
        assertEquals(output1, output2);
        modelOut.reset();
        expectedOut.reset();
    }

}
