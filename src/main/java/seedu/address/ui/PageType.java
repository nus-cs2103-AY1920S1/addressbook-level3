package seedu.address.ui;

public enum PageType {
    ADDRESS_BOOK, CALENDAR, DIARY, FINANCIAL_TRACKER, ITINERARY, MAIN, SAMPLE; // todo remove SAMPLE

    public static PageType of(String string) {
        return PageType.valueOf(string.toUpperCase());
    }
}
