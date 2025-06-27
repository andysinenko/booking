package homes.sinenko.booking.service;

import homes.sinenko.booking.entity.UnitType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UnitFilter {
    private UnitType type;
    private int rooms;
    private int floor;
    private BigDecimal minCost;
    private BigDecimal maxCost;
    private LocalDate from;
    private LocalDate to;

    private int page = 0;
    private int size = 10;
    private String sortBy = "cost";
    private String direction = "asc";
}
