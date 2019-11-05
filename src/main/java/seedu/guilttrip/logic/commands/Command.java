package seedu.guilttrip.logic.commands;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.logic.parser.Prefix;
import seedu.guilttrip.model.Model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    public static final String COMMAND_WORD = "command";

    public static final String MESSAGE_USAGE = "usage";

    public static final Set<Prefix> REQUIRED_PREFIXES = Collections.unmodifiableSet(new HashSet<Prefix>());

    public static final boolean REQUIRES_PREAMBLE = false;

    /**
     * Executes the command and returns the result message.
     *
     * @param model   {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model, CommandHistory history) throws CommandException;
}
