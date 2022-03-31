package domain;

public class MeetTheAuthor extends Event{
    private Author autor;

    /** constructor */
    public MeetTheAuthor(String location, String organizer, String name, String date, Author autor) {
        this.location = location;
        this.organizer = organizer;
        this.name = name;
        this.date = date;
        this.autor = autor;
    }
    /** getter */
    public Author getAutor() {
        return autor;
    }
}
