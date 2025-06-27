package homes.sinenko.booking.service;

import homes.sinenko.booking.dto.unit.CreateUnitRequest;
import homes.sinenko.booking.dto.unit.UnitMapper;
import homes.sinenko.booking.dto.unit.UnitSearchRequest;
import homes.sinenko.booking.entity.Unit;
import homes.sinenko.booking.exception.UnitAlreadyExistsException;
import homes.sinenko.booking.exception.UserNotFoundException;
import homes.sinenko.booking.repository.BookingRepository;
import homes.sinenko.booking.repository.UnitRepository;
import homes.sinenko.booking.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitService {

    private final UnitRepository unitRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    @Transactional
    public Unit createUnit(CreateUnitRequest createUnitRequest) {
        var user = userRepository.findById(createUnitRequest.createdById()).orElseThrow(() -> new UserNotFoundException("User not found"));
        var unit = UnitMapper.fromCreateUnitRequst(createUnitRequest);
        unit.setCreatedBy(user);
        unit.setAvailable(true);
        boolean exists = unitRepository.existsByRoomsAndTypeAndFloorAndCostAndDescriptionAndCreatedBy_NameAndAvailable(
                unit.getRooms(),
                unit.getType(),
                unit.getFloor(),
                unit.getCost(),
                unit.getDescription(),
                unit.getCreatedBy().getName(),
                unit.getAvailable()
        );

        if (exists) {
            throw new UnitAlreadyExistsException("Unit with these parameters already exists");
        }

        return unitRepository.save(unit);
    }

    public List<Unit> findAvailableUnits(UnitFilter filter) {
        Pageable pageable = PageRequest.of(
                filter.getPage(),
                filter.getSize(),
                Sort.by(Sort.Direction.fromString(filter.getDirection()), filter.getSortBy())
        );

        return unitRepository.findAvailableUnits(
                filter.getType(),
                filter.getRooms(),
                filter.getFloor(),
                filter.getMinCost(),
                filter.getMaxCost(),
                filter.getFrom(),
                filter.getTo(),
                pageable
        );
    }


    public Page<Unit> searchAvailableUnits(UnitSearchRequest req, Pageable pageable) {
        Specification<Unit> spec = UnitSpecifications.typeEquals(req.getType());

        if (req.getRooms() != null)
            spec = spec == null ? UnitSpecifications.roomsEquals(req.getRooms()) : spec.and(UnitSpecifications.roomsEquals(req.getRooms()));

        if (req.getAvailable() != null)
            spec = spec == null ? UnitSpecifications.availableEquals(req.getAvailable()) : spec.and(UnitSpecifications.availableEquals(req.getAvailable()));

        if (req.getFloor() != null)
            spec = spec == null ? UnitSpecifications.floorEquals(req.getFloor()) : spec.and(UnitSpecifications.floorEquals(req.getFloor()));

        if (req.getMinCost() != null || req.getMaxCost() != null)
            spec = spec == null ? UnitSpecifications.costBetween(req.getMinCost(), req.getMaxCost()) : spec.and(UnitSpecifications.costBetween(req.getMinCost(), req.getMaxCost()));

        if (req.getFrom() != null && req.getTo() != null)
            spec = spec == null ? UnitSpecifications.isAvailableBetween(req.getFrom(), req.getTo()) : spec.and(UnitSpecifications.isAvailableBetween(req.getFrom(), req.getTo()));

        return unitRepository.findAll(spec, pageable);
    }
}
