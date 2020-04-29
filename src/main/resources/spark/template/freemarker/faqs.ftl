<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <!--Start: Nav  -->
    <div class="ui fixed borderless huge menu">
        <div class="ui container grid">
            <!--Start: Desktop Nav-->
            <div class="computer only row">
                <a class="header item">Pathway</a>
                <a class="active item">Home</a>
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
]    <h1>Frequently Asked Questions</h1>

    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="/logo/t.png"/>
    <link rel="stylesheet" href="/css/faqs.css">
  </head>
  <body>
     <script src="js/jquery-2.1.1.js"></script>
     <script src="js/main.js"></script>

     <br>

     <p style="margin-left:20em;" align="left"> 1. How do we generate concentrations? </p>

     <div class="content" style="margin-left:23em;" align="left">  We take into account each student's workload preferences,
         whether they want their pathway to be aggressively completed (in the least number of semesters) or not,
         as well as the courses they have received credit for in the past to generate the most optimal concentration!
     </div>

     <p style="margin-left:20em;" align="left"> 2. Where do we get course information? </p>

     <div class="content" style="margin-left:23em;" align="left"> For Brown students we generate pathways by using data
         from <a href = "https://thecriticalreview.org/" class="link"> Critical Review </a> and
         <a href = "https://cab.brown.edu/" class="link"> Courses@Brown. </a> We currently support all
         undergraduate courses!
     </div>

     <p style="margin-left:20em;" align="left"> 3. Can I edit my pathway? </p>

     <div class="content" style="margin-left:23em;" align="left"> We are currently working on implementing pathway edit functionality!
         Stay tuned for more progress on Pathway! </div>

     <br>
     <br>
     <br>
     <br>


  <p> <small> For any questions or ideas about how Pathway can be improved, please
          <a href = "mailto: melissa_cui@brown.edu" class = "link"> send us an email! </a>
      </small>
  </p>

  </body>

</html>