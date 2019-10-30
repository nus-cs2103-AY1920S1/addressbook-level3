package seedu.address.ui;

import java.util.EnumSet;
import java.util.function.Consumer;

import javafx.beans.InvalidationListener;
import javafx.beans.binding.BooleanExpression;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Popup;
import javafx.stage.Window;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.CommandHistory;

/**
 * The UI component that is responsible for receiving user command inputs and offering user command suggestions.
 */
public class SuggestingCommandBox extends CommandBox {
    private final Popup popup = new Popup();
    private final ListView<String> listView = new ListView<>();
    private final CommandHistory commandHistory = new CommandHistory();
    private final SuggestionLogic suggestionLogic;
    private SuggestionMode suggestionMode = SuggestionMode.COMMAND_SUGGESTION;
    private int previousCaretPosition = 0;

    public SuggestingCommandBox(CommandExecutor commandExecutor, SuggestionLogic suggestionLogic) {
        super(commandExecutor);
        this.suggestionLogic = suggestionLogic;

        setupListView();
        setupPopup();
        setupHistoryNavigation();
        setupCommandTextField();

        UiUtil.onSceneReady(commandTextField, scene -> {
            commandTextField.requestFocus();
        });
    }

    private void setupPopup() {
        popup.setAutoFix(false);
        popup.getContent().setAll(listView);
        bindPopupPosition();
        bindShowHidePopup();
    }

    /**
     * Setup the necessary bindings that will cause the popup to show or hide.
     * Popup will only be shown when the command TextField is in focus and the user has typed something.
     */
    private void bindShowHidePopup() {
        final BooleanExpression isCommandTextFieldFocused = commandTextField.focusedProperty();
        final BooleanExpression hasInput = commandTextField.textProperty().isNotEmpty();

        final BooleanExpression shouldShowPopupExpression = isCommandTextFieldFocused.and(hasInput);
        final Consumer<Window> setupShowHide = window -> {
            shouldShowPopupExpression.addListener((unused1, unused2, shouldShowPopup) -> {
                if (shouldShowPopup) {
                    popup.show(window);
                } else {
                    popup.hide();
                }
            });
        };
        UiUtil.onWindowReady(commandTextField, setupShowHide);
    }

    /**
     * Setup the necessary bindings that will reposition the suggestions Popup when the command TextField moves.
     */
    private void bindPopupPosition() {
        final InvalidationListener repositionPopup = (observable -> {
            if (!popup.isShowing()) {
                return;
            }

            final double cellHeight = listView.getFixedCellSize();
            assert cellHeight > 0;
            final int maxNumSuggestions = 5;
            final int numSuggestionsToShow = Math.min(listView.getItems().size(), maxNumSuggestions);

            final double popupHeight = numSuggestionsToShow * cellHeight;
            listView.setMaxHeight(popupHeight);

            final double commandTextFieldHeight = commandTextField.getHeight();
            final double fullHeight = commandTextFieldHeight + popupHeight;

            double verticalOffset;

            // calculate the expected bottom-left Point2D of the popup window if it's placed below the command input box
            final Point2D popupBottomLeftPoint = commandTextField.localToScreen(0, fullHeight);
            if (UiUtil.isPointUserVisible(popupBottomLeftPoint, UiUtil.Bounds.VERTICAL)) {
                // there's enough space to place the popup window below the command input box, so we'll do that
                verticalOffset = commandTextFieldHeight;
            } else {
                // not enough space to place the popup window below the command input box, so we'll place it above
                // instead
                verticalOffset = popupHeight * -1;
            }

            final Point2D absolutePosition = commandTextField.localToScreen(0, verticalOffset);
            popup.setX(absolutePosition.getX());
            popup.setY(absolutePosition.getY());
        });

        final Consumer<Window> setupBindings = window -> {
            window.xProperty().addListener(repositionPopup);
            window.yProperty().addListener(repositionPopup);
            window.heightProperty().addListener(repositionPopup);
            listView.itemsProperty().addListener((unused, oldSuggestionsSource, newSuggestionsSource) -> {
                // reposition the popup whenever the underlying items re-filters itself
                oldSuggestionsSource.removeListener(repositionPopup);
                newSuggestionsSource.addListener(repositionPopup);

                // trigger a repositioning because the suggestions source has changed
                repositionPopup.invalidated(null);
            });

            popup.showingProperty().addListener(repositionPopup);
        };

        UiUtil.onWindowReady(commandTextField, setupBindings);
    }

    private void setupListView() {
        listView.setId("suggestions-list");
        listView.setFixedCellSize(17);
        listView.setFocusTraversable(false);
        listView.prefWidthProperty().bind(commandTextField.widthProperty());
        UiUtil.redirectKeyCodeEvents(commandTextField, listView, KeyCode.TAB);
        UiUtil.addKeyCodeListener(listView, KeyCode.TAB, this::onListViewTabKey);
    }

    /**
     * Recompute the suggestions based on the command in the {@link #commandTextField} and its caret.
     */
    private void recomputeSuggestions() {
        final String commandText = commandTextField.getText();
        final int caretPosition = commandTextField.getCaretPosition();

        final ObservableList<String> suggestions = suggestionLogic.getSuggestions(commandText, caretPosition);
        setSuggestionsSource(suggestions);
    }

    private void setupCommandTextField() {
        /*
        JavaFX's events are fired in this order: textProperty invalidation then caretPosition invalidation.

        At the textProperty invalidation, the caretPosition property lags behind. For example, if the user currently
        has a command like "abc" with the caret after the letter "c" (i.e. "abc|") then types one new character "d",
        the textProperty listener will fire with the new text "abcd" but checking for the caretPosition at this point
        will still say the caret is between c and d (i.e. "abc|d"). Only later at the caretPosition invalidation does
        the caretPosition update itself to be "abcd|".

        For simplicity and since suggestions are sensitive to the position of the caret, we recalculate suggestions
        when the caretPosition invalidates, as that seems to be the point at which the data state is consistent with the
        user interface state. This event fires in all the following cases: after the user types in a new character,
        after the user deletes one character, when the user manually moves the caret by keyboard arrow keys and when
        the user manually moves the caret by clicking. This recalculation is seen in the ChangeListener for the
        caretPositionProperty().

        An edge case occurs if somehow the text itself changes but the caretPosition doesn't. This may happen if the
        user currently has a command of length n and atomically overwrites it with a different command but with the same
        length n. For example, the user already has "abc" in the command box then highlights all text and pastes "def".
        Since the caret remains at position 3 throughout, our above caretPosition recalculation doesn't fire, so the new
        suggestions for command "def" don't appear, which is a problem. The only way to force a recalculation just for
        this specific case is to compare the length of the old command and the new command (i.e. "abc".length() vs
        "def".length()) and check if the caret position remains unchanged. This is why the ChangeListener from the
        caretPositionProperty() updates a previousCaretPosition variable so that the ChangeListener on the
        textProperty() can force a recalculation since a caretPosition invalidation will never be fired for this case.
         */

        commandTextField.caretPositionProperty().addListener((unused1, unused2, caretPosition) -> {
            previousCaretPosition = caretPosition.intValue();
            recomputeSuggestions();
        });

        commandTextField.textProperty().addListener((unused, oldText, newText) -> {
            final int newCaretPosition = commandTextField.getCaretPosition();
            if (oldText.length() == newText.length()
                    && !oldText.equals(newText)
                    && previousCaretPosition == newCaretPosition) {
                /*
                We know the text changed but the caret didn't move, user might have copy-pasted a new command with
                exactly the same length as the old command, so in this edge case, we'll specifically recompute the
                suggestions since the caretPositionProperty() invalidation listener will not fire.
                 */
                recomputeSuggestions();
            }
        });
    }

    /**
     * Handles what happens when a user selects a suggestion by pressing the Tab key.
     *
     * @param keyEvent A {@link KeyEvent} representing the {@link KeyCode#TAB} event.
     */
    private void onListViewTabKey(final KeyEvent keyEvent) {
        assert keyEvent.getCode().equals(KeyCode.TAB);

        final MultipleSelectionModel<String> selectionModel = listView.getSelectionModel();
        if (selectionModel.isEmpty()) {
            if (listView.getItems().isEmpty()) {
                return;
            }
            selectionModel.selectFirst();
        }
        keyEvent.consume();

        final String commandText = commandTextField.getText();
        final int caretPosition = commandTextField.getCaretPosition();
        final String selectedItem = selectionModel.getSelectedItem();

        final var newCommandBoxState = suggestionLogic.selectSuggestion(commandText, caretPosition, selectedItem);
        setCommandText(newCommandBoxState.commandText);
        setCaretPosition(newCommandBoxState.caretPosition);
    }


    private void setCaretPosition(final int position) {
        commandTextField.positionCaret(position);
    }

    private void setCommandText(final String commandText) {
        commandTextField.setText(commandText);
    }

    private void setSuggestionsSource(final ObservableList<String> suggestionsSource) {
        listView.setItems(suggestionsSource);
    }

    private void setupHistoryNavigation() {
        final EnumSet<KeyCode> commandHistoryNavigationKeys = EnumSet.of(KeyCode.UP, KeyCode.DOWN);

        // Redirect the UP/DOWN keypresses to the listView only when there is a command in the commandTextField
        UiUtil.redirectKeyCodeEvents(commandTextField, listView, commandHistoryNavigationKeys, (keyEvent) -> {
            if (commandTextField.getText().isEmpty()) {
                suggestionMode = SuggestionMode.HISTORY_COMMAND_NAVIGATION;
                return false;
            }
            return true;
        });

        /*
         When an UP/DOWN keypress is triggered on the commandTextField and is not redirected,
         go through the user's past commands
        */
        UiUtil.addKeyCodeListener(commandTextField, commandHistoryNavigationKeys, keyEvent -> {
            if (commandTextField.getText().isEmpty()) {
                commandHistory.resetHistoryPointer();
            } else if (suggestionMode == SuggestionMode.COMMAND_SUGGESTION) {
                return;
            }

            final KeyCode userDirection = keyEvent.getCode();
            String commandText = null;

            switch (userDirection) {
            case UP:
                commandText = commandHistory.getPreviousCommand();
                break;
            case DOWN:
                commandText = commandHistory.getNextCommand();
                break;
            default:
                throw new IllegalStateException("Unexpected KeyCode: " + userDirection);
            }

            if (null != commandText) {
                commandTextField.setText(commandText);
                commandTextField.positionCaret(commandText.length());
            }
        });
    }

    @FXML
    @Override
    protected void handleCommandEntered() {
        final String userInput = commandTextField.getText();
        commandHistory.add(userInput);
        suggestionMode = SuggestionMode.COMMAND_SUGGESTION;
        commandHistory.resetHistoryPointer();

        super.handleCommandEntered();
    }

    /**
     * Suggestion Modes of the SuggestingCommandBox class
     */
    enum SuggestionMode {
        COMMAND_SUGGESTION, HISTORY_COMMAND_NAVIGATION
    }

    /**
     * Represents a class that can provide command suggestions.
     */
    public interface SuggestionLogic {
        /**
         * Gets the suggested values of a {@code commandText} at the {@code caretPosition}.
         *
         * @param commandText   The full command input.
         * @param caretPosition The position of the caret within the {@code commandText}.
         * @return A list of suggested values at the {@code caretPosition}.
         */
        ObservableList<String> getSuggestions(final String commandText, final int caretPosition);

        /**
         * Selects a suggestion at the {@code caretPosition} within the {@code commandText}. Provides the new
         * {@code commandText} along with the desired caret position within the {@link SelectionResult}.
         *
         * @param commandText   The full command input.
         * @param caretPosition The position of the caret within the {@code commandText}.
         * @param selectedValue The value that was selected at the {@code caretPosition}.
         * @return The new {@code commandText} along with the desired caret position within the {@link SelectionResult}.
         */
        SelectionResult selectSuggestion(
                final String commandText, final int caretPosition, final String selectedValue);

        /**
         * Represents the desired state of the {@link SuggestingCommandBox} in terms of its {@code commandText} and
         * {@code caretPosition}.
         */
        class SelectionResult {
            public final String commandText;
            public final int caretPosition;

            SelectionResult(final String commandText, final int position) {
                CollectionUtil.requireAllNonNull(commandText, position);

                this.commandText = commandText;
                this.caretPosition = position;
            }

            public static SelectionResult of(final String commandText) {
                return new SelectionResult(commandText, commandText.length());
            }

            public static SelectionResult of(final String commandText, final int position) {
                return new SelectionResult(commandText, position);
            }
        }
    }
}
