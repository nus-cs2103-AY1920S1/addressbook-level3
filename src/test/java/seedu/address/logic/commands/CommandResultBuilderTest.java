package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.display.locationdata.ClosestCommonLocationData;
import seedu.address.model.display.timeslots.PersonTimeslot;
import seedu.address.model.person.schedule.Venue;
import seedu.address.ui.util.ColorGenerator;

class CommandResultBuilderTest {

    private CommandResultBuilder commandResultBuilder;
    private String feedback = "feedback";

    @BeforeEach
    void init() {
        commandResultBuilder = new CommandResultBuilder(feedback);
    }

    @Test
    void setShowHelp() {
        commandResultBuilder.setShowHelp();
        assertTrue(commandResultBuilder.build().isShowHelp());
    }

    @Test
    void setExit() {
        commandResultBuilder.setExit();
        assertTrue(commandResultBuilder.build().isExit());
    }

    @Test
    void setScroll() {
        commandResultBuilder.setScroll();
        assertTrue(commandResultBuilder.build().isScroll());
    }

    @Test
    void setHome() {
        commandResultBuilder.setHome();
        assertTrue(commandResultBuilder.build().isHome());
    }

    @Test
    void setToggleNextWeek() {
        commandResultBuilder.setToggleNextWeek();
        assertTrue(commandResultBuilder.build().isToggleNextWeek());
    }

    @Test
    void setSwitchTabs() {
        commandResultBuilder.setSwitchTabs();
        assertTrue(commandResultBuilder.build().isSwitchTabs());
    }

    @Test
    void setExport() {
        commandResultBuilder.setExport();
        assertTrue(commandResultBuilder.build().isExport());
    }

    @Test
    void setSelect() {
        commandResultBuilder.setSelect();
        assertTrue(commandResultBuilder.build().isSelect());
    }

    @Test
    void setPopUp() {
        commandResultBuilder.setPopUp();
        assertTrue(commandResultBuilder.build().isPopUp());
    }

    @Test
    void setFilter() {
        commandResultBuilder.setFilter();
        assertTrue(commandResultBuilder.build().isFilter());
    }

    @Test
    void setLocationData() throws CommandException {
        commandResultBuilder.setLocationData(new ClosestCommonLocationData());
        assertNotNull(commandResultBuilder.build().getLocationData());
    }

    @Test
    void setPersonTimeslotData() {
        PersonTimeslot personTimeslot = new PersonTimeslot(
                "eventname",
                LocalDate.of(2019, 11, 11),
                LocalTime.of(10, 30),
                LocalTime.of(11, 30),
                new Venue("venue"),
                ColorGenerator.generateColor(0),
                false,
                new ClosestCommonLocationData()
        );

        commandResultBuilder.setPersonTimeslotData(personTimeslot);
        assertNotNull(commandResultBuilder.build().getPersonTimeslotData());
    }

    @Test
    void build() {
        assertEquals(new CommandResult(feedback), commandResultBuilder.build());
    }
}
