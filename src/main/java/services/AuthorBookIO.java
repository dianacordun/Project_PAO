package services;

import domain.Author;
import domain.AuthorBook;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthorBookIO implements GenericIO{
    private static final AuthorBookIO INSTANCE = new AuthorBookIO();
    private List<AuthorBook> authorBooks=new ArrayList<AuthorBook>();

    private AuthorBookIO() {}

    public static AuthorBookIO getInstance() {
        return INSTANCE;
    }

    public void setAuthorBookStorage(List<AuthorBook> authorBooks) {
        this.authorBooks = authorBooks;
    }

    public List<AuthorBook> getAuthorBooks() {
        return this.authorBooks;
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
            System.out.println("There was no data saved!");
        }

        return data;
    }

    @Override
    public void readCSV() {
        var data = AuthorBookIO.getData("files/authorBook.csv");
        for(var fields : data){
            var all = new AuthorBook(
                    Integer.parseInt(fields[0]), // author id
                    fields[1] // book ISBN
            );
            this.authorBooks.add(all);
        }
    }

    @Override
    public void writeCSV() {
        try{
            var writer = new FileWriter("files/authorBook.csv");
            for(var authorBook : this.authorBooks){
                writer.write(authorBook.toCSV());
                writer.write("\n");
            }
            writer.close();
        }catch (IOException e){
            System.out.println(e);
        }
    }
}
