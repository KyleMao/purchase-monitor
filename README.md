purchase-monitor
================
###### Dependencies
* PostgreSQL 9.3.5
* sbt 0.13.7
* Other dependencies are specified in build.sbt and should be available through sbt

###### How to run the project
1. Start PostgreSQL server locally (eg. on my Macbook Pro I use *postgres -D /usr/local/var/postgres*), and create a database.
2. Change the user name in Line 32 and 46 in *src/main/resources/sample_user_purchases.sql*, and run it with PostgreSQL.
3. Change the items in db section of *src/main/resources/application.conf* to fit your database.
4. Compile the project with sbt. Then run in sbt, this will invoke the simple command line interface for demoing the project.

###### Notes
* Some sample graphs can be found in *graph/* folder.
* The current command line interface only provides very limitted interface to the functionalities of the module.
* Due to time constraint, the current version lacks documentation and comments in code, which will be added soon.
