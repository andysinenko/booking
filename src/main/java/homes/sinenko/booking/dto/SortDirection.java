package homes.sinenko.booking.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public enum SortDirection {
    ASC("ASC"),
    DESC("DESC");

    @Getter @Setter
    private final String sortDirection;
}
