package seedu.address.ui;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

//@@author SebastianLie
/**
 * helps to highlight user input in the suggestions offered by autocomplete
 */
public class Styles {

    /**
     * highlights a certain portion of text
     * @param text - string with text
     * @param textToHighlight - string to select in text
     * @return - TextFlow
     */
    public static TextFlow buildTextFlow(String text, String textToHighlight) throws IllegalArgumentException {
        //(text.equals("") && textToHighlight.equals(""))
        if (text == null || textToHighlight == null || (text.equals("") && textToHighlight.equals(""))) {
            throw new IllegalArgumentException("Text cannot be null or empty!");
        }

        String caseIndependent = text.toLowerCase();
        String caseIndependentToHighlight = textToHighlight.toLowerCase();

        assert caseIndependent.contains(caseIndependentToHighlight) : "Text to highlight must be in original";

        int highlightIndex = caseIndependent.indexOf(caseIndependentToHighlight);
        int highlightIndexWithinLength = Math.max(0, highlightIndex);

        Text textBefore = new Text(text.substring(0, highlightIndexWithinLength));
        Text textAfter = new Text(text.substring(highlightIndexWithinLength + textToHighlight.length()));
        Text textHighlighted = new Text(text.substring(highlightIndexWithinLength,
                highlightIndexWithinLength + textToHighlight.length()));

        textHighlighted.setFill(Color.LIGHTSKYBLUE);
        textHighlighted.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        return new TextFlow(textBefore, textHighlighted, textAfter);
    }
}
