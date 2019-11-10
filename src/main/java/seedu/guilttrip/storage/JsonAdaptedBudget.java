package seedu.guilttrip.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.guilttrip.commons.exceptions.IllegalValueException;
import seedu.guilttrip.model.entry.Amount;
import seedu.guilttrip.model.entry.Budget;
import seedu.guilttrip.model.entry.BudgetAmount;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.entry.Period;
import seedu.guilttrip.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Budget}.
 */
class JsonAdaptedBudget {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Budget's %s field is missing!";
    private final String category;
    private final String desc;
    private final String date;
    private final String amt;
    private final String period;
    private final String spent;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedBudget} with the given entry details.
     */
    @JsonCreator
    public JsonAdaptedBudget(@JsonProperty("category") String category, @JsonProperty("desc") String desc,
                             @JsonProperty("amt") String amt, @JsonProperty("time") String time,
                             @JsonProperty("period") String period,
                             @JsonProperty("spent") String spent,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.category = category;
        this.desc = desc;
        this.amt = amt;
        this.date = time;
        this.period = period;
        this.spent = spent;

        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Budget} into this class for Jackson use.
     */
    public JsonAdaptedBudget(Budget source) {
        category = source.getCategory().categoryName;
        desc = source.getDesc().fullDesc;
        amt = source.getAmount().toString();
        date = source.getDate().toString();
        period = source.getPeriod().toString();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        spent = source.getSpent().toString();
    }

    /**
     * Converts this Jackson-friendly adapted budget object into the model's {@code Budget} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted budget.
     */
    public Budget toModelType() throws IllegalValueException {
        final List<Tag> entryTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            entryTags.add(tag.toModelType());
        }

        if (desc == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(desc)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }

        final Category modelCategory = new Category(category, "Expense");

        final Description modelDesc = new Description(desc);

        final Date modelDate = new Date(date);

        final Period modelPeriod = new Period(period);

        final Amount modelAmt = new Amount(amt);

        final BudgetAmount modelSpent = new BudgetAmount(spent);

        final Set<Tag> modelTags = new HashSet<>(entryTags);
        Budget budget = new Budget(modelCategory, modelDesc, modelDate, modelPeriod, modelAmt, modelTags);
        budget.setSpentAmount(modelSpent);
        return budget;
    }

}
