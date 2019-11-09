//@@author SakuraBlossom
package seedu.address.logic.parser.staff;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_NO_FIELD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.commands.staff.EditStaffDetailsCommand;
import seedu.address.logic.commands.utils.EditPersonDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.patients.EditPatientDetailsCommandParser;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditStaffDetailsCommandParser implements Parser<ReversibleActionPairCommand> {

    private List<Person> lastShownList;

    public EditStaffDetailsCommandParser(Model model) {
        this.lastShownList = model.getFilteredStaffList();
    }

    @Override
    public ReversibleActionPairCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ENTRY, PREFIX_ID, PREFIX_NAME, PREFIX_PHONE,
                        PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        if (!argMultimap.getValue(PREFIX_ENTRY).isPresent() || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditStaffDetailsCommand.MESSAGE_USAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_ENTRY).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditStaffDetailsCommand.MESSAGE_USAGE), pe);
        }
        EditPersonDescriptor editPersonDescriptor = createEditedPersonDescriptor(argMultimap);
        Person personToEdit = ParserUtil.getEntryFromList(lastShownList, index);
        Person editedPerson = EditPatientDetailsCommandParser.createEditedPerson(personToEdit, editPersonDescriptor);

        return new ReversibleActionPairCommand(
            new EditStaffDetailsCommand(personToEdit, editedPerson),
            new EditStaffDetailsCommand(editedPerson, personToEdit));
    }

    /**
     * Creates and returns a {@code editPersonDescriptor} based on a given {@code ArgumentMultimap}
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    private EditPersonDescriptor createEditedPersonDescriptor(ArgumentMultimap argMultimap)
            throws ParseException {

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_ID).isPresent()) {
            editPersonDescriptor.setReferenceId(
                    ParserUtil.issueStaffReferenceId(argMultimap.getValue(PREFIX_ID).get()));
        }
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editPersonDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        EditPatientDetailsCommandParser
                .parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG))
                .ifPresent(editPersonDescriptor::setTags);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(MESSAGE_NO_FIELD);
        }

        return editPersonDescriptor;
    }
}
