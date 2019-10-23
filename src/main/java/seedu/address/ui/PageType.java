package seedu.address.ui;

/**
 * Enumerates possible page type for travezy application, for purpose of page navigation.
 */
public enum PageType {
    ADDRESS_BOOK, CALENDAR, DIARY, FINANCIAL_TRACKER, ITINERARY, MAIN, ACHIEVEMENTS, SAMPLE; // todo remove SAMPLE

    public static final String MESSAGE_CONSTRAINTS = "Only the following arguments are allowed: "
            + "address_book, calendar, diary,financial_tracker, itinerary, main";
    public static PageType of(String string) {
        return PageType.valueOf(string.toUpperCase());
    }
}
