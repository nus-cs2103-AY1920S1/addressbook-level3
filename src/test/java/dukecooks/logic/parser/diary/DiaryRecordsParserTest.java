package dukecooks.logic.parser.diary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.HelpCommand;
import dukecooks.logic.commands.diary.AddDiaryCommand;
import dukecooks.logic.commands.diary.DeleteDiaryCommand;
import dukecooks.logic.commands.diary.EditDiaryCommand;
import dukecooks.logic.parser.DukeCooksParser;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.model.diary.components.Diary;
import dukecooks.testutil.Assert;
import dukecooks.testutil.TypicalIndexes;
import dukecooks.testutil.diary.DiaryBuilder;
import dukecooks.testutil.diary.DiaryUtil;
import dukecooks.testutil.diary.EditDiaryDescriptorBuilder;


public class DiaryRecordsParserTest {

    private final DukeCooksParser parser = new DukeCooksParser();

    @Test
    public void parseCommand_add() throws Exception {
        Diary diary = new DiaryBuilder().build();
        AddDiaryCommand command = (AddDiaryCommand) parser.parseCommand(DiaryUtil.getAddCommand(diary));
        assertEquals(new AddDiaryCommand(diary), command);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteDiaryCommand command = (DeleteDiaryCommand) parser.parseCommand(
                DeleteDiaryCommand.COMMAND_WORD + " " + DeleteDiaryCommand.VARIANT_WORD
                        + " " + TypicalIndexes.INDEX_FIRST_DIARY.getOneBased());
        assertEquals(new DeleteDiaryCommand(TypicalIndexes.INDEX_FIRST_DIARY), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Diary diary = new DiaryBuilder().build();
        EditDiaryCommand.EditDiaryDescriptor descriptor = new EditDiaryDescriptorBuilder(diary).build();
        EditDiaryCommand command = (EditDiaryCommand) parser.parseCommand(EditDiaryCommand.COMMAND_WORD
                + " " + EditDiaryCommand.VARIANT_WORD
                + " " + TypicalIndexes.INDEX_FIRST_DIARY.getOneBased() + " " + DiaryUtil
                .getEditDiaryDescriptorDetails(descriptor));
        assertEquals(new EditDiaryCommand(TypicalIndexes.INDEX_FIRST_DIARY, descriptor), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        Assert.assertThrows(ParseException.class, String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        Assert.assertThrows(ParseException.class, Messages.MESSAGE_UNKNOWN_COMMAND, ()
            -> parser.parseCommand("unknownCommand"));
    }
}
