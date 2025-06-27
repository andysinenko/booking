package homes.sinenko.booking.service;


import homes.sinenko.booking.dto.event.CreateEventRequest;
import homes.sinenko.booking.entity.Event;
import homes.sinenko.booking.entity.Unit;
import homes.sinenko.booking.entity.User;
import homes.sinenko.booking.exception.UnitNotFoundException;
import homes.sinenko.booking.repository.EventRepository;
import homes.sinenko.booking.repository.UnitRepository;
import homes.sinenko.booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final UnitRepository unitRepository;
    private final UserRepository userRepository;

    @CacheEvict(value = { "allEvents", "eventsByUnit" }, allEntries = true)
    public Event createEvent(CreateEventRequest request) {
        Unit unit = unitRepository.findById(request.unitId())
                .orElseThrow(() -> new IllegalArgumentException("Unit not found"));
        User user = userRepository.findById(request.createdById())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Event event = Event.builder()
                .unit(unit)
                .createdBy(user)
                .eventType(request.eventType())
                .eventTime(LocalDateTime.now())
                .build();

        return eventRepository.save(event);
    }

    @Cacheable("allEvents")
    public List<Event> getAllEvents() {
        return eventRepository.findAllEvents();
    }

    @Cacheable(value = "eventsByUnit", key = "#unitId")
    public List<Event> getEventsByUnit(Long unitId) {
        Unit unit = unitRepository.findById(unitId)
                .orElseThrow(() -> new UnitNotFoundException("Unit not found"));
        return eventRepository.findByUnit(unit);
    }
}
