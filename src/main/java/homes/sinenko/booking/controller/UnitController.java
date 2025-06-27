package homes.sinenko.booking.controller;

import homes.sinenko.booking.dto.ErrorResponseDto;
import homes.sinenko.booking.dto.unit.CreateUnitRequest;
import homes.sinenko.booking.dto.unit.UnitDto;
import homes.sinenko.booking.dto.unit.UnitMapper;
import homes.sinenko.booking.dto.unit.UnitSearchRequest;
import homes.sinenko.booking.entity.Unit;
import homes.sinenko.booking.service.UnitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/units",  produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@Tag(name = "Units controller", description = "Endpoints for managing units")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

    @Operation(summary = "Create a new Unit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unit created successfully", content = @Content(schema = @Schema(implementation = UnitDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<UnitDto> createUnit(@RequestBody CreateUnitRequest request) {
        Unit unit = unitService.createUnit(request);
        return ResponseEntity.ok(UnitMapper.toDto(unit));
    }

    @Operation(summary = "Search of Unit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unit searching result"
                    , content = @Content(schema = @Schema(implementation = UnitDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<UnitDto>> searchUnits(@RequestBody UnitSearchRequest unitSearchRequest, Pageable pageable) {
    Page<Unit> page = unitService.searchAvailableUnits(unitSearchRequest, pageable);
        Page<UnitDto> result = page.map(UnitMapper::toDto);

        return ResponseEntity.ok(result);
    }
}
