package seedu.address.model.achievements;

public enum DurationUnit {
    DAY, WEEK, MONTH;

    public String toLowerCase() {
        return this.toString().toLowerCase();
    }
}
