# Nxt-solution - Mobile Test Automation

Automated test suite for mobile list management application using Java, Appium, and TestNG following Page Object Model (POM) design pattern.

## Test Scenarios

The automation framework covers the following test scenarios:

**List 1:**
- Create list with random items
- Mark all items as completed
- Archive the list

**List 2:**
- Create list with random items
- Rename the list
- Edit random item
- Remove random item

**List 3:**
- Create list with random items
- Remove all items
- Undo the removal

## Tech Stack

- **Java** - Programming language
- **Appium** - Mobile automation framework
- **TestNG** - Testing framework
- **Maven** - Build management
- **Page Object Model** - Design pattern
- **Allure Reports** - Advanced test reporting and analytics

## Prerequisites

Before setting up the project, ensure you have the following installed:

- **Java JDK 11+**
- **Maven 3.6+**
- **Node.js 14+**
- **Allure Command Line Tool**
- **Appium Server 2.0+**
- **Android Studio** (for Android testing)
- **Xcode** (for iOS testing - macOS only)

### Mobile Setup Requirements

**For Android:**
- Android SDK
- Android emulator or real device
- ADB (Android Debug Bridge)

**For iOS:**
- Xcode and iOS Simulator
- ios-deploy (for real device testing)

## Installation

### 1. Clone the Repository

```bash
git clone https://github.com/OmarSenger/Nxt-solution.git
cd Nxt-solution
```

### 2. Install Dependencies

```bash
mvn clean install
```

### 3. Install Appium and Allure

```bash
# Install Appium
npm install -g appium
npm install -g @appium/doctor

# Install Allure Command Line
npm install -g allure-commandline

# Install platform drivers
appium driver install uiautomator2  # For Android
appium driver install xcuitest      # For iOS
```

### 4. Verify Setup

```bash
appium-doctor --android  # Check Android setup
appium-doctor --ios      # Check iOS setup (macOS only)
allure --version         # Verify Allure installation
```

### 5. Configuration Setup

1. Copy the configuration template:
   ```bash
   cp src/test/resources/config.properties.example src/test/resources/config.properties
   ```

2. Update `config.properties` with your device details:
   ```properties
   # Appium Server
   appium.server.url=http://127.0.0.1:4723
   
   # Android Configuration
   android.platform.name=Android
   android.platform.version=13.0
   android.device.name=Pixel_6_API_33
   android.app.path=src/test/resources/apps/ListApp.apk
   android.automation.name=UiAutomator2
   
   # iOS Configuration
   ios.platform.name=iOS
   ios.platform.version=16.0
   ios.device.name=iPhone 14
   ios.app.path=src/test/resources/apps/ListApp.app
   ios.automation.name=XCUITest
   
   # Test Configuration
   implicit.wait=10
   explicit.wait=15
   screenshot.path=allure-results/screenshots/
   
   # Allure Configuration
   allure.results.directory=target/allure-results
   allure.report.directory=target/allure-reports
   ```

## Usage

### Start Appium Server

```bash
appium server --port 4723
```

### Running Tests

**Run all tests:**
```bash
mvn clean test
```

**Run specific test suite:**
```bash
mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testng.xml
```

**Run tests for specific platform:**
```bash
# Android
mvn clean test -Dplatform=android

# iOS
mvn clean test -Dplatform=ios
```

**Run specific test class:**
```bash
mvn clean test -Dtest=List1Tests
```

**Run with specific device:**
```bash
mvn clean test -Ddevice.name="Pixel_6_API_33"
```

## Running Tests

### Prerequisites for Testing

1. **Start Appium Server:**
   ```bash
   appium server --port 4723
   ```

2. **Ensure device/emulator is running:**
   ```bash
   # Check connected devices
   adb devices
   
   # Start Android emulator
   emulator -avd Pixel_6_API_33
   ```

### Test Execution Commands

**Execute Complete Test Suite:**
```bash
mvn clean test
```

**Generate and View Allure Report:**
```bash
# Run tests and generate Allure results
mvn clean test

# Generate and serve Allure report
allure serve target/allure-results
```

**Run Tests with Parallel Execution:**
```bash
mvn clean test -Dparallel=methods -DthreadCount=3
```

**Run Tests with Custom TestNG XML:**
```bash
mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/regression-suite.xml
```

**Run Specific Test Groups:**
```bash
# Smoke tests only
mvn clean test -Dgroups=smoke

# Regression tests
mvn clean test -Dgroups=regression

# Exclude groups
mvn clean test -DexcludedGroups=slow
```

**Debug Mode Execution:**
```bash
mvn clean test -Dmaven.surefire.debug
```

**Run Tests with System Properties:**
```bash
mvn clean test -Dplatform=android -Ddevice.name="Pixel_7" -Dapp.path="/path/to/app.apk"
```

### Test Reports Generation

**Generate Allure Reports After Test Execution:**
```bash
# Generate static Allure report
allure generate target/allure-results --clean -o target/allure-reports

# Open static report in browser
allure open target/allure-reports

# Or serve report with auto-refresh
allure serve target/allure-results
```

**TestNG Default Reports:**
Test reports are automatically generated in `target/surefire-reports/`

### Continuous Integration

**GitHub Actions Example:**
```bash
# This runs automatically on push/PR
# Check .github/workflows/test.yml for configuration
```

**Jenkins Integration:**
```bash
# Pipeline script available in Jenkinsfile
mvn clean test -Dbrowser=chrome -Denvironment=staging
```

## Allure Test Reporting

This project uses Allure Framework for comprehensive test reporting with rich visualizations and detailed analytics.

### Allure Features
- **Test Results Overview** - Summary dashboard with pass/fail statistics
- **Test Suites & Categories** - Organized test execution results  
- **Timeline View** - Test execution timeline and duration analysis
- **Graphs & Charts** - Visual representation of test trends
- **Attachments** - Screenshots, logs, and videos attached to test steps
- **Retries & Flaky Tests** - Track unstable test behavior
- **Environment Info** - Capture test environment details

### Allure Annotations Used

The framework leverages Allure annotations for enhanced reporting:

```java
@Epic("List Management")
@Feature("List Operations")  
@Story("Create and Manage Lists")
@Severity(SeverityLevel.CRITICAL)
@Description("Test to verify list creation with random items")
@Step("Create new list with {itemCount} random items")
public void createListWithRandomItems(int itemCount) {
    // Test implementation
}
```

### Allure Report Generation

**After Test Execution:**
```bash
# Method 1: Generate and serve report (recommended)
allure serve target/allure-results

# Method 2: Generate static report
allure generate target/allure-results --clean -o target/allure-reports
allure open target/allure-reports

# Method 3: Generate report with custom configuration
allure generate target/allure-results --config allure.properties
```

### Allure Configuration

**Maven Dependencies (already configured):**
```xml
<dependency>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-testng</artifactId>
    <version>2.24.0</version>
</dependency>
```

**allure.properties:**
```properties
allure.results.directory=target/allure-results
allure.link.issue.pattern=https://jira.company.com/browse/{}
allure.link.tms.pattern=https://testmanagement.company.com/test/{}
```

### Continuous Integration with Allure

**GitHub Actions Integration:**
```yaml
- name: Generate Allure Report
  uses: simple-elf/allure-report-action@master
  if: always()
  with:
    allure_results: target/allure-results
    allure_history: allure-history
```

### Allure Report Features in This Project

1. **Screenshot Attachments** - Automatic screenshot capture on test failures
2. **Step-by-Step Execution** - Detailed test steps with @Step annotations  
3. **Test Categories** - Tests organized by Epic, Feature, and Story
4. **Environment Information** - Device details, OS version, app version
5. **Execution Timeline** - Visual timeline of test execution
6. **Retry Tracking** - Failed test retry attempts and patterns
7. **Custom Labels** - Platform, device type, test severity labels

## Architecture & Framework Components

### Base Classes
- **BaseTest.java** - Common setup/teardown methods and driver initialization
- **BasePage.java** - Common page methods and element interactions
- **DriverManager.java** - Manages Appium driver lifecycle and capabilities

### Page Object Model (POM)
- Separate page classes for each screen
- Encapsulated element locators and actions
- Reusable methods across test classes

### Utility Classes
- **WaitUtils.java** - Custom explicit waits and element availability checks
- **ScreenshotUtils.java** - Screenshot capture for test failures and evidence
- **ConfigReader.java** - Properties file reader for configuration management
- **TestDataGenerator.java** - Random data generation for test scenarios

### Test Validation
- **TestNG Assertions** - Built-in assertion methods
- **AssertJ** - Fluent assertion library for enhanced readability
- Custom validation methods in utility classes

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/new-test-scenario`)
3. Commit your changes (`git commit -am 'Add new test scenario'`)
4. Push to the branch (`git push origin feature/new-test-scenario`)
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contact

**Omar Senger**
- GitHub: [@OmarSenger](https://github.com/OmarSenger)
- Repository: [Nxt-solution](https://github.com/OmarSenger/Nxt-solution)
