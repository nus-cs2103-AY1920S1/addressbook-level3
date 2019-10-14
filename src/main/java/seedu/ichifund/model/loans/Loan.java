package seedu.ichifund.model.loans;

import java.util.Date;

import seedu.ichifund.model.Amount;
import seedu.ichifund.model.Description;
import seedu.ichifund.model.person.Name;

/**
 * Loan class for storing objects of Loan type.
 */
public class Loan {
    private Name name;
    private Date date;
    private Integer type;
    private Amount amount;
    private Description description;

    public Loan(Amount amount, Name name, Date date, Integer type, Description description) {
        this.amount = amount;
        this.name = name;
        this.date = date;
        this.type = type;
        this.description = description;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }
}
