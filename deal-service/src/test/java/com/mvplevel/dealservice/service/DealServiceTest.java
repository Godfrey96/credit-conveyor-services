package com.mvplevel.dealservice.service;

import com.mvplevel.dealservice.model.Application;
import com.mvplevel.dealservice.repository.ApplicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DealServiceTest {

    @InjectMocks
    private DealService dealService;

    @Mock
    private ApplicationRepository applicationRepository;


    @Test
    void shouldCreateApplication(){

    }

    @Test
    void shouldGetApplication(){

        when(applicationRepository.findById(anyLong())).thenReturn(Optional.of(Application.builder().sesCode(1234).build()));

        Application application = dealService.getApplicationById(1L);

        assertEquals(1234, application.getSesCode());
    }

}
