<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>${title}</title>
    <h1>Sign Up</h1>

    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="/logo/t.png"/>
    <link rel="stylesheet" href="/css/main.css">
  </head>
  <body>
     <script src="js/jquery-2.1.1.js"></script>
     <script src="js/main.js"></script>

     <br>

  <h3> Welcome to Pathway! Please enter your information below. </h3>

     <form action="/mypath" method="post">

         <div class="container">
             <label for="name"><b>Name</b></label>
             <input type="text" placeholder="Enter Name" name="name" required>

             <label for="uname"><b>Username</b></label>
             <input type="text" placeholder="Enter Username" name="uname" required>

             <label for="psw"><b>Password</b></label>
             <input type="password" placeholder="Enter Password" name="psw" required>

             <button type="submit">Sign Up</button>
         </div>

     </form>


  </body>
</html>
