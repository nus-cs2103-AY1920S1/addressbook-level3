package com.typee.ui.game;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Adds highlight to words.
 */
public class TextHighlighter {
    /**
     * Returns the TextFlow of {@code word}.
     *
     * @param word The word that is to be converted into TextFlow.
     */
    public static TextFlow convertToTextFlowUsing(String word) {
        /**
         * TODO: Add CSS Highlighting
         */
        return new TextFlow(new Text(word));
    }
}
