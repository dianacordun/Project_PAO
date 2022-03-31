package persistence;

import domain.AuthorBook;

import java.util.Arrays;

public class AuthorBookRepository implements GenericRepository<AuthorBook> {
    private AuthorBook[] authorBookArray = new AuthorBook[5];
    
    @Override
    public void add(AuthorBook entity) {
        for (int i = 0; i < authorBookArray.length; i++){
            if (authorBookArray[i] == null) {
                authorBookArray[i] = entity;
                return;
            }
        }
        //if there's no more room, we extend the capacity
        AuthorBook[] newAuthorBooks = Arrays.<AuthorBook,AuthorBook>copyOf(authorBookArray, 2*authorBookArray.length, AuthorBook[].class);
        newAuthorBooks[authorBookArray.length] = entity;
        authorBookArray = newAuthorBooks;
    }

    @Override
    public AuthorBook get(int i) {
        return authorBookArray[i];
    }

    @Override
    public void delete(AuthorBook entity) {
        if (authorBookArray == null){
            return;
        }

        AuthorBook[] newAuthorBooks = new AuthorBook[authorBookArray.length -1 ];
        int j = 0;
        for (int i = 0; i < authorBookArray.length; i++) {
            if (!authorBookArray[i].equals(entity)){
                newAuthorBooks[j] = authorBookArray[i];
                j++;
            }
        }
        return;
    }

    @Override
    public int getSize() {
        return authorBookArray.length;
    }
}
