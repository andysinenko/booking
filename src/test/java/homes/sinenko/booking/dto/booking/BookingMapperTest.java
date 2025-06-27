package homes.sinenko.booking.dto.booking;

import homes.sinenko.booking.entity.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BookingMapperTest {

    @Test
    void toDto_shouldMapEntityToDto() {
        var user = new User(22L, "testUser");
        var unit = Unit.builder().id(11L).type(UnitType.FLAT).createdBy(user).createdAt(LocalDateTime.now()).rooms(5).cost(BigDecimal.valueOf(1000.00)).build();

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setUnit(unit);
        booking.setUser(user);
        booking.setStartDate(LocalDate.now());
        booking.setEndDate(LocalDate.now().plusDays(2));
        booking.setStatus(BookingStatus.CONFIRMED);

        BookingDto dto = BookingMapper.toDto(booking);

        assertNotNull(dto);
        assertEquals(1L, dto.id());
        assertEquals(11L, dto.unitId());
        assertEquals(22L, dto.userId());
        assertEquals(LocalDate.now(), dto.startDate());
        assertEquals(LocalDate.now().plusDays(2), dto.endDate());
        assertEquals(BookingStatus.CONFIRMED.name(), dto.status());
    }
}