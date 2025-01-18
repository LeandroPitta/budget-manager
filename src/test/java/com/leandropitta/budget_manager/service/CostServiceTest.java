package com.leandropitta.budget_manager.service;

import com.leandropitta.budget_manager.dto.request.CostRequestDto;
import com.leandropitta.budget_manager.dto.response.CostResponseDto;
import com.leandropitta.budget_manager.dto.response.CostsResponseDto;
import com.leandropitta.budget_manager.dto.response.GiftResponseDto;
import com.leandropitta.budget_manager.entity.Cost;
import com.leandropitta.budget_manager.entity.User;
import com.leandropitta.budget_manager.repository.CostRepository;
import com.leandropitta.budget_manager.repository.UserRepository;
import com.leandropitta.budget_manager.util.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CostServiceTest {

    @Mock
    private CostRepository costRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CostService costService;

    private User user;
    private Cost cost;
    private CostRequestDto costRequestDto;
    private CostResponseDto costResponseDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setBudgetValue(BigDecimal.valueOf(1000));

        // Mock SecurityContext and Authentication
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // Mock UserDetails
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(user.getUsername());
        when(authentication.getPrincipal()).thenReturn(userDetails);

        cost = new Cost();
        cost.setId(1L);
        cost.setUser(user);
        cost.setCost(BigDecimal.valueOf(100));
        cost.setDate(LocalDateTime.now());

        costRequestDto = new CostRequestDto();
        costRequestDto.setBuy("Test Buy");
        costRequestDto.setCost(BigDecimal.valueOf(100));

        costResponseDto = new CostResponseDto();
        costResponseDto.setId(1L);
        costResponseDto.setBuy("Test Buy");
        costResponseDto.setCost(BigDecimal.valueOf(100));
    }

    @Test
    void testGetCosts() {
        when(SecurityUtil.getCurrentUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(costRepository.findByUserId(user.getId(), Sort.by("date").descending()))
                .thenReturn(Collections.singletonList(cost));
        when(modelMapper.map(any(Cost.class), eq(CostResponseDto.class))).thenReturn(costResponseDto);

        CostsResponseDto costsResponseDto = costService.getCosts();

        assertNotNull(costsResponseDto);
        assertEquals(1, costsResponseDto.getCosts().size());
        assertEquals(costResponseDto, costsResponseDto.getCosts().get(0));
    }

    @Test
    void testCalculateGift() {
        when(SecurityUtil.getCurrentUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(costRepository.findByUserId(user.getId(), Sort.by("date").descending()))
                .thenReturn(Collections.singletonList(cost));

        GiftResponseDto giftResponseDto = costService.calculateGift();

        assertNotNull(giftResponseDto);
        assertEquals(user.getBudgetValue(), giftResponseDto.getGift());
        assertEquals(cost.getCost(), giftResponseDto.getSpent());
        assertEquals(user.getBudgetValue().subtract(cost.getCost()), giftResponseDto.getAvailable());
    }

    @Test
    void testCreateCost() {
        when(SecurityUtil.getCurrentUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(modelMapper.map(any(CostRequestDto.class), eq(Cost.class))).thenReturn(cost);
        when(costRepository.save(any(Cost.class))).thenReturn(cost);
        when(modelMapper.map(any(Cost.class), eq(CostResponseDto.class))).thenReturn(costResponseDto);

        CostResponseDto createdCost = costService.createCost(costRequestDto);

        assertNotNull(createdCost);
        assertEquals(costResponseDto, createdCost);
    }

    @Test
    void testUpdateCost() {
        when(costRepository.findById(1L)).thenReturn(Optional.of(cost));
        when(SecurityUtil.getCurrentUsername()).thenReturn("testUser");
        when(costRepository.save(any(Cost.class))).thenReturn(cost);
        when(modelMapper.map(any(Cost.class), eq(CostResponseDto.class))).thenReturn(costResponseDto);

        CostResponseDto updatedCost = costService.updateCost(1L, costRequestDto);

        assertNotNull(updatedCost);
        assertEquals(costResponseDto, updatedCost);
    }

    @Test
    void testDeleteCost() {
        when(costRepository.findById(1L)).thenReturn(Optional.of(cost));
        when(SecurityUtil.getCurrentUsername()).thenReturn("testUser");

        assertDoesNotThrow(() -> costService.deleteCost(1L));
        verify(costRepository, times(1)).delete(cost);
    }
}