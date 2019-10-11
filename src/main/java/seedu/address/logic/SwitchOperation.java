package seedu.address.logic;

import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.quiz.LogicQuizManager;
import seedu.address.model.quiz.AddressQuizBook;
import seedu.address.model.quiz.ModelQuizManager;
import seedu.address.storage.quiz.*;
import seedu.address.ui.quiz.UiQuizManager;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public class SwitchOperation {
    private String args;
    private seedu.address.model.quiz.UserPrefs userPrefs;
    private seedu.address.model.quiz.Model quizModel;
    private seedu.address.logic.quiz.Logic quizLogic;
    private seedu.address.ui.quiz.Ui quizUi;

    public SwitchOperation(String args) {
        this.args = args;
    }

    public void execute() {
        if (args.equals("quiz")) {
            Config config = MainApp.config;
            UserPrefsStorage userPrefsStorage = new JsonQuizUserPrefsStorage(config.getUserPrefsFilePath());
            userPrefs = initPrefs(userPrefsStorage);
            AddressBookStorage addressBookStorage = new JsonQuizAddressBookStorage(userPrefs.getAddressBookFilePath());
            Storage quizStorage = new StorageQuizManager(addressBookStorage, userPrefsStorage);

            quizModel = initModelManager(quizStorage, userPrefs);
            quizLogic = new LogicQuizManager(quizModel, quizStorage);
            quizUi = new UiQuizManager(quizLogic);
            Stage stages = MainApp.primary;
            quizUi.start(stages);
        } else {

        }
    }

    protected seedu.address.model.quiz.UserPrefs initPrefs(seedu.address.storage.quiz.UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        System.out.println("Using prefs file : " + prefsFilePath);

        seedu.address.model.quiz.UserPrefs initializedPrefs;
        try {
            Optional<seedu.address.model.quiz.UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new seedu.address.model.quiz.UserPrefs());
        } catch (DataConversionException e) {
            System.out.println("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new seedu.address.model.quiz.UserPrefs();
        } catch (IOException e) {
            System.out.println("Problem while reading from the file. Will be starting with an empty AddressBook");
            initializedPrefs = new seedu.address.model.quiz.UserPrefs();
        }

        // Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            System.out.println("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    private seedu.address.model.quiz.Model initModelManager(seedu.address.storage.quiz.Storage storage,
                                                            seedu.address.model.quiz.ReadOnlyUserPrefs userPrefs) {
        Optional<seedu.address.model.quiz.ReadOnlyAddressBook> addressBookOptional;
        seedu.address.model.quiz.ReadOnlyAddressBook initialData;
        try {
            addressBookOptional = storage.readAddressBook();
            if (!addressBookOptional.isPresent()) {
                System.out.println("Data file not found. Will be starting with a sample AddressBook");
            }
            initialData = addressBookOptional.orElseGet(seedu.address.model.quiz.util.SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            System.out.println("Data file not in the correct format. Will be starting with an empty AddressBook");
            initialData = new seedu.address.model.quiz.AddressQuizBook();
        } catch (IOException e) {
            System.out.println("Problem while reading from the file. Will be starting with an empty AddressBook");
            initialData = new AddressQuizBook();
        }

        return new ModelQuizManager(initialData, userPrefs);
    }
}
