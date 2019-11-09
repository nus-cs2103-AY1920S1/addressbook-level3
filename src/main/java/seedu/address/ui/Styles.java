package seedu.address.ui;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

//@@author SebastianLie
/**
 * helper class for autocomplete textfield
 */
public class Styles {

    /**
     * highlights a certain portion of text
     * @param text - string with text
     * @param textToHighlight - string to select in text
     * @return - TextFlow
     */
    public static TextFlow buildTextFlow(String text, String textToHighlight) {
        assert text != null : "Text cannot be empty";
        assert textToHighlight != null : "Text to be highlighted cannot be null";
        assert text.contains(textToHighlight) : "Text to highlight must be in original";

        String caseIndependent = text.toLowerCase();
        String caseIndependentFilter = textToHighlight.toLowerCase();

        int highlightIndex = caseIndependent.indexOf(caseIndependentFilter);
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
