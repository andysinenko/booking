package homes.sinenko.booking.dto.booking;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Schema(
        name = "BookingDto",
        description = "Response for booking creation request"
)
@Builder
public record CreateBookingRequest(
        @NotNull(message = "Unit identifier. Can not be a null or empty")
        @Schema( description = "Unit identifier", example = "3", requiredMode = Schema.RequiredMode.REQUIRED )
        Long unitId,

        @NotNull(message = "User identifier. Can not be a null or empty")
        @Schema( description = "ID of the user making the booking", example = "3", requiredMode = Schema.RequiredMode.REQUIRED )
        Long userId,

        @NotNull(message = "Start date of rent. Can not be a null or empty")
        @Schema( description = "Start date of rent", example = "2026-10-14", requiredMode = Schema.RequiredMode.REQUIRED )
        LocalDate startDate,

        @NotNull(message = "End date of rent. Can not be a null or empty")
        @Schema( description = "End date of rent", example = "2026-10-24", requiredMode = Schema.RequiredMode.REQUIRED )
        LocalDate endDate
) {}
