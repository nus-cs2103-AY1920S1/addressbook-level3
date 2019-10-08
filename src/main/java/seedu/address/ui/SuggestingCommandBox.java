package seedu.address.ui;

import java.util.function.Consumer;
import java.util.function.Predicate;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.BooleanExpression;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Point2D;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.stage.Popup;
import javafx.stage.Window;
import seedu.address.logic.parser.SuggestingCommandUtil;

/**
 * The UI component that is responsible for receiving user command inputs and offering user command suggestions.
 */
public class SuggestingCommandBox extends CommandBox {
    private final Popup popup = new Popup();
    private final ListView<String> listView = new ListView<>();
    private final ObservableList<String> commandSuggestions;
    private final FilteredList<String> filteredCommandSuggestions;

    public SuggestingCommandBox(CommandExecutor commandExecutor) {
        super(commandExecutor);
        this.commandSuggestions = SuggestingCommandUtil.getCommandWords();
        filteredCommandSuggestions = new FilteredList<>(this.commandSuggestions);

        setupListView();
        setupPopup();
    }

    private void setupPopup() {
        popup.setAutoFix(false);
        popup.getContent().setAll(listView);
        bindPopupPosition();
        bindShowHidePopup();
    }

    /**
     * Setup the necessary bindings that will cause the popup to show or hide.
     * Popup will only be shown when the command TextField is in focus and there are user command suggestions.
     */
    private void bindShowHidePopup() {
        final BooleanExpression isCommandTextFieldFocused = commandTextField.focusedProperty();
        // final BooleanExpression hasCommandSuggestions = Bindings.size(filteredCommandSuggestions).greaterThan(0);

        final BooleanExpression shouldShowPopupExpression = isCommandTextFieldFocused;
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

            final double commandTextFieldHeight = commandTextField.getHeight();
            final double popupHeight = popup.getHeight();
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
            popup.showingProperty().addListener((unused1, unused2, isShowing) -> {
                // TODO: find a better way to force a popup reposition when it toggles from hidden to shown state
                if (!isShowing) {
                    return;
                }
                Platform.runLater(() -> {
                    repositionPopup.invalidated(null);
                });
            });
        };

        UiUtil.onWindowReady(commandTextField, setupBindings);
    }

    private void setupListView() {
        listView.setId("suggestions-list");
        listView.setMaxHeight(100); // TODO: flexible height
        listView.setFocusTraversable(false);
        listView.setItems(filteredCommandSuggestions);
        listView.prefWidthProperty().bind(commandTextField.widthProperty());
        UiUtil.redirectKeyCodeEvents(commandTextField, listView,
                KeyCode.UP,
                KeyCode.DOWN,
                KeyCode.TAB
        );

        final var listSelection = listView.getSelectionModel();

        popup.showingProperty().addListener((unused1, unused2, isShowing) -> {
            // pre-select the first item in the list when suggestions are being shown
            if (!isShowing || !listSelection.isEmpty()) {
                return;
            }

            listSelection.selectFirst();
        });

        UiUtil.addKeyCodeListener(listView, KeyCode.TAB, keyEvent -> {
            if (listSelection.isEmpty()) {
                if (listView.getItems().isEmpty()) {
                    return;
                }
                listSelection.selectFirst();
            }
            keyEvent.consume();

            final String selectedCommand = listSelection.getSelectedItem();
            commandTextField.setText(selectedCommand + " ");
            commandTextField.positionCaret(Integer.MAX_VALUE);
        });

        commandTextField.textProperty().addListener((unused1, unused2, userCommand) -> {
            final char spaceCharacter = ' ';
            int spaceCharIdx = userCommand.indexOf(spaceCharacter);
            if (spaceCharIdx == -1) {
                spaceCharIdx = userCommand.length();
            }

            final String userCommandWord = userCommand.substring(0, spaceCharIdx);

            if (commandSuggestions.contains(userCommandWord)) {
                // the userCommandWord exactly matches a command, so we stop showing suggestions
                filteredCommandSuggestions.setPredicate((commandWord) -> false);
            } else {
                final Predicate<String> commandMatcher = SuggestingCommandUtil.createSequenceMatcher(userCommandWord);
                filteredCommandSuggestions.setPredicate(commandMatcher);
            }
        });
    }
}
