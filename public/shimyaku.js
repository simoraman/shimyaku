var feedView = {
  render: function($parent, items){
    var html = ['<article>',
                '<h1>{{title}}</h1>',
                '<p>{{}}</p>',
                '</article>'].join('');
    var template = Hogan.compile(html);
    items = items.map(function(item){
      return template.render(item);
    });

    $parent.append(items);
  }
};

var feedService = {
  getFeeds: function(callback){
    $.getJSON('/feeds', function(data){
      callback(data);
    });
  }
};


function main(){
 feedService.getFeeds(function(items){
   feedView.render($('#feed-items'), items);
 });
}
