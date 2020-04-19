<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <h1>Welcome to Pathway</h1>

    <p>Your personalized course generator for Brown University.
        Make course planning easier with our concentration pathway generator!</p>
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- In real-world webapps, css is usually minified and
         concatenated. Here, separate normalize from our code, and
         avoid minification for clarity. -->

    <link rel="shortcut icon" href="/logo/t.png"/>
    <link rel="stylesheet" href="/css/main.css">
  </head>
  <body>

     <!-- Again, we're serving up the unminified source for clarity. -->
     <script src="js/jquery-2.1.1.js"></script>
     <script src="js/main.js"></script>

     <!-- Login form test -->
     <h2>Login</h2>

     <form action="/pathway" method="post">

         <div class="container">
             <label for="uname"><b>Username</b></label>
             <input type="text" placeholder="Enter Username" name="uname" required>

             <label for="psw"><b>Password</b></label>
             <input type="password" placeholder="Enter Password" name="psw" required>

             <button type="submit">Login</button>

         </div>

     </form>

  </body>
  <!-- See http://html5boilerplate.com/ for a good place to start
       dealing with real world issues like old browsers.  -->
</html>