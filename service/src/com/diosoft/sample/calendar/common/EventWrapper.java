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
    private UUID uuid;
    private String title;
    private String name;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<Person> attenders;

    public EventWrapper(Event event) {
        uuid = event.getUuid();
        title = event.getTitle();
        name = event.getName();
        description = event.getDescription();
        startTime = event.getStartTime();
        endTime = event.getEndTime();
        attenders = event.getAttenders();

    }
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @XmlElement
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
        this.startTime = startTime;
    }

    @XmlElement
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @XmlElement
    public void setAttenders(List<Person> attenders) {
        this.attenders = attenders;
    }

    public UUID getUuid() {
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public List<Person> getAttenders() {
        return attenders;
    }



}
