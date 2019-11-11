package seedu.jarvis.logic.parser.finance;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.finance.MonthlyLimit;
import seedu.jarvis.model.finance.installment.InstallmentDescription;
import seedu.jarvis.model.finance.installment.InstallmentMoneyPaid;
import seedu.jarvis.model.finance.purchase.Purchase;
import seedu.jarvis.model.finance.purchase.PurchaseDescription;
import seedu.jarvis.model.finance.purchase.PurchaseMoneySpent;

/**
 * Contains utility methods that are used for parsing strings in the Finance Tracker classes.
 */
public class FinanceParserUtil {

    public static final String MESSAGE_INVALID_DATE = "Date is invalid. Please follow the format: dd/mm/yyyy.";

    public static final double AMOUNT_CLOSE_TO_LIMIT = 50;
    public static final double AMOUNT_LIMIT_REACHED = 0;

    /**
     * Parses a {@code String description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static InstallmentDescription parseInstallmentDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        return new InstallmentDescription(trimmedDescription);
    }

    /**
     * Parses a {@code String description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code money} is invalid.
     */
    public static InstallmentMoneyPaid parseInstallmentMoneySpent(String money) throws ParseException {
        requireNonNull(money);
        String trimmedMoney = money.trim();
        return new InstallmentMoneyPaid(trimmedMoney);
    }

    /**
     * Parses a {@code String description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static PurchaseDescription parsePurchaseDescription(String description) {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        return new PurchaseDescription(trimmedDescription);
    }

    /**
     * Parses a {@code String moneySpent}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static PurchaseMoneySpent parsePurchaseAmount(String moneySpent) throws ParseException {
        requireNonNull(moneySpent);
        String trimmedMoney = moneySpent.trim();
        return new PurchaseMoneySpent(trimmedMoney);
    }

    /**
     * Parses a {@code String moneySpent}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static MonthlyLimit parseMonthlyLimit(String limit) throws ParseException {
        requireNonNull(limit);
        String trimmedLimit = limit.trim();
        return new MonthlyLimit(trimmedLimit);
    }

    /**
     * Parses a {@code String date} into a date to record when purchase was made.
     *
     * @param date the date to be parsed
     * @return the formatted date
     */
    public static LocalDate parseDateOfPurchase(String date) throws ParseException {
        requireNonNull(date);
        try {
            String trimmedDate = date.trim();
            LocalDate formattedDate = LocalDate.parse(trimmedDate, Purchase.getDateFormat());
            return formattedDate;
        } catch (DateTimeParseException e) {
            throw new ParseException(MESSAGE_INVALID_DATE);
        }
    }
}
