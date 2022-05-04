package view;

import domain.*;
import exceptions.InvalidDataException;
import exceptions.NoDataFoundException;
import services.*;

import java.io.IOException;
import java.util.*;

public class ConsoleApp {
    private Scanner s = new Scanner(System.in);
    private AuthorsService authorsService = new AuthorsService();
    private BooksService booksService = new BooksService();
    private CustomersService customersService = new CustomersService();
    private EventsService eventsService = new EventsService();

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_PURPLE= "\u001B[35m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_BLACK = "\u001B[30m";

    public static void main(String args[]) {
        ConsoleApp app = new ConsoleApp();

        while (true) {
            app.showMenu();
            int option = app.readOption();
            try{
                app.execute(option);
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }

    private void showMenu() {
        System.out.println(ANSI_PURPLE_BACKGROUND + ANSI_BLACK + "☵☵☵ Welcome to Bookuria library! ☵☵☵" + ANSI_RESET);
        System.out.println("""
                       .--.                   .---.
                   .---|__|           .-.     |~~~|
                .--|===|--|_          |_|     |~~~|--.
                |  |===|  |'\\     .---!~|  .--|   |--|
                |%%|   |  |.'\\    |===| |--|%%|   |  |
                |%%|   |  |\\.'\\   |   | |__|  |   |  |
                |  |   |  | \\  \\  |===| |==|  |   |  |
                |  |   |__|  \\.'\\ |   |_|__|  |~~~|__|
                |  |===|--|   \\.'\\|===|~|--|%%|~~~|--|
                ^--^---'--^    `-'`---^-^--^--^---'--'\s""");
        System.out.println(ANSI_PURPLE + "What do you want to do?" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + " 1. " + ANSI_RESET + "Add a section");
        System.out.println(ANSI_PURPLE + " 2. " + ANSI_RESET + "Add a book");
        System.out.println(ANSI_PURPLE + " 3. " + ANSI_RESET + "Delete a book");
        System.out.println(ANSI_PURPLE + " 4. " + ANSI_RESET + "List all the books in a section");
        System.out.println(ANSI_PURPLE + " 5. " + ANSI_RESET + "Change a book's price");
        System.out.println(ANSI_PURPLE + " 6. " + ANSI_RESET + "Filter books by price");
        System.out.println(ANSI_PURPLE + " 7. " + ANSI_RESET + "Filter books from a section by price");
        System.out.println(ANSI_PURPLE + " 8. " + ANSI_RESET + "List all books ordered by price");
        System.out.println(ANSI_PURPLE + " 9. " + ANSI_RESET + "Add an author");
        System.out.println(ANSI_PURPLE + "10. " + ANSI_RESET + "List all authors");
        System.out.println(ANSI_PURPLE + "11. " + ANSI_RESET + "List all authors of a book");
        System.out.println(ANSI_PURPLE + "12. " + ANSI_RESET + "List all books written by an author");
        System.out.println(ANSI_PURPLE + "13. " + ANSI_RESET + "Add an event");
        System.out.println(ANSI_PURPLE + "14. " + ANSI_RESET + "Delete an event");
        System.out.println(ANSI_PURPLE + "15. " + ANSI_RESET + "List all events");
        System.out.println(ANSI_PURPLE + "16. " + ANSI_RESET + "Add a customer");
        System.out.println(ANSI_PURPLE + "17. " + ANSI_RESET + "Lend a book");
        System.out.println(ANSI_PURPLE + "18. " + ANSI_RESET + "Return a book");
        System.out.println(ANSI_PURPLE + "19. " + ANSI_RESET + "List all books that are currently borrowed");
        System.out.println(ANSI_PURPLE + "20. " + ANSI_RESET + "List all books borrowed by a customer");
        System.out.println(ANSI_PURPLE + "21. " + ANSI_RESET + "Exit");
    }

    private int readOption() {
        try{
            int option = readInt();
            if (option >= 1 && option <= 21) return option;

        } catch (InvalidDataException invalid) {}

        System.out.println("Invalid option. Try again");
        return readOption();
    }

    private int readEventOption(){
        try{
            int option = readInt();
            if (option >= 1 && option <= 3) return option;

        } catch (InvalidDataException invalid) {}

        System.out.println("Invalid option. Try again");
        return readOption();
    }

    private void execute(int option) throws IOException {
        AuditingService auditingService = new AuditingService();
        try {
            switch (option) {
                case 1:
                    //Add a section
                    AddSection();
                    auditingService.addActionToHistory("Added section");
                    break;
                case 2:
                    // Add a book
                    AddBook();
                    auditingService.addActionToHistory("Added book");
                    break;

                case 3:
                    // Delete a book
                    DeleteBook();
                    auditingService.addActionToHistory("Deleted a book");
                    break;
                case 4:
                    // List all the books in a section
                    ListSectionBooks();
                    auditingService.addActionToHistory("Listed all books in a section");
                    break;
                case 5:
                    // Change a book's price
                    ChangeBookPrice();
                    auditingService.addActionToHistory("Changed a book's price");
                    break;
                case 6:
                    // Filter books by price
                    FilterBooksByPrice();
                    auditingService.addActionToHistory("Filtered books by price");
                    break;

                case 7:
                    // Filter books from a section by price
                    FilterBooksFromSectionByPrice();
                    auditingService.addActionToHistory("Filtered books from a section by price");
                    break;
                case 8:
                    // List all books ordered by price
                    ListAllBooksOrderedByPrice();
                    auditingService.addActionToHistory("Listed all books ordered by price");
                    break;
                case 9:
                    // Add an author
                    AddAnAuthor();
                    auditingService.addActionToHistory("Added an author");
                    break;
                case 10:
                    // List all authors
                    ListAllAuthors();
                    auditingService.addActionToHistory("Listed all authors");
                    break;
                case 11:
                    // List all authors of a book
                    ListAllAuthorsOfABook();
                    auditingService.addActionToHistory("Listed all authors of a book");
                    break;
                case 12:
                    // List all books written by an author
                    ListAllBooksWrittenByAuthor();
                    auditingService.addActionToHistory("Listed all books written by an author");
                    break;
                case 13:
                    // Add an event
                    AddAnEvent();
                    auditingService.addActionToHistory("Added an event");
                    break;
                case 14:
                    // Delete an event
                    DeleteAnEvent();
                    auditingService.addActionToHistory("Deleted an event");
                    break;
                case 15:
                    // List all events
                    ListAllEvents();
                    auditingService.addActionToHistory("Listed all events");
                    break;
                case 16:
                    // Add a customer
                    AddACustomer();
                    auditingService.addActionToHistory("Added a customer");
                    break;
                case 17:
                    // Lend a book
                    LendABook();
                    auditingService.addActionToHistory("Lent a book");
                    break;
                case 18:
                    // Return a book
                    ReturnABook();
                    auditingService.addActionToHistory("Returned a book");
                    break;
                case 19:
                    // List all books that are currently borrowed
                    ListBorrowedBooks();
                    auditingService.addActionToHistory("Listed all books that are currently borrowed");
                    break;
                case 20:
                    // List all books borrowed by a customer
                    ListBorrowedBooksByCustomer();
                    auditingService.addActionToHistory("Listed all books borrowed by a customer");
                    break;
                case 21: {
                    System.out.println(ANSI_PURPLE_BACKGROUND + ANSI_BLACK + "Thank you for visiting us!☺" + ANSI_RESET);
                    System.exit(0);
                    break;
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    private int readInt() throws InvalidDataException {
        String line = s.nextLine();
        if (line.matches("^\\d+$")) {
            return Integer.parseInt(line);
        } else {
            throw new InvalidDataException("Invalid number");
        }
    }

    private void AddSection(){
        System.out.print("Section name: ");
        String name = s.nextLine();
        System.out.print("Section room (must be between 1 and 30): ");
        String room = s.nextLine();
        if (room.matches("^\\d+$")) {
            try {
                booksService.addNewSection(name, Integer.parseInt(room));
                System.out.println("A new section was successfully added!");
            } catch (InvalidDataException invalidData) {
                System.out.println(invalidData.getMessage());
            }
        } else {
            System.out.println("Invalid section room number");
        }
    }

    private void AddBook(){
        System.out.print("ISBN (consists of 13 digits and starts with 973): ");
        String isbn = s.nextLine();
        System.out.print("Price (between 10 and 200 lei, maximum 2 decimals): ");
        String price = s.nextLine();
        System.out.print("Title: ");
        String title = s.nextLine();
        System.out.print("Publisher: ");
        String publisher = s.nextLine();
        System.out.print("Number of copies: ");
        String nrOfCopies = s.nextLine();
        System.out.print("Section name: ");
        String sName = s.nextLine();


        if (price.matches("^(\\d+(\\.\\d{0,2})?|\\.?\\d{1,2})$") && nrOfCopies.matches("^\\d+$")) {
            try {
                Section section = booksService.findSectionByName(sName);
                booksService.addNewBook(isbn, Double.parseDouble(price), title, publisher, Integer.parseInt(nrOfCopies), section);
                System.out.println("A new book was successfully added!");
            } catch (InvalidDataException | NoDataFoundException invalidData) {
                System.out.println(invalidData.getMessage());
            }
        } else {
            System.out.println("Invalid price or invalid number of copies");
        }
    }

    private void DeleteBook(){
        System.out.print("Book ISBN: ");
        String isbn = s.nextLine();

        try {
            booksService.deleteBook(isbn);
            System.out.println("Book with ISBN " + isbn + " was deleted!");
        } catch (NoDataFoundException noData) {
            System.out.println(noData.getMessage());
        }
    }

    private void ListSectionBooks(){
        System.out.print("Section name: ");
        String sName = s.nextLine();

        try {
            Section section = booksService.findSectionByName(sName);
            try{
                Book[] sectionBooks = booksService.getAllBooksFromSection(section);
                System.out.println("All the books in this section: ");
                for (Book sectionBook : sectionBooks) {
                    System.out.println(sectionBook.getTitle());
                }
            } catch (NoDataFoundException noData){
                System.out.println(noData.getMessage());
            }
        } catch (InvalidDataException | NoDataFoundException invalidData) {
            System.out.println(invalidData.getMessage());
        }
    }

    private void ChangeBookPrice(){
        System.out.print("Book ISBN: ");
        String isbn = s.nextLine();
        System.out.print("New Price (must be between 10 and 200 lei, maximum 2 decimals) : ");
        String newprice = s.nextLine();


        if (newprice.matches("^(\\d+(\\.\\d{0,2})?|\\.?\\d{1,2})$")) {
            try {
                booksService.changeBookPrice(isbn, Double.parseDouble(newprice));
            } catch (InvalidDataException | NoDataFoundException invalidData) {
                System.out.println(invalidData.getMessage());
            }
        } else {
            System.out.println("Invalid price");
        }
    }

    private void FilterBooksByPrice(){
        System.out.print("Price you want to filter by (must be between 10 and 200 lei, maximum 2 decimals) : ");
        String price = s.nextLine();

        if (price.matches("^(\\d+(\\.\\d{0,2})?|\\.?\\d{1,2})$")) {
            try {
                Book[] filtered = booksService.filterBooksByPrice(Double.parseDouble(price));
                for (Book book : filtered) {
                    System.out.println(book.getTitle());
                }
            } catch (InvalidDataException | NoDataFoundException invalidData) {
                System.out.println(invalidData.getMessage());
            }
        } else {
            System.out.println("Invalid price");
        }
    }

    private void FilterBooksFromSectionByPrice(){
        System.out.print("Price you want to filter by (must be between 10 and 200 lei, maximum 2 decimals) : ");
        String price = s.nextLine();
        System.out.print("Section name : ");
        String name = s.nextLine();
        try{
            Section section = booksService.findSectionByName(name);
            if (price.matches("^(\\d+(\\.\\d{0,2})?|\\.?\\d{1,2})$")) {
                try {
                    Book[] filtered = booksService.filterBooksFromSectionByPrice(section, Double.parseDouble(price));
                    for (Book book : filtered) {
                        System.out.println(book.getTitle());
                    }
                } catch (InvalidDataException | NoDataFoundException invalidData) {
                    System.out.println(invalidData.getMessage());
                }
            } else {
                System.out.println("Invalid price");
            }

        }catch (InvalidDataException | NoDataFoundException invalidData) {
            System.out.println(invalidData.getMessage());
        }
    }

    private void ListAllBooksOrderedByPrice(){
        try{
            Book[] ordered = booksService.orderByPriceAscending();
            for (Book book : ordered) {
                System.out.println(book.getTitle() + " " + book.getPrice());
            }
        }catch (NoDataFoundException noDataFoundException){
            System.out.println(noDataFoundException.getMessage());
        }

    }

    private void AddAnAuthor(){
        System.out.print("First name: ");
        String fname = s.nextLine();
        System.out.print("Last name: ");
        String lname = s.nextLine();

        try{
            authorsService.addNewAuthor(fname, lname);
        }catch (InvalidDataException invalidData){
            System.out.println(invalidData.getMessage());
        }
    }

    private void ListAllAuthors(){
        try{
            Author[] authors = authorsService.getAllAuthors();
            for (Author author : authors) {
                System.out.println(author.getFirstName() + " " + author.getLastName());
            }
        }catch (NoDataFoundException noDataFoundException){
            System.out.println(noDataFoundException.getMessage());
        }

    }
    private void ListAllAuthorsOfABook(){
        System.out.print("Book title: ");
        String title = s.nextLine();

        try{
            HashMap<Integer, List<String>> authors = authorsService.getAuthorsOfTitle(title);

            for (Map.Entry<Integer, List<String>> entry : authors.entrySet()) {
                System.out.println("Authors for title " + entry.getKey() + " : ");
                entry.getValue().forEach(System.out::println);
            }

        }catch (NoDataFoundException noDataFoundException){
            System.out.println(noDataFoundException.getMessage());
        }

    }

    private void ListAllBooksWrittenByAuthor(){
        System.out.print("Author's first name: ");
        String fname = s.nextLine();
        System.out.print("Author's last name: ");
        String lname = s.nextLine();

        try{
            HashMap<Integer, List<String>> authors = authorsService.GetAllAuthorBooks(fname,lname);

            for (Map.Entry<Integer, List<String>> entry : authors.entrySet()) {
                System.out.println("Books written by author " + entry.getKey() + " : ");
                entry.getValue().forEach(System.out::println);
            }

        }catch (NoDataFoundException noDataFoundException){
            System.out.println(noDataFoundException.getMessage());
        }
    }

    private void AddAnEvent(){
        System.out.print("What type of event do you want to add? \n");
        System.out.print("1 - Simple \n");
        System.out.print("2 - Meet The Author \n");
        System.out.print("3 - Book Launch \n");

        int eventOption = readEventOption();
        System.out.print("Event location: ");
        String location = s.nextLine();
        System.out.print("Organizer: ");
        String organizer = s.nextLine();
        System.out.print("Name (Unique): ");
        String name = s.nextLine();
        System.out.print("Date (dd/mm/yyyy): ");
        String date = s.nextLine();

        switch (eventOption) {
            case 1:
                // Simple
                try{
                    eventsService.addNewEvent(location, organizer, name, date);
                }catch (InvalidDataException | NoDataFoundException invalidData){
                    System.out.println(invalidData.getMessage());
                }
                break;
            case 2:
                // Meet The Author
                System.out.print("Author id: ");
                String id = s.nextLine();

                if (id.matches("^\\d+$")) {
                    try{
                        Author author = authorsService.getAuthorById(Integer.parseInt(id));
                        eventsService.addNewMTA(location, organizer, name, date, author);
                    }catch (InvalidDataException | NoDataFoundException invalidData){
                        System.out.println(invalidData.getMessage());
                    }
                }else {
                    System.out.println("Invalid id");
                }
                break;
            case 3:
                // Book Launch
                System.out.print("ISBN (consists of 13 digits and starts with 973): ");
                String isbn = s.nextLine();

                try{
                    Book book = booksService.getBookByISBN(isbn);
                    eventsService.addNewBookLaunch(location, organizer, name, date, book);
                }catch (InvalidDataException | NoDataFoundException invalidData){
                    System.out.println(invalidData.getMessage());
                }
                break;
        }
    }

    private void DeleteAnEvent(){
        System.out.print("Event id: ");
        String id = s.nextLine();

        if (id.matches("^\\d+$")) {
            try{
                eventsService.deleteEvent(Integer.parseInt(id));
            }catch (NoDataFoundException noDataFoundException){
                System.out.println(noDataFoundException.getMessage());
            }
        }else {
            System.out.println("Invalid id");
        }
    }

    private void ListAllEvents(){
        System.out.print("What type of events do you want to list? \n");
        System.out.print("1 - All \n");
        System.out.print("2 - Meet The Author \n");
        System.out.print("3 - Book Launch \n");

        int eventOption = readEventOption();

        switch (eventOption) {
            case 1:
                // All
                try{
                Event[] events = eventsService.getAllEvents();
                    for (Event event : events) {
                        System.out.println(event.getName() + " - Location: " + event.getLocation() +
                                " - Organizer: " + event.getOrganizer() + " - Date: " + event.getDate());
                    }
                }catch (NoDataFoundException noDataFoundException){
                    System.out.println(noDataFoundException.getMessage());
                }
                break;
            case 2:
                // Meet The Author

                try{
                    MeetTheAuthor[] events = eventsService.getAllMTA();
                    for (MeetTheAuthor event : events) {
                        System.out.println(event.getName() + " - Location: " + event.getLocation() +
                                " - Organizer: " + event.getOrganizer() + " - Date: " + event.getDate() +
                                " - Author: " + event.getAutor().getLastName() + " " + event.getAutor().getFirstName());
                    }
                }catch (NoDataFoundException noDataFoundException){
                    System.out.println(noDataFoundException.getMessage());
                }
                break;
            case 3:
                // Book Launch
                try{
                    BookLaunch[] events = eventsService.getAllBookLaunches();
                    for (BookLaunch event : events) {
                        System.out.println(event.getName() + " - Location: " + event.getLocation() +
                                " - Organizer: " + event.getOrganizer() + " - Date: " + event.getDate() +
                                " - Book: " + event.getBook().getTitle());
                    }
                }catch (NoDataFoundException noDataFoundException){
                    System.out.println(noDataFoundException.getMessage());
                }
                break;
        }
    }

    private void AddACustomer(){
        System.out.print("First name: ");
        String fname = s.nextLine();
        System.out.print("Last name: ");
        String lname = s.nextLine();
        System.out.print("Phone (should start with 07 and have 10 digits): ");
        String phone = s.nextLine();
        System.out.print("Email: ");
        String email = s.nextLine();
        System.out.print("Does the customer have a membership? (1- Yes / Other number - No): ");
        String member = s.nextLine();

        if (member.matches("^\\d+$")) {
            try{
                boolean membership = member.equals("1");

                customersService.addNewCustomer(fname,lname,phone,email,membership);
            }catch (InvalidDataException invalidDataException){
                System.out.println(invalidDataException.getMessage());
            }
        }
        else {
            System.out.println("Invalid membership response");
        }

    }

    private void LendABook(){
        System.out.print("ISBN (consists of 13 digits and starts with 973): ");
        String isbn = s.nextLine();
        System.out.print("Customer id: ");
        String id = s.nextLine();
        System.out.print("Lend date (dd/mm/yyyy): ");
        String lendDate = s.nextLine();
        System.out.print("Return date (dd/mm/yyyy): ");
        String returnDate = s.nextLine();
        System.out.print("Has the book been returned? (1- Yes / Other number - No): ");
        String ret = s.nextLine();

        if (id.matches("^\\d+$")) {
            try {
                boolean returned = ret.equals("1");
                Book book = booksService.getBookByISBN(isbn);
                Customer customer = customersService.getCustomerById(Integer.parseInt(id));

                customersService.lendABook(book, customer, lendDate, returnDate, returned);
            } catch (InvalidDataException | NoDataFoundException invalidDataException) {
                System.out.println(invalidDataException.getMessage());
            }
        }
        else{
            System.out.println("Invalid customer id");
        }
    }


    private void ReturnABook(){
        System.out.print("ISBN (consists of 13 digits and starts with 973): ");
        String isbn = s.nextLine();
        System.out.print("Customer id: ");
        String id = s.nextLine();
        System.out.print("Return date (dd/mm/yyyy): ");
        String returnDate = s.nextLine();

        if (id.matches("^\\d+$")) {
            try {
                Book book = booksService.getBookByISBN(isbn);
                Customer customer = customersService.getCustomerById(Integer.parseInt(id));

                customersService.returnABook(book, customer, returnDate);
            } catch (InvalidDataException | NoDataFoundException invalidDataException) {
                System.out.println(invalidDataException.getMessage());
            }
        }else{
            System.out.println("Invalid customer id");
        }
    }


    private void ListBorrowedBooks(){
        try{
            Book[] borrowed = customersService.getAllBorrowed();
            for (Book book : borrowed) {
                System.out.println(book.getTitle() + " | ISBN: " + book.getISBN());
            }
        }catch (NoDataFoundException noDataFoundException)
        {
            noDataFoundException.getMessage();
        }
    }


    private void ListBorrowedBooksByCustomer(){
        System.out.print("Customer id: ");
        String id = s.nextLine();

        if (id.matches("^\\d+$")) {
            try {
                Customer customer = customersService.getCustomerById(Integer.parseInt(id));
                Book[] borrowed = customersService.getAllBorrowedByCustomer(customer);

                for (Book book : borrowed) {
                    System.out.println(book.getTitle() + " | ISBN: " + book.getISBN());
                }

            } catch ( NoDataFoundException noDataFoundException) {
                System.out.println(noDataFoundException.getMessage());
            }
        }else{
            System.out.println("Invalid customer id");
        }
    }
}
