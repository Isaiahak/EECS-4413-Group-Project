var socket = new WebSocket("ws://localhost:8084/auction-updates"); // Ensure the WebSocket URL matches the one on your backend
var auctionListContainer = document.getElementById("auctionList");

socket.onopen = function() {
    console.log(" js WebSocket connection established.");
};

socket.onmessage = function(event) {
    // Parse the incoming message (assuming it's a JSON array of CatalogueItem objects)
    try {
        var catalogueItems = JSON.parse(event.data);
        updateAuctionList(catalogueItems);
    } catch (error) {
        console.error("Error parsing WebSocket message:", error);
    }
};

socket.onerror = function(error) {
    console.error("WebSocket Error:", error);
};

socket.onclose = function() {
    console.log("WebSocket connection closed.");
};

// Function to update the HTML with the latest auction items
function updateAuctionList(catalogueItems) {
    auctionListContainer.innerHTML = '';  // Clear the current list

    // Create HTML elements for each catalogue item and append them to the container
    catalogueItems.forEach(function(item) {
        var itemElement = document.createElement('div');
        itemElement.classList.add('auction-item');

        // You can customize the HTML based on the CatalogueItem fields
        itemElement.innerHTML = `
            <input type="radio" id="itemSelect" name="auctionSelect" value="${item.aid}" onclick="selectAuction(${item.aid})">
            <h3>${item.title}</h3>
            <p>Price: $${item.highestBid}</p>
            <p>Time Remaining: ${item.auctionTime}</p>
        `;

        auctionListContainer.appendChild(itemElement);
    });
}

function selectAuction(auctionId) {
    selectedAuctionId = auctionId; // Store the selected auction ID

    // Enable the "Bid Now" button only if an auction is selected
    if (selectedAuctionId) {
        console.log("button enabled");
        bidNowButton.disabled = false;
    }
}

function placeBid() {
    if (selectedAuctionId) {
        // Redirect to the auction page, passing the auction ID as a query parameter
        console.log("redirecting");
        window.location.href = `/auction?auctionId=${selectedAuctionId}`;
    } else {
        alert("Please select an auction to place a bid.");
    }
}

// Optional: Close WebSocket connection when page is unloaded
window.onbeforeunload = function() {
    socket.close();
};