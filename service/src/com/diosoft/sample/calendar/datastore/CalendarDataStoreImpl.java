package com.diosoft.sample.calendar.datastore;

import com.diosoft.sample.calendar.common.Event;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CalendarDataStoreImpl implements CalendarDataStore {

    private final Map<String, Event> store = new HashMap<>();

    @Override
    public void publish(Event event) {
        store.put(event.getName(), event);
        persistEvent(event);
    }

    @Override
    public Event remove(String eventName) {
        Event event = store.get(eventName);
        store.remove(eventName);
        return event;
    }

    @Override
    public Event getEvent(String name) {
        return store.get(name);
    }

    private void persistEvent(Event expectedEvent){

        JAXBContext context = null;

        EventAdapter eventAdapter = new EventAdapter(expectedEvent);
        try {
            context = JAXBContext.newInstance(EventAdapter.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(eventAdapter, new File("./"+expectedEvent.getName() +". xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
