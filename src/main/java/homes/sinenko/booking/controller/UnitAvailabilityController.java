package homes.sinenko.booking.controller;

import homes.sinenko.booking.dto.ErrorResponseDto;
import homes.sinenko.booking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/units",  produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Tag(
        name = "Availability check controller"
)
public class UnitAvailabilityController {

    private final BookingService bookingService;

    @Operation(summary = "Get available units count")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved available units count"
                    , content = @Content(schema = @Schema(implementation = Integer.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/available-count")
    public ResponseEntity<Integer> getAvailableUnitsCount() {
        return ResponseEntity.ok(bookingService.getAvailableUnitsCountFromCache());
    }
}
