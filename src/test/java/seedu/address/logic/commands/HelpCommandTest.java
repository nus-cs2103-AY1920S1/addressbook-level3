package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.gui.HelpCommand.SHOWING_HELP_MESSAGE;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.cli.AddModuleCommand;
import seedu.address.logic.commands.cli.BlockCurrentSemesterCommand;
import seedu.address.logic.commands.cli.DeleteModuleCommand;
import seedu.address.logic.commands.cli.RedoCommand;
import seedu.address.logic.commands.cli.SetCurrentSemesterCommand;
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
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalModulesInfo;
import seedu.address.testutil.TypicalStudyPlans;

public class HelpCommandTest {
    private static final String NON_EXISTENT_COMMAND_NAME = "notexistingcommandname";

    private Model model = new ModelManager(TypicalStudyPlans.getTypicalModulePlanner(), new UserPrefs(),
            TypicalModulesInfo.getTypicalModulesInfo());

    @Test
    public void execute_helpNoCommand_success() throws CommandException {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, false, false);
        assertEquals(new HelpCommand().execute(model), expectedCommandResult);
    }

    @Test
    public void execute_helpWithCommand_success() throws CommandException {
        CommandResult expectedCommandResult = new CommandResult(AddModuleCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(AddModuleCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(BlockCurrentSemesterCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(BlockCurrentSemesterCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(DeleteModuleCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(DeleteModuleCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(RedoCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(RedoCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(SetCurrentSemesterCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(SetCurrentSemesterCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(UndoCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(UndoCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(DeleteTagCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(DeleteTagCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(FindModuleCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(FindModuleCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(RemoveAllTagsCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(RemoveAllTagsCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(RemoveTagFromAllCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(RemoveTagFromAllCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(RemoveTagFromModuleCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(RemoveTagFromModuleCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(RemoveTagFromStudyPlanCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(RemoveTagFromStudyPlanCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(RenameTagCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(RenameTagCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(SortStudyPlansByPriorityTagCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(SortStudyPlansByPriorityTagCommand.COMMAND_WORD).execute(model),
                expectedCommandResult);
        expectedCommandResult = new CommandResult(TagModuleCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(TagModuleCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(TagStudyPlanCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(TagStudyPlanCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(ViewAllTagsCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(ViewAllTagsCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(ViewDefaultTagsCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(ViewDefaultTagsCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(ViewModuleTagsCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(ViewModuleTagsCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(ViewTaggedCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(ViewTaggedCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(HelpCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(HelpCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(ActivateStudyPlanCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(ActivateStudyPlanCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(CommitStudyPlanCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(CommitStudyPlanCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(CreateStudyPlanCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(CreateStudyPlanCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(DefaultStudyPlanCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(DefaultStudyPlanCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(DeleteStudyPlanCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(DeleteStudyPlanCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(DeleteCommitCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(DeleteCommitCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(DeleteSemesterCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(DeleteSemesterCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(EditTitleCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(EditTitleCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(ListAllStudyPlansCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(ListAllStudyPlansCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(RevertCommitCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(RevertCommitCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(ViewCommitCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(ViewCommitCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(ViewCommitHistoryCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(ViewCommitHistoryCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(CheckCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(CheckCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(DescriptionCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(DescriptionCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(ValidModsCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(ValidModsCommand.COMMAND_WORD).execute(model), expectedCommandResult);
        expectedCommandResult = new CommandResult(ClearInvalidModsCommand.MESSAGE_USAGE, false, false);
        assertEquals(new HelpCommand(ClearInvalidModsCommand.COMMAND_WORD).execute(model), expectedCommandResult);
    }

    @Test
    public void execute_helpWithCommand_failure() {
        assertThrows(CommandException.class, () -> new HelpCommand(NON_EXISTENT_COMMAND_NAME).execute(model));
    }
}
