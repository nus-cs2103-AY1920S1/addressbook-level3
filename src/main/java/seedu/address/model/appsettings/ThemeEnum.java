package seedu.address.model.appsettings;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ThemeEnum {
    DARK("DarkTheme.css"), LIGHT("LightTheme.css");

    private String themeUrl;

    ThemeEnum(String s) {
        themeUrl = s;
    }

    public String getThemeUrl() {
        return themeUrl;
    }
}
