package homes.sinenko.booking.service;

import homes.sinenko.booking.dto.booking.CreateBookingRequest;
import homes.sinenko.booking.entity.*;
import homes.sinenko.booking.exception.BookingAlreadyExistsException;
import homes.sinenko.booking.exception.UnitNotFoundException;
import homes.sinenko.booking.exception.UserNotFoundException;
import homes.sinenko.booking.repository.BookingRepository;
import homes.sinenko.booking.repository.PaymentRepository;
import homes.sinenko.booking.repository.UnitRepository;
import homes.sinenko.booking.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private static final String AVAILABLE_UNITS_CACHE_KEY = "available_units_count";
    private static final String MARKUP_PERCENT = "markupPercent";
    private final BookingRepository bookingRepository;
    private final UnitRepository unitRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final RedisTemplate<String, Integer> redisTemplate;
    private final SettingService settingService;

    @Transactional
    @CacheEvict(value = "availableUnitsCount", allEntries = true)
    public Booking bookUnit(CreateBookingRequest createBookingRequest) {
        var bookingExisting = bookingRepository
                .existsByUnitIdAndUserIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualAndStatusIn(
                        createBookingRequest.unitId(),
                        createBookingRequest.userId(),
                        createBookingRequest.startDate(),
                        createBookingRequest.endDate(),
                        List.of(BookingStatus.CONFIRMED, BookingStatus.PENDING));

        if (bookingExisting) {
            throw new BookingAlreadyExistsException("Boolking is already booked for this period");
        }

        Unit unit = unitRepository.findById(createBookingRequest.unitId())
                .orElseThrow(() -> new UnitNotFoundException(String.format("Unit %s not found", createBookingRequest.unitId())));
        User user = userRepository.findById(createBookingRequest.userId())
                .orElseThrow(() -> new UserNotFoundException(String.format("Unit %s not found", createBookingRequest.userId())));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setUnit(unit);
        booking.setStartDate(createBookingRequest.startDate());
        booking.setEndDate(createBookingRequest.endDate());
        booking.setStatus(BookingStatus.PENDING);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setCreatedBy(user);

        var bookingSaved = bookingRepository.save(booking);

        return bookingSaved;
    }

    @Transactional
    @CacheEvict(value = "availableUnitsCount", allEntries = true)
    public void cancelBooking(Long bookingId) {
        bookingRepository.findById(bookingId).ifPresent(booking -> {
            booking.setStatus(BookingStatus.CANCELLED);
            bookingRepository.save(booking);
        });
    }

    @Transactional
    @CacheEvict(value = "availableUnitsCount", allEntries = true)
    @CachePut(value = "bookings", key = "#bookingId")
    public Booking payForBooking(Long bookingId) {
        var booking = bookingRepository.findById(bookingId).orElseThrow(
                () -> new IllegalArgumentException(String.format("Booking %s not found", bookingId)));

        Payment payment = new Payment();
        payment.setBooking(booking);

        BigDecimal base = booking.getUnit().getCost()
                .multiply(BigDecimal.valueOf(ChronoUnit.DAYS.between(
                        booking.getStartDate(), booking.getEndDate())));

        BigDecimal fee = base.multiply(getCommissionPercentage().divide(BigDecimal.valueOf(100)));
        payment.setAmount(base.add(fee));

        payment.setPaidAt(LocalDateTime.now());
        paymentRepository.save(payment);

        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);

        return booking;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    @CacheEvict(value = "availableUnitsCount", allEntries = true)
    public void cancelExpiredUnpaidBookings() {
        LocalDateTime threshold = LocalDateTime.now().minusMinutes(15);
        List<Booking> expired = bookingRepository.findAll().stream()
                .filter(b -> b.getStatus() == BookingStatus.PENDING &&
                        b.getCreatedAt().isBefore(threshold))
                .toList();

        for (Booking booking : expired) {
            booking.setStatus(BookingStatus.CANCELLED);
            bookingRepository.save(booking);
        }
    }

    @Cacheable(value = "availableUnitsCount")
    public Integer getAvailableUnitsCountFromCache() {
        return calculateAvailableUnits();
    }

    private int calculateAvailableUnits() {
        List<Long> bookedUnitIds = bookingRepository.findConfirmedUnitIds();
        long totalUnits = unitRepository.count();
        return (int) (totalUnits - bookedUnitIds.size());
    }

    private BigDecimal getCommissionPercentage() {
        return new BigDecimal(settingService.getByKey(MARKUP_PERCENT).value());
    }
}