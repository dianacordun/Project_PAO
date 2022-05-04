package services;

import domain.Author;
import domain.Book;
import domain.BookLaunch;
import domain.MeetTheAuthor;
import exceptions.InvalidDataException;
import exceptions.NoDataFoundException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookLaunchIO implements GenericIO{
    private static final BookLaunchIO INSTANCE = new BookLaunchIO();
    private List<BookLaunch> events=new ArrayList<BookLaunch>();

    private BookLaunchIO() {}

    public static BookLaunchIO getInstance() {
        return INSTANCE;
    }

    public void setEventStorage(List<BookLaunch> events) {
        this.events = events;
    }

    public List<BookLaunch> getEvents() {
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
            System.out.println("There were no book launch events saved!");
        }

        return data;
    }

    @Override
    public void readCSV() {
        var data = BookLaunchIO.getData("files/booklaunch.csv");
        BooksService booksService = new BooksService();

        for(var fields : data){
            try{
                Book book = booksService.getBookByISBN(fields[4]); //book

                var all = new BookLaunch(
                        fields[0], // location
                        fields[1], // organizer
                        fields[2], // name
                        fields[3], // date
                        book
                );
                this.events.add(all);
            } catch (NoDataFoundException | InvalidDataException e){
                System.out.println(e);
            }
        }
    }

    @Override
    public void writeCSV() {
        try{
            var writer = new FileWriter("files/booklaunch.csv");
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
