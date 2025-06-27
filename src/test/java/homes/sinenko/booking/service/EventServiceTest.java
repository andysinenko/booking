package homes.sinenko.booking.service;

import homes.sinenko.booking.dto.event.CreateEventRequest;
import homes.sinenko.booking.entity.Event;
import homes.sinenko.booking.entity.EventType;
import homes.sinenko.booking.entity.Unit;
import homes.sinenko.booking.entity.User;
import homes.sinenko.booking.exception.UnitNotFoundException;
import homes.sinenko.booking.repository.EventRepository;
import homes.sinenko.booking.repository.UnitRepository;
import homes.sinenko.booking.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UnitRepository unitRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    void createEvent_success() {
        Long unitId = 1L;
        Long userId = 2L;

        Unit unit = new Unit();
        unit.setId(unitId);

        User user = new User();
        user.setId(userId);

        CreateEventRequest request = mock(CreateEventRequest.class);
        when(request.unitId()).thenReturn(unitId);
        when(request.createdById()).thenReturn(userId);
        when(request.eventType()).thenReturn(EventType.CANCELLED);

        when(unitRepository.findById(unitId)).thenReturn(Optional.of(unit));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Event savedEvent = Event.builder()
                .unit(unit)
                .createdBy(user)
                .eventType(EventType.CANCELLED)
                .eventTime(LocalDateTime.now())
                .build();

        when(eventRepository.save(any(Event.class))).thenReturn(savedEvent);

        Event result = eventService.createEvent(request);

        assertNotNull(result);
        assertEquals(unit, result.getUnit());
        assertEquals(user, result.getCreatedBy());
        assertEquals(EventType.CANCELLED, result.getEventType());

        verify(unitRepository).findById(unitId);
        verify(userRepository).findById(userId);
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void createEvent_unitNotFound_throws() {
        Long unitId = 1L;

        CreateEventRequest request = mock(CreateEventRequest.class);
        when(request.unitId()).thenReturn(unitId);

        when(unitRepository.findById(unitId)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> eventService.createEvent(request));

        assertEquals("Unit not found", ex.getMessage());

        verify(unitRepository).findById(unitId);
        verify(userRepository, never()).findById(any());
        verify(eventRepository, never()).save(any());
    }

    @Test
    void getAllEvents_returnsAll() {
        List<Event> events = List.of(new Event(), new Event());

        when(eventRepository.findAll()).thenReturn(events);

        List<Event> result = eventService.getAllEvents();

        assertEquals(2, result.size());
        verify(eventRepository).findAll();
    }

    @Test
    void getEventsByUnit_success() {
        Long unitId = 1L;
        Unit unit = new Unit();
        unit.setId(unitId);

        List<Event> events = List.of(new Event(), new Event());

        when(unitRepository.findById(unitId)).thenReturn(Optional.of(unit));
        when(eventRepository.findByUnit(unit)).thenReturn(events);

        List<Event> result = eventService.getEventsByUnit(unitId);

        assertEquals(events, result);
        verify(unitRepository).findById(unitId);
        verify(eventRepository).findByUnit(unit);
    }

    @Test
    void getEventsByUnit_unitNotFound_throws() {
        Long unitId = 1L;

        when(unitRepository.findById(unitId)).thenReturn(Optional.empty());

        assertThrows(UnitNotFoundException.class, () -> eventService.getEventsByUnit(unitId));

        verify(unitRepository).findById(unitId);
        verify(eventRepository, never()).findByUnit(any());
    }
}