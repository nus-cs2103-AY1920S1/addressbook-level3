package seedu.address.logic.commands.gui;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
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
import seedu.address.logic.commands.exceptions.CommandException;
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
import seedu.address.logic.commands.verification.DescriptionCommand;
import seedu.address.logic.commands.verification.ValidModsCommand;
import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Showing help";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD + " or " + COMMAND_WORD + " COMMAND";
    public static final String AUTOCOMPLETE_MESSAGE = "Press TAB to autocomplete a command.";

    public static final String SHOWING_HELP_MESSAGE = "Type " + COMMAND_WORD + " COMMAND to find out more.\n"
            + AUTOCOMPLETE_MESSAGE + "\n\n"
            + "List of commands:\n"
            + "\nSemesters and Modules:\n"
            + AddModuleCommand.HELP_MESSAGE + "\n"
            + DeleteModuleCommand.HELP_MESSAGE + "\n"
            + SetCurrentSemesterCommand.HELP_MESSAGE + "\n"
            + BlockCurrentSemesterCommand.HELP_MESSAGE + "\n"
            + UnblockCurrentSemesterCommand.HELP_MESSAGE + "\n"
            + "NOT YET: nameue - Naming a UE from a semester\n"
            + "NOT YET: move - Moving a module from one semester to another\n"
            + "\nStudy plans and committing:\n"
            + CreateStudyPlanCommand.HELP_MESSAGE + "\n"
            + DeleteStudyPlanCommand.HELP_MESSAGE + "\n"
            + CommitStudyPlanCommand.HELP_MESSAGE + "\n"
            + ViewCommitHistoryCommand.HELP_MESSAGE + "\n"
            + DeleteCommitCommand.HELP_MESSAGE + "\n"
            + RevertCommitCommand.HELP_MESSAGE + "\n"
            + ActivateStudyPlanCommand.HELP_MESSAGE + "\n"
            + EditTitleCommand.HELP_MESSAGE + "\n"
            + ListAllStudyPlansCommand.HELP_MESSAGE + "\n"
            + "NOT YET: viewcommit - Viewing a commit\n"
            + "NOT YET: viewplan - Viewing another study plan\n"
            + "NOT YET: remove - Deleting a semester from a study plan\n"
            + "NOT YET: default - Setting default study plan\n"
            + "\nTags:\n"
            + ViewAllTagsCommand.HELP_MESSAGE + "\n"
            + ViewDefaultTagsCommand.HELP_MESSAGE + "\n"
            + TagModuleCommand.HELP_MESSAGE + "\n"
            + RemoveTagFromModuleCommand.HELP_MESSAGE + "\n"
            + RemoveTagFromAllCommand.HELP_MESSAGE + "\n"
            + RenameTagCommand.HELP_MESSAGE + "\n"
            + DeleteTagCommand.HELP_MESSAGE + "\n"
            + ViewModuleTagsCommand.HELP_MESSAGE + "\n"
            + ViewTaggedCommand.HELP_MESSAGE + "\n"
            + SortStudyPlansByPriorityTagCommand.HELP_MESSAGE + "\n"
            + TagStudyPlanCommand.HELP_MESSAGE + "\n"
            + RemoveTagFromStudyPlanCommand.HELP_MESSAGE + "\n"
            + "\nModule information:\n"
            + DescriptionCommand.HELP_MESSAGE + "\n"
            + ValidModsCommand.HELP_MESSAGE + "\n"
            + FindModuleCommand.HELP_MESSAGE + "\n"
            + CheckCommand.HELP_MESSAGE + "\n"
            + "\nGUI:\n"
            + "NOT YET: expand - Expanding a semester\n"
            + "NOT YET: collapse - Collapsing a semester\n"
            + "\nUndo/Redo:\n"
            + UndoCommand.HELP_MESSAGE + "\n"
            + RedoCommand.HELP_MESSAGE;
    private final String commandName;

    public HelpCommand() {
        this.commandName = "";
    }

    public HelpCommand(String commandName) {
        this.commandName = commandName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        switch (commandName) {
        case AddModuleCommand.COMMAND_WORD:
            return new CommandResult(AddModuleCommand.MESSAGE_USAGE, false, false);
        case BlockCurrentSemesterCommand.COMMAND_WORD:
            return new CommandResult(BlockCurrentSemesterCommand.MESSAGE_USAGE, false, false);
        case DeleteModuleCommand.COMMAND_WORD:
            return new CommandResult(DeleteModuleCommand.MESSAGE_USAGE, false, false);
        case SetCurrentSemesterCommand.COMMAND_WORD:
            return new CommandResult(SetCurrentSemesterCommand.MESSAGE_USAGE, false, false);
        case FindModuleCommand.COMMAND_WORD:
            return new CommandResult(FindModuleCommand.MESSAGE_USAGE, false, false);
        case DescriptionCommand.COMMAND_WORD:
            return new CommandResult(DescriptionCommand.MESSAGE_USAGE, false, false);
        case CheckCommand.COMMAND_WORD:
            return new CommandResult(CheckCommand.MESSAGE_USAGE, false, false);
        case ValidModsCommand.COMMAND_WORD:
            return new CommandResult(ValidModsCommand.MESSAGE_USAGE, false, false);
        case CommitStudyPlanCommand.COMMAND_WORD:
            return new CommandResult(CommitStudyPlanCommand.MESSAGE_USAGE, false, false);
        case CreateStudyPlanCommand.COMMAND_WORD:
            return new CommandResult(CreateStudyPlanCommand.MESSAGE_USAGE, false, false);
        case DeleteStudyPlanCommand.COMMAND_WORD:
            return new CommandResult(DeleteStudyPlanCommand.MESSAGE_USAGE, false, false);
        case TagModuleCommand.COMMAND_WORD:
            return new CommandResult(TagModuleCommand.MESSAGE_USAGE, false, false);
        case ViewCommitHistoryCommand.COMMAND_WORD:
            return new CommandResult(ViewCommitHistoryCommand.MESSAGE_USAGE, false, false);
        case RemoveTagFromModuleCommand.COMMAND_WORD:
            return new CommandResult(RemoveTagFromModuleCommand.MESSAGE_USAGE, false, false);
        case ViewModuleTagsCommand.COMMAND_WORD:
            return new CommandResult(ViewModuleTagsCommand.MESSAGE_USAGE, false, false);
        case DeleteTagCommand.COMMAND_WORD:
            return new CommandResult(DeleteTagCommand.MESSAGE_USAGE, false, false);
        case RemoveTagFromAllCommand.COMMAND_WORD:
            return new CommandResult(RemoveTagFromAllCommand.MESSAGE_USAGE, false, false);
        case EditTitleCommand.COMMAND_WORD:
            return new CommandResult(EditTitleCommand.MESSAGE_USAGE, false, false);
        case ActivateStudyPlanCommand.COMMAND_WORD:
            return new CommandResult(ActivateStudyPlanCommand.MESSAGE_USAGE, false, false);
        case ListAllStudyPlansCommand.COMMAND_WORD:
            return new CommandResult(ListAllStudyPlansCommand.MESSAGE_USAGE, false, false);
        case RevertCommitCommand.COMMAND_WORD:
            return new CommandResult(RevertCommitCommand.MESSAGE_USAGE, false, false);
        case RedoCommand.COMMAND_WORD:
            return new CommandResult(RedoCommand.MESSAGE_USAGE, false, false);
        case UndoCommand.COMMAND_WORD:
            return new CommandResult(UndoCommand.MESSAGE_USAGE, false, false);
        case HelpCommand.COMMAND_WORD:
            return new CommandResult(HelpCommand.MESSAGE_USAGE, false, false);
        case ViewDefaultTagsCommand.COMMAND_WORD:
            return new CommandResult(ViewDefaultTagsCommand.MESSAGE_USAGE, false, false);
        case ViewTaggedCommand.COMMAND_WORD:
            return new CommandResult(ViewTaggedCommand.MESSAGE_USAGE, false, false);
        case ViewAllTagsCommand.COMMAND_WORD:
            return new CommandResult(ViewAllTagsCommand.MESSAGE_USAGE, false, false);
        case RemoveAllTagsCommand.COMMAND_WORD:
            return new CommandResult(RemoveAllTagsCommand.MESSAGE_USAGE, false, false);
        case DeleteCommitCommand.COMMAND_WORD:
            return new CommandResult(DeleteCommitCommand.MESSAGE_USAGE, false, false);
        case DefaultStudyPlanCommand.COMMAND_WORD:
            return new CommandResult(DefaultStudyPlanCommand.MESSAGE_USAGE, false, false);
        case DeleteSemesterCommand.COMMAND_WORD:
            return new CommandResult(DeleteSemesterCommand.MESSAGE_USAGE, false, false);
        case ViewCommitCommand.COMMAND_WORD:
            return new CommandResult(ViewCommitCommand.MESSAGE_USAGE, false, false);
        case RenameTagCommand.COMMAND_WORD:
            return new CommandResult(RenameTagCommand.MESSAGE_USAGE, false, false);
        case TagStudyPlanCommand.COMMAND_WORD:
            return new CommandResult(TagStudyPlanCommand.MESSAGE_USAGE, false, false);
        case RemoveTagFromStudyPlanCommand.COMMAND_WORD:
            return new CommandResult(RemoveTagFromStudyPlanCommand.MESSAGE_USAGE, false, false);
        case SortStudyPlansByPriorityTagCommand.COMMAND_WORD:
            return new CommandResult(SortStudyPlansByPriorityTagCommand.MESSAGE_USAGE, false, false);
        case "":
            return new CommandResult(SHOWING_HELP_MESSAGE, false, false);
        default:
            throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HelpCommand // instanceof handles nulls
                && commandName.equals(((HelpCommand) other).commandName));
    }
}
