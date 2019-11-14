package seedu.address.logic.commands.diary;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.diary.DiaryEntry;

/**
 * Command that flips the page of the diary to the {@link DiaryEntry} with the specified day index.
 */
public class FlipDiaryCommand extends Command {

    public static final String COMMAND_WORD = "flip";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Flips your diary to the specified day entry\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1";

    private static final String MESSAGE_FLIP_PAGE_SUCCESS = "Flipped the current diary page!\n:%1$s";

    private final Index indexToFlipTo;

    public FlipDiaryCommand(Index indexToFlipTo) {
        this.indexToFlipTo = indexToFlipTo;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Optional<DiaryEntry> diaryEntry = model.getPageStatus().getCurrentTripDiary().getDiaryEntry(indexToFlipTo);

        if (diaryEntry.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GENERIC_INDEX);
        }

        model.setPageStatus(model.getPageStatus()
                .withNewDiaryEntry(diaryEntry.get())
                .withNewEditDiaryEntryDescriptor(null));

        return new CommandResult(String.format(MESSAGE_FLIP_PAGE_SUCCESS, diaryEntry.get()));
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj
                || (obj instanceof FlipDiaryCommand
                            && this.indexToFlipTo.equals(((FlipDiaryCommand) obj).indexToFlipTo));
    }
}
