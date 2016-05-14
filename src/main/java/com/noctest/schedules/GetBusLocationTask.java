package com.noctest.schedules;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.transit.realtime.GtfsRealtime;
import com.google.transit.realtime.GtfsRealtime.FeedMessage;
import com.noctest.auth.TokenRepoIF;
import com.noctest.websockets.WSPayload;
import com.noctest.websockets.WSSender;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 5/05/2016.
 */

@Component
public class GetBusLocationTask {
    private static final Logger log = LoggerFactory.getLogger(GetBusLocationTask.class);

    @Autowired
    private TokenRepoIF tokenRepo;

    @Autowired
    private WSSender WSClient;

    public void work() {

        //Can't do much without a token
        if (!tokenRepo.getToken().isPresent()) return;
        log.info("Got token getting data");

        String bearerToken = tokenRepo.getToken().get();

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("https://api.transport.nsw.gov.au/v1/gtfs/vehiclepos/ferries");
        httpget.addHeader("Authorization", "Bearer " + bearerToken);


        try {
            log.debug("Starting retrieval of data");
            CloseableHttpResponse httpresponse = httpclient.execute(httpget);
            HttpEntity entity = httpresponse.getEntity();
            log.debug("Finishing retrieval of data" + httpresponse.toString());
            if (entity != null) {
                Map<String,String> entitymap = new HashMap<String,String>();
                InputStream instream = entity.getContent();
                try {
                    FeedMessage feed = FeedMessage.parseFrom(instream);
                    log.info("Number of entities found=" + feed.getEntityCount());

                    for (GtfsRealtime.FeedEntity gfsentity : feed.getEntityList()) {
                        WSPayload entityPL = new WSPayload(gfsentity.getVehicle().getVehicle().getLabel(),
                                                        gfsentity.getVehicle().getPosition().getLatitude(),
                                                        gfsentity.getVehicle().getPosition().getLongitude(),
                                                        gfsentity.getVehicle().getPosition().getBearing(),
                                                        gfsentity.getVehicle().getPosition().getSpeed());


                        ObjectMapper mapper = new ObjectMapper();
                        String jsonPL = mapper.writeValueAsString(entityPL);
                        entitymap.put(entityPL.getVehiclelName(),jsonPL);

                    }
                } finally {
                    instream.close();
                }
                log.info(" Unique entities "+entitymap.keySet().size());
                for (String key : entitymap.keySet()){
                    log.info(entitymap.get(key));
                    WSClient.deliver(entitymap.get(key));
                }
            } else {
                log.debug("No data returned");
            }


        } catch (Exception ex) {
            log.error("Something went wrong " + ex.getMessage());
        }
    }
}

