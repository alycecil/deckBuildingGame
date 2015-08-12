//TODO alter to real game id
var gameId = null
var token = null

function showLogin() {
	var context = {}
	_showLogin(context)
}

function _showLogin(context){
	getTemplateAjax('handlebar/login.handlebars', function(template) {
	    var html = template(context);
	    $("#gamesList").html(html);
	});
}

function createUser(){

	return false;
}

function login() {
	var usr = $('#usr').val().trim();
	var psw = $('#pwd').val().trim();
	
	if(psw==null||psw==""){
		alert("Password may not be null");
	}
	var hash = CryptoJS.SHA1(psw);
	psw = hash.toString()
	
	var url = '/players/token?username='+usr+'&password='+psw
	
	try {  
	 	$.ajax({
	        url: url,
	        success: function(context) {
	            token = context.token
	            loadGames();
	        },
	        error: function(){
	        	var context = {"error":"Unknown User"}
	        	_showLogin(context);
	        }
	    });
    } catch (e) {
	   throw new Error(e.message);
	}
	return false;
}


function loadGames() {
	if(token==null){
		showLogin()
	}else{
	    $.ajax({
	        url: '/game/list?token='+token,
	        success: function(context) {
	            getTemplateAjax('handlebar/existingGameLink.handlebars', function(template) {
	                var html = template(context);
	                $("#gamesList").html(html);
	            });
	        }
	    });
    }
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
    	gameId=context.id
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
	if(token==null){
		showLogin()
	}else{
	    $.ajax({
	        url: '/game/new?token='+token,
	        success: renderGame
	    });
    }
}

function getGameById(_gameId) {
	gameId = _gameId
	if(token==null){
		showLogin()
	}else{
	    $.ajax({
	        url: '/game/get?id='+gameId+'&token='+token,
	        success: renderGame
	    });
    }
}

function getGame() {
	getGameById(gameId);
}

function playAll() {
	if(token==null){
		showLogin()
	}else{
	    $.ajax({
	        url: '/game/move?id='+gameId+'&action=PlayHand&token='+token,
	        success: renderGame
	    });
    }
}

function buyCard() {
	if(token==null){
		showLogin()
	}else{
	    $.ajax({
	        url: '/game/move?id='+gameId+'&action=BuyCard&token='+token+'&targetCard='+$(this).attr('card'),
	        success: renderGame
	    });
    }
}

function playCard() {
	if(token==null){
		showLogin()
	}else{
	    $.ajax({
	        url: '/game/move?id='+gameId+'&action=PlayCard&token='+token+'&sourceCard='+$(this).attr('card'),
	        success: renderGame
	    });
    }
}

function endTurn() {
	if(token==null){
		showLogin()
	}else{
	    $.ajax({
	        url: '/game/move?id='+gameId+'&token='+token+'&action=EndTurn',
	        success: renderGame
	    });
    }
}
