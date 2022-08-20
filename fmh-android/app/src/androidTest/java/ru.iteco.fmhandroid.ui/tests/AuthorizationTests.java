package ru.iteco.fmhandroid.ui.tests;

import android.os.SystemClock;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.rule.ActivityTestRule;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import ru.iteco.fmhandroid.ui.steps.AuthorizationSteps;
import ru.iteco.fmhandroid.ui.steps.MainSteps;
import ru.iteco.fmhandroid.ui.AppActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static org.hamcrest.Matchers.allOf;

@RunWith(AllureAndroidJUnit4.class)
public class AuthorizationTests {
    AuthorizationSteps Auth = new AuthorizationSteps();
    MainSteps Main = new MainSteps();

    @Rule
    public ActivityTestRule<AppActivity> mActivityTestRule =
            new ActivityTestRule<>(AppActivity.class);

    @Before
    public void authCheck() {
        SystemClock.sleep(5000);
        try {
            Auth.isAuthorizationScreen();
        } catch (NoMatchingViewException e) {
            Main.logOut();
        }
    }

    @Test
    @DisplayName("TC-2 - Вход с валидными данными")
    public void validAuth(){
        Auth.isAuthorizationScreen();
        Auth.validAuth();
        Main.logOut();
    }

    @Test
    @DisplayName("TC-3 - Вход с незаполненным полем Логин")
    public void emptyLogin(){
        Auth.isAuthorizationScreen();
        Auth.loginInputField(" ");
        Auth.passwordInputField("password2");
        Auth.buttonClick();
        onView(allOf(withContentDescription("Login and password cannot be empty"), isDisplayed()));
    }

    @Test
    @DisplayName("TC-4 - Вход с незаполненным полем Пароль")
    public void emptyPassword(){
        Auth.isAuthorizationScreen();
        Auth.loginInputField("login2");
        Auth.passwordInputField(" ");
        Auth.buttonClick();
        onView(allOf(withContentDescription("Login and password cannot be empty"), isDisplayed()));
    }

    @Test
    @DisplayName("TC-5 - Поле Логин заполнено спецсимволами")
    public void loginWithSpecialCharacters(){
        Auth.isAuthorizationScreen();
        Auth.loginInputField("######");
        Auth.passwordInputField("password2");
        Auth.buttonClick();
        onView(allOf(withContentDescription("Wrong login or password"), isDisplayed()));
    }

    @Test
    @DisplayName("TC-6 - Поле Пароль заполнено спецсимволами")
    public void passwordWithSpecialCharacters(){
        Auth.isAuthorizationScreen();
        Auth.loginInputField("login2");
        Auth.passwordInputField("#########");
        Auth.buttonClick();
        onView(allOf(withContentDescription("Wrong login or password"), isDisplayed()));
    }

    @Test
    @DisplayName("TC-7 - Поле Логин заполнено невалидными данными")
    public void invalidLogin(){
        Auth.isAuthorizationScreen();
        Auth.loginInputField("LOGIN1");
        Auth.passwordInputField("password2");
        Auth.buttonClick();
        onView(allOf(withContentDescription("Wrong login or password"), isDisplayed()));
    }

    @Test
    @DisplayName("TC-8 - Поле Пароль заполнено невалидными данными")
    public void invalidPassword(){
        Auth.isAuthorizationScreen();
        Auth.loginInputField("login2");
        Auth.passwordInputField("PASSWORD1");
        Auth.buttonClick();
        onView(allOf(withContentDescription("Wrong login or password"), isDisplayed()));
    }

    @Test
    @DisplayName("TC-9 - Авторизация без заполнения данных")
    public void emptyAuth() {
        Auth.isAuthorizationScreen();
        Auth.buttonClick();
        onView(allOf(withContentDescription("Login and password cannot be empty"), isDisplayed()));
    }
}
