package com.noctest.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by admin on 2/05/2016.
 */
@Component
@Service
public class AuthTokenRepo implements TokenRepoIF {
    private static final Logger log = LoggerFactory.getLogger(AuthTokenRepo.class);
    private Optional<String> authToken = Optional.empty();
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    public void setToken(String token) {
        writeLock.lock();
        try {
            if (!token.isEmpty())
                authToken = Optional.of(token);
        } finally {
            writeLock.unlock();
        }
    }

    public Optional<String> getToken() {
        Optional<String> result = Optional.empty();
        readLock.lock();
        try {
            result = authToken;
        } finally {
            readLock.unlock();
        }
        return result;
    }
}
