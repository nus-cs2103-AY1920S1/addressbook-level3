package seedu.address.ui;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Handles the production of map in Customer Window
 */
public class Map {

    public static final String WEBVIEW_WRAPPER = "<html style=\"background: #424242;\">%s</html>";
    private static final String CODED = "QUl6YVN5QmtSOTlwR0EwSElTUFQxSFA2d1NLOFd2cTBPZGdCbU9J";
    private static final String MAPS_WRAPPER = "<iframe width=\"100%%\" height=\"330\" frameborder=\"0\" "
            + "style=\"border:0\" src=\"https://www.google.com/maps/embed/v1/search?q=%s&key=%s\" "
            + "allowfullscreen></iframe>";

    public static String getAddressString(String address) {
        return String.format(MAPS_WRAPPER, URLEncoder.encode(address, StandardCharsets.UTF_8),
                new String(Base64.getDecoder().decode(CODED)));
    }
}
