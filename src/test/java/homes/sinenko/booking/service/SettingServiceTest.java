package homes.sinenko.booking.service;


import homes.sinenko.booking.dto.setting.CreateSettingRequest;
import homes.sinenko.booking.dto.setting.SettingDto;
import homes.sinenko.booking.dto.setting.SettingsMapper;
import homes.sinenko.booking.entity.Setting;
import homes.sinenko.booking.exception.SettingAlreadyExistsException;
import homes.sinenko.booking.exception.SettingNotExistsException;
import homes.sinenko.booking.repository.SettingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SettingServiceTest {

    @Mock
    private SettingRepository repository;

    @InjectMocks
    private SettingService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_shouldSaveAndReturnDto_whenKeyNotExists() {
        CreateSettingRequest request = new CreateSettingRequest("key1", "someValue");

        Setting setting = new Setting();
        setting.setId(1L);
        setting.setKey("key1");
        setting.setValue("someValue");

        SettingDto dto = new SettingDto(1L, "key1", "someValue");

        when(repository.existsByKey("key1")).thenReturn(false);
        when(repository.save(any(Setting.class))).thenReturn(setting);

        try (MockedStatic<SettingsMapper> mapperMock = mockStatic(SettingsMapper.class)) {
            mapperMock.when(() -> SettingsMapper.toEntity(request)).thenReturn(setting);
            mapperMock.when(() -> SettingsMapper.toDto(setting)).thenReturn(dto);

            SettingDto result = service.create(request);
            assertEquals(dto, result);
            verify(repository).save(setting);
        }
    }


    @Test
    void create_shouldThrow_whenKeyExists() {
        CreateSettingRequest req = new CreateSettingRequest("key1", "value");

        when(repository.existsByKey("key1")).thenReturn(true);

        assertThrows(SettingAlreadyExistsException.class, () -> service.create(req));
    }

    @Test
    void updateSetting_shouldSaveAndReturnDto_whenKeyExists() {
        Long id = 1L;
        CreateSettingRequest req = new CreateSettingRequest("key2", "newValue");
        Setting setting = new Setting(id, "key2", "newValue");
        SettingDto dto = new SettingDto(id, "key2", "newValue");

        when(repository.existsByKey("key2")).thenReturn(true);
        when(repository.findById(id)).thenReturn(Optional.of(setting));
        when(repository.save(any(Setting.class))).thenReturn(setting);

        try (MockedStatic<SettingsMapper> mocked = mockStatic(SettingsMapper.class)) {
            mocked.when(() -> SettingsMapper.toEntity(req)).thenReturn(setting);
            mocked.when(() -> SettingsMapper.toDto(setting)).thenReturn(dto);

            SettingDto result = service.updateSetting(id, req);

            assertEquals(dto, result);
            verify(repository).save(setting);
        }
    }

    @Test
    void updateSetting_shouldThrow_whenKeyNotExists() {
        CreateSettingRequest req = new CreateSettingRequest("key2", "newValue");

        when(repository.existsByKey("key2")).thenReturn(false);

        assertThrows(SettingNotExistsException.class, () -> service.updateSetting(1L, req));
    }

    @Test
    void deleteSetting_shouldCallRepository() {
        service.deleteSetting(5L);
        verify(repository).deleteById(5L);
    }

    @Test
    void getById_shouldReturnDto_whenFound() {
        Setting setting = new Setting(2L, "key3", "val3");
        SettingDto dto = new SettingDto(2L, "key3", "val3");

        when(repository.findById(2L)).thenReturn(Optional.of(setting));
        mockStatic(SettingsMapper.class).when(() -> SettingsMapper.toDto(setting)).thenReturn(dto);

        SettingDto result = service.getById(2L);

        assertEquals(dto, result);
    }

    @Test
    void getById_shouldThrow_whenNotFound() {
        when(repository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.getById(3L));
    }

    @Test
    void getAllSettings_shouldReturnListOfDtos() {
        Setting s1 = new Setting(1L, "k1", "v1");
        Setting s2 = new Setting(2L, "k2", "v2");
        SettingDto d1 = new SettingDto(1L, "k1", "v1");
        SettingDto d2 = new SettingDto(2L, "k2", "v2");

        when(repository.findAll()).thenReturn(List.of(s1, s2));

        try (MockedStatic<SettingsMapper> mocked = mockStatic(SettingsMapper.class)) {
            mocked.when(() -> SettingsMapper.toDto(s1)).thenReturn(d1);
            mocked.when(() -> SettingsMapper.toDto(s2)).thenReturn(d2);

            List<SettingDto> result = service.getAllSettings();

            assertEquals(List.of(d1, d2), result);
        }
    }
}