package seedu.scheduler.logic.commands;

import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_FACULTY;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_NUS_WORK_EMAIL;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_PERSONAL_EMAIL;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_SLOT;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_YEAR_OF_STUDY;

import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.model.Model;

/**
 * Edits the details of an existing entity in the Scheduler.
 */
public abstract class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the entity identified "
            + "by its name as displayed on the entity list. Entities are interviewees or interviewers.\n"
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters to edit interviewees:\n"
            + "NAME " + PREFIX_ROLE + "interviewee "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_FACULTY + "FACULTY] "
            + "[" + PREFIX_YEAR_OF_STUDY + "YEAR OF STUDY] "
            + "[" + PREFIX_PERSONAL_EMAIL + "PERSONAL_EMAIL] "
            + "[" + PREFIX_NUS_WORK_EMAIL + "NUS_WORK_EMAIL] "
            + "[" + PREFIX_DEPARTMENT + "DEPARTMENT]... "
            + "[" + PREFIX_SLOT + "SLOT]... "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Parameters to edit interviewers:\n"
            + "NAME " + PREFIX_ROLE + "interviewer "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_NUS_WORK_EMAIL + "NUS_WORK_EMAIL] "
            + "[" + PREFIX_DEPARTMENT + "DEPARTMENT] "
            + "[" + PREFIX_SLOT + "SLOT]... "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example (edit interviewee):\n"
            + COMMAND_WORD + " John Doe "
            + PREFIX_ROLE + "interviewee "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_FACULTY + "School of Computing "
            + PREFIX_SLOT + "05/11/2019 18:00-19:00\n"
            + "Example (edit interviewer):\n"
            + COMMAND_WORD + " John Doe "
            + PREFIX_ROLE + "interviewer "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_DEPARTMENT + "Marketing\n";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the database.";
    public static final String MESSAGE_DUPLICATE_DEPARTMENT = "Duplicate departments are not allowed.";
    public static final String MESSAGE_DUPLICATE_SLOT = "Duplicate slots are not allowed";

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;
}
