package seedu.address.ui;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Logic;

/**
 * A singleton task that handles all UI for page navigation. It must be initialised using {@code getInstance} before
 * use.
 */
public class PageManager {
    private static List<Page> pages;
    private static Scene commonScene;
    private static Stage primaryStage;
    private static Logic guiSettingsLogic;
    private static Optional<PageManager> pageManager = Optional.empty();
    // prevent multiple instances of pages
    private PageManager(Stage primaryStage, Scene commonScene, Logic guiSettingsLogic, Page... pages) {
        this.primaryStage = primaryStage;
        this.commonScene = commonScene;
        this.guiSettingsLogic = guiSettingsLogic;
        this.pages = Stream.of(pages)
                .collect(Collectors.toList());
    }

    public static PageManager getInstance(Stage primaryStage, Scene commonScene,
                                          Logic guiSettingsLogic, Page... pages) {
        pageManager = Optional.of(new PageManager(primaryStage, commonScene, guiSettingsLogic, pages));
        return pageManager.get();
    }


    public static void getPage(PageType pageType) {
        if (pageManager.isEmpty()) {
            assert false : "Page manager has to be initialised before other pages can be retrieved";
        }
        pages.stream().forEach(p -> p.closeResources());

        Optional<Parent> requestedPage = pages.stream()
                .filter(p -> p.getPageType().equals(pageType))
                .map(p -> p.getParent())
                .findFirst();

        if (requestedPage.isEmpty()) {
            assert false : "Every get page command should have a page class implemented for it";
        }
        //@@author bjhoohaha-reused
        //Credits to : Asfal, Genuine Coder
        //https://www.genuinecoder.com/javafx-scene-switch-change-animation/
        Parent requestedRoot = requestedPage.get();
        //Fade out transition
        FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(500), commonScene.getRoot());
        fadeOutTransition.setFromValue(1.0);
        fadeOutTransition.setToValue(0.0);
        fadeOutTransition.play();
        commonScene.setRoot(requestedRoot);
        //Fade in transition
        FadeTransition fadeInTransition = new FadeTransition(Duration.millis(500), requestedRoot);
        fadeInTransition.setFromValue(0.0);
        fadeInTransition.setToValue(1.0);
        fadeInTransition.play();
    }

    /**
     * Closes the TravEzy application window when the user calls the exit command.
     */
    public static void closeWindows() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        guiSettingsLogic.setGuiSettings(guiSettings);
        primaryStage.hide();
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event -> {
            Platform.exit();
        });
        delay.play();
    }

    public static double getXPosition() {
        return primaryStage.getX() + primaryStage.getWidth() / 2;
    }

    public static double getYPosition() {
        return primaryStage.getY() + primaryStage.getHeight() / 2;
    }
}
