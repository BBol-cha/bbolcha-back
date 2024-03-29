package project.BBolCha.domain.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.BBolCha.domain.user.dto.controller.request.UserRequest;
import project.BBolCha.domain.user.dto.service.responce.UserResponse;
import project.BBolCha.domain.user.entity.User;
import project.BBolCha.domain.user.service.UserService;
import project.BBolCha.global.model.CustomResponseEntity;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 로그인
    @PostMapping("auth/login")
    public CustomResponseEntity<UserResponse.Login> login(
            @RequestBody @Valid UserRequest.Login request,
            HttpServletResponse response
    ) {
        return CustomResponseEntity.success(userService.login(request.toServiceRequest(), response));
    }

    // 회원가입
    @PostMapping("auth")
    public CustomResponseEntity<UserResponse.Login> registerNewUser(
            @RequestBody @Valid UserRequest.Registration request,
            HttpServletResponse response
    ) {
        return CustomResponseEntity.success(userService.registerNewUser(request.toServiceRequest(), response));
    }

    // 정보 조회
    @GetMapping("auth/info")
    public CustomResponseEntity<UserResponse.Detail> read(
            @AuthenticationPrincipal User user
    ) {
        return CustomResponseEntity.success(userService.read(user));
    }

    // 로그인 만료시 atk 재발급
    @GetMapping("auth")
    public CustomResponseEntity<UserResponse.Reissue> reissue(
            @CookieValue(value = "refreshToken", required = false) Cookie cookie
    ) {
        return CustomResponseEntity.success(userService.reissue(cookie.getValue()));
    }

    // 로그아웃
    @DeleteMapping("auth/logout")
    public CustomResponseEntity<Void> logout(
            @RequestHeader(value = "Authorization") String bearerToken
    ) {
        return CustomResponseEntity.success(userService.logout(bearerToken.substring(7)));
    }

    // 회원정보 수정
    @PatchMapping("auth")
    public CustomResponseEntity<UserResponse.Detail> updateUserNameAndProfileImageUrl(
            @Valid @RequestBody UserRequest.Update request,
            @AuthenticationPrincipal User user
    ) {
        return CustomResponseEntity.success(userService.update(request.toServiceRequest(), user));
    }

    // 내 프로필 이미지 확인하기
    @GetMapping("auth/check/image")
    public CustomResponseEntity<UserResponse.CheckProfile> checkMyProfileImageUrl (
            @AuthenticationPrincipal User user
    ) {
        return CustomResponseEntity.success(userService.checkMyProfileImage(user));
    }

}
