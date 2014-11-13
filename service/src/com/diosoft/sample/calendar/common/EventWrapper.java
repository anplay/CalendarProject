package com.diosoft.sample.calendar.common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Created by dev-anplay on 11.11.2014.
 */
@XmlRootElement
public class EventWrapper {
    private static final long serialVersionUID = 1L;
    private String uuid;
    private String title;
    private String name;
    private String description;
    private String startTime;
    private String endTime;
    private String attenders = null;

    public EventWrapper(Event event) {
        uuid = event.getUuid().toString();
        title = event.getTitle();
        name = event.getName();
        description = event.getDescription();
        startTime = event.getStartTime().toString();
        endTime = event.getEndTime().toString();
        for (Person person: event.getAttenders()) {
            if (attenders == null) {
                attenders = person.getEmail();
            } else {
                attenders = attenders + ", " + person.getEmail();
            }
        }
    }
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @XmlElement
    public void setUuid(UUID uuid) {
        this.uuid = uuid.toString();
    }

    @XmlElement
    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime.toString();
    }

    @XmlElement
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime.toString();
    }

    @XmlElement
    public void setAttenders(List<Person> attendersList) {
        for (Person person: attendersList) {
            if (attenders == null) {
                this.attenders = person.getEmail();
            } else {
                this.attenders = this.attenders + ", " + person.getEmail();
            }
        }
    }

    public String getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getAttenders() {
        return attenders;
    }



}
