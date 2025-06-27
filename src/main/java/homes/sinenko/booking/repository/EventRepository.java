package homes.sinenko.booking.repository;

import homes.sinenko.booking.entity.Event;
import homes.sinenko.booking.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByUnit(Unit unit);

    @Query("SELECT e FROM Event e JOIN FETCH Unit u ON e.unit.id = u.id JOIN FETCH User c ON e.createdBy.id = c.id")
    List<Event> findAllEvents();
}
