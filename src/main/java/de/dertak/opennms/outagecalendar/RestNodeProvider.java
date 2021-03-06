package de.dertak.opennms.outagecalendar;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.client.apache.ApacheHttpClient;
import java.util.List;
import org.opennms.netmgt.model.OnmsIpInterfaceList;
import org.opennms.netmgt.model.OnmsNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestNodeProvider {

    private static Logger logger = LoggerFactory.getLogger(RestNodeProvider.class);

    public static List<OnmsNode> getNodes(ApacheHttpClient httpClient, String baseUrl, String parameters) {
        WebResource webResource = httpClient.resource(baseUrl + "rest/nodes" + parameters);
        List<OnmsNode> nodes = null;
        try {
            nodes = webResource.header("Accept", "application/xml").get(new GenericType<List<OnmsNode>>() {});
        } catch (Exception ex) {
            logger.debug("Rest-Call for Nodes went wrong", ex);
        }
        return nodes;
    }

//    public static Set<OnmsIpInterface> getIpInterfacesByNode(ApacheHttpClient httpClient, String baseUrl, Integer nodeId, String parameters) {
//    WebResource webResource = httpClient.resource(baseUrl + "rest/nodes/" + nodeId + "/ipinterfaces" + parameters);
//        Set<OnmsIpInterface> ipInterfaces = new HashSet<OnmsIpInterface>();
//        try {
//            ipInterfaces = webResource.header("Accept", "application/xml").get(new GenericType<Set<OnmsIpInterface>>() {});
//        } catch (Exception ex) {
//            logger.debug("Rest-Call for IpInterfaces by Nodes went wrong");
//        }
//        return ipInterfaces;
//    }

    public static OnmsIpInterfaceList getIpInterfacesByNode(ApacheHttpClient httpClient, String baseUrl, Integer nodeId, String parameters) {
    WebResource webResource = httpClient.resource(baseUrl + "rest/nodes/" + nodeId + "/ipinterfaces" + parameters);
        OnmsIpInterfaceList ipInterfaces = new OnmsIpInterfaceList();
        try {
            ipInterfaces = webResource.header("Accept", "application/xml").get(OnmsIpInterfaceList.class);
        } catch (Exception ex) {
            logger.debug("Rest-Call for IpInterfaces by Nodes went wrong", ex);
        }
        return ipInterfaces;
    }
}
