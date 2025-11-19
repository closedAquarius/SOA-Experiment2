package org.csu.petstore.user.service;

public interface TokenBlackService {
    boolean addToBlackList(String token);
    boolean isInBlackList(String token);
}