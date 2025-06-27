package homes.sinenko.booking.dto.setting;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "Setting DTO")
@Builder
public record SettingDto(
        @Schema(description = "Unique identifier of the setting")
        Long id,
        @Schema(description = "Key of the setting")
        String key,
        @Schema(description = "Value of the setting")
        String value
) {}
