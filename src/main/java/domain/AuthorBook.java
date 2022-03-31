package domain;

public class AuthorBook {
    private final int authorId;
    private final String bookIsbn;

    /** constructor */
    public AuthorBook(int authorId, String bookIsbn) {
        this.authorId = authorId;
        this.bookIsbn = bookIsbn;
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }
}
