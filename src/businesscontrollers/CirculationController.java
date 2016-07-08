package businesscontrollers;

import java.time.LocalDate;
import java.util.List;
import models.base.Address;
import models.business.CheckoutRecordEntry;
import models.business.LendableCopy;
import models.business.LibraryMember;
import models.business.User;

public interface CirculationController {

    /**
     * Searches for a library member by ID.
     *
     * @param memberId the library member ID.
     * @return the {@link LibraryMember} if found, {@code null} otherwise.
     */
    LibraryMember searchLibraryMember(String memberId);

    /**
     * Gets list of library members.
     *
     * @return list of library members.
     */
    public List<LibraryMember> getLibraryMembers();

    /**
     *
     * @param memberId
     * @param lendableCopy
     * @return
     */
    boolean checkout(String memberId, LendableCopy lendableCopy);

    /**
     *
     * @param memberId
     * @param checkoutRecordEntry
     * @return
     */
    boolean checkin(String memberId, CheckoutRecordEntry checkoutRecordEntry);

    /**
     *
     * @param memberId
     * @param firstName
     * @param lastName
     * @param address
     * @param phoneNumber
     * @return
     */
    boolean addLibraryMember(String memberId, String firstName, String lastName, Address address, String phoneNumber);

    /**
     *
     * @param memberId
     * @param firstName
     * @param lastName
     * @param address
     * @param phoneNumber
     * @return
     */
    boolean updateLibraryMember(String memberId, String firstName, String lastName, Address address, String phoneNumber);
}
