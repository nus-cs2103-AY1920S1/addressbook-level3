package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.LoanSlipException;
import seedu.address.commons.util.LoanSlipUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CatalogParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyCatalog;
import seedu.address.model.book.Book;
import seedu.address.model.borrower.Borrower;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CatalogParser catalogParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        catalogParser = new CatalogParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = catalogParser.parseCommand(commandText);
        commandResult = command.execute(model);

        if (command instanceof ReversibleCommand) {
            model.commitCommand((ReversibleCommand) command);
        }

        try {
            storage.saveLoanRecords(model.getLoanRecords());
            storage.saveCatalog(model.getCatalog());
            storage.saveBorrowerRecords(model.getBorrowerRecords());
            if (commandResult.isDone()) {
                if (LoanSlipUtil.isMounted()) {
                    logger.info("making new loan slip");
                    storage.storeNewLoanSlip();
                    LoanSlipUtil.openGeneratedLoanSlip();
                    LoanSlipUtil.clearSession();
                }
            }
        } catch (IOException ioe) {
            logger.info("IOException");
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        } catch (LoanSlipException lse) {
            logger.info("Error in generating loan slip");
            return commandResult;
        }

        return commandResult;
    }

    @Override
    public Borrower getServingBorrower() {
        assert (isServeMode()) : "Not in serve mode";
        return model.getServingBorrower();
    }

    @Override
    public ObservableList<Book> getServingBorrowerBookList() {
        return FXCollections.observableList(model.getBorrowerBooks());
    }

    @Override
    public String getLoanHistoryOfBookAsString(Book target) {
        return model.getLoanHistoryOfBookAsString(target);
    }

    @Override
    public boolean isServeMode() {
        return model.isServeMode();
    }

    @Override
    public ReadOnlyCatalog getCatalog() {
        return model.getCatalog();
    }

    @Override
    public ObservableList<Book> getFilteredBookList() {
        return model.getFilteredBookList();
    }

    @Override
    public Path getCatalogFilePath() {
        return model.getCatalogFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public String getLoadStatus() {
        return model.getLoadStatus();
    }
}
