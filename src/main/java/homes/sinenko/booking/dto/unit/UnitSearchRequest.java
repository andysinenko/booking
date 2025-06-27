package homes.sinenko.booking.dto.unit;

import homes.sinenko.booking.entity.UnitType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema( description = "Schema to search for units",
        name = "UnitSearchRequest",
        title = "Unit Search Request")
@Data
public class UnitSearchRequest {
    @Schema(
            description = "Type of unit to search for",
            example = "APARTMENT",
            allowableValues = { "APARTMENT", "HOUSE", "ROOM" }
    )
    private UnitType type;

    @Schema(
            description = "Number of rooms in the unit",
            example = "3"
    )
    private Integer rooms;

    @Schema(
            description = "Floor number of the unit",
            example = "2"
    )
    private Integer floor;

    @Schema(
            description = "Minimum cost of the unit",
            example = "50.00"
    )
    private BigDecimal minCost;

    @Schema(
            description = "Maximum cost of the unit",
            example = "500.00"
    )
    private BigDecimal maxCost;

    @Schema(
            description = "Start date for availability search",
            example = "2023-10-01"
    )
    private LocalDate from;

    @Schema(
            description = "End date for availability search",
            example = "2023-10-31"
    )
    private LocalDate to;

    @Schema(
            description = "Availability status of the unit",
            example = "true"
    )
    private Boolean available;

    @Schema(
            description = "Page number for pagination",
            example = "0"
    )
    private int page = 0;

    @Schema(
            description = "Number of units per page for pagination",
            example = "10"
    )
    private int size = 10;

    @Schema(
            description = "Field to sort the results by",
            example = "cost",
            allowableValues = { "cost", "rooms", "floor", "type" }
    )
    private String sortBy = "cost";

    @Schema(
            description = "Direction of sorting, either 'asc' or 'desc'",
            example = "asc",
            allowableValues = { "asc", "desc" }
    )
    private String direction = "asc";
}
