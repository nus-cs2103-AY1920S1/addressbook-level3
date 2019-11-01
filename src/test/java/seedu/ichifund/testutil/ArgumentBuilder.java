package seedu.ichifund.testutil;

import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_TRANSACTION_TYPE;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_YEAR;

/**
 * A utility class to build arguments
 */
public class ArgumentBuilder {
    private String argument;
    
    public ArgumentBuilder() {
        argument = "";
    }

    /**
     * Adds a description argument to the string being built.
     */
    public ArgumentBuilder withDescription(String description) {
        argument += PREFIX_DESCRIPTION + description + " ";
        return this;
    }

    /**
     * Adds an amount argument to the string being built.
     */
    public ArgumentBuilder withAmount(String amount) {
        argument += PREFIX_AMOUNT + amount + " ";
        return this;
    }

    /**
     * Adds a category argument to the string being built.
     */
    public ArgumentBuilder withCategory(String category) {
        argument += PREFIX_CATEGORY + category + " ";
        return this;
    }

    /**
     * Adds a day argument to the string being built.
     */
    public ArgumentBuilder withDay(String day) {
        argument += PREFIX_DAY + day + " ";
        return this;
    }

    /**
     * Adds a month argument to the string being built.
     */
    public ArgumentBuilder withMonth(String month) {
        argument += PREFIX_MONTH + month + " ";
        return this;
    }

    /**
     * Adds a year argument to the string being built.
     */
    public ArgumentBuilder withYear(String year) {
        argument += PREFIX_YEAR + year + " ";
        return this;
    }

    /**
     * Adds a transactionType argument to the string being built.
     */
    public ArgumentBuilder withTransactionType(String transactionType) {
        argument += PREFIX_TRANSACTION_TYPE + transactionType + " ";
        return this;
    }
    
    public String build() {
        return argument;
    }
}
