package homes.sinenko.booking.controller;

import homes.sinenko.booking.dto.ErrorResponseDto;
import homes.sinenko.booking.dto.booking.BookingDto;
import homes.sinenko.booking.dto.booking.BookingMapper;
import homes.sinenko.booking.dto.booking.CreateBookingRequest;
import homes.sinenko.booking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/bookings", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@RequiredArgsConstructor
@Tag(name = "Booking controller", description = "Endpoints for managing bookings")
public class BookingController {

    private final BookingService bookingService;

    @Operation(summary = "Create a new booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking created successfully"
                    , content = @Content(schema = @Schema(implementation = BookingDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping
    public ResponseEntity<BookingDto> createBooking(@Valid  @RequestBody CreateBookingRequest createBookingRequest) {
        var booking = bookingService.bookUnit(createBookingRequest);
        return ResponseEntity.ok(BookingMapper.toDto(booking));
    }

    @Operation(summary = "Cancel of created booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Booking created successfully"
                    , content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Creating payment for booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking created successfully"
                    , content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/{id}/pay")
    public ResponseEntity<Void> payForBooking(@PathVariable Long id) {
        bookingService.payForBooking(id);
        return ResponseEntity.ok().build();
    }
}
