package dukecooks.model.util;

import dukecooks.model.Image;
import dukecooks.model.diary.components.Page;
import dukecooks.model.diary.components.PageDescription;
import dukecooks.model.diary.components.Title;

/**
 * Contains utility methods for populating {@code DiaryRecords} with sample data.
 */
public class SamplePagesDataUtil {

    public static Page[] getSamplePages() {
        return new Page[]{
            new Page(new Title("Pho"), new PageDescription("Amazing food from Vietnam!"),
                    new Image(SampleImageUtil.PATH_TO_PHO)),
            new Page(new Title("ChickenRice"), new PageDescription("Tasty as always!"),
                    new Image(SampleImageUtil.PATH_TO_CHICKEN_RICE)),
            new Page(new Title("Sushi"),
                    new PageDescription("Enjoy some freshly sliced sashimi!"),
                        new Image(SampleImageUtil.PATH_TO_SUSHI)),
        };
    }
}


