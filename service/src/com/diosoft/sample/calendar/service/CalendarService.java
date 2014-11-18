package com.diosoft.sample.calendar.service;

import com.diosoft.sample.calendar.common.Event;
import com.diosoft.sample.calendar.common.Person;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface CalendarService extends Remote {

    void createEvent(String title, String name, String description, LocalDateTime startDate, LocalDateTime endDate, List<Person> attenders) throws RemoteException;

    void addEvent(Event event) throws RemoteException, JAXBException, FileNotFoundException;

    Event removeEvent(UUID uuid) throws Exception;

    Event addAttender(UUID uuid, Person... newPersons) throws RemoteException;

    Event getEvent(UUID uuid) throws RemoteException;

    List<Event> getEventsInTimeRange(LocalDateTime startTime, LocalDateTime endTime) throws RemoteException;

    List<LocalDateTime> getSuitableTimeForPerson(LocalDateTime start, LocalDateTime end, Person person) throws RemoteException;

    List<LocalDateTime> getSuitableTimeForListOfPersons(LocalDateTime start, LocalDateTime end, List<Person> listOfPerson) throws RemoteException;

    List<Person> getAttenders(UUID uuid) throws RemoteException;

    List<Event> searchEventByDateTime(LocalDateTime date1) throws RemoteException;

    List<Event> searchEventByDateTime(LocalDateTime date1, LocalDateTime date2) throws RemoteException;

    List<Event> isAttenderAvailable(Person person, LocalDateTime dateTime) throws RemoteException;

    List<Event> isAvailableToday(Person person, LocalTime time) throws RemoteException;
}
