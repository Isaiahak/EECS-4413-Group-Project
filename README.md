"# EECS-4413-Group-Project" 
# EECS-4413-Group-Project

LATEST UPDATE:  Auction Specific page is receiving real time updates and pub sub feature is able to broadcast
                to any page that starts a websocket connection. Code cleaned up and commented on all microservices.
                Unused files deleted. 


LATEST ISSUES:  The Pub Sub implementation is pretty basic, so it does not handle disconnects or 
                proper 3rd party bus. In the future we can use Kafka or RabbitMQ for a real pub 
                sub implementation, however this may force the clients to listen to the live server 
                directly, breaking the layered arch rules. All the components in this push are 
                fully functional. All thats required is code cleanup and event handling for winning auctions.
                Auction that end without any subscribers will throw null error, will need to change the logic
                for that

SETUP (TEMPORARY)
As of now the applications will not work from the github repo.
you can clone the repo and extract the individual applications to your local drive
you can then open them as separate projects and run them. 

Starting Sequence
1. Catalogue must be started first so it can start the websocket
2. Live Server
2. Pages
3. SignUp
4. Security

Ports:
1. Catalogue: 8084
2. Live Server: 8086
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