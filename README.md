# Software testing + debugging
Since there was no documentation provided by the developers, here are the steps I took to try to get the app running:
1. Open the project in Intellij.
2. Install MySQL server and create a local database and user (remember to give user db priviledges) for the app.
3. Edit `src/main/resources/application.properties` to match the config used in step 2.
4. Run `src/main/java/com/example/IMS/ImsApApplication.java` to start the Spring Boot app.
5. Visit [http://localhost:8086/](http://localhost:8086/) to see it in a browser.

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
