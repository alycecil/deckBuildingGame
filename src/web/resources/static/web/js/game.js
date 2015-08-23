var gameId = null;
var token = null;
var userId = null;
var gameChannel = null;

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

function getPassword(){
	var psw = $('#pwd').val().trim();
	
	if(psw==null||psw==""){
		var context = {"error":"Password may not be blank"}
	    _showLogin(context);
	    pwd = null;
	}else{
		var hash = CryptoJS.SHA1(psw);
		psw = hash.toString();
	}
	
	return psw;
}

function createUser(){
	var usr = $('#usr').val().trim();
	var psw = getPassword();
	
	if(psw!=null&&pwd!=''){
		var url = '/players/new?username='+usr+'&password='+psw
		
		try {  
		 	$.ajax({
		        url: url,
		        success: function(context) {
			         token = context.token
			         userId = context.userId
		             login();
		        },
		        error: function(){
		        	var context = {"error":"Unable to create user, try a different username"}
		        	_showLogin(context);
		        }
		    });
	    } catch (e) {
		   throw new Error(e.message);
		}
	}
	
	return false;
}

function login() {
	var usr = $('#usr').val().trim();
	var psw = getPassword();
	
	if(psw!=null&&pwd!=''){
		var url = '/players/token?username='+usr+'&password='+psw
		
		try {  
		 	$.ajax({
		        url: url,
		        success: function(context) {
		            token = context.token
		            userId = context.userId
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
	}
	return false;
}


function loadGames() {
	if(token==null){
		showLogin();
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
        
        joinGameChannel();
        
        $("#gamesList").html(html);
        $('.endTurn').click(endTurn);
        $('.playCard').click(playCard);
        $('.buyCard').click(buyCard);
        $('.playAll').click(playAll);
        $('.showHistory').click(loadHistory)
        
		$('[data-toggle="tooltip"]').tooltip()

        scrollToAnnouncement();
    });
}

function newGame() {
	if(token==null){
		showLogin();
	}else{
	    $.ajax({
	        url: '/game/queue?token='+token,
	        success: loadGames
	    });
    }
}

function newSoloGame() {
	if(token==null){
		showLogin();
	}else{
	    $.ajax({
	        url: '/game/solo?token='+token,
	        success: renderGame
	    });
    }
}

function getGameById(_gameId) {
	gameId = _gameId
	if(token==null){
		showLogin();
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
		showLogin();
	}else{
	    $.ajax({
	        url: '/game/move?id='+gameId+'&action=PlayHand&token='+token,
	        success: renderGame
	    });
    }
}

function buyCard() {
	if(token==null){
		showLogin();
	}else{
	    $.ajax({
	        url: '/game/move?id='+gameId+'&action=BuyCard&token='+token+'&targetCard='+$(this).attr('card'),
	        success: renderGame
	    });
    }
}

function playCard() {
	if(token==null){
		showLogin();
	}else{
	    $.ajax({
	        url: '/game/move?id='+gameId+'&action=PlayCard&token='+token+'&sourceCard='+$(this).attr('card'),
	        success: renderGame
	    });
    }
}

function endTurn() {
	if(token==null){
		showLogin();
	}else{
	    $.ajax({
	        url: '/game/move?id='+gameId+'&token='+token+'&action=EndTurn',
	        success: function(){}
	    });
    }
}


function loadHistory(){
	if(token==null){
		showLogin();
	}else{
		$.ajax({
	        url: '/game/audit?id='+gameId+'&token='+token,
	        success:  function(context){
		        	getTemplateAjax('handlebar/audit.handlebars', function(template) {
				        var html = template(context);
				        $('.well.audit').html(html);
    				});
    		}
	    });
	}
}

function handleGameMessages(raw){
	var msg = JSON.parse(raw.body);
	if(msg.content == gameId){
		if(msg.type="ENDTURN"){
			console.log("End Turn Recieved");
			setTimeout(getGame,100);
		}
	}else{
		console.log("should of unsubcribed this channel")
		joinGameChannel();
	}
}


function joinGameChannel(){
	var channel = gameId;
	if(gameChannel != channel){
		if(gameChannel!=null)
			stompClient.unsubscribe('gamesub');
		
		gameChannel = channel;
		subscribe('/topic/game.'+channel, handleGameMessages, {id:'gamesub'})
	}
	
}