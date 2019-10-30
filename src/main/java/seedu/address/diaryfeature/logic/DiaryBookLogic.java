package seedu.address.diaryfeature.logic;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.diaryfeature.logic.parser.DiaryBookParser;
import seedu.address.diaryfeature.model.DiaryModel;
import seedu.address.diaryfeature.model.diaryEntry.DiaryEntry;
import seedu.address.diaryfeature.storage.DiaryBookStorage;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The main AddressBookLogicManager of the app.
 */

public class DiaryBookLogic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(seedu.address.diaryfeature.logic.DiaryBookLogic.class);

    private final DiaryModel diaryModel;
    private final DiaryBookStorage storage;
    private final DiaryBookParser diaryBookParser;

    public DiaryBookLogic(DiaryModel diaryModel, DiaryBookStorage storage) {
        this.diaryModel = diaryModel;
        this.storage = storage;
        this.diaryBookParser = new DiaryBookParser();
    }

    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = diaryBookParser.parseCommand(commandText);
        commandResult = command.execute(diaryModel);

        try {
            storage.saveDiaryBook(diaryModel.getDiaryBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }


    public ObservableList<DiaryEntry> getFilteredDiaryEntryList() {
        return diaryModel.getFilteredDiaryEntryList();
    }




}
