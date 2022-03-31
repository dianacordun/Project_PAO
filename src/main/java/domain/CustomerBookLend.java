package domain;

public class CustomerBookLend {
    private final int customerId;
    private final String bookIsbn;
    private String lendDate;
    private String returnDate;
    private Boolean returned;

    /** constructor */
    public CustomerBookLend(int customerId, String bookIsbn, String lendDate, String returnDate, Boolean returned) {
        this.customerId = customerId;
        this.bookIsbn = bookIsbn;
        this.lendDate = lendDate;
        this.returnDate = returnDate;
        this.returned = returned;
    }

    /**  getters and setters */
    public int getCustomerId() {
        return customerId;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public String getLendDate() {
        return lendDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public Boolean getReturned() {
        return returned;
    }

    public void setReturned(Boolean returned) {
        this.returned = returned;
    }
}
