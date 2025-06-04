package org.csu.petstore.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.csu.petstore.entity.*;

import java.util.List;

@Data
public class AccountVO {

    // 基本信息
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String status;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String phone;

    // 偏好设置
    private String languagePreference;
    private String favouriteCategoryId;
    private boolean listOption;
    private boolean bannerOption;
    private String bannerName;

    // 日志
    private List<Journal> journals;

    public Signon getSignon(){
        Signon signon=new Signon();
        signon.setUsername(username);
        signon.setPassword(password);
        return signon;
    }

    public void setAccount(Account account){
        this.username=account.getUsername();
        this.email=account.getEmail();
        this.firstName=account.getFirstName();
        this.lastName=account.getLastName();
        this.status=account.getStatus();
        this.address1=account.getAddress1();
        this.address2=account.getAddress2();
        this.city=account.getCity();
        this.state=account.getState();
        this.zip=account.getZip();
        this.country=account.getCountry();
        this.phone=account.getPhone();
    }
    public Account getAccount(){
        Account account=new Account();
        account.setUsername(username);
        account.setEmail(email);
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setStatus(status);
        account.setAddress1(address1);
        account.setAddress2(address2);
        account.setCity(city);
        account.setState(state);
        account.setZip(zip);
        account.setCountry(country);
        account.setPhone(phone);
        return account;

    }

    public void setJournal(List<Journal> journals){
        this.journals=journals;
    }

    public void setBannerDataAndProfile(BannerData bannerData, Profile profile){
        this.bannerName=bannerData.getBannerName();
        this.favouriteCategoryId=profile.getFavouriteCategoryId();
        this.languagePreference=profile.getLanguagePreference();
        this.listOption=profile.isListOption();
        this.bannerOption=profile.isBannerOption();
    }

    public Profile getProfile(){
        Profile profile=new Profile();
        profile.setUsername(username);
        profile.setFavouriteCategoryId(favouriteCategoryId);
        profile.setLanguagePreference(languagePreference);
        profile.setListOption(listOption);
        profile.setBannerOption(bannerOption);
        return profile;
    }
}
