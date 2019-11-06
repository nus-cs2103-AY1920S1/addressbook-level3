package seedu.address.testutil;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Optional;

import seedu.address.model.expenditure.DayNumber;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.model.expenditure.MiscExpenditure;
import seedu.address.model.expenditure.PlannedExpenditure;
import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Name;

/**
 * Builder class to accommodate optional properties using builder pattern.
 * Can be used to construct {@link Expenditure} without optional fields.
 */
public class ExpenditureBuilder {
    private Name name;
    private Budget budget;
    private Optional<DayNumber> dayNumber;
    private String type;

    private ExpenditureBuilder() {}

    public static ExpenditureBuilder newInstance() {
        return new ExpenditureBuilder();
    }

    /**
     * Constructs a ExpenditureBuilder instance from the specified expenditure.
     *
     * @param expenditure Expenditure to use.
     * @return new ExpenditureBuilder instance.
     */
    public static ExpenditureBuilder of(Expenditure expenditure) {
        requireAllNonNull(expenditure.getName(), expenditure.getBudget());
        if (expenditure instanceof PlannedExpenditure) {
            return ExpenditureBuilder.newInstance()
                    .setName(expenditure.getName())
                    .setBudget(expenditure.getBudget())
                    .setDayNumber(expenditure.getDayNumber())
                    .setType("planned");
        } else if (expenditure instanceof MiscExpenditure) {
            return ExpenditureBuilder.newInstance()
                    .setName(expenditure.getName())
                    .setBudget(expenditure.getBudget())
                    .setDayNumber(expenditure.getDayNumber())
                    .setType("misc");
        } else {
            throw new AssertionError("Invalid expenditure type");
        }
    }

    public ExpenditureBuilder setName(Name name) {
        this.name = name;
        return this;
    }

    public ExpenditureBuilder setBudget(Budget budget) {
        this.budget = budget;
        return this;
    }

    public ExpenditureBuilder setDayNumber(Optional<DayNumber> dayNumber) {
        this.dayNumber = dayNumber;
        return this;
    }

    public ExpenditureBuilder setType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Terminal method to construct new {@link Expenditure}.
     */
    public Expenditure build() {
        if (type.equals("planned")) {
            return new PlannedExpenditure(name, budget, dayNumber);
        } else if (type.equals("misc")) {
            return new MiscExpenditure(name, budget, dayNumber);
        } else {
            throw new AssertionError("Invalid expenditure type");
        }
    }

}
