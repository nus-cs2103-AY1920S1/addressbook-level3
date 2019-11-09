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

    public static Page[] getFoodSamplePages() {
        return new Page[]{
            new Page(new Title("Pho"), new PageType("food"),
                        new PageDescription("Phở, pronounced \"fuh,\" is a Vietnamese soup that is normally made with"
                                + " a bone-beef broth, banh pho noodles, and thinly sliced beef, that's often "
                                + "served with bean sprouts and other fresh herbs on the side. Not to be confused with"
                                + " Japanese ramen, which is usually made with wheat noodles, pho is made with rice "
                                + "noodles. The most common is pho nam, which originates in Southern Vietnam, and pho"
                                + " bac, which is from Northern Vietnam and considered to be the original pho."),
                        new Image(SampleImageUtil.PATH_TO_PHO)),
            new Page(new Title("Chicken Rice"), new PageType("food"),
                        new PageDescription("The Hainanese chicken rice is a dish that consists of succulent steamed "
                                + "white chicken cut into bite-size pieces and served on fragrant rice with some light "
                                + "soy sauce. The dish is topped with sprigs of coriander leaf and sesame oil, "
                                + "and accompanied by a garlic-chilli dip. The rich flavour of the rice comes from the "
                                + "grains that have been pre-fried in chicken fat and then cooked in chicken broth."),
                        new Image(SampleImageUtil.PATH_TO_CHICKEN_RICE)),
            new Page(new Title("Sushi"), new PageType("food"),
                        new PageDescription("Sushi is a popular Japanese dish made from seasoned rice with fish, "
                                + "egg, or vegetables. A sushi roll is shaped inside a thin sheet of seaweed. "
                                + "Sushi comes from a Japanese word meaning \"sour rice,\" and it's the rice that's at "
                                + "the heart of sushi, even though most Americans think of it as raw fish."),
                        new Image(SampleImageUtil.PATH_TO_SUSHI)),
        };
    }

    public static Page[] getExerciseSamplePages() {
        return new Page[]{
            new Page(new Title("Planking"), new PageType("exercise"),
                        new PageDescription("This simple maneuver can help strengthen your center of gravity."
                                + " Also known as “the bridge,” the plank is a versatile core "
                                + "strengthening exercise that helps athletes improve core stability. The plank has "
                                + "its roots in yoga and, "
                                + "like most yoga poses, involves isometric, or long-hold, muscle contractions."
                                + " You can even do it with your partner if you wish to!"),
                        new Image(SampleImageUtil.PATH_TO_PLANKS)),
            new Page(new Title("Zumba"), new PageType("exercise"),
                        new PageDescription("Zumba is a fitness program that combines Latin and international music"
                                + " dance moves. Zumba routines incorporate interval training — "
                                + "alternating fast and slow rhythms — and resistance training."
                                + " An aerobic activity, Zumba can count toward the 150 minutes a week of moderate "
                                + "aerobic activity or 75 minutes a week of vigorous aerobic activity recommended"
                                + " for most healthy adults by the Department of Health and Human Services."),
                        new Image(SampleImageUtil.PATH_TO_ZUMBA)),
        };
    }

    public static Page[] getMixedSamplePages() {
        return new Page[]{
            new Page(new Title("Superfoods"), new PageType("health"),
                        new PageDescription("Superfoods are foods — mostly plant-based but also some "
                                + "fish and dairy — "
                                + "that are thought to be nutritionally dense and thus good for one's health. "
                                + "Blueberries, salmon, kale and acai are just a few examples of foods that have"
                                + " garnered the \"superfood\" label. Superfoods contain a variety of nutrients, "
                                + "such as antioxidants, which are thought to ward off cancer."),
                        new Image(SampleImageUtil.PATH_TO_SUPERFOODS)),
            new Page(new Title("Sprints"), new PageType("exercise"),
                        new PageDescription("Sprinting is running over a short distance in a limited period of time. "
                                + "It is used in many sports that incorporate running, typically as a way of quickly "
                                + "reaching a target or goal, or avoiding or catching an opponent. It can help to "
                                + "improve aerobic fitness and cardiovascular health. Plus, it burns calories and can"
                                + " build strength, among other things."),
                        new Image(SampleImageUtil.PATH_TO_SPRINTS)),
        };
    }

    public static Page[] getMeatSamplePages() {
        return new Page[]{
            new Page(new Title("Chicken Rice"), new PageType("food"),
                        new PageDescription("The Hainanese chicken rice is a dish that consists of succulent steamed "
                                + "white chicken cut into bite-size pieces and served on fragrant rice with some light "
                                + "soy sauce. The dish is topped with sprigs of coriander leaf and sesame oil, "
                                + "and accompanied by a garlic-chilli dip. The rich flavour of the rice comes from the "
                                + "grains that have been pre-fried in chicken fat and then cooked in chicken broth."),
                        new Image(SampleImageUtil.PATH_TO_CHICKEN_RICE)),
            new Page(new Title("Steak"), new PageType("food"),
                        new PageDescription("A steak is a meat generally sliced across the muscle fibers, potentially"
                                + " including a bone. Exceptions, in which the meat is sliced parallel to the fibers, "
                                + "include the skirt steak cut from the plate, the flank steak cut from the "
                                + "abdominal muscles, and the silverfinger steak cut from the loin and includes "
                                + "three rib bones. It is usually grilled, but can be pan-fried! They can also be "
                                + "cooked in sauce, or even made into patties for hamburgers."),
                        new Image("/images/steak.jpg")),
            new Page(new Title("Ayam Penyet"), new PageType("food"),
                        new PageDescription("Ayam penyet is Indonesian — more precisely East Javanese cuisine — "
                                + "fried chicken dish consisting of fried chicken that is smashed with the pestle "
                                + "against mortar to make it softer, served with sambal, slices of cucumbers, "
                                + "fried tofu and tempeh. It is also known for its spicy sambal, which is made with a"
                                + " mixture of chilli, anchovies, tomatoes, shallots, garlics, shrimp paste, tamarind"
                                + " and lime juice. Like its namesake, the mixture is then smashed into a paste "
                                + "to be eaten with the dish."),
                        new Image("/images/ayampenyet.jpg")),
        };
    }

    public static Page[] getSingaporeSamplePages() {
        return new Page[] {
            new Page(new Title("Rojak"), new PageType("food"),
                        new PageDescription("Rojak is a local salad of mixed vegetables and fruits, drizzled with a "
                                + "sweet and sour sauce comprising local prawn paste, sugar and lime. This Asian salad"
                                + " is a rich mix of vegetables and fruits. Fresh vegetables, such as water spinach "
                                + "and bean sprouts, are blanched. Others, such as cucumbers and Chinese turnips,"
                                + " are sliced in an angled fashion to add crunch. Sour, tangy flavours come from "
                                + "other ingredients such as sliced pineapples."),
                        new Image("/images/rojak.jpg")),
            new Page(new Title("CarrotCake"), new PageType("food"),
                        new PageDescription("This savoury carrot cake has no carrot, "
                                + "at least not of the orange variety."
                                + " Instead, the core ingredients of the cake are rice flour and white radish, "
                                + "which some call white carrot. The mixture is steamed, then cut into cubes and "
                                + "fried with garlic, eggs and preserved radish called 'chai poh'.  It is served black "
                                + "(fried with sweet dark soya sauce) or white (original)."),
                        new Image("/images/carrotcake.jpg"))
        };
    }

    public static Page[] getSlimmingSamplePages() {
        return new Page[] {
            new Page(new Title("Low Sugar Foods"), new PageType("health"),
                        new PageDescription("These low-sugar snacks combine protein, fiber, and good-for-you fat "
                                + "without loading up on the added sugar found in many processed foods and desserts. "
                                + "We’ve made sure that each of these picks fall in the single digits range for "
                                + "added sugar — none contain more than 8 grams per serving. You'll also be able to "
                                + "choose from a variety of flavours - from sweet, spicy to savory!"),
                        new Image("/images/lowsugar.jpg")),
            new Page(new Title("Planks"), new PageType("exercise"),
                        new PageDescription("This simple maneuver can help strengthen your center of gravity."
                                + " Also known as “the bridge,” the plank is a "
                                + "versatile core strengthening exercise "
                                + "that helps athletes improve core stability. The plank has its roots in yoga and, "
                                + "like most yoga poses, involves isometric, or long-hold, muscle contractions."
                                + " You can even do it with your partner if you wish to!"),
                        new Image(SampleImageUtil.PATH_TO_PLANKS))
        };
    }

    public static Page[] getStudentSamplePages() {
        return new Page[] {
            new Page(new Title("Sprints"), new PageType("exercise"),
                        new PageDescription("Sprinting is running over a short distance in a limited period of time. "
                                + "It is used in many sports that incorporate running, typically as a way of quickly "
                                + "reaching a target or goal, or avoiding or catching an opponent. It can help to "
                                + "improve aerobic fitness and cardiovascular health. Plus, it burns calories and can"
                                + " build strength, among other things. Most importantly, it's quick and perfect for "
                                + "busy students looking to burn that extra fat away!"),
                        new Image(SampleImageUtil.PATH_TO_SPRINTS)),
            new Page(new Title("Goji Berries"), new PageType("health"),
                        new PageDescription("Deemed as one of the superfoods, goji berries are life-savers for "
                                + "students. It has various functions, ranging from protecting the eyes, providing "
                                + "immune system support, promoting healthy skin and even protection against cancer! "
                                + "What's more, goji berries can be eaten in many different ways. You could eat it "
                                + "raw, make a goji berry tea or even add in into soups!"),
                        new Image(SampleImageUtil.PATH_TO_GOJI)),
            new Page(new Title("Sushi"), new PageType("food"),
                        new PageDescription("Sushi is a popular Japanese dish made from seasoned rice with fish, "
                                + "egg, or vegetables. A sushi roll is shaped inside a thin sheet of seaweed. "
                                + "Sushi comes from a Japanese word meaning \"sour rice,\" and it's the rice that's at "
                                + "the heart of sushi, even though most Americans think of it as raw fish. It is "
                                + "also super easy to prepare sushi, making it an absolute perfect choice for the busy "
                                + "students to prepare!"),
                        new Image(SampleImageUtil.PATH_TO_SUSHI)),
        };
    }
}


