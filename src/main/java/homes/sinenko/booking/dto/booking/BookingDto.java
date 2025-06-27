package homes.sinenko.booking.dto.booking;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(
        name = "BookingDto",
        description = "Response for booking creation response"
)
public record BookingDto(
        @Schema(description = "Booking ID", example = "1")
        Long id,

        @Schema(description = "ID of the unit being booked", example = "101")
        Long unitId,

        @Schema(description = "ID of the user who made the booking", example = "202")
        Long userId,

        @Schema(description = "Start date of the booking", example = "2025-07-01")
        LocalDate startDate,

        @Schema(description = "End date of the booking", example = "2025-07-10")
        LocalDate endDate,

        @Schema(description = "Status of the booking", example = "CONFIRMED")
        String status
) {}
