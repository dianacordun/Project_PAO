# PAO Project

This project implements a public library, where a person can borrow books, well as attend events.
A book has an unique ISBN and there is a limited number of copies of every book.
The library organizes three types of events: "Meet the author", with an author as a guest, book launches and simple events(such as a group reading session).
When a book launch is added, the book is already registered and has an ISBN code.



## Entities

The library is implemented using the following entities:
+ Section
+ Book
+ Author
+ AuthorBook (manages many-to-many between Book and Author)
+ Customer
+ CustomerBookLend (manages many-to-many between Book and Customer)
+ Event
+ MeetTheAuthor
+ BookLaunch


## Relationships between entities

A book belongs to only one section, but it can be written by multiple authors. 
An author can write multiple books.
A customer can lend multiple books, and the same book can be lent by different customers (if there are enough copies left or the borrowed copies have been returned).
A "Meet the author" event has only one author as a guest, and a book launch is dedicated to only one book.

### Functionalities
#### Add
+ A section
+ A book
+ An author
+ An event (When an event is added, the user will have to choose between the 3 types available)
+ A customer

#### Delete
+ A book
+ An event

#### Actions
+ Change a book's price
+ Filter books by price
+ Filter books from a section by price
+ Lend a book
+ Return a book

#### List
+ Books in a section
+ Books ordered by price
+ Authors
+ Authors of a given book
+ Books written by a given author
+ Events (The user will choose if they want to see all events or just Meet The Authors / Book Launches)
+ Books that are currently borrowed
+ Books borrowed by given customer
