package seedu.address.logic.parser.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

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
import seedu.address.logic.commands.verification.DescriptionCommand;
import seedu.address.logic.commands.verification.ValidModsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

class HelpCommandParserTest {
    private static final String NON_EXISTENT_COMMAND_NAME = "notexistingcommandname";

    @Test
    public void parse_helpNoCommand_success() throws ParseException {
        HelpCommand expectedHelpCommand = new HelpCommand();
        assertEquals(new HelpCommandParser().parse(""), expectedHelpCommand);
    }

    @Test
    public void parse_helpWithCommand_success() throws ParseException {
        HelpCommand expectedHelpCommand = new HelpCommand(AddModuleCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(AddModuleCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(BlockCurrentSemesterCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(BlockCurrentSemesterCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(DeleteModuleCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(DeleteModuleCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(RedoCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(RedoCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(SetCurrentSemesterCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(SetCurrentSemesterCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(UnblockCurrentSemesterCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(UnblockCurrentSemesterCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(UndoCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(UndoCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(DeleteTagCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(DeleteTagCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(FindModuleCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(FindModuleCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(RemoveAllTagsCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(RemoveAllTagsCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(RemoveTagFromAllCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(RemoveTagFromAllCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(RemoveTagFromModuleCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(RemoveTagFromModuleCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(RemoveTagFromStudyPlanCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(RemoveTagFromStudyPlanCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(RenameTagCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(RenameTagCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(SortStudyPlansByPriorityTagCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(SortStudyPlansByPriorityTagCommand.COMMAND_WORD),
                expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(TagModuleCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(TagModuleCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(TagStudyPlanCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(TagStudyPlanCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(ViewAllTagsCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(ViewAllTagsCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(ViewDefaultTagsCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(ViewDefaultTagsCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(ViewModuleTagsCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(ViewModuleTagsCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(ViewTaggedCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(ViewTaggedCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(HelpCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(HelpCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(ActivateStudyPlanCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(ActivateStudyPlanCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(AddSemesterCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(AddSemesterCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(CommitStudyPlanCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(CommitStudyPlanCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(CreateStudyPlanCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(CreateStudyPlanCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(DefaultStudyPlanCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(DefaultStudyPlanCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(DeleteCommitCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(DeleteCommitCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(DeleteSemesterCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(DeleteSemesterCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(DeleteStudyPlanCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(DeleteStudyPlanCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(EditTitleCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(EditTitleCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(ListAllStudyPlansCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(ListAllStudyPlansCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(RevertCommitCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(RevertCommitCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(ViewCommitCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(ViewCommitCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(ViewCommitHistoryCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(ViewCommitHistoryCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(ViewStudyPlanCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(ViewStudyPlanCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(CheckCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(CheckCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(DescriptionCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(DescriptionCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(ValidModsCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(ValidModsCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(ExitCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(ExitCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(ExpandCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(ExpandCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(CollapseCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(CollapseCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(ExpandAllCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(ExpandAllCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(CollapseAllCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(CollapseAllCommand.COMMAND_WORD), expectedHelpCommand);

        expectedHelpCommand = new HelpCommand(ChangeThemeCommand.COMMAND_WORD);
        assertEquals(new HelpCommandParser().parse(ChangeThemeCommand.COMMAND_WORD), expectedHelpCommand);
    }

    @Test
    public void parse_helpWithCommand_failure() {
        assertThrows(ParseException.class, () -> new HelpCommandParser().parse(NON_EXISTENT_COMMAND_NAME));
    }
}
