package com.leandropitta.budget_manager.repository;

import com.leandropitta.budget_manager.entity.Cost;
import com.leandropitta.budget_manager.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CostRepositoryTest {

    @Autowired
    private CostRepository costRepository;

    @Autowired
    private UserRepository userRepository;

    private Cost cost1;
    private Cost cost2;
    private User user;

    @BeforeEach
    void setUp() {
        user = userRepository.getReferenceById(34L);

        cost1 = new Cost();
        cost1.setDate(LocalDateTime.now());
        cost1.setBuy("Item1");
        cost1.setCost(BigDecimal.valueOf(100.0));
        cost1.setUser(user);

        cost2 = new Cost();
        cost2.setDate(LocalDateTime.now());
        cost2.setBuy("Item2");
        cost2.setCost(BigDecimal.valueOf(200.0));
        cost2.setUser(user);

        costRepository.save(cost1);
        costRepository.save(cost2);
    }

    @AfterEach
    void tearDown() {
        costRepository.delete(cost1);
        costRepository.delete(cost2);
    }

    @Test
    @Transactional
    void findByUserIdReturnsSortedCosts() {
        Sort sort = Sort.by(Sort.Direction.ASC, "cost");
        List<Cost> costs = costRepository.findByUserId(user.getId(), sort);
        assertThat(costs).containsExactly(cost1, cost2);
    }

    @Test
    @Transactional
    void findByUserIdReturnsEmptyListWhenNoCostsFound() {
        Sort sort = Sort.by(Sort.Direction.ASC, "cost");
        List<Cost> costs = costRepository.findByUserId(100L, sort);
        assertThat(costs).isEmpty();
    }

    @Test
    @Transactional
    void findByUserIdReturnsCostsSortedByAmountDescending() {
        Sort sort = Sort.by(Sort.Direction.DESC, "cost");
        List<Cost> costs = costRepository.findByUserId(user.getId(), sort);
        assertThat(costs).containsExactly(cost2, cost1);
    }
}