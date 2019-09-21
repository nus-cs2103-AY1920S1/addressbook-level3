package seedu.address.ui;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Window;

/**
 * Contains utility methods used by the various *UiPart classes.
 */
public class UiUtil {
    private static final Map<Node, List<Consumer<Scene>>> SCENE_READY_CONSUMERS = new WeakHashMap<>();

    /**
     * Schedules the {@code onSceneReady} code to run when the {@code targetNode} is added to a {@link Scene}
     *
     * @param targetNode   The {@link Node} whose {@link Node#sceneProperty()} to wait on.
     * @param onSceneReady The code to execute once the {@code targetNode}'s {@link Node#sceneProperty()} is ready.
     */
    public static void onSceneReady(final Node targetNode, final Consumer<Scene> onSceneReady) {
        requireAllNonNull(targetNode, onSceneReady);

        List<Consumer<Scene>> list = SCENE_READY_CONSUMERS.get(targetNode);
        if (list == null) {
            list = new ArrayList<>();
            SCENE_READY_CONSUMERS.put(targetNode, list);

            final List<Consumer<Scene>> finalList = list;
            final ChangeListener<Scene> listener = (unused1, oldScene, newScene) -> {
                if (!(oldScene == null && newScene != null)) {
                    return;
                }

                for (final Consumer<Scene> sceneConsumer : finalList) {
                    sceneConsumer.accept(newScene);
                }

                finalList.clear();
            };

            targetNode.sceneProperty().addListener(listener);
        }

        list.add(onSceneReady);
    }

    /**
     * Schedules the {@code onSceneReady} code to run when the {@code targetNode} is added to a {@link Window}
     *
     * @param targetNode    The {@link Node} whose {@link Node#sceneProperty()} to wait on.
     * @param onWindowReady The code to execute once the {@code targetNode}'s {@link Node#sceneProperty()} is ready.
     */
    public static void onWindowReady(final Node targetNode, final Consumer<Window> onWindowReady) {
        onSceneReady(targetNode, scene -> {
            onWindowReady.accept(scene.getWindow());
        });
    }

    /**
     * Listens for {@link KeyEvent#KEY_PRESSED} events from {@code node} and passes it to {@code keyEventConsumer}
     * if {@code eventPredicate} returns true.
     *
     * @param node             The {@link Node} where the {@link KeyEvent#KEY_PRESSED} events will occur.
     * @param eventPredicate   A predicate that checks if a {@link KeyEvent} should be
     *                         handled by {@code keyEventConsumer}.
     * @param keyEventConsumer The code to execute if the {@link KeyEvent#KEY_PRESSED} events
     *                         passes the {@code eventPredicate}.
     */
    private static void matchKeyEvents(final Node node,
                                       final Function<KeyEvent, Boolean> eventPredicate,
                                       final Consumer<KeyEvent> keyEventConsumer) {
        requireAllNonNull(node, eventPredicate, keyEventConsumer);

        node.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            final Boolean isInterestedEvent = eventPredicate.apply(keyEvent);
            if (!isInterestedEvent) {
                return;
            }

            keyEventConsumer.accept(keyEvent);
        });
    }

    /**
     * Redirects {@link KeyEvent#KEY_PRESSED} events from {@code eventSource} to {@code eventTarget}
     * if it is in the {@code keyCodes}.
     *
     * @param eventSource The {@link Node} where the original {@link KeyEvent#KEY_PRESSED} events will occur.
     * @param eventTarget The {@link Node} where the {@link KeyEvent#KEY_PRESSED} events will be redirected to.
     * @param keyCodes    The {@link KeyCode}s that will be redirected to the {@code eventTarget}.
     */
    public static void redirectKeyCodeEvents(final Node eventSource, final Node eventTarget,
                                             final KeyCode... keyCodes) {
        requireAllNonNull(eventSource, eventTarget, keyCodes);

        addKeyCodeListener(eventSource, keyEvent -> {
            keyEvent.consume();
            eventTarget.fireEvent(keyEvent);
        }, keyCodes);
    }

    /**
     * Schedules the {@code keyEventConsumer} code to run when a {@link KeyEvent#KEY_PRESSED} event happens on the
     * {@code node} and the key pressed is in the {@code interestedKeyCodes}.
     *
     * @param node               The {@link Node} where the {@link KeyEvent#KEY_PRESSED} events will occur.
     * @param interestedKeyCodes The {@link KeyCode}s that will trigger the {@code keyEventConsumer}.
     * @param keyEventConsumer   The code to execute if the {@link KeyCode} is in the {@code keyCodes}.
     */
    public static void addKeyCodeListener(final Node node, final Collection<KeyCode> interestedKeyCodes,
                                          final Consumer<KeyEvent> keyEventConsumer) {
        requireAllNonNull(node, keyEventConsumer, interestedKeyCodes);

        final Function<KeyEvent, Boolean> keyCodeMatcher = (keyEvent -> {
            return interestedKeyCodes.contains(keyEvent.getCode());
        });
        matchKeyEvents(node, keyCodeMatcher, keyEventConsumer);
    }

    /**
     * Schedules the {@code keyEventConsumer} code to run when a {@link KeyEvent#KEY_PRESSED} event happens on the
     * {@code node} and the key pressed is in the {@code interestedKeyCodes}.
     *
     * @param node               The {@link Node} where the {@link KeyEvent#KEY_PRESSED} events will occur.
     * @param keyEventConsumer   The code to execute if the {@link KeyCode} is in the {@code keyCodes}.
     * @param interestedKeyCodes The {@link KeyCode}s that will trigger the {@code keyEventConsumer}.
     */
    public static void addKeyCodeListener(final Node node, final Consumer<KeyEvent> keyEventConsumer,
                                          final KeyCode... interestedKeyCodes) {
        addKeyCodeListener(node, List.of(interestedKeyCodes), keyEventConsumer);
    }

    /**
     * Schedules the {@code keyEventConsumer} code to run when a {@link KeyEvent#KEY_PRESSED} event happens on the
     * {@code node} and the key pressed matches the {@code interestedKeyCode}.
     *
     * @param node              The {@link Node} where the {@link KeyEvent#KEY_PRESSED} events will occur.
     * @param interestedKeyCode The {@link KeyCode} that will trigger the {@code keyEventConsumer}.
     * @param keyEventConsumer  The code to execute if the {@link KeyCode} is in the {@code keyCodes}.
     */
    public static void addKeyCodeListener(final Node node, final KeyCode interestedKeyCode,
                                          final Consumer<KeyEvent> keyEventConsumer) {
        addKeyCodeListener(node, keyEventConsumer, interestedKeyCode);
    }

}
