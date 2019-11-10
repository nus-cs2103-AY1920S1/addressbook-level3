package seedu.address.diaryfeature.logic;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.diaryfeature.logic.parser.DiaryBookParser;
import seedu.address.diaryfeature.logic.parser.exceptions.EmptyArgumentException;
import seedu.address.diaryfeature.model.DiaryBook;
import seedu.address.diaryfeature.model.DiaryModel;
import seedu.address.diaryfeature.model.diaryEntry.DiaryEntry;
import seedu.address.diaryfeature.model.util.DiaryBookStatistics;
import seedu.address.diaryfeature.model.util.SampleDataUtil;
import seedu.address.diaryfeature.storage.DiaryBookStorage;
import seedu.address.diaryfeature.storage.JsonDiaryBookStorage;
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

    public DiaryBookLogic() {
        JsonDiaryBookStorage storage = new JsonDiaryBookStorage(Paths.get("data","diaryBook.json"));
        Optional<DiaryBook> diaryBookOptional;
        DiaryBook initialData;
        try {
            diaryBookOptional = storage.readDiaryBook();
            if (diaryBookOptional.isEmpty()) {
                logger.info("Data file not found. Will be starting with a sample DiaryBook");
                initialData = SampleDataUtil.getSampleDiaryBook();
            } else {
                initialData = diaryBookOptional.get();
            }
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty DiaryBook");
            initialData = new DiaryBook();
        }
        this.diaryModel = new DiaryModel(initialData);
        this.storage = storage;
        this.diaryBookParser = new DiaryBookParser();
    }

    public CommandResult execute(String commandText) throws CommandException, ParseException, EmptyArgumentException {
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

    public DiaryBookStatistics getStatistics() {
        return new DiaryBookStatisticsManager();
    }

    /**
     * Local class for {@link DiaryBookStatistics}
     */
    private class DiaryBookStatisticsManager implements DiaryBookStatistics {
        @Override
        public int getTotalDiaryEntries() {
            return diaryModel.getTotalDiaryEntries();
        }

        @Override
        public XYChart.Series<String, Number> getDiaryBarChart() {
            return diaryModel.getDiaryBarChart();
        }
    }
}
