package domain;

import java.util.Objects;

public class Book implements Comparable<Book> {
    private final String ISBN;
    private Double price;
    private String title;
    private String publisher;
    private int numberOfCopies;
    private final Section section;


    /** constructors */
    public Book(String ISBN, Double price, String title, String publisher,int numberOfCopies ,Section section) {
        this.ISBN = ISBN;
        this.price = price;
        this.title = title;
        this.publisher = publisher;
        this.numberOfCopies = numberOfCopies;
        this.section = section;
    }

    /**  getters and setters */
    public String getISBN() {
        return ISBN;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    public String getSection() {
        return section.getSectionName();
    }

    /**
     * Custom method
     */
    @Override
    public int compareTo(Book o) {
        if(o != null){
           if(Objects.equals(o.getPrice(), this.price)){
               return 1;
           }
           else
               if (o.getPrice() > this.price) return -1;
               else return 1;
        }
        return 0;
    }

    public String toCSV() {
        return ISBN+","+price+","+title+","+publisher+","+numberOfCopies+","+section.toCSV();
    }


}
