package homes.sinenko.booking.dto.setting;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Schema(description = "Create Setting Request")
@Builder
public record CreateSettingRequest(
        @Schema(description = "Key of the setting", example = "maxBookingDays")
        @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Key must contain only alphanumeric characters and underscores")
        String key,

        @Schema(description = "Value of the setting", example = "30")
        @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Value must contain only alphanumeric characters")
        String value
) {}
