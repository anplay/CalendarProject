package com.diosoft.sample.calendar.service;

import com.diosoft.sample.calendar.common.Event;
import com.diosoft.sample.calendar.common.Person;
import com.diosoft.sample.calendar.datastore.CalendarDataStore;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static java.util.Arrays.asList;

public class CalendarServiceImpl implements CalendarService {

    public static final Logger logger = Logger.getAnonymousLogger();
    public static final int INTERVAL = 15;
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
        return dataStore.getMapEvents().get(uuid).getAttenders();
    }

    @Override
    public List<Event> getEventsInTimeRange(LocalDateTime startTime, LocalDateTime endTime) throws RemoteException {
        List<Event> listOfEvents = new ArrayList<Event>(dataStore.getMapEvents().values());

        for (Event event : listOfEvents) {
            if ((event.getStartTime().compareTo(startTime) < 0) && (event.getEndTime().compareTo(startTime) < 0)) {
                listOfEvents.remove(event);
                continue;
            }
            if ((event.getStartTime().compareTo(endTime) > 0) && (event.getEndTime().compareTo(endTime) > 0)) {
                listOfEvents.remove(event);
            }
        }
        return listOfEvents;
    }

    //suitable time of event for Person, search starts from "start"
    @Override
    public List<LocalDateTime> getSuitableTimeForPerson(LocalDateTime start, LocalDateTime end, Person person) throws RemoteException {
        while (true) {
            List<Event> list = this.getEventsInTimeRange(start, end);
            List<Event> listOfPerson = new ArrayList<>();
            for (Event event : list) {
                if (event.getAttenders().contains(person)) {
                    start.plusMinutes(INTERVAL);
                    end.plusMinutes(INTERVAL);
                } else {
                    return Arrays.asList(start, end);
                }
            }
        }
    }

    //Searches time suitable for all persons from searchList, search starts from "start"
    @Override
    public List<LocalDateTime> getSuitableTimeForListOfPersons(LocalDateTime start, LocalDateTime end, List<Person> searchList) throws RemoteException {
        boolean rezult = false;
        while (!rezult) {
            List<LocalDateTime> listTime = new ArrayList<>();
            for (Person p : searchList) {
                listTime.add(getSuitableTimeForPerson(start, end, p).get(0));
            }
            LocalDateTime comparisonTime = listTime.get(0);
            for (LocalDateTime time : listTime) {
                if (time.compareTo(comparisonTime) != 0) {
                    start.plusMinutes(INTERVAL);
                    end.plusMinutes(INTERVAL);
                    rezult = false;
                    break;
                } else {
                    rezult = true;
                }
            }

        }
        return Arrays.asList(start, end);
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
