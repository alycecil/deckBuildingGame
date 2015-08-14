function getTemplateAjax(path, callback) {
    var source;
    var template;

    $.ajax({
      url: path,
        success: function(data) {
          source    = data;
          template  = Handlebars.compile(source);

          //execute the callback if passed
          if (callback){ 
          	callback(template);
          }else{
          	$('head').append(template);
          }
        }
    });
}


function getPartialAjax(path, name) {
    var source;
    var template;

    $.ajax({
      url: path,
        success: function(data) {
          Handlebars.registerPartial(name, data)
        }
    });
}


function registerPartials(){
	getPartialAjax('/resources/handlebar/partial/playerHandCard.handlebars','playerHandCard')
	getPartialAjax('/resources/handlebar/partial/blankCard.handlebars','blankCard')
	getPartialAjax('/resources/handlebar/partial/buyCard.handlebars','buyCard')
	getPartialAjax('/resources/handlebar/partial/player.handlebars','player')
	getPartialAjax('/resources/handlebar/partial/renderMulticard.handlebars','renderMulticard')
}