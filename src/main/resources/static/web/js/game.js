//TODO alter to real game id
var gameId = 1

function loadGames() {
    $.ajax({
        url: '/game/list',
        success: function(context) {
            console.log(context);
            getTemplateAjax('handlebar/existingGameLink.handlebars', function(template) {
                var context = {
                    title: "My New Post",
                    body: "This is my first post!"
                };
                var html = template(context);
                $("#gamesList").html(html);
            });
        }
    });
}

function renderGame(context){
    console.log(context);
    getTemplateAjax('handlebar/renderGame.handlebars', function(template) {
        var html = template(context);
        $("#gamesList").html(html);
    });
}

function newGame() {
    $.ajax({
        url: '/game/new',
        success: renderGame
    });
}

function getGame() {
    $.ajax({
        url: '/game/get?id='+gameId,
        success: renderGame
    });
}

function getGameById(gameId) {
    $.ajax({
        url: '/game/get?id='+gameId,
        success: renderGame
    });
}

function buyCard() {
    $.ajax({
        url: '/game/get?id='+gameId,
        success: renderGame
    });
}

