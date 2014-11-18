package com.diosoft.sample.calendar.parser.jaxbimpl;

import com.diosoft.sample.calendar.common.Event;
import com.diosoft.sample.calendar.common.EventWrapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by home on 11/18/2014.
 */
public class RemoveTask implements Runnable {
    private final EventWrapper event;

    public RemoveTask(EventWrapper event){
        this.event = event;
    }

    @Override
    public void run() {
        if(this.event != null && new File("store",this.event.getUuid()+".xml").exists()) {
            System.out.println("Path to file is: "+ Paths.get("store\\" + this.event.getUuid() + ".xml"));
            try {
                Files.delete(Paths.get("store\\" + this.event.getUuid() + ".xml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("File "+ this.event.getUuid() + ".xml" + " is deleted!");
        } else {
            try {
                throw new Exception("Event is null, please specify the correct event");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
