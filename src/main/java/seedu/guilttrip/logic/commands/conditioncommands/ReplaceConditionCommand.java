package seedu.guilttrip.logic.commands.conditioncommands;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.guilttrip.commons.core.Messages;
import seedu.guilttrip.commons.core.index.Index;
import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.reminders.conditions.Condition;

/**
 * Replaces a condition, or edits some details.
 */
public class ReplaceConditionCommand extends Command {

    public static final String COMMAND_WORD = "replaceCondition";

    public static final String ONE_LINER_DESC = COMMAND_WORD + ": Edits the details of the Condition identified "
            + "by the index number used in the displayed Conditions list. ";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DESC + "CONDITION_TYPE] "
            + "[" + PREFIX_DATE + "TIME] "
            + "[" + PREFIX_AMOUNT + "AMOUNT] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_AMOUNT + "5.60";

    public static final String MESSAGE_EDIT_ENTRY_SUCCESS = "Condition replaced";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ENTRY = "This entry already exists in the guilttrip book.";
    private final Index indexToReplace;
    private final Index replacingIndex;
    public ReplaceConditionCommand(Index indexToReplace, Index replacingIndex) {
        requireNonNull(indexToReplace);
        requireNonNull(replacingIndex);
        this.indexToReplace = indexToReplace;
        this.replacingIndex = replacingIndex;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Condition> lastShownList = model.getFilteredConditions();
        if (indexToReplace.getZeroBased() >= lastShownList.size()
                || replacingIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
        }
        if (indexToReplace.equals(replacingIndex)) {
            throw new CommandException(MESSAGE_DUPLICATE_ENTRY);
        }
        Condition conditionToReplace = lastShownList.get(indexToReplace.getZeroBased());
        Condition replacingCondition = lastShownList.get(replacingIndex.getZeroBased());
        model.setCondition(conditionToReplace, replacingCondition);
        return new CommandResult(String.format(MESSAGE_EDIT_ENTRY_SUCCESS));
    }
}
