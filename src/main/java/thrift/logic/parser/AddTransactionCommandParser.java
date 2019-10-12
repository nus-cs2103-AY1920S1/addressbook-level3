package thrift.logic.parser;

import static thrift.model.transaction.TransactionDate.DATE_FORMATTER;

import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import thrift.logic.parser.exceptions.ParseException;
import thrift.model.tag.Tag;
import thrift.model.transaction.Description;
import thrift.model.transaction.Remark;
import thrift.model.transaction.TransactionDate;
import thrift.model.transaction.Value;

/**
 * Parses common parameters that are necessary for either AddExpenseCommand or AddIncomeCommand.
 */
public abstract class AddTransactionCommandParser {

    /**
     * This methods checks if the required prefixes are present in the {@code ArgumentMultimap}.
     *
     * @param argumentMultimap the object to check for the existence of prefixes.
     * @param prefixes variable amount of {@code Prefix} to confirm the existence of.
     * @return true if specified prefixes are present in the argumentMultimap.
     */
    protected static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses for {@code Description} object given the {@code ArgumentMultimap} object.
     * @param argMultimap the object to parse from.
     * @return {@code Description} object based on the values in the input {@code ArgumentMultimap}.
     */
    protected Description parseTransactionDescription(ArgumentMultimap argMultimap) {
        return ParserUtil.parseDescription(argMultimap.getValue(CliSyntax.PREFIX_NAME).get());
    }

    /**
     * Parses for {@code Value} object given the {@code ArgumentMultimap} object.
     * @param argMultimap the object to parse from.
     * @return {@code Value} object based on the values in the input {@code ArgumentMultimap}.
     */
    protected Value parseTransactionValue(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parseValue(argMultimap.getValue(CliSyntax.PREFIX_COST).get());
    }

    /**
     * Parses for {@code Remark} object given the {@code ArgumentMultimap} object.
     *
    * @param argMultimap the object to parse from.
    * @return {@code Remark} object based on the values in the input {@code ArgumentMultimap}.
    */
    protected Remark parseTransactionRemark(ArgumentMultimap argMultimap) {
        Optional<String> remark = argMultimap.getValue(CliSyntax.PREFIX_REMARK);
        if (remark.isEmpty()) {
            return ParserUtil.parseRemark("");
        } else {
            return ParserUtil.parseRemark(remark.get());
        }
    }

    /**
     * Parses for {@code TransactionDate} object using the current System's date.
     * @return {@code TransactionDate} object based on the current System's date.
     */
    protected TransactionDate parseTransactionDate() {
        return new TransactionDate(DATE_FORMATTER.format(new Date()));
    }

    /**
     * Parses for {@code Set<Tag>} object given the {@code ArgumentMultimap} object.
     * @param argMultimap the object to parse from.
     * @return {@code Set<Tag>} object based on the values in the input {@code ArgumentMultimap}.
     */
    protected Set<Tag> parseTransactionTags(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parseTags(argMultimap.getAllValues(CliSyntax.PREFIX_TAG));
    }

}
