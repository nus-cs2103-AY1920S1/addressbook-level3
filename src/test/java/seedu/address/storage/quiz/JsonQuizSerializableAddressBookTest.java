package seedu.address.storage.quiz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.quiz.AddressQuizBook;
import seedu.address.testutil.TypicalQuestion;

public class JsonQuizSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_QUESTION_FILE = TEST_DATA_FOLDER.resolve("typicalQuestionAddressBook.json");
    private static final Path INVALID_QUESTION_FILE = TEST_DATA_FOLDER.resolve("invalidQuestionAddressBook.json");
    private static final Path DUPLICATE_QUESTION_FILE = TEST_DATA_FOLDER.resolve("duplicateQuestionAddressBook.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonQuizSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_QUESTION_FILE,
                JsonQuizSerializableAddressBook.class).get();
        AddressQuizBook addressBookFromFile = dataFromFile.toModelType();
        AddressQuizBook typicalQuestionAddressBook = TypicalQuestion.getTypicalAddressQuizBook();
        assertEquals(addressBookFromFile, typicalQuestionAddressBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonQuizSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_QUESTION_FILE,
                JsonQuizSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonQuizSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_QUESTION_FILE,
                JsonQuizSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonQuizSerializableAddressBook.MESSAGE_DUPLICATE_QUESTION,
                dataFromFile::toModelType);
    }

}
