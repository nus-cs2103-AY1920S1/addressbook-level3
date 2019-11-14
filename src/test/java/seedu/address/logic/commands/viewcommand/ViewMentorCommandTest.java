package seedu.address.logic.commands.viewcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalIds.ID_FIRST_MENTOR;
import static seedu.address.testutil.TypicalIds.ID_SECOND_MENTOR;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Mentor;
import seedu.address.stub.ModelManagerStub;
import seedu.address.testutil.MentorBuilder;

public class ViewMentorCommandTest {

    private static final String NEW_LINE = System.lineSeparator();

    private Model modelOneMentor;
    private Model expectedOneMentor;
    private Mentor mentorToView;
    private final ByteArrayOutputStream modelOut = new ByteArrayOutputStream();
    private final ByteArrayOutputStream expectedOut = new ByteArrayOutputStream();
    private final PrintStream modelPrintStream = new PrintStream(modelOut);
    private final PrintStream expectedPrintStream = new PrintStream(expectedOut);

    @BeforeEach
    public void setup() throws AlfredException {
        this.mentorToView = new MentorBuilder().build();
        modelOneMentor = new ModelManagerStub();
        modelOneMentor.addMentor(mentorToView);
        expectedOneMentor = new ModelManagerStub();
        expectedOneMentor.addMentor(mentorToView);
    }

    @Test
    public void execute_emptyModel_throwCommandException() {
        Model emptyModel = new ModelManagerStub();
        Executable viewMentorCommand = () ->
                new ViewMentorCommand(ID_FIRST_MENTOR).execute(emptyModel);
        assertThrows(
                CommandException.class,
                viewMentorCommand,
                ViewMentorCommand.MESSAGE_INVALID_MENTOR_DISPLAYED_INDEX
        );
    }

    @Test
    public void execute_invalidId_throwCommandException() {
        Executable viewMentorCommand = () ->
                new ViewMentorCommand(ID_SECOND_MENTOR).execute(modelOneMentor);
        assertThrows(
                CommandException.class,
                viewMentorCommand,
                ViewMentorCommand.MESSAGE_INVALID_MENTOR_DISPLAYED_INDEX
        );
    }

    @Test
    public void execute_validParameters_success() throws CommandException {
        // Collect console output of ViewMentorCommand of modelOneMentor
        System.setOut(modelPrintStream);
        new ViewMentorCommand(this.mentorToView.getId()).execute(modelOneMentor);
        String output = modelOut.toString();
        // Configure correct output
        String expectedOutput = new StringBuilder()
                .append(String.format("Viewing %s%s", this.mentorToView.getName(), NEW_LINE))
                .append(String.format("\t%s%s", this.mentorToView.toString(), NEW_LINE))
                .toString();
        // Test and reset OutputStream
        assertEquals(expectedOutput, output);
        modelOut.reset();
    }

    @Test
    public void execute_modelWithSameMentor_sameOutput() throws CommandException {
        // Collect console output of ViewMentorCommand of modelOneMentor
        System.setOut(modelPrintStream);
        new ViewMentorCommand(this.mentorToView.getId()).execute(modelOneMentor);
        String output1 = modelOut.toString();
        // Collect console output of ViewMentorCommand of expectedOneMentor
        System.setOut(expectedPrintStream);
        new ViewMentorCommand(this.mentorToView.getId()).execute(expectedOneMentor);
        String output2 = expectedOut.toString();
        // Test and reset OutputStreams
        assertEquals(output1, output2);
        modelOut.reset();
        expectedOut.reset();
    }

}
