package Scenarios;

import io.qameta.allure.*;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.HomePage;
import base.BaseTest;
import pages.ShoppingListsPage;
import org.testng.annotations.Test;
import io.qameta.allure.*;
import listeners.AllureUtility;
import java.util.List;
import java.time.Duration;

@Epic("Shopping List Application")
@Feature("Shopping List Management")
public class Scenarios extends BaseTest {

    HomePage home_page;
    ShoppingListsPage shopping_page;

    @Test
    @Story("Create and manage shopping lists")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test creating a new shopping list, adding items, checking all items and archiving the list")
    public void createList1Scenario() {
        AllureUtility.addStep("Opening Shopping Lists page");
        home_page = new HomePage(driver);
        home_page.openShoppingLists();
        AllureUtility.takeScreenshot("Shopping Lists Page Opened");

        AllureUtility.addStep("Creating new shopping list");
        shopping_page = new ShoppingListsPage(driver);
        shopping_page.addNewList("My Shopping List");
        AllureUtility.takeScreenshot("New List Created");

        AllureUtility.addStep("Adding items to the list");
        shopping_page.addItemsToList();
        AllureUtility.takeScreenshot("Items Added");

        AllureUtility.addStep("Checking all items in the list");
        shopping_page.checkAllItems();
        AllureUtility.takeScreenshot("All Items Checked");

        AllureUtility.addStep("Archiving the shopping list");
        shopping_page.archiveList();
        AllureUtility.takeScreenshot("List Archived");

        AllureUtility.addStep("Verifying archive success message");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement successMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        AppiumBy.accessibilityId("List has been archived successfully.")
                )
        );

        Assert.assertTrue(successMessage.isDisplayed(), "Archive success message should be displayed");
        String actualContentDesc = successMessage.getAttribute("content-desc");
        Assert.assertEquals(actualContentDesc, "List has been archived successfully.", "Message content doesn't match");

        AllureUtility.takeScreenshot("Archive Success Message Displayed");
        AllureUtility.addStep("Waiting for undo button to disappear");
        shopping_page.waitForUndoToDisappear();
        AllureUtility.takeScreenshot("Undo Button Disappeared");
    }

    @Test
    @Story("Edit shopping list items")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test editing items in shopping list and removing items")
    public void createList2Scenario(){
        AllureUtility.addStep("Opening Shopping Lists and creating new list");
        home_page = new HomePage(driver);
        home_page.openShoppingLists();
        AllureUtility.takeScreenshot("Shopping Lists Page Opened");

        shopping_page = new ShoppingListsPage(driver);
        shopping_page.addNewList("Weekend Shopping");
        AllureUtility.takeScreenshot("Weekend Shopping List Created");
        AllureUtility.addStep("Navigating back and renaming list");
        home_page.openShoppingLists();
        shopping_page.renameList("New List Name After Edit");
        AllureUtility.takeScreenshot("List Renamed");
        String expectedListName = "New List Name After Edit";
        WebElement renamedListElement = driver.findElement(AppiumBy.xpath("(//android.view.View[contains(@content-desc, 'List')])[1]"));
        Assert.assertTrue(renamedListElement.isDisplayed(), "Renamed list should be visible");
        Assert.assertEquals(renamedListElement.getAttribute("content-desc"), expectedListName, "List name does not match expected value");

        AllureUtility.addStep("Adding items to the list");
        shopping_page.addItemsToList();
        AllureUtility.takeScreenshot("Items Added to List");

        AllureUtility.addStep("Editing random item");
        shopping_page.editRandomItem("New Item Name After Edit");
        AllureUtility.takeScreenshot("Item Edited");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement renamedItem = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        AppiumBy.xpath("//android.view.View[@text='New Item Name After Edit, No name']")
                )
        );

        // Assert the renamed item is displayed
        Assert.assertTrue(renamedItem.isDisplayed(), "Renamed item should be visible");
        AllureUtility.addStep("Removing random item");
        shopping_page.removeRandomItem();
        AllureUtility.takeScreenshot("Item Removed");
        WebElement removedMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        AppiumBy.accessibilityId("Item has been removed from the list.")
                )
        );

        // Assert the message is displayed
        Assert.assertTrue(removedMessage.isDisplayed(), "Item removed message should be displayed");
        AllureUtility.addStep("Waiting for undo button to disappear");
        shopping_page.waitForUndoToDisappear();
        AllureUtility.takeScreenshot("Undo Button Disappeared");
    }

    @Test
    @Story("Undo functionality")
    @Severity(SeverityLevel.MINOR)
    @Description("Test undo removal functionality")
    public void createList3Scenario() throws InterruptedException {
        AllureUtility.addStep("Setting up new shopping list");
        home_page = new HomePage(driver);
        home_page.openShoppingLists();
        AllureUtility.takeScreenshot("Shopping Lists Page Opened");

        shopping_page = new ShoppingListsPage(driver);
        shopping_page.addNewList("New Shopping List");
        AllureUtility.takeScreenshot("New Shopping List Created");

        AllureUtility.addStep("Adding items to list");
        shopping_page.addItemsToList();
        AllureUtility.takeScreenshot("Items Added");

        List<WebElement> itemsBefore = driver.findElements(AppiumBy.xpath("//android.view.View[contains(@content-desc, 'Item')]"));
        int countBefore = itemsBefore.size();
        AllureUtility.addStep("Removing item and testing undo");
        shopping_page.removeAllItemsWithUndo();
        AllureUtility.takeScreenshot("Item Removed");

        List<WebElement> allItems = driver.findElements(
                By.xpath("//android.view.View[contains(@text, 'Item') or contains(@content-desc, 'Item')]")
        );
        int countAfter = allItems.size();
        Assert.assertEquals(countAfter, countBefore, "Item count should remain the same after delete and undo");

    }
}