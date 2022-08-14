package com.esdp.demo_esdp.service;

import com.esdp.demo_esdp.entity.Localities;
import com.esdp.demo_esdp.repositories.LocalitiesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class LocalitiesServiceTest {

    @Autowired
    LocalitiesService localitiesService;

    @MockBean
    LocalitiesRepository localitiesRepository;

    @Test
    void getLocalitiesDTOs() {
        var localites = mock(Localities.class);
        when(localitiesRepository.findAllChildren()).thenReturn(List.of(localites));

        var  localitiesList = localitiesService.getLocalitiesDTOs();

        assertNotNull(localitiesList);
        verify(localitiesRepository).findAllChildren();
    }

    @Test
    void getFilterLocalities() {
    }

    @Test
    void getLocalityDTOById() {
    }
}