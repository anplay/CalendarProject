package com.diosoft.sample.calendar.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Created by dev-anplay on 11.11.2014.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class EventWrapper {
    private static final long serialVersionUID = 1L;
    @XmlElement
    private String uuid;
    @XmlElement
    private String title;
    @XmlElement
    private String name;
    @XmlElement
    private String description;
    @XmlElement
    private String startTime;
    @XmlElement
    private String endTime;
    @XmlElement
    private String attenders = null;

    public EventWrapper(Event event) {
        uuid = event.getUuid().toString();
        title = event.getTitle();
        name = event.getName();
        description = event.getDescription();
        startTime = event.getStartTime().toString();
        endTime = event.getEndTime().toString();
        if(event.getAttenders() != null){
            for (Person person : event.getAttenders()) {
                if (attenders == null) {
                    attenders = new StringBuilder(person.getFirstName() + ",").append(person.getSecondName() + ",").append(person.getEmail()).toString();
                } else {
                    attenders = attenders + ", " + new StringBuilder(person.getFirstName() + ",").append(person.getSecondName() + ",").append(person.getEmail()).toString();
                }
            }
        }
    }
    private EventWrapper() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid.toString();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime.toString();
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime.toString();
    }

    public void setAttenders(List<PersonWrapper> attendersList) {
        for (PersonWrapper person: attendersList) {
            if (attenders == null) {
                this.attenders = new StringBuilder(person.getFirstName() + ",").append(person.getSecondName() + ",").append(person.getEmail()).toString();
            } else {
                this.attenders = this.attenders + ", " + new StringBuilder(person.getFirstName() + ",").append(person.getSecondName() + ",").append(person.getEmail()).toString();
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

    @Override
    public String toString() {
        return "EventWrapper{" +
                "uuid='" + uuid + '\'' +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", attenders='" + attenders + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventWrapper that = (EventWrapper) o;

        if (attenders != null ? !attenders.equals(that.attenders) : that.attenders != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (attenders != null ? attenders.hashCode() : 0);
        return result;
    }
}
