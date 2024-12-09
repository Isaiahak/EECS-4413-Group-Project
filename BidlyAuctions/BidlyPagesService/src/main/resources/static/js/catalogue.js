var socket = new WebSocket("ws://localhost:8080/auction-updates"); // Ensure the WebSocket URL matches the one on your backend
var auctionListContainer = document.getElementById("auctionList");
const usernameField = document.getElementById('username');

socket.onopen = function() {
    console.log(" js WebSocket connection established.");
    const usernameValue = usernameField.innerHTML;
    console.log(usernameValue);
    socket.send(JSON.stringify({type:'connect', data:usernameValue}));
};

socket.onmessage = function(event) {
    try {
        const message = JSON.parse(event.data);


        switch (message.type) {
            case "init":
                console.log("Processing 'init' message");
                initCatalogue(message.data);
                break;
            case "newCat":
                console.log("Processing 'newCat' message");
                addCatItem(message.data);
                break;
            case "bidUpdate":
                console.log("Processing 'bidUpdate' message");
                handleBidUpdate(message.data);
                break;
            case "auctionClosed":
                console.log("Processing 'auctionClosed' message");
                handleAuctionClosed(message.data);
                break;
            case "update":
                console.log("Processing 'update' message");
                update(message.data);
                break;
            case "closed":
                console.log("processing closed")
                closed(message.data);
            default:
                console.error("Unknown message type:", message.type);
        }
    } catch (e) {
        console.error("Error parsing message:", e);
    }
};


socket.onerror = function(error) {
    console.error("WebSocket Error:", error);
};

socket.onclose = function() {
    console.log("WebSocket connection closed.");
};

// Function to update the HTML with the latest auction items
function initCatalogue(catalogueItems) {
    console.log("Initializing catalogue with items:", catalogueItems);  // Log catalogue items to make sure they are passed correctly

    auctionListContainer.innerHTML = '';  // Clear the current list

    // Use setTimeout to avoid blocking the event loop
    setTimeout(() => {
        catalogueItems.forEach(function(item) {
            console.log("Creating element for item:", item);  // Log each item being processed

            var itemElement = document.createElement('div');
            itemElement.classList.add('auction-item');
            itemElement.setAttribute("id", item.aid);

            itemElement.innerHTML = `
<<<<<<< HEAD
                <input type="radio" id="itemSelect" name="auctionSelect" value="${item.aid}" onclick="selectAuction(${item.aid})">
=======
                <input type="radio" id="itemSelect" name="auctionSelect" value="${item.aid}" onclick="selectAuction(${item.aid},'${item.type}')">
>>>>>>> 5038a01 (added the shipping date)
                <h3>${item.title}</h3>
                <p id="price-${item.aid}">Price: $${item.highestBid}</p>
                <p id="timeRemaining-${item.aid}">Time Remaining: CLOSED</p>
            `;

            auctionListContainer.appendChild(itemElement);
        });
    }, 0);  // Run after the current call stack has cleared
}

function addCatItem(catalogueItem) {

    // Create HTML elements for each catalogue item and append them to the container
        var itemElement = document.createElement('div');
        itemElement.classList.add('auction-item');
        itemElement.setAttribute("id", catalogueItem.aid);

        // You can customize the HTML based on the CatalogueItem fields
        itemElement.innerHTML = `
<<<<<<< HEAD
            <input type="radio" id="itemSelect" name="auctionSelect" value="${catalogueItem.aid}" onclick="selectAuction(${catalogueItem.aid})">
=======
            <input type="radio" id="itemSelect" name="auctionSelect" value="${catalogueItem.aid}" onclick="selectAuction(${catalogueItem.aid},${catalogueItem.type})">
>>>>>>> 5038a01 (added the shipping date)
            <h3>${catalogueItem.title}</h3>
            <p id="price-${catalogueItem.aid}">Price: $${catalogueItem.highestBid}</p>
            <p id="timeRemaining-${catalogueItem.aid}">Time Remaining: ${catalogueItem.auctionTime}</p>
        `;

        auctionListContainer.appendChild(itemElement);
}

function update(updates){
    updates.forEach(function(item){
        var catalogueItem = document.getElementById(item.aid);

        var time = catalogueItem.querySelector(`#timeRemaining-${item.aid}`);
        var bid = catalogueItem.querySelector(`#price-${item.aid}`);

        time.textContent = `Time Remaining: ${item.timeRemaining}`;
        bid.textContent = `Price: $${item.highestBid}`;
    });
}

<<<<<<< HEAD
function selectAuction(auctionId) {
    selectedAuctionId = auctionId; // Store the selected auction ID
=======
function selectAuction(auctionId, type) {
    selectedAuctionId = auctionId;// Store the selected auction ID
    selectedType = type;
>>>>>>> 5038a01 (added the shipping date)

    // Enable the "Bid Now" button only if an auction is selected
    if (selectedAuctionId) {
        console.log("button enabled");
        bidNowButton.disabled = false;
    }
}

function placeBid() {
    if (selectedAuctionId) {
<<<<<<< HEAD
        // Redirect to the auction page, passing the auction ID as a query parameter
        if(catalogueItem.getType() == "dutch"){
            window.location.href = `/dutch-auction?auctionId=${selectedAuctionId}`;
        }
        else{
            window.location.href = `/auction?auctionId=${selectedAuctionId}`;
        }
        console.log("redirecting");
=======
        if(selectedType=="forward"){
            // Redirect to the auction page, passing the auction ID as a query parameter
            console.log("redirecting");
            window.location.href = `/auction?auctionId=${selectedAuctionId}`;
        }else if (selectedType == "dutch"){
            // Redirect to the auction page, passing the auction ID as a query parameter
            console.log("redirecting");
            window.location.href = `/dutch-auction?auctionId=${selectedAuctionId}`;
        }

>>>>>>> 5038a01 (added the shipping date)
    } else {
        alert("Please select an auction to place a bid.");
    }
}

function closed(auction){
    alert("Auction" + auction.aid + "Has Ended");
    window.location.href = `${auction.redirectUrl}?auctionId=${aid}`;
}

// Function to filter auction items based on search input
searchInput.addEventListener("input", function() {
    var query = searchInput.value.toLowerCase(); // Get the current search query
    var auctionItems = document.querySelectorAll('.auction-item'); // Get all auction items

    auctionItems.forEach(function(item) {
        var title = item.querySelector('h3').textContent.toLowerCase(); // Get the title of each auction item

        // If the title includes the search query, show the item; otherwise, hide it
        if (title.includes(query)) {
            item.style.display = '';  // Show the item
        } else {
            item.style.display = 'none';  // Hide the item
        }
    });
});



// Optional: Close WebSocket connection when page is unloaded
window.onbeforeunload = function() {
    socket.close();
};

socket.onerror = function(error) {
    console.error("WebSocket Error:", error);
};

socket.onclose = function(event) {
    console.log("WebSocket connection closed.");
    if (!event.wasClean) {
        console.error("Connection closed with error code:", event.code);
    }
};