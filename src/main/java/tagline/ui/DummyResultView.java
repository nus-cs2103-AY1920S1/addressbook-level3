package tagline.ui;

/**
 * A dummy UI component to display a second view. To be replaced with a view which displays notes.
 */
public class DummyResultView extends ResultView {

    private static final String FXML = "DummyResultView.fxml";

    public DummyResultView() {
        super(FXML);
    }
}
