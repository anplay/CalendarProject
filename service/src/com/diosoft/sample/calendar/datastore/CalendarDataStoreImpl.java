package com.diosoft.sample.calendar.datastore;

import com.diosoft.sample.calendar.common.Event;
import com.diosoft.sample.calendar.common.Person;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CalendarDataStoreImpl implements CalendarDataStore {

    private final Map<UUID, Event> store = new HashMap<>();

    @Override
    public void publish(Event event) {
        store.put(event.getUuid(), event);
        persistEvent(event);
    }

    @Override
    public Event remove(UUID eventUUID) {
        Event event = store.get(eventUUID);
        store.remove(eventUUID);
        return event;
    }

    @Override
    public Event getEvent(UUID eventUUID) {
        return store.get(eventUUID);
    }

    @Override
    public Map<UUID, Event> getMapEvents() {
        return store;
    }

    //TODO: Andriy Paliy
    @Override
    public Map<String, List<UUID>> getMapUniqueTitle() {
        return null;
    }

    //TODO: Andriy Paliy
    public Event searchByTitle() {
        return null;
    }

    @Override
    public List<Person> getAttenders(UUID eventUUID) {
        return store.get(eventUUID).getAttenders();
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
