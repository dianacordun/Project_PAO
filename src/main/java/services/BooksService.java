package services;

import domain.Book;
import domain.Event;
import domain.Section;
import exceptions.InvalidDataException;
import exceptions.NoDataFoundException;
import persistence.BookRepository;
import persistence.SectionRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BooksService {
    private SectionRepository sectionRepository = new SectionRepository();
    private BookRepository bookRepository = new BookRepository();


    public void addStorage(List<Section> sections, List<Book> books){
        for (int i=0;i<sections.size();i++){
            this.sectionRepository.add(sections.get(i));
        }
        for (int i=0;i<books.size();i++){
            this.bookRepository.add(books.get(i));
        }
    }

    /**
     * Registers a new section.
     * Checks if the name and the room are valid.
     * There are only 30 rooms in the library.
     */
    public void addNewSection(String sectionName, int room) throws InvalidDataException {
        if (sectionName == null || sectionName.trim().isEmpty()) {
            throw new InvalidDataException("Invalid name");
        }
        if (room <= 0 || room >30) {
            throw new InvalidDataException("Invalid section room number");
        }
        Section section = new Section(sectionName, room);
        sectionRepository.add(section);
    }

    /**
     * Returns the room a section is located in based on its name.
     * Checks if the section name is valid.
     */
    public Section findSectionByName(String sectionName) throws InvalidDataException, NoDataFoundException {
        if (sectionName == null || sectionName.trim().isEmpty()) {
            throw new InvalidDataException("Invalid name");
        }
        for (int i = 0; i < sectionRepository.getSize(); i++){
            if (sectionRepository.get(i) == null){
                throw new NoDataFoundException("No sections");
            }
            if (sectionRepository.get(i).getSectionName().equals(sectionName)){
                return sectionRepository.get(i);
            }
        }
        throw new InvalidDataException("There is no section with given name");

    }

    /**
     * Adds a new book.
     * Checks if the ISBN, title, price and publisher are valid.
     * The ISBN code should consist of 13 digits and start with 973 (registration group for Romania).
     */
    public void addNewBook(String ISBN, Double price, String title, String publisher, int numberOfCopies,  Section section) throws InvalidDataException{
        if (ISBN.length() != 13 || !ISBN.startsWith("973")) {
            throw new InvalidDataException("Invalid ISBN code");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidDataException("Invalid book title");
        }
        if (publisher == null || publisher.trim().isEmpty()) {
            throw new InvalidDataException("Invalid publisher name");
        }
        if (price >= 200.0 || price.isNaN() || price <= 10.0) {
            throw new InvalidDataException("Invalid price");
        }
        Book book = new Book(ISBN, price, title, publisher,numberOfCopies, section);
        bookRepository.add(book);
    }

    /**
     * Deletes a book, given its ISBN code.
     */

    public void deleteBook(String ISBN) throws NoDataFoundException{
        for(int i = 0; i < bookRepository.getSize(); i++){
            if (bookRepository.get(i) == null){
                throw new NoDataFoundException("No books");
            }
            if (bookRepository.get(i).getISBN().equals(ISBN)){
                bookRepository.delete(bookRepository.get(i));
            }
        }
        throw new NoDataFoundException("There is no book with given ISBN code");
    }

    /**
     * Changes a book's price, given its ISBN code.
     * The new price should be between 10 and 200 lei.
     */

    public void changeBookPrice(String ISBN, Double newPrice) throws NoDataFoundException, InvalidDataException {
        if (newPrice >= 200.0 || newPrice.isNaN() || newPrice <= 10.0) {
            throw new InvalidDataException("Invalid price");
        }
        for(int i = 0; i < bookRepository.getSize(); i++){
            if (bookRepository.get(i) == null){
                throw new NoDataFoundException("No books");
            }
            if (bookRepository.get(i).getISBN().equals(ISBN)){
                bookRepository.get(i).setPrice(newPrice);
            }
        }
        throw new NoDataFoundException("There is no book with given ISBN code");
    }

    /**
     * Returns a list of all the books with given price smaller than or equal to given price.
     */
    public Book[] filterBooksByPrice(Double price) throws InvalidDataException, NoDataFoundException {
        if (price.isNaN() || price <= 10.0) {
            throw new InvalidDataException("Invalid price");
        }

        List<Book> result = new ArrayList<>();

        for(int i = 0; i < bookRepository.getSize(); i++){
            if (bookRepository.get(i) == null){
                throw new NoDataFoundException("No books");
            }
            if (bookRepository.get(i).getPrice() >= price){
                result.add(bookRepository.get(i));
            }
        }

        if(result.isEmpty()){
            throw new NoDataFoundException("There are no books that meet the given criteria");
        }

        return result.toArray(new Book[0]);
    }

    /**
     * Orders all the books in ascending order based on their price.
     */
    public Book[] orderByPriceAscending() throws NoDataFoundException {
        List<Book> result = new ArrayList<>();
        for(int i = 0; i < bookRepository.getSize(); i++){
            if (bookRepository.get(i) == null){
                throw new NoDataFoundException("No books");
            }
            result.add(bookRepository.get(i));

        }

        Collections.sort(result);

        return result.toArray(new Book[0]);
    }

    /**
     * Filters books from given section considering given price.
     * A section is identified by its name.
     * Checks if the given section name and price are valid.
     */

    public Book[] filterBooksFromSectionByPrice(Section section, Double price) throws InvalidDataException, NoDataFoundException {

        if (price.isNaN() || price <= 10.0) {
            throw new InvalidDataException("Invalid price");
        }

        List<Book> result = new ArrayList<>();

        for(int i = 0; i < bookRepository.getSize(); i++){
            if (bookRepository.get(i) == null){
                throw new NoDataFoundException("No books");
            }
            if (bookRepository.get(i).getSection().equals(section.getSectionName()) && bookRepository.get(i).getPrice() >= price){
                result.add(bookRepository.get(i));
            }
        }

        if(result.isEmpty()){
            throw new NoDataFoundException("There are no books that meet the given criteria in this section");
        }

        return result.toArray(new Book[0]);
    }

    /**
     * Gets all the books from a section
     */
    public Book[] getAllBooksFromSection(Section section) throws NoDataFoundException {
        List<Book> result = new ArrayList<>();



        for(int i = 0; i < bookRepository.getSize(); i++){
            if (bookRepository.get(i) == null){
                throw new NoDataFoundException("No books");
            }

            if(bookRepository.get(i).getSection().equals(section.getSectionName())){
                result.add(bookRepository.get(i));
            }
        }
        if(result.isEmpty()){
            throw new NoDataFoundException("There are no books in this section");
        }
        return result.toArray(new Book[0]);
    }

    /**
     * Returns the book with the given ISBN.
     * Checks if the ISBN code is valid.
     */
    public Book getBookByISBN(String ISBN) throws NoDataFoundException, InvalidDataException {
        if (ISBN.length() != 13 || !ISBN.startsWith("973")) {
            throw new InvalidDataException("Invalid ISBN code");
        }
        for(int i = 0; i < bookRepository.getSize(); i++) {
            if (bookRepository.get(i) == null) {
                throw new NoDataFoundException("No books");
            }

            if (bookRepository.get(i).getISBN().equals(ISBN)){
                return bookRepository.get(i);
            }
        }
        throw new NoDataFoundException("There is no book with given ISBN code");
    }

    /**
     * Returns the book with the given ISBN.
     * Checks if the ISBN code is valid.
     */
    public Section getSectionByName(String name) throws NoDataFoundException {

        for(int i = 0; i < sectionRepository.getSize(); i++) {
            if (sectionRepository.get(i) == null) {
                throw new NoDataFoundException("No sections");
            }

            if (sectionRepository.get(i).getSectionName().equals(name)){
                return sectionRepository.get(i);
            }
        }
        throw new NoDataFoundException("There is no section with given name");
    }
}
