package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.MrtWindowHandle;
import seedu.address.logic.commands.MrtCommand;


public class MrtWindowTest extends AddressBookGuiTest {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    @Test
    public void openMrtWindow() {

        //use menu button
        getMainMenu().openMrtWindowUsingMenu();
        assertMrtWindowOpen();

        //use command box
        runCommand(MrtCommand.COMMAND_WORD);
        assertMrtWindowOpen();
    }

    /**
     * Asserts that the mrt window is open, and closes it after checking.
     */
    private void assertMrtWindowOpen() {
        assertTrue(ERROR_MESSAGE, MrtWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new MrtWindowHandle(guiRobot.getStage(MrtWindowHandle.MRT_WINDOW_TITLE)).close();
        mainWindowHandle.focus();
    }

    /**
     * Asserts that the mrt window isn't open.
     */
    private void assertMrtWindowNotOpen() {
        assertFalse(ERROR_MESSAGE, MrtWindowHandle.isWindowPresent());
    }

}
