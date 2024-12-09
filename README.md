"# EECS-4413-Group-Project"

# EECS-4413-Group-Project

LATEST UPDATE: The entire backend functionality and frontend is complete. In this current state, the application will fully work.

IMPORTANT: A TON of fields in the AuctionDB have been added. Please delete the auction and catalogue tables before starting
the catalogue service so Hibernate can correctly regenerate the table.

WHATS LEFT: DEPLOY

LATEST ISSUES: The Live server throws a null exception when there are no auctions left in the catalogue, this does not hinder
the user experience but we will need to handle that. There has been little testing for user sign up, incorrect login,
existing user upon signup, we need to make sure those functions are working correctly. The Pub Sub implementation is
pretty basic, but we might be too far gone now to make changes. Currently There is no generation of shipping dates in
the app or the database, we will need to create those and handle them for expedited vs standard shipping

SETUP (TEMPORARY)
As of now the applications will not work from the github repo.
you can clone the repo and extract the individual applications to your local drive
you can then open them as separate projects and run them.

In order to set up the database, you need to install Postgre with pgAdmin4. When installing pgAdmin, make sure to remember your username
and password for the admin user, you need this to access the database from the microservices. in pgAdmin4, first create a server using
any name you prefer, but it is recommended to keep the server port at the default (5432) port number. Create two databases; one for
users and one for auctions. You just need to create those two empty databases, Hibernate will generate the tables itself.

Next you need to set up the database access for the microservices. BidlyCatalogue, BidlySignUpService, and BidlyLiveServer require access
to the databases. For each of the services above, go to: src/main/resource/application.properties. here you will need to edit these three
lines:

1. spring.datasource.url=jdbc:postgresql://localhost:{SERVER PORT NUMBER}/{DATABASE NAME}
2. spring.datasource.username={YOUR ADMIN USER USERNAME}
3. spring.datasource.password={YOUR ADMIN USER PASSWORD}

Catalogue will access the Auction database
LiveServer will access the Auction database
SignUpService will access the User database.

Starting Sequence

1. Catalogue
2. SignUp
3. Security
4. Live Server
5. Pages (Ensure Live Server completes startup before pages completes startup)

You can now access the webapp via localhost:8080/main
To add auctions, access localhost8080/submit-auction

Ports:

1. Catalogue: 8084
2. Live Server: 8086
3. Pages: 8080
4. SignUp: 8081
5. Security: 8083
