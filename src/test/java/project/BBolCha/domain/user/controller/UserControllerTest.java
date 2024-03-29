package project.BBolCha.domain.user.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import project.BBolCha.domain.ControllerTestSupport;
import project.BBolCha.domain.user.dto.controller.request.UserRequest;
import project.BBolCha.domain.user.dto.service.responce.UserResponse;
import project.BBolCha.domain.user.entity.User;

import javax.servlet.http.Cookie;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends ControllerTestSupport {

    @DisplayName("회원가입 API")
    @Test
    void registerNewUser() throws Exception {
        // given
        UserRequest.Registration request = new UserRequest.Registration("덕배", "kevin@gmail.com", "abc123!");

        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/auth")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("로그인 API")
    @Test
    void loginUser() throws Exception {
        // given
        UserRequest.Login request = new UserRequest.Login("kevin@gmail.com", "abc123!");

        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/auth/login")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("유저 정보 조회 API")
    @Test
    void readUserDetail() throws Exception {
        User user = User.builder().build();
        // given
        given(userService.read(user))
                .willReturn(UserResponse.Detail.builder()
                        .id(1L)
                        .email("kevin@gmail.com")
                        .name("kevin")
                        .profileImageUrl("test.png")
                        .build()
                );

        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/auth/info")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("토큰 재발급 API")
    @Test
    void reissueToken() throws Exception {
        // given
        Cookie cookie = new Cookie("refreshToken", "testToken");
        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/auth")
                                .cookie(cookie)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("로그아웃 API")
    @Test
    void logoutUser() throws Exception {
        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/auth/logout")
                                .header("Authorization", "testToken")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("회원정보 수정 API")
    @Test
    void updateUserNameAndProfileImageUrl() throws Exception {
        // given
        UserRequest.Update request = new UserRequest.Update("test", "test");
        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/auth")
                                .content(objectMapper.writeValueAsString(request.toServiceRequest()))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("유저 프로필 이미지 확인 API")
    @Test
    void checkMyProfileImageUrl() throws Exception {
        // given
        User user = User.builder().build();

        given(userService.read(user))
                .willReturn(UserResponse.Detail.builder()
                        .id(1L)
                        .email("kevin@gmail.com")
                        .name("kevin")
                        .profileImageUrl("test.png")
                        .build()
                );

        // when // then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/auth/check/image")
                                .header("Authorization", "testToken")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
}