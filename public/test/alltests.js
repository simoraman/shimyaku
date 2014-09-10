describe('Feed service', function(){
  it('can get feeds', function(){
    feedService.getFeeds(function(feeds){
      feeds[0].title.equals('Feed 1');
      feeds[1].title.equals('Feed 2');
    });
  });

});


describe('Feed controller', function(){
  var testData = [
    {
      "_id": "5184de0be86ac43878000001",
      "title": "Feed 1",
      "items": [
        { "title": "Star City" },
        { "title": "untitled" }
      ]
    },
    {
      "_id": "5184de34e86ac43878000002",
      "title": "Feed 2",
      "items": [
        { "title": "The Engine That Does More" }
      ]
    }
  ];

  var expected = [
    { "title": "Star City",
      "feedId":"5184de0be86ac43878000001",
      "feedName": "Feed 1"
    },
    { "title": "untitled",
      "feedName": "Feed 1",
      "feedId":"5184de0be86ac43878000001"
    },
    { "title": "The Engine That Does More",
      "feedId":"5184de34e86ac43878000002",
      "feedName": "Feed 2"
    }
  ];

  it('makes one list of feed items', function(){
    var result = feedItemController().pickFeedItems(testData);
    result.should.eql(expected);
  });

  it('feed items are rendered', function(){
    var getter = function(cb){ cb(testData); };
    var render = sinon.spy();
    feedItemController().showFeedItems(getter, render);
    render.calledWith(expected).should.be.true;
  });

  it('read items are removed', function(){
    var data = expected.slice();
    data.push({"title" : "read", "read" : true});
    var result = feedItemController().filterReadItems(data);
    result.should.eql(expected);
  });

  it('can mark item as read', function(){

  });
});
