package homes.sinenko.booking.service;

import homes.sinenko.booking.dto.booking.CreateBookingRequest;
import homes.sinenko.booking.dto.setting.SettingDto;
import homes.sinenko.booking.entity.Booking;
import homes.sinenko.booking.entity.BookingStatus;
import homes.sinenko.booking.entity.Unit;
import homes.sinenko.booking.entity.User;
import homes.sinenko.booking.repository.BookingRepository;
import homes.sinenko.booking.repository.PaymentRepository;
import homes.sinenko.booking.repository.UnitRepository;
import homes.sinenko.booking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {
    @Mock
    BookingRepository bookingRepository;
    @Mock
    PaymentRepository paymentRepository;
    @Mock
    RedisTemplate<String, Integer> redisTemplate;
    @Mock
    SettingService settingService;
    @Mock
    UnitRepository unitRepository;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    BookingService bookingService;

    @Mock
    private ValueOperations<String, Integer> valueOperations;

    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void bookUnit_shouldSaveBookingAndUpdateCache() {
        CreateBookingRequest req = new CreateBookingRequest(1L, 2L, LocalDate.now(), LocalDate.now().plusDays(2));
        Unit unit = new Unit();
        User user = new User();
        Booking booking = new Booking();

        when(unitRepository.findById(1L)).thenReturn(Optional.of(unit));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        when(bookingRepository.save(any())).thenReturn(booking);

        Booking result = bookingService.bookUnit(req);

        assertNotNull(result);
        verify(bookingRepository).save(any());
        verify(redisTemplate).opsForValue();
    }

    @Test
    void cancelBooking_shouldUpdateStatusAndCache() {
        Booking booking = new Booking();
        booking.setStatus(BookingStatus.PENDING);
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any())).thenReturn(booking);

        bookingService.cancelBooking(1L);

        assertEquals(BookingStatus.CANCELLED, booking.getStatus());
        verify(redisTemplate).opsForValue();
    }

    @Test
    void payForBooking_shouldSavePaymentAndUpdateStatus() {
        Unit unit = new Unit();
        unit.setCost(BigDecimal.valueOf(100.0));

        Booking booking = new Booking();
        booking.setStatus(BookingStatus.PENDING);
        booking.setUnit(unit);
        booking.setId(1L);
        booking.setStartDate(LocalDate.now());
        booking.setEndDate(LocalDate.now().plusDays(2));

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any())).thenReturn(booking);
        when(settingService.getByKey("markupPercent"))
                .thenReturn(new SettingDto(null, "markupPercent", "15"));

        bookingService.payForBooking(1L);

        assertEquals(BookingStatus.CONFIRMED, booking.getStatus());
        verify(paymentRepository).save(any());
        verify(redisTemplate).opsForValue();
    }
}