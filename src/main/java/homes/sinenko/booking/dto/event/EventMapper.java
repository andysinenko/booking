package homes.sinenko.booking.dto.event;

import homes.sinenko.booking.entity.Event;

import java.util.List;
import java.util.stream.Collectors;

public class EventMapper {
    public static EventDto toDto(Event event) {
        return new EventDto(
                event.getId(),
                event.getUnit().getId(),
                event.getCreatedBy().getId(),
                event.getEventType(),
                event.getEventTime()
        );
    }

    public static List<EventDto> toDtoList(List<Event> events) {
        return events.stream()
                .map(EventMapper::toDto)
                .collect(Collectors.toList());
    }
}
