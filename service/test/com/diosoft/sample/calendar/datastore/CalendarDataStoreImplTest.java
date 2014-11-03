package com.diosoft.sample.calendar.datastore;

import com.diosoft.sample.calendar.common.Event;
import com.diosoft.sample.calendar.common.Person;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.mock;

public class CalendarDataStoreImplTest {

    @Test
    public void testAddEvent() throws Exception {
        // initialize variable inputs
        LocalDateTime inputStartDate = LocalDateTime.of(2008, Calendar.APRIL, Calendar.TUESDAY, 10, 12);
        LocalDateTime inputEndDate = LocalDateTime.of(2008, Calendar.APRIL, Calendar.TUESDAY, 10, 12);
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

        // initialize class to test
        CalendarDataStore dataStore =new CalendarDataStoreImpl();

        // invoke method on class to test
        dataStore.publish(expectedEvent);

        // assert return value
        Event returnedValue = dataStore.getEvent(expectedEvent.getUuid());

        assertEquals(expectedEvent, returnedValue);

        // verify mock expectations
    }

    @Test
    public void testRemove() throws Exception {
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

        // initialize class to test
        CalendarDataStore dataStore =new CalendarDataStoreImpl();
        dataStore.publish(expectedEvent);

        // invoke method on class to test
        Event returnedValue = dataStore.remove(expectedEvent.getUuid());

        // assert return value
        Event returnedEmptyValue = dataStore.remove(expectedEvent.getUuid());

        assertEquals(expectedEvent, returnedValue);
        assertNull(returnedEmptyValue);

        // verify mock expectations
    }
}
