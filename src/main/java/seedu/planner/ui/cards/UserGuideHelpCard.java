package seedu.planner.ui.cards;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Region;
import seedu.planner.logic.commands.util.HelpExplanation;
import seedu.planner.ui.UiPart;

/**
 * An UI component that displays information of a {@code HelpExplanation}.
 * @author 1nefootstep
 */
public class UserGuideHelpCard extends UiPart<Region> {
    private static final String FXML = "UserGuideHelpCard.fxml";
    @FXML
    private Hyperlink hyperlink;

    public static final String USERGUIDE_URL = "https://ay1920s1-cs2103t-t09-1.github.io/main/UserGuide.html";

    public UserGuideHelpCard() {
        super(FXML);
        hyperlink.setText(USERGUIDE_URL);
        hyperlink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                final Clipboard clipboard = Clipboard.getSystemClipboard();
                final ClipboardContent url = new ClipboardContent();
                url.putString(USERGUIDE_URL);
                clipboard.setContent(url);
                hyperlink.setVisited(false);
            }
        });
    }
}
