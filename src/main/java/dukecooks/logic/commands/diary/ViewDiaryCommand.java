package dukecooks.logic.commands.diary;

import static dukecooks.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import dukecooks.commons.core.Event;
import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.ViewCommand;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.Model;
import dukecooks.model.diary.components.Diary;

/**
 * Views a diary in DukeCooks.
 */
public class ViewDiaryCommand extends ViewCommand {

    public static final String VARIANT_WORD = "diary";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a page to a specific diary "
            + "Parameters: "
            + "1";

    public static final String MESSAGE_SUCCESS = "You are viewing diary : %1$s";

    private static Event event;

    private final Index targetIndex;

    /**
     * Creates an ViewDiaryCommand to view the specified Diary
     */
    public ViewDiaryCommand(Index targetIndex) {
        requireAllNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);
        List<Diary> lastShownList = model.getFilteredDiaryList();

        int index = targetIndex.getZeroBased();

        // check if index is out of bounds
        if (index >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DIARY_DISPLAYED_INDEX);
        }

        String type = "update-" + index;

        // Set your events here
        event = Event.getInstance();
        event.set("diary", type);

        return new CommandResult(String.format(MESSAGE_SUCCESS, lastShownList.get(index).getDiaryName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewDiaryCommand // instanceof handles nulls
                && targetIndex.equals(((ViewDiaryCommand) other).targetIndex));
    }
}
