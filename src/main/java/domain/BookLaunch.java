package domain;

public class BookLaunch extends Event{
    private Book book;

    /** constructor */
    public BookLaunch(String location, String organizer, String name, String date, Book book) {
        super(location,organizer,name,date);
        this.book = book;
    }
    /** getter */
    public Book getBook() {
        return book;
    }
    public String toCSV() {
        return location+","+organizer+","+name+","+date+","+book.toCSV();
    }
}
