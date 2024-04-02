package org.talos.telbotbank.service;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.talos.telbotbank.model.CurrencyModel;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

@Component
public class CurrencyService {
    public static String getCurrencyRate(String message, CurrencyModel model) throws IOException, ParseException {
        URL url = new URL("https://api.nbrb.by/exrates/rates/" + message + "?parammode=2");
        Scanner scanner = new Scanner((InputStream) url.getContent());

        StringBuilder result = new StringBuilder();
        while (scanner.hasNext()) result.append(scanner.nextLine());
        JSONObject object = new JSONObject(result.toString());

        model.setCur_ID(object.getInt("Cur_ID"));
        model.setDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(object.getString("Date")));
        model.setCur_Abbr(object.getString("Cur_Abbreviation"));
        model.setCur_Scale(object.getInt("Cur_Scale"));
        model.setCur_Name(object.getString("Cur_Name"));
        model.setCur_OfficialRate(object.getDouble("Cur_OfficialRate"));

        return "Official rate of BYN to " + model.getCur_Abbr() + "\n" +
                "on the date: " + getFormatDate(model) + "\n" +
                "is: " + model.getCur_OfficialRate() +
                " BYN per " + model.getCur_Scale() +
                " " + model.getCur_Abbr();
    }

    private static String getFormatDate(CurrencyModel model) {
        return new SimpleDateFormat("dd MMM yyyy").format(model.getDate());
    }
}
