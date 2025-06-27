package homes.sinenko.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import homes.sinenko.booking.dto.booking.BookingDto;
import homes.sinenko.booking.dto.booking.CreateBookingRequest;
import homes.sinenko.booking.entity.*;
import homes.sinenko.booking.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookingService bookingService;

    @Autowired
    private ObjectMapper objectMapper;

    private CreateBookingRequest createBookingRequest;
    private BookingDto bookingDto;

    @BeforeEach
    void setup() {
        createBookingRequest = new CreateBookingRequest(
                1L, 2L, LocalDate.parse("2025-07-01"), LocalDate.parse("2025-07-05")
        );

        bookingDto = new BookingDto(
                100L, 1L, 2L, LocalDate.parse("2025-07-01"), LocalDate.parse("2025-07-05"), "CONFIRMED"
        );
    }

    @Test
    void createBooking_returnsBookingDto() throws Exception {
        var booking = Booking.builder()
                        .id(100L)
                        .status(BookingStatus.CONFIRMED)
                        .createdBy(new User(1L, "testuser"))
                        .unit(Unit.builder()
                                .id(1L)
                                .rooms(2)
                                .type(UnitType.FLAT)
                                .floor(3)
                                .cost(BigDecimal.valueOf(1000))
                                .description("Nice flat")
                                .build())
                        .startDate(LocalDate.parse("2025-07-01"))
                        .user(new User(1L, "testuser"))
                        .createdAt(LocalDateTime.now())
                        .endDate(LocalDate.parse("2025-07-05"))
                        .build();

        when(bookingService.bookUnit(ArgumentMatchers.any(CreateBookingRequest.class)))
                .thenReturn(booking);

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBookingRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(bookingDto.id()))
                .andExpect(jsonPath("$.status").value(bookingDto.status()));

        verify(bookingService).bookUnit(ArgumentMatchers.any(CreateBookingRequest.class));
    }

    @Test
    void payForBooking_returnsNoContent() throws Exception {
        var booking = Booking.builder()
                .id(1L)
                .status(BookingStatus.CONFIRMED)
                .createdBy(new User(1L, "testuser"))
                .unit(Unit.builder()
                        .id(1L)
                        .rooms(2)
                        .type(UnitType.FLAT)
                        .floor(3)
                        .cost(BigDecimal.valueOf(1000))
                        .description("Nice flat")
                        .build())
                .startDate(LocalDate.parse("2025-07-01"))
                .endDate(LocalDate.parse("2025-07-05"))
                .build();
        when(bookingService.payForBooking(100L)).thenReturn(booking);

        mockMvc.perform(post("/api/bookings/{id}/pay", 100L))
                .andExpect(status().isOk());

        verify(bookingService).payForBooking(100L);
    }
}