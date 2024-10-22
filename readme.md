# Running the application
(I will assume docker is set up locally)
## Ensure a postgres instance is running locally 
```
docker run -e POSGRES_PASSWORD=password -e POSTGRES_DB=urlshortener -p 5432:5432 postgres
```
## Running urlshortener app
```gradle boot run```
(or right click UrlShortenerApplication and run it)


# Example requests
## Shortening an url
(please ensure that your url starts with either http:// or https:// otherwise redirect wont work)
```
curl --location 'localhost:8080/api/urls' \
--header 'Content-Type: application/json' \
--data '{
    "url": "https://youtube.com"
}'
```

## Retrieving shortened url 
```
curl --location --request GET 'localhost:8080/api/urls/MhayN28a' 
```
Set MhayN28a to the shortId you got when creating your short url.

## Using redirect
Open localhost:8080/api/urls/MhayN28a in your browser. Replace MhayN28a with your own shortId