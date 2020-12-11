$(function() {
    var userValid = false, firstValid = false, lastValid = false, emailValid = false, passwordValid = false, repeatValid = false;
    var tempPassword;
    
    // check first name format
	$('#UserName').on("input", function() {
		function checkUser(name) {
            if (name == "") return "Invalid";
			for (var i = 0; i < name.length; i++)
				if (!name[i].match(/[a-zA-Z0-9_]/)) return "Invalid";
			if (name.length < 1 || name.length > 20) return "Invalid";
			return "";
        }
        var text = checkUser($(this).val());
		return (text != "") ? userValid = false : userValid = true;
    })

    // check first name format
	$('#FirstName').on("input", function() {
		function checkFirst(name) {
            if (name == "") return "Invalid";
			for (var i = 0; i < name.length; i++)
				if (!name[i].match(/[a-zA-Z0-9_]/)) return "Invalid";
			if (name.length < 1 || name.length > 20) return "Invalid";
			return "";
        }
        var text = checkFirst($(this).val());
		return (text != "") ? firstValid = false : firstValid = true;
    })
    
    // check last name format
	$('#LastName').on("input", function() {
		function checkLast(name) {
            if (name == "") return "Invalid";
			for (var i = 0; i < name.length; i++)
				if (!name[i].match(/[a-zA-Z0-9_]/)) return "Invalid";
			if (name.length < 1 || name.length > 20) return "Invalid";
			return "";
		}
        var text = checkLast($(this).val());
		return (text != "") ? lastValid = false : lastValid = true;
    })

    // check email format
	$('#InputEmail').on("input", function() {
		function checkEmail(email) {
            if (email == "") return "Invalid";
            return ((/^[a-zA-Z0-9_\-]+@(([a-zA-Z0-9_\-])+\.)+[a-zA-Z]{2,4}$/).exec(email)) ? "" : "Invalid";
		}
        var text = checkEmail($(this).val());
		return (text != "") ? emailValid = false : emailValid = true;		
	})
    
    // check password format
	$('#InputPassword').on("input", function() {
		function checkPassword(name) {
            if (name.length < 8) return "Invalid";
            else tempPassword = name;
            
            return "";
		}
        var text = checkPassword($(this).val());
		return (text != "") ? passwordValid = false : passwordValid = true;
    })

    // check repeat format
	$('#RepeatPassword').on("input", function() {
		function checkRepeat(name) {
            if (name != tempPassword) return "Invalid";
			return "";
		}
        var text = checkRepeat($(this).val());
		return (text != "") ? repeatValid = false : repeatValid = true;
    })
	
    // submit
	$("#form").submit(function (e) {
        if (!userValid) alert("Wrong username format");
        else if (!firstValid || !lastValid) alert("Wrong firstname or lastname format");
        else if (!emailValid) alert("Wrong email format");
        else if (!passwordValid) alert("Wrong password format");
        else if (!repeatValid) alert("Repeat password inconsistent");
		if (!userValid || !firstValid || !lastValid || !emailValid || !passwordValid || !repeatValid) {
			e.preventDefault();
			return;
		}
	
		var InputUserName = $('#UserName').val();
		var InputFirstName = $('#FirstName').val();
		var InputLastName = $('#LastName').val();
		var InputEmail = $('#InputEmail').val();
		var InputPassword = $('#InputPassword').val();

		var jsonObj = {
			"uname": InputUserName,
			"passwd": InputPassword,
			"email": InputEmail,
			"fName": InputFirstName,
			"lName": InputLastName
		};

		$.ajax({
			type: 'get',
			dataType: "jsonp",
			jsonp: "callback", 
			data: jsonObj,
			// url : "http://localhost:8084/user/userReg" + jsonObj,
			url: "http://localhost:8084/user/userReg?uname=" + InputUserName + "&pass=" + InputPassword + "&email=" + InputEmail + "&fName=" + InputFirstName + "&lName=" + InputLastName,
			success: function(callback) {
				console.log(callback);

				if (callback == "success") {
					$.ajax({
						type: 'get',
						dataType: "jsonp",
						jsonp: "callback", 
						data: jsonObj,
						url: "http://localhost:8084/user/loginIn?uname=" + InputUserName + "&pass=" + InputPassword,
						success: function(callback) {
							console.log(callback);
				
							$.cookie('cookieName', 'userSession');
							window.location.href ="dashboard.html";
						},
						error: function() {
							alert("No such user, or wrong password!");
						}
					});
				}
				else if (callback == "User name already in use") {
					alert("Username already in use!");
				}
				else if (callback == "failure") {
					alert("Error!");
				}
			},
			error: function() {
				alert("Error!");
			}
		});
		e.preventDefault();
	})
});