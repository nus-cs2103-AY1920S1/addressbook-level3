package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;
import seedu.address.testutil.BookBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalCatalog(), new UserPrefs());
    }

    @Test
    public void execute_newBook_success() {
        Book validBook = new BookBuilder().withTitle("Hari").build();

        Model expectedModel = new ModelManager(model.getCatalog(), new UserPrefs());
        expectedModel.addBook(validBook);

        assertCommandSuccess(new AddCommand(validBook), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validBook), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Book bookInList = model.getCatalog().getBookList().get(0);
        assertCommandFailure(new AddCommand(bookInList), model, AddCommand.MESSAGE_DUPLICATE_BOOK);
    }

}
