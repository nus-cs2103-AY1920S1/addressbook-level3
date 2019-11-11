package seedu.address.logic.commands.merge;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.DuplicatePersonWithMergeException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;

/**
 * Stops a merging process in the Addressbook.
 */
public class MergeStopCommand extends Command {

    public static final String COMMAND_WORD = "stop";

    public static final String MESSAGE_MERGE_STOPPED = "Merging has been stopped.\n%1$s";

    private static final Logger logger = LogsCenter.getLogger(MergeStopCommand.class);

    private MergeCommand previousMergeCommand;
    private String mergeType;

    /**
     * Creates an MergeStopCommand to stop a merging process
     */
    public MergeStopCommand(MergeCommand previousMergeCommand, String mergeType) {
        requireNonNull(previousMergeCommand);
        requireNonNull(mergeType);
        this.previousMergeCommand = previousMergeCommand;
        this.mergeType = mergeType;
    }

    @Override
    public CommandResult execute(Model model) throws DuplicatePersonWithMergeException {
        requireNonNull(model);
        logger.info("Merge stopping...");
        if (mergeType.equals(AddressBookParser.MERGE_PERSON)) {
            assert(previousMergeCommand instanceof MergePersonCommand);
            Person currentPerson = ((MergePersonCommand) previousMergeCommand).getOriginalPerson();
            assert(currentPerson != null);
            return new CommandResult(String.format(MESSAGE_MERGE_STOPPED, currentPerson));
        } else {
            assert(previousMergeCommand instanceof MergePolicyCommand);
            Policy currentPolicy = ((MergePolicyCommand) previousMergeCommand).getOriginalPolicy();
            assert(currentPolicy != null);
            return new CommandResult(String.format(MESSAGE_MERGE_STOPPED, currentPolicy));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MergeStopCommand // instanceof handles nulls
                && previousMergeCommand.equals(((MergeStopCommand) other).previousMergeCommand)
                && mergeType.equals(((MergeStopCommand) other).mergeType));
    }
}
