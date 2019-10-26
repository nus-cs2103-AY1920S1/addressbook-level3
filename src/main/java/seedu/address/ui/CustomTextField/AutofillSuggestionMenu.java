package seedu.address.ui;

import java.util.Collection;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Window;

/**
 * Context Menu to show suggestions to replace text in a Text Area.
 */
public class AutofillSuggestionMenu extends ContextMenu {
    private FilteredList<String> matchingSuggestions;
    private ObservableList<String> suggestions;
    private TextArea t;
    private SimpleStringProperty matchingText;
    private Color matchColour;
    private Color completionColour;

    public AutofillSuggestionMenu(TextArea t) {
        super();
        this.t = t;

        suggestions = FXCollections.observableArrayList();
        matchingSuggestions = new FilteredList<>(suggestions);
        matchingText = new SimpleStringProperty();

        matchColour = Color.RED;
        completionColour = Color.ORANGE;

        t.textProperty().addListener((a, b, text) -> {
            matchingSuggestions.setPredicate(x -> x.startsWith(text.trim()));
            matchingText.setValue(text.trim());
            if (matchingText.get().length() > 0) {
                showSuggestions();
            } else {
                hide();
            }
        });

        setOnAction(this::replaceText);
    }

    /**
     * Set the suggestions that can be shown.
     * @param suggestions A Collection of possible suggestions.
     */
    public void setSuggestions(Collection<String> suggestions) {
        this.suggestions.setAll(suggestions);
    }

    /**
     * Show the context menu.
     */
    private void showSuggestions() {
        if (matchingSuggestions.size() > 0) {
            this.show(t, Side.BOTTOM, 0, 0);
            return;
        }
        hide();
    }

    /**
     * Replaces the text of the text area with the targeted suggestion.
     * @param event The event which targets the menu item containing the suggestion.
     */
    private void replaceText(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getTarget();
        TextFlow textFlow = (TextFlow) menuItem.getGraphic();
        t.clear();
        textFlow.getChildren().forEach(x -> {
            for (Character c : ((Text)x).getText().toCharArray()) {
                t.appendText(c.toString());
            }
        });
    }

    @Override
    protected void show() {
        if (matchingText.get().isEmpty()) {
            return;
        }
        populateList(this, matchingSuggestions,matchingText.get());
        super.show();
    }

    @Override
    public void show(Node anchor, Side side, double dx, double dy) {
        if (matchingText.get().isEmpty()) {
            return;
        }
        populateList(this, matchingSuggestions,matchingText.get());
        super.show(anchor, side, dx, dy);
    }

    @Override
    public void show(Window owner) {
        if (matchingText.get().isEmpty()) {
            return;
        }
        populateList(this, matchingSuggestions,matchingText.get());
        super.show(owner);
    }

    @Override
    public void show(Window ownerWindow, double anchorX, double anchorY) {
        if (matchingText.get().isEmpty()) {
            return;
        }
        populateList(this, matchingSuggestions,matchingText.get());
        super.show(ownerWindow, anchorX, anchorY);
    }

    /**
     * Populates the context menu with suggestions.
     * @param m The context menu.
     * @param matchingSuggestions The list of suggestions.
     * @param match The matching text.
     */
    public void populateList(ContextMenu m, FilteredList<String> matchingSuggestions, String match) {
        m.getItems().clear();
        for (String suggestion : matchingSuggestions) {
            String completion = suggestion.replaceFirst(match, "");
            TextFlow graphic = getGraphic("", match, completion);
            MenuItem item = new MenuItem();
            item.setGraphic(graphic);
            m.getItems().add(item);
        }
    }

    /**
     * Creates a TextFlow to represent the menu item being displayed with the matching text in the suggestion highlighted.
     * @param start The text before the match.
     * @param match The matching text.
     * @param after The text after the match.
     * @return
     */
    public  TextFlow getGraphic(String start, String match, String after) {
        Text completionTextBeforeMatch = new Text(start);
        completionTextBeforeMatch.setFill(completionColour);
        Text matchingText = new Text(match);
        matchingText.setFill(matchColour);
        Text completionTextAfterMatch = new Text(after);
        completionTextAfterMatch.setFill(completionColour);
        TextFlow graphic = new TextFlow(completionTextBeforeMatch, matchingText, completionTextAfterMatch);
        return graphic;
    }

}
