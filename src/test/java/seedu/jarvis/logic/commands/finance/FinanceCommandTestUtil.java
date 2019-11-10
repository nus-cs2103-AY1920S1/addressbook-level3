package seedu.jarvis.logic.commands.finance;

import static seedu.jarvis.logic.parser.CliSyntax.FinanceSyntax.PREFIX_DESCRIPTION;
import static seedu.jarvis.logic.parser.CliSyntax.FinanceSyntax.PREFIX_MONEY;

import seedu.jarvis.logic.commands.finance.EditInstallmentCommand.EditInstallmentDescriptor;
import seedu.jarvis.testutil.finance.EditInstallmentDescriptorBuilder;

/**
 * Contains helper methods for testing all finance tracker related commands.
 */
public class FinanceCommandTestUtil {

    public static final String VALID_DESC_NETFLIX = "Netflix";
    public static final String VALID_MONEY_NETFLIX = "13.50";
    public static final String VALID_DESC_SPOTIFY = "Spotify";
    public static final String VALID_MONEY_SPOTIFY = "9.50";
    public static final String VALID_DESC_LUNCH = "Lunch at Reedz";
    public static final String VALID_MONEY_LUNCH = "5.50";
    public static final String VALID_DESC_EARPHONES = "Earphones";
    public static final String VALID_MONEY_EARPHONES = "30.50";

    public static final String INSTAL_DESC_NETFLIX = " " + PREFIX_DESCRIPTION + VALID_DESC_NETFLIX;
    public static final String INSTAL_MONEY_NETFLIX = " " + PREFIX_MONEY + VALID_MONEY_NETFLIX;

    public static final String INSTAL_DESC_SPOTIFY = " " + PREFIX_DESCRIPTION + VALID_DESC_SPOTIFY;
    public static final String INSTAL_MONEY_SPOTIFY = " " + PREFIX_MONEY + VALID_MONEY_SPOTIFY;

    public static final String PURCHASE_DESC_LUNCH = " " + PREFIX_DESCRIPTION + VALID_DESC_LUNCH;
    public static final String PURCHASE_MONEY_LUNCH = " " + PREFIX_MONEY + VALID_MONEY_LUNCH;

    public static final String INVALID_INSTAL_MONEY = " " + PREFIX_MONEY + "-10.0";
    public static final String INVALID_PURCHASE_MONEY = " " + PREFIX_MONEY + "-10.0";

    public static final String HIGH_MONTHLY_LIMIT = " " + PREFIX_MONEY + "800.0";

    public static final String INVALID_MONTHLY_LIMIT = " " + PREFIX_MONEY + "-500.0";

    public static final EditInstallmentDescriptor INSTALL_NETFLIX;
    public static final EditInstallmentDescriptor INSTALL_SPOTIFY;

    static {
        INSTALL_NETFLIX = new EditInstallmentDescriptorBuilder()
                .withDescription(VALID_DESC_NETFLIX)
                .withSubscriptionFee(VALID_MONEY_NETFLIX)
                .build();
        INSTALL_SPOTIFY = new EditInstallmentDescriptorBuilder()
                .withDescription(VALID_DESC_SPOTIFY)
                .withSubscriptionFee(VALID_MONEY_SPOTIFY)
                .build();
    }


}
