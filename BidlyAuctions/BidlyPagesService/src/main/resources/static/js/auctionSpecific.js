var socket = new WebSocket("ws://localhost:8080/auction-updates"); // Ensure the WebSocket URL matches the one on your backend
const usernameField = document.getElementById('username');

socket.onopen = function() {
    console.log(" js WebSocket connection established.");
    const usernameValue = usernameField.innerHTML;
    console.log("sending message")
    socket.send(JSON.stringify({type:'connect', data:usernameValue}));
    initAuctionSpecific();
};

socket.onmessage = function(event) {
    try {
        const message = JSON.parse(event.data);

        switch (message.type) {
            case "closed":
                console.log("processing closed")
                closed(message.data, message.redirectUrl);
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


function closed(aid, url){
    alert("Auction " + aid + "Has Ended")
    console.log("redirecting " + url +" " + aid);
    window.location.href = `${url}?auctionId=${aid}`;


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
            if(item.timeRemaining == "CLOSED"){
                console.log("removing")
                document.getElementById('bidButton').remove();
                document.getElementById('bidInput').remove();
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

    if(document.getElementById('timeRemaining-' + auctionId).textContent == "Time Remaining: CLOSED"){
        console.log("true")
        console.log("removing")
        document.getElementById('bidButton').remove();
        document.getElementById('bidInput').remove();
    }
}

