package com.diosoft.sample.calendar.datastore;

import com.diosoft.sample.calendar.common.Event;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CalendarDataStore {

    void publish(Event event);

    Event remove(UUID eventUUID);

    Event getEvent(UUID eventUUID);

    Map<UUID, Event> getMapEvents();

    Map<String, List<UUID>> getMapUniqueTitle();
}
