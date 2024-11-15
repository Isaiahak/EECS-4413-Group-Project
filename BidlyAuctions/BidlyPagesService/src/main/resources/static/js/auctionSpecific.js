function initAuctionSpecific() {
    var bidField = document.getElementById("bidInput");

    // Get the text content of the highestBid element (i.e., the <p> element with id="highestBid")
    var highestBidText = document.getElementById("highestBid").textContent;

    // Extract the numeric part by removing the 'Price: $' prefix
    var highestBid = parseFloat(highestBidText.replace('Price: $', '').trim());

    console.log("Highest Bid:", highestBid); // For debugging purposes

    // Set the 'min' attribute of the bid input field to the highest bid + 1
    bidField.setAttribute("min", highestBid + 1);
}