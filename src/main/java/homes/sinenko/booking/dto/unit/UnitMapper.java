package homes.sinenko.booking.dto.unit;

import homes.sinenko.booking.entity.Unit;

public class UnitMapper {
    public static UnitDto toDto(Unit unit) {
        return UnitDto.builder()
                .id(unit.getId())
                .rooms(unit.getRooms())
                .type(unit.getType())
                .floor(unit.getFloor())
                .cost(unit.getCost())
                .description(unit.getDescription())
                .available(unit.getAvailable())
                .build();
    }

    public static Unit fromDto(UnitDto dto) {
        return Unit.builder()
                .rooms(dto.rooms())
                .type(dto.type())
                .floor(dto.floor())
                .cost(dto.cost())
                .description(dto.description())
                .available(dto.available())
                .build();
    }

    public static Unit fromCreateUnitRequst(CreateUnitRequest dto) {
        return Unit.builder()
                .rooms(dto.rooms())
                .type(dto.type())
                .floor(dto.floor())
                .cost(dto.cost())
                .description(dto.description())
                .available(dto.available())
                .build();
    }
}