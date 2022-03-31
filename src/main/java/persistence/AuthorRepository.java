package persistence;

import domain.Author;

import java.util.Arrays;

public class AuthorRepository implements GenericRepository<Author>{
    
    private Author[] authorArray = new Author[5];
    
    @Override
    public void add(Author entity) {
        for (int i = 0; i < authorArray.length; i++){
            if (authorArray[i] == null) {
                authorArray[i] = entity;
                return;
            }
        }
        //if there's no more room, we extend the capacity
        Author[] newAuthor = Arrays.<Author,Author>copyOf(authorArray, 2*authorArray.length, Author[].class);
        newAuthor[authorArray.length] = entity;
        authorArray = newAuthor;
    }

    @Override
    public Author get(int i) {
        return authorArray[i];
    }

    @Override
    public void delete(Author entity) {
        if (authorArray == null){
            return;
        }

        Author[] newAuthor = new Author[authorArray.length -1 ];
        int j = 0;
        for (int i = 0; i < authorArray.length; i++) {
            if (!authorArray[i].equals(entity)){
                newAuthor[j] = authorArray[i];
                j++;
            }
        }
        return;
    }

    @Override
    public int getSize() {
        return authorArray.length;
    }
}
