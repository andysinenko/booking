package homes.sinenko.booking.dto.event;

import homes.sinenko.booking.entity.EventType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Schema(
        name = "EventDto model",
        description = "Response object for event"
)
@Builder
public record EventDto(
        @Schema( description = "Event Id", example = "3" )
        Long id,

        @Schema( description = "Unit Id", example = "101" )
        Long unitId,

        @Schema( description = "User Id", example = "101" )
        Long createdById,

        @Schema( description = "Event type", example = "BOOKED", allowableValues = { "CREATED", "UPDATED", "DELETED", "BOOKED", "CANCELLED", "PAID" })
        EventType eventType,

        @Schema( description = "Date time of event", example = "2025-07-01T10:15:30" )
        LocalDateTime eventTime
) {}
