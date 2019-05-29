# pcshop

REST API for PC parts webshop.
<br>

## URI
```
localhost:8080/pcshop/api
```

## Available resources

HTTP verb | URI | description
---- | ---- | ----
GET | /products | get list of all products
GET | /products/1 | get product with id 1
POST | /products | add new product
PUT | /products/1 | update product with id 1
GET | /products/products/price?min=_VALUE_&max=_VALUE_ | get products within price range
GET | /products/name/_NAME_ | get products matching _%NAME%_
GET | /products/category/_CATEGORY_ | get products from category
POST | /products/1/comments | add new comment to product of id 1
GET | /products/1/comments | get list of all comments to product of id 1
DELETE | /products/1/comments/1 | delete comment 1 from product of id 1

### notes

GET by name matches %string% and is converted to UPPERCASE so it's not case sensitive.  
GET by category is case sensitive and must be UPPERCASE to match.  
the price of product can be decimal, GET_byPrice works with it too.

## Data models

* Product
    + category: Enum
        - MOTHERBOARD
        - GRAPHICSCARD
        - HARDDISK
        - RAM
    + name: String
    + price: Double
    + comments: List<Comment>

* Comment
    + content: String

## JSON examples

product(POST)
```json
{
    "name": "ASUS GTX 1050 Ti",
    "price": 745,
    "category": "GRAPHICSCARD"
}
```

comment(POST)
```json
{
    "content": "it is a comment"
}
```

product(PUT)
```json
{
    "price": 777
}
```

## Test Objects

As previously I'm using my utility [crudster](https://github.com/sdnkr/crudster) I wrote for testing my REST APIs.  
I'm not providing Postman compatible files, because I don't use it.

In a directory **requests**, there are request samples.  
They are put in logical structure so it is easy to find what we want to test.

I didn't provided scripts for other requests than POST, because there is no point.  
It is good to fill the database with records so I can test what I want at the time.

## examples

Assuming we are in **requests** directory we can:

populate database with products
```
sh post.sh
```

get all products
```
crudster -r GET/GET.json
```

get all hard disks
```
crudster -r GET/GET_byCategory/GET_byCategory_HARDDISK.json
```

get products matching %DATA%
```
crudster -r GET/GET_byName/GET_byName_data.json
```

get products with price beetween 150 and 300
```
crudster -r GET/GET_byPrice/GET_byPrice_150_300.json
```
