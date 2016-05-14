package com.noctest.auth;

import java.util.Optional;

/**
 * Created by admin on 3/05/2016.
 */
public interface TokenRepoIF {
    public void setToken(String token);
    public Optional<String> getToken();
}
