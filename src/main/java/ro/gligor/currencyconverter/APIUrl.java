package ro.gligor.currencyconverter;

public class APIUrl {

    //get a key from:
    //https://free.currencyconverterapi.com/

    public static String getUrlForAPI(String fromCurrency, String toCurrency){
        String key = "";
        return("https://free.currconv.com/api/v7/convert?q=" + fromCurrency + "_" + toCurrency + "&compact=ultra&apiKey=" + key);
    }
}
