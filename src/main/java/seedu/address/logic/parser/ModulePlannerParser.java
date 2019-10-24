package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.cli.AddModuleCommand;
import seedu.address.logic.commands.cli.BlockCurrentSemesterCommand;
import seedu.address.logic.commands.cli.DeleteModuleCommand;
import seedu.address.logic.commands.cli.NameUeFromSemesterCommand;
import seedu.address.logic.commands.cli.SetCurrentSemesterCommand;
import seedu.address.logic.commands.datamanagement.DeleteTagCommand;
import seedu.address.logic.commands.datamanagement.FindCommand;
import seedu.address.logic.commands.datamanagement.RemoveAllTagsCommand;
import seedu.address.logic.commands.datamanagement.RemoveTagFromAllCommand;
import seedu.address.logic.commands.datamanagement.RemoveTagFromModuleCommand;
import seedu.address.logic.commands.datamanagement.TagModuleCommand;
import seedu.address.logic.commands.datamanagement.ViewAllTagsCommand;
import seedu.address.logic.commands.datamanagement.ViewDefaultTagsCommand;
import seedu.address.logic.commands.datamanagement.ViewModuleTagsCommand;
import seedu.address.logic.commands.datamanagement.ViewTaggedCommand;
import seedu.address.logic.commands.gui.HelpCommand;
import seedu.address.logic.commands.storage.ActivateStudyPlanCommand;
import seedu.address.logic.commands.storage.CommitStudyPlanEditCommand;
import seedu.address.logic.commands.storage.CreateStudyPlanCommand;
import seedu.address.logic.commands.storage.DefaultStudyPlanCommand;
import seedu.address.logic.commands.storage.DeleteCommand;
import seedu.address.logic.commands.storage.DeleteCommitCommand;
import seedu.address.logic.commands.storage.DeleteSemesterCommand;
import seedu.address.logic.commands.storage.EditTitleCommand;
import seedu.address.logic.commands.storage.ListAllStudyPlansCommand;
import seedu.address.logic.commands.storage.RevertCommitCommand;
import seedu.address.logic.commands.storage.ViewCommitCommand;
import seedu.address.logic.commands.storage.ViewCommitHistoryCommand;
import seedu.address.logic.commands.verification.DescriptionCommand;
import seedu.address.logic.commands.verification.ValidModsCommand;
import seedu.address.logic.parser.cli.AddModuleParser;
import seedu.address.logic.parser.cli.BlockCurrentSemesterParser;
import seedu.address.logic.parser.cli.DeleteModuleFromSemesterParser;
import seedu.address.logic.parser.cli.NameUeFromSemesterParser;
import seedu.address.logic.parser.cli.SetCurrentSemesterParser;
import seedu.address.logic.parser.datamanagement.DeleteTagCommandParser;
import seedu.address.logic.parser.datamanagement.FindCommandParser;
import seedu.address.logic.parser.datamanagement.RemoveTagFromAllCommandParser;
import seedu.address.logic.parser.datamanagement.RemoveTagFromModuleCommandParser;
import seedu.address.logic.parser.datamanagement.TagModuleCommandParser;
import seedu.address.logic.parser.datamanagement.ViewModuleTagsCommandParser;
import seedu.address.logic.parser.datamanagement.ViewTaggedCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.storage.ActivateStudyPlanParser;
import seedu.address.logic.parser.storage.CommitStudyPlanEditsParser;
import seedu.address.logic.parser.storage.CreateStudyPlanCommandParser;
import seedu.address.logic.parser.storage.DefaultStudyPlanCommandParser;
import seedu.address.logic.parser.storage.DeleteCommitCommandParser;
import seedu.address.logic.parser.storage.DeleteSemesterCommandParser;
import seedu.address.logic.parser.storage.DeleteStudyPlanParser;
import seedu.address.logic.parser.storage.EditStudyPlanTitleParser;
import seedu.address.logic.parser.storage.ListAllStudyPlansParser;
import seedu.address.logic.parser.storage.RevertCommitParser;
import seedu.address.logic.parser.storage.ViewCommitCommandParser;
import seedu.address.logic.parser.storage.ViewCommitHistoryParser;
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

        case NameUeFromSemesterCommand.COMMAND_WORD:
            return new NameUeFromSemesterParser().parse(arguments);

        case SetCurrentSemesterCommand.COMMAND_WORD:
            return new SetCurrentSemesterParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case DescriptionCommand.COMMAND_WORD:
            return new DescriptionCommandParser().parse(arguments);

        case ValidModsCommand.COMMAND_WORD:
            return new ValidModsCommandParser().parse(arguments);

        case CommitStudyPlanEditCommand.COMMAND_WORD:
            return new CommitStudyPlanEditsParser().parse(arguments);

        case CreateStudyPlanCommand.COMMAND_WORD:
            return new CreateStudyPlanCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteStudyPlanParser().parse(arguments);

        case TagModuleCommand.COMMAND_WORD:
            return new TagModuleCommandParser().parse(arguments);

        case ViewCommitHistoryCommand.COMMAND_WORD:
            return new ViewCommitHistoryParser().parse(arguments);

        case RemoveTagFromModuleCommand.COMMAND_WORD:
            return new RemoveTagFromModuleCommandParser().parse(arguments);

        case ViewModuleTagsCommand.COMMAND_WORD:
            return new ViewModuleTagsCommandParser().parse(arguments);

        case DeleteTagCommand.COMMAND_WORD:
            return new DeleteTagCommandParser().parse(arguments);

        case RemoveTagFromAllCommand.COMMAND_WORD:
            return new RemoveTagFromAllCommandParser().parse(arguments);

        case EditTitleCommand.COMMAND_WORD:
            return new EditStudyPlanTitleParser().parse(arguments);

        case ActivateStudyPlanCommand.COMMAND_WORD:
            return new ActivateStudyPlanParser().parse(arguments);

        case ListAllStudyPlansCommand.COMMAND_WORD:
            return new ListAllStudyPlansParser().parse(arguments);

        case RevertCommitCommand.COMMAND_WORD:
            return new RevertCommitParser().parse(arguments);

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ViewDefaultTagsCommand.COMMAND_WORD:
            return new ViewDefaultTagsCommand();

        case ViewTaggedCommand.COMMAND_WORD:
            return new ViewTaggedCommandParser().parse(arguments);

        case ViewAllTagsCommand.COMMAND_WORD:
            return new ViewAllTagsCommand();

        case RemoveAllTagsCommand.COMMAND_WORD:
            return new RemoveAllTagsCommand();

        case DeleteCommitCommand.COMMAND_WORD:
            return new DeleteCommitCommandParser().parse(arguments);

        case DefaultStudyPlanCommand.COMMAND_WORD:
            return new DefaultStudyPlanCommandParser().parse(arguments);

        case DeleteSemesterCommand.COMMAND_WORD:
            return new DeleteSemesterCommandParser().parse(arguments);

        case ViewCommitCommand.COMMAND_WORD:
            return new ViewCommitCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);

        }
    }

}
