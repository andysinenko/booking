package homes.sinenko.booking.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SortDirection {
    ASC("ASC"),
    DESC("DESC");

    @Getter
    private final String sortDirection;
}
