package dukecooks.testutil.diary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.diary.components.Page;

/**
 * A utility class containing a list of {@code Page} objects to be used in tests.
 */
public class TypicalPages {

    public static final Page SUSHI = new PageBuilder().withTitle("Sushi").withPageType("food")
            .withPageDescription("Sashimi is the greatest!").withImage("/images/sushi.jpg").build();
    public static final Page PHO = new PageBuilder().withTitle("Pho").withPageType("food")
                    .withPageDescription("Simply Delicious").withImage("/images/pho.jpg").build();
    public static final Page STEAK = new PageBuilder().withTitle("Steak").withPageType("food")
            .withPageDescription("Tender and Juicy!!").withImage("/images/steak.jpg").build();

    // Manually added - Page's details found in {@code CommandTestUtil}
    public static final Page PHO_PAGE = new PageBuilder().withTitle(CommandTestUtil.VALID_PHO_TITLE)
                    .withPageType(CommandTestUtil.VALID_PHO_TYPE)
                    .withPageDescription(CommandTestUtil.VALID_PHO_DESCRIPTION)
                    .withImage(CommandTestUtil.VALID_PHO_IMAGE).build();
    public static final Page SUSHI_PAGE = new PageBuilder().withTitle(CommandTestUtil.VALID_SUSHI_TITLE)
            .withPageType(CommandTestUtil.VALID_SUSHI_TYPE)
            .withPageDescription(CommandTestUtil.VALID_SUSHI_DESCRIPTION)
            .withImage(CommandTestUtil.VALID_SUSHI_IMAGE).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPages() {} // prevents instantiation

    public static List<Page> getTypicalPages() {
        return new ArrayList<>(Arrays.asList(SUSHI, PHO, STEAK));
    }
}
