package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.InfoCommand.MESSAGE_BOOK_INFO;
import static seedu.address.testutil.TypicalBooks.BOOK_1;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.BorrowerRecords;
import seedu.address.model.LoanRecords;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

class InfoCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalCatalog(), new LoanRecords(), new BorrowerRecords(), new UserPrefs());
        expectedModel = new ModelManager(
                getTypicalCatalog(), new LoanRecords(), new BorrowerRecords(), new UserPrefs());
    }

    @Test
    public void execute_invalidIndex_failure() {
        InfoCommand infoCommand = new InfoCommand(Index.fromOneBased(10));
        assertCommandFailure(infoCommand, model, MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_info_success() {
        CommandResult expectedCommandResult =
                CommandResult.commandResultInfo(String.format(MESSAGE_BOOK_INFO, BOOK_1.getTitle()), BOOK_1);
        assertCommandSuccess(new InfoCommand(Index.fromOneBased(1)), model, expectedCommandResult, expectedModel);
    }

}
