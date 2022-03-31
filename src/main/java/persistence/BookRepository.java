package persistence;

import domain.Book;

import java.util.Arrays;

public class BookRepository implements GenericRepository<Book>{
    private Book[] bookArray = new Book[5];
    
    @Override
    public void add(Book entity) {
        for (int i = 0; i < bookArray.length; i++){
            if (bookArray[i] == null) {
                bookArray[i] = entity;
                return;
            }
        }
        //if there's no more room, we extend the capacity
        Book[] newBooks = Arrays.<Book,Book>copyOf(bookArray, 2*bookArray.length, Book[].class);
        newBooks[bookArray.length] = entity;
        bookArray = newBooks;
    }

    @Override
    public Book get(int i) {
        return bookArray[i];
    }

    @Override
    public void delete(Book entity) {
        if (bookArray == null){
            return;
        }

        Book[] newBooks = new Book[bookArray.length -1 ];
        int j = 0;
        for (int i = 0; i < bookArray.length; i++) {
            if (!bookArray[i].equals(entity)){
                newBooks[j] = bookArray[i];
                j++;
            }
        }
        return;
    }

    @Override
    public int getSize() {
        return bookArray.length;
    }

}
