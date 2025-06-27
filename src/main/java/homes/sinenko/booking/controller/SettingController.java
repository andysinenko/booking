package homes.sinenko.booking.controller;

import homes.sinenko.booking.dto.ErrorResponseDto;
import homes.sinenko.booking.dto.booking.BookingDto;
import homes.sinenko.booking.dto.setting.CreateSettingRequest;
import homes.sinenko.booking.dto.setting.SettingDto;
import homes.sinenko.booking.service.SettingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/settings",  produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
@Tag(
        name = "Service application settings",
        description = "Controller for managing application settings"
)
public class SettingController {

    private final SettingService settingsService;

    @Operation(summary = "Create a new setting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Setting created successfully"
                    , content = @Content(schema = @Schema(implementation = SettingDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping
    public ResponseEntity<SettingDto> createSetting(@RequestBody @Validated CreateSettingRequest request) {
        return ResponseEntity.ok(settingsService.create(request));
    }

    @Operation(summary = "Get all settings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Settings returned successfully"
                    , content = @Content(schema = @Schema(implementation = SettingDto.class, type = "array"))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping
    public ResponseEntity<List<SettingDto>> getAllSettings() {
        return ResponseEntity.ok(settingsService.getAllSettings());
    }

    @Operation(summary = "Get setting by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Setting returned successfully"
                    , content = @Content(schema = @Schema(implementation = SettingDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })

    @GetMapping("/{id}")
    public ResponseEntity<SettingDto> getSettingById(@PathVariable Long id) {
        return ResponseEntity.ok(settingsService.getById(id));
    }

    @Operation( summary = "Updates an existing setting" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Setting updated successfully"
                    , content = @Content(schema = @Schema(implementation = SettingDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<SettingDto> updateSetting(
            @PathVariable Long id,
            @RequestBody CreateSettingRequest request
    ) {
        return ResponseEntity.ok(settingsService.updateSetting(id, request));
    }

    @Operation(summary = "Deletes a setting by its Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Setting deleted successfully"
                    , content = @Content(schema = @Schema(implementation = BookingDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSetting(@PathVariable Long id) {
        settingsService.deleteSetting(id);
        return ResponseEntity.ok().build();
    }
}
