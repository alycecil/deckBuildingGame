//TODO alter to real game id
var gameId = 1

function loadGames() {
    $.ajax({
        url: '/game/list',
        success: function(context) {
            getTemplateAjax('handlebar/existingGameLink.handlebars', function(template) {
                var html = template(context);
                $("#gamesList").html(html);
            });
        }
    });
}

function scrollToAnnouncement() {
    if ($('.announcement').size()) {
        $('html, body').animate({
            scrollTop: $(".announcement").offset().top
        }, 2000);
    }
}
function renderGame(context){
    console.log(context);
    getTemplateAjax('handlebar/renderGame.handlebars', function(template) {
        var html = template(context);
        $("#gamesList").html(html);
        $('.endTurn').click(endTurn);
        $('.playCard').click(playCard);
        $('.buyCard').click(buyCard);
        $('.playAll').click(playAll);
        
		$('[data-toggle="tooltip"]').tooltip()

        scrollToAnnouncement();
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

function playAll() {
    $.ajax({
        url: '/game/move?id='+gameId+'&action=PlayHand',
        success: renderGame
    });
}

function buyCard() {
    $.ajax({
        url: '/game/move?id='+gameId+'&action=BuyCard&targetCard='+$(this).attr('card'),
        success: renderGame
    });
}

function playCard() {
    $.ajax({
        url: '/game/move?id='+gameId+'&action=PlayCard&sourceCard='+$(this).attr('card'),
        success: renderGame
    });
}

function endTurn() {
    $.ajax({
        url: '/game/move?id='+gameId+'&action=EndTurn',
        success: renderGame
    });
}
