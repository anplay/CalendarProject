package com.diosoft.sample.calendar.service;

import com.diosoft.sample.calendar.common.Event;
import com.diosoft.sample.calendar.common.Person;
import com.diosoft.sample.calendar.datastore.CalendarDataStore;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class CalendarServiceImpl implements CalendarService {

    public static final Logger logger = Logger.getAnonymousLogger();
    private final CalendarDataStore dataStore;

    public CalendarServiceImpl(CalendarDataStore dataStore) {

        this.dataStore = dataStore;
    }

    @Override
    public void createEvent(String title, String name, String description, LocalDateTime startDate, LocalDateTime endDate, List<Person> attenders) throws RemoteException {

        dataStore.publish(new Event.Builder()
                        .setId(UUID.randomUUID())
                        .title(title)
                        .name(name)
                        .description(description)
                        .startTime(startDate)
                        .endTime(endDate)
                        .attenders(attenders)
                        .build()
        );

        logger.info("Published event on service side " + name);
    }

    @Override
    public void addEvent(Event event) throws RemoteException {

        dataStore.publish(event);

        logger.info("Added event on service side " + event.getName());
    }

    @Override
    public Event removeEvent(UUID uuid) {
        return dataStore.remove(uuid);

    }

    @Override
    public Event getEvent(UUID uuid) {
        return dataStore.getEvent(uuid);
    }

    @Override
    public List<Person> getAttenders(UUID uuid) {
        return dataStore.getAttenders(uuid);
        }

    @Override
    public Event addAttender(UUID uuid, Person... newPersons) {

        Event originEvent = dataStore.remove(uuid);
        if (originEvent == null) {
            return null;
        }
        List<Person> newAttenders = new ArrayList<>(originEvent.getAttenders());
        newAttenders.addAll(asList(newPersons));

        Event newEvent = new Event.Builder(originEvent)
                .attenders(newAttenders)
                .build();
        dataStore.publish(newEvent
        );
        return newEvent;
    }

    @Override
    public List<Event> searchEventByDateTime(LocalDateTime date1){
        List<Event> foundEvents = new ArrayList();
        Map<UUID, Event> allEvents = dataStore.getMapEvents();
        logger.info("Keys " + allEvents.keySet());
        if (allEvents != null || allEvents.size() !=0)
        {
            foundEvents.addAll(allEvents.keySet().stream().filter(uuid -> (date1.compareTo(allEvents.get(uuid).getStartTime()) >= 0) && date1.compareTo(allEvents.get(uuid).getEndTime()) < 0).map(this::getEvent).collect(Collectors.toList()));
            return foundEvents;
        }
        throw new NoSuchElementException("Unfortunately cannot retrieve events list to search");
    }

    @Override
    public List<Event> searchEventByDateTime(LocalDateTime date1, LocalDateTime date2) {
        List<Event> foundEvents = new ArrayList();
        Map<UUID, Event> allEvents = dataStore.getMapEvents();
        if (allEvents != null || allEvents.size() !=0)
        {
            foundEvents.addAll(allEvents.keySet().stream().filter(uuid -> (date1.compareTo(allEvents.get(uuid).getStartTime()) >= 0) && date2.compareTo(allEvents.get(uuid).getEndTime()) <= 0).map(this::getEvent).collect(Collectors.toList()));
            return foundEvents;
        }
        throw new NoSuchElementException("Unfortunately cannot retrieve events list to search");
    }
}