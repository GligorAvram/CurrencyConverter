package ro.gligor.currencyconverter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

public class CurrencyConverter extends Application {

    private Button convertButton;
    private ChoiceBox<String> fromCurrency, toCurrency;
    private TextField amountToConvert;
    private Label convertedAmountLabel;
    private List<String> currencyList;


    public static void main(String[] args) {
        launch(args);
    }

    public CurrencyConverter() {
        convertButton = new Button("Convert");
        fromCurrency = new ChoiceBox<>();
        toCurrency = new ChoiceBox<>();
        amountToConvert = new TextField();
        convertedAmountLabel = new Label();

        //list of currencies
        currencyList = new ArrayList<>();
        currencyList.add("RON");
        currencyList.add("EUR");
        currencyList.add("USD");
        currencyList.add("GBP");
        currencyList.add("CHF");

        fromCurrency.getItems().addAll(currencyList);
        fromCurrency.setValue(currencyList.get(0));
        toCurrency.getItems().addAll(currencyList);
        toCurrency.setValue(currencyList.get(1));
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Currency converter");

        convertButton.setOnAction(e -> convert(amountToConvert.getText(), fromCurrency.getValue(), toCurrency.getValue()));

        VBox layout = new VBox();
//        layout.setPadding(new Insets(100,150, 100, 150));
        layout.getChildren().addAll(amountToConvert, fromCurrency, toCurrency, convertButton, convertedAmountLabel);

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

        public void convert(String amountToConvert, String fromCurrency, String toCurrency) {
            boolean validAmount = false;
            double amount = 0;
            try {
                amount = Double.parseDouble(amountToConvert);
                if(amount != 0) {
                    validAmount = true;
                }
            }catch (NumberFormatException e){
                e.printStackTrace();
            }

            if (validAmount) {
                double rate = 0;
                String getURL = APIUrl.getUrlForAPI(fromCurrency, toCurrency);
                try {
                    URL url = new URL(getURL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    int responseCode = httpURLConnection.getResponseCode();

                    if(responseCode == HttpURLConnection.HTTP_OK){
                        BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();
                        while((inputLine = br.readLine()) != null){
                            response.append(inputLine);
                        }
                        br.close();

                        System.out.println(response.toString().length());


                        String[] splitResponse = response.toString().split(":");
                        if (splitResponse.length != 2) {
                            //todo
                            throw new RuntimeException();
                        }
                        rate = Double.parseDouble(splitResponse[1].replace("}", ""));

                    }
                    if(responseCode != HttpURLConnection.HTTP_OK) {
                        convertedAmountLabel.setText("Something happened. Contact support.");
                        return;
                    }
                } catch (MalformedURLException e) {
                    convertedAmountLabel.setText("Make sure your internet connection is working or try again later");
                    e.printStackTrace();
                    return;
                } catch (IOException e) {
                    convertedAmountLabel.setText("Make sure your internet connection is working or try again later");
                    e.printStackTrace();
                    return;
                }

                double convertedAmount = amount * rate;

                DecimalFormat format = new DecimalFormat("#.##");
                String amountToDisplay = format.format(convertedAmount);
                convertedAmountLabel.setText(amountToDisplay);
            } else {
                convertedAmountLabel.setText("Please check the amount to convert");
            }
        }

    public void setAmountToConvert(String amountToConvert) {
        this.amountToConvert.setText(amountToConvert);
    }

    public String getConvertedAmountLabel() {
        return convertedAmountLabel.getText();
    }
}