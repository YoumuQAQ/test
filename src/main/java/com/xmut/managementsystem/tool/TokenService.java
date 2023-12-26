package com.xmut.managementsystem.tool;

public interface TokenService {

    String create(String username);

    Boolean check(String token);

    String getUsername(String token);
}
