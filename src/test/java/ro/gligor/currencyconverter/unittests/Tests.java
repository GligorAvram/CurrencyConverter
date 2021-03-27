package ro.gligor.currencyconverter.unittests;

import de.saxsys.javafx.test.JfxRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import ro.gligor.currencyconverter.CurrencyConverter;


import java.net.UnknownHostException;

import static org.junit.Assert.assertEquals;

@RunWith(JfxRunner.class)
public class Tests {
        CurrencyConverter currencyConverter = new CurrencyConverter();

    @Test
    public void nonNumberInput() {
        currencyConverter.convert( "This is not a number", "RON", "EUR");
        assertEquals("Please check the amount to convert", currencyConverter.getConvertedAmountLabel());
    }

    @Test(expected = NullPointerException.class)
    public void inputIsNull() {
        currencyConverter.convert( null, "RON", "EUR");
        assertEquals("Please check the amount to convert", currencyConverter.getConvertedAmountLabel());
    }

    @Test
    public void inputIsZero() {
        currencyConverter.convert( "0", "RON", "EUR");
        assertEquals("Please check the amount to convert", currencyConverter.getConvertedAmountLabel());
    }
    @Test
    public void inputIsBlank() {
        currencyConverter.convert( "", "GBP", "EUR");
        assertEquals("Please check the amount to convert", currencyConverter.getConvertedAmountLabel());
    }

    @Test
    public void wrongCurrency(){
        currencyConverter.convert( "30", "WRONG VALUE", "EUR");
        assertEquals("Something happened. Contact support.", currencyConverter.getConvertedAmountLabel());
    }

    //check while internet is down
    @Test
    public void noInternet() {
        currencyConverter.convert( "30", "GBP", "EUR");
        assertEquals("Make sure your internet connection is working or try again later", currencyConverter.getConvertedAmountLabel());
    }
}
