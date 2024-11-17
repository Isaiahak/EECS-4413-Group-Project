var socket = new WebSocket("ws://localhost:8080/auction-updates"); // Ensure the WebSocket URL matches the one on your backend
const usernameField = document.getElementById('username');

socket.onopen = function() {
    console.log(" js WebSocket connection established.");
    const usernameValue = usernameField.innerHTML;
    console.log("sending message")
    socket.send(JSON.stringify({type:'connect', data:usernameValue}));
};

socket.onmessage = function(event) {
    console.log(event.data);
    try {
        const message = JSON.parse(event.data);

        switch (message.type) {
            case "closed":
                console.log("processing closed")
                closed(message.data);
                break;
            case "update":
                console.log("Processing 'bidUpdate' message");
                update(message.data);
                break;
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


function closed(aid){
    alert("Auction" + aid + "Has Ended")
}
function update(updates) {
    var auctionId = document.getElementById('auctionId').value;

    updates.forEach(function(item) {
        if (item.aid == auctionId) {
            var priceElement = document.getElementById('price-' + auctionId);
            var timeElement = document.getElementById('timeRemaining-' + auctionId);

            if (priceElement) {
                priceElement.textContent = `Price: $${item.highestBid}`;
            }
            if (timeElement) {
                timeElement.textContent = `Time Remaining: ${item.timeRemaining}`;
            }
        }
    });
}

window.onbeforeunload = function() {
    socket.close();
};

function initAuctionSpecific() {
    var bidField = document.getElementById("bidInput");
    var auctionId = document.getElementById("auctionId").value;
    console.log(auctionId);

    var highestBidText = document.getElementById('price-' + auctionId).innerHTML;
    var highestBid = parseFloat(highestBidText.replace('Price: $', '').trim());

    bidField.setAttribute("min", highestBid + 1);
}

