package domain;

public class BookLaunch extends Event{
    private Book book;

    /** constructor */
    public BookLaunch(String location, String organizer, String name, String date, Book book) {
        this.location = location;
        this.organizer = organizer;
        this.name = name;
        this.date = date;
        this.book = book;
    }
    /** getter */
    public Book getBook() {
        return book;
    }
}
