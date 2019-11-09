package seedu.address.achievements.logic.commands;

import seedu.address.logic.commands.CommandResult;

public class AchievementsCommandResult extends CommandResult {
    private final boolean showAddressBook;

    private final boolean showCalendar;

    private final boolean showDiary;

    private final boolean showItinerary;

    private final boolean showFinancial;

    public AchievementsCommandResult(String feedbackToUser, boolean showAddressBook, boolean showCalendar,
                                     boolean showDiary, boolean showItinerary, boolean showFinancial) {
        super(feedbackToUser, false,false);
        this.showAddressBook = showAddressBook;
        this.showCalendar = showCalendar;
        this.showDiary = showDiary;
        this.showFinancial = showFinancial;
        this.showItinerary = showItinerary;
    }

    public boolean isShowAddressBook() {
        return showAddressBook;
    }

    public boolean isShowCalendar() {
        return showCalendar;
    }

    public boolean isShowDiary() {
        return showDiary;
    }

    public boolean isShowFinancial() {
        return showFinancial;
    }

    public boolean isShowItinerary() {
        return showItinerary;
    }
}
