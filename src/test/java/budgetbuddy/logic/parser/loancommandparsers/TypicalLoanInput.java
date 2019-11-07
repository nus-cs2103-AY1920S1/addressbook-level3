package budgetbuddy.logic.parser.loancommandparsers;

import static budgetbuddy.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DATE;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_PERSON;
import static budgetbuddy.testutil.loanutil.TypicalLoans.JOHN_OUT_UNPAID;

import budgetbuddy.model.attributes.Description;

/**
 * A utility class containing {@code String} input for testing loan command parsers.
 */
public class TypicalLoanInput {

    // input for creating the Loan object JOHN_OUT_UNPAID in TypicalLoans
    public static final String JOHN_DIRECTION = JOHN_OUT_UNPAID.getDirection().toString().toLowerCase();
    public static final String JOHN_PERSON = PREFIX_PERSON + JOHN_OUT_UNPAID.getPerson().getName().toString();
    public static final String JOHN_AMOUNT = PREFIX_AMOUNT + JOHN_OUT_UNPAID.getAmount().toString().substring(1);
    public static final String JOHN_DESCRIPTION = PREFIX_DESCRIPTION + JOHN_OUT_UNPAID.getDescription().toString();
    public static final String JOHN_DATE = PREFIX_DATE + JOHN_OUT_UNPAID.getDateString();

    public static final String INVALID_INPUT_PERSON = PREFIX_PERSON + "John$";
    public static final String INVALID_INPUT_AMOUNT = PREFIX_AMOUNT + "-1";
    public static final String INVALID_INPUT_AMOUNT_ZERO = PREFIX_AMOUNT + "0";
    public static final String INVALID_INPUT_AMOUNT_CENTS = PREFIX_AMOUNT + "1.111";
    public static final String INVALID_INPUT_DESCRIPTION = PREFIX_DESCRIPTION + "d".repeat(Description.MAX_LENGTH + 1);
    public static final String INVALID_INPUT_DATE = PREFIX_DATE + "1st October 2019";
}
