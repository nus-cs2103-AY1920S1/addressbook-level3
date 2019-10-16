package com.typee.logic.commands;

import static com.typee.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import com.typee.logic.commands.exceptions.CommandException;
import com.typee.model.Model;
import com.typee.model.engagement.Engagement;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an engagement to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe ";

    public static final String MESSAGE_SUCCESS = "New engagement added: %1$s";
    public static final String MESSAGE_CONFLICTING_ENGAGEMENT = "This engagement clashes with an already existing one";

    private final Engagement toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Engagement engagement) {
        requireNonNull(engagement);
        toAdd = engagement;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasEngagement(toAdd)) {
            throw new CommandException(MESSAGE_CONFLICTING_ENGAGEMENT);
        }

<<<<<<< HEAD
        model.addEngagement(toAdd);
=======
        model.addPerson(toAdd);
        model.saveAppointmentList();
>>>>>>> 95a35944eea68dec37a5a185a16f207c3884228f
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
