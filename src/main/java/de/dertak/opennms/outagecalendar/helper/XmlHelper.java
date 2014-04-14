package de.dertak.opennms.outagecalendar.helper;

import com.sun.org.apache.xml.internal.serializer.OutputPropertiesFactory;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlHelper {

    private static Logger logger = LoggerFactory.getLogger(XmlHelper.class);

    public String formatXmlString(String xml) {
        StringWriter writer = new StringWriter();
        try {

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputPropertiesFactory.S_KEY_INDENT_AMOUNT, "1");
            transformer.transform(new StreamSource(new StringReader(xml)), new StreamResult(writer));

        } catch (Exception e) {
            logger.error("Formatting XmlString went wrong", e);
        }
        StringBuffer buffer = writer.getBuffer();
        return buffer.toString();
    }
}