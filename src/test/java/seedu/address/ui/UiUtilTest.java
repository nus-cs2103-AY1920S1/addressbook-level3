package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

class UiUtilTest {
    private static Point2D originPoint;

    /**
     * Set up a JavaFX Application thread for test functions to use {@link Platform#runLater(Runnable)}.
     * May create a Java application icon on the macOS Dock.
     */
    @BeforeAll
    static void setup() {
        // TODO: find a better way to test JavaFX-related things without invoking JavaFX
        Platform.startup(() -> {
        });

        final var primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        originPoint = new Point2D(primaryScreenBounds.getMinX(), primaryScreenBounds.getMinY());
    }

    /**
     * Returns a new {@link Stage} instance by creating it on the JavaFX application thread.
     *
     * @return A new {@link Stage} instance.
     */
    private static Stage createStage() {
        final CompletableFuture<Stage> stageFuture = new CompletableFuture<>();
        Platform.runLater(() -> {
            stageFuture.complete(new Stage());
        });

        try {
            final Stage stage = stageFuture.get(2, TimeUnit.SECONDS);
            assertNotNull(stage);
            return stage;
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            fail(e);
            return null;
        }
    }

    @Test
    void onSceneReady_oneNodeOneHandler_success() {
        final Region testRegion = new Region();
        final CompletableFuture<Scene> sceneFuture = new CompletableFuture<>();

        UiUtil.onSceneReady(testRegion, sceneFuture::complete);
        final Scene expectedScene = new Scene(testRegion);

        try {
            final Scene receivedScene = sceneFuture.get(2, TimeUnit.SECONDS);
            assertEquals(expectedScene, receivedScene);
        } catch (ExecutionException | TimeoutException | InterruptedException e) {
            fail(e);
        }
    }

    @Test
    void onSceneReady_oneNodeManyHandlers_success() {
        final Region testRegion = new Region();
        final CompletableFuture<Scene> sceneFuture1 = new CompletableFuture<>();
        final CompletableFuture<Scene> sceneFuture2 = new CompletableFuture<>();

        UiUtil.onSceneReady(testRegion, sceneFuture1::complete);
        UiUtil.onSceneReady(testRegion, sceneFuture2::complete);
        final Scene expectedScene = new Scene(testRegion);

        try {
            final Scene receivedScene1 = sceneFuture1.get(2, TimeUnit.SECONDS);
            assertEquals(expectedScene, receivedScene1);

            final Scene receivedScene2 = sceneFuture2.get(2, TimeUnit.SECONDS);
            assertEquals(expectedScene, receivedScene2);
        } catch (ExecutionException | TimeoutException | InterruptedException e) {
            fail(e);
        }
    }

    @Test
    void onSceneReady_manyNodesOneHandlerEach_success() {
        final Region testRegion1 = new Region();
        final CompletableFuture<Scene> sceneFuture1 = new CompletableFuture<>();

        final Region testRegion2 = new Region();
        final CompletableFuture<Scene> sceneFuture2 = new CompletableFuture<>();

        final Group regions = new Group(testRegion1, testRegion2);

        UiUtil.onSceneReady(testRegion1, sceneFuture1::complete);
        UiUtil.onSceneReady(testRegion2, sceneFuture2::complete);
        final Scene expectedScene = new Scene(regions);

        try {
            final Scene receivedScene1 = sceneFuture1.get(2, TimeUnit.SECONDS);
            assertEquals(expectedScene, receivedScene1);

            final Scene receivedScene2 = sceneFuture2.get(2, TimeUnit.SECONDS);
            assertEquals(expectedScene, receivedScene2);
        } catch (ExecutionException | TimeoutException | InterruptedException e) {
            fail(e);
        }
    }

    @Test
    void onSceneReady_nodeNotAddedToScene_neverTriggered() {
        final Region testRegion = new Region();
        final CompletableFuture<Scene> sceneFuture = new CompletableFuture<>();

        UiUtil.onSceneReady(testRegion, sceneFuture::complete);

        Assertions.assertThrows(TimeoutException.class, () -> {
            sceneFuture.get(1, TimeUnit.SECONDS);
        });
    }

    @Test
    void onWindowReady_oneNodeOneHandler_success() {
        final Region testRegion = new Region();
        final Scene scene = new Scene(testRegion);
        final Stage expectedWindow = createStage();
        final CompletableFuture<Window> windowFuture = new CompletableFuture<>();

        UiUtil.onWindowReady(testRegion, windowFuture::complete);
        Platform.runLater(() -> {
            expectedWindow.setScene(scene);
        });

        try {
            final Window receivedWindow = windowFuture.get(1, TimeUnit.SECONDS);
            assertEquals(expectedWindow, receivedWindow);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            fail(e);
        }
    }

    @Test
    void onWindowReady_nodeNotAddedToWindow_neverTriggered() {
        final Region testRegion = new Region();
        final Scene scene = new Scene(testRegion);
        final CompletableFuture<Window> windowFuture = new CompletableFuture<>();

        UiUtil.onWindowReady(testRegion, windowFuture::complete);

        Assertions.assertThrows(TimeoutException.class, () -> {
            windowFuture.get(1, TimeUnit.SECONDS);
        });
    }

    private static KeyEvent createKeyEvent(final KeyCode keyCode) {
        return new KeyEvent(KeyEvent.KEY_PRESSED, keyCode.getChar(), "", keyCode, false, false, false, false);
    }

    @Test
    void redirectKeyCodeEvents_fireExpectedSingleKeyCodeEventOnSource_matchingKeyCodeEventsFiredOnTarget() {
        final Region eventSource = new Region();
        final Region eventTarget = new Region();

        final KeyCode expectedKeyCode = KeyCode.TAB;
        UiUtil.redirectKeyCodeEvents(eventSource, eventTarget, expectedKeyCode);

        final CompletableFuture<KeyEvent> keyEventFuture = new CompletableFuture<>();
        eventTarget.addEventFilter(KeyEvent.KEY_PRESSED, keyEventFuture::complete);

        final KeyEvent event = createKeyEvent(expectedKeyCode);
        eventSource.fireEvent(event);

        final KeyEvent receivedEvent = keyEventFuture.getNow(null);
        assertNotNull(receivedEvent);
        assertEquals(expectedKeyCode, receivedEvent.getCode());
    }

    @Test
    void redirectKeyCodeEvents_fireUnexpectedSingleKeyCodeEventOnSource_keyCodeEventFiredOnlyOnSource() {
        final Region eventSource = new Region();
        final CompletableFuture<KeyEvent> sourceKeyEventFuture = new CompletableFuture<>();
        eventSource.addEventFilter(KeyEvent.KEY_PRESSED, sourceKeyEventFuture::complete);

        final Region eventTarget = new Region();
        final CompletableFuture<KeyEvent> targetKeyEventFuture = new CompletableFuture<>();
        eventTarget.addEventFilter(KeyEvent.KEY_PRESSED, targetKeyEventFuture::complete);

        final KeyCode listeningKeyCode = KeyCode.TAB;
        UiUtil.redirectKeyCodeEvents(eventSource, eventTarget, listeningKeyCode);

        final KeyCode differentKeyCode = KeyCode.END;
        final KeyEvent event = createKeyEvent(differentKeyCode);
        eventSource.fireEvent(event);

        final KeyEvent targetReceivedEvent = targetKeyEventFuture.getNow(null);
        assertNull(targetReceivedEvent);

        final KeyEvent sourceReceivedEvent = sourceKeyEventFuture.getNow(null);
        assertNotNull(sourceReceivedEvent);
        assertEquals(differentKeyCode, sourceReceivedEvent.getCode());
    }

    @Test
    void redirectKeyCodeEvents_fireMultipleExpectedKeyCodeEventsOnSource_matchingKeyCodeEventsFiredOnTarget() {
        final Region eventSource = new Region();
        final Region eventTarget = new Region();

        final List<KeyCode> expectedKeyCodes = List.of(KeyCode.TAB, KeyCode.E, KeyCode.F10);
        UiUtil.redirectKeyCodeEvents(eventSource, eventTarget, expectedKeyCodes.toArray(new KeyCode[0]));

        final Deque<KeyEvent> receivedKeyEvents = new ArrayDeque<>();
        eventTarget.addEventFilter(KeyEvent.KEY_PRESSED, receivedKeyEvents::push);

        for (final KeyCode expectedKeyCode : expectedKeyCodes) {
            final KeyEvent event = createKeyEvent(expectedKeyCode);
            eventSource.fireEvent(event);
            assertEquals(expectedKeyCode, receivedKeyEvents.pop().getCode());
        }
    }

    @Test
    void addKeyCodeListener_fireExpectedSingleKeyCodeEvent_matchingKeyCodeEventFired() {
        final Region eventSource = new Region();

        final KeyCode expectedKeyCode = KeyCode.TAB;
        final KeyEvent event = createKeyEvent(expectedKeyCode);

        final CompletableFuture<KeyEvent> keyEventFuture = new CompletableFuture<>();
        UiUtil.addKeyCodeListener(eventSource, keyEventFuture::complete, expectedKeyCode);

        eventSource.fireEvent(event);

        final KeyEvent receivedEvent = keyEventFuture.getNow(null);
        assertNotNull(receivedEvent);
        assertEquals(expectedKeyCode, receivedEvent.getCode());
    }

    @Test
    void addKeyCodeListener_fireUnexpectedSingleKeyCodeEvent_noEventFired() {
        final Region eventSource = new Region();

        final KeyCode listeningKeyCode = KeyCode.TAB;
        final KeyEvent expectedEvent = createKeyEvent(listeningKeyCode);

        final CompletableFuture<KeyEvent> keyEventFuture = new CompletableFuture<>();
        UiUtil.addKeyCodeListener(eventSource, keyEventFuture::complete, listeningKeyCode);

        final KeyCode unexpectedKeyCode = KeyCode.END;
        final KeyEvent unexpectedEvent = createKeyEvent(unexpectedKeyCode);
        eventSource.fireEvent(unexpectedEvent);

        final KeyEvent receivedEvent = keyEventFuture.getNow(null);
        assertNull(receivedEvent);
    }

    @Test
    void addKeyCodeListener_fireMultipleExpectedKeyCodeEvents_matchingKeyCodeEventsFired() {
        final Region eventSource = new Region();
        final List<KeyCode> expectedKeyCodes = List.of(KeyCode.TAB, KeyCode.E, KeyCode.F10);
        final Deque<KeyEvent> receivedKeyEvents = new ArrayDeque<>();

        UiUtil.addKeyCodeListener(eventSource, expectedKeyCodes, receivedKeyEvents::push);

        for (final KeyCode expectedKeyCode : expectedKeyCodes) {
            final KeyEvent event = createKeyEvent(expectedKeyCode);
            eventSource.fireEvent(event);
            assertEquals(expectedKeyCode, receivedKeyEvents.pop().getCode());
        }
    }

    @Test
    void addKeyCodeListener_fireMultipleUnexpectedKeyCodesFired_noEventFired() {
        final Region eventSource = new Region();
        final List<KeyCode> listeningKeyCodes = List.of(KeyCode.TAB, KeyCode.E, KeyCode.F10);
        final Deque<KeyEvent> receivedKeyEvents = new ArrayDeque<>();

        UiUtil.addKeyCodeListener(eventSource, listeningKeyCodes, receivedKeyEvents::push);

        final List<KeyCode> unexpectedKeyCodes = List.of(KeyCode.ENTER, KeyCode.P, KeyCode.UP);
        for (final KeyCode unexpectedKeyCode : unexpectedKeyCodes) {
            final KeyEvent event = createKeyEvent(unexpectedKeyCode);
            eventSource.fireEvent(event);
        }

        assertTrue(receivedKeyEvents.isEmpty());
    }

    Point2D pointWithX(final Point2D point, final double x) {
        return new Point2D(x, point.getY());
    }

    Point2D pointWithY(final Point2D point, final double y) {
        return new Point2D(point.getX(), y);
    }

    @Test
    void isPointUserVisible_origin_true() {
        assertTrue(UiUtil.isPointUserVisible(originPoint));
    }

    @Test
    void isPointUserVisible_offscreenMinXy_false() {
        final Point2D offscreenMinXyPoint = new Point2D(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
        assertFalse(UiUtil.isPointUserVisible(offscreenMinXyPoint));
    }

    @Test
    void isPointUserVisible_offscreenMaxXy_false() {
        final Point2D offscreenMaxXyPoint = new Point2D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        assertFalse(UiUtil.isPointUserVisible(offscreenMaxXyPoint));
    }

    @Test
    void isPointUserVisible_offscreenOriginXMinY_false() {
        final Point2D originXMinYPoint = pointWithY(originPoint, Double.NEGATIVE_INFINITY);
        assertFalse(UiUtil.isPointUserVisible(originXMinYPoint));
    }

    @Test
    void isPointUserVisible_offscreenOriginXMaxY_false() {
        final Point2D originXMaxYPoint = pointWithY(originPoint, Double.POSITIVE_INFINITY);
        assertFalse(UiUtil.isPointUserVisible(originXMaxYPoint));
    }

    @Test
    void isPointUserVisible_offscreenMinXOriginY_false() {
        final Point2D minXOriginYPoint = pointWithX(originPoint, Double.NEGATIVE_INFINITY);
        assertFalse(UiUtil.isPointUserVisible(minXOriginYPoint));
    }

    @Test
    void isPointUserVisible_offscreenMaxXOriginY_false() {
        final Point2D maxXOriginYPoint = pointWithX(originPoint, Double.POSITIVE_INFINITY);
        assertFalse(UiUtil.isPointUserVisible(maxXOriginYPoint));
    }

    @Test
    void isPointUserVisibleTopBound_offscreenXoriginY_true() {
        final Point2D offscreenXoriginY = pointWithX(originPoint, Double.NEGATIVE_INFINITY);
        final UiUtil.Bounds bound = UiUtil.Bounds.TOP;

        assertTrue(UiUtil.isPointUserVisible(offscreenXoriginY, bound));
    }

    @Test
    void isPointUserVisibleTopBound_offscreenXoffscreenY_false() {
        final Point2D offscreenXoffscreenYPoint = new Point2D(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);
        final UiUtil.Bounds bound = UiUtil.Bounds.TOP;

        assertFalse(UiUtil.isPointUserVisible(offscreenXoffscreenYPoint, bound));
    }

    @Test
    void isPointUserVisibleRightBound_originXoffscreenY_true() {
        final Point2D originXoffscreenYPoint = pointWithY(originPoint, Double.POSITIVE_INFINITY);
        ;
        final UiUtil.Bounds bound = UiUtil.Bounds.RIGHT;

        assertTrue(UiUtil.isPointUserVisible(originXoffscreenYPoint, bound));
    }

    @Test
    void isPointUserVisibleRightBound_offscreenXoffscreenY_false() {
        final Point2D offscreenXoffscreenYPoint = new Point2D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        final UiUtil.Bounds bound = UiUtil.Bounds.RIGHT;

        assertFalse(UiUtil.isPointUserVisible(offscreenXoffscreenYPoint, bound));
    }

    @Test
    void isPointUserVisibleBottomBound_offscreenXoriginY_true() {
        final Point2D offscreenXoriginY = pointWithX(originPoint, Double.NEGATIVE_INFINITY);
        final UiUtil.Bounds bound = UiUtil.Bounds.BOTTOM;

        assertTrue(UiUtil.isPointUserVisible(offscreenXoriginY, bound));
    }

    @Test
    void isPointUserVisibleBottomBound_offscreenXoffscreenY_false() {
        final Point2D offscreenXoffscreenYPoint = new Point2D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        final UiUtil.Bounds bound = UiUtil.Bounds.BOTTOM;

        assertFalse(UiUtil.isPointUserVisible(offscreenXoffscreenYPoint, bound));
    }

    @Test
    void isPointUserVisibleLeftBound_originXoffscreenY_true() {
        final Point2D originXoffscreenYPoint = pointWithY(originPoint, Double.NEGATIVE_INFINITY);
        final UiUtil.Bounds bound = UiUtil.Bounds.LEFT;

        assertTrue(UiUtil.isPointUserVisible(originXoffscreenYPoint, bound));
    }

    @Test
    void isPointUserVisibleLeftBound_offscreenXoffscreenY_false() {
        final Point2D offscreenXoffscreenYPoint = new Point2D(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        final UiUtil.Bounds bound = UiUtil.Bounds.LEFT;

        assertFalse(UiUtil.isPointUserVisible(offscreenXoffscreenYPoint, bound));
    }

}
