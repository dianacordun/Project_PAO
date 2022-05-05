package services;

import domain.*;
import exceptions.InvalidDataException;
import exceptions.NoDataFoundException;
import persistence.BookRepository;
import persistence.CustomerBookLendRepository;
import persistence.CustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CustomersService {
    private CustomerRepository customerRepository = new CustomerRepository();
    private CustomerBookLendRepository customerBookLendRepository = new CustomerBookLendRepository();
    private BookRepository bookRepository = new BookRepository();

    public void addStorage(List<Customer> customers, List<Book> books, List<CustomerBookLend> cbl){
        for (int i=0;i<customers.size();i++){
            this.customerRepository.add(customers.get(i));
        }
        for (int i=0;i<books.size();i++){
            this.bookRepository.add(books.get(i));
        }
        for (int i=0;i<cbl.size();i++){
            this.customerBookLendRepository.add(cbl.get(i));
        }
    }

    /**
     * Adds a new author.
     * Checks if both last name and first name are valid.
     * Checks if the phone number and the email are valid.
     * The phone number should start with "07" and have 10 digits.
     */
    public void addNewCustomer(String fname, String lname, String phone, String email, Boolean membership) throws InvalidDataException {

        if (fname == null || fname.trim().isEmpty()) {
            throw new InvalidDataException("Invalid first name");
        }
        if (lname == null || lname.trim().isEmpty()) {
            throw new InvalidDataException("Invalid last name");
        }
        if (phone.length() != 10 || !phone.startsWith("07") ) {
            throw new InvalidDataException("Invalid phone number");
        }

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
        if (email == null || !Pattern.compile(emailRegex).matcher(email).matches()) {
            throw new InvalidDataException("Invalid email");
        }

        Customer customer = new Customer(fname, lname, phone, email, membership);
        customerRepository.add(customer);
    }


    /**
     * Registers a new book borrowing.
     * Checks if the date format is valid. Format is dd/mm/yyyy and the year must be between 1900 and 2099.
     * The returning date should be bigger than the lending one.
     */
    public void lendABook(Book b, Customer c, String lendDate,String returnDate, Boolean returned) throws InvalidDataException, NoDataFoundException {
        if (b.getNumberOfCopies() == 0){
            throw new NoDataFoundException("This book has no more copies left");
        }

        String dateRegex = "^(0[1-9]|1[0-2])\\/(0[1-9]|1\\d|2\\d|3[01])\\/(19|20)\\d{2}$";
        if (lendDate == null || !Pattern.compile(dateRegex).matcher(lendDate).matches()) {
            throw new InvalidDataException("Invalid date");
        }
        if (returnDate == null || !Pattern.compile(dateRegex).matcher(lendDate).matches()) {
            throw new InvalidDataException("Invalid date");
        }
        if(returnDate.equals(lendDate)) throw new InvalidDataException("Invalid return date: equal to lending date");

        if(Integer.parseInt(returnDate.substring(6,10)) < Integer.parseInt(lendDate.substring(6,10)))
            throw new InvalidDataException("Invalid return date: year is bigger");
        else if(Integer.parseInt(returnDate.substring(6,10)) == Integer.parseInt(lendDate.substring(6,10)) && Integer.parseInt(returnDate.substring(3,5)) < Integer.parseInt(lendDate.substring(3,5)))
                 throw new InvalidDataException("Invalid return date: month is bigger");
             else if(Integer.parseInt(returnDate.substring(3,5)) == Integer.parseInt(lendDate.substring(3,5)) && Integer.parseInt(returnDate.substring(0,2)) < Integer.parseInt(lendDate.substring(0,2)))
                 throw new InvalidDataException("Invalid return date: day is bigger");

        b.setNumberOfCopies(b.getNumberOfCopies() - 1);
        CustomerBookLend customerBookLend = new CustomerBookLend(c.getId(), b.getISBN(), lendDate, returnDate, returned);
        customerBookLendRepository.add(customerBookLend);

    }

    /**
     * Registers a book return.
     */
    public void returnABook(Book b, Customer c, String returnDate) throws NoDataFoundException {

        for(int i = 0 ; i < customerBookLendRepository.getSize(); i++){
            if (customerBookLendRepository.get(i) == null){
                throw new NoDataFoundException("No borrowings");
            }
            if(customerBookLendRepository.get(i).getBookIsbn().equals(b.getISBN()) && customerBookLendRepository.get(i).getCustomerId() == c.getId()){
                customerBookLendRepository.get(i).setReturnDate(returnDate);
                customerBookLendRepository.get(i).setReturned(true);
            }
        }
        b.setNumberOfCopies(b.getNumberOfCopies() + 1);
    }

    /**
     * Gets all books that are currently borrowed.
     */
    public Book[] getAllBorrowed() throws NoDataFoundException {

        List<Book> result = new ArrayList<>();
        String isbn;
        for(int i = 0 ; i < bookRepository.getSize(); i++)    {
            if (bookRepository.get(i) == null){
                throw new NoDataFoundException("No books");
            }

            isbn = bookRepository.get(i).getISBN();
            for(int j = 0 ; j < customerBookLendRepository.getSize(); j++){
                if (customerBookLendRepository.get(i) == null){
                    throw new NoDataFoundException("No borrowings");
                }

                if(customerBookLendRepository.get(i).getBookIsbn().equals(isbn) && !customerBookLendRepository.get(i).getReturned()){
                    result.add(bookRepository.get(i));
                    break; //means that at least one copy is borrowed
                }
            }
        }

        return result.toArray(new Book[0]);
    }

    /**
     * Get all books borrowed by a certain customer.
     */
    public Book[] getAllBorrowedByCustomer(Customer c) throws NoDataFoundException {
        List<Book> result = new ArrayList<>();
        String isbn;
        for(int i = 0 ; i < customerBookLendRepository.getSize(); i++)    {
            if (customerBookLendRepository.get(i) == null){
                throw new NoDataFoundException("No borrowings");
            }
            if(customerBookLendRepository.get(i).getCustomerId() == c.getId()){
                isbn = customerBookLendRepository.get(i).getBookIsbn();
                for(int j = 0 ; j < bookRepository.getSize(); j++){
                    if (bookRepository.get(i) == null){
                        throw new NoDataFoundException("No books");
                    }
                    if(bookRepository.get(i).getISBN().equals(isbn)){
                        result.add(bookRepository.get(i));
                    }
                }
            }
        }

        return result.toArray(new Book[0]);
    }

    /**
     * Returns the customer with the given id.
     */
    public Customer getCustomerById(int id) throws NoDataFoundException {

        for(int i = 0; i < customerRepository.getSize(); i++) {
            if (customerRepository.get(i) == null) {
                throw new NoDataFoundException("No customers");
            }

            if (customerRepository.get(i).getId() == id){
                return customerRepository.get(i);
            }
        }
        throw new NoDataFoundException("There is no customer with given id");
    }
}
