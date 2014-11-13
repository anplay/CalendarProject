package com.diosoft.sample.calendar.parser.jaxbimpl;

import com.diosoft.sample.calendar.common.Event;
import com.diosoft.sample.calendar.parser.Parser;
import com.sun.jmx.remote.internal.Unmarshal;
import org.w3c.dom.events.EventException;

import javax.xml.bind.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.FormatFlagsConversionMismatchException;

/**
 * Created by home on 11/12/2014.
 */
public class JAXBParser implements Parser {
    @Override
    public void marshallEvent(File file, Event event) throws DataBindingException, JAXBException, FileNotFoundException, SecurityException {
        if(file != null && file.canWrite() && event != null) {
            JAXBContext jaxbContext = JAXBContext.newInstance(com.diosoft.sample.calendar.common.Event.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(event, file);
            jaxbMarshaller.marshal(event, System.out);
        }else{
            throw new FileNotFoundException("The File or Event is null otherwise the write operation is prohibited");
        }
    }

    @Override
    public Event unmarshallEvent(File file) throws JAXBException, FileNotFoundException {
        if(file.exists() && file.canRead()) {
            JAXBContext jaxbContext = JAXBContext.newInstance(com.diosoft.sample.calendar.common.Event.class);
            Unmarshaller jaxbUnmarshall = jaxbContext.createUnmarshaller();
            return (Event) jaxbUnmarshall.unmarshal(file);
        } else{
            throw new FileNotFoundException("The File or Event is null otherwise the read operation is prohibited");
        }
    }

    @Override
    public void removeEvent(Event event) throws Exception {
        if(event != null){
            int counter = 0;
            File[] listOfFiles = new File("store\\").listFiles();
            if(listOfFiles != null) {
                for (File f : listOfFiles) {
                    Event eventObj = unmarshallEvent(f);
                    if (eventObj.equals(event))
                        if(f.delete())
                            counter++;
                }
                System.out.println(counter+ " files have been removed");
            }
        } else {
            throw new Exception("Event is null, please specify the correct event"); //xml file corresponding to this Event can't be found.
        }

    }

}
