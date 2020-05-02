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
<!--Start: Nav  -->
<div class="ui fixed borderless huge menu">
    <div class="ui container grid">
        <!--Start: Desktop Nav-->
        <div class="computer only row">
            <form action="/login" method="post">
                <a class="header item">Pathway</a>
            </form>
            <a class="active item">Sign Up</a>
            <form action="/faqs" method="post">
                <a href="/faqs" class="item">FAQ</a>
            </form>
        </div>
        <!--End: Desktop Nav-->

        <!--Start: Mobile Nav-->
        <div class="tablet mobile only row">
            <div class="right menu">
                <a class="menu item">
                    <div class="ui basic icon toggle button">
                        <i class="content icon"></i>
                    </div>
                </a>
            </div>
            <div class="ui vertical accordion borderless fluid menu">
                <!-- Start: Search -->
                <!-- End: Search -->
                <a class="active item"> Home</a>
                <form action="/faqs" method="post">
                    <a class="item">FAQ</a>
                </form>
                <a class="item"> Login</a>
                <div class="ui divider"></div>
                <a class="item" href="#">Default</a>
                <a class="item" href="#">Static top</a>
                <a class="active item" href="#">Fixed top</a>
            </div>
        </div>
        <!--End: Mobile Nav-->
    </div>
</div>
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
