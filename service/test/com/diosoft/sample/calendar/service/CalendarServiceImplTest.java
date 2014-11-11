package com.diosoft.sample.calendar.service;

import com.diosoft.sample.calendar.common.Event;
import com.diosoft.sample.calendar.common.Person;
import com.diosoft.sample.calendar.datastore.CalendarDataStore;
import com.diosoft.sample.calendar.datastore.CalendarDataStoreImpl;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

import static junit.framework.Assert.*;
import static org.fest.assertions.api.Assertions.*;
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

       when(dataStore.getMapEvents().get(event.getUuid()).getAttenders()).thenReturn(expectedAttenders);

       // initialize class to test
       CalendarService service = new CalendarServiceImpl(dataStore);

       // invoke method on class to test
       List<Person> returnedAttenders = service.getAttenders(event.getUuid());

       // assert return value
       assertEquals(expectedAttenders, returnedAttenders);

       // verify mock expectations
       verify(dataStore).getMapEvents().get(event.getUuid()).getAttenders();
   }

    @Test
    public void testGetSuitableTimeForPerson() throws Exception {
        // initialize variable inputs
        String inputName = "Event";
        Person inputPerson1 = new Person.Builder().firstName("NAME").build();
        Person inputPerson2 = new Person.Builder().firstName("NAME2").build();
        LocalDateTime s = LocalDateTime.now();

        Event event = new Event.Builder()
                .setId(UUID.randomUUID())
                .name(inputName)
                .attenders(inputPerson1, inputPerson2)
                .startTime(s)
                .endTime(s.plusMinutes(10))
                .build();
        List<LocalDateTime> expectedResult = new ArrayList<>();
        expectedResult.add(s.plusMinutes(15));
        expectedResult.add(expectedResult.get(0).plusMinutes(10));
        Map<UUID,Event> map = new HashMap<>();
        map.put(event.getUuid(), event);

        // initialize mocks
        CalendarDataStore dataStore = mock(CalendarDataStore.class);
        when(dataStore.getMapEvents()).thenReturn(map);
        //doReturn(Arrays.asList(event)).when(dataStore.getMapEvents().values());

        // initialize class to test
        CalendarService service = new CalendarServiceImpl(dataStore);

        // invoke method on class to test
        List<LocalDateTime> actualResult = service.getSuitableTimeForPerson(s, s.plusMinutes(10), inputPerson1);

        // assert return value
       assertEquals(expectedResult, actualResult);

        // verify mock expectations
        verify(dataStore, times(2)).getMapEvents();
    }

    @Test
    public void testSearchByDate() throws Exception {
        // initialize inputs
        Person inputPerson = new Person.Builder().firstName("aName").build();
        Person inputNewPerson = new Person.Builder().firstName("aName2").build();
        List<Person> attenders = Arrays.asList(inputPerson);
        List<Person> newAttenders = Arrays.asList(inputPerson, inputNewPerson);

        Event Event1 = new Event.Builder()
                .setId(UUID.randomUUID())
                .title("Estimation meeting")
                .name("estimation")
                .description("scrum... is it OK")
                .startTime( LocalDateTime.of( 2008, Month.APRIL,  20, 10, 00))
                .endTime( LocalDateTime.of( 2008, Month.APRIL,  20, 11, 00))
                .attenders(attenders)
                .build();

        Event Event2 = new Event.Builder()
                .setId(UUID.randomUUID())
                .title("3way chat")
                .name("meeting")
                .description("Who should fix the build")
                .startTime( LocalDateTime.of( 2008, Month.APRIL,  20, 15, 00))
                .endTime( LocalDateTime.of( 2008, Month.APRIL,  20, 16, 00))
                .attenders(newAttenders)
                .build();

        Event Event3 = new Event.Builder()
                .setId(UUID.randomUUID())
                .title("3way chat11")
                .name("meeting112")
                .description("Details needed")
                .startTime( LocalDateTime.of( 2008, Month.APRIL,  20, 15, 30))
                .endTime( LocalDateTime.of( 2008, Month.APRIL,  20, 16, 00))
                .attenders(newAttenders)
                .build();

        CalendarDataStore dataStore =new CalendarDataStoreImpl();
        CalendarService service = new CalendarServiceImpl(dataStore);

        service.addEvent(Event1);
        service.addEvent(Event2);
        service.addEvent(Event3);

        List<Event> returnValue = service.searchEventByDateTime(LocalDateTime.of( 2008, Month.APRIL,  20, 15, 30));
        ArrayList expectedListOfEvent = new ArrayList();
        expectedListOfEvent.add(Event2);
        expectedListOfEvent.add(Event3);

        returnValue.removeAll(expectedListOfEvent);

        assertEquals(0, returnValue.size());
    }

    @Test
    public void testIsAttenderAvailable() throws Exception {
        // initialize variable inputs
        LocalDateTime dateTime = LocalDateTime.of(2014, Month.NOVEMBER,  12, 10, 45);
        Person person1 = new Person.Builder().firstName("Anton").secondName("Djedaev").build();
        Person person2 = new Person.Builder().firstName("Sasha").secondName("Yodavich").build();
        List<Person> attenders = Arrays.asList(person1,person2);

        Event eventCoffee = new Event.Builder()
                                .setId(UUID.randomUUID())
                                .title("Coffee break")
                                .name("Coffee break - room 237")
                                .description("New coffee, let us taste it")
                                .startTime(LocalDateTime.of(2014, Month.NOVEMBER, 12, 10, 30))
                                .endTime(LocalDateTime.of(2014, Month.NOVEMBER, 12, 11, 00))
                                .attenders(attenders)
                                .build();

        Event eventPingPong = new Event.Builder()
                                .setId(UUID.randomUUID())
                                .title("Ping Pong")
                                .name("Ping Pong - room 200")
                                .description("Ping Pong time!")
                                .startTime(LocalDateTime.of(2014, Month.NOVEMBER, 12, 10, 45))
                                .endTime(LocalDateTime.of(2014, Month.NOVEMBER, 12, 11, 00))
                                .attenders(attenders)
                                .build();

        Map<UUID, Event> store = new HashMap<>();
        store.put(eventCoffee.getUuid(), eventCoffee);
        store.put(eventPingPong.getUuid(), eventPingPong);

        // initialize mocks
        CalendarDataStore dataStore = mock(CalendarDataStore.class);
        when(dataStore.getMapEvents()).thenReturn(store);

        // initialize class to test
        CalendarService service = new CalendarServiceImpl(dataStore);

        // invoke method on class to test
        List<Event> actualResult = service.isAttenderAvailable(person1,dateTime);

        // assert return value
        assertThat(actualResult).contains(eventCoffee,eventPingPong);

        // verify mock expectations
        verify(dataStore).getMapEvents();
    }

    @Test
    public void testIsAttenderAvailableWithUnmatchedDateTime() throws Exception {
        // initialize variable inputs
        LocalDateTime dateTime = LocalDateTime.of(2014, Month.NOVEMBER,  12, 12, 00);
        Person person1 = new Person.Builder().firstName("Anton").secondName("Djedaev").build();
        Person person2 = new Person.Builder().firstName("Sasha").secondName("Yodavich").build();
        List<Person> attenders = Arrays.asList(person1,person2);

        Event eventCoffee = new Event.Builder()
                                .setId(UUID.randomUUID())
                                .title("Coffee break")
                                .name("Coffee break - room 237")
                                .description("New coffee, let us taste it")
                                .startTime(LocalDateTime.of(2014, Month.NOVEMBER, 12, 11, 45))
                                .endTime(LocalDateTime.of(2014, Month.NOVEMBER, 12, 11, 59))
                                .attenders(attenders)
                                .build();

        Event eventPingPong = new Event.Builder()
                                .setId(UUID.randomUUID())
                                .title("Ping Pong")
                                .name("Ping Pong - room 202")
                                .description("Ping Pong time!")
                                .startTime(LocalDateTime.of(2014, Month.NOVEMBER, 12, 12, 1))
                                .endTime(LocalDateTime.of(2014, Month.NOVEMBER, 12, 13, 00))
                                .attenders(attenders)
                                .build();

        Map<UUID, Event> store = new HashMap<>();
        store.put(eventCoffee.getUuid(), eventCoffee);
        store.put(eventPingPong.getUuid(), eventPingPong);

        // initialize mocks
        CalendarDataStore dataStore = mock(CalendarDataStore.class);
        when(dataStore.getMapEvents()).thenReturn(store);

        // initialize class to test
        CalendarService service = new CalendarServiceImpl(dataStore);

        // invoke method on class to test
        List<Event> actualResult = service.isAttenderAvailable(person1,dateTime);

        // assert return value
        assertThat(actualResult).doesNotContain(eventCoffee, eventPingPong);

        // verify mock expectations
        verify(dataStore).getMapEvents();
    }

    @Test
    public void testIsAttenderAvailableWithNull() throws Exception {
        // initialize variable inputs
        LocalDateTime nullDateTime = null;
        Person nullPerson = null;
        Person person2 = new Person.Builder().firstName("Sasha").secondName("Yodavich").build();
        List<Person> attenders = Arrays.asList(person2);

        Event eventCoffee = new Event.Builder()
                                .setId(UUID.randomUUID())
                                .title("Coffee break")
                                .name("Coffee break - room 237")
                                .description("New coffee, let us taste it")
                                .startTime(LocalDateTime.of(2014, Month.NOVEMBER, 12, 10, 30))
                                .endTime(LocalDateTime.of(2014, Month.NOVEMBER, 12, 11, 00))
                                .attenders(attenders)
                                .build();

        Map<UUID, Event> store = new HashMap<>();
        store.put(eventCoffee.getUuid(), eventCoffee);

        // initialize mocks
        CalendarDataStore dataStore = mock(CalendarDataStore.class);
        when(dataStore.getMapEvents()).thenReturn(store);

        // initialize class to test
        CalendarService service = new CalendarServiceImpl(dataStore);

        // invoke method on class to test
        List<Event> actualResult = service.isAttenderAvailable(nullPerson,nullDateTime);

        // assert return value
        assertThat(actualResult).isEmpty();

        // verify mock expectations
        verify(dataStore, never()).getMapEvents();
    }

    @Test
    public void testIsAvailableToday() throws Exception {
        // initialize variable inputs
        LocalTime time = LocalTime.of(7, 30);
        Person person1 = new Person.Builder().firstName("Anton").secondName("Djedaev").build();
        Person person2 = new Person.Builder().firstName("Sasha").secondName("Yodavich").build();
        List<Person> attenders = Arrays.asList(person1,person2);

        Event eventCoffee = new Event.Builder()
                                .setId(UUID.randomUUID())
                                .title("Coffee break")
                                .name("Coffee break - room 237")
                                .description("New coffee, let us taste it")
                                .startTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(7, 25)))
                                .endTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(7, 40)))
                                .attenders(attenders)
                                .build();

        Event eventPingPong = new Event.Builder()
                                .setId(UUID.randomUUID())
                                .title("Ping Pong")
                                .name("Ping Pong - room 200")
                                .description("Ping Pong time!")
                                .startTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(7, 31)))
                                .endTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0)))
                                .attenders(attenders)
                                .build();

        Map<UUID, Event> store = new HashMap<>();
        store.put(eventCoffee.getUuid(), eventCoffee);
        store.put(eventPingPong.getUuid(), eventPingPong);

        // initialize mocks
        CalendarDataStore dataStore = mock(CalendarDataStore.class);
        when(dataStore.getMapEvents()).thenReturn(store);

        // initialize class to test
        CalendarService service = new CalendarServiceImpl(dataStore);

        // invoke method on class to test
        List<Event> actualResult = service.isAvailableToday(person1,time);

        // assert return value
        assertThat(actualResult).contains(eventCoffee);

        // verify mock expectations
        verify(dataStore).getMapEvents();
    }


    @Test
    public void testIsAvailableTodayUnmatchedTime() throws Exception {
        // initialize variable inputs
        LocalTime time = LocalTime.of(0, 0);
        Person person1 = new Person.Builder().firstName("Anton").secondName("Djedaev").build();
        Person person2 = new Person.Builder().firstName("Sasha").secondName("Yodavich").build();
        List<Person> attenders = Arrays.asList(person1,person2);

        Event eventCoffee = new Event.Builder()
                .setId(UUID.randomUUID())
                .title("Coffee break")
                .name("Coffee break - room 237")
                .description("New coffee, let us taste it")
                .startTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 0)))
                .endTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 30)))
                .attenders(attenders)
                .build();

        Event eventPingPong = new Event.Builder()
                .setId(UUID.randomUUID())
                .title("Ping Pong")
                .name("Ping Pong - room 200")
                .description("Ping Pong time!")
                .startTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 31)))
                .endTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(13, 0)))
                .attenders(attenders)
                .build();

        Map<UUID, Event> store = new HashMap<>();
        store.put(eventCoffee.getUuid(), eventCoffee);
        store.put(eventPingPong.getUuid(), eventPingPong);

        // initialize mocks
        CalendarDataStore dataStore = mock(CalendarDataStore.class);
        when(dataStore.getMapEvents()).thenReturn(store);

        // initialize class to test
        CalendarService service = new CalendarServiceImpl(dataStore);

        // invoke method on class to test
        List<Event> actualResult = service.isAvailableToday(person1,time);

        // assert return value
        assertThat(actualResult).isEmpty();

        // verify mock expectations
        verify(dataStore).getMapEvents();
    }
}
