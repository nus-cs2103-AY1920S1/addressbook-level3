package com.typee.ui.game;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Adds highlight to words.
 */
public class TextHighlighter {
    /**
     * Returns the TextFlow of {@code word} with highlighting on text that player has typed.
     *
     * @param word The word that is to be converted into TextFlow.
     * @param playerInput The text typed by the player that will be highlighted.
     */
    public static TextFlow convertToTextFlowUsing(String playerInput, String word) {
        if (word.indexOf(playerInput) == 0 && playerInput.length() <= word.length()) {
            Text highlightedText = new Text(word.substring(0, playerInput.length()));
            highlightedText.getStyleClass().add("withShadow");
            Text remainingText = new Text(word.substring(playerInput.length()));
            return new TextFlow(highlightedText, remainingText);
        }
        Text text = new Text(word);
        return new TextFlow(text);
    }

    /**
     * Returns the TextFlow of {@code word} without highlighting.
     *
     * @param word The word that is to be converted into TextFlow.
     */
    public static TextFlow convertToTextFlowUsing(String word) {
        Text text = new Text(word);
        text.getStyleClass().add("whiteText");
        return new TextFlow(text);
    }
}
