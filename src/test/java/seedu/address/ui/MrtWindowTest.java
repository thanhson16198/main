package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.ui.MrtWindow.USERGUIDE_FILE_PATH;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import guitests.guihandles.MrtWindowHandle;
import javafx.stage.Stage;

public class MrtWindowTest extends GuiUnitTest {

    private MrtWindow mrtWindow;
    private MrtWindowHandle mrtWindowHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> mrtWindow = new MrtWindow());
        Stage mrtWindowStage = FxToolkit.setupStage((stage) -> stage.setScene(mrtWindow.getRoot().getScene()));
        FxToolkit.showStage();
        mrtWindowHandle = new MrtWindowHandle(mrtWindowStage);
    }

    @Test
    public void display() {
        URL expectedHelpPage = HelpWindow.class.getResource(USERGUIDE_FILE_PATH);
        assertEquals(expectedHelpPage, mrtWindowHandle.getLoadedUrl());
    }
}
