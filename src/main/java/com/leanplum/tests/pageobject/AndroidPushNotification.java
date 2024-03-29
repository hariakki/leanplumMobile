package com.leanplum.tests.pageobject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.leanplum.tests.helpers.MobileDriverUtils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class AndroidPushNotification {

    private static final String PUSH_NOTIFICATION_MESSAGE_XPATH = "//*[contains(@resource-id,'text') and contains(@text,'%s')]";
    private static final String PUSH_NOTIFICATION_BY_MESSAGE_XPATH = PUSH_NOTIFICATION_MESSAGE_XPATH
            + "/ancestor::*[@resource-id='android:id/status_bar_latest_event_content']";
    private static final String PUSH_NOTIFICATION_EXPAND_BUTTON_XPATH = PUSH_NOTIFICATION_BY_MESSAGE_XPATH
            + "//*[@resource-id='android:id/expand_button']";
    private static final String PICTURE_IN_PUSH_NOTIFICATION_XPATH = PUSH_NOTIFICATION_BY_MESSAGE_XPATH
            + "//*[@resource-id='android:id/big_picture']";

    private AndroidDriver<MobileElement> driver;
    private String message;

    /**
     * Push notification identified by message since several push notifications from same app can be displayed
     * @param driver
     * @param message
     */
    public AndroidPushNotification(AndroidDriver<MobileElement> driver, String message) {
        this.driver = driver;
        this.message = message;
    }

    public void expand() {
        String formattedExpandButtonXpath = String.format(PUSH_NOTIFICATION_EXPAND_BUTTON_XPATH, message);

        MobileElement expandButton = driver.findElement(By.xpath(formattedExpandButtonXpath));

        if (expandButton.getAttribute("content-desc").equals("Expand")) {
            expandButton.click();
        }
    }

    public boolean doesContainImage() {
        expand();
        String pictureInPushNotificationFormattedXpath = String.format(PICTURE_IN_PUSH_NOTIFICATION_XPATH, message);
        return MobileDriverUtils.doesSelectorMatchAnyElements(driver, pictureInPushNotificationFormattedXpath);
    }

    public void waitForPresence() {
        String formattedNotificationXpath = String.format(PUSH_NOTIFICATION_MESSAGE_XPATH, message);

        MobileDriverUtils.waitForExpectedCondition(driver, 300,
                ExpectedConditions.visibilityOfElementLocated(By.xpath(formattedNotificationXpath)));
    }

    public boolean confirmAbsence() {
        try {
            MobileDriverUtils.waitForExpectedCondition(driver, 10, ExpectedConditions
                    .visibilityOfElementLocated(By.xpath(String.format(PUSH_NOTIFICATION_MESSAGE_XPATH, message))));
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    public void open() {
        MobileDriverUtils
                .waitForExpectedCondition(driver, ExpectedConditions
                        .visibilityOfElementLocated(By.xpath(String.format(PUSH_NOTIFICATION_MESSAGE_XPATH, message))))
                .click();
    }
}
