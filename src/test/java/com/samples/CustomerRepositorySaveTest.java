package com.samples;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@SpringBootTest
@Import(SpringAsyncConfig.class)
@Slf4j
public class CustomerRepositorySaveTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;


    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer = new Customer("Mark", "Smith");

    @BeforeEach
    public void init() {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(customer);
        transaction.commit();
    }

    @Test
    public void shouldThrowOptimisticLoadingException() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Future<Void>> futures = startAllAsynCalls(3);
        processAllCalls(futures);
        stopWatch.stop();
        log.info("time spent: {}", stopWatch.getTotalTimeMillis());

    }



    private List<Future<Void>> startAllAsynCalls(int numberOfTimes) throws Exception {
        List<Future<Void>> futures = new ArrayList<>();
        for (int i = 0; i < numberOfTimes; i++) {
            Future<Void> future = this.customerRepository.save(this.customer);
            futures.add(future);
        }
        return futures;
    }

    private void processAllCalls(List<Future<Void>> futures) throws Exception {
        while (true) {
            if (!areFuturesAllDone(futures)) {
                Thread.sleep(200);
            } else return;

        }
    }

    private boolean areFuturesAllDone(List<Future<Void>> futures) throws Exception {
        boolean allDone;
        for (Future<Void> future : futures) {
            if (!future.isDone()) {
                return false;
            }
            future.get();
        }
        return true;
    }


    @AfterEach
    public void tearDown() {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(customer);
        transaction.commit();
    }
}

@Configuration
@EnableAsync
class SpringConfig {

}
