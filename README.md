# Transfer Service

RESTful API implemented for money transfers between accounts.

### Teck stack
- Javalin -> Lightweight web framework
- ORMLite -> Lightweight Object Relational Mapping (ORM)
- H2Db -> open-source lightweight Java database

### Database

Database create manually and filled with following data. You can use them to test.
However, you can update or insert data with H2 console.

| ID  | Name | Amount |
| ------------- | ------------- | ------------- |
| 50d69494-bac4-42e9-ba8f-b7256cd031ce  | Hamilton  | 5000 |
| a9e43155-8617-4725-b077-9ca7cf725106  | Vettel  | 5000 | 
| f1d53448-eb96-44da-9ade-05ae671533ae | Max | 5000 |

H2 database LOCK_MODE is 3 by default. It means, read and write
processes to table are locked.

### Endpoints

[GET] {host}:7000/accounts -> collect all data from db

[POST] {host}:7000/transfer

```
BODY: 
{
    "from":"id1",
    "to":"id2",
    "amount":money amount
}
```

it will send requested amount money from account with id1 to account with id2 if there will not be validation error

### How to run
Run following command in project folder
```
mvn exec:java
```

### Unit tests are implemented for service and handler