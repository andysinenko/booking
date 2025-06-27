package homes.sinenko.booking.dto.event;

import homes.sinenko.booking.entity.Event;
import homes.sinenko.booking.entity.EventType;
import homes.sinenko.booking.entity.Unit;
import homes.sinenko.booking.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventMapperTest {

    @Test
    void testToDto() {
        Unit unit = Unit.builder().id(1L).build();
        var localDateTime = LocalDateTime.now();

        Event event = Event.builder()
                .id(1L)
                .unit(unit)
                .eventType(EventType.BOOKED)
                .eventTime(localDateTime)
                .createdBy(new User(1L, "testUser"))
                .build();

        EventDto dto = EventMapper.toDto(event);

        assertEquals(1L, dto.id());
        assertEquals(1L, dto.unitId());
        assertEquals(EventType.BOOKED, dto.eventType());
        assertEquals(localDateTime, dto.eventTime());
    }

    @Test
    void testToDtoList() {
        Unit unit1 = Unit.builder().id(1L).build();
        Unit unit2 = Unit.builder().id(2L).build();
        User user1 = new User(1L, "user1");
        User user2 = new User(2L, "user2");

        var localDateTime1 = LocalDateTime.now();
        var localDateTime2 = LocalDateTime.now();

        Event event1 = Event.builder()
                .id(1L)
                .unit(unit1)
                .eventType(EventType.BOOKED)
                .eventTime(localDateTime1)
                .createdBy(user1)
                .build();
        Event event2 = Event.builder()
                .id(2L)
                .unit(unit2)
                .eventType(EventType.CANCELLED)
                .eventTime(localDateTime2)
                .createdBy(user2)
                .build();

        List<Event> events = Arrays.asList(event1, event2);
        List<EventDto> eventDtoList = EventMapper.toDtoList(events);

        assertEquals(2, eventDtoList.size());
        assertEquals(1L, eventDtoList.get(0).id());
        assertEquals(1L, eventDtoList.get(0).unitId());
        assertEquals(EventType.BOOKED, eventDtoList.get(0).eventType());
        assertEquals(localDateTime1, eventDtoList.get(0).eventTime());

        assertEquals(2L, eventDtoList.get(1).id());
        assertEquals(2L, eventDtoList.get(1).unitId());
        assertEquals(EventType.CANCELLED, eventDtoList.get(1).eventType());
        assertEquals(localDateTime2, eventDtoList.get(1).eventTime());
    }
}