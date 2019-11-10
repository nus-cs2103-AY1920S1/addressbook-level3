package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ENTITY_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SIGNATURE_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.GenReportCommand.MESSAGE_GENREPORT_SUCCESS;
import static seedu.address.testutil.TypicalBodies.ALICE;
import static seedu.address.testutil.TypicalBodies.BOB;
import static seedu.address.testutil.TypicalIdentificationNumbers.FIRST_BODY_ID_NUM;
import static seedu.address.testutil.TypicalIdentificationNumbers.SECOND_BODY_ID_NUM;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.body.Body;

public class GenReportCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

    @Test
    public void execute_validBodyId_success() {
        List<Body> bodyList = model.getFilteredBodyList();
        for (Body body : bodyList) {
            if (body.getIdNum().equals(FIRST_BODY_ID_NUM)) {
                GenReportCommand genReportCommand =
                        new GenReportCommand(Index.fromZeroBased(FIRST_BODY_ID_NUM.getIdNum()), "Manager A");

                String expectedBodyMessage = String.format(MESSAGE_GENREPORT_SUCCESS, body);

                assertCommandSuccess(genReportCommand, model, expectedBodyMessage, expectedModel);
                break;
            }
        }
    }

    @Test
    public void execute_invalidBodyId_throwsCommandException() {
        IdentificationNumber outOfBoundBodyIndex = IdentificationNumber.customGenerateId("B",
                model.getFilteredBodyList().size() + 1);
        GenReportCommand genReportCommand =
                new GenReportCommand(Index.fromZeroBased(outOfBoundBodyIndex.getIdNum()), "Manager A");

        assertCommandFailure(genReportCommand, model, MESSAGE_INVALID_ENTITY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_signatureContainsSpecialChars_throwsCommandException() {
        List<Body> bodyList = model.getFilteredBodyList();
        for (Body body : bodyList) {
            if (body.getIdNum().equals(FIRST_BODY_ID_NUM)) {
                GenReportCommand genReportCommand =
                        new GenReportCommand(Index.fromZeroBased(FIRST_BODY_ID_NUM.getIdNum()), "AB%");

                assertCommandFailure(genReportCommand, model, MESSAGE_INVALID_SIGNATURE_FORMAT);
                break;
            }
        }
    }

    @Test
    public void execute_signatureContainsNumbers_throwsCommandException() {
        List<Body> bodyList = model.getFilteredBodyList();
        for (Body body : bodyList) {
            if (body.getIdNum().equals(FIRST_BODY_ID_NUM)) {
                GenReportCommand genReportCommand =
                        new GenReportCommand(Index.fromZeroBased(FIRST_BODY_ID_NUM.getIdNum()), "AB3");

                assertCommandFailure(genReportCommand, model, MESSAGE_INVALID_SIGNATURE_FORMAT);
                break;
            }
        }
    }
    
    @Test
    public void equals() {
        model.addEntity(ALICE);
        model.addEntity(BOB);
        GenReportCommand genFirstBodyReportCommand =
                new GenReportCommand(Index.fromZeroBased(FIRST_BODY_ID_NUM.getIdNum()), "Manager A");
        GenReportCommand genSecondBodyReportCommand =
                new GenReportCommand(Index.fromZeroBased(SECOND_BODY_ID_NUM.getIdNum()), "Manager A");

        // same object -> returns true
        assertEquals(genFirstBodyReportCommand, genFirstBodyReportCommand);

        // same values -> returns true
        GenReportCommand genFirstBodyCommandCopy =
                new GenReportCommand(Index.fromZeroBased(FIRST_BODY_ID_NUM.getIdNum()), "Manager A");
        assertEquals(genFirstBodyReportCommand, genFirstBodyCommandCopy);

        // different types -> returns false
        assertNotEquals(1, genFirstBodyReportCommand);

        // null -> returns false
        assertNotEquals(null, genFirstBodyReportCommand);

        // different person -> returns false
        assertNotEquals(genFirstBodyReportCommand, genSecondBodyReportCommand);
    }
}
