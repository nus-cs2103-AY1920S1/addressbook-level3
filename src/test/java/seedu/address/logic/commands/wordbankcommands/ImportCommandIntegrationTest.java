package seedu.address.logic.commands.wordbankcommands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.storage.wordbanks.JsonWordBankListStorage;
import seedu.address.storage.wordbanks.WordBankListStorage;

import java.nio.file.Path;
import java.nio.file.Paths;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
class ImportCommandIntegrationTest {
    private static Path defaultPath = Paths.get("data", "ImportCommandTest");

    private Model model;

    private WordBankListStorage wbls = new JsonWordBankListStorage(defaultPath, false);

    ImportCommandIntegrationTest() throws DataConversionException, IllegalValueException {
    }

    @BeforeEach
    void setUp() {
        model = new ModelManager();
    }

//    @Test
//    void execute_Import_success() {
//    }
//
//
//    @Test
//    void execute_duplicateCard_throwsCommandException() {
//    }

}
