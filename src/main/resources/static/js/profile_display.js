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
                $("#avatar").attr("src", callback.resultObj.photo);
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

    var current_uid = getParams("uid");
    
    $.ajax({
        type: 'get',
        dataType: "jsonp",
        jsonp: "callback", 
        url: "http://localhost:8084/user/getUserByUid?uid=" + current_uid,
        success: function(callback) {
            console.log(callback);

            $("#email").html(callback.resultObj.email);
            $("#fName").html(callback.resultObj.fName);
            $("#lName").html(callback.resultObj.lName);
            $("#addr1").html(callback.resultObj.addr1);
            $("#addr2").html(callback.resultObj.addr2);
            $("#intro").html(callback.resultObj.intro);
            $("#uname").html(callback.resultObj.uname);
            $("#photo").attr("src", callback.resultObj.photo);
        },
        error: function() {
            alert("No such user, or wrong password!");
        }
    });

    
    $.ajax({
        type: 'get',
        dataType: "jsonp",
        jsonp: "callback", 
        url: "http://localhost:8084/membership/getBlockByUid?uid=" + current_uid,
        success: function(callback) {
            console.log(callback);

            $("#blockName").html(callback.resultObj.bname);

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

