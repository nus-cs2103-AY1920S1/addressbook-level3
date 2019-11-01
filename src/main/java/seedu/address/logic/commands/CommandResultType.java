package seedu.address.logic.commands;

/**
 * Represents the type of Command entered and the actionable task from the result.
 */
public enum CommandResultType {

    SHOW_HELP,
    SHOW_QUIZ_QUESTIONS,
    SHOW_QUIZ_ANSWERS,
    SHOW_QUIZ_ALL,
    SHOW_SLIDESHOW,
    SHOW_STATISTIC,
    SHOW_STUDENT,
    SHOW_SCHEDULE,
    SHOW_GROUP,
    SHOW_QUESTION,
    SHOW_QUESTION_SEARCH,
    SCHEDULE_SCREENSHOT,
    EXPORT_CALENDAR,
    EXIT,
    OTHER;

    private boolean isPrintable = false;
    private String printableName;

    public void setPrintable(boolean isPrintable) {
        this.isPrintable = isPrintable;
    }

    public boolean isPrintable() {
        return isPrintable;
    }

    public void setPrintableName(String fileName) {
        this.printableName = fileName;
    }

    public String getPrintableName() {
        return printableName;
    }
}
