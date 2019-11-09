package seedu.algobase.ui.action.parser;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.logic.parser.ParserUtil;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.Id;
import seedu.algobase.ui.action.UiActionDetails;
import seedu.algobase.ui.action.UiParser;
import seedu.algobase.ui.action.actions.EditProblemUiAction;
import seedu.algobase.ui.action.actions.EditProblemUiAction.EditProblemDescriptor;

/**
 * Parses input arguments and creates a new EditProblemUiAction object
 */
public class EditProblemUiActionParser implements UiParser<EditProblemUiAction> {

    private static final int ID_INDEX = 0;
    private static final int NAME_INDEX = 1;
    private static final int AUTHOR_INDEX = 2;
    private static final int WEBLINK_INDEX = 3;
    private static final int DESCRIPTION_INDEX = 4;
    private static final int DIFFICULTY_INDEX = 5;
    private static final int REMARK_INDEX = 6;
    private static final int SOURCE_INDEX = 7;

    private static final Logger logger = LogsCenter.getLogger(EditProblemUiActionParser.class);

    /**
     * Parses the given {@code UiActionDetails} object
     * and returns an EditProblemUiAction object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditProblemUiAction parse(UiActionDetails uiActionDetails) throws ParseException {
        logger.info("Parsing UI Action Details of type " + uiActionDetails.getActionWord()
            + " and size " + uiActionDetails.size());

        requireNonNull(uiActionDetails);
        assert uiActionDetails.size() == 8;
        assert uiActionDetails.get(ID_INDEX) instanceof Id;
        assert uiActionDetails.get(NAME_INDEX) instanceof String;
        assert uiActionDetails.get(AUTHOR_INDEX) instanceof String;
        assert uiActionDetails.get(WEBLINK_INDEX) instanceof String;
        assert uiActionDetails.get(DESCRIPTION_INDEX) instanceof String;
        assert uiActionDetails.get(DIFFICULTY_INDEX) instanceof String;
        assert uiActionDetails.get(REMARK_INDEX) instanceof String;
        assert uiActionDetails.get(SOURCE_INDEX) instanceof String;

        Id id = UiParserUtil.parseId(uiActionDetails.get(ID_INDEX));

        EditProblemDescriptor editProblemDescriptor = new EditProblemDescriptor();

        String nameString = UiParserUtil.parseString(uiActionDetails.get(NAME_INDEX));
        if (!nameString.isBlank()) {
            editProblemDescriptor.setName(ParserUtil.parseName(nameString));
        }

        String authorString = UiParserUtil.parseString(uiActionDetails.get(AUTHOR_INDEX));
        if (!authorString.isBlank()) {
            editProblemDescriptor.setAuthor(ParserUtil.parseAuthor(authorString));
        }

        String weblinkString = UiParserUtil.parseString(uiActionDetails.get(WEBLINK_INDEX));
        if (!weblinkString.isBlank()) {
            editProblemDescriptor.setWebLink(ParserUtil.parseWeblink(weblinkString));
        }

        String descriptionString = UiParserUtil.parseString(uiActionDetails.get(DESCRIPTION_INDEX));
        if (!descriptionString.isBlank()) {
            editProblemDescriptor.setDescription(ParserUtil.parseDescription(descriptionString));
        }

        String difficultyString = UiParserUtil.parseString(uiActionDetails.get(DIFFICULTY_INDEX));
        if (!difficultyString.isBlank()) {
            editProblemDescriptor.setDifficulty(ParserUtil.parseDifficulty(difficultyString));
        }

        String remarkString = UiParserUtil.parseString(uiActionDetails.get(REMARK_INDEX));
        if (!remarkString.isBlank()) {
            editProblemDescriptor.setRemark(ParserUtil.parseRemark(remarkString));
        }

        String sourceString = UiParserUtil.parseString(uiActionDetails.get(SOURCE_INDEX));
        if (!sourceString.isBlank()) {
            editProblemDescriptor.setSource(ParserUtil.parseSource(sourceString));
        }

        if (!editProblemDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditProblemUiAction.MESSAGE_NOT_EDITED);
        }

        return new EditProblemUiAction(id, editProblemDescriptor);
    }
}
