package ru.iteco.fmhandroid.ui.tests;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;

import android.os.SystemClock;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.steps.AboutSteps;
import ru.iteco.fmhandroid.ui.steps.AuthorizationSteps;
import ru.iteco.fmhandroid.ui.steps.ClaimsSteps;
import ru.iteco.fmhandroid.ui.steps.MainSteps;
import ru.iteco.fmhandroid.ui.steps.NewsControlPanelSteps;
import ru.iteco.fmhandroid.ui.steps.NewsSteps;
import ru.iteco.fmhandroid.ui.steps.QuotesSteps;
import ru.iteco.fmhandroid.ui.AppActivity;

@RunWith(AllureAndroidJUnit4.class)

public class AllTests {
    AuthorizationSteps Auth = new AuthorizationSteps();
    AboutSteps About = new AboutSteps();
    ClaimsSteps Claims = new ClaimsSteps();
    MainSteps Main = new MainSteps();
    NewsControlPanelSteps NewsControlPanel = new NewsControlPanelSteps();
    NewsSteps News = new NewsSteps();
    QuotesSteps Quotes = new QuotesSteps();

    @Rule
    public ActivityTestRule<AppActivity> mActivityTestRule =
            new ActivityTestRule<>(AppActivity.class);

    @Before
    public void authCheck() {
        SystemClock.sleep(5000);
        try {
            Main.isMainScreen();
        } catch (NoMatchingViewException e) {
            Auth.validAuth();
        }
        SystemClock.sleep(2000);
    }
    @Test
    @DisplayName("TC-10 - Переход на вкладку ВСЕ НОВОСТИ")
    public void openAllNews(){
        Main.clickButtonAllNews();
    }

    @Test
    @DisplayName("TC-11 - Переход на вкладку ВСЕ ЗАЯВКИ")
    public void openAllClaims(){
        Main.clickButtonAllClaims();
    }

    @Test
    @DisplayName("TC-12 - Переход в меню вкладки Заявки")
    public void openMenuClaims(){
        Main.openClaims();
    }

    @Test
    @DisplayName("TC-13 - Переход в меню вкладки Новости")
    public void openMenuNews(){
        Main.openNews();
    }

    @Test
    @DisplayName("TC-14 - Переход в меню вкладки О приложении")
    public void openMenuAbout(){
        Main.openAbout();
    }

    @Test
    @DisplayName("TC-15 - Фильтрация заявок по критерию Открыта")
    public void applicationFilteringOpen(){
        Main.openClaims();
        Claims.isClaimsScreen();
        Claims.filter();
        Claims.isFilteringScreen();
        Claims.removeCheckBoxInProgress();
        Claims.applyClaims();
    }

    @Test
    @DisplayName("TC-16 - Фильтрация заявок по критерию В работе")
    public void applicationFilteringInProgress(){
        Main.openClaims();
        Claims.isClaimsScreen();
        Claims.filter();
        Claims.isFilteringScreen();
        Claims.removeCheckBoxOpen();
        Claims.applyClaims();
    }

    @Test
    @DisplayName("TC-17 - Фильтрация заявок по критерию Выполнена")
    public void applicationFilteringExecuted(){
        Main.openClaims();
        Claims.isClaimsScreen();
        Claims.filter();
        Claims.isFilteringScreen();
        Claims.executedCheck();
        Claims.applyClaims();
    }

    @Test
    @DisplayName("TC-18 - Фильтрация заявок по критерию Отмененные")
    public void applicationFilteringCancelled(){
        Main.openClaims();
        Claims.isClaimsScreen();
        Claims.filter();
        Claims.isFilteringScreen();
        Claims.cancelledCheck();
        Claims.applyClaims();
    }

    @Test
    @DisplayName("TC-19 - Открытие заявки")
    public void openClaim(){
        Main.openClaims();
        Claims.open();
        onView(withId(android.R.id.content)).perform(swipeUp());
        Claims.goBack();
        Claims.isClaimsScreen();
    }

    @Test
    @DisplayName("TC-20 - Создание Заявки")
    public void creatingNewClaim(){
        Main.openClaims();
        Claims.addNew();
        Claims.isCreatingScreen();
        Claims.enterTitle("Заголовок");
        Claims.enterExecutor("Смирнов Николай Петрович");
        Claims.enterDate("25.08.2022");
        Claims.enterTime("10:18");
        Claims.enterDescription("Описание");
        Claims.saveButton();
        SystemClock.sleep(1000);
        Claims.isClaimsScreen();
    }

    @Test
    @DisplayName("TC-21 - Создание Заявки без сохранения")
    public void creatingNewClaimWithoutSave(){
        Main.openClaims();
        Claims.addNew();
        Claims.isCreatingScreen();
        Claims.enterTitle("Заголовок");
        Claims.enterExecutor("Смирнов Николай Петрович");
        Claims.enterDate("25.08.2022");
        Claims.enterTime("10:18");
        Claims.enterDescription("Описание");
        Claims.cancelButton();
        Claims.checkPopUpOk();
        Claims.isClaimsScreen();
    }

    @Test
    @DisplayName("TC-22 - Создание Заявки без указания темы")
    public void creatingNewClaimWithoutTitle(){
        Main.openClaims();
        Claims.addNew();
        Claims.isCreatingScreen();
        Claims.enterTitle(" ");
        Claims.enterExecutor("Смирнов Николай Петрович");
        Claims.enterDate("25.08.2022");
        Claims.enterTime("10:18");
        Claims.enterDescription("Описание");
        Claims.saveButton();
        SystemClock.sleep(1000);
        Claims.checkEmpty();
    }

    @Test
    @DisplayName("TC-23 - Создание Заявки с указанием спецсимволов в качестве темы")
    public void titleFieldWithSpecialCharacters(){
        Main.openClaims();
        Claims.addNew();
        Claims.isCreatingScreen();
        Claims.enterTitle("########");
        Claims.enterExecutor("Смирнов Николай Петрович");
        Claims.enterDate("25.08.2022");
        Claims.enterTime("10:18");
        Claims.enterDescription("Описание");
        Claims.saveButton();
        SystemClock.sleep(1000);
        Claims.isClaimsScreen();
    }

    @Test
    @DisplayName("TC-24 - Создание Заявки без указания исполнителя")
    public void creatingNewClaimWithoutExecutor(){
        Main.openClaims();
        Claims.addNew();
        Claims.isCreatingScreen();
        Claims.enterTitle("Заголовок");
        Claims.enterExecutor(" ");
        Claims.enterDate("25.08.2022");
        Claims.enterTime("10:18");
        Claims.enterDescription("Описание");
        Claims.saveButton();
        SystemClock.sleep(1000);
        Claims.checkBlockComment();
    }

    @Test
    @DisplayName("TC-25 - Создание Заявки с указанием спецсимволов в качестве исполнителя")
    public void executorFieldWithSpecialCharacters(){
        Main.openClaims();
        Claims.addNew();
        Claims.isCreatingScreen();
        Claims.enterTitle("Заголовок");
        Claims.enterExecutor("############");
        Claims.enterDate("25.08.2022");
        Claims.enterTime("10:18");
        Claims.enterDescription("Описание");
        Claims.saveButton();
        SystemClock.sleep(1000);
        Claims.checkBlockComment();
    }

    @Test
    @DisplayName("TC-26 - Создание Заявки без указания даты")
    public void creatingNewClaimWithoutDate(){
        Main.openClaims();
        Claims.addNew();
        Claims.isCreatingScreen();
        Claims.enterTitle("Заголовок");
        Claims.enterExecutor("Смирнов Николай Петрович");
        Claims.enterDate(" ");
        Claims.enterTime("12:08");
        Claims.enterDescription("Описание");
        Claims.saveButton();
        SystemClock.sleep(1000);
        Claims.checkEmpty();
    }

    @Test
    @DisplayName("TC-27 - Создание Заявки без указания времени")
    public void creatingNewClaimWithoutTime(){
        Main.openClaims();
        Claims.addNew();
        Claims.isCreatingScreen();
        Claims.enterTitle("Заголовок");
        Claims.enterExecutor("Смирнов Николай Петрович");
        Claims.enterDate("25.08.2022");
        Claims.enterTime(" ");
        Claims.enterDescription("Описание");
        Claims.saveButton();
        SystemClock.sleep(1000);
        Claims.checkEmpty();
    }

    @Test
    @DisplayName("TC-29 - Создание Заявки без указания описания")
    public void creatingNewClaimWithoutDescription(){
        Main.openClaims();
        Claims.addNew();
        Claims.isCreatingScreen();
        Claims.enterTitle("Заголовок");
        Claims.enterExecutor("Смирнов Николай Петрович");
        Claims.enterDate("25.08.2022");
        Claims.enterTime("15:28 ");
        Claims.enterDescription(" ");
        Claims.saveButton();
        SystemClock.sleep(1000);
        Claims.checkEmpty();
    }

    @Test
    @DisplayName("TC-30 - Создание Заявки с указанием спецсимволов в качестве описания")
    public void descriptionFieldWithSpecialCharacters(){
        Main.openClaims();
        Claims.addNew();
        Claims.isCreatingScreen();
        Claims.enterTitle("Заголовок");
        Claims.enterExecutor("Смирнов Николай Петрович");
        Claims.enterDate("25.08.2022");
        Claims.enterTime("10:18");
        Claims.enterDescription("####");
        Claims.saveButton();
        SystemClock.sleep(1000);
        Claims.isClaimsScreen();
    }

    @Test
    @DisplayName("TC-31 - Добавление комментария к созданной заявке")
    public void addComment(){
        Main.openClaims();
        Claims.isClaimsScreen();
        Claims.filter();
        Claims.isFilteringScreen();
        Claims.removeCheckBoxInProgress();
        Claims.applyClaims();
        SystemClock.sleep(1000);
        Claims.open();
        onView(withId(android.R.id.content)).perform(swipeUp());
        SystemClock.sleep(2000);
        Claims.clickButtonAddComment();
        Claims.addToCommentField();
        Claims.saveButton();
        onView(withId(android.R.id.content)).perform(swipeUp());
        Claims.checkCommentField();
    }

    @Test
    @DisplayName("TC-32 - Редактирование комментария")
    public void editComment(){
        Main.openClaims();
        Claims.isClaimsScreen();
        Claims.filter();
        Claims.isFilteringScreen();
        Claims.removeCheckBoxInProgress();
        Claims.applyClaims();
        SystemClock.sleep(1000);
        Claims.open();
        onView(withId(android.R.id.content)).perform(swipeUp());
        SystemClock.sleep(2000);
        Claims.clickButtonAddComment();
        Claims.addToCommentField();
        Claims.saveButton();
        Claims.clickButtonEditComment();
        Claims.editCommentField();
        Claims.saveButton();
        SystemClock.sleep(1000);
        onView(withId(android.R.id.content)).perform(swipeUp());
        Claims.checkCommentField();
    }

    @Test
    @DisplayName("TC-33 - Редактирование заявки, находящейся в статусе Открыта")
    public void editClaimIfStatusOpen(){
        Main.openClaims();
        Claims.isClaimsScreen();
        Claims.filter();
        Claims.isFilteringScreen();
        Claims.removeCheckBoxInProgress();
        Claims.applyClaims();
        SystemClock.sleep(1000);
        Claims.open();
        onView(withId(android.R.id.content)).perform(swipeUp());
        SystemClock.sleep(2000);
        Claims.enterEditButton();
        Claims.enterEditTitle("Отредактированный текст заголовка");
        Claims.selectEditExecutor("Лебедев Данил Александрович");
        Claims.enterEditDate("27.08.2022");
        Claims.enterEditTime("20:18");
        Claims.enterEditDescription("Отредактированное описание");
        closeSoftKeyboard();
        Claims.saveButton();
        SystemClock.sleep(1000);
        onView(withId(android.R.id.content)).perform(swipeUp());
        Claims.goBack();
        Claims.checkBlockComment();
    }

    @Test
    @DisplayName("TC-34 - Изменение статуса заявки с Открыта на В работу")
    public void changeStatusOpenToTakeToWork(){
        Main.openClaims();
        Claims.filter();
        Claims.isFilteringScreen();
        Claims.removeCheckBoxInProgress();
        Claims.applyClaims();
        Claims.open();
        onView(withId(android.R.id.content)).perform(swipeUp());
        Claims.clickButtonSettings();
        Claims.clickButtonTakeToWork();
        onView(withId(android.R.id.content)).perform(swipeDown());
        Claims.checkClaimStatusInProgress();
    }

    //@Test
    //@DisplayName("TC-35 - Изменение статуса заявки с Открыта на Отменена")
    //public void changeStatusOpenToCancel(){
    //    Main.openClaims();
    //    Claims.filter();
    //    Claims.isFilteringScreen();
    //    Claims.removeCheckBoxInProgress();
    //    Claims.applyClaims();
    //    Claims.open();
    //    onView(withId(android.R.id.content)).perform(swipeUp());
    //    Claims.clickButtonSettings();
    //    Claims.clickButtonCancel();
    //    Claims.checkClaimStatusCanceled();
    //}

    @Test
    @DisplayName("TC-37 - Открытие и закрытие новости")
    public void openNews() {
        Main.openNews();
        News.newsExpand();
        News.checkExpandNews();
    }

    @Test
    @DisplayName("TC-38 - Сортировка новостей")
    public void sortNews() {
        Main.openNews();
        News.sortNews();
        News.isNewsScreen();
    }

    @Test
    @DisplayName("TC-39 - Фильтрация новостей по категории Объявление")
    public void filterNewsAdd() {
        Main.openNews();
        News.filterNews();
        News.isFilterScreen();
        News.selectFilterCategory("Объявление");
        News.applyNews();
        News.isNewsScreen();
    }

    @Test
    @DisplayName("TC-40 - Фильтрация новостей по категории День рождения")
    public void filterNewsBirthday() {
        Main.openNews();
        News.filterNews();
        News.isFilterScreen();
        News.selectFilterCategory("День рождения");
        News.applyNews();
        News.isNewsScreen();
    }

    @Test
    @DisplayName("TC-41 - Фильтрация новостей по категории Зарплата")
    public void filterNewsSalary() {
        Main.openNews();
        News.filterNews();
        News.isFilterScreen();
        News.selectFilterCategory("Зарплата");
        News.applyNews();
        News.isNewsScreen();
    }

    @Test
    @DisplayName("TC-42 - Фильтрация новостей по категории Профсоюз")
    public void filterNewsUnion() {
        Main.openNews();
        News.filterNews();
        News.isFilterScreen();
        News.selectFilterCategory("Профсоюз");
        News.applyNews();
        News.isNewsScreen();
    }

    @Test
    @DisplayName("TC-43 - Фильтрация новостей по категории Праздник")
    public void filterNewsHoliday() {
        Main.openNews();
        News.filterNews();
        News.isFilterScreen();
        News.selectFilterCategory("Праздник");
        News.applyNews();
        News.isNewsScreen();
    }

    @Test
    @DisplayName("TC-44 - Фильтрация новостей по категории Массаж")
    public void filterNewsMassage() {
        Main.openNews();
        News.filterNews();
        News.isFilterScreen();
        News.selectFilterCategory("Массаж");
        News.applyNews();
        News.isNewsScreen();
    }

    @Test
    @DisplayName("TC-45 - Фильтрация новостей по категории Благодарность")
    public void filterNewsGratitude() {
        Main.openNews();
        News.filterNews();
        News.isFilterScreen();
        News.selectFilterCategory("Благодарность");
        News.applyNews();
        News.isNewsScreen();
    }

    @Test
    @DisplayName("TC-46 - Фильтрация новостей по категории Нужна помощь")
    public void filterNewsHelp() {
        Main.openNews();
        News.filterNews();
        News.isFilterScreen();
        News.selectFilterCategory("Нужна помощь");
        News.applyNews();
        News.isNewsScreen();
    }

    @Test
    @DisplayName("TC-47 - Фильтрация новостей без указания категории")
    public void filterNews() {
        Main.openNews();
        News.filterNews();
        News.isFilterScreen();
        News.selectFilterCategory(" ");
        News.applyNews();
        News.isNewsScreen();
    }

    @Test
    @DisplayName("TC-48 - Фильтрация новостей без указания категории, но с выбором периода времени")
    public void filterNewsWithTimesPeriod() {
        Main.openNews();
        News.filterNews();
        News.isFilterScreen();
        News.selectFilterCategory(" ");
        News.dateStart("01.08.2022");
        News.dateEnd("20.08.2022");
        News.applyNews();
        News.isNewsScreen();
    }

    @Test
    @DisplayName("TC-49 - Просмотр новости на Панели инструментов")
    public void viewNewsInControlPanel() {
        Main.openNews();
        News.editNews();
        NewsControlPanel.expandNews();
        NewsControlPanel.checkExpandNews();
    }

    @Test
    @DisplayName("TC-50 - Сортировка новостей на Панели инструментов")
    public void sortNewsOnControlPanel() {
        Main.openNews();
        News.editNews();
        NewsControlPanel.sortNews();
        NewsControlPanel.isControlPanelScreen();
    }

    @Test
    @DisplayName("TC-51 - Фильтрация новостей по критерию Активна")
    public void filterNewsByActiveCriteria() {
        Main.openNews();
        News.editNews();
        NewsControlPanel.filterNews();
        NewsControlPanel.activeNews();
        NewsControlPanel.filterButton();
        NewsControlPanel.isControlPanelScreen();
    }

    @Test
    @DisplayName("TC-52 - Фильтрация новостей по критерию Не активна")
    public void filterNewsByNotActiveCriteria() {
        Main.openNews();
        News.editNews();
        NewsControlPanel.filterNews();
        NewsControlPanel.notActiveNews();
        NewsControlPanel.filterButton();
        NewsControlPanel.isControlPanelScreen();
    }

    @Test
    @DisplayName("TC-53 - Смена статуса новости с Активна на статус Не активна")
    public void changeStatusNewsFromActiveToNotActive() {
        Main.openNews();
        News.editNews();
        NewsControlPanel.filterNews();
        NewsControlPanel.activeNews();
        NewsControlPanel.filterButton();
        NewsControlPanel.editNews();
        NewsControlPanel.editStatus();
        NewsControlPanel.checkStatusNotActive();
        NewsControlPanel.saveButton();
        NewsControlPanel.isControlPanelScreen();
    }

    @Test
    @DisplayName("TC-54 - Смена статуса новости с Не активна на статус Активна")
    public void changeStatusNewsFromNotActiveToActive() {
        Main.openNews();
        News.editNews();
        NewsControlPanel.filterNews();
        NewsControlPanel.notActiveNews();
        NewsControlPanel.filterButton();
        NewsControlPanel.editNews();
        NewsControlPanel.editStatus();
        NewsControlPanel.checkStatusActive();
        NewsControlPanel.saveButton();
        NewsControlPanel.isControlPanelScreen();
    }

    @Test
    @DisplayName("TC-55 - Создание новости на Панеле Управления")
    public void createNewsOnControlPanel() {
        Main.openNews();
        News.editNews();
        NewsControlPanel.createNews();
        NewsControlPanel.selectCategory("Праздник");
        NewsControlPanel.enterTitle("Не забыть отметить!");
        NewsControlPanel.enterDate("26.08.2022");
        NewsControlPanel.enterTime("17:18");
        NewsControlPanel.enterDescription("Описание");
        closeSoftKeyboard();
        NewsControlPanel.saveButton();
        NewsControlPanel.isControlPanelScreen();
    }

    @Test
    @DisplayName("TC-56 - Создание новости без указания категории")
    public void createNewsWithEmptyCategory() {
        Main.openNews();
        News.editNews();
        NewsControlPanel.createNews();
        NewsControlPanel.selectCategory(" ");
        NewsControlPanel.enterTitle("Заголовок");
        NewsControlPanel.enterDate("26.08.2022");
        NewsControlPanel.enterTime("19:18");
        NewsControlPanel.enterDescription("Описание");
        closeSoftKeyboard();
        NewsControlPanel.saveButton();
        SystemClock.sleep(1000);
        onView(allOf(withContentDescription("Fill empty fields"), isDisplayed()));
    }

    @Test
    @DisplayName("TC-57 - Создание новости с указанием произвольной категории")
    public void createNewsWithOwnCategory() {
        Main.openNews();
        News.editNews();
        NewsControlPanel.createNews();
        NewsControlPanel.selectCategory("Категория");
        NewsControlPanel.enterTitle("Заголовок");
        NewsControlPanel.enterDate("26.08.2022");
        NewsControlPanel.enterTime("19:18");
        NewsControlPanel.enterDescription("Описание");
        closeSoftKeyboard();
        NewsControlPanel.saveButton();
        SystemClock.sleep(1000);
        onView(allOf(withContentDescription("Saving failed. Try again later"), isDisplayed()));
    }

    @Test
    @DisplayName("TC-58 - Создание новости без указания заголовка")
    public void createNewsWithoutTitle() {
        Main.openNews();
        News.editNews();
        NewsControlPanel.createNews();
        NewsControlPanel.selectCategory("Зарплата");
        NewsControlPanel.enterEditTitle(" ");
        NewsControlPanel.enterDate("26.08.2022");
        NewsControlPanel.enterTime("19:18");
        NewsControlPanel.enterDescription("Описание");
        closeSoftKeyboard();
        NewsControlPanel.saveButton();
        SystemClock.sleep(1000);
        onView(allOf(withContentDescription("Fill empty fields"), isDisplayed()));
        NewsControlPanel.cancelButton();
        NewsControlPanel.checkPopUpOk();
        NewsControlPanel.isControlPanelScreen();
    }

    @Test
    @DisplayName("TC-59 - Создание новости без указания даты публикации")
    public void createNewsWithoutDate() {
        Main.openNews();
        News.editNews();
        NewsControlPanel.isControlPanelScreen();
        NewsControlPanel.createNews();
        NewsControlPanel.selectCategory("Зарплата");
        NewsControlPanel.enterDate(" ");
        NewsControlPanel.enterTime("19:18");
        NewsControlPanel.enterDescription("Описание");
        closeSoftKeyboard();
        NewsControlPanel.saveButton();
        SystemClock.sleep(1000);
        onView(allOf(withContentDescription("Fill empty fields"), isDisplayed()));
        NewsControlPanel.cancelButton();
        NewsControlPanel.checkPopUpOk();
        NewsControlPanel.isControlPanelScreen();
    }

    @Test
    @DisplayName("TC-60 - Создание новости без указания времени")
    public void createNewsWithoutTime() {
        Main.openNews();
        News.editNews();
        NewsControlPanel.isControlPanelScreen();
        NewsControlPanel.createNews();
        NewsControlPanel.selectCategory("Зарплата");
        NewsControlPanel.enterDate("26.08.2022 ");
        NewsControlPanel.enterTime(" ");
        NewsControlPanel.enterDescription("Описание");
        closeSoftKeyboard();
        NewsControlPanel.saveButton();
        SystemClock.sleep(1000);
        onView(allOf(withContentDescription("Fill empty fields"), isDisplayed()));
        NewsControlPanel.cancelButton();
        NewsControlPanel.checkPopUpOk();
        NewsControlPanel.isControlPanelScreen();
    }

    @Test
    @DisplayName("TC-62 - Создание новости без указания описания")
    public void createNewsWithoutDescription() {
        Main.openNews();
        News.editNews();
        NewsControlPanel.isControlPanelScreen();
        NewsControlPanel.createNews();
        NewsControlPanel.selectCategory("Зарплата");
        NewsControlPanel.enterDate("26.08.2022 ");
        NewsControlPanel.enterTime("13:28 ");
        NewsControlPanel.enterDescription(" ");
        closeSoftKeyboard();
        NewsControlPanel.saveButton();
        SystemClock.sleep(1000);
        onView(allOf(withContentDescription("Fill empty fields"), isDisplayed()));
        NewsControlPanel.cancelButton();
        NewsControlPanel.checkPopUpOk();
        NewsControlPanel.isControlPanelScreen();
    }

    @Test
    @DisplayName("TC-63 - Удаление новости")
    public void deleteNewsOnControlPanel() {
        Main.openNews();
        News.editNews();
        NewsControlPanel.deleteNews();
        NewsControlPanel.checkPopUpOk();
        NewsControlPanel.isControlPanelScreen();
    }

    @Test
    @DisplayName("TC-64 - Редактирование новости  ")
    public void editNewsOnControlPanel() {
        Main.openNews();
        News.editNews();
        NewsControlPanel.editNews();
        NewsControlPanel.enterEditTitle("Новый заголовок");
        closeSoftKeyboard();
        NewsControlPanel.saveButton();
        SystemClock.sleep(1000);
        NewsControlPanel.isControlPanelScreen();
    }

    @Test
    @DisplayName("TC-65 - Развернуть/свернуть тематическую цитату")
    public void expandThematicQuote(){
        Quotes.openQuotes();
        Quotes.quotesExpand();
        SystemClock.sleep(1000);
        Quotes.quotesRollUp();
    }

    @Test
    @DisplayName("TC-66 - Просмотр ссылки Политика конфиденциальности")
    public void transitionToPrivacyPolicy(){
        Main.openAbout();
        About.isAboutScreen();
        About.checkPrivacy();
        About.backButton();
    }

    @Test
    @DisplayName("TC-67 - Просмотр ссылки Пользовательское соглашение")
    public void transitionToTermsOfUse(){
        Main.openAbout();
        About.isAboutScreen();
        About.checkTerms();
        About.backButton();
    }
}
