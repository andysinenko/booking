package homes.sinenko.booking.repository;

import homes.sinenko.booking.entity.Unit;
import homes.sinenko.booking.entity.UnitType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long>, JpaSpecificationExecutor<Unit> {
    List<Unit> findByTypeAndRoomsAndFloorAndCostBetween(UnitType type, int rooms, int floor, BigDecimal minCost, BigDecimal maxCost);

    @Query("""
    SELECT u FROM Unit u
    WHERE (:type IS NULL OR u.type = :type)
      AND (:rooms IS NULL OR u.rooms = :rooms)
      AND (:floor IS NULL OR u.floor = :floor)
      AND (u.cost BETWEEN COALESCE(:minCost, 0) AND COALESCE(:maxCost, 1000000))
      AND u.id NOT IN (
          SELECT b.unit.id FROM Booking b
          WHERE (:from IS NULL OR :to IS NULL OR (b.startDate < :to AND b.endDate > :from))
          AND b.status <> 'CANCELLED'
      )
    """)
    List<Unit> findAvailableUnits(
            @Param("type") UnitType type,
            @Param("rooms") int rooms,
            @Param("floor") int floor,
            @Param("minCost") BigDecimal minCost,
            @Param("maxCost") BigDecimal maxCost,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to,
            Pageable pageable
    );

    boolean existsByRoomsAndTypeAndFloorAndCostAndDescriptionAndCreatedBy_NameAndAvailable(
            int rooms,
            UnitType type,
            int floor,
            BigDecimal cost,
            String description,
            String createdByName,
            Boolean available
    );
}
