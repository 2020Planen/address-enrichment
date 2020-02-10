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
        Scanner scanner = new Scanner(getUrl.openStream());
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
//        String address = msg.getMetaData().getAddress();
//
//        StringBuilder queryParams = new StringBuilder("?");
//
//        appendQueryParam("q", address, queryParams);
//
//        appendQueryParam("struktur", "flad", queryParams);
//        String addressData = getHttpData((url + queryParams).replace(" ", "%20"));
//
//        System.out.println(addressData);
//
//        JsonObject jo = new JsonParser().parse(addressData).getAsJsonArray().get(0).getAsJsonObject();
//        AddressData ad = gson.fromJson(jo, AddressData.class);
//
//        System.out.println(ad.toString());  
//        
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
