

function disconnect(stompClient) {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}
        
function connect(stompClient, socketRoot) {
    var socket = new SockJS(socketRoot);
    stompClient = Stomp.over(socket); 
    
    return stompClient;           
}

function subscribe(stompClient, socketRoot, channel, callback, data) {
    if(stompClient==null){
		stompClient = connect(stompClient, socketRoot);
	}

	stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe(channel, callback, data);
    });   
    
    return stompClient;        
}

function handleMessage(msg){
	console.log(msg.content);
}

function logMessage(raw){
	var msg = JSON.parse(raw.body);
	console.log(msg);
}

var hearbeatStomp = null;
function listenHeartBeat(){
	if(hearbeatStomp!=null){
		hearbeatStomp.unsubscribe('heartbeat');
		disconnect(hearbeatStomp);
		hearbeatStomp = null;
	}
	hearbeatStomp = subscribe(hearbeatStomp, '/wshello', '/topic/heartbeat', logMessage, {id:'heartbeat'})	
}
