package domain;

import java.util.concurrent.atomic.AtomicInteger;

public class Event {

    protected static AtomicInteger nextId = new AtomicInteger();
    protected final int id;
    protected String location;
    protected String organizer;
    protected String name;
    protected String date;

    /** constructors */
    public Event() {
        id = nextId.incrementAndGet();
    }

    public Event(String location, String organizer, String name, String date) {
        this.id = nextId.incrementAndGet();
        this.location = location;
        this.organizer = organizer;
        this.name = name;
        this.date = date;
    }

    /**  getters and setters */
    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrganizer(){
        return organizer;
    }

    public void setOrganizer(String organizer){
        this.organizer = organizer;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

}
