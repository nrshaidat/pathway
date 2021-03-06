<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>Pathway FAQs</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="/logo/t.png"/>
    <link rel="stylesheet" href="/css/faqs.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.js"></script>
</head>
<body>
    <!--Start: Nav  -->
    <div class="ui fixed borderless huge menu">
        <div class="ui container grid">
            <!--Start: Desktop Nav-->
            <div class="computer only row">
                <form action="/login" method="get">
                    <a href ="/login" class="header item">Pathway</a>
                </form>
                <form action="/home" method="post">
                    <a href="/home" class="item">Back to Home</a>
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
            </div>
            <!--End: Mobile Nav-->
        </div>
    </div>

    <br>
    <br>
    <br>
    <h1>Frequently Asked Questions</h1>

    <div class="no-fouc"></div>

     <br>

    <div style="margin: 0 auto;">
     <p style="margin-left:25vw; font-size:1.3em;" align="left"> 1. How do we generate concentration pathways? </p>

     <div class="content" style="margin-left:25vw;" align="left">We take into account whether the student wants their concentration
         to be completed quickly or not, as well as the courses they have received credit for to generate
         optimal pathways! We always give three options with varying workloads plus we make sure that if it's
         feasible, the pathway completes in the spring of their senior year, at latest. We also make sure
         to prioritize courses with good ratings so that the student gets the best courses in their pathway!
     </div>

     <br>
     <p style="margin-left:25vw; font-size:1.3em;" align="left"> 2. Where do we get course information? </p>

     <div class="content" style="margin-left:25vw;" align="left"> For Brown students we generate pathways by using data
         from <a href = "https://thecriticalreview.org/" class="link"> Critical Review </a> and
         <a href = "https://cab.brown.edu/" class="link"> Courses@Brown. </a> We currently support all
         undergraduate courses!
     </div>

     <br>
     <p style="margin-left:25vw; font-size:1.3em;" align="left"> 3. Can I edit my pathway? </p>

     <div class="content" style="margin-left:25vw;" align="left"> Yes! If you click on a semester, you can edit your pathway by
         adding, removing, or swapping out a course for another. </div>

    </div>

     <br>
     <br>
     <br>
     <br>


      <p> <small> For any questions or ideas about how Pathway can be improved, please
              <a href = "mailto: melissa_cui@brown.edu" class = "link"> send us an email! </a>
          </small>
      </p>

</body>
<script>
    $(function() {
        $('.no-fouc').removeClass('no-fouc');
        $('#dialog').dialog();
    });
</script>

</html>