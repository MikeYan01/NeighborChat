$(document).ready(function(e) {
    var isInBlock = false;

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

    // friend list
    $.ajax({
        type: 'get',
        dataType: "jsonp",  
        jsonp: "callback", 
        url: "http://localhost:8084/loadData/loadFriendCompleteList",
        success: function(callback) {
            console.log(callback);
            
            $("#friendList").empty();
            for (var i = 0; i < callback.resultObj.length; i++) {
                var uid = callback.resultObj[i].uid;

                var tempRowHTML = "<li><div class='call-chat'><button class='btn btn-info btn-circle btn-lg' type='button'><a href = 'post.html?recipient=" + uid + "'><i class='fa fa-comments-o'></i></a></button><button class='btn btn-danger btn-circle btn-lg' type='button'><a id = 'deleteFriend' href = '#'><i class='fa fa-times deleteFriend'></i></a></button></div><a href='profile_display.html?uid=" + uid + "'><img src='" + callback.resultObj[i].photo + "' alt='user-img' class='img-circle'> <span>" + callback.resultObj[i].uname + "</span></a></li>";

                $("#friendList").append(tempRowHTML);
            }
        },
        error: function() {
            alert("Load messages error!");
        }
    });

    $(".deleteFriend").on("click", function(e) {
        console.log("ddd");
    });
    // block
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
                $("#neighborList").empty();
                $("#PendingBlockApplications").empty();
                $("#strangerList").empty();
            }
        },
        error: function(e) {
            console.log(e);
            alert("Error!");
        }
    });

    // neighbor list
    $.ajax({
        type: 'get',
        dataType: "jsonp",
        jsonp: "callback", 
        url: "http://localhost:8084/loadData/loadNeighborCompleteList",
        success: function(callback) {
            console.log(callback);
            
            $("#neighborList").empty();
            for (var i = 0; i < callback.resultObj.length; i++) {
                var uid = callback.resultObj[i].uid;

                var tempRowHTML = "<li><div class='call-chat'><button class='btn btn-info btn-circle btn-lg' type='button'><a href = 'post.html?recipient=" + uid + "'><i class='fa fa-comments-o'></i></a></button><button class='btn btn-danger btn-circle btn-lg' type='button'><i class='fa fa-times'></i></button></div><a href='profile_display.html?uid=" + uid + "'><img src='" + callback.resultObj[i].photo + "' alt='user-img' class='img-circle'> <span>" + callback.resultObj[i].uname + "</span></a></li>";

                $("#neighborList").append(tempRowHTML);
            }
        },
        error: function() {
            alert("Load messages error!");
        }
    });

    // Pending Applications
    $.ajax({
        type: 'get',
        dataType: "jsonp",
        jsonp: "callback",
        url: "http://localhost:8084/notify/notifyNewFriendApplicationAsRecipient",
        success: function(callback) {
            console.log(callback);

            if (callback.resultObj != null) {
                $("#allApplications").empty();
                

                for (var i = 0; i < callback.resultObj.length; i++) {
                    var uphoto, uname, uid;
                    var applicant = callback.resultObj[i].applicant;
                    var appliTime = convertTime(callback.resultObj[i].faTime);
                    var appliTXT = callback.resultObj[i].txt;
                    
                    myajax = $.ajax({
                        type: 'get',
                        dataType: "jsonp",
                        jsonp: "callback",
                        async: false,
                        url: "http://localhost:8084/user/getUserByUid?uid=" + applicant,
                        success: function(callback) {
                            console.log(callback);
                            uname = callback.resultObj.fName + "    " + callback.resultObj.lName;
                            uphoto = callback.resultObj.photo;
                            uid = callback.resultObj.uid;
                        },
                        error: function(e) {
                            console.log(e);
                            alert("Error!");
                        }
                    });

                    $.when(myajax).done(function(){
                        var tempRowHTML1 = "<div class='comment-body'><div class='user-img'> <a href='profile_display.html?uid=" + uid + "'><img src=" + uphoto + " alt='user' class='img-circle'></a></div>"

                        var tempRowHTML2 = "<div class='mail-contnet'><h5>" + uname + "</h5><span class='time'>" + appliTime
                            + "</span><br/><span class='mail-desc'>" + appliTXT + "</span> <a href='#' class='btn btn btn-rounded btn-default btn-outline m-r-5'><i class='ti-check text-success m-r-5'></i>Approve</a><a href='#' class='btn-rounded btn btn-default btn-outline'><i class='ti-close text-danger m-r-5'></i> Reject</a></div></div>";
    
                        $("#allApplications").append(tempRowHTML1 + tempRowHTML2);
                    });
                }
            }
            else $("#PendingBlockApplications").empty();

        },
        error: function(e) {
            console.log(e);
            alert("Error!");
        }
    });

    // strangers nearby
    $.ajax({
        type: 'get',
        dataType: "jsonp",
        jsonp: "callback",
        url: "http://localhost:8084/loadData/loadStrangerFromHood",
        success: function(callback) {
            console.log(callback);

            $("#strangerList").empty();
            if (callback.resultObj != null) {
                for (var i = 0; i < callback.resultObj.length; i++) {
                    var id = callback.resultObj[i].uid;
                    var fName = callback.resultObj[i].fName;
                    var lName = callback.resultObj[i].lName;
                    var name = fName + "     " + lName;
                    var uName = callback.resultObj[i].uname;
    
                    var tempRowHTML = "<tr><td>" + id + "</td><td>" + name + "</td><td><a href='profile_display.html?uid=" + id + "'>@" + uName + 
                        "</a></td><td><button class='btn btn-success'>Send</button></td><td><button class='btn btn-success'>Set</button></td></tr>";
                    $("#strangerList").append(tempRowHTML);
                }
            }
        },
        error: function(e) {
            console.log(e);
            alert("Error!");
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

