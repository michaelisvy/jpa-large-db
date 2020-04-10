package com.samples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalTime;
import java.util.concurrent.Future;

@Service
public class CustomerRepository {

    @Autowired
    private EntityManager entityManager;

    @Transactional(isolation = Isolation.REPEATABLE_READ) @Async
    public Future<Customer> find() throws InterruptedException {
        Query query = this.entityManager.createQuery("from Customer where " +
                "(field1= :field1 and field2=:field2) or (field3= :field3 and field4=:field4) order by firstName");
        query.setParameter("field1", "aaaaaaaaaaaa");
        query.setParameter("field2", "aaaaaaaaaaaa");
        query.setParameter("field3", "aaaaaaaaaaaa");
        query.setParameter("field4", "aaaaaaaaaaaa");
        query.setMaxResults(1);
        Customer customer1 = (Customer) query.getSingleResult();
        return new AsyncResult<>(customer1);
    }
}
