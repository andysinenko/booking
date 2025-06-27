package homes.sinenko.booking.repository;

import homes.sinenko.booking.entity.Booking;
import homes.sinenko.booking.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

import java.util.Collection;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existsByUnitIdAndUserIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndStatusIn(
            Long unitId,
            Long userId,
            LocalDate endDate,
            LocalDate startDate,
            Collection<BookingStatus> statuses
    );

    @Query("SELECT DISTINCT b.unit.id FROM Booking b WHERE b.status = 'CONFIRMED'")
    List<Long> findConfirmedUnitIds();
}
