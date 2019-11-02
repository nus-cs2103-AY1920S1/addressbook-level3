package dukecooks.model.util;

import java.net.URL;

import dukecooks.model.Image;
import dukecooks.model.diary.components.Page;
import dukecooks.model.diary.components.PageDescription;
import dukecooks.model.diary.components.Title;

/**
 * Contains utility methods for populating {@code DiaryRecords} with sample data.
 */
public class SamplePagesDataUtil {

    public static URL getResourceUrl(String fileName) {
        return SamplePagesDataUtil.class.getResource("/images/" + fileName);
    }

    public static Page[] getSamplePages() {
        return new Page[]{
            new Page(new Title("Pho"), new PageDescription("Amazing food from Vietnam!"),
                    new Image(getResourceUrl("pho.jpg"))),
            new Page(new Title("ChickenRice"), new PageDescription("Tasty as always!"),
                    new Image(getResourceUrl("chicken_rice.jpg"))),
            new Page(new Title("Sushi"),
                    new PageDescription("Enjoy some freshly sliced sashimi!"),
                        new Image(getResourceUrl("sushi.jpg"))),
        };
    }
}


