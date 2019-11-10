package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PARTICIPANT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PARTICIPANT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPANT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.activity.Amount.MESSAGE_CONSTRAINTS;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SettleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.activity.Amount;


public class SettleCommandParserTest {
    private static ArrayList<String> persons = new ArrayList<>();

    private SettleCommandParser parser = new SettleCommandParser();
    private Amount amount = new Amount(Double.parseDouble(VALID_AMOUNT));

    @BeforeAll
    public static void init() {
        persons.add(VALID_NAME_BOB);
        persons.add(VALID_NAME_AMY);
    }

    @Test
    public void parse_allFieldsPresent_success() {

        assertParseSuccess(
            parser,
            PREAMBLE_WHITESPACE
            + PARTICIPANT_DESC_BOB
            + PARTICIPANT_DESC_AMY
            + VALID_AMOUNT_DESC,
            new SettleCommand(
                persons,
                amount
            )
        );

    }

    @Test
    public void parse_multiplePersons_success() {
        SettleCommand result = new SettleCommand(
                persons,
                amount
        );

        // This checks that the sequence of amounts is indeed as input
        assertParseSuccess(
                parser,
                PREAMBLE_WHITESPACE
                + PARTICIPANT_DESC_BOB
                + VALID_AMOUNT_DESC
                + PARTICIPANT_DESC_AMY,
                result
        );

        try {
            SettleCommand e1 = parser.parse(
                    PREAMBLE_WHITESPACE
                            + PARTICIPANT_DESC_AMY
                            + VALID_AMOUNT_DESC
                            + PARTICIPANT_DESC_BOB);

            assertNotEquals(result, e1);
        } catch (ParseException e) {
            System.out.println(e);
        }
    }

    @Test
    public void parse_negativeAmount_fail() {
        // negative amount
        assertParseFailure(
            parser,
            PREAMBLE_WHITESPACE
            + PARTICIPANT_DESC_BOB
            + PARTICIPANT_DESC_AMY
            + INVALID_AMOUNT_DESC,
            MESSAGE_CONSTRAINTS
        );
    }

    @Test
    public void parse_samePerson_fail() {
        assertParseFailure(
            parser,
            PARTICIPANT_DESC_BOB
            + PARTICIPANT_DESC_BOB
            + VALID_AMOUNT_DESC,
            SettleCommand.MESSAGE_REPEATED_PERSON
        );
    }

    @Test
    public void parse_multipleExpenses_fail() {
        assertParseFailure(
            parser,
            PARTICIPANT_DESC_BOB
            + PARTICIPANT_DESC_AMY
            + VALID_AMOUNT_DESC
            + VALID_AMOUNT_DESC,
            SettleCommand.MESSAGE_TOO_MANY_AMOUNTS
        );
    }

    @Test
    public void parse_tooManyPeople_fail() {
        assertParseFailure(
            parser,
            PARTICIPANT_DESC_BOB
            + PARTICIPANT_DESC_AMY
            + " " + PREFIX_PARTICIPANT + "hey"
            + VALID_AMOUNT_DESC,
            SettleCommand.MESSAGE_NOT_TWO_PEOPLE
        );
    }

    @Test
    public void parse_tooLittlePeople_fail() {
        assertParseFailure(
            parser,
            PARTICIPANT_DESC_BOB
            + VALID_AMOUNT_DESC,
            SettleCommand.MESSAGE_NOT_TWO_PEOPLE
        );
    }
}
