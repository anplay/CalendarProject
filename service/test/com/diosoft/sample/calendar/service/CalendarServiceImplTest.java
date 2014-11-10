package com.diosoft.sample.calendar.service;

import com.diosoft.sample.calendar.common.Event;
import com.diosoft.sample.calendar.common.Person;
import com.diosoft.sample.calendar.datastore.CalendarDataStore;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.*;

public class CalendarServiceImplTest {

    @Test
    public void testCreateEvent() throws Exception {
        // initialize variable inputs
        LocalDateTime inputStartDate = LocalDateTime.of(2008, Calendar.APRIL,Calendar.TUESDAY,10, 12);
        LocalDateTime inputEndDate = LocalDateTime.of(2008, Calendar.APRIL,Calendar.TUESDAY,10, 12);
        String inputTitle = "football";
        String inputName = "sampleEvent";
        String inputDescription = "sampleEventDescription";
        List<Person> attenders = Arrays.asList(new Person.Builder().firstName("aName").build());

        Event expectedEvent = new Event.Builder()
                .setId(UUID.randomUUID())
                .title(inputTitle)
                .name(inputName)
                .description(inputDescription)
                .startTime(inputStartDate)
                .endTime(inputEndDate)
                .attenders(attenders)
                .build();

        // initialize mocks
        CalendarDataStore dataStore = mock(CalendarDataStore.class);

        // initialize class to test
        CalendarService service = new CalendarServiceImpl(dataStore);

        // invoke method on class to test
        service.createEvent(inputTitle, inputName, inputDescription, inputStartDate, inputEndDate, attenders);

        // assert return value

        //TODO-fix Roman Tereschenko (events have different ids)
        // verify mock expectations
        verify(dataStore).publish(expectedEvent);
    }

    @Test
    public void testAddEvent() throws Exception {
        //initialize inputs
        LocalDateTime inputStartDate = LocalDateTime.of(2008, Calendar.APRIL,Calendar.TUESDAY,10, 12);
        LocalDateTime inputEndDate = LocalDateTime.of(2008, Calendar.APRIL,Calendar.TUESDAY,10, 12);
        String inputTitle = "football";
        String inputName = "sampleEvent";

        Event expectedEvent = new Event.Builder()
                .setId(UUID.randomUUID())
                .title(inputTitle)
                .name(inputName)
                .startTime(inputStartDate)
                .endTime(inputEndDate)
                .build();

        //initialize mocks
        CalendarDataStore dataStore = mock(CalendarDataStore.class);

        //initialize class to test
        CalendarService calendar = new CalendarServiceImpl(dataStore);

        // invoke method on class to test
        calendar.addEvent(expectedEvent);

        // assert return value

        // verify mock expectations
        verify(dataStore).publish(expectedEvent);

    }

    @Test
    public void testRemoveEvent() throws Exception {
        // initialize variable inputs
        String inputName = "sampleEvent";

        Event expectedEvent = new Event.Builder()
                .setId(UUID.randomUUID())
                .name(inputName)
                .build();

        // initialize mocks
        CalendarDataStore dataStore = mock(CalendarDataStore.class);
        when(dataStore.remove(expectedEvent.getUuid())).thenReturn(expectedEvent);

        // initialize class to test
        CalendarService service = new CalendarServiceImpl(dataStore);

        // invoke method on class to test
        Event returnedValue = service.removeEvent(expectedEvent.getUuid());

        // assert return value
        assertEquals(expectedEvent, returnedValue);

        // verify mock expectations
    }

    @Test
    public void testGetEvent() throws Exception {
        // initialize variable inputs
        String inputName = "sampleEvent";

        Event expectedEvent = new Event.Builder()
                .setId(UUID.randomUUID())
                .name(inputName)
                .build();

        // initialize mocks
        CalendarDataStore dataStore = mock(CalendarDataStore.class);
        when(dataStore.getEvent(expectedEvent.getUuid())).thenReturn(expectedEvent);


        // initialize class to test
        CalendarService service = new CalendarServiceImpl(dataStore);

        // invoke method on class to test
        Event returnedValue = service.getEvent(expectedEvent.getUuid());

        // assert return value
        assertEquals(expectedEvent, returnedValue);

        // verify mock expectations
    }

    @Test
    public void testAddAttenderWithNull() throws Exception {
        // initialize variable inputs
        LocalDateTime inputStartDate = LocalDateTime.of(2008, Calendar.APRIL,Calendar.TUESDAY,10, 12);
        LocalDateTime inputEndDate = LocalDateTime.of(2008, Calendar.APRIL,Calendar.TUESDAY,10, 12);
        String inputTitle = "football";
        String inputName = "sampleEvent";
        String inputDescription = "sampleEventDescription";
        Person inputPerson = new Person.Builder().firstName("aName").build();
        Person inputNewPerson = new Person.Builder().firstName("aName2").build();
        List<Person> attenders = Arrays.asList(inputPerson);
        List<Person> newAttenders = Arrays.asList(inputPerson,inputNewPerson);


        Event expectedNewEvent = new Event.Builder()
                .setId(UUID.randomUUID())
                .title(inputTitle)
                .name(inputName)
                .description(inputDescription)
                .startTime(inputStartDate)
                .endTime(inputEndDate)
                .attenders(newAttenders)
                .build();


        // initialize mocks
        CalendarDataStore dataStore = mock(CalendarDataStore.class);
        when(dataStore.remove(expectedNewEvent.getUuid())).thenReturn(null);


        // initialize class to test
        CalendarService service = new CalendarServiceImpl(dataStore);

        // invoke method on class to test
        Event returnValue = service.addAttender(expectedNewEvent.getUuid(), inputNewPerson);

        // assert return value
        assertNull(returnValue);

        // verify mock expectations
        verify(dataStore,times(0)).publish(expectedNewEvent);
    }

    @Test
    public void testAddAttender() throws Exception {
        // initialize variable inputs
        LocalDateTime inputStartDate = LocalDateTime.of(2008, Calendar.APRIL,Calendar.TUESDAY,10, 12);
        LocalDateTime inputEndDate = LocalDateTime.of(2008, Calendar.APRIL,Calendar.TUESDAY,10, 12);
        String inputTitle = "football";
        String inputName = "sampleEvent";
        String inputDescription = "sampleEventDescription";
        Person inputPerson = new Person.Builder().firstName("aName").build();
        Person inputNewPerson = new Person.Builder().firstName("aName2").build();
        List<Person> attenders = Arrays.asList(inputPerson);
        List<Person> newAttenders = Arrays.asList(inputPerson,inputNewPerson);

        Event expectedEvent = new Event.Builder()
                .setId(UUID.randomUUID())
                .title(inputTitle)
                .name(inputName)
                .description(inputDescription)
                .startTime(inputStartDate)
                .endTime(inputEndDate)
                .attenders(attenders)
                .build();
        Event expectedNewEvent = new Event.Builder()
                .setId(UUID.randomUUID())
                .title(inputTitle)
                .name(inputName)
                .description(inputDescription)
                .startTime(inputStartDate)
                .endTime(inputEndDate)
                .attenders(newAttenders)
                .build();


        // initialize mocks
        CalendarDataStore dataStore = mock(CalendarDataStore.class);
        when(dataStore.remove(expectedNewEvent.getUuid())).thenReturn(expectedEvent);

        // initialize class to test
        CalendarService service = new CalendarServiceImpl(dataStore);

        // invoke method on class to test
        Event returnValue = service.addAttender(expectedNewEvent.getUuid(), inputNewPerson);

        // assert return value
        assertNotSame(expectedNewEvent, returnValue);

        //TODO-fix Roman Tereschenko (events have different ids)
        // verify mock expectations
        verify(dataStore).publish(expectedNewEvent);
    }

    @Test
    public void testAddAttenderMulti() throws Exception {
        // initialize variable inputs
        LocalDateTime inputStartDate = LocalDateTime.of(2008, Calendar.APRIL,Calendar.TUESDAY,10, 12);
        LocalDateTime inputEndDate = LocalDateTime.of(2008, Calendar.APRIL,Calendar.TUESDAY,10, 12);
        String inputTitle = "football";
        String inputName = "sampleEvent";
        String inputDescription = "sampleEventDescription";
        Person inputPerson = new Person.Builder().firstName("aName").build();
        Person inputNewPerson = new Person.Builder().firstName("aName2").build();
        Person inputNewPerson2 = new Person.Builder().firstName("aName3").build();
        List<Person> attenders = Arrays.asList(inputPerson);
        List<Person> newAttenders = Arrays.asList(inputPerson,inputNewPerson,inputNewPerson2);

        Event expectedEvent = new Event.Builder()
                .setId(UUID.randomUUID())
                .title(inputTitle)
                .name(inputName)
                .description(inputDescription)
                .startTime(inputStartDate)
                .endTime(inputEndDate)
                .attenders(attenders)
                .build();
        Event expectedNewEvent = new Event.Builder()
                .setId(UUID.randomUUID())
                .title(inputTitle)
                .name(inputName)
                .description(inputDescription)
                .startTime(inputStartDate)
                .endTime(inputEndDate)
                .attenders(newAttenders)
                .build();


        // initialize mocks
        CalendarDataStore dataStore = mock(CalendarDataStore.class);
        when(dataStore.remove(expectedEvent.getUuid())).thenReturn(expectedEvent);

        // initialize class to test
        CalendarService service = new CalendarServiceImpl(dataStore);

        // invoke method on class to test
        Event returnValue = service.addAttender(expectedEvent.getUuid(), inputNewPerson, inputNewPerson2);

        // assert return value
        assertNotSame(expectedNewEvent, returnValue);

        //TODO-fix Roman Tereschenko (events have different ids)
        // verify mock expectations
        verify(dataStore).publish(expectedNewEvent);
    }

   @Test
   public void testGetAttenders() throws Exception {
       // initialize variable inputs
       String inputName = "Event";
       Person inputPerson = new Person.Builder().firstName("NAME").build();

       Event event = new Event.Builder()
               .setId(UUID.randomUUID())
               .name(inputName)
               .attenders(inputPerson)
               .build();

       List<Person> expectedAttenders = event.getAttenders();

       // initialize mocks
       CalendarDataStore dataStore = mock(CalendarDataStore.class);

       when(dataStore.getEventsMap().get(event.getUuid()).getAttenders()).thenReturn(expectedAttenders);

       // initialize class to test
       CalendarService service = new CalendarServiceImpl(dataStore);

       // invoke method on class to test
       List<Person> returnedAttenders = service.getAttenders(event.getUuid());

       // assert return value
       assertEquals(expectedAttenders, returnedAttenders);

       // verify mock expectations
       verify(dataStore).getEventsMap().get(event.getUuid()).getAttenders();
   }

}
