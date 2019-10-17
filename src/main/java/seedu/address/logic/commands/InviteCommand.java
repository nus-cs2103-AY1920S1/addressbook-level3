package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class InviteCommand extends Command {

    public static final String COMMAND_WORD = "invite";


    private final List<String> participants;

    public InviteCommand(List<String> participants) {
        requireAllNonNull(participants);
        this.participants = participants;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);


    }

}
