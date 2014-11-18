package com.diosoft.sample.calendar.service;

import com.diosoft.sample.calendar.common.Event;
import com.diosoft.sample.calendar.common.EventWrapper;
import com.diosoft.sample.calendar.common.Person;
import com.diosoft.sample.calendar.datastore.CalendarDataStore;
import com.diosoft.sample.calendar.parser.jaxbimpl.JAXBParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
    public void addEvent(Event event) throws RemoteException, JAXBException, FileNotFoundException {
        dataStore.publish(event);
       // JAXBParser.marshallEvent(new EventWrapper(event),new File(event.getUuid()+".xml"));
        logger.info("Added event on service side " + event.getName());
    }

    @Override
    public Event removeEvent(UUID uuid) throws Exception {
       // JAXBParser.removeEvent(new EventWrapper(new Event.Builder().setId(uuid).build()));
        return dataStore.remove(uuid);
    }

    @Override
    public Event getEvent(UUID uuid) {
//        try {
//            JAXBParser.unmarshallEvent(new File(uuid.toString() + ".xml"));
//        } catch (JAXBException | FileNotFoundException | ExecutionException |InterruptedException e) {
//            e.printStackTrace();
//        }
        return dataStore.getEvent(uuid);
    }

    @Override
    public List<Person> getAttenders(UUID uuid) throws RemoteException {
        return dataStore.getMapEvents().get(uuid).getAttenders();
    }

    @Override
    public List<Event> getEventsInTimeRange(LocalDateTime startTime, LocalDateTime endTime) throws RemoteException {
        List<Event> listOfEvents = new ArrayList<Event>(dataStore.getMapEvents().values());
        List<Event> removeList = new ArrayList<>();
        for (Event event : listOfEvents) {
            if ((event.getStartTime().compareTo(startTime) < 0) && (event.getEndTime().compareTo(startTime) < 0)) {
                removeList.add(event);
                continue;
            }
            if ((event.getStartTime().compareTo(endTime) > 0) && (event.getEndTime().compareTo(endTime) > 0)) {
                removeList.add(event);
            }
        }
        listOfEvents.removeAll(removeList);
        return listOfEvents;
    }

    //suitable time of event for Person, search starts from "start"
    @Override
    public List<LocalDateTime> getSuitableTimeForPerson(LocalDateTime start, LocalDateTime end, Person person) throws RemoteException {
        LocalDateTime st = start;
        LocalDateTime en = end;
        while (true) {
            List<Event> list = this.getEventsInTimeRange(st, en);
            if (list.isEmpty()) return Arrays.asList(st, en);
            for (Event event : list) {
                System.out.println(event.getAttenders().contains(person));
                if (event.getAttenders().contains(person)) {
                    st = st.plusMinutes(INTERVAL);
                    en = en.plusMinutes(INTERVAL);
                    break;
                } else {
                    return Arrays.asList(st, en);
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
                    start = start.plusMinutes(INTERVAL);
                    end = end.plusMinutes(INTERVAL);
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

    @Override
    public List<Event> searchEventByDateTime(LocalDateTime date1) {
        List<Event> foundEvents = new ArrayList();
        Map<UUID, Event> allEvents = dataStore.getMapEvents();
        logger.info("Keys " + allEvents.keySet());
        if (allEvents != null || allEvents.size() != 0) {
            foundEvents.addAll(allEvents.keySet().stream().filter(uuid -> (date1.compareTo(allEvents.get(uuid).getStartTime()) >= 0) && date1.compareTo(allEvents.get(uuid).getEndTime()) < 0).map(this::getEvent).collect(Collectors.toList()));
            return foundEvents;
        }
        throw new NoSuchElementException("Unfortunately cannot retrieve events list to search");
    }

    @Override
    public List<Event> searchEventByDateTime(LocalDateTime date1, LocalDateTime date2) {
        List<Event> foundEvents = new ArrayList();
        Map<UUID, Event> allEvents = dataStore.getMapEvents();
        if (allEvents != null || allEvents.size() != 0) {
            foundEvents.addAll(allEvents.keySet().stream().filter(uuid -> (date1.compareTo(allEvents.get(uuid).getStartTime()) >= 0) && date2.compareTo(allEvents.get(uuid).getEndTime()) <= 0).map(this::getEvent).collect(Collectors.toList()));
            return foundEvents;
        }
        throw new NoSuchElementException("Unfortunately cannot retrieve events list to search");
    }

    @Override
    public List<Event> isAttenderAvailable(Person person, LocalDateTime dateTime) throws RemoteException {
        if(dateTime != null && person != null) {
            List<Event> foundEvents = new ArrayList<>();
            Map<UUID, Event> allEvents = dataStore.getMapEvents();
            foundEvents.addAll(allEvents.keySet().stream().filter(uuid -> (allEvents.get(uuid).getStartTime().compareTo(dateTime) <= 0) && (allEvents.get(uuid).getEndTime().compareTo(dateTime) >= 0)).map(allEvents::get).collect(Collectors.toList()));
            return foundEvents;
        }
        return new ArrayList<Event>(){};
    }

    @Override
    public List<Event> isAvailableToday(Person person, LocalTime time) throws RemoteException {
        if(time != null && person != null) {
            List<Event> foundEvents = new ArrayList<>();
            Map<UUID, Event> allEvents = dataStore.getMapEvents();
            foundEvents.addAll(allEvents.keySet().stream().filter(uuid -> (allEvents.get(uuid).getStartTime().toLocalDate().isEqual(LocalDate.now()) && allEvents.get(uuid).getStartTime().toLocalTime().compareTo(time) <= 0) && (allEvents.get(uuid).getEndTime().toLocalTime().compareTo(time) >= 0)).map(allEvents::get).collect(Collectors.toList()));
            return foundEvents;
        }
        return new ArrayList<Event>(){};
    }

}
