var stompClient = null;

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}
        
function connect() {
    var socket = new SockJS('/hello');
    stompClient = Stomp.over(socket);            
}

function subscribe(channel, callback, data) {
    if(stompClient==null){
		connect();
	}

	stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe(channel, callback, data);
    });           
}

function handleMessage(msg){
	console.log(msg.content);
}

function logMessage(raw){
	var msg = JSON.parse(raw.body);
	console.log(msg);
}

function listenHeartBeat(){
	subscribe('/topic/heartbeat', logMessage, {id:'heartbeat'})	
}
