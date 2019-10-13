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

public class EditDiaryCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the current diary entry\n";

    public static final String MESSAGE_FLIP_PAGE_SUCCESS = "Brought up the edit window! :%1$s";

    public EditDiaryCommand() { }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Optional<DiaryEntry> diaryEntry = Optional.ofNullable(model.getPageStatus().getDiaryEntry());

        if (!diaryEntry.isPresent()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GENERIC_INDEX);
        }

        model.setPageStatus(model.getPageStatus()
                .withNewDiaryEntry(diaryEntry.get()));

        return new CommandResult(String.format(MESSAGE_FLIP_PAGE_SUCCESS, diaryEntry.get()));
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj
                || obj instanceof EditDiaryCommand;
    }
}
