package com.diosoft.sample.calendar.service;

import com.diosoft.sample.calendar.common.Event;
import com.diosoft.sample.calendar.common.Person;
import com.diosoft.sample.calendar.datastore.CalendarDataStore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static java.util.Arrays.asList;

public class CalendarServiceImpl implements CalendarService {

    public static final Logger logger = Logger.getAnonymousLogger();
    private final CalendarDataStore dataStore;

    public CalendarServiceImpl(CalendarDataStore dataStore) {

        this.dataStore = dataStore;
    }

    @Override
    public void addEvent2(String title, String name, String description, LocalDateTime startDate, LocalDateTime endDate, List<Person> attenders) {

        dataStore.publish(new Event.Builder()
                        .generateId(UUID.randomUUID())
                        .title(title)
                        .name(name)
                        .description(description)
                        .startTime(startDate)
                        .endTime(endDate)
                        .attenders(attenders)
                        .build()
        );

        logger.info("Published even on service side " + name);
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
}
