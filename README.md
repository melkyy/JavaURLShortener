# Java URL Shortener

A simple Java Spring application that submits a simple url and converts into a new url with a hashcode.

## Features

- Web application with login and register forms
- List with url shortener and the original url
- form with the original url that generates the new url shortener
- A separated Web application for validation of the url shortener and handling redirection to the original URL

## Installation

```bash
git clone https://github.com/melkyy/JavaURLShortener.git
cd JavaURLShortener
docker compose up --build
```

## Usage
The project consist of two web applications to decouple URL management and the validation of the shortened URL.

### URL Shortener Generation FrontEnd  http://localhost:5173
Vue.js application that is used to manage your URLS

### URL Shortener Generation Endpoints
There are several endpoints that manages the URLS and open the url shortened in a new tab
      - http://localhost:8081/login - User authentication
      - http://localhost:8081/register - New account creation
      - http://localhost:8081/url - URL management and generation

### URL Shortener Validator http://localhost:8082
The web application validates the hashed code and ensures that the URL has the correct format otherwise redirects to the original URL
      - http://localhost:8082/{hashedcode}


## Requirements

- Java 8 or higher

