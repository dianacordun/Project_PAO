package domain;

public class MeetTheAuthor extends Event{
    private Author author;

    /** constructor */
    public MeetTheAuthor(String location, String organizer, String name, String date, Author author) {
        super(location,organizer,name,date);
        this.author = author;
    }
    /** getter */
    public Author getAuthor() {
        return author;
    }

    public String toCSV() {
        return location+","+organizer+","+name+","+date+","+author.toCSV();
    }
}
