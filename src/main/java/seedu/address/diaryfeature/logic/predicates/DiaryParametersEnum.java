package seedu.address.diaryfeature.logic.predicates;

/**
 * Enumerates possible diary entry params
 */
public enum DiaryParametersEnum {
    TITLE, DATE, PLACE, MEMORY;

    public static final String MESSAGE_CONSTRAINTS = "Only the following arguments are allowed: "
            + "address_book, calendar, diary,financial_tracker, itinerary, main";

    public static DiaryParametersEnum of(String string) {
        return DiaryParametersEnum.valueOf(string.toUpperCase());
    }
}
