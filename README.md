# Software testing + debugging

[Link to the GitHub repository for this Readme](https://github.com/njiang7/Inventory-Management-System-Spring-Boot)

Since there was no documentation provided by the developers, here are the steps I took to try to get the app running:
1. Clone from github and import project into IntelliJ.
2. Download and install MySQL server from [here](https://dev.mysql.com/downloads/mysql/).
3. Create a local database by following these steps in Terminal, [link for more details](https://spring.io/guides/gs/accessing-data-mysql/):
-  `$ sudo mysql --password`
-  `create database mydb;`
-  `create user 'springuser'@'%' identified by 'password';`
-  `grant all on mydb.* to 'springuser'@'%';`
4. Run `src/main/java/com/example/IMS/ImsApApplication.java` to start the Spring Boot app.
9. Visit [http://localhost:8086/](http://localhost:8086/) to see it in a browser.

How to run tests:
1. All of the unit tests can be run in IntelliJ normally.
2. To generate a Jacoco report, run the test command in Maven either through the GUI sidebar (underneath Lifecycle) in Intellij or `mvn test` in Terminal. The report should be generated as an html file under `/target/site/jacoco/index.html`. I have also uploaded a pregenerated report in this repository. 
3. To run the GUI tests, clone the Selenium project from this [repository](https://github.com/njiang7/seleniumTests) and import into IntelliJ.

[Presentation Slides](https://docs.google.com/presentation/d/19hat_Rq2T6M-MDNm6B_IVFE0hMuOp1OfI8BYF5ReItI/edit?usp=sharing)

## Additional comments:
- Although in our proposal we aimed only for 100% line coverage, we were also able to acheive very high branch coverage (95%) with our tests and we ended up with 98% line coverage, with a few lines missing because they were unreachable.
- We originally planned to only conduct GUI testing in addition to unit tests, but because Spring Boot applications are structured such that many classes use services from others due to the MVC design pattern, we also made use of test doubles and stubbing in our unit tests to be able to test methods.
- We based our GUI tests on the video shown below that was created by the apps developers. However, we found that the version of the app in this repository was lacking much of functionality in the video, which reduced the amount of GUI testing we could actually achieve.
- There was not a lot of documentation in the code, so most of the black box testing ended up being more like grey box testing. We still tried to employ black box testing techniques such as equivalence partioning, boundary value analysis, and error guessing whenever we could. 
- We found 11 faults in the code, and they are discussed in the comments in the test files. 
- Although we believe we conducted systematic and thorough testing, the lack of documentation and specifications for this app leads us to conclude that the app still has faults we did not find and is very much a work in progress, as shown by the GUI testing and actually trying to use the app.

(end of Readme for STAD)
--- 
# Inventory Management System 

Organizations use management systems to regularize their tasks. They can be simple or complex depending on the needs of the organization. An inventory management system (or inventory system) is the process by which you track your goods throughout your entire supply chain, from purchasing to production to end sales. It governs how you approach inventory management for your business.

The Web-based Inventory Management System will attempt to automate and replace the traditional paper based approach for inventory management and tracking which is being used in the Police Department. The paper based approach is quite tedious and it results in a lot of time wastage. Records would be created for each transaction and would serve as a central database where looking for a single record would take a few seconds. This paperless system of management would increase the efficiency, decrease the complexity and provide flexibility to the organization.

## Entity Relationship Diagram

The ERD of the project was developed based on the functional requirements and is shown below:

<p align="center">
<img src="/Resources/IMS-ERD.PNG">
</p>

## Project Prototype Demonstration

A prototype of the project was developed to identify the interfaces and to develop navigation between different screens. A demonstration is shown in the video below: 

https://user-images.githubusercontent.com/57248707/122088515-38f7bd00-ce1f-11eb-8bc7-3bdb428f7858.mp4

## Tech Stack

* Spring Boot
* Thymeleaf
* MySQL
* Hibernate

## Project Status: In-Progress 🚧
