package ro.gligor.currencyconverter;

public class APIUrl {

    //using the free version of the api which is subject to downtime

    public static String getUrlForAPI(String fromCurrency, String toCurrency){
        String key = "";
        return("https://free.currconv.com/api/v7/convert?q=" + fromCurrency + "_" + toCurrency + "&compact=ultra&apiKey=" + key);
    }
}
