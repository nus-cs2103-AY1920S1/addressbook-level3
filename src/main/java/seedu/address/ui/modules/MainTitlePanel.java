package seedu.address.ui.modules;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import seedu.address.commons.util.AppUtil;
import seedu.address.statistics.WordBankStatistics;
import seedu.address.ui.AvatarImage;
import seedu.address.ui.UiPart;

public class MainTitlePanel extends UiPart<Region> {

    private static final String FXML = "MainTitlePanel.fxml";
    private static final Image LOGO = AppUtil.getImage("/images/logo.png");

    @FXML
    private BorderPane logoPlaceholder;

    @FXML
    private Label playedTimesText;

    @FXML
    private Label timesText;

    @FXML
    private Label favoriteWordBankText;

    @FXML
    private ImageView avatarImageView;

    public MainTitlePanel(int playedTimes, WordBankStatistics maxWbStats, int avatarId) {
        super(FXML);

        ImageView titleImage = new ImageView();
        titleImage.setImage(LOGO);
        titleImage.setPreserveRatio(true);
        titleImage.setScaleX(2.5);
        titleImage.setScaleY(2.5);
        logoPlaceholder.setCenter(titleImage);
        playedTimesText.setText(playedTimes + "");
        timesText.setText(playedTimes == 1 ? " time" : " times");
        favoriteWordBankText.setText(maxWbStats.getWordBankName());

        Image avatarImage = AvatarImage.get(avatarId);
        avatarImageView.setImage(avatarImage);
    }
}
