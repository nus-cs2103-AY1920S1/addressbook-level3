package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalog;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.BorrowerRecords;
import seedu.address.model.Catalog;
import seedu.address.model.LoanRecords;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookPredicate;

class InfoCommandTest {
    private Model model =
            new ModelManager(getTypicalCatalog(), new LoanRecords(), new BorrowerRecords(), new UserPrefs());

    @Test
    public void execute_validArguments_success() {
        InfoCommand infoCommand = new InfoCommand(INDEX_FIRST_BOOK);
        Book target = model.getFilteredBookList().get(0);
        String expectedMessage = String.format(InfoCommand.MESSAGE_BOOK_INFO, target.getTitle());

        Model expectedModel = new ModelManager(
                new Catalog(model.getCatalog()), new LoanRecords(), new BorrowerRecords(), new UserPrefs());
        BookPredicate predicate = new BookPredicate().setTitle(target.getTitle().toString());
        expectedModel.updateFilteredBookList(predicate);

        assertCommandSuccess(infoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_failure() {
        InfoCommand infoCommand = new InfoCommand(Index.fromOneBased(10));
        assertCommandFailure(infoCommand, model, MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

}
