package seedu.address.model;

/**
 * Encapsulates the schedule timetable in memory.
 */
public class Schedule {
    private String date;

    /**
     * Returns the first row of table.
     * @return the first row of table.
     */
    public Row getFirstRow() {
        return new Row();
    }

    /**
     * Returns a row in the table.
     * @param index the index of the row in the table.
     * @return a row in the table.
     */
    public Row getRow(int index) {
        return new Row();
    }

    /**
     * Returns a row in the table with the timing given
     * @param timing timing of the row.
     * @return a row in the table with the timing given
     */
    public Row getRow(String timing) {
        return new Row();
    }

    /**
     * Returns a column in the table.
     * @param index index of the column in the table.
     * @return a column in the table.
     */
    public Column getColumn(int index) {
        return new Column();
    }

    /**
     * Returns a column in the table with the interviewer's description given.
     * @param interviewerDesc the title of the column, which is the interviewer's description.
     * @return a column in the table with the interviewer's description given.
     */
    public Column getColumn(String interviewerDesc) {
        return new Column();
    }

    /**
     * Deletes a column in the table with the index given.
     * @param index the index of the column in the table.
     * @return the deleted column in the table with the index given.
     */
    public Column deleteColumn(int index) {
        return new Column();
    }

    /**
     * Deletes a column in the table with the interviewer's description given.
     * @param interviewerDesc the title of the column, which is the interviewer's description.
     * @return the deleted column in the table with the interviewer's description given.
     */
    public Column deleteColumn(String interviewerDesc) {
        return new Column();
    }
}
