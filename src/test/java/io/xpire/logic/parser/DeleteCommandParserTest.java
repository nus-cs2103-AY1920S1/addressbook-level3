package io.xpire.logic.parser;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static io.xpire.logic.parser.CommandParserTestUtil.assertParseFailure;
import static io.xpire.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static io.xpire.testutil.TypicalIndexes.INDEX_FIRST_ITEM;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import io.xpire.logic.commands.DeleteCommand;
import io.xpire.model.tag.Tag;
import io.xpire.model.tag.TagComparator;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 * TODO: Quantity Deletion
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteCommand(INDEX_FIRST_ITEM));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_deleteSingleTagMode_returnsDeleteCommand() {
        Set<Tag> set = new TreeSet<>(new TagComparator());
        set.add(new Tag("Tag1"));
        assertParseSuccess(parser, "1|#Tag1", new DeleteCommand(INDEX_FIRST_ITEM, set));
    }

    @Test
    public void parse_deleteMultipleTagMode_returnsDeleteCommand() {
        Set<Tag> set = new TreeSet<>(new TagComparator());
        set.add(new Tag("Tag1"));
        set.add(new Tag("Tag2"));
        assertParseSuccess(parser, "1|#Tag1", new DeleteCommand(INDEX_FIRST_ITEM, set));
    }

    @Test
    public void parse_deleteBlankTagMode_throwsParseException() {
        assertParseFailure(parser, "1|#", Tag.MESSAGE_CONSTRAINTS);
    }

}
