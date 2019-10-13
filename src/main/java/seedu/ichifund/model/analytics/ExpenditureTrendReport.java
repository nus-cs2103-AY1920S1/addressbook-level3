package seedu.ichifund.model.analytics;

import static java.util.Objects.requireNonNull;

import seedu.ichifund.model.Model;
import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.person.Person;
import seedu.ichifund.model.transaction.Transaction;

import java.util.List;

/**
 * Represents a report for expenditure trends.
 */
public class ExpenditureTrendReport extends TrendReport {

    /**
     * Constructs a {@code ExpenditureTrendReport}.
     *
     * @param date A date.
     */
    public ExpenditureTrendReport(Date date) {
        super(date);
    }

    /**
     * Fetches the relevant transaction information to generate the expenditure trend report.
     *
     * @param model {@code Model} which the report should be based on.
     */
    @Override
    public void fetch(Model model, Date date) {
        // TO-DO: Get expenditure trends
        requireNonNull(model);
        requireNonNull(date);
        List<Person> transactionList = model.getFundBook().getPersonList();
        // test for expenditure...
    }
}
