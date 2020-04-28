<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="/logo/t.png"/>
    <link rel="stylesheet" href="/css/signup.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.js"></script>
    <script src="js/jquery-2.1.1.js"></script>
    <script src="js/main.js"></script>
  </head>
  <body style="visibility: hidden;" onload= "js_Load()">

  <br>
  <br>
  <br>


     <h1>Sign Up</h1>
     <h3> Welcome to Pathway! Please enter your information below. </h3>
     <div class="container">
         <label for="firstname"><b>First Name</b></label>
         <input type="text" placeholder="Elon" name="firstname" required>

         <label for="lastname"><b>Last Name</b></label>
         <input type="text" placeholder="Muskerton" name="lastname" required>

         <label for="email"><b>Email</b></label>
         <input type="text" placeholder="elon_muskerton@hotmail.com" name="email" required>

<#--         <label for="uname"><b>Username</b></label>-->
<#--         <input type="text" placeholder="Enter Username" name="uname" required>-->

<#--         <label for="psw"><b>Password</b></label>-->
<#--         <input type="password" placeholder="Enter Password" name="psw" required>-->
         <form method="POST" action="/generate">
             <button id="submit" type="submit" value="Next">
                 <i class="fa fa-arrow-circle-right"></i>
             </button>
         </form>
     </div>

  </body>
<script>function js_Load() {
        document.body.style.visibility='visible';
    }
</script>
<noscript><style> body { visibility: visible; }</style></noscript>
</html>
