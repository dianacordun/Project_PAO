package services;

import domain.Author;
import domain.Book;
import exceptions.InvalidDataException;
import exceptions.NoDataFoundException;
import persistence.AuthorBookRepository;
import persistence.AuthorRepository;
import persistence.BookRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AuthorsService {
    private AuthorRepository authorRepository = new AuthorRepository();
    private BookRepository bookRepository = new BookRepository();
    private AuthorBookRepository authorBookRepository = new AuthorBookRepository();

    /**
     * Adds a new author.
     * Checks if both last name and first name are valid.
     */
    public void addNewAuthor(String fname, String lname) throws InvalidDataException {

        if (fname == null || fname.trim().isEmpty()) {
            throw new InvalidDataException("Invalid first name");
        }
        if (lname == null || lname.trim().isEmpty()) {
            throw new InvalidDataException("Invalid last name");
        }

        Author author = new Author(fname, lname);
        authorRepository.add(author);
    }

    /**
     * Returns all authors.
     */
    public Author[] getAllAuthors() throws NoDataFoundException {
        List<Author> result = new ArrayList<>();

        for (int i = 0; i < authorRepository.getSize(); i++) {
            if (authorRepository.get(i) == null){
                throw new NoDataFoundException("No authors");
            }
            result.add(authorRepository.get(i));
        }

        return result.toArray(new Author[0]);

    }

    /**
     * Return all authors of given book ISBN.
     * Checks if the ISBN exists.
     */
    public List<String> getAllAuthorsOfBookISBN(String ISBN) throws NoDataFoundException {

        List<String> authors = new ArrayList<>();

        for (int i = 0; i < authorBookRepository.getSize(); i++) {
            if (authorBookRepository.get(i).getBookIsbn().equals(ISBN)) {
                if (authorBookRepository.get(i) == null){
                    throw new NoDataFoundException("No borrowings");
                }

                //searching for author
                for (int j = 0; j < authorRepository.getSize(); j++) {
                    if (authorRepository.get(i) == null){
                        throw new NoDataFoundException("No authors");
                    }

                    if (authorRepository.get(j).getId() == authorBookRepository.get(i).getAuthorId()) {
                        authors.add(authorRepository.get(j).getLastName() + " " + authorRepository.get(j).getFirstName());
                    }
                }
            }
        }

        if(authors.isEmpty()){
            throw new NoDataFoundException("There is no books with given ISBN");
        }
        return authors;
    }

    /**
     * Return all authors of book, given its title.
     * If more books have the same title, all the authors will be returned.
     * Checks if the title is valid.
     */
    public HashMap<Integer,List<String>> getAuthorsOfTitle(String title) throws NoDataFoundException {

        HashMap<Integer,List<String>> result = new HashMap<>();

        Integer titleNr = 1;
        for (int i = 0; i < bookRepository.getSize(); i++) {
            if (bookRepository.get(i) == null){
                throw new NoDataFoundException("No books");
            }

            if(bookRepository.get(i).getTitle().equals(title)){
                result.put(titleNr,getAllAuthorsOfBookISBN(bookRepository.get(i).getISBN()));
                titleNr++;
            }
        }

        if(result.isEmpty()){
            throw new NoDataFoundException("There is no books with given title");
        }

        return result;
    }


    /**
     * Gets an author's book titles given its id.
     */
    public List<String> GetAuthorBooksById(int id) throws NoDataFoundException {
        List<String> books = new ArrayList<>();

        for (int i = 0; i < authorBookRepository.getSize(); i++) {
            if (authorBookRepository.get(i) == null){
                throw new NoDataFoundException("No borrowings");
            }

            if (authorBookRepository.get(i).getAuthorId() == id) {
                for (int j = 0; j < bookRepository.getSize(); j++) {
                    if (bookRepository.get(i) == null){
                        throw new NoDataFoundException("No books");
                    }

                    if (bookRepository.get(j).getISBN() == authorBookRepository.get(i).getBookIsbn()) {
                        books.add(bookRepository.get(j).getTitle());
                    }
                }
            }
        }
        if(books.isEmpty()){
            throw new NoDataFoundException("There is no author with given id");
        }

        return books;
    }

    /**
     * Return all the titles written by an author, given its fullname.
     * Checks if first name and last name are valid.
     * If two authors have the same name, all of their books will be returned.
     */
    public HashMap<Integer,List<String>> GetAllAuthorBooks(String fname, String lname) throws NoDataFoundException {
        HashMap<Integer,List<String>> result = new HashMap<>();

        Integer index = 1;
        for (int i = 0; i < authorRepository.getSize(); i++) {
            if (authorRepository.get(i) == null){
                throw new NoDataFoundException("No authors");
            }
            if(authorRepository.get(i).getFirstName().equals(fname) && authorRepository.get(i).getLastName().equals(lname)){
                result.put(index, GetAuthorBooksById(authorRepository.get(i).getId()));
                index++;
            }
        }

        if(result.isEmpty()){
            throw new NoDataFoundException("There are no authors with given name");
        }

        return result;
    }

    /**
     * Returns the author with the given id.
     */
    public Author getAuthorById(int id) throws NoDataFoundException {

        for(int i = 0; i < authorRepository.getSize(); i++) {
            if (authorRepository.get(i) == null) {
                throw new NoDataFoundException("No authors");
            }

            if (authorRepository.get(i).getId() == id){
                return authorRepository.get(i);
            }
        }
        throw new NoDataFoundException("There is no author with given id");
    }
}
