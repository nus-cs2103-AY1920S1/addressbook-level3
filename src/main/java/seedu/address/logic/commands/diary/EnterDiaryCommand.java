package seedu.address.logic.commands.diary;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.diary.DiaryEntry;

/**
 * Command that enters the diary page of the current {@link seedu.address.model.trip.Trip}.
 */
public class EnterDiaryCommand extends Command {
    public static final String COMMAND_WORD = "diary";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enters the diary page\n";

    private static final String MESSAGE_ENTER_DIARY_SUCCESS = " Welcome to your diary!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Optional<DiaryEntry> firstDiaryEntry = model.getPageStatus().getCurrentTripDiary().getFirstDiaryEntry();

        model.setPageStatus(model.getPageStatus()
                .withNewPageType(PageType.DIARY)
                .withNewDiaryEntry(firstDiaryEntry.orElse(null))
                .withNewEditDiaryEntryDescriptor(null));

        return new CommandResult(MESSAGE_ENTER_DIARY_SUCCESS, true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof EnterDiaryCommand;
    }
}
