package com.diosoft.sample.calendar.parser.jaxbimpl;

import com.diosoft.sample.calendar.common.EventWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by home on 11/16/2014.
 */
public class WriteTask implements Runnable {
    private final File file;
    private final EventWrapper event;

    public WriteTask(EventWrapper event,File file){
        this.file= file;
        this.event = event;

    }

    @Override
    public void run() {
        if(this.file != null && this.event != null) {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(com.diosoft.sample.calendar.common.EventWrapper.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                if (Files.notExists(Paths.get("store\\")))
                    new File("store\\").mkdir();
                jaxbMarshaller.marshal(this.event, new File("store", this.file.toString()));
            } catch(JAXBException e) {
                e.printStackTrace();
            }
        }else{
            try {
                throw new FileNotFoundException("The File or Event is null otherwise the write operation is prohibited");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
