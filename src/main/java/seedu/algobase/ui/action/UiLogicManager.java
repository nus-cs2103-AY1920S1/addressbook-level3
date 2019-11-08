package seedu.algobase.ui.action;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.util.logging.Logger;

import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.logic.LogicManager;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.Model;
import seedu.algobase.storage.Storage;
import seedu.algobase.ui.UiActionException;
import seedu.algobase.ui.action.parser.AlgoBaseUiActionParser;

/**
 * The main UI LogicManager of the app.
 */
public class UiLogicManager implements UiLogic {

    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AlgoBaseUiActionParser algoBaseUiActionParser;

    public UiLogicManager(Model model, Storage storage) {
        requireAllNonNull(model, storage);

        this.model = model;
        this.storage = storage;
        algoBaseUiActionParser = new AlgoBaseUiActionParser();
    }

    @Override
    public UiActionResult execute(UiActionDetails uiActionDetails) throws UiActionException, ParseException {
        logger.info("----------------[USER COMMAND][" + uiActionDetails + "]");

        requireNonNull(uiActionDetails);

        UiAction uiAction = algoBaseUiActionParser.parseCommand(uiActionDetails);
        UiActionResult uiActionResult = uiAction.execute(model);

        try {
            storage.saveAlgoBase(model.getAlgoBase());
        } catch (IOException ioe) {
            throw new UiActionException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return uiActionResult;
    }
}
