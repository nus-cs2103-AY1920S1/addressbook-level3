package thrift.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class CurrencyUtilTest {

    private final double conversionAmount = 100;

    @Test
    public void setCurrencyMap_nullInput_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> CurrencyUtil.setCurrencyMap(null));
    }

    @Test
    public void setCurrencyMap_validMappingInput_success() {
        Map<String, Double> testCurrencyMapping = Map.of("TestCurrency", 123.45);
        CurrencyUtil.setCurrencyMap(testCurrencyMapping);
        assertEquals(testCurrencyMapping, CurrencyUtil.getCurrencyMap());
    }

    @Test
    public void convertFromDefaultCurrency_nonExistentMapping_returnDefaultCurrency() {
        // No "ABC" currency in default currency mappings
        Map<String, Double> currencyMap = CurrencyUtil.getCurrencyMap();
        double conversionResult = CurrencyUtil.convertFromDefaultCurrency(currencyMap, conversionAmount, "ABC");
        assertEquals(conversionAmount, conversionResult);
    }

    @Test
    public void convertToDefaultCurrency_nonExistentMapping_returnDefaultCurrency() {
        // No "ABC" currency in default currency mappings
        Map<String, Double> currencyMap = CurrencyUtil.getCurrencyMap();
        double conversionResult = CurrencyUtil.convertToDefaultCurrency(currencyMap, conversionAmount, "ABC");
        assertEquals(conversionAmount, conversionResult);
    }
}
