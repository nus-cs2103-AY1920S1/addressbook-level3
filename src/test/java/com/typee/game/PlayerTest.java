package com.typee.game;

import static com.typee.testutil.TypicalEngagements.TYPICAL_APPOINTMENT;
import static com.typee.testutil.TypicalPlayers.END_GAME;
import static com.typee.testutil.TypicalPlayers.MID_GAME;
import static com.typee.testutil.TypicalPlayers.START_OF_GAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.typee.testutil.Assert;
import com.typee.testutil.PlayerBuilder;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class PlayerTest {

    private final Player player = new Player();

    @Test
    public void equals() {
        assertEquals(player, player);
        assertEquals(START_OF_GAME, player);
        assertNotEquals(MID_GAME, player);
        assertNotEquals(END_GAME, player);
        assertNotEquals(player, TYPICAL_APPOINTMENT);
    }

    @Test
    public void setInputAs_nullInput_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> player.setInputAs(null));
    }

    @Test
    public void setInputAs_validInput_setInput() {
        StringProperty stringProperty = new SimpleStringProperty();
        player.setInputAs(stringProperty);
        assertEquals(player.getInputTextProperty(), stringProperty);
    }

    @Test
    public void setInputTextAs_validInputText_setInputText() {
        StringProperty stringProperty = new SimpleStringProperty();
        player.setInputAs(stringProperty);
        player.setInputTextAs("John Maynard Keynes");
        assertEquals("John Maynard Keynes", player.getInputText());
    }

    @Test
    public void incrementScore_invalidInput_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> player.incrementScore(-1));
        Assert.assertThrows(IllegalArgumentException.class, () -> player.incrementScore(Integer.MIN_VALUE));
    }

    @Test
    public void incrementScore_validScore_incrementScore() {
        assertEquals(player.incrementScore(1).getScore(), new PlayerBuilder().withScore(1).build().getScore());
        assertEquals(player.incrementScore(757).getScore(), player.getScore());
        assertEquals(END_GAME.incrementScore(1).getScoreProperty(), END_GAME.getScoreProperty());
    }

    @Test
    public void decrementHealth_healthLessThanZero_setGameOVer() {
        assertEquals(MID_GAME.decrementHealth(50).getGameOverProperty().get(), END_GAME.getGameOverProperty().get());
    }

    @Test
    public void decrementHealth_validHealth_decrementHealth() {
        assertEquals(player.decrementHealth(1).getHealth(), new PlayerBuilder().withHealth(99).build().getHealth());
        assertEquals(player.decrementHealth(757).getHealth(), player.getHealth());
        assertEquals(END_GAME.decrementHealth(1).getHealthProperty(), END_GAME.getHealthProperty());
    }

    @Test
    public void isSamePlayer_inputSamePlayer_returnsTrue() {
        assertTrue(MID_GAME.isSamePlayer(MID_GAME));
        assertTrue(player.isSamePlayer(START_OF_GAME));
    }

    @Test
    public void hashcode() {
        // same values -> returns same hashcode
        assertEquals(player.hashCode(), new Player().hashCode());

        // different score value -> returns different hashcode
        assertNotEquals(player.hashCode(), new PlayerBuilder().withScore(1000).build().hashCode());

        // different health value -> returns different hashcode
        assertNotEquals(player.hashCode(), new PlayerBuilder().withHealth(50).hashCode());
    }
}
