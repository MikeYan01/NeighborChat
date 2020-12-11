var map;
var marker;

function initMap() {
    myLatLng = {lat: 40.693809, lng: -73.986622}
    map = new google.maps.Map(document.getElementById('map'), {
        center: myLatLng,
        zoom: 14
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

        console.log(currentLatLng);
        
        marker.setMap(map);
    });

    map2 = new google.maps.Map(document.getElementById('map2'), {
        center: myLatLng,
        zoom: 14
    });

    marker = new google.maps.Marker({
　　　　　position: myLatLng,
　　　　　map: map2,
　　　　　title: 'Hello World!'
　　});

    google.maps.event.addListener(map2,'click',function(event) {
        if (marker != null) marker.setMap(null);
        
        currentLatLng = {lat: event.latLng.lat(), lng: event.latLng.lng()};
        marker = new google.maps.Marker({
    　　    position: currentLatLng,
    　　    map: map2,
    　　    title: 'Hello World!'
    　　});

        marker.setMap(map2);

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

