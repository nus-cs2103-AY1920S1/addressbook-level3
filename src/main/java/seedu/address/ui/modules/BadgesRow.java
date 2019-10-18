package seedu.address.ui.modules;

import javafx.fxml.FXML;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import seedu.address.commons.util.AppUtil;
import seedu.address.ui.UiPart;

public class BadgesRow extends UiPart<Region> {
    private static final String FXML = "BadgesRow.fxml";

    private static final String BADGE_PATH = "/images/badges/";
    private static final Image BADGE_1_BNW = AppUtil.getImage(BADGE_PATH + "normal_badge_bnw.png");
    private static final Image BADGE_2_BNW = AppUtil.getImage(BADGE_PATH + "medium_badge_bnw.png");
    private static final Image BADGE_3_BNW = AppUtil.getImage(BADGE_PATH + "hard_badge_bnw.png");
    private static final Image BADGE_1_COLOR = AppUtil.getImage(BADGE_PATH + "normal_badge.png");
    private static final Image BADGE_2_COLOR = AppUtil.getImage(BADGE_PATH + "medium_badge.png");
    private static final Image BADGE_3_COLOR = AppUtil.getImage(BADGE_PATH + "hard_badge.png");

    @FXML
    private ImageView badge1;

    @FXML
    private ImageView badge2;

    @FXML
    private ImageView badge3;

    public BadgesRow(boolean isReceived1, boolean isReceived2, boolean isReceived3) {
        super(FXML);

        if (isReceived1) {
            this.badge1.setImage(BADGE_1_COLOR);
        } else {
            this.badge1.setImage(BADGE_1_BNW);
        }

        if (isReceived2) {
            this.badge2.setImage(BADGE_2_COLOR);
        } else {
            this.badge2.setImage(BADGE_2_BNW);
        }

        if (isReceived3) {
            this.badge3.setImage(BADGE_3_COLOR);
        } else {
            this.badge3.setImage(BADGE_3_BNW);
        }

        Tooltip easyTooltip =  new Tooltip("Received on clearing easy mode.");
        easyTooltip.setShowDelay(Duration.ZERO);
        Tooltip mediumTooltip =  new Tooltip("Received on clearing medium mode.");
        mediumTooltip.setShowDelay(Duration.ZERO);
        Tooltip hardTooltip =  new Tooltip("Received on clearing hard mode.");
        hardTooltip.setShowDelay(Duration.ZERO);
        Tooltip.install(badge1, easyTooltip);
        Tooltip.install(badge2, mediumTooltip);
        Tooltip.install(badge3, hardTooltip);
    }

}
