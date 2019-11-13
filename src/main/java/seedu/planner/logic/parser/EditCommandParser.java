package seedu.planner.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.planner.commons.core.index.Index;

import seedu.planner.logic.commands.HelpCommand;
import seedu.planner.logic.commands.editcommand.EditAccommodationCommand;
import seedu.planner.logic.commands.editcommand.EditAccommodationCommand.EditAccommodationDescriptor;
import seedu.planner.logic.commands.editcommand.EditActivityCommand;
import seedu.planner.logic.commands.editcommand.EditActivityCommand.EditActivityDescriptor;
import seedu.planner.logic.commands.editcommand.EditCommand;
import seedu.planner.logic.commands.editcommand.EditContactCommand;
import seedu.planner.logic.commands.editcommand.EditContactCommand.EditContactDescriptor;

import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.tag.Tag;

//@@author KxxMxxx
/**
 * Parses input arguments and creates a new EditContactCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    private static final Pattern EDIT_COMMAND_FORMAT = Pattern.compile("(?<type>\\S+)(?<arguments>.*)");

    /**
     * Parses the given {@code String} of arguments in the context of the EditActivityCommand or EditContactCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        final Matcher matcher = EDIT_COMMAND_FORMAT.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String type = matcher.group("type");
        final String arguments = matcher.group("arguments");

        switch (type) {
        case EditActivityCommand.SECOND_COMMAND_WORD:
            return parseActivityForEdit(arguments);
        case EditAccommodationCommand.SECOND_COMMAND_WORD:
            return parseAccommodationForEdit(arguments);
        case EditContactCommand.SECOND_COMMAND_WORD:
            return parseContactForEdit(arguments);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the EditAccommodationCommand
     * and returns an EditAccommodationCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditAccommodationCommand parseAccommodationForEdit(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditAccommodationCommand.MESSAGE_USAGE), pe);
        }

        EditAccommodationDescriptor editAccommodationDescriptor = new EditAccommodationDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editAccommodationDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editAccommodationDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editAccommodationDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editAccommodationDescriptor::setTags);

        if (!editAccommodationDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditAccommodationCommand.MESSAGE_NOT_EDITED);
        }

        return new EditAccommodationCommand(index, editAccommodationDescriptor, false);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the EditActivityCommand
     * and returns an EditActivityCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditActivityCommand parseActivityForEdit(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE,
                PREFIX_COST, PREFIX_TAG, PREFIX_DURATION, PREFIX_PRIORITY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditActivityCommand.MESSAGE_USAGE),
                    pe);
        }

        EditActivityDescriptor editActivityDescriptor = new EditActivityDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editActivityDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editActivityDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editActivityDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_COST).isPresent()) {
            editActivityDescriptor.setCost(ParserUtil.parseCost(argMultimap.getValue(PREFIX_COST).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editActivityDescriptor::setTags);

        if (argMultimap.getValue(PREFIX_DURATION).isPresent()) {
            editActivityDescriptor.setDuration(ParserUtil.parseDuration(argMultimap.getValue(PREFIX_DURATION).get()));
        }
        if (argMultimap.getValue(PREFIX_PRIORITY).isPresent()) {
            editActivityDescriptor.setPriority(ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get()));
        }
        if (!editActivityDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditActivityCommand.MESSAGE_NOT_EDITED);
        }

        return new EditActivityCommand(index, editActivityDescriptor, false);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the EditContactCommand
     * and returns an EditContactCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditContactCommand parseContactForEdit(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditContactCommand.MESSAGE_USAGE),
                    pe);
        }

        EditContactDescriptor editContactDescriptor = new EditContactDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editContactDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editContactDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editContactDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editContactDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editContactDescriptor::setTags);

        if (!editContactDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditContactCommand.MESSAGE_NOT_EDITED);
        }

        return new EditContactCommand(index, editContactDescriptor, false);
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
