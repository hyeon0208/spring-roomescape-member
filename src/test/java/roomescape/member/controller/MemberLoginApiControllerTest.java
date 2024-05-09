package roomescape.member.controller;

import static roomescape.config.LoginArgumentResolver.COOKIE_TOKEN_KEY;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import roomescape.member.dto.MemberLoginRequest;
import roomescape.member.dto.MemberSignUpRequest;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
class MemberLoginApiControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("회원 가입 후 로그인에 성공하면 200 응답을 받고 쿠키가 존재하는지 확인하고 로그인 체크 한다.")
    @Test
    void loginAndCheck() {
        String name = "카키";
        String email = "kaki@email.com";
        String password = "1234";
        MemberSignUpRequest memberSignUpRequest = new MemberSignUpRequest(name, email, password);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(memberSignUpRequest)
                .when().post("/members")
                .then().log().all()
                .statusCode(201);

        MemberLoginRequest memberLoginRequest = new MemberLoginRequest(email, password);
        Response response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(memberLoginRequest)
                .when().post("/login")
                .thenReturn();

        String cookie = String.valueOf(response.getDetailedCookie(COOKIE_TOKEN_KEY));
        RestAssured.given().log().all()
                .cookie(cookie)
                .when().get("/login/check")
                .then().log().all()
                .statusCode(200);
    }
}
