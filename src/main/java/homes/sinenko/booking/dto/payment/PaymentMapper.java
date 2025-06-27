package homes.sinenko.booking.dto.payment;

import homes.sinenko.booking.entity.Payment;

public class PaymentMapper {
    public static PaymentDto toDto(Payment payment) {
        return new PaymentDto(
                payment.getId(),
                payment.getBooking().getId(),
                payment.getAmount(),
                payment.getPaidAt()
        );
    }
}
