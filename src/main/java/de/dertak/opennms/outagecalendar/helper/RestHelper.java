package de.dertak.opennms.outagecalendar.helper;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.client.apache.ApacheHttpClient;
import com.sun.jersey.client.apache.config.DefaultApacheHttpClientConfig;

public class RestHelper {

    public static ApacheHttpClient createApacheHttpClient(String username, String password) {
        DefaultApacheHttpClientConfig httpClientConfig = new DefaultApacheHttpClientConfig();

        httpClientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

        httpClientConfig.getProperties().put(httpClientConfig.PROPERTY_PREEMPTIVE_AUTHENTICATION, Boolean.TRUE);
        httpClientConfig.getState().setCredentials(null, null, -1, username, password);

        ApacheHttpClient httpClient = ApacheHttpClient.create(httpClientConfig);

        return httpClient;
    }
}
