package guitests.guihandles;

import java.net.URL;

import guitests.GuiRobot;
import javafx.stage.Stage;

/**
 * A handle to the {@code MrtWindow} of the application.
 */
public class MrtWindowHandle extends StageHandle {

    public static final String MRT_WINDOW_TITLE = "MRT Map";

    private static final String MRT_WINDOW_BROWSER_ID = "#browser";

    public MrtWindowHandle(Stage mrtWindowStage) {
        super(mrtWindowStage);
    }

    /**
     * Returns true if a mrt window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(MRT_WINDOW_TITLE);
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(MRT_WINDOW_BROWSER_ID));
    }
}
