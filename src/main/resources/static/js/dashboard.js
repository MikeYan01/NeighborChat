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
            if (document.cookie == "") {
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

    // block number
    $.ajax({
        type: 'get',
        dataType: "jsonp",
        jsonp: "callback", 
        url: "http://localhost:8084/message/countNewMessageFromBlock",
        success: function(callback) {
            console.log(callback);
            if (callback.resultObj != null) {
                $("#block_count").html(callback.resultObj);
            }
            else $("#block_count").html("0");
        },
        error: function() {
            alert("Error!");
        }
    });

    // hood number
    $.ajax({
        type: 'get',
        dataType: "jsonp",
        jsonp: "callback", 
        url: "http://localhost:8084/message/countNewMessageFromHood",
        success: function(callback) {
            console.log(callback);
            if (callback.resultObj != null) {
                $("#hood_count").html(callback.resultObj);
            }
            else $("#hood_count").html("0");
        },
        error: function() {
            alert("Error!");
        }
    });

    particular_msg = [];
    friend_msg = [];
    neighbor_msg = [];
    block_msg = [];
    hood_msg = [];

    $.ajax({
        type: 'get',
        dataType: "jsonp",
        jsonp: "callback", 
        url: "http://localhost:8084/loadData/loadAllMessageThread",
        success: function(callback) {
            console.log(callback);
            $("#allParticularMessages").empty();
            $("#allFriendMessages").empty();
            $("#allNeighborMessages").empty();
            $("#allBlockMessages").empty();
            $("#allHoodMessages").empty();
            
            obj_list = [];
            if (callback.resultObj != null) {
                obj_list = callback.resultObj;

                message_count = callback.resultObj.length;

                uphoto_list = [];
                uname_list = [];
                uid_list = [];
                author_list = [];
                mTime_list = [];
                title_list = [];
                txt_list = [];
                

                for (var i = 0; i < callback.resultObj.length; i++) {
                    var uphoto, uname, uid;
                    var author = callback.resultObj[i].author;
                    var mTime = convertTime(callback.resultObj[i].mTime);
                    var title = callback.resultObj[i].title;
                    var txt = callback.resultObj[i].txt;

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

                        var tempRowHTML2 = "<div class='mail-contnet'><h5>" + uname_list[i] + "</h5><span class='time'>" + mTime_list[i] + "</span><br/><span class='mail-desc'><a href = 'message_detail.html'><h5>" + title_list[i] + "</h5>" + txt_list[i] + "</div></div>";

                        if (obj_list[i].rRange == 0) $("#allParticularMessages").append(tempRowHTML1 + tempRowHTML2);
                        if (obj_list[i].rRange == 1) $("#allFriendMessages").append(tempRowHTML1 + tempRowHTML2);
                        else if (obj_list[i].rRange == 2) $("#allNeighborMessages").append(tempRowHTML1 + tempRowHTML2);
                        else if (obj_list[i].rRange == 3) $("#allBlockMessages").append(tempRowHTML1 + tempRowHTML2);
                        else if (obj_list[i].rRange == 4) $("#allHoodMessages").append(tempRowHTML1 + tempRowHTML2);  
                    }
                });
            }
        },
        error: function() {
            alert("Error!");
        }
    });


    $("#logout").on("click", function() {
        $.ajax({
            type: 'get',
            dataType: "jsonp",
            jsonp: "callback",
            url: "http://localhost:8084/user/logOut",
            success: function(callback) {
                console.log(callback);
                alert("successfully logged out");
                window.location.href ="login.html";
            },
            error: function() {
                alert("No such user, or wrong password!");
            }
        });
    })








    "use strict";
     // toat popup js
     $.toast({
         heading: 'Welcome to neighborChat!',
         position: 'top-right',
         loaderBg: '#fff',
         hideAfter: 3000,
         stack: 6
     })


     //ct-visits
     new Chartist.Line('#ct-visits', {
         labels: ['2008', '2009', '2010', '2011', '2012', '2013', '2014', '2015'],
         series: [
    [5, 2, 7, 4, 5, 3, 5, 4]
    , [2, 5, 2, 6, 2, 5, 2, 4]
  ]
     }, {
         top: 0,
         low: 1,
         showPoint: true,
         fullWidth: true,
         plugins: [
    Chartist.plugins.tooltip()
  ],
         axisY: {
             labelInterpolationFnc: function (value) {
                 return (value / 1) + 'k';
             }
         },
         showArea: true
     });
     // counter
     $(".counter").counterUp({
         delay: 100,
         time: 1200
     });

     var sparklineLogin = function () {
         $('#sparklinedash').sparkline([0, 5, 6, 10, 9, 12, 4, 9], {
             type: 'bar',
             height: '30',
             barWidth: '4',
             resize: true,
             barSpacing: '5',
             barColor: '#7ace4c'
         });
         $('#sparklinedash2').sparkline([0, 5, 6, 10, 9, 12, 4, 9], {
             type: 'bar',
             height: '30',
             barWidth: '4',
             resize: true,
             barSpacing: '5',
             barColor: '#7460ee'
         });
         $('#sparklinedash3').sparkline([0, 5, 6, 10, 9, 12, 4, 9], {
             type: 'bar',
             height: '30',
             barWidth: '4',
             resize: true,
             barSpacing: '5',
             barColor: '#11a0f8'
         });
         $('#sparklinedash4').sparkline([0, 5, 6, 10, 9, 12, 4, 9], {
             type: 'bar',
             height: '30',
             barWidth: '4',
             resize: true,
             barSpacing: '5',
             barColor: '#f33155'
         });
     }
     var sparkResize;
     $(window).on("resize", function (e) {
         clearTimeout(sparkResize);
         sparkResize = setTimeout(sparklineLogin, 500);
     });
     sparklineLogin();
});

