package com.diosoft.sample.calendar.common;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;

public class Event implements Serializable {

    private final UUID uuid;
    private final String title;
    private final String name;
    private final String description;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final List<Person> attenders;


    private Event(Builder builder) {
        this.uuid = builder.uuid;
        this.title = builder.title;
        this.name = builder.name;
        this.description = builder.description;
        this.startTime = builder.startTime;
        this.attenders = builder.attenders;
        this.endTime = builder.endTime;
    }


    public UUID getUuid() { return uuid; }

    public String getTitle() { return title; }

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


    public static class Builder {
        private UUID uuid;
        private String title;
        private String name;
        private String description;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private List<Person> attenders;

        public Builder() {
        }

        public Builder(Event origin) {
            this.uuid = origin.uuid;
            this.title = origin.title;
            this.name = origin.name;
            this.description = origin.description;
            this.startTime = origin.startTime;
            this.attenders = origin.attenders;
            this.endTime = origin.endTime;
        }

        public Builder generateId(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder startTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder attenders(List<Person> attenders) {
            this.attenders = attenders;
            return this;
        }

        public Builder attenders(Person... attenders) {
            this.attenders = attenders != null ? asList(attenders) : null;
            return this;
        }

        public Builder endTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public Event build() {
            return new Event(this);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (attenders != null ? !attenders.equals(event.attenders) : event.attenders != null) return false;
        if (description != null ? !description.equals(event.description) : event.description != null) return false;
        if (endTime != null ? !endTime.equals(event.endTime) : event.endTime != null) return false;
        if (name != null ? !name.equals(event.name) : event.name != null) return false;
        if (startTime != null ? !startTime.equals(event.startTime) : event.startTime != null) return false;
        if (title != null ? !title.equals(event.title) : event.title != null) return false;
        if (uuid != null ? !uuid.equals(event.uuid) : event.uuid != null) return false;

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

    @Override
    public String toString() {
        return "Event{" +
                "uuid=" + uuid +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", attenders=" + attenders +
                '}';
    }
}
