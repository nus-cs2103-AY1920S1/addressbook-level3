package seedu.ezwatchlist.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_DATE_OF_RELEASE;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_IS_WATCHED;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_RUNNING_TIME;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_ACTOR;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.ezwatchlist.commons.core.index.Index;
import seedu.ezwatchlist.logic.commands.EditCommand;
import seedu.ezwatchlist.logic.parser.exceptions.ParseException;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.commons.core.Messages;

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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE_OF_RELEASE, PREFIX_DESCRIPTION, 
                        PREFIX_IS_WATCHED, PREFIX_RUNNING_TIME, PREFIX_ACTOR);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditCommand.EditShowDescriptor editShowDescriptor = new EditCommand.EditShowDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editShowDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE_OF_RELEASE).isPresent()) {
            editShowDescriptor.setDateOfRelease(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE_OF_RELEASE).get()));
        }
        if (argMultimap.getValue(PREFIX_IS_WATCHED).isPresent()) {
            editShowDescriptor.setIsWatched(ParserUtil.parseIsWatched(argMultimap.getValue(PREFIX_IS_WATCHED).get()));
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editShowDescriptor.setDescription(ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }
        if (argMultimap.getValue(PREFIX_RUNNING_TIME).isPresent()) {
            editShowDescriptor.setRunningTime(ParserUtil.parseRunningTime(argMultimap.getValue(PREFIX_RUNNING_TIME).get()));
        }
        parseActorsForEdit(argMultimap.getAllValues(PREFIX_ACTOR)).ifPresent(editShowDescriptor::setActors);

        if (!editShowDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editShowDescriptor);
    }

    /**
     * Parses {@code Collection<String> actors} into a {@code Set<Actor>} if {@code actors} is non-empty.
     * If {@code actors} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Actor>} containing zero actors.
     */
    private Optional<Set<Actor>> parseActorsForEdit(Collection<String> actors) throws ParseException {
        assert actors != null;

        if (actors.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> actorSet = actors.size() == 1 && actors.contains("") ? Collections.emptySet() : actors;
        return Optional.of(ParserUtil.parseActors(actorSet));
    }

}
