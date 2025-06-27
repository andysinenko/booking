package homes.sinenko.booking.dto.payment;

import homes.sinenko.booking.entity.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PaymentMapperTest {


    @Test
    void toDto_shouldMapEntityToDto() {
        var paymentDate = LocalDateTime.now();
        var booking = Booking.builder().id(2L).build();

        var payment = new Payment();
        payment.setId(1L);
        payment.setBooking(booking);
        payment.setAmount(BigDecimal.valueOf(100.00));
        payment.setPaidAt(paymentDate);

        PaymentDto dto = PaymentMapper.toDto(payment);

        assertNotNull(dto);
        assertEquals(1L, dto.id());
        assertEquals(2L, dto.bookingId());
        assertEquals(paymentDate, dto.paidAt());
        assertEquals(BigDecimal.valueOf(100.00), dto.amount());
        assertNotNull(dto.paidAt());
    }
}