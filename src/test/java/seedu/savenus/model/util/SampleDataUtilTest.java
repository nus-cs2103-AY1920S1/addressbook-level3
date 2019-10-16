package seedu.savenus.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.savenus.model.ReadOnlyMenu;
import seedu.savenus.model.wallet.Wallet;

public class SampleDataUtilTest {

    @Test
    public void correctSampleFoodAmount() {
        assertEquals(6,
                SampleDataUtil.getSampleFood().length);
    }

    @Test
    public void getSampleWallet_correctReturnType() {
        assertEquals(true,
                SampleDataUtil.getSampleWallet() instanceof Wallet);
    }

    @Test
    public void getSampleMenu_correctReturnType() {
        assertEquals(true,
                SampleDataUtil.getSampleMenu() instanceof ReadOnlyMenu);
    }
}
