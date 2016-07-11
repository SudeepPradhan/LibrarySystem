package views;

import businesscontrollers.AuthorController;
import businesscontrollers.CirculationController;
import businesscontrollers.CirculationControllerImpl;
import businesscontrollers.PublicationController;
import businesscontrollers.AuthorControllerImpl;
import businesscontrollers.PublicationControllerImpl;
import businesscontrollers.UserManagementController;
import businesscontrollers.UserManagementControllerImpl;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import models.base.Address;
import models.base.Author;
import models.business.LibraryMember;
import businessmodels.User;
import models.business.publications.Book;
import utilities.PublicationValidation;
import utilities.UserValidation;
import utilities.AuthorValidation;
import utilities.AutoCompleteComboBoxListener;
import utilities.LibraryMemberValidation;
import businessmodels.CheckoutRecordEntry;
import businessmodels.Inventory;

public class MainPageController implements Initializable {

    private UserManagementController userManagementController;
    private CirculationController circulationController;
    private PublicationController publicationController;
    private AuthorController authorController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userManagementController = new UserManagementControllerImpl();
        circulationController = new CirculationControllerImpl();
        publicationController = new PublicationControllerImpl();
        authorController = new AuthorControllerImpl();

        clearInitialValues();
        setInitialUserState();
        setInitialLibraryMemberState();
        setInitialBookState();
        setInitialAuthorState();
        setInitialCirculationState();
    }
        
    private void clearInitialValues() {
        user_error_label.setText("");
        main_username_label.setText("");
        lib_mem_error_label.setText("");
        lib_mem_memId.setText("");
        book_error_label.setText("");
        author_error_label.setText("");
        cir_memId_label.setText("");
        cir_firstname_label.setText("");
        cir_lastname_label.setText("");
        cir_phone_label.setText("");
        cir_error_label.setText("");
    }

    /*
     * OUTER REGION
     */
    @FXML
    private Label main_username_label;

    @FXML
    void logout_button_clicked(ActionEvent event) {
        userManagementController.logout();
    }
    /*
     * END OUTER REGION
     */
 /*
     * 
     */

 /* 
     * USER REGION
     */
    @FXML
    private Label user_error_label;

    @FXML
    private ListView<User> user_userList_list;

    @FXML
    private PasswordField user_password_textbox;

    @FXML
    private ComboBox<User.UserType> user_usertype_textbox;

    @FXML
    private PasswordField user_re_password_textbox;

    @FXML
    private TextField user_username_textbox;

    @FXML
    private Button user_save_button;

    @FXML
    private Button user_edit_button;

    @FXML
    private Button user_create_button;

    @FXML
    private Button user_cancel_button;

    @FXML
    private Button user_delete_button;

    private User selectedUser = null;

    private void setInitialUserState() {
        user_create_button.setDisable(false);
        user_save_button.setDisable(true);
        user_edit_button.setDisable(true);
        user_delete_button.setDisable(true);
        user_cancel_button.setDisable(true);
        user_usertype_textbox.getItems().removeAll(user_usertype_textbox.getItems());
        user_usertype_textbox.getItems().addAll(
                User.UserType.ADMIN,
                User.UserType.LIBRARIAN,
                User.UserType.BOTH
        );
        setUserForm(false);
        populateUserTable();
        user_userList_list.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<User>() {
            public void changed(ObservableValue<? extends User> ov,
                    User old_val, User new_val) {
                user_list_changed(new_val);
            }
        });
    }

    @FXML
    private void user_edit_buttonclick(ActionEvent event) {
        user_create_button.setDisable(true);
        user_save_button.setDisable(false);
        user_edit_button.setDisable(true);
        user_delete_button.setDisable(true);
        user_cancel_button.setDisable(false);
        setUserForm(true);
    }

    @FXML
    private void user_delete_buttonclick(ActionEvent event) {
        int confirmationResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + selectedUser.getUsername());
        if (confirmationResult == JOptionPane.YES_OPTION) {
            if (userManagementController.deleteUser(selectedUser.getUsername())) {
                user_userList_list.getItems().remove(selectedUser);
                selectedUser = null;
                clearUserFields();
            }
        }
    }

    @FXML
    private void user_create_buttonclick(ActionEvent event) {
        user_create_button.setDisable(true);
        user_save_button.setDisable(false);
        user_edit_button.setDisable(true);
        user_delete_button.setDisable(true);
        user_cancel_button.setDisable(false);
        user_username_textbox.setText("");
        selectedUser = null;
        setUserForm(true);
    }

    @FXML
    private void user_save_buttonclick(ActionEvent event) {
        boolean saveSuccess = false;
        if (selectedUser == null) {
            String error = UserValidation.validate(user_username_textbox.getText().trim(), user_password_textbox.getText(), user_re_password_textbox.getText(), user_usertype_textbox.getSelectionModel().getSelectedItem());
            if (error != null) {
                user_error_label.setText(error);
                return;
            }
            if (userManagementController.createUser(user_username_textbox.getText().trim(), user_password_textbox.getText(), user_usertype_textbox.getSelectionModel().getSelectedItem())) {
                selectedUser = null;
                clearUserFields();
                populateUserTable();
                saveSuccess = true;
            }
        } else {
            String error = UserValidation.validate(user_password_textbox.getText(), user_re_password_textbox.getText(), user_usertype_textbox.getSelectionModel().getSelectedItem());
            if (error != null) {
                user_error_label.setText(error);
                return;
            }
            if (userManagementController.updateUser(selectedUser.getUsername(), user_password_textbox.getText(), user_usertype_textbox.getValue())) {
                clearUserFields();
                saveSuccess = true;
            }
        }

        if (saveSuccess) {
            user_create_button.setDisable(false);
            user_save_button.setDisable(true);
            user_edit_button.setDisable(true);
            user_delete_button.setDisable(true);
            user_cancel_button.setDisable(true);
            setUserForm(false);
        }
    }

    @FXML
    private void user_cancel_buttonclick(ActionEvent event) {
        user_create_button.setDisable(false);
        user_save_button.setDisable(true);
        user_delete_button.setDisable(true);
        user_cancel_button.setDisable(true);
        setUserForm(false);
        selectedUser = user_userList_list.getSelectionModel().getSelectedItem();
        user_edit_button.setDisable(selectedUser == null);
        clearUserFields();
    }

    private void user_list_changed(User selecttion) {
        selectedUser = selecttion;
        user_edit_button.setDisable(false);
        user_delete_button.setDisable(false);

        clearUserFields();
    }

    private void setUserForm(boolean enable) {
        user_username_textbox.setEditable(enable && selectedUser == null);
        user_password_textbox.setEditable(enable);
        user_re_password_textbox.setEditable(enable);
        user_usertype_textbox.setDisable(!enable);
        user_userList_list.setDisable(enable);
    }

    private void clearUserFields() {
        user_error_label.setText("");
        user_password_textbox.setText("");
        user_re_password_textbox.setText("");
        user_usertype_textbox.setValue(null);
        if (selectedUser == null) {
            user_username_textbox.setText("");
        } else {
            user_username_textbox.setText(selectedUser.getUsername());
            user_usertype_textbox.setValue(selectedUser.getUserType());
        }
    }

    private void populateUserTable() {
        List<User> users = userManagementController.getUsers();
        user_userList_list.getItems().removeAll(user_userList_list.getItems());
        user_userList_list.getItems().addAll(users);
    }

    /*
     * END USER REGION
     */
 /*
     * 
     */

 /* 
     * LIBRARY MEMBER REGION
     */
    @FXML
    private ListView<LibraryMember> lib_mem_userList_list;

    @FXML
    private TextField lib_mem_search_textbox;

    @FXML
    private Label lib_mem_error_label;
    @FXML
    private Label lib_mem_memId;
    @FXML
    private TextField lib_mem_firstname_textbox;
    @FXML
    private TextField lib_mem_lastname_textbox;
    @FXML
    private TextField lib_mem_phone_textbox;
    @FXML
    private TextField lib_mem_state_textbox;
    @FXML
    private TextField lib_mem_city_textbox;
    @FXML
    private TextField lib_mem_street_textbox;
    @FXML
    private TextField lib_mem_zip_textbox;

    @FXML
    private Button lib_mem_search_button;
    @FXML
    private Button lib_mem_create_button;
    @FXML
    private Button lib_mem_edit_button;
    @FXML
    private Button lib_mem_save_button;
    @FXML
    private Button lib_mem_cancel_button;

    private void setInitialLibraryMemberState() {
        populateLibraryMemberList();

        // add change listener in the listview
        lib_mem_userList_list.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<LibraryMember>() {
            public void changed(ObservableValue<? extends LibraryMember> ov,
                    LibraryMember old_val, LibraryMember new_val) {
                fillLibraryMemberForm(new_val);
            }
        });
    }

    @FXML
    void lib_mem_cancel_buttonclick(ActionEvent event) {
        fillLibraryMemberForm(lib_mem_userList_list.getSelectionModel().getSelectedItem());
    }

    @FXML
    void lib_mem_save_buttonclick(ActionEvent event) {
        lib_mem_error_label.setText("");
        String _memberId = lib_mem_memId.getText();
        String _memberFirstName = lib_mem_firstname_textbox.getText();
        String _memberLastName = lib_mem_lastname_textbox.getText();
        String _memberPhone = lib_mem_phone_textbox.getText();
        String _memberStreet = lib_mem_street_textbox.getText();
        String _memberState = lib_mem_state_textbox.getText();
        String _memberCity = lib_mem_city_textbox.getText();
        String _memberZip = lib_mem_zip_textbox.getText();

        String validation = LibraryMemberValidation.validate(
                _memberFirstName,
                _memberLastName,
                _memberPhone,
                _memberStreet,
                _memberCity,
                _memberState,
                _memberZip);

        if (validation != null) {
            lib_mem_error_label.setText(validation);
            return;
        }

        Address _memberAddress = new Address(
                _memberStreet,
                _memberCity,
                _memberState,
                _memberZip);

        if (Integer.parseInt(_memberId) <= gettLibraryMemberSize()) {
            circulationController.updateLibraryMember(
                    _memberId,
                    _memberFirstName,
                    _memberLastName,
                    _memberAddress,
                    _memberPhone);
        } else {
            circulationController.addLibraryMember(
                    _memberId,
                    _memberFirstName,
                    _memberLastName,
                    _memberAddress,
                    _memberPhone);
        }
        populateLibraryMemberList();
        lib_mem_userList_list.getSelectionModel().select(Integer.parseInt(_memberId) - 1);
    }

    @FXML
    void lib_mem_edit_buttonclick(ActionEvent event) {
        enableLibraryMemberForm(true);
        libMemberButtonsEnableDisable(true, true, false, false);
    }

    @FXML
    void lib_mem_create_buttonclick(ActionEvent event) {
        clearLibraryMemberForm();
        lib_mem_memId.setText(getNewtLibraryMemberId());
        enableLibraryMemberForm(true);
        libMemberButtonsEnableDisable(true, true, false, false);
    }

    @FXML
    void lib_mem_search_buttonclick(ActionEvent event) {
        if (lib_mem_search_textbox.getText().trim().isEmpty()) {
            populateLibraryMemberList();
        } else {
            LibraryMember libraryMember = circulationController.searchLibraryMember(lib_mem_search_textbox.getText());
            lib_mem_userList_list.getItems().removeAll(lib_mem_userList_list.getItems());
            if (libraryMember == null) {
                lib_mem_userList_list.getItems().removeAll(lib_mem_userList_list.getItems());
                clearLibraryMemberForm();
                libMemberButtonsEnableDisable(false, true, true, true);
            } else {
                lib_mem_userList_list.getItems().add(libraryMember);
            }
        }

        lib_mem_userList_list.getSelectionModel().select(0);
        lib_mem_error_label.setText("");
    }

    private void enableLibraryMemberForm(boolean enable) {
        lib_mem_firstname_textbox.setEditable(enable);
        lib_mem_lastname_textbox.setEditable(enable);
        lib_mem_phone_textbox.setEditable(enable);
        lib_mem_state_textbox.setEditable(enable);
        lib_mem_city_textbox.setEditable(enable);
        lib_mem_street_textbox.setEditable(enable);
        lib_mem_zip_textbox.setEditable(enable);
    }

    private void clearLibraryMemberForm() {
        lib_mem_memId.setText("");
        lib_mem_firstname_textbox.setText("");
        lib_mem_lastname_textbox.setText("");
        lib_mem_phone_textbox.setText("");
        lib_mem_state_textbox.setText("");
        lib_mem_city_textbox.setText("");
        lib_mem_street_textbox.setText("");
        lib_mem_zip_textbox.setText("");
    }

    private void populateLibraryMemberList() {
        List<LibraryMember> libraryList = circulationController.getLibraryMembers();
        lib_mem_userList_list.getItems().removeAll(lib_mem_userList_list.getItems());
        lib_mem_userList_list.getItems().addAll(libraryList);

        enableLibraryMemberForm(false);
        libMemberButtonsEnableDisable(false, true, true, true);
    }

    private void fillLibraryMemberForm(LibraryMember libraryMember) {
        if (libraryMember == null) {
            lib_mem_memId.setText("");
            libMemberButtonsEnableDisable(false, true, true, true);
            return;
        }

        enableLibraryMemberForm(false);
        lib_mem_memId.setText(libraryMember.getMemberId());
        lib_mem_firstname_textbox.setText(libraryMember.getFirstName());
        lib_mem_lastname_textbox.setText(libraryMember.getLastName());
        lib_mem_phone_textbox.setText(libraryMember.getPhoneNumber());
        lib_mem_state_textbox.setText(libraryMember.getAddress().getState());
        lib_mem_city_textbox.setText(libraryMember.getAddress().getCity());
        lib_mem_street_textbox.setText(libraryMember.getAddress().getStreet());
        lib_mem_zip_textbox.setText(libraryMember.getAddress().getZip());

        libMemberButtonsEnableDisable(false, false, true, true);
    }

    private void libMemberButtonsEnableDisable(boolean create, boolean edit, boolean save, boolean cancel) {
        lib_mem_error_label.setText("");
        lib_mem_create_button.setDisable(create);
        lib_mem_edit_button.setDisable(edit);
        lib_mem_save_button.setDisable(save);
        lib_mem_cancel_button.setDisable(cancel);
    }

    private String getNewtLibraryMemberId() {
        return Integer.toString(gettLibraryMemberSize() + 1);
    }

    private int gettLibraryMemberSize() {
        return circulationController.getLibraryMembers().size();
    }
    /*
     * END LIBRARY MEMBER REGION
     */
 /*
     * 
     */
 /* 
     * BOOK REGION
     */
    @FXML
    private Tab book_tab;

    @FXML
    private Label book_error_label;

    @FXML
    private TextField book_isbn_textfield;

    @FXML
    private TextField book_titlesearch_textfield;

    @FXML
    private ListView<Book> book_list_listview;

    @FXML
    private TextField book_title_textfield;

    @FXML
    private TextField book_loanperiod_textfield;

    @FXML
    private TextField book_addcopies_textbox;

    @FXML
    private Button book_addauthor_button;

    @FXML
    private Button book_removeauthor_button;

    @FXML
    private Button book_addcopies_button;

    @FXML
    private Button book_save_button;

    @FXML
    private Button book_cancel_button;

    @FXML
    private Button book_create_button;

    @FXML
    private Button book_edit_button;

    @FXML
    private ListView<Author> book_authorslist_listview;

    @FXML
    private TextField book_numberofcopies_textfield;

    @FXML
    private ComboBox<Author> book_authors_combobox;

    private boolean createNewBook = false;

    private static final String DEFAULT_LOAN_PERIOD = "21";
    private static final String DEFAULT_NUMBER_OF_COPIES = "1";

    private void setInitialBookState() {

        publicationController = new PublicationControllerImpl();
        authorController = new AuthorControllerImpl();
        populateBookList(publicationController.getBooks());
        populateAuthorsCombobox(authorController.getAuthors());

        new AutoCompleteComboBoxListener(book_authors_combobox);

        book_isbn_textfield.setEditable(false);
        book_title_textfield.setEditable(false);
        book_loanperiod_textfield.setEditable(false);
        book_numberofcopies_textfield.setEditable(false);
        book_authors_combobox.setEditable(false);

        book_list_listview.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Book>() {

            @Override
            public void changed(ObservableValue<? extends Book> observable, Book oldValue, Book newValue) {
                if (newValue != null) {
                    showBookDetails(newValue);
                }
            }
        });
    }

    private void showBookDetails(Book book) {

        resetBookFieldsAndControlls();

        book_edit_button.setDisable(false);

        book_isbn_textfield.setEditable(false);
        book_isbn_textfield.setText(book.getIsbn());
        book_title_textfield.setText(book.getTitle());
        book_numberofcopies_textfield.setText(String.valueOf(book.getNumberOfAvailableInventory()));
        book_loanperiod_textfield.setText(String.valueOf(book.getBorrowDuration()));

        book_authorslist_listview.getItems().clear();
        book_authorslist_listview.getItems().addAll(book.getAuthors());
    }

    @FXML
    void book_save_buttonclick(ActionEvent event) {
        int borrowDuration;
        int numberOfCopies;
        try {
            borrowDuration = Integer.parseInt(book_loanperiod_textfield.getText());
        } catch (NumberFormatException ex) {
            book_error_label.setText("Invalid input: loan period");
            return;
        }
        try {
            numberOfCopies = Integer.parseInt(book_numberofcopies_textfield.getText());
        } catch (NumberFormatException ex) {
            book_error_label.setText("Invalid input: number of copies");
            return;
        }
        List<Author> authors = new ArrayList<>();
        authors.addAll(book_authorslist_listview.getItems());
        String errorMessage = PublicationValidation.validateBook(book_title_textfield.getText(), borrowDuration, book_isbn_textfield.getText(), authors, createNewBook);
        if (errorMessage != null) {
            book_error_label.setText(errorMessage);
        } else if (createNewBook) {
            Book book = publicationController.addBook(book_isbn_textfield.getText(), book_title_textfield.getText(), 0.0, 0.0, borrowDuration, authors);
            if (book != null) {
                publicationController.addCopies(book_isbn_textfield.getText(), numberOfCopies);
                book_list_listview.getItems().add(book);
                resetBookFieldsAndControlls();
                book_list_listview.getSelectionModel().select(book);
            } else {
                resetBookFieldsAndControlls(); 
            }
        } else {
            int index = book_list_listview.getSelectionModel().getSelectedIndex();
            if (index > -1) {
                Book book = book_list_listview.getSelectionModel().getSelectedItem();
                int currentNumberOfCopies = (int) book.getNumberOfAvailableInventory();
                if (numberOfCopies > currentNumberOfCopies) {
                    publicationController.addCopies(book_isbn_textfield.getText(), numberOfCopies - currentNumberOfCopies);
                } else if (numberOfCopies < currentNumberOfCopies) {
                    book_error_label.setText("Reducing copies not supported");
                    return;
                }
                publicationController.updateBook(book_isbn_textfield.getText(), book_title_textfield.getText(), borrowDuration, authors);

                book_searchwititle_enter(null);
            }
            resetBookFieldsAndControlls();
        }

    }

    @FXML
    void book_edit_buttonclick(ActionEvent event) {
        book_save_button.setDisable(false);
        book_cancel_button.setDisable(false);
        book_edit_button.setDisable(true);
        book_create_button.setDisable(true);

        book_addauthor_button.setDisable(false);
        book_removeauthor_button.setDisable(false);
        book_addcopies_button.setDisable(false);

        book_isbn_textfield.setEditable(false);
        book_title_textfield.setEditable(true);
        book_loanperiod_textfield.setEditable(true);
        book_numberofcopies_textfield.setEditable(true);
        book_authors_combobox.setEditable(true);
    }

    @FXML
    void book_searchwititle_enter(ActionEvent event) {
        Book isbnBook = publicationController.searchBookWithIsbn(book_titlesearch_textfield.getText());
        List<Book> books;
        if (isbnBook != null) {
            books = new ArrayList<Book>();
            books.add(isbnBook);
        } else {
            books = publicationController.searchBooksWithTitle(book_titlesearch_textfield.getText());
        }
        book_list_listview.getItems().clear();
        book_list_listview.getItems().addAll(books);
    }

    @FXML
    void book_addcopies_buttonclick(ActionEvent event) {
        try {
            int numberOfCopies = Integer.parseInt(book_numberofcopies_textfield.getText());
            book_numberofcopies_textfield.setText(String.valueOf(numberOfCopies + 1));
        } catch (NumberFormatException ex) {
            book_error_label.setText("Invalid input: number of copies");
        }
    }

    @FXML
    void book_addauthor_buttonclick(ActionEvent event) {
        int selectedAuthorIndex = book_authors_combobox.getSelectionModel().getSelectedIndex();
        if (selectedAuthorIndex > -1) {
            book_authorslist_listview.getItems().add(book_authors_combobox.getItems().get(selectedAuthorIndex));
        }
    }

    @FXML
    void book_removeuthor_buttonclick(ActionEvent event) {
        Author author = book_authorslist_listview.getSelectionModel().getSelectedItem();
        if (author != null) {
            book_authorslist_listview.getItems().remove(author);
        }
    }

    @FXML
    void book_cancel_button_click(ActionEvent event) {
        book_list_listview.getSelectionModel().clearSelection();
        resetBookFieldsAndControlls();
    }

    private void resetBookFieldsAndControlls() {

        createNewBook = false;
        clearBookFields();

        book_create_button.setDisable(false);
        book_edit_button.setDisable(true);
        book_save_button.setDisable(true);
        book_cancel_button.setDisable(true);

        book_addcopies_button.setDisable(true);
        book_addauthor_button.setDisable(true);
        book_removeauthor_button.setDisable(true);

        book_isbn_textfield.setEditable(false);
        book_title_textfield.setEditable(false);
        book_loanperiod_textfield.setEditable(false);
        book_numberofcopies_textfield.setEditable(false);
        book_authors_combobox.setEditable(false);

        book_error_label.setText(null);

    }

    @FXML
    void book_tab_selection_changed(Event event) {
        if (book_tab.isSelected()) {
            setInitialBookState();
        }
    }

    private void clearBookFields() {
        book_isbn_textfield.setText(null);
        book_title_textfield.setText(null);
        book_loanperiod_textfield.setText(DEFAULT_LOAN_PERIOD);
        book_numberofcopies_textfield.setText(DEFAULT_NUMBER_OF_COPIES);
        book_authorslist_listview.getItems().clear();
    }

    @FXML
    void book_create_button(ActionEvent event) {
        createNewBook = true;
        clearBookFields();
        book_isbn_textfield.setEditable(true);

        book_list_listview.getSelectionModel().clearSelection();

        book_save_button.setDisable(false);
        book_cancel_button.setDisable(false);

        book_edit_button.setDisable(true);
        book_create_button.setDisable(true);

        book_addauthor_button.setDisable(false);
        book_removeauthor_button.setDisable(false);

        book_isbn_textfield.setEditable(true);
        book_title_textfield.setEditable(true);
        book_loanperiod_textfield.setEditable(true);
        book_numberofcopies_textfield.setEditable(true);
        book_authors_combobox.setEditable(true);
    }

    void populateBookList(List<Book> books) {
        book_list_listview.getItems().clear();
        book_list_listview.getItems().addAll(books);
    }

    void populateBookAuthorList(List<Author> authors) {
        book_authorslist_listview.getItems().clear();
        book_authorslist_listview.getItems().addAll(authors);
    }

    void addAuthorsToBook(Author author) {
        book_authorslist_listview.getItems().add(author);
    }

    void populateAuthorsCombobox(List<Author> authors) {
        book_authors_combobox.getItems().clear();
        book_authors_combobox.getItems().addAll(authors);
    }

    private void populateLibraryMemberTable() {
        // List<LibraryMember> users = circulationController.getUsers();
//    user_userList_list.getItems().removeAll(user_userList_list.getItems());
//    user_userList_list.getItems().addAll(users);
    }

    /*
     * END BOOK REGION
     */
 /* 
     * AUTHOR REGION
     */
    @FXML
    private Label author_error_label;
    @FXML
    private TextField author_firstname_textbox;
    @FXML
    private TextField author_lastname_textbox;
    @FXML
    private TextField author_phone_textbox;
    @FXML
    private TextArea author_credential_textbox;
    @FXML
    private TextArea author_biography_textbox;
    @FXML
    private TextField author_state_textbox;
    @FXML
    private TextField author_city_textbox;
    @FXML
    private TextField author_street_textbox;
    @FXML
    private TextField author_zip_textbox;
    @FXML
    private ListView<Author> author_authorList_list;
    @FXML
    private TextField author_search_textbox;

    @FXML
    private Button author_save_button;
    @FXML
    private Button author_cancel_button;
    @FXML
    private Button author_edit_button;
    @FXML
    private Button author_create_button;
    //@FXML
    //private Button author_delete_button;

    private Author selectedAuthor = null;

    private void setInitialAuthorState() {
        author_create_button.setDisable(false);
        author_save_button.setDisable(true);
        author_cancel_button.setDisable(true);
        author_edit_button.setDisable(true);
        setAuthorForm(false);
        populateAuthorTable();

        author_authorList_list.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Author>() {
            public void changed(ObservableValue<? extends Author> ov,
                    Author old_val, Author new_val) {
                author_list_changed(new_val);
            }
        });
    }

    @FXML
    void author_save_buttonclick(ActionEvent event) {
        boolean saveSuccess = false;

        String street = author_street_textbox.getText();
        String city = author_city_textbox.getText();
        String state = author_state_textbox.getText();
        String zip = author_zip_textbox.getText();
        String error = null;
        Address address = null;
        if (state.isEmpty() && city.isEmpty() && street.isEmpty() && zip.isEmpty()) {
            error = AuthorValidation.validate(author_firstname_textbox.getText(), author_lastname_textbox.getText(), author_phone_textbox.getText(), author_credential_textbox.getText(), author_biography_textbox.getText());
        } else {
            address = new Address(street, city, state, zip);
            error = AuthorValidation.validate(author_firstname_textbox.getText(), author_lastname_textbox.getText(), author_phone_textbox.getText(), author_credential_textbox.getText(), author_biography_textbox.getText(), street, city, state, zip);
        }

        if (error != null) {
            author_error_label.setText(error);
            return;
        }

        if (selectedAuthor == null) {
            if (authorController.createAuthor(author_firstname_textbox.getText(), author_lastname_textbox.getText(), address, author_phone_textbox.getText(), author_credential_textbox.getText(), author_biography_textbox.getText())) {
                selectedAuthor = null;
                saveSuccess = true;
            }
        } else if (authorController.updateAuthor(selectedAuthor, author_firstname_textbox.getText(), author_lastname_textbox.getText(), address, author_phone_textbox.getText(), author_credential_textbox.getText(), author_biography_textbox.getText())) {
            saveSuccess = true;
        }

        if (saveSuccess) {
            clearAuthorFields();
            populateAuthorTable();
            author_create_button.setDisable(false);
            author_save_button.setDisable(true);
            author_cancel_button.setDisable(true);
            author_edit_button.setDisable(true);
            setAuthorForm(false);
        }
    }

    @FXML
    void author_cancel_buttonclick(ActionEvent event) {
        author_create_button.setDisable(false);
        author_save_button.setDisable(true);
        author_cancel_button.setDisable(true);
        author_edit_button.setDisable(selectedAuthor == null);
        setAuthorForm(false);
        selectedAuthor = author_authorList_list.getSelectionModel().getSelectedItem();
        clearAuthorFields();
    }

    @FXML
    void author_edit_buttonclick(ActionEvent event) {
        author_create_button.setDisable(true);
        author_save_button.setDisable(false);
        author_cancel_button.setDisable(false);
        author_edit_button.setDisable(true);
        setAuthorForm(true);
    }

    @FXML
    void author_create_buttonclick(ActionEvent event) {
        author_create_button.setDisable(true);
        author_save_button.setDisable(false);
        author_cancel_button.setDisable(false);
        author_edit_button.setDisable(true);
        selectedAuthor = null;
        clearAuthorFields();
        setAuthorForm(true);
    }

    @FXML
    void author_search_click(ActionEvent event) {
        List<Author> authors = authorController.searchAuthors(author_search_textbox.getText());
        author_authorList_list.getItems().removeAll(author_authorList_list.getItems());
        author_authorList_list.getItems().addAll(authors);
    }

    private void setAuthorForm(boolean enable) {
        author_search_textbox.setEditable(!enable);
        author_firstname_textbox.setEditable(enable);
        author_lastname_textbox.setEditable(enable);
        author_phone_textbox.setEditable(enable);
        author_credential_textbox.setEditable(enable);
        author_biography_textbox.setEditable(enable);
        author_state_textbox.setEditable(enable);
        author_city_textbox.setEditable(enable);
        author_street_textbox.setEditable(enable);
        author_zip_textbox.setEditable(enable);
        author_authorList_list.setDisable(enable);
    }

    private void populateAuthorTable() {
        List<Author> authors = authorController.getAuthors();
        author_authorList_list.getItems().removeAll(author_authorList_list.getItems());
        author_authorList_list.getItems().addAll(authors);
    }

    private void author_list_changed(Author selecttion) {
        selectedAuthor = selecttion;
        author_edit_button.setDisable(false);

        clearAuthorFields();
    }

    private void clearAuthorFields() {
        author_error_label.setText("");
        loadObjectToControls(selectedAuthor);
    }

    private void loadObjectToControls(Author author) {
        if (author != null) {
            author_firstname_textbox.setText(author.getFirstName());
            author_lastname_textbox.setText(author.getLastName());
            author_phone_textbox.setText(author.getPhoneNumber());
            author_credential_textbox.setText(author.getCredentials());
            author_biography_textbox.setText(author.getBiography());
            Address address = author.getAddress();
            if (address != null) {
                author_state_textbox.setText(address.getState());
                author_city_textbox.setText(address.getCity());
                author_street_textbox.setText(address.getStreet());
                author_zip_textbox.setText(address.getZip());
            }
        } else {
            author_firstname_textbox.setText("");
            author_lastname_textbox.setText("");
            author_phone_textbox.setText("");
            author_credential_textbox.setText("");
            author_biography_textbox.setText("");
            //Address address = author.getAddress();
            author_state_textbox.setText("");
            author_city_textbox.setText("");
            author_street_textbox.setText("");
            author_zip_textbox.setText("");
        }
    }
    /*
     * END AUTHOR REGION
     */
 /*
     * 
     */

 /* 
     * CIRCULATION REGION
     */
    @FXML
    private TableView<CheckoutRecordEntry> cir_checkout_record_table;
    @FXML
    private TableView<Book> cir_booksearch_list;

    @FXML
    private TextField cir_search_member_textbox;

    @FXML
    private TextField cir_booksearch_textbox;

    @FXML
    private Label cir_lastname_label;

    @FXML
    private Label cir_error_label;

    @FXML
    private Label cir_firstname_label;

    @FXML
    private Label cir_memId_label;

    @FXML
    private Label cir_phone_label;

    @FXML
    private Button cir_print_button;

    @FXML
    private TableColumn<CheckoutRecordEntry, String> cir_checkouttable_isbn;

    @FXML
    private TableColumn<CheckoutRecordEntry, String> cir_checkouttable_title;

    @FXML
    private TableColumn<CheckoutRecordEntry, String> cir_checkouttable_duedate;

    @FXML
    private TableColumn<CheckoutRecordEntry, String> cir_checkouttable_returndate;

    @FXML
    private TableColumn<CheckoutRecordEntry, String> cir_checkouttable_action;
    @FXML
    private TableColumn<Book, String> cir_bookttable_isbn;

    @FXML
    private TableColumn<Book, String> cir_bookttable_title;

    @FXML
    private TableColumn<Book, Integer> cir_bookttable_loanperiod;

    @FXML
    private TableColumn<Book, Integer> cir_bookttable_copies;

    @FXML
    private TableColumn<Book, String> cir_bookttable_action;

    private LibraryMember selectedCirculationMember;

    private void setInitialCirculationState() {

        cir_checkouttable_isbn.setCellValueFactory(new Callback<CellDataFeatures<CheckoutRecordEntry, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<CheckoutRecordEntry, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getInventory().getProduct().getProductId());
            }
        });
        cir_checkouttable_title.setCellValueFactory(new Callback<CellDataFeatures<CheckoutRecordEntry, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<CheckoutRecordEntry, String> p) {
                return new ReadOnlyObjectWrapper(((Book) (p.getValue().getInventory().getProduct())));
            }
        });
        cir_checkouttable_duedate.setCellValueFactory(new Callback<CellDataFeatures<CheckoutRecordEntry, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<CheckoutRecordEntry, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getDueDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            }
        });
        cir_checkouttable_returndate.setCellValueFactory(new Callback<CellDataFeatures<CheckoutRecordEntry, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<CheckoutRecordEntry, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getReturnDate() != null ? p.getValue().getReturnDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) : "");
            }
        });
        cir_checkouttable_action.setCellFactory(new Callback<TableColumn<CheckoutRecordEntry, String>, TableCell<CheckoutRecordEntry, String>>() {
            @Override
            public TableCell<CheckoutRecordEntry, String> call(TableColumn<CheckoutRecordEntry, String> param) {
                return new CheckinButtonCell(cir_checkout_record_table);
            }
        });

        cir_bookttable_isbn.setCellValueFactory(new PropertyValueFactory<Book, String>("isbn"));
        cir_bookttable_title.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        cir_bookttable_loanperiod.setCellValueFactory(new PropertyValueFactory<Book, Integer>("borrowDuration"));
        cir_bookttable_copies.setCellValueFactory(new Callback<CellDataFeatures<Book, Integer>, ObservableValue<Integer>>() {
            public ObservableValue<Integer> call(CellDataFeatures<Book, Integer> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getNumberOfAvailableInventory());
            }
        });
        cir_bookttable_action.setCellFactory(new Callback<TableColumn<Book, String>, TableCell<Book, String>>() {
            @Override
            public TableCell<Book, String> call(TableColumn<Book, String> param) {
                return new CheckoutButtonCell(cir_booksearch_list);
            }
        });

        cir_print_button.setDisable(true);
        cir_booksearch_button_click(null);

    }

    @FXML
    void cir_search_buttonclick(ActionEvent event) {
        selectedCirculationMember = circulationController.searchLibraryMember(cir_search_member_textbox.getText());
        if (selectedCirculationMember == null) {
            cir_error_label.setText("Member not found");
            cir_memId_label.setText("");
            cir_firstname_label.setText("");
            cir_lastname_label.setText("");
            cir_phone_label.setText("");
        } else {
            cir_error_label.setText("");
            cir_memId_label.setText(selectedCirculationMember.getMemberId());
            cir_firstname_label.setText(selectedCirculationMember.getFirstName());
            cir_lastname_label.setText(selectedCirculationMember.getLastName());
            cir_phone_label.setText(selectedCirculationMember.getPhoneNumber());
        }
        cir_print_button.setDisable(selectedCirculationMember == null);
        refreshCirculationCheckoutTableView();
    }

    @FXML
    void cir_print_buttonclick(ActionEvent event) {
        if (selectedCirculationMember != null) {
            List<CheckoutRecordEntry> entries = selectedCirculationMember.getCheckoutRecord().getCheckoutRecordEntries();
            if (entries.isEmpty()) {
                return;
            }
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Checkout record for " + selectedCirculationMember.getMemberId() + " - " + selectedCirculationMember.getFirstName() + " " + selectedCirculationMember.getLastName());
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
            String format = "%-3s%-18s%-80s%-12s%-12s\n";
            System.out.printf(format, "NO", "ISBN", "TITLE", "DUE DATE", "RETURN DATE");
            int i = 0;
            for (CheckoutRecordEntry checkoutRecordEntry : entries) {
                System.out.printf(format, ++i + "", checkoutRecordEntry.getInventory().getProduct().getProductId(), checkoutRecordEntry.getInventory().getProduct().getTitle(), checkoutRecordEntry.getDueDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")), checkoutRecordEntry.getReturnDate() != null ? checkoutRecordEntry.getReturnDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) : "");
            }
        }
    }

    @FXML
    void cir_booksearch_button_click(ActionEvent event) {
        Book isbnBook = publicationController.searchBookWithIsbn(cir_booksearch_textbox.getText());
        List<Book> searchResults = publicationController.searchBooksWithTitle(cir_booksearch_textbox.getText());
        if (isbnBook != null) {
            searchResults.add(0, isbnBook);
        }
        cir_booksearch_list.getItems().setAll(searchResults);
    }

    private void refreshCirculationCheckoutTableView() {
        cir_print_button.setDisable(selectedCirculationMember == null);
        if (selectedCirculationMember != null) {
            List<CheckoutRecordEntry> entries = selectedCirculationMember.getCheckoutRecord().getCheckoutRecordEntries();
            cir_checkout_record_table.getItems().setAll(entries);
            cir_print_button.setDisable(entries.size() == 0);
        } else {
            cir_checkout_record_table.getItems().removeAll(cir_checkout_record_table.getItems());
        }
    }

    @FXML
    void circulation_tab_selection_changed(Event event) {
        cir_booksearch_button_click(null);
    }

    private class CheckoutButtonCell extends TableCell<Book, String> {
        // a button for adding a new person.

        final Button checkout_button = new Button("Checkout");
        // pads and centers the add button in the cell.
        final StackPane paddedButton = new StackPane();

        CheckoutButtonCell(final TableView table) {
            paddedButton.setPadding(new Insets(3));
            paddedButton.getChildren().add(checkout_button);
            checkout_button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (selectedCirculationMember == null) {
                        JOptionPane.showMessageDialog(null, "Please search for a library member before checking out", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    Book selectedBook = (Book) CheckoutButtonCell.this.getTableRow().getItem();
                    Inventory availableCopy = selectedBook.getAvailableInventory().get(0); //sudeep
                    if (availableCopy == null) {
                        JOptionPane.showMessageDialog(null, "There are no available copies for this book", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    circulationController.checkout(selectedCirculationMember.getMemberId(), availableCopy);
                    refreshCirculationCheckoutTableView();
                    cir_booksearch_button_click(null);
                }
            });
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setGraphic(paddedButton);
            } else {
                setGraphic(null);
            }
        }
    }

    private class CheckinButtonCell extends TableCell<CheckoutRecordEntry, String> {
        // a button for adding a new person.

        final Button checkin_button = new Button("Checkin");
        // pads and centers the add button in the cell.
        final StackPane paddedButton = new StackPane();

        CheckinButtonCell(final TableView table) {
            paddedButton.setPadding(new Insets(3));
            paddedButton.getChildren().add(checkin_button);
            checkin_button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (selectedCirculationMember == null) {
                        JOptionPane.showMessageDialog(null, "Please search for a library member before checking out", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    CheckoutRecordEntry selectedEntry = (CheckoutRecordEntry) CheckinButtonCell.this.getTableRow().getItem();
                    circulationController.checkin(selectedCirculationMember.getMemberId(), selectedEntry);
                    refreshCirculationCheckoutTableView();
                    cir_booksearch_button_click(null);
                }
            });
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                TableRow row = CheckinButtonCell.this.getTableRow();
                CheckoutRecordEntry entry = ((CheckoutRecordEntry) row.getItem());
                if (entry != null && entry.getReturnDate() == null) {
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    setGraphic(paddedButton);
                } else {
                    setGraphic(null);
                }
            } else {
                setGraphic(null);
            }
        }
    }
}
/*
 * END CIRCULATION REGION
 */
