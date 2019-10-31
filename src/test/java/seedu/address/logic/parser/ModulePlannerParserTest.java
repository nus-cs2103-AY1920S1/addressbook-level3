package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.cli.AddModuleCommand;
import seedu.address.logic.commands.cli.BlockCurrentSemesterCommand;
import seedu.address.logic.commands.cli.DeleteModuleCommand;
import seedu.address.logic.commands.cli.NameUeFromSemesterCommand;
import seedu.address.logic.commands.cli.RedoCommand;
import seedu.address.logic.commands.cli.SetCurrentSemesterCommand;
import seedu.address.logic.commands.cli.UndoCommand;
import seedu.address.logic.commands.datamanagement.DeleteTagCommand;
import seedu.address.logic.commands.datamanagement.FindModuleCommand;
import seedu.address.logic.commands.datamanagement.RemoveAllTagsCommand;
import seedu.address.logic.commands.datamanagement.RemoveTagFromAllCommand;
import seedu.address.logic.commands.datamanagement.RemoveTagFromModuleCommand;
import seedu.address.logic.commands.datamanagement.RenameTagCommand;
import seedu.address.logic.commands.datamanagement.TagModuleCommand;
import seedu.address.logic.commands.datamanagement.ViewAllTagsCommand;
import seedu.address.logic.commands.datamanagement.ViewDefaultTagsCommand;
import seedu.address.logic.commands.datamanagement.ViewModuleTagsCommand;
import seedu.address.logic.commands.datamanagement.ViewTaggedCommand;
import seedu.address.logic.commands.gui.HelpCommand;
import seedu.address.logic.commands.storage.ActivateStudyPlanCommand;
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
import seedu.address.logic.commands.verification.DescriptionCommand;
import seedu.address.logic.commands.verification.ValidModsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.semester.SemesterName;

public class ModulePlannerParserTest {

    private static final String VALID_MODULE_CODE = "CS3244";
    private static final String VALID_SEMESTER = "Y1S1";
    private static final SemesterName VALID_SEMESTER_NAME = SemesterName.Y1S1;
    private static final String VALID_TAG_1 = "cool";
    private static final String VALID_TAG_2 = "cool_beans";
    private static final String VALID_COMMIT_MESSAGE = "AI study plan changes";

    private final ModulePlannerParser parser = new ModulePlannerParser();

    // =================== CLI ===================

    @Test
    public void parseCommand_add() throws Exception {
        AddModuleCommand command = (AddModuleCommand) parser.parseCommand(
                AddModuleCommand.COMMAND_WORD + " " + VALID_MODULE_CODE + " " + VALID_SEMESTER);
        assertEquals(new AddModuleCommand(VALID_MODULE_CODE, VALID_SEMESTER_NAME), command);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteModuleCommand command = (DeleteModuleCommand) parser.parseCommand(
                DeleteModuleCommand.COMMAND_WORD + " " + VALID_SEMESTER + " " + VALID_MODULE_CODE);
        assertEquals(new DeleteModuleCommand(VALID_MODULE_CODE, VALID_SEMESTER_NAME), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_setCurrentSemester() throws Exception {
        SetCurrentSemesterCommand command = new SetCurrentSemesterCommand(VALID_SEMESTER_NAME);
        assertEquals(parser.parseCommand(SetCurrentSemesterCommand.COMMAND_WORD + " " + VALID_SEMESTER), command);
    }


    @Test
    public void parseCommand_blockSemester() throws Exception {
        BlockCurrentSemesterCommand command = new BlockCurrentSemesterCommand(VALID_SEMESTER_NAME, "LOA");
        assertEquals(parser.parseCommand(BlockCurrentSemesterCommand.COMMAND_WORD + " "
                + VALID_SEMESTER + " " + "LOA"), command);
    }

    @Test
    public void parseCommand_nameUeFromSemester() throws Exception {
        NameUeFromSemesterCommand command = new NameUeFromSemesterCommand(VALID_MODULE_CODE, VALID_SEMESTER_NAME);
        assertEquals(parser.parseCommand(NameUeFromSemesterCommand.COMMAND_WORD + " "
                + VALID_SEMESTER + " " + VALID_MODULE_CODE), command);
    }

    // =================== TAG ===================

    @Test
    public void parseCommand_deleteTag() throws Exception {
        DeleteTagCommand command = new DeleteTagCommand(VALID_TAG_1);
        assertEquals(parser.parseCommand(DeleteTagCommand.COMMAND_WORD + " " + VALID_TAG_1), command);
    }

    @Test
    public void parseCommand_removeTagFromAll() throws Exception {
        RemoveTagFromAllCommand command = new RemoveTagFromAllCommand(VALID_TAG_1);
        assertEquals(parser.parseCommand(RemoveTagFromAllCommand.COMMAND_WORD + " " + VALID_TAG_1), command);
    }

    @Test
    public void parseCommand_removeTagFromModule() throws Exception {
        RemoveTagFromModuleCommand command = new RemoveTagFromModuleCommand(VALID_MODULE_CODE, VALID_TAG_1);
        assertEquals(parser.parseCommand(RemoveTagFromModuleCommand.COMMAND_WORD + " "
                + VALID_MODULE_CODE + " " + VALID_TAG_1), command);
    }

    @Test
    public void parseCommand_tagModule() throws Exception {
        TagModuleCommand command = new TagModuleCommand(VALID_TAG_1, VALID_MODULE_CODE);
        assertEquals(parser.parseCommand(TagModuleCommand.COMMAND_WORD + " "
                + VALID_MODULE_CODE + " " + VALID_TAG_1), command);
    }

    @Test
    public void parseCommand_renameTag() throws Exception {
        RenameTagCommand command = new RenameTagCommand(VALID_TAG_1, VALID_TAG_2);
        assertEquals(parser.parseCommand(RenameTagCommand.COMMAND_WORD + " "
                + VALID_TAG_1 + " " + VALID_TAG_2), command);
    }

    @Test
    public void parseCommand_viewModuleTags() throws Exception {
        ViewModuleTagsCommand command = new ViewModuleTagsCommand(VALID_MODULE_CODE);
        assertEquals(parser.parseCommand(ViewModuleTagsCommand.COMMAND_WORD + " " + VALID_MODULE_CODE), command);
    }

    @Test
    public void parseCommand_viewTagged() throws Exception {
        ViewTaggedCommand command = new ViewTaggedCommand(VALID_TAG_1);
        assertEquals(parser.parseCommand(ViewTaggedCommand.COMMAND_WORD + " " + VALID_TAG_1), command);
    }

    @Test
    public void parseCommand_viewDefaultTags() throws Exception {
        assertTrue(parser.parseCommand(ViewDefaultTagsCommand.COMMAND_WORD) instanceof ViewDefaultTagsCommand);
    }

    @Test
    public void parseCommand_viewAllTags() throws Exception {
        assertTrue(parser.parseCommand(ViewAllTagsCommand.COMMAND_WORD) instanceof ViewAllTagsCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        FindModuleCommand command = (FindModuleCommand)
                parser.parseCommand(FindModuleCommand.COMMAND_WORD + " CS2030");
        assertEquals(new FindModuleCommand("CS2030"), command);
    }

    public void parseCommand_removeAllTags() throws Exception {
        assertTrue(parser.parseCommand(RemoveAllTagsCommand.COMMAND_WORD) instanceof RemoveAllTagsCommand);
    }
    // =================== GUI ===================

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
    }

    // =================== STORAGE ===================

    @Test
    public void parseCommand_activateStudyPlan() throws Exception {
        ActivateStudyPlanCommand command = new ActivateStudyPlanCommand(1);
        assertEquals(parser.parseCommand(ActivateStudyPlanCommand.COMMAND_WORD + " " + 1), command);
    }

    @Test
    public void parseCommand_deleteStudyPlan() throws Exception {
        DeleteStudyPlanCommand command = new DeleteStudyPlanCommand(Index.fromZeroBased(1));
        assertEquals(parser.parseCommand(DeleteStudyPlanCommand.COMMAND_WORD + " " + 1), command);
    }

    @Test
    public void parseCommand_createStudyPlan() throws Exception {
        CreateStudyPlanCommand commandEmptyString = new CreateStudyPlanCommand("");
        assertEquals(parser.parseCommand(CreateStudyPlanCommand.COMMAND_WORD), commandEmptyString);

        CreateStudyPlanCommand command = new CreateStudyPlanCommand("SP1");
        assertEquals(parser.parseCommand(CreateStudyPlanCommand.COMMAND_WORD + " " + "SP1"), command);
    }

    @Test
    public void parseCommand_defaultStudyPlan() throws Exception {
        DefaultStudyPlanCommand command = new DefaultStudyPlanCommand();
        assertEquals(parser.parseCommand(DefaultStudyPlanCommand.COMMAND_WORD), command);
    }

    @Test
    public void parseCommand_deleteCommit() throws Exception {
        DeleteCommitCommand command = new DeleteCommitCommand(1, 2);
        assertEquals(parser.parseCommand(DeleteCommitCommand.COMMAND_WORD + " 1.2"), command);
    }

    @Test
    public void parseCommand_deleteSemester() throws Exception {
        DeleteSemesterCommand command = new DeleteSemesterCommand(VALID_SEMESTER_NAME);
        assertEquals(parser.parseCommand(DeleteSemesterCommand.COMMAND_WORD + " " + VALID_SEMESTER), command);
    }

    @Test
    public void parseCommand_editStudyPlanTitle() throws Exception {
        EditTitleCommand command = new EditTitleCommand("SP1");
        assertEquals(parser.parseCommand(EditTitleCommand.COMMAND_WORD + " " + "SP1"), command);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListAllStudyPlansCommand.COMMAND_WORD) instanceof ListAllStudyPlansCommand);
    }

    @Test
    public void parseCommand_viewCommit() throws Exception {
        ViewCommitCommand command = new ViewCommitCommand(1, 2);
        assertEquals(parser.parseCommand(ViewCommitCommand.COMMAND_WORD + " 1.2"), command);
    }

    @Test
    public void parseCommand_revertCommit() throws Exception {
        RevertCommitCommand command = new RevertCommitCommand(1, 2);
        assertEquals(parser.parseCommand(RevertCommitCommand.COMMAND_WORD + " 1.2"), command);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(ViewCommitHistoryCommand.COMMAND_WORD) instanceof ViewCommitHistoryCommand);

        try {
            parser.parseCommand("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    // =================== VERIFICATION ===================

    @Test
    public void parseCommand_description() throws Exception {
        DescriptionCommand command = new DescriptionCommand(VALID_MODULE_CODE);
        assertEquals(parser.parseCommand(DescriptionCommand.COMMAND_WORD + " " + VALID_MODULE_CODE), command);
    }

    @Test
    public void parseCommand_validMods() throws Exception {
        ValidModsCommand command = new ValidModsCommand(VALID_SEMESTER_NAME);
        assertEquals(parser.parseCommand(ValidModsCommand.COMMAND_WORD + " " + VALID_SEMESTER), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }


    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
