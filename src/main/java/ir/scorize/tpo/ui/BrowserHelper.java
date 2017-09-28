package ir.scorize.tpo.ui;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by mjafar on 8/4/17.
 */
public class BrowserHelper {
    public static void openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void openWebpage(URL url) {
        try {
            openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
