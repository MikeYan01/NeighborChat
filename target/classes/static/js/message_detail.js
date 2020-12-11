var eventCoord;
var map, map2;

function initMap() {
    myLatLng = {lat: 40.693809, lng: -73.986622}
    map = new google.maps.Map(document.getElementById('map'), {
        center: myLatLng,
        zoom: 8
    });

    marker = new google.maps.Marker({
　　　　　position: null,
　　　　　map: map,
　　　　　title: 'Hello World!'
　　});


    map2 = new google.maps.Map(document.getElementById('map2'), {
        center: myLatLng,
        zoom: 8
    });

    marker = new google.maps.Marker({
　　　　　position: null,
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
        eventCoord = currentLatLng.lat + "%2C" + currentLatLng.lng
        console.log(eventCoord);

        marker.setMap(map2);

    });
}

$(document).ready(function(e) {
    function convertTime(time = +new Date()) {
        var date = new Date(time + 8 * 3600 * 1000);
        return date.toJSON().substr(0, 19).replace('T', ' ').replace(/-/g, '.');
    }

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

    function getParams(key) {
        var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) {
            return unescape(r[2]);
        }
        return null;
    };

    var msg_id = getParams("msgid");
    var photo = getParams("photo");
    var uname = getParams("uname");

    $.ajax({
        type: 'get',
        dataType: "jsonp",
        jsonp: "callback", 
        url: "http://localhost:8084/message/getMessageByThread?msgid=" + msg_id,
        success: function(callback) {
            console.log(callback);

            $.ajax({
                type: 'get',
                dataType: "jsonp",
                jsonp: "callback", 
                url: "http://localhost:8084/message/setMessageRead?msgid=" + msg_id,
                success: function(callback) {
                    console.log(callback);
                },
                error: function() {
                    alert("Error!");
                }
            });

            author = callback.resultObj.author;
            $("#profileLink").attr("href", "profile_display.html?uid=" + author);
            $("#avatar").attr("src", photo);
            $("#uname").html(uname);
            $("#time").html(convertTime(callback.resultObj.mTime));
            $("#title").html(callback.resultObj.title);
            $("#text").html(callback.resultObj.txt);
            

            recordLatLng = callback.resultObj.coord.split(",");
            newlat = parseFloat(recordLatLng[0]);
            newlng = parseFloat(recordLatLng[1]);



            if (callback.resultObj.coord == "" || callback.resultObj.coord == null) $("#mapContainer").empty();
            else {
                marker = new google.maps.Marker({
                    position: {lat: newlat, lng: newlng},
            　　     map: map,
                    title: 'Hello World!'
            　　});
            }
        },
        error: function() {
            alert("Error!");
        }
    });

    $.ajax({
        type: 'get',
        dataType: "jsonp",
        jsonp: "callback", 
        url: "http://localhost:8084/message/getReplyByThread?msgid=" + msg_id,
        success: function(callback) {
            console.log(callback);
            $("#allReplies").empty();

            if (callback.resultObj != null) {
                uphoto_list = [];
                uname_list = [];
                uid_list = [];
                rTime_list = [];
                txt_list = [];

                message_count = callback.resultObj.length

                for (var i = 0; i < message_count; i++) {
                    var uphoto, uname;
                    var uid = callback.resultObj[i].uid;
                    var rTime = callback.resultObj[i].rTime;
                    var txt = callback.resultObj[i].txt;

                    uid_list.push(uid);
                    rTime_list.push(rTime);
                    txt_list.push(txt);
                    
                    myajax = $.ajax({
                        type: 'get',
                        dataType: "jsonp",
                        jsonp: "callback",
                        async: false,
                        url: "http://localhost:8084/user/getUserByUid?uid=" + uid,
                        success: function(callback) {
                            console.log(callback);
                            uname = callback.resultObj.fName + "    " + callback.resultObj.lName;
                            uphoto = callback.resultObj.photo;

                            uphoto_list.push(uphoto);
                            uname_list.push(uname);
                            uid_list.push(uid);
                        },
                        error: function(e) {
                            console.log(e);
                            alert("Error!");
                        }
                    });
                }
                
                $.when(myajax).done(function(){
                    for (var i = 0; i < message_count; i++) {
                        var textHTML = "<div class='comment-body'><div class='user-img'><img src=" + uphoto_list[i]+ "alt='user' class='img-circle'></div><div class='mail-content'><h5>" + uname_list[i] + "</h5><span class='time'>" + convertTime(rTime_list[i]) + "</span><br/><span class='mail-desc'>" + txt_list[i] + "</span></div></div>";

                        $("#allReplies").append(textHTML);
                    }
                });
            }
        },
        error: function() {
            alert("Error!");
        }
    });

    $("#submit").on("click", function(e) {
        var txt = $("#reply_txt").val();

        $.ajax({
            type: 'get',
            dataType: "jsonp",
            jsonp: "callback", 
            url: "http://localhost:8084/message/addReply?msgid=" + msg_id + "&txt=" + txt + "&coord=" + eventCoord,
            success: function(callback) {
                console.log(callback);
                alert("Successfully posted!");
                window.location.href ="message_detail.html?msgid=" + msg_id;
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

