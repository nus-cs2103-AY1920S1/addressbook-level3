package seedu.address.ui.modules;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import seedu.address.commons.util.AppUtil;
import seedu.address.ui.AvatarImage;
import seedu.address.ui.UiPart;

public class MainTitlePanel extends UiPart<Region> {

    private static final String FXML = "MainTitlePanel.fxml";
    private static final Image LOGO = AppUtil.getImage("/images/logo.png");

    @FXML
    BorderPane logoPlaceholder;

    @FXML
    Label playedTimesText;

    @FXML
    Label favoriteWordBankText;

    @FXML
    ImageView avatarImageView;

    public MainTitlePanel(int playedTimes, String favoriteWordBankTitle, int avatarId) {
        super(FXML);

        ImageView titleImage = new ImageView();
        titleImage.setImage(LOGO);
        titleImage.setPreserveRatio(true);
        titleImage.setScaleX(2.5);
        titleImage.setScaleY(2.5);
        logoPlaceholder.setCenter(titleImage);
        playedTimesText.setText(playedTimes
            + (playedTimes == 1
                ? " time"
                : " times"));
        favoriteWordBankText.setText(favoriteWordBankTitle);

        Image avatarImage = AvatarImage.get(avatarId);
        avatarImageView.setImage(avatarImage);
    }
}
