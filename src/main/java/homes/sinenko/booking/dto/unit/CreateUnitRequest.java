package homes.sinenko.booking.dto.unit;

import homes.sinenko.booking.entity.UnitType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;


public record CreateUnitRequest(
        @NotNull(message = "Rooms quantity. Can not be a null or empty")
        @Schema( description = "Rooms quantity", example = "3", requiredMode = Schema.RequiredMode.REQUIRED )
        int rooms,

        @NotNull(message = "UnitType - tpe of property")
        @Schema( description = "UnitType must be HOME, FLAT, or APARTMENT", example = "APARTMENT", requiredMode = Schema.RequiredMode.REQUIRED,
        allowableValues = { "HOME", "FLAT", "APARTMENT" })
        UnitType type,

        @NotNull(message = "Floor number of unit. Can not be a null or empty")
        @Schema( description = "Floor number", example = "3", requiredMode = Schema.RequiredMode.REQUIRED )
        int floor,


        @Schema(description = "Cost per unit", example = "100.00", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Cost must not be null")
        @DecimalMin(value = "0.0", inclusive = false, message = "Cost must be greater than 0")
        BigDecimal cost,

        @Schema(description = "Description of the unit", example = "Cozy apartment with sea view")
        @Size(max = 255, message = "Description must be at most 255 characters")
        String description,

        @Schema(description = "ID of the user who created the unit", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "createdById must not be null")
        Long createdById,

        @Schema(description = "Availability of the unit", example = "true")
        @NotNull(message = "Availability must not be null")
        Boolean available
) {}
