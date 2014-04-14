package de.dertak.opennms.outagecalendar;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.SocketException;
import java.util.List;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;
import org.opennms.netmgt.model.OnmsOutage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutagesToIcal {

    private static Logger logger = LoggerFactory.getLogger(OutagesToIcal.class);

    public static void outagesToIcal(List<OnmsOutage> outages) {
        Calendar calendar = new Calendar();

        calendar.getProperties().add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);

        UidGenerator uidGenerator = null;

        try {
            uidGenerator = new UidGenerator("FooBar");
        } catch (SocketException ex) {
            logger.error("UidGenerator had a problem ", ex);
        }

        VEvent vEvent;
        for (OnmsOutage myOutage : outages) {
            vEvent = new VEvent(new DateTime(myOutage.getIfLostService()),
                    new DateTime(myOutage.getIfRegainedService()),
                    "Outage - " + myOutage.getServiceLostEvent().getEventHost() + " - "
                    + myOutage.getMonitoredService().getServiceName());

            vEvent.getProperties().add(uidGenerator.generateUid());
            try {
                vEvent.getProperties().add(new Description("Node " + myOutage.getServiceLostEvent().getEventHost()
                        + " lost at interface " + myOutage.getServiceLostEvent().getIpAddr()
                        + " the service " + myOutage.getMonitoredService().getServiceName()
                        + " with the message " + myOutage.getServiceLostEvent().getEventUei() + " " + myOutage.getServiceLostEvent().getEventLogMsg()
                        + " it regaind after " + ((myOutage.getIfRegainedService().getTime() - myOutage.getIfLostService().getTime()) / 1000) + " seconds"
                        + " with the message " + myOutage.getServiceRegainedEvent().getEventUei() + " " + myOutage.getServiceRegainedEvent().getEventLogMsg()));
            } catch (Exception ex) {
                logger.error("Problem during creation of event description", ex);
            }
            calendar.getComponents().add(vEvent);
        }

        try {
            calendar.validate();
        } catch (ValidationException ex) {
            logger.error("Invalid Calendar", ex);
        }
        
        FileOutputStream fout;

        try {
            fout = new FileOutputStream("/tmp/mycalendar.ics");
            CalendarOutputter outputter = new CalendarOutputter();
            outputter.output(calendar, fout);
        } catch (FileNotFoundException ex) {
            logger.error("file not found problem during calender output", ex);
        } catch (Exception ex) {
            logger.error("problem during calender output", ex);
        }
        
        logger.info("Created /tmp/mycalendar.ics file");
    }
}
