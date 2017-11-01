package seedu.address.model.place;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EmailTest {

    @Test
    public void isValidEmail() {
        // blank email
        assertFalse(Email.isValidEmail("")); // empty string
        assertFalse(Email.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(Email.isValidEmail("@example.com")); // missing local part
        assertFalse(Email.isValidEmail("peterjackexample.com")); // missing '@' symbol
        assertFalse(Email.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Email.isValidEmail("peterjack@-")); // invalid domain name
        assertFalse(Email.isValidEmail("peter jack@example.com")); // spaces in local part
        assertFalse(Email.isValidEmail("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(Email.isValidEmail("peterjack@@example.com")); // double '@' symbol
        assertFalse(Email.isValidEmail("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(Email.isValidEmail("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(Email.isValidEmail("a@b"));  // invalid domain
        assertFalse(Email.isValidEmail("test@localhost"));   // invalid domain
        assertFalse(Email.isValidEmail("123@145"));  // numeric local part and domain name, invalid domain
        assertFalse(Email.isValidEmail("_user_@_e_x_a_m_p_l_e_.com_"));    // underscores
        assertFalse(Email.isValidEmail("peter_jack@very_very_very_long_example.com"));   // invalid domain name
        assertFalse(Email.isValidEmail("max()min@ggg.com")); //invalid email

        // valid email
        assertTrue(Email.isValidEmail("PeterJack_1190@example.com"));
        assertTrue(Email.isValidEmail("a1@example1.com"));  // mixture of alphanumeric and dot characters
        assertTrue(Email.isValidEmail("if.you.dream.it_you.can.do.it@example.com"));    // long local part
        assertTrue(Email.isValidEmail("-"));    // user never set the email
        assertTrue(Email.isValidEmail("this-example@example-test.com")); //dashes
        assertTrue(Email.isValidEmail("sss.ppp@example.com")); //dots
        assertTrue(Email.isValidEmail("sss.ppp@example.com.sg")); // two TLD domain
        assertTrue(Email.isValidEmail("sss.ppp-qqq@example.edu.sg")); //dots and dashes
        assertTrue(Email.isValidEmail("example@1.com")); //domain name is a number with valid TLD

    }
}
