package com.diosoft.sample.calendar;

import com.diosoft.sample.calendar.common.Event;
import com.diosoft.sample.calendar.common.EventWrapper;
import com.diosoft.sample.calendar.common.Person;
import com.diosoft.sample.calendar.common.PersonWrapper;
import com.diosoft.sample.calendar.parser.jaxbimpl.JAXBParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by home on 11/16/2014.
 */
public class Startup {

    public static void main(String[] args) throws Exception {
        // Creation of PersonWrapper
        Person p1 = new Person.Builder().firstName("Vladimir").secondName("Klichko").email("klichko@gmail.com").phone("0501112223").build();
        Person p2 = new Person.Builder().firstName("Tatyana").secondName("Skorikova").email("tatyana@gmail.com").phone("0954445556").build();
        Person p3 = new Person.Builder().firstName("Roman").secondName("Tereschneko").email("romant@gmail.com").phone("0997778889").build();

        List<PersonWrapper> listOfPersons = Arrays.asList(new PersonWrapper(p1), new PersonWrapper(p2), new PersonWrapper(p3));

        // Creation of EventWrapper
        Event event = new Event.Builder().setId(UUID.randomUUID()).title("football").name("Friday football!").description("Football event description").startTime(LocalDateTime.now()).endTime(LocalDateTime.now()).build();
        EventWrapper eventWrapper = new EventWrapper(event);
        eventWrapper.setAttenders(listOfPersons);
        System.out.println("Details of the created event: " + eventWrapper.toString());

        // Create directory and file
        File file = new File(eventWrapper.getUuid()+".xml");
        System.out.println(file);

        // Marshal EventWrapper to XML
        JAXBParser.marshallEvent(eventWrapper, file);

        // Unmarshall EventWrapper from XML
        EventWrapper eventFromFile = JAXBParser.unmarshallEvent(file);
        System.out.println("Details of the unmarshalled event: "+eventFromFile.toString());

        // Remove EventWrapper from the disk
        JAXBParser.removeEvent(eventWrapper);
        System.exit(200);
    }
}
