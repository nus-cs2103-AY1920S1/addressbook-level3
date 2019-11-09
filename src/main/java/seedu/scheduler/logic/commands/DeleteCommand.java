package seedu.scheduler.logic.commands;

import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_ROLE;

/**
 * Deletes a person from Scheduler.
 */
public abstract class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes an entity identified by the entity's name. "
            + "An entity can either be an interviewee or interviewer.\n"
            + "Parameters to delete interviewees:\n"
            + "NAME " + PREFIX_ROLE + "interviewee\n"
            + "Parameters to delete interviewees:\n"
            + "NAME " + PREFIX_ROLE + "interviewer\n"
            + "Examples:\n"
            + COMMAND_WORD + " John Doe " + "r/interviewee\n"
            + COMMAND_WORD + " Mary Jane " + "r/interviewer";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
}
