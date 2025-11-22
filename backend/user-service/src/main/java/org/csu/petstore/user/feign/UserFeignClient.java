package org.csu.petstore.user.feign;


import org.csu.petstore.user.entity.Signon;
import org.csu.petstore.user.vo.AccountVO;
import org.csu.petstore.user.vo.ResetPasswordDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service", path = "/api/v1")
public interface UserFeignClient {
    @PostMapping("/auth/login")
    ResponseEntity<String> login(@RequestBody Signon signon);

    @DeleteMapping("/auth/tokens/current")
    ResponseEntity<String> signOff(@RequestHeader("Authorization") String authorization);

    @PostMapping("/account")
    ResponseEntity<String> register(@RequestBody AccountVO user);

    @GetMapping("/account/me")
    ResponseEntity<AccountVO> getUserInfo(@RequestHeader("Authorization") String authorization);

    @GetMapping("/account/me/myJournal")
    ResponseEntity<Object> getMyJournal(@RequestHeader("Authorization") String authorization);

    @PostMapping("/auth/resetPsw")
    ResponseEntity<String> resetPassword(@RequestHeader("Authorization") String authorization,
                                         @RequestBody ResetPasswordDTO dto);

    @PutMapping("/account/me/info")
    ResponseEntity<AccountVO> updateUserInfo(@RequestHeader("Authorization") String authorization,
                                             @RequestBody AccountVO user);
}
