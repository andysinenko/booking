package homes.sinenko.booking.dto.unit;

import homes.sinenko.booking.entity.Unit;
import homes.sinenko.booking.entity.UnitType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UnitMapperTest {

    @Test
    void toDto_shouldMapEntityToDto() {
        Unit unit = Unit.builder()
                .id(10L)
                .rooms(2)
                .type(UnitType.APARTMENT)
                .floor(3)
                .cost(BigDecimal.valueOf(1500.00))
                .description("Nice view")
                .available(true)
                .build();

        UnitDto dto = UnitMapper.toDto(unit);

        assertEquals(unit.getId(), dto.id());
        assertEquals(unit.getRooms(), dto.rooms());
        assertEquals(unit.getType(), dto.type());
        assertEquals(unit.getFloor(), dto.floor());
        assertEquals(unit.getCost(), dto.cost());
        assertEquals(unit.getDescription(), dto.description());
        assertEquals(unit.getAvailable(), dto.available());
    }

    @Test
    void fromDto_shouldMapDtoToEntity() {
        UnitDto dto = UnitDto.builder()
                .id(5L)
                .rooms(1)
                .type(UnitType.APARTMENT)
                .floor(1)
                .cost(BigDecimal.valueOf(800))
                .description("Cozy")
                .available(false)
                .build();

        Unit unit = UnitMapper.fromDto(dto);

        assertNull(unit.getId());
        assertEquals(dto.rooms(), unit.getRooms());
        assertEquals(dto.type(), unit.getType());
        assertEquals(dto.floor(), unit.getFloor());
        assertEquals(dto.cost(), unit.getCost());
        assertEquals(dto.description(), unit.getDescription());
        assertEquals(dto.available(), unit.getAvailable());
    }

    @Test
    void fromCreateUnitRequest_shouldMapCreateRequestToEntity() {
        CreateUnitRequest req = new CreateUnitRequest(4, UnitType.HOME, 2, BigDecimal.valueOf(2000.99), "Spacious", 1L, true);

        Unit unit = UnitMapper.fromCreateUnitRequst(req);

        assertNull(unit.getId());
        assertEquals(req.rooms(), unit.getRooms());
        assertEquals(req.type(), unit.getType());
        assertEquals(req.floor(), unit.getFloor());
        assertEquals(req.cost(), unit.getCost());
        assertEquals(req.description(), unit.getDescription());
        assertEquals(req.available(), unit.getAvailable());
    }
}

