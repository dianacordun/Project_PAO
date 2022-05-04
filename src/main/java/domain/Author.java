package domain;

import java.util.concurrent.atomic.AtomicInteger;

public class Author {
    private static AtomicInteger nextId = new AtomicInteger();
    private final int id;
    private String firstName, lastName;

    /** constructors */
    public Author() {
        id = nextId.incrementAndGet();
    }

    public Author(String firstName, String lastName) {
        this.id = nextId.incrementAndGet();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**  getters and setters */
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String toCSV() {
        return firstName+","+lastName;
    }
}
