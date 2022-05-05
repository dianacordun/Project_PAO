package services;

import domain.Author;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthorIO implements GenericIO {
    private static final AuthorIO INSTANCE = new AuthorIO();
    private List<Author> authors=new ArrayList<Author>();

    private AuthorIO() {}

    public static AuthorIO getInstance() {
        return INSTANCE;
    }

    public void setAuthorStorage(List<Author> authors) {
        this.authors = authors;
    }

    public List<Author> getAuthors() {
        return this.authors;
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
            System.out.println("There were no authors saved!");
        }

        return data;
    }

    @Override
    public void readCSV() {
        var data = AuthorIO.getData("src/main/java/files/author.csv");
        for(var fields : data){
            var all = new Author(
                    fields[0], // first name
                    fields[1] // last name
            );
            this.authors.add(all);
        }
    }

    @Override
    public void writeCSV() {
        try{
            var writer = new FileWriter("src/main/java/files/author.csv");
            for(var author : this.authors){
                writer.write(author.toCSV());
                writer.write("\n");
            }
            writer.close();
        }catch (IOException e){
            System.out.println(e);
        }
    }
}
