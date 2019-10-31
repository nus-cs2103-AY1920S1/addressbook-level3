package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FACULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUS_WORK_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSONAL_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SLOT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR_OF_STUDY;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Edits the details of an existing entity in the Scheduler.
 */
public abstract class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the entity identified "
            + "by the entity's name used in the displayed entity list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: NAME, ROLE (must be interviewee/interviewer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_FACULTY + "FACULTY] "
            + "[" + PREFIX_YEAR_OF_STUDY + "YEAR OF STUDY] "
            + "[" + PREFIX_DEPARTMENT + "DEPARTMENT]... "
            + "[" + PREFIX_SLOT + "TIMESLOTS]... "
            + "[" + PREFIX_PERSONAL_EMAIL + "PERSONAL_EMAIL] "
            + "[" + PREFIX_NUS_WORK_EMAIL + "NUS_WORK_EMAIL]\n"
            + "Example: " + COMMAND_WORD + " John Doe "
            + PREFIX_ROLE + "interviewee "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_FACULTY + "School of Computing";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;
}
