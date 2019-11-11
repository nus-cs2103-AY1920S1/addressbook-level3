package seedu.address.testutil;

import seedu.address.model.classid.ClassId;
import seedu.address.model.earnings.Amount;
import seedu.address.model.earnings.Date;
import seedu.address.model.earnings.Earnings;
import seedu.address.model.earnings.Type;

/**
 * A utility class to help with building Earnings objects.
 */
public class EarningsBuilder {

    public static final String DEFAULT_DATE = "01/01/2019";
    public static final String DEFAULT_TYPE = "tut";
    public static final String DEFAULT_CLASSID = "F14";
    public static final String DEFAULT_AMOUNT = "50.00";


    private Date date;
    private Type type;
    private ClassId classid;
    private Amount amount;

    public EarningsBuilder() {
        date = new Date(DEFAULT_DATE);
        type = new Type(DEFAULT_TYPE);
        classid = new ClassId(DEFAULT_CLASSID);
        amount = new Amount(DEFAULT_AMOUNT);
    }

    /**
     * Initializes the EarningsBuilder with the data of {@code earningsToCopy}.
     */
    public EarningsBuilder(Earnings earningsToCopy) {
        date = earningsToCopy.getDate();
        type = earningsToCopy.getType();
        classid = earningsToCopy.getClassId();
        amount = earningsToCopy.getAmount();
    }

    /**
     * Sets the {@code Date} of the {@code Earnings} that we are building.
     */
    public EarningsBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Type} of the {@code Earnings} that we are building.
     */
    public EarningsBuilder withType(String type) {
        this.type = new Type(type);
        return this;
    }

    /**
     * Sets the {@code ClassId} of the {@code Earnings} that we are building.
     */
    public EarningsBuilder withClassId(String classId) {
        this.classid = new ClassId(classId);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code Earnings} that we are building.
     */
    public EarningsBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    public Earnings build() {
        return new Earnings(date, classid, amount, type);
    }

}
