package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalog;

import org.junit.jupiter.api.Test;

import seedu.address.model.BorrowerRecords;
import seedu.address.model.Catalog;
import seedu.address.model.LoanRecords;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.BookPredicate;

public class ClearCommandTest {

    @Test
    public void execute_emptyCatalog_success() {
        Model model = new ModelManager();
        Model expectedModel =
                new ModelManager(new Catalog(), new LoanRecords(), new BorrowerRecords(), new UserPrefs());
        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyCatalog_success() {
        Model model = new ModelManager(getTypicalCatalog(), new LoanRecords(), new BorrowerRecords(), new UserPrefs());
        Model expectedModel =
                new ModelManager(getTypicalCatalog(), new LoanRecords(), new BorrowerRecords(), new UserPrefs());
        // filter the model with a predicate
        BookPredicate predicate = new BookPredicate().setTitle("harry");
        model.updateFilteredBookList(predicate);

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
