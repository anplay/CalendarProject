package com.diosoft.sample.calendar.common;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by home on 11/16/2014.
 */

@XmlRootElement(name = "Person")
public class PersonWrapper {
    private static final long serialVersionUID = 2L;

    private  String firstName;
    private  String secondName;
    private  String email;
    private  String phone;

    public PersonWrapper(Person person) {
        firstName = person.getFirstName();
        secondName = person.getSecondName();
        email = person.getEmail();
        phone = person.getPhone();
    }

    @XmlElement(name = "name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @XmlElement(name = "surname")
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @XmlElement(name = "email")
    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElement(name = "mobile")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
