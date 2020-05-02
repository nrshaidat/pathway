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
                <form action="/login" method="post">
                    <a class="header item">Pathway</a>
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
    <h1>Frequently Asked Questions</h1>

    <div class="no-fouc"></div>

     <br>

     <p style="margin-left:20em;" align="left"> 1. How do we generate concentrations? </p>

     <div class="content" style="margin-left:23em;" align="left">  We take into account each student's workload preferences,
         whether they want their pathway to be aggressively completed (in the least number of semesters) or not,
         as well as the courses they have received credit for in the past to generate the most optimal concentration!

         We use a weighted shuffle algorithm that takes the course requirements in a concentration, and finds the best courses to take
         depending on information you put in, like how fast you prefer your concentration to be finished, semester level, and courses you have received
         credit for! This information then is used to weight each course in the department, then the algorithm chooses the optimal pathways in three
         different workload amounts.
     </div>

     <p style="margin-left:20em;" align="left"> 2. Where do we get course information? </p>

     <div class="content" style="margin-left:23em;" align="left"> For Brown students we generate pathways by using data
         from <a href = "https://thecriticalreview.org/" class="link"> Critical Review </a> and
         <a href = "https://cab.brown.edu/" class="link"> Courses@Brown. </a> We currently support all
         undergraduate courses!
     </div>

     <p style="margin-left:20em;" align="left"> 3. Can I edit my pathway? </p>

     <div class="content" style="margin-left:23em;" align="left"> Yes! If you click on a semester, you can edit your pathway by adding, removing,
     or swapping a course. </div>

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