# ask2_4968

This repository contains coursework implementations exploring REST services, Spring Boot, Jersey, XML parsing, and communication with external APIs.

The project is organized as several independent examples. Each folder demonstrates a different part of the assignment and may need its own build configuration before it can be run as a standalone application.

## Project structure

### `Jersey_i`

A small Jersey/JAX-RS REST API for unit conversion.

It exposes the `/api/convert` resource with endpoints for:

- Inches to centimeters: `/inch_to_cm?number=...`
- Centimeters to inches: `/cm_to_inch?number=...`

`ApplicationConfig.java` configures the `/api` application path, while `Convertion.java` implements the conversion endpoints.

### `Jersey_ii`

A Jersey client that calls the OpenWeatherMap API.

The application asks the user for a city, requests the current weather in XML format, and prints a more readable version of the response.

`Weather_Client.java` contains the client logic and `ApplicationConfig.java` contains the Jersey application configuration.

### `Springboot_i`

A Spring Boot REST service for querying world geography data.

The application includes controllers for:

- Country information such as name, population, capital, area, and political system
- River information such as countries crossed and outfall location

`GeoService.java` parses a Mondial XML data file at startup and stores country and river information in memory. The application expects the relevant `xmldata_mondial-3.0.xml` resource to be available on the classpath.

### `Spingboot_ii`

A Spring Boot web application for annotating text with DBpedia Spotlight.

The application provides a simple HTML form where users can submit text. It sends the text to the DBpedia Spotlight API, parses the returned JSON, and displays recognized entities as links.

The folder name is kept as it exists in the original project: `Spingboot_ii`.

### `XML_parsing`

A standalone Java XML parsing example using DOM and XPath.

`Parser.java` reads Mondial XML data, explores the document structure, finds European countries, identifies countries spanning Europe and Asia, and exports selected country data using Gson.

### `Report.pdf`

The assignment report and supporting documentation.

## Technologies

- Java
- Spring Boot
- Jersey / JAX-RS
- XML DOM and XPath parsing
- Gson and Jackson
- Thymeleaf
- DBpedia Spotlight API
- OpenWeatherMap API

## Project status

This repository is an academic/coursework project. The folders are currently source collections rather than a single fully configured multi-module build. Maven or Gradle configuration, dependency declarations, tests, and deployment settings may need to be added or adjusted for each application before production use.

## Important security note

The OpenWeatherMap key is not hard-coded in `Jersey_ii/Weather_Client.java`; it is loaded from local configuration. If the previous key has already been used publicly, revoke it and generate a replacement. Store secrets in environment variables or a local configuration file that is excluded through `.gitignore`.

The local `.env` file contains the OpenWeatherMap key and is excluded from Git. To configure another machine, copy `.env.example` to `.env` and add a valid key:

```powershell
Copy-Item .env.example .env
```

The weather client first checks the `OPENWEATHER_API_KEY` environment variable and then looks for `.env` in the project folder or its parent folder. Do not commit `.env` or share its contents.

## Creating a new Git repository

Run these commands from the `ask2_4968` folder:

```powershell
cd C:\Users\30697\Documents\csdcode\AWS\ask2_4968
git init
git add .
git commit -m "Initial commit"
git branch -M main
git remote add origin https://github.com/<YOUR_USERNAME>/ask2_4968.git
git push -u origin main
```

Before `git add .`, make sure secrets, IDE files, compiled output, and local environments are ignored. A basic `.gitignore` should normally include:

```gitignore
.venv/
target/
build/
out/
*.class
.idea/
*.iml
.env
```

If the GitHub repository already exists and contains an initial README or other commit, use `git pull --rebase origin main` before pushing, or create the remote repository without initializing it.

