"# EECS-4413-Group-Project" 
# EECS-4413-Group-Project

LATEST UPDATE: Catalogue page is now running and periodically updating the catalogue
                you can select an auction from the catalogue and click the bidnow button
                to view that specific auction


LATEST ISSUES:  As of now the periodic updates cause the entire catalogue to be rebuilt
                in javascript, this is one very inefficient and two resets the selected 
                radio button. This means that the user has exactly 5 seconds to select 
                an auction and click the bid now button. We need to find a way to update 
                the list without changing the selected radio button. A solution to this 
                would be to only update the catalogue page when an auction is added or 
                removed, and only show the changes upon page refresh. We can periodically
                update the time remaining, and update highest bidder when a new highest bid
                is placed. Updating these values individually will not affect the radio 
                buttons. We also need to change /singup in the SignUpService to a REST Service

SETUP (TEMPORARY)
As of now the applications will not work from the github repo.
you can clone the repo and extract the individual applications to your local drive
you can then open them as separate projects and run them. 

Starting Sequence
1. Catalogue must be started first so it can start the websocket
2. Pages
3. SignUp
4. Security

Ports:
1. Catalogue: 8084
2. Pages: 8080
3. SignUp: 8081
4. Security: 8083

Routes
Port 8080
1. /singupSuccess (Success page on signup)
2. /login (login page)
3. /catalogue (Catalogue page)
4. /auction (Auction specific page)
5. /submit-auction (page to add new auctions)

Port 8083
1. /api/sec/sqli-check (REST service for sqli check)

Port 8084
1. /api/catalogue/add-auction (REST service to add auction to DB)
2. /api/catalogue/fetch-auction (REST service to fetch specific auction)
3. /auction-updates (Websocket url for periodic updates)

Port 8081
1. /signup (Invoke sign up method) (TO BE CHANGED)
2. /api/login (REST service to verify login credentials)