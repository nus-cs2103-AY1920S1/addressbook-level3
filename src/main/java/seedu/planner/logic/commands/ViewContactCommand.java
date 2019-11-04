package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.planner.commons.core.Messages;
import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.commands.result.ResultInformation;
import seedu.planner.logic.commands.result.UiFocus;
import seedu.planner.logic.commands.util.HelpExplanation;
import seedu.planner.model.Model;
import seedu.planner.model.contact.Contact;

/**
 * Views the specified contact.
 */
public class ViewContactCommand extends ViewCommand {

    public static final String SECOND_COMMAND_WORD = "contact";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            "Opens the specified contact on the info tab or "
                    + "simply opens the contact side panel",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD + " INDEX",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD + " 2"
    );

    public static final String MESSAGE_SUCCESS = "Opened the contact tab!";

    private final Index index;

    /**
     * Creates an ViewContactCommand to view the specific contact of {@code index}
     */
    public ViewContactCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Contact> lastShownList = model.getFilteredContactList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
        }

        return new CommandResult(MESSAGE_SUCCESS,
                new ResultInformation[]{
                    new ResultInformation(
                        lastShownList.get(index.getZeroBased()),
                        index,
                        ""
                    )
                },
                new UiFocus[] {UiFocus.CONTACT, UiFocus.INFO});
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ViewContactCommand);
    }
}
