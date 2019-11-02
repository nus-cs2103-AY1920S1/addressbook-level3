package seedu.address.ui;

import javafx.collections.ObservableMap;
import javafx.scene.layout.Region;
import seedu.address.logic.Logic;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.visual.DisplayIndicator;

/**
 * An abstract class for display controllers.
 */
public abstract class DisplayController extends UiPart<Region> {
    protected String title;

    public DisplayController(String fxmlFileName) {
        super(fxmlFileName);
    }

    protected void initTitle(String title) {
        this.title = title;
    }

    /**
     * Initalises attributes of the chart based on display indicator.
     *
     * @param logic            logic
     * @param displayIndicator display indicator
     * @throws ParseException suggested formats.
     */
    protected void initAttributes(Logic logic, DisplayIndicator displayIndicator) throws ParseException {
        initTitle(displayIndicator.toString());
        switch (displayIndicator.value) {
        case DisplayIndicator.POLICY_POPULARITY_BREAKDOWN:
            initPolicyPopularityBreakdown(logic);
            break;
        case DisplayIndicator.AGE_GROUP_BREAKDOWN:
            initAgeGroupBreakdown(logic);
            break;
        case DisplayIndicator.GENDER_BREAKDOWN:
            initGenderBreakdown(logic);
            break;
        default:
            throw new ParseException(DisplayIndicator.getMessageConstraints());
        }
    }

    protected abstract void initPolicyPopularityBreakdown(Logic logic);

    protected abstract void initAgeGroupBreakdown(Logic logic);

    protected abstract void initGenderBreakdown(Logic logic);

    protected abstract <T> T castData(ObservableMap<String, Integer> map);

    protected abstract void setChart();

    protected abstract void requireNonNullAttributes();

}
