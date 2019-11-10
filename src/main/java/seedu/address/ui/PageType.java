package seedu.address.ui;

/**
 * Enumerates possible page type for travezy application, for purpose of page navigation.
 */
public enum PageType {
    ADDRESS_BOOK, ACHIEVEMENTS, CALENDAR, DIARY, FINANCIAL_TRACKER, ITINERARY, MAIN;

    public static final String MESSAGE_CONSTRAINTS = "Only the following arguments are allowed: "
            + PageType.ADDRESS_BOOK + ", "
            + PageType.ACHIEVEMENTS + ", "
            + PageType.CALENDAR + ", "
            + PageType.DIARY + ", "
            + PageType.FINANCIAL_TRACKER + ", "
            + PageType.ITINERARY + ", "
            + PageType.MAIN;

    public static PageType of(String string) {
        return PageType.valueOf(string.toUpperCase());
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
