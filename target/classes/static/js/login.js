$("#form").submit(function (e) {
    var InputName = $('#InputName').val();
    var InputPassword = $('#InputPassword').val();

    if (InputName == "" || InputPassword == "") {
        alert("Username or password cannot be null");
        e.preventDefault();
        return;
    }

    var jsonObj = {
        "uname": InputName,
        "pass": InputPassword,
    };

    $.ajax({
        type: 'get',
        dataType: "jsonp",
        jsonp: "callback", 
        data: jsonObj,
        url: "http://localhost:8084/user/loginIn?uname=" + InputName + "&pass=" + InputPassword,
        success: function(callback) {
            console.log(callback);
            if (callback.resultDesc == "username and password do not match") {
                alert("No such user, or wrong password!");
                return;
            } else {
                $.cookie('cookieName', 'userSession');
                window.location.href ="dashboard.html";
            }
        },
        error: function() {
            
        }
    });
    e.preventDefault();
})