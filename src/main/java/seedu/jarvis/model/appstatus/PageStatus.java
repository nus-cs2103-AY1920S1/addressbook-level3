package seedu.jarvis.model.appstatus;

/**
 * Represents the page status of the model. This is essentially a wrapper around a single {@code PageType} object so
 * so the model just needs to change this every single time a command is called.
 */
public class PageStatus {

    private PageType pageType;

    public PageStatus(PageType pageType) {
        this.pageType = pageType;
    }

    public void setPageType(PageType pageType) {
        this.pageType = pageType;
    }

    public PageType getPageType() {
        return pageType;
    }
}
