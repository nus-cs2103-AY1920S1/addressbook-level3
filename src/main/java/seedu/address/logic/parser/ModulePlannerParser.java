//@@author andyylam

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
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
import seedu.address.logic.commands.datamanagement.RemoveTagFromModuleCommand;
import seedu.address.logic.commands.datamanagement.RemoveTagFromStudyPlanCommand;
import seedu.address.logic.commands.datamanagement.RenameTagCommand;
import seedu.address.logic.commands.datamanagement.ShowCoreCommand;
import seedu.address.logic.commands.datamanagement.ShowFocusCommand;
import seedu.address.logic.commands.datamanagement.SortStudyPlansByPriorityTagCommand;
import seedu.address.logic.commands.datamanagement.TagModuleCommand;
import seedu.address.logic.commands.datamanagement.TagStudyPlanCommand;
import seedu.address.logic.commands.datamanagement.ViewAllTagsCommand;
import seedu.address.logic.commands.datamanagement.ViewDefaultTagsCommand;
import seedu.address.logic.commands.datamanagement.ViewModuleTagsCommand;
import seedu.address.logic.commands.datamanagement.ViewTaggedCommand;
import seedu.address.logic.commands.gui.ChangeThemeCommand;
import seedu.address.logic.commands.gui.CollapseAllCommand;
import seedu.address.logic.commands.gui.CollapseCommand;
import seedu.address.logic.commands.gui.ExpandAllCommand;
import seedu.address.logic.commands.gui.ExpandCommand;
import seedu.address.logic.commands.gui.HelpCommand;
import seedu.address.logic.commands.storage.ActivateStudyPlanCommand;
import seedu.address.logic.commands.storage.AddSemesterCommand;
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
import seedu.address.logic.commands.storage.ViewStudyPlanCommand;
import seedu.address.logic.commands.verification.CheckCommand;
import seedu.address.logic.commands.verification.ClearInvalidModsCommand;
import seedu.address.logic.commands.verification.DescriptionCommand;
import seedu.address.logic.commands.verification.ValidModsCommand;
import seedu.address.logic.parser.cli.AddModuleParser;
import seedu.address.logic.parser.cli.BlockCurrentSemesterParser;
import seedu.address.logic.parser.cli.DeleteModuleFromSemesterParser;
import seedu.address.logic.parser.cli.SetCurrentSemesterParser;
import seedu.address.logic.parser.cli.UnblockCurrentSemesterCommandParser;
import seedu.address.logic.parser.datamanagement.DeleteTagCommandParser;
import seedu.address.logic.parser.datamanagement.FindModuleCommandParser;
import seedu.address.logic.parser.datamanagement.RemoveTagFromModuleCommandParser;
import seedu.address.logic.parser.datamanagement.RemoveTagFromStudyPlanCommandParser;
import seedu.address.logic.parser.datamanagement.RenameTagCommandParser;
import seedu.address.logic.parser.datamanagement.TagModuleCommandParser;
import seedu.address.logic.parser.datamanagement.TagStudyPlanCommandParser;
import seedu.address.logic.parser.datamanagement.ViewModuleTagsCommandParser;
import seedu.address.logic.parser.datamanagement.ViewTaggedCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.gui.ChangeThemeCommandParser;
import seedu.address.logic.parser.gui.CollapseCommandParser;
import seedu.address.logic.parser.gui.ExpandCommandParser;
import seedu.address.logic.parser.gui.HelpCommandParser;
import seedu.address.logic.parser.storage.ActivateStudyPlanCommandParser;
import seedu.address.logic.parser.storage.AddSemesterCommandParser;
import seedu.address.logic.parser.storage.CommitStudyPlanCommandParser;
import seedu.address.logic.parser.storage.CreateStudyPlanCommandParser;
import seedu.address.logic.parser.storage.DeleteCommitCommandParser;
import seedu.address.logic.parser.storage.DeleteSemesterCommandParser;
import seedu.address.logic.parser.storage.DeleteStudyPlanCommandParser;
import seedu.address.logic.parser.storage.EditTitleCommandParser;
import seedu.address.logic.parser.storage.ListAllStudyPlansCommandParser;
import seedu.address.logic.parser.storage.RevertCommitCommandParser;
import seedu.address.logic.parser.storage.ViewCommitCommandParser;
import seedu.address.logic.parser.storage.ViewStudyPlanCommandParser;
import seedu.address.logic.parser.verification.CheckCommandParser;
import seedu.address.logic.parser.verification.DescriptionCommandParser;
import seedu.address.logic.parser.verification.ValidModsCommandParser;

/**
 * Parses user input.
 */
public class ModulePlannerParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {

        case AddModuleCommand.COMMAND_WORD:
            return new AddModuleParser().parse(arguments);

        case BlockCurrentSemesterCommand.COMMAND_WORD:
            return new BlockCurrentSemesterParser().parse(arguments);

        case DeleteModuleCommand.COMMAND_WORD:
            return new DeleteModuleFromSemesterParser().parse(arguments);

        case UnblockCurrentSemesterCommand.COMMAND_WORD:
            return new UnblockCurrentSemesterCommandParser().parse(arguments);

        case SetCurrentSemesterCommand.COMMAND_WORD:
            return new SetCurrentSemesterParser().parse(arguments);

        case FindModuleCommand.COMMAND_WORD:
            return new FindModuleCommandParser().parse(arguments);

        case DescriptionCommand.COMMAND_WORD:
            return new DescriptionCommandParser().parse(arguments);

        case ValidModsCommand.COMMAND_WORD:
            return new ValidModsCommandParser().parse(arguments);

        case CommitStudyPlanCommand.COMMAND_WORD:
            return new CommitStudyPlanCommandParser().parse(arguments);

        case CheckCommand.COMMAND_WORD:
            return new CheckCommandParser().parse(arguments);

        case ClearInvalidModsCommand.COMMAND_WORD:
            return new ClearInvalidModsCommand();

        case CreateStudyPlanCommand.COMMAND_WORD:
            return new CreateStudyPlanCommandParser().parse(arguments);

        case DeleteStudyPlanCommand.COMMAND_WORD:
            return new DeleteStudyPlanCommandParser().parse(arguments);

        case TagModuleCommand.COMMAND_WORD:
            return new TagModuleCommandParser().parse(arguments);

        case ViewCommitHistoryCommand.COMMAND_WORD:
            return new ViewCommitHistoryCommand();

        case RemoveTagFromModuleCommand.COMMAND_WORD:
            return new RemoveTagFromModuleCommandParser().parse(arguments);

        case ViewModuleTagsCommand.COMMAND_WORD:
            return new ViewModuleTagsCommandParser().parse(arguments);

        case DeleteTagCommand.COMMAND_WORD:
            return new DeleteTagCommandParser().parse(arguments);

        case EditTitleCommand.COMMAND_WORD:
            return new EditTitleCommandParser().parse(arguments);

        case ActivateStudyPlanCommand.COMMAND_WORD:
            return new ActivateStudyPlanCommandParser().parse(arguments);

        case ListAllStudyPlansCommand.COMMAND_WORD:
            return new ListAllStudyPlansCommandParser().parse(arguments);

        case RevertCommitCommand.COMMAND_WORD:
            return new RevertCommitCommandParser().parse(arguments);

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommandParser().parse(arguments);

        case ViewDefaultTagsCommand.COMMAND_WORD:
            return new ViewDefaultTagsCommand();

        case ViewTaggedCommand.COMMAND_WORD:
            return new ViewTaggedCommandParser().parse(arguments);

        case ViewAllTagsCommand.COMMAND_WORD:
            return new ViewAllTagsCommand();

        case RemoveAllTagsCommand.COMMAND_WORD:
            return new RemoveAllTagsCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case DeleteCommitCommand.COMMAND_WORD:
            return new DeleteCommitCommandParser().parse(arguments);

        case DefaultStudyPlanCommand.COMMAND_WORD:
            return new DefaultStudyPlanCommand();

        case DeleteSemesterCommand.COMMAND_WORD:
            return new DeleteSemesterCommandParser().parse(arguments);

        case ViewCommitCommand.COMMAND_WORD:
            return new ViewCommitCommandParser().parse(arguments);

        case RenameTagCommand.COMMAND_WORD:
            return new RenameTagCommandParser().parse(arguments);

        case SortStudyPlansByPriorityTagCommand.COMMAND_WORD:
            return new SortStudyPlansByPriorityTagCommand();

        case RemoveTagFromStudyPlanCommand.COMMAND_WORD:
            return new RemoveTagFromStudyPlanCommandParser().parse(arguments);

        case TagStudyPlanCommand.COMMAND_WORD:
            return new TagStudyPlanCommandParser().parse(arguments);

        case ViewStudyPlanCommand.COMMAND_WORD:
            return new ViewStudyPlanCommandParser().parse(arguments);

        case AddSemesterCommand.COMMAND_WORD:
            return new AddSemesterCommandParser().parse(arguments);

        case ExpandCommand.COMMAND_WORD:
            return new ExpandCommandParser().parse(arguments);

        case CollapseCommand.COMMAND_WORD:
            return new CollapseCommandParser().parse(arguments);

        case ExpandAllCommand.COMMAND_WORD:
            return new ExpandAllCommand();

        case CollapseAllCommand.COMMAND_WORD:
            return new CollapseAllCommand();

        case ChangeThemeCommand.COMMAND_WORD:
            return new ChangeThemeCommandParser().parse(arguments);

        case ShowCoreCommand.COMMAND_WORD:
            return new ShowCoreCommand();

        case ShowFocusCommand.COMMAND_WORD:
            return new ShowFocusCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);

        }
    }

}
