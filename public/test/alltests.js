describe('Feed service', function(){
  it('can get feeds', function(){
    feedService.getFeeds(function(feeds){
      feeds[0].title.equals('Feed 1');
      feeds[1].title.equals('Feed 2');
    });
  });

});

var feedController = {
  showFeeds: function(service, view){

  }
};
describe('Feed controller', function(){
  it('makes one list of feed items', function(){
    feedController.showFeeds(feedService, feedView);
  });
});
