package com.samples;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.StopWatch;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@SpringBootTest
@Import(SpringAsyncConfig.class)
@Slf4j
public class CustomerRepositoryTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;


    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer = new Customer("John", "Black");

    @Test
    public void shouldCreateTransactionIsolationHigh() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Future<Customer>> futures = startAllAsynCalls(10);
        processAllCalls(futures);
        stopWatch.stop();
        log.info("time spent: {}", stopWatch.getTotalTimeMillis());

    }





    private List<Future<Customer>> startAllAsynCalls(int numberOfTimes) throws Exception {
        List<Future<Customer>> futures = new ArrayList<>();
        for (int i = 0; i < numberOfTimes; i++) {
            Future<Customer> customer = this.customerRepository.find();
            futures.add(customer);
        }
        return futures;
    }

    private void processAllCalls(List<Future<Customer>> futures) throws Exception {
        while (true) {
            if (!areFuturesAllDone(futures)) {
                Thread.sleep(200);
            } else return;

        }
    }

    private boolean areFuturesAllDone(List<Future<Customer>> futures) throws Exception {
        boolean allDone;
        for (Future<Customer> future : futures) {
            if (!future.isDone()) {
                return false;
            }
            future.get();
        }
        return true;
    }
}


@Configuration
@EnableAsync
class SpringAsyncConfig {

}