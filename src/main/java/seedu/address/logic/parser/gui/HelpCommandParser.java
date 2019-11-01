package seedu.address.logic.parser.gui;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.cli.AddModuleCommand;
import seedu.address.logic.commands.cli.BlockCurrentSemesterCommand;
import seedu.address.logic.commands.cli.DeleteModuleCommand;
import seedu.address.logic.commands.cli.RedoCommand;
import seedu.address.logic.commands.cli.SetCurrentSemesterCommand;
import seedu.address.logic.commands.cli.UnblockCurrentSemesterCommand;
import seedu.address.logic.commands.cli.UndoCommand;
import seedu.address.logic.commands.datamanagement.DeleteTagCommand;
import seedu.address.logic.commands.datamanagement.FindModuleCommand;
import seedu.address.logic.commands.datamanagement.RemoveAllTagsCommand;
import seedu.address.logic.commands.datamanagement.RemoveTagFromAllCommand;
import seedu.address.logic.commands.datamanagement.RemoveTagFromModuleCommand;
import seedu.address.logic.commands.datamanagement.RemoveTagFromStudyPlanCommand;
import seedu.address.logic.commands.datamanagement.RenameTagCommand;
import seedu.address.logic.commands.datamanagement.SortStudyPlansByPriorityTagCommand;
import seedu.address.logic.commands.datamanagement.TagModuleCommand;
import seedu.address.logic.commands.datamanagement.TagStudyPlanCommand;
import seedu.address.logic.commands.datamanagement.ViewAllTagsCommand;
import seedu.address.logic.commands.datamanagement.ViewDefaultTagsCommand;
import seedu.address.logic.commands.datamanagement.ViewModuleTagsCommand;
import seedu.address.logic.commands.datamanagement.ViewTaggedCommand;
import seedu.address.logic.commands.gui.HelpCommand;
import seedu.address.logic.commands.storage.ActivateStudyPlanCommand;
import seedu.address.logic.commands.storage.CommitStudyPlanCommand;
import seedu.address.logic.commands.storage.CreateStudyPlanCommand;
import seedu.address.logic.commands.storage.DefaultStudyPlanCommand;
import seedu.address.logic.commands.storage.DeleteCommitCommand;
import seedu.address.logic.commands.storage.DeleteSemesterCommand;
import seedu.address.logic.commands.storage.DeleteStudyPlanCommand;
import seedu.address.logic.commands.storage.EditTitleCommand;
import seedu.address.logic.commands.storage.ListAllStudyPlansCommand;
import seedu.address.logic.commands.storage.RevertCommitCommand;
import seedu.address.logic.commands.storage.ViewCommitCommand;
import seedu.address.logic.commands.storage.ViewCommitHistoryCommand;
import seedu.address.logic.commands.verification.CheckCommand;
import seedu.address.logic.commands.verification.ClearInvalidModsCommand;
import seedu.address.logic.commands.verification.DescriptionCommand;
import seedu.address.logic.commands.verification.ValidModsCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new HelpCommand object
 */
public class HelpCommandParser implements Parser<HelpCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * HelpCommand and returns a HelpCommand object for
     * execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public HelpCommand parse(String args) throws ParseException {
        String commandName = args.trim();
        if (commandName.isEmpty()) {
            return new HelpCommand();
        }
        switch (commandName) {
        case AddModuleCommand.COMMAND_WORD:
        case BlockCurrentSemesterCommand.COMMAND_WORD:
        case DeleteModuleCommand.COMMAND_WORD:
        case UnblockCurrentSemesterCommand.COMMAND_WORD:
        case SetCurrentSemesterCommand.COMMAND_WORD:
        case FindModuleCommand.COMMAND_WORD:
        case DescriptionCommand.COMMAND_WORD:
        case CheckCommand.COMMAND_WORD:
        case ValidModsCommand.COMMAND_WORD:
        case CommitStudyPlanCommand.COMMAND_WORD:
        case CreateStudyPlanCommand.COMMAND_WORD:
        case DeleteStudyPlanCommand.COMMAND_WORD:
        case TagModuleCommand.COMMAND_WORD:
        case ViewCommitHistoryCommand.COMMAND_WORD:
        case RemoveTagFromModuleCommand.COMMAND_WORD:
        case ViewModuleTagsCommand.COMMAND_WORD:
        case DeleteTagCommand.COMMAND_WORD:
        case RemoveTagFromAllCommand.COMMAND_WORD:
        case EditTitleCommand.COMMAND_WORD:
        case ActivateStudyPlanCommand.COMMAND_WORD:
        case ListAllStudyPlansCommand.COMMAND_WORD:
        case RevertCommitCommand.COMMAND_WORD:
        case RedoCommand.COMMAND_WORD:
        case UndoCommand.COMMAND_WORD:
        case HelpCommand.COMMAND_WORD:
        case ViewDefaultTagsCommand.COMMAND_WORD:
        case ViewTaggedCommand.COMMAND_WORD:
        case ViewAllTagsCommand.COMMAND_WORD:
        case RemoveAllTagsCommand.COMMAND_WORD:
        case DeleteCommitCommand.COMMAND_WORD:
        case DefaultStudyPlanCommand.COMMAND_WORD:
        case DeleteSemesterCommand.COMMAND_WORD:
        case ViewCommitCommand.COMMAND_WORD:
        case RenameTagCommand.COMMAND_WORD:
        case TagStudyPlanCommand.COMMAND_WORD:
        case RemoveTagFromStudyPlanCommand.COMMAND_WORD:
        case SortStudyPlansByPriorityTagCommand.COMMAND_WORD:
        case ClearInvalidModsCommand.COMMAND_WORD:
            return new HelpCommand(commandName);
        default:
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
    }

}
