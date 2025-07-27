package homes.sinenko.booking.dto.booking;

import homes.sinenko.booking.entity.Booking;

public class BookingMapper {
    public static BookingDto toDto(Booking booking) {
        return new BookingDto(
                booking.getId(),
                booking.getUnit().getId(),
                booking.getUser().getId(),
                booking.getStartDate(),
                booking.getEndDate(),
                booking.getStatus().name()
        );
    }
}
