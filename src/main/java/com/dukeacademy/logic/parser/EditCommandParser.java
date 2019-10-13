package com.dukeacademy.logic.parser;

import static com.dukeacademy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static com.dukeacademy.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static com.dukeacademy.logic.parser.CliSyntax.PREFIX_STATUS;
import static com.dukeacademy.logic.parser.CliSyntax.PREFIX_TAG;
import static com.dukeacademy.logic.parser.CliSyntax.PREFIX_TITLE;
import static com.dukeacademy.logic.parser.CliSyntax.PREFIX_TOPIC;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import com.dukeacademy.commons.core.index.Index;
import com.dukeacademy.logic.commands.EditCommand;
import com.dukeacademy.logic.commands.EditCommand.EditQuestionDescriptor;
import com.dukeacademy.logic.parser.exceptions.ParseException;
import com.dukeacademy.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_TOPIC,
                    PREFIX_STATUS, PREFIX_DIFFICULTY, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditQuestionDescriptor editQuestionDescriptor = new EditQuestionDescriptor();
        if (argMultimap.getValue(PREFIX_TITLE).isPresent()) {
            editQuestionDescriptor.setTitle(ParserUtil.parseName(argMultimap.getValue(PREFIX_TITLE).get()));
        }
        if (argMultimap.getValue(PREFIX_TOPIC).isPresent()) {
            editQuestionDescriptor.setTopic(ParserUtil.parseTopic(argMultimap.getValue(PREFIX_TOPIC).get()));
        }
        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            editQuestionDescriptor.setStatus(ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get()));
        }
        if (argMultimap.getValue(PREFIX_DIFFICULTY).isPresent()) {
            editQuestionDescriptor.setDifficulty(ParserUtil
                .parseDifficulty(argMultimap.getValue(PREFIX_DIFFICULTY).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editQuestionDescriptor::setTags);

        if (!editQuestionDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editQuestionDescriptor);
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

}
