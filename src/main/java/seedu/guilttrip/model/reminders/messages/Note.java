package seedu.guilttrip.model.reminders.messages;

import java.time.Period;
import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import seedu.guilttrip.model.entry.AutoExpense;
import seedu.guilttrip.model.entry.Budget;
import seedu.guilttrip.model.entry.Entry;

/**
 * Additional notes that user may wish to be displayed in the notification pop up.
 */
public class Note extends Cell {
    public static final String TYPE = "Message";

    public static final String ENTRY_TYP_PH = "#eTyp#";
    public static final String CAT_PH = "#cat#";
    public static final String DESC_PH = "#desc#";
    public static final String AMT_PH = "#amt#";
    public static final String DATE_PH = "#date#";
    public static final String TAGS_PH = "#tags#";
    public static final String AMT_SPENT_PH = "#xAmt#";
    public static final String PERIOD_PH = "#p#";
    public static final String START_PH = "#start#";
    public static final String END_PH = "#end#";
    public static final String NUM_PUR_PH = "#xNum#";
    public static final String FREQ_PH = "#freq#";
    public static final String LT_PH = "#LT#";
    private static final String defaultFont = "/fonts/Faraco_Hand.ttf";
    private static final double defaultFontSize = 10;
    private Entry entry;
    private String noteTemplate;
    private String displayedNote;
    private String fontName = defaultFont;
    private double fontSize = defaultFontSize;
    private Font font;

    public Note(String noteTemplate) {
        this.noteTemplate = noteTemplate;
        this.displayedNote = noteTemplate;
    }

    /**
     * Automatically called by generalReminder when budget is updated.
     * @param entry
     */
    public void update(Entry entry) {
        this.entry = entry;
        displayedNote = processNote(noteTemplate);
    }

    /**
     * Parses a string and filling in the placeholders with entry details
     */
    private String processNote(String note) {
        note.replace(ENTRY_TYP_PH, getEntryType());
        note.replace(CAT_PH, getCategory());
        note.replace(DESC_PH, getDesc());
        note.replace(AMT_PH, getAmt());
        note.replace(DATE_PH, getDate());
        note.replace(TAGS_PH, getTags());
        if (entry instanceof Budget) {
            note.replace(AMT_SPENT_PH, getSpent());
            note.replace(PERIOD_PH, getPeriod());
            note.replace(END_PH, getEnd());
            note.replace(NUM_PUR_PH, getExpensesNum());
        } else if (entry instanceof AutoExpense) {
            note.replace(FREQ_PH, getFrequency());
            note.replace(LT_PH, getLastTime());
        }
        return note;
    }

    //===== Methods use to parse macros ====
    //=== General details ===
    private String getEntryType() {
        return entry.getType();
    }

    private String getCategory() {
        return entry.getCategory().getCategoryName();
    }

    private String getDesc() {
        return entry.getDesc().toString();
    }

    private String getAmt() {
        return entry.getAmount().toString();
    }

    private String getDate() {
        return entry.getDate().toString();
    }

    private String getTags() {
        return String.join(",", new ArrayList(entry.getTags()));
    }

    public void setFont(String font, double size) {
        this.fontName = font;
        this.fontSize = size;
        this.font = Font.font(fontName, fontSize);
    }

    //=== Budget Specific Details ===
    private String getSpent() {
        assert (entry instanceof Budget);
        Budget budget = (Budget) entry;
        return budget.getSpent().toString();
    }

    /**
     * gets Period period.toString(). May have to change later if they use an actual period.
     */
    private String getPeriod() {
        assert (entry instanceof Budget);
        Budget budget = (Budget) entry;
        String output = budget.getPeriod().toString();
        return output;
    }

    private String getEnd() {
        assert (entry instanceof Budget);
        Budget budget = (Budget) entry;
        return budget.getEndDate().toString();
    }

    private String getExpensesNum() {
        assert (entry instanceof Budget);
        Budget budget = (Budget) entry;
        budget.updateSpent();
        return Integer.toString(budget.getFilteredExpenses().size());
    }

    //=== Get AutoExpense Specific Details ===

    private String getFrequency() {
        assert (entry instanceof AutoExpense);
        AutoExpense aExpense = (AutoExpense) entry;
        return Period.from((aExpense.getFrequency().getPeriod())).getDays() + " day(s)";
    }

    private String getLastTime() {
        assert (entry instanceof AutoExpense);
        AutoExpense aExpense = (AutoExpense) entry;
        return aExpense.getLastTime().toString();
    }

    public Node getNode() {
        Text text = new Text(displayedNote);
        text.setFont(font);
        return text;
    }
}
