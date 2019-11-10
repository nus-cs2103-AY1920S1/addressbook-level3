package seedu.pluswork.model.settings;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ThemeTest {

    @Test
    void getExtensionUrl_checkStoredData_success() {
        assertEquals(Theme.LIGHT.getExtensionUrl(), ThemeTest.class.getClassLoader()
                .getResource("view/ExtensionsLight.css").toExternalForm());
        assertEquals(Theme.DARK.getExtensionUrl(), ThemeTest.class.getClassLoader()
                .getResource("view/Extensions.css").toExternalForm());
    }

    @Test
    void getThemeUrl() {
        assertEquals(Theme.LIGHT.getThemeUrl(), ThemeTest.class.getClassLoader()
                .getResource("view/LightTheme.css").toExternalForm());
        assertEquals(Theme.DARK.getThemeUrl(), ThemeTest.class.getClassLoader()
                .getResource("view/DarkTheme.css").toExternalForm());
    }
}
