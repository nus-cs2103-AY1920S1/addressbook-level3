package seedu.address.calendar.model;

public class Calendar {
    private Month monthShown;
    private int currentYear;
    private boolean hasVisibleUpdates;

    public Calendar() {
        java.util.Calendar currentDate = java.util.Calendar.getInstance();

        currentYear = currentDate.get(java.util.Calendar.YEAR);

        int currentUnformattedMonth = currentDate.get(java.util.Calendar.MONTH);
        MonthOfYear currentMonth = MonthOfYear.convertJavaMonth(currentUnformattedMonth);

        monthShown = new Month(currentMonth, currentYear);
        hasVisibleUpdates = false;
    }

    public Month getMonth() {
        return monthShown;
    }

    public void updateMonthShown(Month updatedMonth) {
        monthShown = updatedMonth;
        hasVisibleUpdates = true;
    }

    /**
     * Checks whether there are any visible update that has to be shown to user.
     * @return {@code true} if and only if there are visible updates that can be shown to user
     */
    public boolean hasViewUpdates() {
        return hasVisibleUpdates;
    }

    /**
     * Marks visible update as complete, i.e. latest visible update has been shown to user.
     */
    public void completeUpdate() {
        hasVisibleUpdates = false;
    }
}
