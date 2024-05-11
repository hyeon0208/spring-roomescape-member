package roomescape.admin;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import roomescape.auth.Role;
import roomescape.config.RoleCheckInterceptor;
import roomescape.config.WebMvcControllerTestConfig;
import roomescape.auth.JwtTokenProvider;
import roomescape.exception.ExceptionPageController;
import roomescape.member.dto.LoginMember;

@WebMvcTest(AdminPageController.class)
@Import({WebMvcControllerTestConfig.class, ExceptionPageController.class})
class AdminPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new AdminPageController())
                .setControllerAdvice(new ExceptionPageController())
                .addInterceptors(new RoleCheckInterceptor(jwtTokenProvider))
                .build();
    }

    @Nested
    @DisplayName("어드민 사용자 접근 성공 테스트")
    class AdminRoleTest {

        @Test
        @DisplayName("/admin 을 요청하면 index.html 를 반환한다.")
        void requestAdmin() throws Exception {
            LoginMember loginMember = new LoginMember(1L, Role.ADMIN, "어드민", "admin@email.com");
            doReturn(loginMember).when(jwtTokenProvider)
                    .getMember(anyString());

            mockMvc.perform(get("/admin")
                            .cookie(new Cookie("token", "cookieValue")))
                    .andExpect(status().isOk())
                    .andExpect(view().name("admin/index"));
        }

        @Test
        @DisplayName("/admin/reservation 를 요청하면 admin/reservation-new.html 를 반환한다.")
        void requestAdminReservation() throws Exception {
            LoginMember loginMember = new LoginMember(1L, Role.ADMIN, "어드민", "admin@email.com");
            doReturn(loginMember).when(jwtTokenProvider)
                    .getMember(anyString());

            mockMvc.perform(get("/admin/reservation")
                            .cookie(new Cookie("token", "cookieValue")))
                    .andExpect(status().isOk())
                    .andExpect(view().name("admin/reservation-new"));
        }

        @Test
        @DisplayName("/admin/time을 요청하면 time.html 를 반환한다.")
        void requestTime() throws Exception {
            LoginMember loginMember = new LoginMember(1L, Role.ADMIN, "어드민", "admin@email.com");
            doReturn(loginMember).when(jwtTokenProvider)
                    .getMember(anyString());

            mockMvc.perform(get("/admin/time")
                            .cookie(new Cookie("token", "cookieValue")))
                    .andExpect(status().isOk())
                    .andExpect(view().name("admin/time"));
        }

        @Test
        @DisplayName("/theme 을 요청하면 admin/theme.html 를 반환한다.")
        void requestTheme() throws Exception {
            LoginMember loginMember = new LoginMember(1L, Role.ADMIN, "어드민", "admin@email.com");
            doReturn(loginMember).when(jwtTokenProvider)
                    .getMember(anyString());

            mockMvc.perform(get("/admin/theme")
                            .cookie(new Cookie("token", "cookieValue")))
                    .andExpect(status().isOk())
                    .andExpect(view().name("admin/theme"));
        }
    }

    @Nested
    @DisplayName("일반 사용자 접근 성공 테스트")
    class MemberRoleTest {

        @Test
        @DisplayName("일반 사용자가 접근시에 403과 에러 페이지를 반환한다.")
        void AdminPathAccessDenied() throws Exception {
            LoginMember loginMember = new LoginMember(1L, Role.MEMBER, "카키", "kaki@email.com");
            doReturn(loginMember).when(jwtTokenProvider)
                    .getMember(anyString());

            mockMvc.perform(get("/admin")
                            .cookie(new Cookie("token", "cookieValue")))
                    .andExpect(status().isForbidden())
                    .andExpect(view().name("error/403"));
        }

        @Test
        @DisplayName("일반 사용자가 접근시에 403과 에러 페이지를 반환한다.")
        void AdminReservationPathAccessDenied() throws Exception {
            LoginMember loginMember = new LoginMember(1L, Role.MEMBER, "카키", "kaki@email.com");
            doReturn(loginMember).when(jwtTokenProvider)
                    .getMember(anyString());

            mockMvc.perform(get("/admin/reservation")
                            .cookie(new Cookie("token", "cookieValue")))
                    .andExpect(status().isForbidden())
                    .andExpect(view().name("error/403"));
        }

        @Test
        @DisplayName("일반 사용자가 접근시에 403과 에러 페이지를 반환한다.")
        void AdminTimePathAccessDenied() throws Exception {
            LoginMember loginMember = new LoginMember(1L, Role.MEMBER, "카키", "kaki@email.com");
            doReturn(loginMember).when(jwtTokenProvider)
                    .getMember(anyString());

            mockMvc.perform(get("/admin/time")
                            .cookie(new Cookie("token", "cookieValue")))
                    .andExpect(status().isForbidden())
                    .andExpect(view().name("error/403"));
        }

        @Test
        @DisplayName("일반 사용자가 접근시에 403과 에러 페이지를 반환한다.")
        void AdminThemePathAccessDenied() throws Exception {
            LoginMember loginMember = new LoginMember(1L, Role.MEMBER, "카키", "kaki@email.com");
            doReturn(loginMember).when(jwtTokenProvider)
                    .getMember(anyString());

            mockMvc.perform(get("/admin/theme")
                            .cookie(new Cookie("token", "cookieValue")))
                    .andExpect(status().isForbidden())
                    .andExpect(view().name("error/403"));
        }
    }
}