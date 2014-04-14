package de.dertak.opennms.outagecalendar;

import de.dertak.opennms.outagecalendar.helper.RestHelper;
import java.util.List;
import org.opennms.netmgt.model.OnmsOutage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Starter {

    private static Logger logger = LoggerFactory.getLogger(Starter.class);

    public static void main(String[] args) {
        logger.info("OpenNMS Outages to ICal sample application");
        
        String baseUrl = "http://demo.opennms.com/opennms/";
//        String baseUrl = "http://opennms.opennms-edu.net/opennms/";
        
        String username = "demo";
        String password = "demo";

        logger.info("Getting Outages from " + baseUrl);
        List<OnmsOutage> outages = RestOutageProvider.getOutages(RestHelper.createApacheHttpClient(username, password), baseUrl, "?limit=100");
        logger.info("Received '{}' Outages", outages.size());
        logger.info("Creating calendar file for Outages");
        OutagesToIcal.outagesToIcal(outages);
//        Set<OnmsIpInterface> myIpInterfaces = (RestNodeProvider.getIpInterfacesByNode(RestHelper.createApacheHttpClient(username, password), baseUrl, 3, ""));
//        logger.info("myIpInterfaces '{}'", myIpInterfaces);
//        List<OnmsNode> allNodes = RestNodeProvider.getNodes(RestHelper.createApacheHttpClient(username, password), baseUrl, "?limit=0");
//        for (OnmsNode node : allNodes) {
//            logger.info("NodeId: '{}'", node.getId());
//            OnmsIpInterfaceList ipInterfaces = (RestNodeProvider.getIpInterfacesByNode(RestHelper.createApacheHttpClient(username, password), baseUrl, node.getId(), ""));
//            logger.info("\t\t\tNodeLable: '{}' Interfaces: {}", node.getLabel(), ipInterfaces.getTotalCount());
//            for (OnmsIpInterface ipInterface : ipInterfaces.getInterfaces()) {
//                logger.info("\t\t\t\tInterface '{}'", ipInterface.getIpAddress().toString());
//            }
//        }

//        Map<OnmsNode, List<OnmsOutage>> nodesToOutages = RestOutageProvider.getOutagesForNodes(RestHelper.createApacheHttpClient(username, password), baseUrl, nodes, "?limit=100");
//        for (OnmsNode node : nodesToOutages.keySet()) {
//            if(nodesToOutages.get(node).size() > 0) {
//                logger.warn("Node: {} {}\t has {} Outages", node.getId(), node.getLabel(), nodesToOutages.get(node).size());
//            }
//        }

        logger.info("Thanks for computing with OpenNMS!");
    }
}
