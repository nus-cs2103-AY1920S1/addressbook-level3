package seedu.address.ui;

import javafx.scene.Scene;
import seedu.address.logic.commands.CommandResult;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PageScenes {
    private List<Scene> pageScenes;
    private Scene mainPageScene;

    // todo: change this parameter to the specified scenes to minimise possible errors
    // keep PageScene constructor package private to prevent creation of this class elsewhere
    PageScenes(Scene mainPageScene, Scene... scenes) {
        this.mainPageScene = mainPageScene;
        pageScenes = Stream.of(scenes)
                .collect(Collectors.toList());
    }

    public Scene getPage(CommandResult commandResult) {
        String requestedPage = commandResult.getFeedback();
        Optional<Scene> requestedPageScene = pageScenes.stream().filter(p -> p.toString().equals(requestedPage)).
                findFirst();
        // todo: when all pages have been added, throw error if requestedPageScene isEmpty
        return requestedPageScene.orElseGet(() -> SamplePage.getScene());
    }

    public Scene getMainPageScene() {
        return mainPageScene;
    }

}
