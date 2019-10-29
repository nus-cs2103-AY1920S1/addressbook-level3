package thrift.ui;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Label;

/**
 * Factory class for making coloured labels.
 */
public class ColouredLabelFactory {

    //keyword reserved colours
    private static final String BACKGROUND_COLOR_GREEN = "-fx-background-color: #00897B";
    private static final String BACKGROUND_COLOR_RED = "-fx-background-color: #e53935";
    private static final String BACKGROUND_COLOR_BLUE = "-fx-background-color: #3949AB";
    private static final String BACKGROUND_COLOR_ORANGE = "-fx-background-color: #e65100";

    //default
    private static final String BACKGROUND_COLOR_PURPLE = "-fx-background-color: #755990";

    //unused colours
    //private static final String BACKGROUND_COLOR_LIGHT_GREEN = "-fx-background-color: #558B2F";
    //private static final String BACKGROUND_COLOR_NAVY_BLUE = "-fx-background-color: #3e7b91";
    //private static final String BACKGROUND_COLOR_YELLOW = "-fx-background-color: #FFA000";
    //private static final String BACKGROUND_COLOR_PINK = "-fx-background-color: #ec407a";
    //private static final String BACKGROUND_COLOR_GREY = "-fx-background-color: #959595";
    //private static final String BACKGROUND_COLOR_DARK_GREY = "-fx-background-color: #616161";
    //private static final String BACKGROUND_COLOR_BLACK = "-fx-background-color: #555555";
    //private static final String BACKGROUND_COLOR_WHITE = "-fx-background-color: #FFFFFF";

    private static Map<String, String> mapTagColour = new HashMap<String, String>(0);


    /**
     * Takes in a tag name and returns a JavaFX Label coloured based on it.
     *
     * @param tagName of the tag
     * @return a JavaFX Label coloured based on tag name
     */
    public static Label getColouredTagLabel(String tagName) {
        initMapTagColour();
        String trueTagName = tagName.toLowerCase();

        Label colouredLabel = new Label(tagName);

        if (mapTagColour.containsKey(trueTagName)) {
            colouredLabel.setStyle(mapTagColour.get(trueTagName));
        } else {
            colouredLabel.setStyle(BACKGROUND_COLOR_PURPLE);
        }

        return colouredLabel;
    }

    /**
     * Initialises the Tag-Colour map if it hasn't been initialised yet
     */
    public static void initMapTagColour() {
        if (mapTagColour.size() > 0) {
            return;
        }

        // Critical Expenses
        mapTagColour.put("debt", BACKGROUND_COLOR_RED);
        mapTagColour.put("fees", BACKGROUND_COLOR_RED);
        mapTagColour.put("gamble", BACKGROUND_COLOR_RED);

        // Sources of Income
        mapTagColour.put("allowance", BACKGROUND_COLOR_GREEN);
        mapTagColour.put("award", BACKGROUND_COLOR_GREEN);
        mapTagColour.put("salary", BACKGROUND_COLOR_GREEN);

        // Entertainment Expenses
        mapTagColour.put("shopping", BACKGROUND_COLOR_BLUE);
        mapTagColour.put("travel", BACKGROUND_COLOR_BLUE);
        mapTagColour.put("transport", BACKGROUND_COLOR_BLUE);

        // Food & Necessities
        mapTagColour.put("breakfast", BACKGROUND_COLOR_ORANGE);
        mapTagColour.put("brunch", BACKGROUND_COLOR_ORANGE);
        mapTagColour.put("dinner", BACKGROUND_COLOR_ORANGE);
        mapTagColour.put("groceries", BACKGROUND_COLOR_ORANGE);
        mapTagColour.put("lunch", BACKGROUND_COLOR_ORANGE);
        mapTagColour.put("snack", BACKGROUND_COLOR_ORANGE);
        mapTagColour.put("supper", BACKGROUND_COLOR_ORANGE);
    }
}
