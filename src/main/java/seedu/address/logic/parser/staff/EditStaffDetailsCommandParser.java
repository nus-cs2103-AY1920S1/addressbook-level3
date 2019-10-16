package seedu.address.logic.parser.staff;

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

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

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
                ArgumentTokenizer.tokenize(args, PREFIX_ID, PREFIX_NAME, PREFIX_PHONE,
                        PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditStaffDetailsCommand.MESSAGE_USAGE), pe);
        }
        EditPersonDescriptor editPersonDescriptor = EditPatientDetailsCommandParser.createEditedPersonDescriptor(argMultimap);
        Person personToEdit = ParserUtil.getEntryFromList(lastShownList, index);
        Person editedPerson = EditPatientDetailsCommandParser.createEditedPerson(personToEdit, editPersonDescriptor);

        return new ReversibleActionPairCommand(
            new EditStaffDetailsCommand(personToEdit, editedPerson),
            new EditStaffDetailsCommand(editedPerson, personToEdit));
    }
}
