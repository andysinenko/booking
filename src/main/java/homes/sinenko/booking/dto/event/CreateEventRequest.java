package homes.sinenko.booking.dto.event;

import homes.sinenko.booking.entity.EventType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;


@Schema(
        name = "CreateEventRequest model",
        description = "Schema to create a new event"
)
@Builder
public record CreateEventRequest(
        @NotNull(message = "Event identifier. Can not be a null or empty")
        @Schema( description = "Unit identifier", example = "3", required = true )
        Long unitId,

        @NotNull(message = "User identifier. Can not be a null or empty")
        @Schema( description = "User identifier", example = "3", required = true )
        Long createdById,

        @Schema(description = "Type of event.", example = "BOOKED", required = true,
                allowableValues = { "CREATED", "UPDATED", "DELETED", "BOOKED", "CANCELLED", "PAID" })
        @NotNull
        EventType eventType
) {}
