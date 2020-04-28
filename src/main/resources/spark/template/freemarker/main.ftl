<!DOCTYPE html>
  <head>
    <meta charset="utf-8">

    <title>${title}</title>
    <h1>Welcome to Pathway</h1>

    <p>Your personalized concentration pathway generator for Brown University.</p>
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="/logo/t.png"/>
    <link rel="stylesheet" href="/css/main.css">
  </head>
  <body>
     <script src="js/jquery-2.1.1.js"></script>
     <script src="js/main.js"></script>

     <br>

     <!-- Login form test -->
     <h2>Login</h2>

     <form action="/generate" method="post">

         <div class="container">
             <label name="username" id="username" for="uname"><b>${username}</b></label>
             <input name="username" id="username"
                    type="text" placeholder="Enter Username" name="uname" value="" required>

             <label name="password" id="password" for="psw"><b>Password</b></label>
             <input name="password" id="password"
                    type="password" placeholder="Enter Password" name="psw" required>

             <button type="submit">Login</button>
         </div>

     </form>
     <br>
     <form action="/generate" method="post">
         <button type="submit">Login as Guest</button>
     </form>

  <h3> <a href="/signup" class="link"> Sign Up </a> </h3>

     <br>

  <p> <small> For any questions or ideas about how Pathway can be improved, please
          <a href = "mailto: melissa_cui@brown.edu" class = "link"> send us an email! </a>
      </small>
  </p>

  </body>

</html>
