function parseRSS(url, callback) {
  $.ajax({
    url: 'https://ajax.googleapis.com/ajax/services/feed/load?v=1.0&num=10&callback=?&q=' + encodeURIComponent(url),
    dataType: 'json',
    success: function(data) {
      callback(data.responseData.feed);
    }
  });
}

// Every div with the class rss_box must have data-url with the url to the rss feed
$(function(){
	$(".rss_box").each(function(){
		var box = $(this)
		var url = box.data("url");
		parseRSS(url, function(rss){
			console.log(rss)
			var h2 = document.createElement("h2");
			h2.innerText = rss.title;
			box.append(h2);

			var ul = document.createElement("ul");
			box.append(ul);
			for(var index in rss.entries){
				var entry = rss.entries[index]
				var li = document.createElement("li");
				ul.appendChild(li);
				li.innerText = entry.title;
				// var span = document.createElement("span")
				// span.innerText = entry.content;
				// li.appendChild(span)
			}
		})
	})

})

