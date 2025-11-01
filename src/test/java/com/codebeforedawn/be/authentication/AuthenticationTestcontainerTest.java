package com.codebeforedawn.be.authentication;

import com.codebeforedawn.be.TestcontainerTestsBase;
import com.codebeforedawn.be.authentication.dto.RegisterRequest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class AuthenticationTestcontainerTest extends TestcontainerTestsBase {

    private static final String TEST_EMAIL = "test@test.com";
    private static final String TEST_PASSWORD = "Test1234!";

    @Test
    void canRegisterUser() {
        RegisterRequest request = new RegisterRequest(
                TEST_EMAIL,
                TEST_PASSWORD
        );
        given()
                .contentType("application/json")
                .body(request)
                .when()
                .post("/api/auth/register")
                .then()
                .statusCode(200);
    }

    @Test
    void canNotRegisterUserThatIsAlaredayRegistered() {
        RegisterRequest request = new RegisterRequest(
                TEST_EMAIL,
                TEST_PASSWORD
        );
        given()
                .contentType("application/json")
                .body(request)
                .when()
                .post("/api/auth/register")
                .then()
                .statusCode(200);

        given()
                .contentType("application/json")
                .body(request)
                .when()
                .post("/api/auth/register")
                .then()
                .statusCode(403);
    }

    @Test
    void canLoginUser() {
        RegisterRequest registerRequest = new RegisterRequest(
                TEST_EMAIL,
                TEST_PASSWORD
        );
        given()
                .contentType("application/json")
                .body(registerRequest)
                .when()
                .post("/api/auth/register")
                .then()
                .statusCode(200);

        var loginRequest = new com.codebeforedawn.be.authentication.dto.LoginRequest(
                TEST_EMAIL,
                TEST_PASSWORD
        );

        given()
                .contentType("application/json")
                .body(loginRequest)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(200);
    }
}
