package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.eatery.Eatery;
import seedu.address.model.eatery.Tag;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";

    private static final String K = "";
    private static final String WEBVIEW_WRAPPER = "<html style=\"background: #424242;\">%s</html>";
    private static final String MAPS_WRAPPER = "<iframe width=\"100%%\" height=\"330\" frameborder=\"0\" "
            + "style=\"border:0\" src=\"https://www.google.com/maps/embed/v1/search?q=%s&key=%s\" "
            + "allowfullscreen></iframe>";

    private final ReviewListPanel reviewListPanel;

    @FXML
    private Label commandFeedback;

    @FXML
    private Label eateryName;
    @FXML
    private Label eateryCategory;
    @FXML
    private Label eateryAddress;
    @FXML
    private FlowPane eateryTags;
    @FXML
    private WebView eateryMap;
    @FXML
    private Label reviewHeader;
    @FXML
    private StackPane reviewListPanelPlaceholder;

    public ResultDisplay(ReviewListPanel reviewListPanel) {
        super(FXML);
        eateryMap.getEngine().loadContent(String.format(WEBVIEW_WRAPPER, ""));
        this.reviewListPanel = reviewListPanel;
    }

    public void setFeedbackToUser(CommandResult commandResult) {
        reset();

        String feedbackToUser = commandResult.getFeedbackToUser();
        Eatery eateryToShow = commandResult.getEateryToShow();

        requireNonNull(feedbackToUser);
        commandFeedback.setText(feedbackToUser);

        if (eateryToShow != null) {
            // Basic info
            eateryName.setText(eateryToShow.getIsOpen()
                    ? eateryToShow.getName().fullName : String.format("%s [closed]", eateryToShow.getName().fullName));
            eateryCategory.setText(eateryToShow.getCategory().getName());
            eateryAddress.setText(eateryToShow.getAddress().value);

            // Tags
            eateryToShow.getTags().stream()
                    .sorted(Comparator.comparing(Tag::getName))
                    .forEach(tag -> eateryTags.getChildren().add(new Label(String.format("#%s", tag.getName()))));

            // Map
            String doc = String.format(MAPS_WRAPPER,
                    URLEncoder.encode(eateryToShow.getAddress().value, StandardCharsets.UTF_8),
                    new String(Base64.getDecoder().decode(K)));
            eateryMap.getEngine().loadContent(String.format(WEBVIEW_WRAPPER, doc));

            // Reviews
            reviewHeader.setText("Reviews");
            reviewListPanelPlaceholder.getChildren().add(reviewListPanel.getRoot());
        }
    }

    public void setFeedbackToUser(Exception e) {
        requireNonNull(e);
        reset();
        commandFeedback.setText(e.getMessage());
    }

    /**
     * Resets the UI to its initial empty state.
     */
    private void reset() {
        commandFeedback.setText("");

        eateryName.setText("");
        eateryCategory.setText("");
        eateryAddress.setText("");
        eateryTags.getChildren().clear();

        eateryMap.getEngine().loadContent(String.format(WEBVIEW_WRAPPER, ""));

        reviewHeader.setText("");
        reviewListPanelPlaceholder.getChildren().clear();
    }

}
