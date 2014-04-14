package de.dertak.opennms.outagecalendar;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.client.apache.ApacheHttpClient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.opennms.netmgt.model.OnmsNode;
import org.opennms.netmgt.model.OnmsOutage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestOutageProvider {

    private static Logger logger = LoggerFactory.getLogger(RestOutageProvider.class);

    public static List<OnmsOutage> getOutages(ApacheHttpClient httpClient, String baseUrl, String parameters) {
        WebResource webResource = httpClient.resource(baseUrl + "rest/outages" + parameters);
        List<OnmsOutage> outages = null;
        try {
            outages = webResource.header("Accept", "application/xml").get(new GenericType<List<OnmsOutage>>() {
            });
        } catch (Exception ex) {
            logger.debug("Rest-Call for Outages went wrong", ex);
        }
        return outages;
    }

    public static Map<OnmsNode, List<OnmsOutage>> getOutagesForNodes(ApacheHttpClient httpClient, String baseUrl, List<OnmsNode> nodes, String parameters) {
        Map<OnmsNode, List<OnmsOutage>> nodesToOutages = new HashMap<OnmsNode, List<OnmsOutage>>();
        for (OnmsNode node : nodes) {
            WebResource webResource = httpClient.resource(baseUrl + "rest/outages/forNode/" + node.getId() + parameters);
            List<OnmsOutage> outages = null;
            try {
                outages = webResource.header("Accept", "application/xml").get(new GenericType<List<OnmsOutage>>() {
                });
            } catch (Exception ex) {
                logger.debug("Rest-Call for Outages for Node '{}' went wrong", node, ex);
            }
            nodesToOutages.put(node, outages);
        }
        return nodesToOutages;
    }
}
