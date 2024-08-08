# API-for-storefront-application
Preface
The expectations are to implement the assignment in the order 1-2-3-4. It is not necessary to implement
all of the scope. The quality of the implementation should be prioritized over the scope.
Abstract
Create a REST service that provides API for a storefront application. Implementation should be done in
Java using Spring framework, without borrowing files from some examples from Internet.
API for online store application
Create a service with API that support the following UI capabilities (UI itself is not necessary):
1. Browsing of the list of items with pagination. Each item must show an ItemId, URL to item’s
image, name, short description, price and percentage discount from suggested price.
2. Submit of checkout form with data validation. Form will contain: ItemId, Full Name, Address,
email, phone number, Credit card number. Should return unique OrderId. Every form item
should be verified for correctness. For example:
a. Full Name - should only contain letters A-Z, a-z, and spaces.
b. E-mail – should have a valid email address syntax.
c. Phone Number – should 10 digit long in a format xxx-xxx-xxxx
d. Credit card should be 19 digits long and contain only digits.
e. Address should be non-empty string.
f. ItemId should correspond to one of the items in the list.
3. Order confirmation page. It should include all details about the item specified in checkout
form and all personal info. Input parameter is OrderId.
4. (Optional) Method to get service KPIs. Should return number of requests, maximum delay of
request execution. Segmented by request type. Spring boot Actuator should be used for
that.

There is no need to implement any persistence, all information about items should be auto generated or
imported from local file. All other information (like order id) should be stored in memory only. Not
necessary to provide any real images, URL unique per item is enough.
Should be no authentication required to access the API. It should be accessible from PostMan and/or
browser.
Delivery
Delivery should include:
- Ready to run service implementation and batch file to start it.
- Source code

- Swagger API description
- Postman collection for API testing
