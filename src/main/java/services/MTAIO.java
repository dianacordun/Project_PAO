package services;

import domain.Author;
import domain.Event;
import domain.MeetTheAuthor;
import domain.Section;
import exceptions.NoDataFoundException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MTAIO implements GenericIO{
    private static final MTAIO INSTANCE = new MTAIO();
    private List<MeetTheAuthor> events=new ArrayList<MeetTheAuthor>();

    private MTAIO() {}

    public static MTAIO getInstance() {
        return INSTANCE;
    }

    public void setEventStorage(List<MeetTheAuthor> events) {
        this.events = events;
    }

    public List<MeetTheAuthor> getEvents() {
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
            System.out.println("There were no meet the author events saved!");
        }

        return data;
    }

    @Override
    public void readCSV() {
        var data = MTAIO.getData("files/mta.csv");
        AuthorsService authorsService = new AuthorsService();

        for(var fields : data){
            try{
                Author author = authorsService.getAuthorById(Integer.parseInt(fields[4])); //author

                var all = new MeetTheAuthor(
                        fields[0], // location
                        fields[1], // organizer
                        fields[2], // name
                        fields[3], // date
                        author
                );
                this.events.add(all);
            } catch (NoDataFoundException e){
            System.out.println(e);
        }
        }
    }

    @Override
    public void writeCSV() {
        try{
            var writer = new FileWriter("files/mta.csv");
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
