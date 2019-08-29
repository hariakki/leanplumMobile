package com.leanplum.tests.pageobject.inapp;

import java.time.Duration;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.google.common.collect.ImmutableMap;
import com.leanplum.tests.helpers.MobileDriverUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class BannerPO extends InAppPopupPO {

    private static final String ANDROID_BANNER_XPATH = "//*[@resource-id='title']/ancestor::*[@class='android.view.View']";
    private static final String ANDROID_BANNER_CLOSE_BUTTON_XPATH = ANDROID_BANNER_XPATH
            + "/*[@resource-id='close-button']";

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = ANDROID_BANNER_XPATH)
    public MobileElement banner;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = ANDROID_BANNER_CLOSE_BUTTON_XPATH)
    public MobileElement bannerCloseButton;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = ANDROID_BANNER_XPATH + "/*[@resource-id='title']")
    public MobileElement bannerTitleElement;

    // @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = ANDROID_BANNER_XPATH + "/*[@resource-id='message']")
    public MobileElement bannerMessageElement;

    AppiumDriver<MobileElement> driver;

    public BannerPO(AppiumDriver<MobileElement> driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(5)), this);
    }

    public boolean verifyBannerLayout(String bannerTitle, String bannerMessage) {
        MobileDriverUtils.waitForExpectedCondition(driver, ExpectedConditions.visibilityOf(banner));

        return MobileDriverUtils.doesSelectorMatchAnyElements(driver, ANDROID_BANNER_CLOSE_BUTTON_XPATH)
                && verifyInAppPopup(ImmutableMap.of(bannerTitleElement, bannerTitle, bannerMessageElement, bannerMessage));
    }
    
    public void clickOnBanner() {
        banner.click();
    }
    
    public void clickOnCloseBanner() {
        bannerCloseButton.click();
    }
}
