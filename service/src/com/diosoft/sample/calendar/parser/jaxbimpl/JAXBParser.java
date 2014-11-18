package com.diosoft.sample.calendar.parser.jaxbimpl;


import com.diosoft.sample.calendar.common.Event;
import com.diosoft.sample.calendar.common.EventWrapper;


import javax.xml.bind.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * Created by home on 11/12/2014.
 */
public class JAXBParser {

    static private final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void marshallEvent(EventWrapper event, File file) throws DataBindingException, JAXBException, FileNotFoundException, SecurityException {
        threadPool.submit(new WriteTask(event,file));
    }

    public static EventWrapper unmarshallEvent(File file) throws JAXBException, FileNotFoundException, ExecutionException, InterruptedException {
         do {
             if (Files.exists(Paths.get("store\\" + file.getName()))) {
                 Future<EventWrapper> result = threadPool.submit(new ReadTask(file));
                 System.out.println(result.get().toString());
                 return result.get();
             }
         }while(true);
    }

    public static void removeEvent(EventWrapper event) throws Exception {
        threadPool.submit(new RemoveTask(event));
    }

}
