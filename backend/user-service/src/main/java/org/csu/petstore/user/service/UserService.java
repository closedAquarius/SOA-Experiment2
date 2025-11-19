package org.csu.petstore.user.service;

import org.csu.petstore.user.entity.Journal;
import org.csu.petstore.user.entity.Signon;
import org.csu.petstore.user.vo.AccountVO;

import java.util.List;

public interface UserService {
    Signon getSignonByUsername(String username);
    void insertAccount(AccountVO accountVO);
    AccountVO getAccountVOByUsername(String username);
    void updateAccount(AccountVO accountVO);
    void updateJournal(String userId, String description, String date, String color);
    List<Journal> getAllJournals(String username);
    boolean login(String username, String password);
}