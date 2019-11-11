package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_AMOUNT_OVERFLOW;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AMOUNT_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AMOUNT_OVERFLOW_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AMOUNT_RANGE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AMOUNT_TYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AMOUNT_ZERO_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATETYPE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATETYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.SplitCommand;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.Split;
import seedu.address.model.util.Date;
import seedu.address.testutil.LedgerOperationBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

class SplitCommandParserTest {
    private final SplitCommandParser parser = new SplitCommandParser();
    private Split expectedEvenSplit;

    @BeforeEach
    void init() {
        Person a = new PersonBuilder(TypicalPersons.ALICE).withAmount("0").build();
        Person b = new PersonBuilder(TypicalPersons.BENSON).withAmount("0").build();
        Person f = new PersonBuilder(TypicalPersons.FIONA).withAmount("0").build();
        Person d = new PersonBuilder(TypicalPersons.DANIEL).withAmount("0").build();
        expectedEvenSplit = new LedgerOperationBuilder()
                .addPerson(a)
                .addPerson(b)
                .addPerson(f)
                .addPerson(d)
                .withDescription("description")
                .withAmount("5")
                .withDate("20102019")
                .asSplit(1, 1, 1, 1, 1);
    }

    @Test
    void parse_allFieldsPresent_success() {
        assertParseSuccess(parser,
                String.format(" $/5 d/20102019 n/%s n/%s n/%s n/%s s/1 s/1 s/1 s/1 s/1 a/description",
                TypicalPersons.ALICE.getName().fullName,
                TypicalPersons.BENSON.getName().fullName,
                TypicalPersons.FIONA.getName().fullName,
                TypicalPersons.DANIEL.getName().fullName),
                new SplitCommand(expectedEvenSplit));
    }

    @Test
    void parse_optionalFieldsMissing_success() {
        assertParseSuccess(parser,
                String.format(" $/5 d/20102019 n/%s n/%s n/%s n/%s a/description",
                        TypicalPersons.ALICE.getName().fullName,
                        TypicalPersons.BENSON.getName().fullName,
                        TypicalPersons.FIONA.getName().fullName,
                        TypicalPersons.DANIEL.getName().fullName),
                new SplitCommand(expectedEvenSplit));

        Split today = new LedgerOperationBuilder(expectedEvenSplit)
                .withDate(Date.now().toString())
                .asSplit(1, 1, 1, 1, 1);

        assertParseSuccess(parser,
                String.format(" $/5 n/%s n/%s n/%s n/%s a/description",
                        TypicalPersons.ALICE.getName().fullName,
                        TypicalPersons.BENSON.getName().fullName,
                        TypicalPersons.FIONA.getName().fullName,
                        TypicalPersons.DANIEL.getName().fullName),
                new SplitCommand(today));
    }

    @Test
    void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SplitCommand.MESSAGE_USAGE);

        //no amount
        assertParseFailure(parser,
                String.format(" d/20102019 n/%s n/%s n/%s n/%s a/description",
                        TypicalPersons.ALICE.getName().fullName,
                        TypicalPersons.BENSON.getName().fullName,
                        TypicalPersons.FIONA.getName().fullName,
                        TypicalPersons.DANIEL.getName().fullName),
                expectedMessage);

        //no description
        assertParseFailure(parser,
                String.format(" $/5 d/20102019 n/%s n/%s n/%s n/%s ",
                        TypicalPersons.ALICE.getName().fullName,
                        TypicalPersons.BENSON.getName().fullName,
                        TypicalPersons.FIONA.getName().fullName,
                        TypicalPersons.DANIEL.getName().fullName),
                expectedMessage);

        //no names
        assertParseFailure(parser, " $/5 d/20102019 a/description", expectedMessage);
    }

    @Test
    void parse_invalidValues_failure() {
        // invalid amount (zero)
        assertParseFailure(parser, " " + INVALID_AMOUNT_ZERO_DESC + " d/20102019 a/description n/Name",
                Messages.MESSAGE_AMOUNT_ZERO);

        // invalid amount (range)
        assertParseFailure(parser, " " + INVALID_AMOUNT_RANGE_DESC + " d/20102019 a/description n/Name",
                MESSAGE_AMOUNT_OVERFLOW);

        // invalid amount (double)
        assertParseFailure(parser, " " + INVALID_AMOUNT_TYPE_DESC + " d/20102019 a/description n/Name",
                Amount.DOUBLE_CONSTRAINTS);

        // invalid amount (overflow)
        assertParseFailure(parser, " " + INVALID_AMOUNT_OVERFLOW_DESC + " d/20102019 a/description n/Name",
                MESSAGE_AMOUNT_OVERFLOW);

        // invalid date (format)
        assertParseFailure(parser, " " + AMOUNT_DESC_ALICE + INVALID_DATE_DESC + " a/description n/Name",
                Date.MESSAGE_FORMAT_CONSTRAINTS);

        //invalid date (type)
        assertParseFailure(parser, " " + AMOUNT_DESC_ALICE + INVALID_DATETYPE_DESC + " a/description n/Name",
                String.format(Date.MESSAGE_DATE_INVALID, INVALID_DATETYPE));

        //invalid description
        assertParseFailure(parser, " " + AMOUNT_DESC_ALICE + DATE_DESC_ALICE + " a/Milk@ n/Name",
                Description.MESSAGE_CONSTRAINTS);

        //invalid name
        assertParseFailure(parser, " " + AMOUNT_DESC_ALICE + DATE_DESC_ALICE + " a/milk n/Name!!",
                Name.MESSAGE_CONSTRAINTS);
    }
}
