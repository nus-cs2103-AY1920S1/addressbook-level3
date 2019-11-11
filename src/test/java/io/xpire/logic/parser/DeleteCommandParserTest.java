package io.xpire.logic.parser;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static io.xpire.commons.core.Messages.MESSAGE_INVALID_INDEX;
import static io.xpire.logic.parser.CommandParserTestUtil.assertEqualsParseSuccess;
import static io.xpire.logic.parser.CommandParserTestUtil.assertParseFailure;
import static io.xpire.testutil.TypicalIndexes.INDEX_FIRST_ITEM;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import io.xpire.logic.commands.DeleteCommand;
import io.xpire.model.ListType;
import io.xpire.model.item.Quantity;
import io.xpire.model.tag.Tag;
import io.xpire.model.tag.TagComparator;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser xpireParser = new DeleteCommandParser(ListType.XPIRE);
    private DeleteCommandParser replenishParser = new DeleteCommandParser(ListType.REPLENISH);

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertEqualsParseSuccess(xpireParser, "1", new DeleteCommand(ListType.XPIRE, INDEX_FIRST_ITEM));

        assertEqualsParseSuccess(replenishParser, "1", new DeleteCommand(ListType.REPLENISH,
                INDEX_FIRST_ITEM));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {

        // invalid arguments in XpireParser
        assertParseFailure(xpireParser, "a", MESSAGE_INVALID_INDEX);

        //invalid trailing arguments in XpireParser
        assertParseFailure(xpireParser, "1||||||1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));

        // invalid arguments in ReplenishParser
        assertParseFailure(replenishParser, "a", MESSAGE_INVALID_INDEX);

        //invalid trailing arguments in ReplenishParser
        assertParseFailure(replenishParser, "1||||||1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_deleteSingleTagMode_returnsDeleteCommand() {
        Set<Tag> set = new TreeSet<>(new TagComparator());
        set.add(new Tag("Tag1"));
        assertEqualsParseSuccess(xpireParser, "1|#Tag1", new DeleteCommand(ListType.XPIRE, INDEX_FIRST_ITEM,
                set));
        assertEqualsParseSuccess(replenishParser, "1|#Tag1", new DeleteCommand(ListType.REPLENISH,
                INDEX_FIRST_ITEM, set));
    }

    @Test
    public void parse_deleteMultipleTagMode_returnsDeleteCommand() {
        Set<Tag> set = new TreeSet<>(new TagComparator());
        set.add(new Tag("Tag1"));
        set.add(new Tag("Tag2"));
        assertEqualsParseSuccess(xpireParser, "1|#Tag1", new DeleteCommand(ListType.XPIRE, INDEX_FIRST_ITEM,
                set));
        assertEqualsParseSuccess(replenishParser, "1|#Tag1", new DeleteCommand(ListType.REPLENISH,
                INDEX_FIRST_ITEM, set));
    }

    @Test
    public void parse_deleteBlankTagMode_throwsParseException() {
        assertParseFailure(xpireParser, "1|#", Tag.MESSAGE_CONSTRAINTS);

        assertParseFailure(replenishParser, "1|#", Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_deleteValidQuantityInXpire_returnsDeleteCommand() {
        Quantity validQuantity = new Quantity("1");
        assertEqualsParseSuccess(xpireParser, "1|1", new DeleteCommand(ListType.XPIRE, INDEX_FIRST_ITEM,
                validQuantity));
    }

    //invalid Quantity
    @Test
    public void parse_deleteInvalidQuantityInXpire_throwsParseException() {
        assertParseFailure(xpireParser, "1|-2", Quantity.MESSAGE_CONSTRAINTS);
    }

    //deleting of Quantity not allowed in replenish list as replenish items have no quantity
    @Test
    public void parse_deleteQuantityInReplenishList_throwsParseException() {
        assertParseFailure(replenishParser, "1|1", DeleteCommandParser.MESSAGE_DELETE_QUANTITY_INVALID_USAGE);
    }
}
