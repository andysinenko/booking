package homes.sinenko.booking.service;

import homes.sinenko.booking.dto.unit.CreateUnitRequest;
import homes.sinenko.booking.dto.unit.UnitSearchRequest;
import homes.sinenko.booking.entity.Unit;
import homes.sinenko.booking.entity.UnitType;
import homes.sinenko.booking.entity.User;
import homes.sinenko.booking.exception.UnitAlreadyExistsException;
import homes.sinenko.booking.exception.UserNotFoundException;
import homes.sinenko.booking.repository.BookingRepository;
import homes.sinenko.booking.repository.UnitRepository;
import homes.sinenko.booking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnitServiceTest {

    private UnitRepository unitRepository;
    private BookingRepository bookingRepository;
    private UserRepository userRepository;
    private UnitService unitService;

    @BeforeEach
    void setUp() {
        unitRepository = mock(UnitRepository.class);
        bookingRepository = mock(BookingRepository.class);
        userRepository = mock(UserRepository.class);
        unitService = new UnitService(unitRepository, bookingRepository, userRepository);
    }

    @Test
    void createUnit_success() {
        CreateUnitRequest request = new CreateUnitRequest( 2, UnitType.FLAT, 3,  BigDecimal.valueOf(1000), "Nice", 1L, true);
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(unitRepository.existsByRoomsAndTypeAndFloorAndCostAndDescriptionAndCreatedBy_NameAndAvailable(
                anyInt(),
                any(UnitType.class),
                anyInt(),
                any(BigDecimal.class),
                anyString(),
                anyString(),
                anyBoolean()
        )).thenReturn(true);
        when(unitRepository.save(any(Unit.class))).thenAnswer(i -> i.getArgument(0));

        Unit unit = unitService.createUnit(request);

        assertNotNull(unit);
        assertEquals(UnitType.FLAT, unit.getType());
    }

    @Test
    void createUnit_userNotFound() {
        CreateUnitRequest request = new CreateUnitRequest( 2, UnitType.FLAT, 3,  BigDecimal.valueOf(1000), "Nice", 1L, true);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> unitService.createUnit(request));
    }

    @Test
    void createUnit_alreadyExists_throwsException() {
        CreateUnitRequest request = new CreateUnitRequest( 2, UnitType.FLAT, 3,  BigDecimal.valueOf(1000), "Nice", 1L, true);
        User user = new User();
        user.setId(1L);
        user.setName("John");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(unitRepository.save(any(Unit.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(unitRepository.existsByRoomsAndTypeAndFloorAndCostAndDescriptionAndCreatedBy_NameAndAvailable(
                anyInt(),                     // rooms
                any(UnitType.class),          // type
                anyInt(),                     // floor
                any(BigDecimal.class),        // cost
                anyString(),                  // description
                anyString(),                  // createdBy.name
                anyBoolean()                  // available
        )).thenReturn(true);
        assertThrows(UnitAlreadyExistsException.class, () -> unitService.createUnit(request));
    }

    @Test
    void findAvailableUnits_returnsList() {
        UnitFilter filter = new UnitFilter();
        filter.setPage(0);
        filter.setSize(10);
        filter.setSortBy("cost");
        filter.setDirection("ASC");

        when(unitRepository.findAvailableUnits(
                eq(UnitType.FLAT),
                eq(2),
                eq(2),
                eq(BigDecimal.valueOf(100)),
                eq(BigDecimal.valueOf(1000)),
                eq(LocalDate.now()),
                eq(LocalDate.now().plusDays(2)),
                any(Pageable.class)
        )).thenReturn(Collections.emptyList());

        assertDoesNotThrow(() -> unitService.findAvailableUnits(filter));
    }

    @Test
    void testSearchAvailableUnits_returnsPage() {
        UnitSearchRequest request = new UnitSearchRequest();
        request.setType(UnitType.APARTMENT);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Unit> mockedPage = new PageImpl<>(List.of(new Unit()));

        when(unitRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(mockedPage);

        Page<Unit> result = unitService.searchAvailableUnits(request, pageable);

        assertEquals(mockedPage, result);
        verify(unitRepository).findAll(any(Specification.class), eq(pageable));
    }
}