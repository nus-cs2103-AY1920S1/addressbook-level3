package seedu.address.ui;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import seedu.address.logic.commands.CommandResult;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A singleton task that handles all UI for page navigation. It must be initialised using {@code getInstance} before
 * use.
 */
public class PageManager {
    private static Stage primaryStage;
    private static List<Page> pages;
    private static Scene mainPageScene;
    private static Optional<PageManager> pageManager = Optional.empty();

    // prevent multiple instances of pages
    private PageManager(Stage primaryStage, Scene mainPageScene, Page... pages) {
        this.primaryStage = primaryStage;
        this.mainPageScene = mainPageScene;
        this.pages = Stream.of(pages)
                .collect(Collectors.toList());
    }

    public static PageManager getInstance(Stage primaryStage, Scene mainPageScene, Page... pages) {
        pageManager = Optional.of(new PageManager(primaryStage, mainPageScene, pages));
        return pageManager.get();
    }


    public static void getPage(PageType pageType) {
        if (pageManager.isEmpty()) {
            assert false : "Page manager has to be initialised before other pages can be retrieved";
        }

        Optional<Scene> requestedPage = pages.stream()
                .filter(p -> p.getPageType().equals(pageType))
                .map(p -> p.getScene())
                .findFirst();

        if (requestedPage.isEmpty() && pageType.equals(PageType.MAIN)) {
            requestedPage = Optional.of(mainPageScene);
        } else if (requestedPage.isEmpty()) {
            assert false : "Every get page command should have a page class implemented for it";
        }
        //Credits to : Asfal, Genuine Coder
        //https://www.genuinecoder.com/javafx-scene-switch-change-animation/
        Scene requestedScene = requestedPage.get();
        //Fade out transition
        FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(500), primaryStage.getScene().getRoot());
        fadeOutTransition.setFromValue(1.0);
        fadeOutTransition.setToValue(0.0);
        fadeOutTransition.play();
        primaryStage.setScene(requestedScene);
        //Fade in transition
        FadeTransition fadeInTransition = new FadeTransition(Duration.millis(500), requestedPage.get().getRoot());
        fadeInTransition.setFromValue(0.0);
        fadeInTransition.setToValue(1.0);
        fadeInTransition.play();
    }
}
