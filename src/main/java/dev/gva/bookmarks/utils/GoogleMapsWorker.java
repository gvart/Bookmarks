package dev.gva.bookmarks.utils;

import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


public class GoogleMapsWorker {

    private final String GOOGLE_MAPS_URL = "http://maps.googleapis.com/maps/api/geocode/json";
    private RestTemplate restTemplate = new RestTemplate();

    public String getAddress(float lat, float lng) throws Exception {
        String finalAddress = GOOGLE_MAPS_URL + "?latlng=" + lat + "," + lng;

        ResponseEntity<String> result = restTemplate.exchange(finalAddress,
                HttpMethod.GET, new HttpEntity(getHeader()), String.class);

        if(result.getStatusCode() != HttpStatus.OK)
            throw new Exception("Failed to retrieve address from google.Status code: " + result.getStatusCode().toString());

        JSONObject json = new JSONObject(result.getBody());

        return (String) json.getJSONArray("results").getJSONObject(0).get("formatted_address");
    }

    public HttpHeaders getHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
