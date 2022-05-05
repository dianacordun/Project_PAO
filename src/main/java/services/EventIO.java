package services;

import domain.Customer;
import domain.Event;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventIO implements GenericIO{
    private static final EventIO INSTANCE = new EventIO();
    private List<Event> events=new ArrayList<Event>();

    private EventIO() {}

    public static EventIO getInstance() {
        return INSTANCE;
    }

    public void setEventStorage(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return this.events;
    }

    private static List<String[]> getData(String file){
        List<String[]> data = new ArrayList<>();

        try(var in = new BufferedReader(new FileReader(file))) {

            String line;

            while((line = in.readLine()) != null ) {
                String[] fields = line.split(",");
                data.add(fields);
            }
        } catch (IOException e) {
            System.out.println("There were no events saved!");
        }

        return data;
    }

    @Override
    public void readCSV() {
        var data = EventIO.getData("src/main/java/files/event.csv");
        for(var fields : data){
            var all = new Event(
                    fields[0], // location
                    fields[1], // organizer
                    fields[2], // name
                    fields[3] // date
            );
            this.events.add(all);
        }
    }

    @Override
    public void writeCSV() {
        try{
            var writer = new FileWriter("src/main/java/files/event.csv");
            for(var event : this.events){
                writer.write(event.toCSV());
                writer.write("\n");
            }
            writer.close();
        }catch (IOException e){
            System.out.println(e);
        }
    }
}
