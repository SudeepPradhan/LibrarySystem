package businesscontrollers;

import java.util.List;
import models.base.Address;
import models.base.Author;

public interface AuthorController {
   
    /**
     * Adds a new Author.
     * 
     * @param firstName the first name..
     * @param lastName the last name.
     * @param address the {@link Address}. Can be null.
     * @param phoneNumber the phone number.
     * @param credentials the credentials.
     * @param bibliography the bibliography.
     * @return 
     */
    boolean createAuthor(String firstName, String lastName,  Address address, String phoneNumber, String credentials, String bibliography);
    
    /**
     * Updates an existing author.
     * @param author the {@link  Author} instance.
     * @param firstName the first name..
     * @param lastName the last name.
     * @param address the {@link Address}. Can be null.
     * @param phoneNumber the phone number.
     * @param credentials the credentials.
     * @param bibliography the bibliography.
     * @return true if the operation succeeds, false otherwise.
     */
    boolean updateAuthor(Author author, String firstName,  String lastName, Address address, String phoneNumber, String credentials, String bibliography);
   
    /**
     * Search authors by last name.
     * 
     * @param lastName the last name or sequence of characters in last name.
     * @return list of {@link Author}s.
     */
    List<Author> searchAuthors(String name); 
    
    /**
     * Gets all authors.
     * @return  list of {@link Author}s.
     */
    List<Author> getAuthors();
}
