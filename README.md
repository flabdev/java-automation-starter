# Automation Framework for Web, API and DB
## Prerequisites
JAVA 11 or 17
Eclipse or IntelliJ IDEA


## Clone the project
      git clone https://github.com/flabdev/java-automation-starter.git

## Steps to run the tests.
    
    1. Import the Project to Eclipse or IntelliJ IDEA
  
    2. Go to Config folder and update the Environment Property files with URLs(QA, DEV, STAGE and PROD).
  
    3. Go to config.properties and update the required configuration for each key.
  
    4. Design the Pageobjects for the application(src\test\java\web\pageobjects)
  
    5. Design the Tests for the applications(\src\test\java\web\tests)
  
    6. Run the tests using UI.xml under the Project directory
  
  ## Command Line Execution
      mvn clean test -Dtarget=web -DrunMode=local -Dbrowser=chrome -Dheadless=false -Denvironment=qa
