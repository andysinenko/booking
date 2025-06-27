package homes.sinenko.booking.service;

import homes.sinenko.booking.entity.Booking;
import homes.sinenko.booking.entity.BookingStatus;
import homes.sinenko.booking.entity.Unit;
import homes.sinenko.booking.entity.UnitType;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UnitSpecifications {

    public static Specification<Unit> typeEquals(UnitType type) {
        return (root, query, cb) ->
                type == null ? null : cb.equal(root.get("type"), type);
    }

    public static Specification<Unit> roomsEquals(Integer rooms) {
        return (root, query, cb) ->
                rooms == null ? null : cb.equal(root.get("rooms"), rooms);
    }

    public static Specification<Unit> floorEquals(Integer floor) {
        return (root, query, cb) ->
                floor == null ? null : cb.equal(root.get("floor"), floor);
    }

    public static Specification<Unit> availableEquals(Boolean available) {
        return (root, query, cb) ->
                available == null ? null : cb.equal(root.get("available"), available);
    }

    public static Specification<Unit> costBetween(BigDecimal min, BigDecimal max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min == null) return cb.lessThanOrEqualTo(root.get("cost"), max);
            if (max == null) return cb.greaterThanOrEqualTo(root.get("cost"), min);
            return cb.between(root.get("cost"), min, max);
        };
    }

    public static Specification<Unit> isAvailableBetween(LocalDate from, LocalDate to) {
        return (root, query, cb) -> {
            if (from == null || to == null) return null;

            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Booking> bookingRoot = subquery.from(Booking.class);
            subquery.select(bookingRoot.get("unit").get("id"))
                    .where(
                            cb.and(
                                    cb.notEqual(bookingRoot.get("status"), BookingStatus.CANCELLED),
                                    cb.lessThan(bookingRoot.get("startDate"), to),
                                    cb.greaterThan(bookingRoot.get("endDate"), from)
                            )
                    );

            return cb.not(root.get("id").in(subquery));
        };
    }
}
