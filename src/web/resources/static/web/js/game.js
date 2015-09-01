var gameId = null;
var token = null;
var userId = null;
var gameChannel = null;
var userChannel = null;
var gameStomp = null;
var userStomp = null;
var msgCnt = 0;

var currentPlayerId = null;
var currentUserId=null;

function showLogin() {
	gameId = null;
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
			         token = context.token;
			         userId = context.userId;
			         connectUserChannel();
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
		            token = context.token;
		            userId = context.userId;
		            connectUserChannel();
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
	gameId = null;
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
    	gameId=context.id;
    	currentPlayerId=context.currentPlayer.id;
    	currentUserId=context.currentPlayer.userId;
        var html = template(context);
        
        joinGameChannel();
        
        $("#gamesList").html(html);
        $('.endTurn').click(endTurn);
        $('.playCard').click(playCard);
        $('.buyCard').click(buyCard);
        $('.playAll').click(playAll);
        $('.showHistory').click(loadHistory);
        
		$('[data-toggle="tooltip"]').tooltip();

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
	        success: function(){}
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
	        success: function(){}
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

function updateCounts(){
	$('.zone-count').each(function(index, element){var _ele = $(element); _ele.html($(_ele.attr('x-counttarget')).find('.card:not([style])').length)})
}

function moveCard(zone,sourceCard, sourcePlayer){
	var card = $("#card-"+sourceCard.id);
	if(card==null){
		card=$('#card-:not([style]):last');
	}
	if(card!=null){card.fadeOut(1000);}
	
	var playerPlayedRoot = $('.'+zone+'.player-'+sourcePlayer.id);
	playerPlayedRoot.collapse('show');
	
	var newCard = null ;
	
	if(zone == 'hand'){
		newCard = Handlebars.compile("{{> playerHandCard playerId="+sourcePlayer.id+" currentPlayerId="+currentPlayerId+" currentUserId="+currentUserId+"}}")(sourceCard);
	}else{
		newCard = Handlebars.compile("{{> blankCard}}")(sourceCard);
	}
	
	playerPlayedRoot.find('.panel-body').append(newCard);
	
	updateCounts();
}

function updatePlayer(player){
	if(player!=null&&player.id!=null){
		if(player.money!=null){
			$('.money-total.player-'+player.id).html(player.money);
		}
	}
}

function handleGameMessages(raw){
	var msg = JSON.parse(raw.body);
	if(msg.gameId == gameId){
		if(msg.type=="ENDTURN"){
			console.log("End Turn Recieved");
			setTimeout(getGame,100);
		}else if(msg.type=="UPDATE"){
			if(msg.content!=null&&msg.content.type!=null){
				if(msg.content.type.endsWith('PlayCard')){
					moveCard('played',msg.content.sourceCard,msg.content.sourcePlayer);
					updatePlayer(msg.content.sourcePlayer);
				}else if(msg.content.type.endsWith('DrawCard')){
					//moveCard('hand',msg.content.sourceCard,msg.content.sourcePlayer);
					//updatePlayer(msg.content.sourcePlayer);
					setTimeout(getGame,100);
				}else if(msg.content.type.endsWith('BuyCard')){
					moveCard('discard',msg.content.targetCard,msg.content.sourcePlayer);
					updatePlayer(msg.content.sourcePlayer);
				}else if(msg.content.type.endsWith('DiscardCard')){
					moveCard('discard',msg.content.targetCard,msg.content.targetPlayer);
					updatePlayer(msg.content.targerPlayer);
				}
			}
		}
	}else{
		console.log("should of unsubcribed this channel")
		joinGameChannel();
	}
}


function joinGameChannel(){
	var channel = gameId;
	if(gameChannel != channel){
		if(gameChannel!=null){
			gameStomp.unsubscribe('gamesub');
			disconnect(gameStomp);
			gameStomp = null;
			gameChannel = null;
		}
		
		gameChannel = channel;
		subscribe(gameStomp, '/wshello', '/topic/game.'+channel, handleGameMessages, {id:'gamesub'})
	}
	
}

function myAlert(msg, myClass){
	if(myClass==null){myClass='success';}
	msgCnt++;
	var myCnt = msgCnt;
	$('#gameHeader').append('<div class="alert alert-'
		+myClass
		+'" id="success-alert-'
		+ myCnt
		+'"><button type="button" class="close" data-dismiss="alert">x</button>'
		+ msg
		+'</div>');
		
	
}


function showUpdate(msg){
	myAlert(msg.content, 'info');
}

function newGameAlert(msg){
	if(gameId==null){
		loadGames();
	}
	
	var message = '<a onclick="gameId=\''+msg.content.trim()+'\'; getGame();">Started a new game, click to play now</a>'
	
	myAlert(message);
}

function handleUserMessages(raw){
	var msg = JSON.parse(raw.body);
	console.log(msg);
	if(userChannel == userId){
		if(msg.type == "UPDATE"){
			showUpdate(msg);
		}else if(msg.type == "NEWGAME"){
			newGameAlert(msg);
		}else{
			console.log("Unknown Message Type in usersChannel :"+msg.type);
		}
	}else{
		connectUserChannel();
	}
}


function connectUserChannel(){
	var channel = userId;
	if(userChannel != channel){
		if(userChannel!=null){
			userStomp.unsubscribe('usersub');
			disconnect(userStomp);
			userStomp = null;
			userChannel = null;
		}
		
		userChannel = channel;
		userStomp = subscribe(userStomp,'/wshello','/topic/user.'+channel, handleUserMessages, {id:'usersub'})
	}
}