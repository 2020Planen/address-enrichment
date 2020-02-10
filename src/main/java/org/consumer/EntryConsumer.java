package org.consumer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.acme.jsonObjectMapper.Message;

import org.acme.restclient.AddressData;
import org.acme.restclient.AddressService;
import org.apache.camel.ProducerTemplate;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 * @author Mathias
 */
@ApplicationScoped
public class EntryConsumer {

    String url = "https://dawa.aws.dk/adresser";

    public String getHttpData(String url) throws MalformedURLException, IOException {
        URL getUrl = new URL(url);
        Scanner scanner = new Scanner(getUrl.openStream(), "UTF-8");
        String response = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return response;
    }

    @Inject
    ProducerTemplate camelProducer;

    @Incoming("address-enrichment")
    //@Outgoing("routing")
    public void enrich(String content) throws IOException, InterruptedException, ExecutionException, TimeoutException {
        Gson gson = new Gson();
        Message msg = gson.fromJson(content, Message.class);
        msg.startLog("address-enrichment");
        String address = msg.getMetaData().getAddress();
        int zip = msg.getMetaData().getZip();
        StringBuilder queryParams = new StringBuilder("?");


        appendQueryParam("q", address + ", " +zip, queryParams);
        appendQueryParam("struktur", "flad", queryParams);
        
        
        
        //Replace whitespaces with "%20" to make the fetch call URL format
        String urlFormatted = (url + queryParams).replace(" ", "%20");
        System.out.println("\n\nFetching from url: " +urlFormatted +" ........."); 
        String addressData = getHttpData(urlFormatted);

        JsonArray ja = new JsonParser().parse(addressData).getAsJsonArray();
        if (ja.size() > 0) {
            JsonObject data = ja.get(0).getAsJsonObject();
            msg.getData().put("addressData", data);
        } else {
            msg.getData().put("addressData", "No data found with input address="+address +", zip="+zip);
        }

        msg.endLog();
        try {
            msg.sendToKafkaQue();
        } catch (Exception ex) {
            Logger.getLogger(EntryConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void appendQueryParam(String key, String value, StringBuilder sb) {
        if (sb.length() != 1) {
            sb.append("&");
        }
        sb.append(key + "=" + value);

    }

}
