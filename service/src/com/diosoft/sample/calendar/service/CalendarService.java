package com.diosoft.sample.calendar.service;

import com.diosoft.sample.calendar.common.Event;
import com.diosoft.sample.calendar.common.Person;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface CalendarService extends Remote {

    void addEvent2(String title, String name, String description, LocalDateTime startDate, LocalDateTime endDate, List<Person> attenders) throws RemoteException;

    Event removeEvent(UUID uuid) throws RemoteException;

    Event addAttender(UUID uuid, Person... newPersons) throws RemoteException;

    Event getEvent(UUID uuid) throws RemoteException;

    List<Person> getAttenders(UUID uuid);
}
