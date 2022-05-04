package services;

import domain.Book;
import domain.Section;
import exceptions.NoDataFoundException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookIO implements GenericIO{
    private static final BookIO INSTANCE = new BookIO();
    private List<Book> books=new ArrayList<Book>();

    private BookIO() {}

    public static BookIO getInstance() {
        return INSTANCE;
    }

    public void setBookStorage(List<Book> books) {
        this.books = books;
    }

    public List<Book> getBooks() {
        return this.books;
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
            System.out.println("There were no books saved!");
        }

        return data;
    }

    @Override
    public void readCSV() throws IOException {
        try{
            BooksService booksService = new BooksService();
            var data = BookIO.getData("files/book.csv");
            for(var fields : data){
                Section section = booksService.getSectionByName(fields[5]); //section name
                var all = new Book(
                        fields[0], //ISBN
                        Double.parseDouble(fields[1]), //price
                        fields[2], //title
                        fields[3], //publisher
                        Integer.parseInt(fields[4]), //number of copies
                        section
                );
                this.books.add(all);
            }
        }catch (NoDataFoundException e){
            System.out.println(e);
        }
    }

    @Override
    public void writeCSV() throws IOException {
        try{
            var writer = new FileWriter("files/book.csv");
            for(var book : this.books){
                writer.write(book.toCSV());
                writer.write("\n");
            }
            writer.close();
        }catch (IOException e){
            System.out.println(e);
        }
    }
}
