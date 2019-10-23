package seedu.address.ui.panels;

import java.net.URL;

import seedu.address.ui.UiPart;

public abstract class PanelComponent<T> extends UiPart<T> {

    /**
     * Constructs a UiPart with the specified FXML file URL. The FXML file must not specify the {@code fx:controller}
     * attribute.
     *
     * @param fxmlFileUrl
     */
    public PanelComponent(URL fxmlFileUrl) {
        super(fxmlFileUrl);
    }

    /**
     * Constructs a UiPart using the specified FXML file within {@link #FXML_FILE_FOLDER}.
     *
     * @param fxmlFileName
     * @see #UiPart(URL)
     */
    public PanelComponent(String fxmlFileName) {
        super(fxmlFileName);
    }

    /**
     * Constructs a UiPart with the specified FXML file URL and root object. The FXML file must not specify the {@code
     * fx:controller} attribute.
     *
     * @param fxmlFileUrl
     * @param root
     */
    public PanelComponent(URL fxmlFileUrl, T root) {
        super(fxmlFileUrl, root);
    }

    /**
     * Constructs a UiPart with the specified FXML file within {@link #FXML_FILE_FOLDER} and root object.
     *
     * @param fxmlFileName
     * @param root
     * @see #UiPart(URL, T)
     */
    public PanelComponent(String fxmlFileName, T root) {
        super(fxmlFileName, root);
    }
}
