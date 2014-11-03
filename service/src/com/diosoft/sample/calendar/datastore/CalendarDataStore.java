package com.diosoft.sample.calendar.datastore;

import com.diosoft.sample.calendar.common.Event;
import com.diosoft.sample.calendar.common.Person;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CalendarDataStore {

    void publish(Event event);

    Event remove(UUID eventUUID);

    Event getEvent(UUID eventUUID);

    Map<UUID, Event> getMapEvents();

    List<Person> getAttenders(UUID uuid);
}
