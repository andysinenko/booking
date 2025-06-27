package homes.sinenko.booking.dto.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "Payment",
        description = "Schema to hold payment information"
)
public record PaymentDto(
        @Schema(description = "Unique identifier of the payment", example = "1")
        Long id,

        @Schema(description = "Unique identifier of the booking associated with the payment", example = "1")
        Long bookingId,

        @Schema(description = "Value of payment", example = "100.00", required = true)
        BigDecimal amount,

        @Schema(description = "Date and time when the payment was made", example = "2023-10-01T12:00:00", required = true)
        LocalDateTime paidAt
) {}
