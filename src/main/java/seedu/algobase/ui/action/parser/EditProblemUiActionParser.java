package seedu.algobase.ui.action.parser;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.core.Messages.MESSAGE_UNKNOWN_UI_ACTION_PROPERTY;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.algobase.logic.parser.ParserUtil;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.Id;
import seedu.algobase.model.tag.Tag;
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

    /**
     * Parses the given {@code UiActionDetails} object
     * and returns an EditProblemUiAction object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditProblemUiAction parse(UiActionDetails uiActionDetails) throws ParseException {
        requireNonNull(uiActionDetails);

        Id id = parseId(uiActionDetails.get(ID_INDEX));

        EditProblemDescriptor editProblemDescriptor = new EditProblemDescriptor();

        String nameString = parseString(uiActionDetails.get(NAME_INDEX));
        if (!nameString.isBlank()) {
            editProblemDescriptor.setName(ParserUtil.parseName(nameString));
        }

        String authorString = parseString(uiActionDetails.get(AUTHOR_INDEX));
        if (!authorString.isBlank()) {
            editProblemDescriptor.setAuthor(ParserUtil.parseAuthor(authorString));
        }

        String weblinkString = parseString(uiActionDetails.get(WEBLINK_INDEX));
        if (!weblinkString.isBlank()) {
            editProblemDescriptor.setWebLink(ParserUtil.parseWeblink(weblinkString));
        }

        String descriptionString = parseString(uiActionDetails.get(DESCRIPTION_INDEX));
        if (!descriptionString.isBlank()) {
            editProblemDescriptor.setDescription(ParserUtil.parseDescription(descriptionString));
        }

        String difficultyString = parseString(uiActionDetails.get(DIFFICULTY_INDEX));
        if (!difficultyString.isBlank()) {
            editProblemDescriptor.setDescription(ParserUtil.parseDescription(descriptionString));
        }

        String remarkString = parseString(uiActionDetails.get(REMARK_INDEX));
        if (!remarkString.isBlank()) {
            editProblemDescriptor.setRemark(ParserUtil.parseRemark(remarkString));
        }
        String sourceString = parseString(uiActionDetails.get(SOURCE_INDEX));
        if (!sourceString.isBlank()) {
            editProblemDescriptor.setSource(ParserUtil.parseSource(sourceString));
        }

        if (!editProblemDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditProblemUiAction.MESSAGE_NOT_EDITED);
        }

        return new EditProblemUiAction(id, editProblemDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

    /**
     * Converts an id of type {@Object} into an id of type {@Id}
     *
     * @throws ParseException if given object is not of type {@Id}
     */
    private Id parseId(Object id) throws ParseException {
        if (!(id instanceof Id)) {
            throw new ParseException(MESSAGE_UNKNOWN_UI_ACTION_PROPERTY);
        }

        return (Id) id;
    }

    /**
     * Converts a string of type {@Object} into an string of type {@String}
     *
     * @throws ParseException if given object is not of type {@String}
     */
    private String parseString(Object string) throws ParseException {
        if (!(string instanceof String)) {
            throw new ParseException(MESSAGE_UNKNOWN_UI_ACTION_PROPERTY);
        }

        return ((String) string).trim();
    }
}
