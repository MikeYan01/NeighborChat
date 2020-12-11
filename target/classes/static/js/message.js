$(document).ready(function(e) {
    function convertTime(time = +new Date()) {
        var date = new Date(time + 8 * 3600 * 1000);
        return date.toJSON().substr(0, 19).replace('T', ' ').replace(/-/g, '.');
    }

    var unread_list = [];

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
        url: "http://localhost:8084/message/getMsgidUnread",
        success: function(callback) {
            console.log(callback);
            if (callback.resultObj != null) {
                for (var i = 0; i < callback.resultObj.length; i++) {
                    unread_list.push(callback.resultObj[i]);
                }
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
        url: "http://localhost:8084/message/getAllMessageThreads",
        success: function(callback) {
            console.log(callback);

            $("#allMessages").empty();
            if (callback.resultObj != null) {

                message_count = callback.resultObj.length;

                msgid_list = [];
                uphoto_list = [];
                uname_list = [];
                uid_list = [];
                author_list = [];
                mTime_list = [];
                title_list = [];
                txt_list = [];

                for (var i = 0; i < callback.resultObj.length; i++) {
                    var uphoto, uname;
                    var msgid = callback.resultObj[i].msgid;
                    var author = callback.resultObj[i].author;
                    var mTime = convertTime(callback.resultObj[i].mTime);
                    var title = callback.resultObj[i].title;
                    var txt = callback.resultObj[i].txt;

                    msgid_list.push(msgid);
                    author_list.push(author);
                    mTime_list.push(mTime);
                    title_list.push(title);
                    txt_list.push(txt);
                    
                    myajax = $.ajax({
                        type: 'get',
                        dataType: "jsonp",
                        jsonp: "callback",
                        async: false,
                        url: "http://localhost:8084/user/getUserByUid?uid=" + author,
                        success: function(callback) {
                            console.log(callback);
                            uname = callback.resultObj.fName + "    " + callback.resultObj.lName;
                            uphoto = callback.resultObj.photo;
                            uid = callback.resultObj.uid;

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
                        var tempRowHTML1 = "<div class='comment-body'><div class='user-img'> <a href='profile_display.html?uid=" + uid_list[i] + "'><img src=" + uphoto_list[i] + " alt='user' class='img-circle'></a></div>"

                        var tempRowHTML2 = "<div class='mail-contnet'><h5>" + uname_list[i] + "</h5><span class='time'>" + mTime_list[i] + "</span><br/><span class='mail-desc'><a href = 'message_detail.html?msgid=" + msgid_list[i] + "&photo=" + uphoto_list[i] + "&uname=" + uname_list[i] + "'><h5>" + title_list[i] + "</h5>" + txt_list[i] + "</div></div>";

                        if (unread_list.indexOf(msgid_list[i]) != -1) $("#allMessages").append("<b>" + tempRowHTML1 + tempRowHTML2 + "</b>");
                        else $("#allMessages").append(tempRowHTML1 + tempRowHTML2);
                    }
                });
            }

        },
        error: function() {
            alert("Error!");
        }
    });

    $("#search").on("click", function(e) {
        var keyword = $("#searchContent").val();
        $.ajax({
            type: 'get',
            dataType: "jsonp",
            jsonp: "callback", 
            url: "http://localhost:8084/message/searchMsgByKeyword?keyword=" + keyword,
            success: function(callback) {
                console.log(callback);
                if (callback.resultObj == "keyword empty!") {
                    alert("keyword empty!");
                    e.preventDefault();
                }
                else {
                    $("#allMessages").empty();
                    message_count = callback.resultObj.length;

                    msgid_list = [];
                    uphoto_list = [];
                    uname_list = [];
                    uid_list = [];
                    author_list = [];
                    mTime_list = [];
                    title_list = [];
                    txt_list = [];

                    for (var i = 0; i < callback.resultObj.length; i++) {
                        var uphoto, uname;
                        var msgid = callback.resultObj[i].msgid;
                        var author = callback.resultObj[i].author;
                        var mTime = convertTime(callback.resultObj[i].mTime);
                        var title = callback.resultObj[i].title;
                        var txt = callback.resultObj[i].txt;

                        msgid_list.push(msgid);
                        author_list.push(author);
                        mTime_list.push(mTime);
                        title_list.push(title);
                        txt_list.push(txt);
                        
                        myajax = $.ajax({
                            type: 'get',
                            dataType: "jsonp",
                            jsonp: "callback",
                            async: false,
                            url: "http://localhost:8084/user/getUserByUid?uid=" + author,
                            success: function(callback) {
                                console.log(callback);
                                uname = callback.resultObj.fName + "    " + callback.resultObj.lName;
                                uphoto = callback.resultObj.photo;
                                uid = callback.resultObj.uid;

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
                            var tempRowHTML1 = "<div class='comment-body'><div class='user-img'> <a href='profile_display.html?uid=" + uid_list[i] + "'><img src=" + uphoto_list[i] + " alt='user' class='img-circle'></a></div>"

                            var tempRowHTML2 = "<div class='mail-contnet'><h5>" + uname_list[i] + "</h5><span class='time'>" + mTime_list[i] + "</span><br/><span class='mail-desc'><a href = 'message_detail.html?msgid=" + msgid_list[i] + "&photo=" + uphoto_list[i] + "&uname=" + uname_list[i] + "'><h5>" + title_list[i] + "</h5>" + txt_list[i] + "</div></div>";

                            if (unread_list.indexOf(msgid_list[i]) == -1) $("#allMessages").append("<b>" + tempRowHTML1 + tempRowHTML2 + "</b>");
                            else $("#allMessages").append(tempRowHTML1 + tempRowHTML2);
                        }
                    });
                }   
            },
            error: function() {
                alert("Error!");
            }
        });
    })


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

