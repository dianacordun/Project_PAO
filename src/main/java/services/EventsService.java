package services;

import domain.*;
import exceptions.InvalidDataException;
import exceptions.NoDataFoundException;
import persistence.EventRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class EventsService {
    /**
     * Registers a new event.
     * Checks if the name, the location, the date and the organizer are valid.
     * Two events are not allowed to have the same name.
     */
    private EventRepository eventRepository = new EventRepository();

    public void addNewEvent(String location, String organizer, String name, String date) throws InvalidDataException, NoDataFoundException {
        if (location == null || location.trim().isEmpty()) {
            throw new InvalidDataException("Invalid location");
        }
        if (organizer == null || organizer.trim().isEmpty()) {
            throw new InvalidDataException("Invalid organizer");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidDataException("Invalid name");
        }
        String dateRegex = "^(0[1-9]|1[0-2])\\/(0[1-9]|1\\d|2\\d|3[01])\\/(19|20)\\d{2}$";
        if (date == null || !Pattern.compile(dateRegex).matcher(date).matches()) {
            throw new InvalidDataException("Invalid date");
        }

        for(int i = 0; i < eventRepository.getSize(); i++){
            if (eventRepository.get(i) == null){
                throw new NoDataFoundException("No events");
            }
            if(eventRepository.get(i).getName().equals(name)){
                throw new InvalidDataException("Event name is already taken");
            }
        }

        Event event = new Event(location,organizer, name,date);
        eventRepository.add(event);
    }
    /**
     * Register a new MeetTheAuthor event.
     * Two events are not allowed to have the same name.
     */
    public void addNewMTA(String location, String organizer, String name, String date, Author author) throws InvalidDataException, NoDataFoundException {
        if (location == null || location.trim().isEmpty()) {
            throw new InvalidDataException("Invalid location");
        }
        if (organizer == null || organizer.trim().isEmpty()) {
            throw new InvalidDataException("Invalid organizer");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidDataException("Invalid name");
        }
        String dateRegex = "^(0[1-9]|1[0-2])\\/(0[1-9]|1\\d|2\\d|3[01])\\/(19|20)\\d{2}$";
        if (date == null || !Pattern.compile(dateRegex).matcher(date).matches()) {
            throw new InvalidDataException("Invalid date");
        }
        for(int i = 0; i < eventRepository.getSize(); i++){
            if (eventRepository.get(i) == null){
                throw new NoDataFoundException("No events");
            }

            if(eventRepository.get(i).getName().equals(name)){
                throw new InvalidDataException("Event name is already taken");
            }
        }

        MeetTheAuthor event = new MeetTheAuthor(location, organizer, name, date, author);
        eventRepository.add(event);
    }

    /**
     * Register a new BookLaunch event.
     * Two events are not allowed to have the same name.
     */
    public void addNewBookLaunch(String location, String organizer, String name, String date, Book book) throws InvalidDataException, NoDataFoundException {
        if (location == null || location.trim().isEmpty()) {
            throw new InvalidDataException("Invalid location");
        }
        if (organizer == null || organizer.trim().isEmpty()) {
            throw new InvalidDataException("Invalid organizer");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidDataException("Invalid name");
        }
        String dateRegex = "^(0[1-9]|1[0-2])\\/(0[1-9]|1\\d|2\\d|3[01])\\/(19|20)\\d{2}$";
        if (date == null || !Pattern.compile(dateRegex).matcher(date).matches()) {
            throw new InvalidDataException("Invalid date");
        }
        for(int i = 0; i < eventRepository.getSize(); i++){
            if (eventRepository.get(i) == null){
                throw new NoDataFoundException("No events");
            }

            if(eventRepository.get(i).getName().equals(name)){
                throw new InvalidDataException("Event name is already taken");
            }
        }

        BookLaunch event = new BookLaunch(location, organizer, name, date, book);
        eventRepository.add(event);
    }

    /**
     * Delete an event, given its id.
     */
    public void deleteEvent(int id) throws NoDataFoundException {
        for(int i = 0; i < eventRepository.getSize(); i++){
            if (eventRepository.get(i) == null){
                throw new NoDataFoundException("No events");
            }
            if (eventRepository.get(i).getId() == id){
                eventRepository.delete(eventRepository.get(i));
            }
        }
        throw new NoDataFoundException("There is no event with given id");
    }

    /**
     * Get all events.
     */
    public Event[] getAllEvents() throws NoDataFoundException {
        List<Event> result = new ArrayList<>();

        for(int i = 0; i < eventRepository.getSize(); i++){
            if (eventRepository.get(i) == null){
                throw new NoDataFoundException("No events");
            }
            result.add(eventRepository.get(i));
        }

        return result.toArray(new Event[0]);
    }

    /**
     * Get all Meet The Author events.
     */
    public MeetTheAuthor[] getAllMTA() throws NoDataFoundException {
        List<MeetTheAuthor> result = new ArrayList<>();

        for(int i = 0; i < eventRepository.getSize(); i++){
            if (eventRepository.get(i) == null){
                throw new NoDataFoundException("No events");
            }
            if(eventRepository.get(i) instanceof MeetTheAuthor){
                result.add((MeetTheAuthor) eventRepository.get(i));
            }
        }
        if(result.isEmpty()){
            throw new NoDataFoundException("No Meet The Author events");
        }

        return result.toArray(new MeetTheAuthor[0]);
    }

    /**
     * Get all book launches.
     */

    public BookLaunch[] getAllBookLaunches() throws NoDataFoundException {
        List<BookLaunch> result = new ArrayList<>();

        for(int i = 0; i < eventRepository.getSize(); i++){
            if (eventRepository.get(i) == null){
                throw new NoDataFoundException("No events");
            }
            if(eventRepository.get(i) instanceof BookLaunch){
                result.add((BookLaunch) eventRepository.get(i));
            }
        }

        if(result.isEmpty()){
            throw new NoDataFoundException("No book launches");
        }

        return result.toArray(new BookLaunch[0]);
    }

    /**
     * Returns an event given its name.
     */
    public Event getEventByName(String name) throws NoDataFoundException{
        for (int i = 0; i < eventRepository.getSize(); i++) {
            if (eventRepository.get(i) == null){
                throw new NoDataFoundException("No events");
            }

            if (eventRepository.get(i).getName().equals(name)) {
                return eventRepository.get(i);
            }
        }
        throw new NoDataFoundException("There is no event with given name");
    }


}
