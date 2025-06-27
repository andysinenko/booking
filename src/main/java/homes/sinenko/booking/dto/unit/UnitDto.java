package homes.sinenko.booking.dto.unit;

import homes.sinenko.booking.entity.UnitType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.math.BigDecimal;


@Schema(
        name = "Unit",
        description = "Schema to hold Unit information"
)
@Builder
public record UnitDto(
        @Schema(description = "Unique identifier of the unit", example = "1")
        Long id,

        @Schema(description = "Unique identifier of the unit", example = "1")
        int rooms,

        @Schema(description = "Type of the unit", example = "APARTMENT", required = true,
                allowableValues = { "HOME", "FLAT", "APARTMENT" })
        UnitType type,

        @Schema(description = "Floor number of the unit", example = "3", required = true)
        int floor,

        @Schema(description = "Cost per unit", example = "100.00", required = true)
        BigDecimal cost,

        @Schema(description = "Description of the unit", example = "Cozy apartment with sea view")
        String description,

        @Schema(description = "Availability of UNIT", example = "true", required = true)
        Boolean available
) {}
