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
  function filterRead(items) {
    return _.filter(items, function(item){
      return item.read !== true;
    });
  }
  return{
    showFeedItems: function(getFeeds, render){
      getFeeds(function(feeds) {
        var items = filterRead(pickFeedItems(feeds));
        render(items);
      });
    },
    filterReadItems: filterRead,
    pickFeedItems: pickFeedItems
  };
};

function main(){
  feedItemController().showFeedItems(feedService.getFeeds, feedView().render);
}
