# Eshop

### This application is a classic eshop where the user can:
* create or update an account
* save a product
* get a list of all products or a specific one
* save product variations
* add products to cart or delete the entire cart
* add vouchers and apply them to the cart
* place an order
* see a list of all orders placed by that customer 

### The project contains 7 entities
* Customer - keeps the information about a customer
* Product - keeps the information about a product
* ProductVariation - keeps the variations of one product
* Voucher - keeps the information of a voucher
* Cart - a user's cart where he can see the products he added, the voucher applied and the total value
* Order - keeps the information about a user's order when it is placed
* OrderItem - copies the information from product when an order is placed so when the product information changes, the orderItem still reflects the information at the moment the order was placed

## How it works

### Add to cart

When the user adds to cart first we make sure that the user and the product added exist.
Then we make sure there is enough stock of that product.
If that is the case, we get customer's cart or create a new one, add the product to its products list, update product stock, recalculate the value and persist it (either save or update).

### Add voucher

Similar with adding a product, we make sure the user and voucher exist.
If the voucher exists, we make sure it is active, add it to the voucher property of the cart, recalculate total value and persist the cart.

### Checkout

when the user places an order, an Order object is created, cart items are copied to OrderItem objects linked with the order and then all are persisted.
Then we clear user's cart.

### Saving and updating a user

Taking into account that the email is unique, when a new user is created or when one is updated, first of all we make sure the email address does not correspond to another user's email.
Then we Create a Customer object and persist it.
