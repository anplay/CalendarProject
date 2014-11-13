package com.diosoft.sample.calendar.parser;

import com.diosoft.sample.calendar.common.Event;

import javax.xml.bind.DataBindingException;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by home on 11/12/2014.
 */
public interface Parser {

    public void marshallEvent(File f, Event obj) throws DataBindingException, JAXBException, FileNotFoundException, SecurityException;

    public Event unmarshallEvent(File file) throws JAXBException, FileNotFoundException, SecurityException;

    public void removeEvent(Event event) throws Exception;

}
