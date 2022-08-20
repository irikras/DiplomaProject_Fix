package ru.iteco.fmhandroid.ui.tests;

import androidx.test.rule.ActivityTestRule;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.steps.LoadingSteps;

@RunWith(AllureAndroidJUnit4.class)
public class LoadingTest {
    LoadingSteps Loading = new LoadingSteps();

    @Rule
    public ActivityTestRule<AppActivity> mActivityTestRule =
            new ActivityTestRule<>(AppActivity.class);

    @Test
    @DisplayName("Проверить элементы загрузки")
    public void loadingElements() {
        Loading.checkLoading();
    }
}
