package com.diosoft.sample.calendar.parser.jaxbimpl;

import com.diosoft.sample.calendar.common.EventWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.Callable;

/**
 * Created by home on 11/16/2014.
 */
public class ReadTask implements Callable<EventWrapper> {
    private final File file;

    public ReadTask(File file){
        this.file = file;
    }
    @Override
    public EventWrapper call() throws Exception {
        if(file != null && new File("store",file.toString()).exists()) {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(com.diosoft.sample.calendar.common.EventWrapper.class);
                Unmarshaller jaxbUnmarshall = jaxbContext.createUnmarshaller();
                return (EventWrapper) jaxbUnmarshall.unmarshal(new File("store", file.toString()));
            } catch(JAXBException e){
                e.printStackTrace();
            }
        } else{
            try {
                throw new FileNotFoundException("The File or Event is null otherwise the read operation is prohibited");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
