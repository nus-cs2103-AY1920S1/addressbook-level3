package dukecooks.testutil.diary;

import dukecooks.model.Image;
import dukecooks.model.diary.components.Page;
import dukecooks.model.diary.components.PageDescription;
import dukecooks.model.diary.components.PageType;
import dukecooks.model.diary.components.Title;

/**
 * A utility class to help with building Page objects.
 */
public class PageBuilder {

    public static final String DEFAULT_TITLE = "Sushi";
    public static final String DEFAULT_DESCRIPTION = "This is a test description!!";
    public static final String DEFAULT_PAGE_TYPE = "food";
    public static final String DEFAULT_IMAGE = "/images/sushi.jpg";

    private Title title;
    private PageDescription description;
    private PageType pageType;
    private Image image;

    public PageBuilder() {
        title = new Title(DEFAULT_TITLE);
        description = new PageDescription(DEFAULT_DESCRIPTION);
        pageType = new PageType(DEFAULT_PAGE_TYPE);
        image = new Image(DEFAULT_IMAGE);
    }

    /**
     * Initializes the PageBuilder with the data of {@code pageToCopy}.
     */
    public PageBuilder(Page pageToCopy) {

        title = pageToCopy.getTitle();
        description = pageToCopy.getDescription();
        pageType = pageToCopy.getPageType();
        image = pageToCopy.getImage();
    }

    /**
     * Sets the {@code Title} of the {@code Page} that we are building.
     */
    public PageBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Sets the {@code PageDescription} of the {@code Page} that we are building.
     */
    public PageBuilder withPageDescription(String description) {
        this.description = new PageDescription(description);
        return this;
    }

    /**
     * Sets the {@code PageType} of the {@code Page} that we are building.
     */
    public PageBuilder withPageType(String pageType) {
        this.pageType = new PageType(pageType);
        return this;
    }

    /**
     * Sets the {@code Image} of the {@code Page} that we are building.
     */
    public PageBuilder withImage(String image) {
        this.image = new Image(image);
        return this;
    }

    public Page build() {
        return new Page(title, pageType, description, image);
    }
}
