function feedView() {
  return {
    render: function(items){
      var html = ['<article>',
                  '<a href="{{link}}">',
                  '<h1>{{title}}</h1></a>',
                  '<p>{{description}}</p>',
                  '</article>'].join('');
      var template = Hogan.compile(html);
      items = items.map(function(item){
        return template.render(item);
      });

      $('#feed-items').append(items);
    }
  };
};

var feedService = {
  getFeeds: function(callback){
    $.getJSON('/feeds', function(data){
      callback(data);
    });
  }
};

function feedItemController() {
  function pickFeedItems(feeds) {
    return _.flatten(feeds, 'items');
  }
  return{
    showFeedItems: function(getFeeds, render){
      getFeeds(function(feeds){
        var items = pickFeedItems(feeds);
        render(items);
      });
    },
    pickFeedItems: pickFeedItems
  };
};

function main(){
  feedItemController().showFeedItems(feedService.getFeeds, feedView().render);
}
