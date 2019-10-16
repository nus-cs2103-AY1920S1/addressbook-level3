package seedu.address.ui.layouts;

import seedu.address.ui.UiPart;

import javax.swing.plaf.synth.Region;
import java.net.URL;

public abstract class Layout extends UiPart<Region> {

    public Layout(URL fxmlFileUrl) {
        super(fxmlFileUrl);
    }

    public Layout(String fxmlFileName) {
        super(fxmlFileName);
    }
}
