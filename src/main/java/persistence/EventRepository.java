package persistence;

import domain.Event;

import java.util.Arrays;

public class EventRepository implements GenericRepository<Event>{
    private Event[] eventArray = new Event[5];
    
    @Override
    public void add(Event entity) {
        for (int i = 0; i < eventArray.length; i++){
            if (eventArray[i] == null) {
                eventArray[i] = entity;
                return;
            }
        }
        //if there's no more room, we extend the capacity
        Event[] newEvents = Arrays.<Event,Event>copyOf(eventArray, 2*eventArray.length, Event[].class);
        newEvents[eventArray.length] = entity;
        eventArray = newEvents;
    }

    @Override
    public Event get(int i) {
        return eventArray[i];
    }

    @Override
    public void delete(Event entity) {
        if (eventArray == null){
            return;
        }

        Event[] newEvents = new Event[eventArray.length -1 ];
        int j = 0;
        for (int i = 0; i < eventArray.length; i++) {
            if (!eventArray[i].equals(entity)){
                newEvents[j] = eventArray[i];
                j++;
            }
        }
        return;
    }

    @Override
    public int getSize() {
        return eventArray.length;
    }
}
