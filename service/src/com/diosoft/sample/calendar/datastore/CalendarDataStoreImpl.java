package com.diosoft.sample.calendar.datastore;

import com.diosoft.sample.calendar.common.Event;
import com.diosoft.sample.calendar.common.Person;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CalendarDataStoreImpl implements CalendarDataStore {

    private final HashMap<UUID, Event> store = new HashMap<>();

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
    public HashMap<UUID, Event> getMapEvents() {
        return store;
    }


    //TODO: Andriy Paliy
    private Map<String, List<UUID>> getMapUniqueTitle(HashMap<UUID, Event> eventsMap) {
        HashSet<String> listUniqueTitle = new HashSet<>();
        Map<String, List<UUID>> mapUniqueTitle = new HashMap<>();

        if ((eventsMap.size() == 0) || (eventsMap == null)) {
            System.err.println("Events didn't find in the Calendar!");
            return null;
        }

        for (Map.Entry<UUID, Event> entry: eventsMap.entrySet()) {
           listUniqueTitle.add(entry.getValue().getTitle().toLowerCase());
        }

        for (String uniqueTitle: listUniqueTitle) {
            ArrayList<UUID> listUUID = null;
            for (Map.Entry<UUID, Event> entry: eventsMap.entrySet()) {
                if (uniqueTitle.equals(entry.getValue().getTitle().toLowerCase())) {
                    listUUID.add(entry.getKey());
                }
            }
            mapUniqueTitle.put(uniqueTitle, listUUID);
        }
        return mapUniqueTitle;
    }

    //TODO: Andriy Paliy
    public List<Event> searchByTitle(String title) {

        HashMap<UUID, Event> allEventsMap = getMapEvents();
        Map<String , List<UUID>> resultMap = getMapUniqueTitle(allEventsMap);
        List<Event> eventList = null;

        if ((resultMap.containsKey(title))) {
            List<UUID> listUUID = resultMap.get(title.toLowerCase());
            for (UUID uuidTemp: listUUID) {
                eventList.add(allEventsMap.get(uuidTemp));
            }
            return eventList;
        } else {
            System.err.println("Events with title: " + title + " didn't find!");
            return null;
        }
    }

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
