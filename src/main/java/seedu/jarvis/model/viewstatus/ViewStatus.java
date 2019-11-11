package seedu.jarvis.model.viewstatus;

/**
 * Represents the page status of the model. This is essentially a wrapper around a single {@code PageType} object so
 * so the model just needs to change this every single time a command is called.
 */
public class ViewStatus {

    private ViewType viewType;

    public ViewStatus(ViewType viewType) {
        this.viewType = viewType;
    }

    public void setViewType(ViewType viewType) {
        this.viewType = viewType;
    }

    public ViewType getViewType() {
        return viewType;
    }
}
