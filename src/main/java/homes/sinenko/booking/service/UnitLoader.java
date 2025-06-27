package homes.sinenko.booking.service;


import homes.sinenko.booking.entity.Unit;
import homes.sinenko.booking.entity.UnitType;
import homes.sinenko.booking.repository.UnitRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class UnitLoader {

    private final UnitRepository unitRepository;
    private final Random random = new Random();

    @PostConstruct
    public void loadRandomUnits() {
        long count = unitRepository.count();
        if (count >= 100) {
            return;
        }

        for (int i = 0; i < 90; i++) {
            Unit unit = new Unit();
            unit.setRooms(random.nextInt(5) + 1);
            unit.setFloor(random.nextInt(10));
            unit.setType(randomEnum(UnitType.class));
            BigDecimal baseCost = BigDecimal.valueOf(50 + random.nextDouble() * 450)
                    .setScale(2, RoundingMode.HALF_UP);
            unit.setCost(baseCost);
            unit.setDescription("Random unit " + (i + 1));
            unit.setCreatedAt(LocalDateTime.now().minusDays(random.nextInt(365)));
            unitRepository.save(unit);
        }
    }

    private <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
}
