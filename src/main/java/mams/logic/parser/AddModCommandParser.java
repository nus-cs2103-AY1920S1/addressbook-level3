package mams.logic.parser;

import static java.util.Objects.requireNonNull;
import static mams.logic.parser.CliSyntax.PREFIX_CREDITS;
import static mams.logic.parser.CliSyntax.PREFIX_EMAIL;
import static mams.logic.parser.CliSyntax.PREFIX_MATRICID;
import static mams.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static mams.logic.parser.CliSyntax.PREFIX_NAME;
import static mams.logic.parser.CliSyntax.PREFIX_SESSIONID;
import static mams.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import mams.logic.commands.AddModCommand;
import mams.logic.commands.EditCommand;
import mams.logic.parser.exceptions.ParseException;
import mams.model.tag.Tag;

/**
 * temp.
 */
public class AddModCommandParser implements Parser<AddModCommand> {

    /**
     * Temp.
     * @param args temp
     * @return temp
     * @throws ParseException temp
     */
    @Override
    public AddModCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MATRICID, PREFIX_MODULE_CODE,
                        PREFIX_SESSIONID);

        //TODO: Note that fields not changed(name,email etc) will be retaken in EditCommand
        // Here, only need to tokenize and push the strings to Addmodcommand
        AddModCommand.EditStudentDescriptor editStudentDescriptor = new AddModCommand.EditStudentDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editStudentDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_CREDITS).isPresent()) {
            editStudentDescriptor.setCredits(ParserUtil.parseCredits(argMultimap.getValue(PREFIX_CREDITS).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editStudentDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_MATRICID).isPresent()) {
            editStudentDescriptor.setMatricId(ParserUtil.parseMatricId(argMultimap.getValue(PREFIX_MATRICID).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editStudentDescriptor::setTags);

        if (!editStudentDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new AddModCommand(argMultimap.getValue(PREFIX_MATRICID).get(),
                argMultimap.getValue(PREFIX_MATRICID).get(), editStudentDescriptor);
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
