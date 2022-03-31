package domain;

import java.util.concurrent.atomic.AtomicInteger;

public class Customer {
    private static AtomicInteger nextId = new AtomicInteger();
    private final int id;
    private String firstName, lastName;
    private String phoneNumber;
    private String email;
    private Boolean membership;

    /** constructors */
    public Customer() {
        id = nextId.incrementAndGet();
    }

    public Customer(String firstName, String lastName, String phoneNumber, String email, Boolean membership) {
        this.id = nextId.incrementAndGet();
        this.firstName = firstName;
        this.lastName = lastName;
        this.membership = membership;
        this.phoneNumber = phoneNumber;
        this.email = email;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getMembership() {
        return membership;
    }

    public void setMembership(Boolean membership) {
        this.membership = membership;
    }
}
