package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.verification.CheckCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.storage.CreateStudyPlanCommand;
import seedu.address.logic.commands.datamanagement.FindCommand;
import seedu.address.logic.commands.gui.HelpCommand;
import seedu.address.logic.parser.cli.AddModuleParser;
import seedu.address.logic.parser.cli.BlockCurrentSemesterParser;
import seedu.address.logic.parser.cli.DeleteCommandParser;
import seedu.address.logic.parser.cli.DeleteModuleFromSemesterParser;
import seedu.address.logic.parser.cli.NameUEFromSemesterParser;
import seedu.address.logic.parser.cli.SetCurrentSemesterParser;
import seedu.address.logic.parser.datamanagement.FindCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.gui.CollapseSemesterParser;
import seedu.address.logic.parser.gui.SpecificHelpCommandParser;
import seedu.address.logic.parser.storage.ActivateStudyPlanParser;
import seedu.address.logic.parser.storage.CommitStudyPlanEditsParser;
import seedu.address.logic.parser.storage.CreateStudyPlanCommandParser;
import seedu.address.logic.parser.storage.DeleteSemesterFromStudyPlanParser;
import seedu.address.logic.parser.storage.DeleteStudyPlanParser;
import seedu.address.logic.parser.storage.EditStudyPlanTitleParser;
import seedu.address.logic.parser.storage.ExpandSemesterParser;
import seedu.address.logic.parser.storage.MoveSemesterAcrossStudyPlansCommandParser;
import seedu.address.logic.parser.storage.RevertCommitParser;
import seedu.address.logic.parser.storage.ViewCommitParser;
import seedu.address.logic.parser.storage.ViewDescriptionParser;
import seedu.address.logic.parser.storage.ViewStudyPlanParser;
import seedu.address.logic.parser.verification.CheckCommandParser;

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

        case DeleteCommandCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case DeleteModuleFromSemesterCommand.COMMAND_WORD:
            return new DeleteModuleFromSemesterParser().parse(arguments);

        case NameUEFromSemesterCommand.COMMAND_WORD:
            return new NameUEFromSemesterParser().parse(arguments);

        case SetCurrentSemesterCommand.COMMAND_WORD:
            return new SetCurrentSemesterParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case CollapseSemesterCommand.COMMAND_WORD:
            return new CollapseSemesterParser().parse(arguments);

        case ExpandSemesterCommand.COMMAND_WORD:
            return new ExpandSemesterParser().parse(arguments);

        case SpecificHelpCommand.COMMAND_WORD:
            return new SpecificHelpCommandParser().parse(arguments);

        case ActivateStudyPlanCommand.COMMAND_WORD:
            return new ActivateStudyPlanParser().parse(arguments);

        case CommitStudyPlanEditsCommand.COMMAND_WORD:
            return new CommitStudyPlanEditsParser().parse(arguments);

        case CreateStudyPlanCommand.COMMAND_WORD:
            return new CreateStudyPlanCommandParser().parse(arguments);

        case DeleteSemesterFromStudyPlanCommand.COMMAND_WORD:
            return new DeleteSemesterFromStudyPlanParser().parse(arguments);

        case DeleteStudyPlanCommand.COMMAND_WORD:
            return new DeleteStudyPlanParser().parse(arguments);

        case EditStudyPlanTitleCommand.COMMAND_WORD:
            return new EditStudyPlanTitleParser().parse(arguments);

        case MoveSemesterAcrossStudyPlansCommand.COMMAND_WORD:
            return new MoveSemesterAcrossStudyPlansCommandParser().parse(arguments);

        case RevertCommitCommand.COMMAND_WORD:
            return new RevertCommitParser().parse(arguments);

        case ViewCommitCommand.COMMAND_WORD:
            return new ViewCommitParser().parse(arguments);

        case ViewDescriptionCommand.COMMAND_WORD:
            return new ViewDescriptionParser().parse(arguments);

        case ViewStudyPlanCommand.COMMAND_WORD:
            return new ViewStudyPlanParser().parse(arguments);

        case CheckCommand.COMMAND_WORD:
            return new CheckCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
