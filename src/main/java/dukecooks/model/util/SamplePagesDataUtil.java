package dukecooks.model.util;

import dukecooks.model.Image;
import dukecooks.model.diary.components.Page;
import dukecooks.model.diary.components.PageDescription;
import dukecooks.model.diary.components.PageType;
import dukecooks.model.diary.components.Title;

/**
 * Contains utility methods for sample pages data.
 */
public class SamplePagesDataUtil {

    public static Page[] getSamplePages() {
        return new Page[]{
            new Page(new Title("Pho"), new PageType("food"),
                    new PageDescription("Phở, pronounced \"fuh,\" is a Vietnamese soup that is normally made with "
                            + "a bone-beef broth, banh pho noodles, and thinly sliced beef, "
                            + "that's often served with bean sprouts and other fresh herbs on the side."),
                    new Image(SampleImageUtil.PATH_TO_PHO)),
            new Page(new Title("Steak"), new PageType("food"), new PageDescription("Steak is usually grilled but can "
                    + "be pan-fried. It is often grilled in an attempt to replicate the flavor of steak cooked over "
                    + "the glowing coals of an open fire."),
                    new Image(SampleImageUtil.PATH_TO_STEAK)),
            new Page(new Title("Sushi"), new PageType("food"),
                    new PageDescription("Sushi is a popular Japanese dish made from seasoned rice with fish, egg, "
                            + "or vegetables. A sushi roll is shaped inside a thin sheet of seaweed. Sushi comes from"
                            + " a Japanese word meaning \"sour rice,\". "),
                        new Image(SampleImageUtil.PATH_TO_SUSHI)),
        };
    }
}


