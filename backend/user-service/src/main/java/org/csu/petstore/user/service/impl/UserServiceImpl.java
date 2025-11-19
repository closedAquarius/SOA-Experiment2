package org.csu.petstore.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.petstore.user.entity.Account;
import org.csu.petstore.user.entity.Journal;
import org.csu.petstore.user.entity.Profile;
import org.csu.petstore.user.entity.Signon;
import org.csu.petstore.user.persistence.AccountMapper;
import org.csu.petstore.user.persistence.JournalMapper;
import org.csu.petstore.user.persistence.ProfileMapper;
import org.csu.petstore.user.persistence.SignonMapper;
import org.csu.petstore.user.service.UserService;
import org.csu.petstore.user.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private SignonMapper signonMapper;
    @Autowired
    private ProfileMapper profileMapper;
    @Autowired
    private JournalMapper journalMapper;

    @Override
    public Signon getSignonByUsername(String username) {
        return signonMapper.selectById(username);
    }

    @Override
    @Transactional
    public void insertAccount(AccountVO accountVO) {
        accountMapper.insert(accountVO.getAccount());
        signonMapper.insert(accountVO.getSignon());
        profileMapper.insert(accountVO.getProfile());
    }

    @Override
    public AccountVO getAccountVOByUsername(String username) {
        Account account = accountMapper.selectById(username);
        Signon signon = signonMapper.selectById(username);
        Profile profile = profileMapper.selectById(username);

        AccountVO accountVO = new AccountVO();
        accountVO.setAccount(account);
        accountVO.setPassword(signon != null ? signon.getPassword() : null);
        accountVO.setBannerDataAndProfile(null, profile);

        QueryWrapper<Journal> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", username);
        List<Journal> journals = journalMapper.selectList(queryWrapper);
        accountVO.setJournals(journals);
        return accountVO;
    }

    @Override
    @Transactional
    public void updateAccount(AccountVO accountVO) {
        accountMapper.updateById(accountVO.getAccount());
        profileMapper.updateById(accountVO.getProfile());
        if (accountVO.getPassword() != null && accountVO.getPassword().length() > 0) {
            signonMapper.updateById(accountVO.getSignon());
        }
    }

    @Override
    @Transactional
    public void updateJournal(String userId, String description, String date, String color) {
        Journal journal = new Journal();
        journal.setUsername(userId);
        journal.setDescription(description);
        journal.setDate(date);
        journal.setColor(color);
        journalMapper.insert(journal);
    }

    @Override
    public List<Journal> getAllJournals(String username) {
        QueryWrapper<Journal> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", username);
        return journalMapper.selectList(queryWrapper);
    }

    @Override
    public boolean login(String username, String password) {
        Signon signon = signonMapper.selectById(username);
        if (signon == null) {
            return false;
        }
        return password != null && password.equals(signon.getPassword());
    }
}