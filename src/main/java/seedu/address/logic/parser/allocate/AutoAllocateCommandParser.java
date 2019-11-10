package seedu.address.logic.parser.allocate;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.allocate.AutoAllocateCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;


/**
 * Parses input arguments and creates a new AutoAllocateCommand object
 */
public class AutoAllocateCommandParser implements Parser<AutoAllocateCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AutoAllocateCommand
     * and returns an AutoAllocateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AutoAllocateCommand parse(String args) throws ParseException, CommandException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EMPLOYEE_NUMBER, PREFIX_TAG);

        Index eventIndex;
        Integer manpowerCount;
        Set<Tag> tagList;

        try {
            eventIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
            manpowerCount = ParserUtil.parseManpowerToAllocate(argMultimap.getValue(PREFIX_EMPLOYEE_NUMBER)
                    .orElse(null));

        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AutoAllocateCommand.MESSAGE_USAGE), pe);
        }

        tagList = parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).orElse(new HashSet<>());

        return new AutoAllocateCommand(eventIndex, manpowerCount, tagList);
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
