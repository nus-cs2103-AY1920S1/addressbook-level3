package seedu.address.testutil;

import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_AMOUNT_LOG1;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_AMOUNT_LOG2;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_CAT_FOOD;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_CAT_PRESENT;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_DESC_LOG1;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_DESC_LOG2;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_FROM_LOG1;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_FROM_LOG2;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_PLACE_LOG1;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_PLACE_LOG2;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_TDATE_LOG1;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_TDATE_LOG2;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_TMET_LOG1;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_TMET_LOG2;
import static seedu.address.logic.finance.commands.CommandTestUtil.VALID_TO_LOG1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.finance.FinanceLog;
import seedu.address.model.finance.attributes.TransactionDate;
import seedu.address.model.finance.logentry.BorrowLogEntry;
import seedu.address.model.finance.logentry.LendLogEntry;
import seedu.address.model.finance.logentry.LogEntry;

/**
 * A utility class containing a list of {@code LogEntries} objects to be used in tests.
 */
public class TypicalLogEntries {

    public static final LogEntry LOG1 = new LogEntryBuilder().withAmount("1")
            .withTransactionDate("08-08-2019")
            .withDescription("Vitasoy")
            .withTransactionMethod("Cash")
            .withCats("food")
            .withPlace("Frontier")
            .withPerson("Uncle Ben").buildSpend();
    public static final LogEntry LOG2 = new LogEntryBuilder().withAmount("2.80")
            .withTransactionDate("26-08-2019")
            .withDescription("Yong Tau Foo")
            .withTransactionMethod("Cash")
            .withCats("YTF", "school")
            .withPlace("Frontier")
            .withPerson("NIL").buildSpend();
    public static final LogEntry LOG3 = new LogEntryBuilder().withAmount("300")
            .withTransactionDate("3-11-2019")
            .withDescription("GST Voucher")
            .withTransactionMethod("Bank credit")
            .withCats("rebate")
            .withPlace("NIL")
            .withPerson("Govt").buildIncome();
    public static final LogEntry LOG4 = new LogEntryBuilder().withAmount("2.60")
            .withTransactionDate("08-10-2018")
            .withDescription("Umian Dry")
            .withTransactionMethod("NETS Flashpay")
            .withCats("umian", "terrace")
            .withPlace("Terrace")
            .withPerson("NIL").buildSpend();
    public static final LogEntry LOG5 = new LogEntryBuilder().withAmount("10")
            .withTransactionDate("29-10-2019")
            .withDescription("Groceries")
            .withTransactionMethod("Cash")
            .withCats()
            .withPlace("NIL")
            .withPerson("Mother").buildBorrow();
    public static final LogEntry LOG6 = new LogEntryBuilder().withAmount("2.40")
            .withTransactionDate("1-11-2019")
            .withDescription("2 Haribo Sour")
            .withTransactionMethod("Cash")
            .withCats("sweets", "lollies", "haribo")
            .withPlace("Science COOP")
            .withPerson("NIL").buildSpend();
    public static final LogEntry LOG7 = new LogEntryBuilder().withAmount("5")
            .withTransactionDate("3-11-2019")
            .withDescription("2 for 5 Famous Bowl")
            .withTransactionMethod("NETS")
            .withCats("kfc")
            .withPlace("KFC")
            .withPerson("Sister").buildLend();
    // Manually added
    public static final LogEntry LOG8 = new LogEntryBuilder().withAmount("50")
            .withTransactionDate("4-11-2019")
            .withDescription("Ezlink Top-up")
            .withTransactionMethod("NETS")
            .withCats("transport")
            .withPlace("Caldecott MRT")
            .withPerson("NIL").buildSpend();
    public static final LogEntry LOG9 = new LogEntryBuilder().withAmount("1")
            .withTransactionDate("5-11-2019")
            .withDescription("HL Choco Milk")
            .withTransactionMethod("Cash")
            .withCats()
            .withPlace("Frontier")
            .withPerson("NIL").buildSpend();

    // Manually added - Log entry's details found in {@code CommandTestUtil}
    public static final LogEntry LOG01 = new LogEntryBuilder().withAmount(VALID_AMOUNT_LOG1)
            .withTransactionDate(VALID_TDATE_LOG1)
            .withDescription(VALID_DESC_LOG1)
            .withTransactionMethod(VALID_TMET_LOG1)
            .withCats(VALID_CAT_FOOD)
            .withPlace(VALID_PLACE_LOG1)
            .withPerson(VALID_FROM_LOG1).buildSpend();
    public static final LogEntry LOG02 = new LogEntryBuilder().withAmount(VALID_AMOUNT_LOG2)
            .withTransactionDate(VALID_TDATE_LOG2)
            .withDescription(VALID_DESC_LOG2)
            .withTransactionMethod(VALID_TMET_LOG2)
            .withCats(VALID_CAT_FOOD, VALID_CAT_PRESENT)
            .withPlace(VALID_PLACE_LOG2)
            .withPerson(VALID_FROM_LOG2).buildIncome();
    public static final LogEntry LOG03 = new LogEntryBuilder().withAmount(VALID_AMOUNT_LOG1)
            .withTransactionDate(VALID_TDATE_LOG1)
            .withDescription(VALID_DESC_LOG2)
            .withTransactionMethod(VALID_TMET_LOG2)
            .withCats()
            .withPlace(VALID_PLACE_LOG1)
            .withPerson(VALID_TO_LOG1).buildBorrow();
    public static final LogEntry LOG04 = new LogEntryBuilder().withAmount(VALID_AMOUNT_LOG2)
            .withTransactionDate(VALID_TDATE_LOG1)
            .withDescription(VALID_DESC_LOG2)
            .withTransactionMethod(VALID_TMET_LOG2)
            .withCats(VALID_CAT_PRESENT)
            .withPlace(VALID_PLACE_LOG2)
            .withPerson(VALID_FROM_LOG2).buildLend();
    public static final LogEntry LOG05 = new LogEntryBuilder().withAmount(VALID_AMOUNT_LOG2)
            .withTransactionDate(VALID_TDATE_LOG2)
            .withDescription(VALID_DESC_LOG2)
            .withTransactionMethod(VALID_TMET_LOG2)
            .withCats(VALID_CAT_PRESENT)
            .withPlace(VALID_PLACE_LOG2)
            .withPerson(VALID_FROM_LOG2).buildSpend();

    public static final String KEYWORD_MATCHING_VITASOY = "Vitasoy"; // A keyword that matches VITASOY

    private TypicalLogEntries() {} // prevents instantiation

    /**
     * Returns an {@code FinanceLog} with all the typical log entries.
     */
    public static FinanceLog getTypicalFinanceLog() {
        FinanceLog fl = new FinanceLog();
        String repaidDate = (new TransactionDate()).toString();
        for (LogEntry log : getTypicalLogEntries()) {
            if (log instanceof BorrowLogEntry) {
                BorrowLogEntry borrowLog = (BorrowLogEntry) log;
                borrowLog.markAsRepaid();
                borrowLog.setRepaidDate(repaidDate, borrowLog.getTransactionDate().toString());
                fl.addLogEntry(borrowLog);
            } else if (log instanceof LendLogEntry) {
                LendLogEntry lendLog = (LendLogEntry) log;
                lendLog.markAsRepaid();
                lendLog.setRepaidDate(repaidDate, lendLog.getTransactionDate().toString());
                fl.addLogEntry(lendLog);
            } else {
                fl.addLogEntry(log);
            }
        }
        return fl;
    }

    public static List<LogEntry> getTypicalLogEntries() {
        return new ArrayList<>(Arrays.asList(LOG1, LOG2, LOG3, LOG4, LOG5, LOG6, LOG7));
    }
}
