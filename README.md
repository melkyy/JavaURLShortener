# Java URL Shortener

A simple Java Spring application that submits a simple url and converts into a new url with a hashcode.

## Features

- Web application with login and register forms
- List with url shortener and the original url
- form with the original url that generates the new url shortener
- A separated Web application for validation of the url shortener and handling redirection to the original URL

## Installation

```bash
git clone [<repository-url>](https://github.com/melkyy/JavaURLShortener.git)
cd JavaURLShortener
docker compose up --build
```

## Usage
The project contains two web applications to separate the managing of url and the validation of the shortened url it has two endpoints:

### uRL Shortener Generation FrontEnd
Vue.js application that is used to manage your URLS

### uRL Shortener Generation Endpoints
There are several endpoints that manages the URLS and open the url shortened in a new tab
      http://localhost:8081/login
      http://localhost:8081/register
      http://localhost:8081/url

### uRL Shortener Validator http://localhost:8082
The web application validates the hashed code and ensures that the URL has the correct format
      http://localhost:8082/{hashed code}


## Requirements

- Java 8 or higher
- [Other dependencies]

