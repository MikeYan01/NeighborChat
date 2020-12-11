var eventCoord;
var map;
var marker;

var isInBlock = false;

function initMap() {
    myLatLng = {lat: 40.693809, lng: -73.986622}
    map = new google.maps.Map(document.getElementById('map'), {
        center: myLatLng,
        zoom: 8
    });

    marker = new google.maps.Marker({
　　　　　position: myLatLng,
　　　　　map: map,
　　　　　title: 'Hello World!'
　　});

    google.maps.event.addListener(map,'click',function(event) {
        if (marker != null) marker.setMap(null);
        
        currentLatLng = {lat: event.latLng.lat(), lng: event.latLng.lng()};
        marker = new google.maps.Marker({
    　　    position: currentLatLng,
    　　    map: map,
    　　    title: 'Hello World!'
    　　});
        eventCoord = currentLatLng.lat + "%2C" + currentLatLng.lng
        console.log(eventCoord);
        
        marker.setMap(map);
    });
}

$(document).ready(function(e) {
    $.ajax({
        type: 'get',
        dataType: "jsonp",
        jsonp: "callback", 
        url: "http://localhost:8084/user/currentUserInfo",
        success: function(callback) {
            console.log(callback);
            if (document.cookie == "" || callback.resultCode == "USER_NOT_LOGIN_IN") {
                window.location.href ="login.html";
            }
            else {
                $("#upper_right_name").html(callback.resultObj.uname);
                $("#photo").attr("src", callback.resultObj.photo);
            }
        },
        error: function() {
            alert("No such user, or wrong password!");
        }
    });

    $.ajax({
        type: 'get',
        dataType: "jsonp",
        jsonp: "callback",
        url: "http://localhost:8084/membership/getCurrentBlockInfo",
        success: function(callback) {
            console.log(callback);
    
            if (callback.resultObj != null) {
                isInBlock = true;
            }
            else {
                alert("You need to join a block to post!")
                window.location.href ="membership.html";
            }
        },
        error: function(e) {
            console.log(e);
            alert("Error!");
        }
    });

    function getParams(key) {
        var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) {
            return unescape(r[2]);
        }
        return null;
    };

    var recipient = getParams("recipient");

    $("#submit").on("click", function(e) {
        var inputTitle = $("#inputTitle").val();
        var inputSubject = $("#inputSubject").val();
        var inputText = $("#inputText").val();

        if (inputTitle == "" || inputSubject == "" || inputText == "") {
            alert("All blocks are needed to be filled in!");
            return;
        }

        if ($("#rRange").val() != "Particular") recipient = 0;
        else if (recipient != null) {
            recipient = recipient;
        }
        else if (recipient == null) {
            alert("Cannot post particular message without specifying someone");
            window.location.href ="post.html";
        }

        var temprRange;
        if ($("#rRange").val() == "Particular") temprRange = 0;
        else if ($("#rRange").val() == "All friends") temprRange = 1;
        else if ($("#rRange").val() == "All neighbors") temprRange = 2;
        else if ($("#rRange").val() == "All people in the block") temprRange = 3;
        else if ($("#rRange").val() == "All people in the hood") temprRange = 4;

        $.ajax({
            type: 'get',
            dataType: "jsonp",
            jsonp: "callback", 
            url: "http://localhost:8084/message/addMessage?recipient=" + recipient + "&rRange=" + temprRange + "&title=" + inputTitle + "&sub=" + inputSubject + "&txt=" + inputText + "&coord=" + eventCoord,
            success: function(callback) {
                console.log(callback);
                alert("Successfully posted!");
                window.location.href ="message.html";
            },
            error: function(e) {
                console.log(e);
                e.preventDefault();
            }
        });
    });

    $("#logout").on("click", function() {
        function clearAllCookie() {
            var keys = document.cookie.match(/[^ =;]+(?=\=)/g);
            if (keys) {
                for (var i = keys.length; i--;)
                    document.cookie = keys[i] + '=0;expires=' + new Date(0).toUTCString()
            }
        }
        clearAllCookie();
    })
});

