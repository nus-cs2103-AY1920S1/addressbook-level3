package seedu.module.ui;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import seedu.module.model.module.Link;


/**
 * Button that will launch the contained link when clicked
 */
public class LinkButton extends Button {
    private Link link;

    public LinkButton(Link link) {
        super(link.name);
        if (link.isMarked()) {
            this.setText("\u2605" + link.name);
        }
        this.link = link;
        this.setTooltip(new Tooltip(link.name + "\n" + link.url));
        this.setPrefHeight(25);
        this.setPrefWidth(160);
        this.setOnAction(event -> this.link.launch());
    }
}
