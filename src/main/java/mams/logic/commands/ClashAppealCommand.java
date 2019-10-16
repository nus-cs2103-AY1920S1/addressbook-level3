package mams.logic.commands;

import mams.logic.commands.exceptions.CommandException;
import mams.model.Model;
import mams.model.module.Module;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class ClashAppealCommand extends ClashCommand {

    private final String appeal;
    private Module requestedModule;

    public ClashAppealCommand(String appeal) {
        requireNonNull(appeal);

        this.appeal = appeal;
        super.clashingSlots = new ArrayList<>();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Module> lastShownList = model.getFilteredModuleList();

        // todo: check whether the uniquemodulelist contains the two modules.
            return new CommandResult(MESSAGE_CLASH_NOT_DETECTED);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClashAppealCommand)) {
            return false;
        }

        // state check
        ClashAppealCommand c = (ClashAppealCommand) other;
        return appeal.equals(c.appeal);
    }
}
