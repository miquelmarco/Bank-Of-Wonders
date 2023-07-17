package com.mindhub.homebanking;
import com.mindhub.homebanking.utils.Utilities;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.text.IsEmptyString.emptyOrNullString;
@SpringBootTest
public class UtilitiesTests {
    @Test
    public void checkCardNumberGenerator() {
        String cardNumber = Utilities.cardNumberGenerator();
        assertThat(cardNumber, is(not(emptyOrNullString())));
    }
//    @Test
//    public void checkCardNumberDuplicates() {
//        String cardNumber = Utilities.cardNumberGenerator();
//        assertThat();
//    }
    @Test
    public void checkCvvGenerator() {
        short cvv = Utilities.cvvGenerator();
        assertThat(String.valueOf(cvv).length(), is(3));
    }
    @Test
    public void checkCvvLength() {
        short cvv = Utilities.cvvGenerator();
        assertThat(cvv, is(not(equals(3))));
    }
}