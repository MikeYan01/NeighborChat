$(document).ready(function(e) {
    var currentUserInfo;
    var tempphoto;
    $.ajax({
        type: 'get',
        dataType: "jsonp",
        jsonp: "callback", 
        url: "http://localhost:8084/user/currentUserInfo",
        success: function(callback) {
            console.log(callback);
            if (document.cookie == "") {
                window.location.href ="login.html";
            }
            else {
                currentUserInfo = callback.resultObj;
                $("#upper_right_name").html(callback.resultObj.uname);
                $("#userName").html(callback.resultObj.uname);
                $("#avatar").attr("src", callback.resultObj.photo);
                tempphoto = callback.resultObj.photo;
                $("#photo").attr("src", callback.resultObj.photo);
                $("#password").val(callback.resultObj.passwd);
                $("#email").val(callback.resultObj.email);
                $("#fName").val(callback.resultObj.fName);
                $("#lName").val(callback.resultObj.lName);
                $("#addr1").val(callback.resultObj.addr1);
                $("#addr2").val(callback.resultObj.addr2);
                $("#intro").html(callback.resultObj.intro);

                if (callback.resultObj.notify == 1) $("#email_receive").attr("checked", true);
                $("#nRange").get(0).selectedIndex = callback.resultObj.nRange; 
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
        url: "http://localhost:8084/membership/getCurrentBlockInfo",
        success: function(callback) {
            console.log(callback);

            $("#blockName").html(callback.resultObj.bname);
        },
        error: function() {
            alert("Error!");
        }
    });

    $("#update").on("click", function() {
        var tempnRange, tempNotify;

        if ($("#nRange").val() == "Same Building") tempnRange = 0;
        else if ($("#nRange").val() == "Same Block") tempnRange = 1;
        else if ($("#nRange").val() == "Same Hood") tempnRange = 2;

        tempNotify = ($("#email_receive").val() == 1) ? 1 : 0;

        var newUserInfo = {
            "passwd":  $("#password").val(),
            "email": $("#email").val(),
            "fName": $("#fName").val(),
            "lName": $("#lName").val(),
            "addr1": $("#addr1").val(),
            "addr2": $("#addr2").val(),
            "intro": $("#intro").html(),
            "photo": tempphoto,
            "notify": tempNotify,
            "nRange": tempnRange
        };

        $.ajax({
            type: 'get',
            dataType: "jsonp",
            jsonp: "callback", 
            data: newUserInfo,
            url: "http://localhost:8084/user/updateInfo?uid=" + currentUserInfo.uid + "&uname=" + currentUserInfo.uname + "&passwd=" + newUserInfo.passwd + "&email=" + newUserInfo.email + "&fName=" + newUserInfo.fName + "&lName=" + newUserInfo.lName + "&addr1=" + newUserInfo.addr1 + "&addr2=" + newUserInfo.addr2
                + "&intro=" + newUserInfo.intro + "&photo=" + newUserInfo.photo + "&nRange=" + newUserInfo.nRange + "&notify=" + newUserInfo.notify,
            
            success: function(callback) {
                console.log(callback);
            },
            error: function(e) {
                console.log(e);
            }
        });
    });

    $.ajax({
        type: 'get',
        dataType: "jsonp",
        jsonp: "callback", 
        url: "http://localhost:8084/loadData/loadUserProfile",
        
        success: function(callback) {
            console.log(callback);
            $("#password").val(callback.resultObj.passwd);
            $("#email").val(callback.resultObj.email);
            $("#fName").val(callback.resultObj.fName);
            $("#lName").val(callback.resultObj.lName);
            $("#addr1").val(callback.resultObj.addr1);
            $("#addr2").val(callback.resultObj.addr2);
            $("#intro").html(callback.resultObj.intro);

            if (callback.resultObj.notify == 1) $("#email_receive").attr("checked", true);
            else $("#email_receive").attr("checked", false);
            
            $("#nRange").get(0).selectedIndex = callback.resultObj.nRange;
        },
        error: function(e) {
            console.log(e);
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

