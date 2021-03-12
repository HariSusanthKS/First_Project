<html>
  <head><title>Login</title>
  <style>
.for {
	align:center;
	background-color:#8cb3d9;
	border: 3px solid black;
	padding-top: 50px;
	padding-right: 80px;
	padding-bottom: 50px;
	padding-left: 80px;
	border-radius: 40px 40px 40px 40px;
	width: 200px;
	margin-top: 140px;
	margin-left: 600px;
}
</style>
  <meta name="google-signin-client_id" content="850398037024-up0jr97g6s6fon0bmlt78vi3qr0b60n3.apps.googleusercontent.com">
  <script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
  </head>
  <body bgcolor=" #204060">
  <div class="for"  align="center" >
  <h3>Enter your username and password<h3>
        <form name="loginForm" method="POST" action="j_security_check">
		
		
            <p>User name: <input type="text" id ="j_username" name="j_username" size="20"/></p>
            <p>Password: <input type="password" id="j_password"  size="20" name="j_password"/></p>
            
         	<!--google sigin button -->
		<p> <div id="my-signin2" ></div></p>
		<p>  <input type="submit" id="fo" value="Submit"/></p>
		 </form>       
		 <a href="#" onclick="signOut();">Sign out</a>
	</div>
  <script>
    function onSuccess(googleUser) {
	  var profile = googleUser.getBasicProfile();
		var username = profile.getEmail();
		var password= profile.getName();
	   document.getElementById("j_username").value = username;
	   document.getElementById("j_password").value = password;
    }
    function onFailure(error) {
      console.log(error);
    }
    function renderButton() {
      gapi.signin2.render('my-signin2', {
        'scope': 'profile email',
        'width': 120,
        'height': 30,
        'theme': 'dark',
        'onsuccess': onSuccess,
        'onfailure': onFailure
      });
    }
	function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
      console.log('User signed out.');
	  document.getElementById("j_username").value = "";
	   document.getElementById("j_password").value = "";
    });
  }

  </script>
 
		   
  <script src="https://apis.google.com/js/platform.js?onload=renderButton" async defer></script>
 
  
   </body>
</html>
