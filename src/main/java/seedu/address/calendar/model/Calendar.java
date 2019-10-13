package seedu.address.calendar.model;

public class Calendar {
    Month month;
    int currentYear;


    public Calendar() {
        java.util.Calendar currentDate = java.util.Calendar.getInstance();

        currentYear = currentDate.get(java.util.Calendar.YEAR);

        int currentUnformattedMonth = currentDate.get(java.util.Calendar.MONTH);
        MonthOfYear currentMonth = MonthOfYear.convertJavaMonth(currentUnformattedMonth);

        month = new Month(currentMonth, currentYear);
    }

    public Month getMonth() {
        return month;
    }

    // todo: upgrade this method LOL
    public void updateMonth(String str) {
        MonthOfYear newMonth = MonthOfYear.valueOf(str);
        month = new Month(newMonth, 2019);
    }
}
