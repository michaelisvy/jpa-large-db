package com.samples;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import javax.persistence.EntityManager;

@Component @Slf4j
public class BatchProcessor {

    public static final int FIVE_MILLION = 5000000;
    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void insertData() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for(int i = 0; i< FIVE_MILLION; i++) {
            Customer customer = new Customer("John" + i, "Smith" + i);
            this.entityManager.persist(customer);
        }
        stopWatch.stop();
        log.info("time: {}", stopWatch.getTotalTimeMillis());


    }
}
