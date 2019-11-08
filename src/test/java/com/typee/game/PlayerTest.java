package com.typee.game;

import static com.typee.testutil.TypicalEngagements.TYPICAL_APPOINTMENT;
import static com.typee.testutil.TypicalPlayers.END_GAME;
import static com.typee.testutil.TypicalPlayers.MID_GAME;
import static com.typee.testutil.TypicalPlayers.START_OF_GAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import com.typee.testutil.Assert;
import com.typee.testutil.PlayerBuilder;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


class PlayerTest {

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
    void setInputTextAs_validInputText_setInputText() {
        StringProperty stringProperty = new SimpleStringProperty();
        player.setInputAs(stringProperty);
        player.setInputTextAs("John Maynard Keynes");
        assertEquals("John Maynard Keynes", player.getInputText());
    }

    @Test
    void testIncrementScore1() {
    }

    @Test
    void testDecrementHealth1() {
    }

    @Test
    void testGetScoreProperty1() {
    }

    @Test
    void testGetHealthProperty1() {
    }

    @Test
    void testGetGameOverProperty1() {
    }

    @Test
    void testIsSamePlayer() {
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
