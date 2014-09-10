function feedView() {
  function removeFromDom(event){
    $(event.target).parent().fadeOut();
  }
  function markAsRead(event){
    var article = $(event.target).parent();
    article.append('<input type="hidden" class="read" value="true"/>');
    var id = article.find('.feed-id').val();
    var feedItems = $($.find('.feed-id[value="'+ id +'"]')).parent();

    var feedName = $(feedItems[0]).find('.feed-name').val();
    var feedId = $(feedItems[0]).find('.feed-id').val();
    var items = _.map(feedItems, function(item){
      var $item = $(item);
      var itemObject = {};
      itemObject.title = $item.find('.title').text();
      itemObject.link = $item.find('.link').attr('href');
      itemObject.description = $item.find('.description').text();
      itemObject.read = $item.find('.read').val()==="true" ? true : false;
      return itemObject;
    });
    var persistentFeed = {"_id":feedId, "title":feedName, "items":items};
    $.ajax({
      type: 'POST',
      url: '/feeds',
      contentType:"application/json; charset=utf-8",
      dataType: 'json',
      data: JSON.stringify(persistentFeed)
    });

  }
  return {
    render: function(items){
      var html = ['<article>',
                  '<a class="link" href="{{link}}">',
                  '<h1 class="title">{{title}}</h1></a>',
                  '<p class="description">{{description}}</p>',
                  '<button class="mark-read">mark as read</button>',
                  '<input type="hidden" class="feed-id" value="{{feedId}}"/>',
                  '<input type="hidden" class="feed-name" value="{{feedName}}"/>',
                  '</article>'].join('');
      var template = Hogan.compile(html);
      items = items.map(function(item){
        return template.render(item);
      });

      $('#feed-items').append(items);

      $('button.mark-read').asEventStream('click')
        //.doAction(removeFromDom)
        .onValue(markAsRead);
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
    var feedItems = _.map(feeds, function(feed) {
      return _.forEach(feed.items, function(item) {
        item.feedId = feed._id;
        item.feedName = feed.title;
        return item;
      });
    });
    return _.flatten(feedItems);
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
