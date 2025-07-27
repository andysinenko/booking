package homes.sinenko.booking.controller;

import homes.sinenko.booking.dto.ErrorResponseDto;
import homes.sinenko.booking.dto.event.CreateEventRequest;
import homes.sinenko.booking.dto.event.EventDto;
import homes.sinenko.booking.dto.event.EventMapper;
import homes.sinenko.booking.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/events", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Tag(name = "Event controller", description = "Endpoints for managing events")
public class EventController {

    private final EventService eventService;

    @PostMapping(consumes = "application/json")
    @Operation(summary = "Create a new event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event created successfully"
                    , content = @Content(schema = @Schema(implementation = EventDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<EventDto> createEvent(@RequestBody CreateEventRequest request) {
        return ResponseEntity.ok(EventMapper.toDto(eventService.createEvent(request)));
    }

    @GetMapping
    @Operation(summary = "Get all events")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event created successfully"
                    , content = @Content(schema = @Schema(implementation = EventDto.class, type = "array"))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<List<EventDto>> getAllEvents() {
        return ResponseEntity.ok(EventMapper.toDtoList(eventService.getAllEvents()));
    }

    @GetMapping("/unit/{unitId}")
    @Operation(summary = "Get all events for a unit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event created successfully"
                    , content = @Content(schema = @Schema(implementation = EventDto.class, type = "array"))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    public ResponseEntity<List<EventDto>> getEventsByUnit(@PathVariable Long unitId) {
        var events = eventService.getEventsByUnit(unitId);
        return ResponseEntity.ok(EventMapper.toDtoList(events));
    }
}
