<!DOCTYPE html>
  <head>
    <meta charset="utf-8">

  <!--Start: Nav  -->
  <div class="ui fixed borderless huge menu" onload="js_Load()">
    <div class="ui container grid">
      <!--Start: Desktop Nav-->
      <div class="computer only row">
        <form action="/login" method="post">
          <a class="header item">Pathway - ${uniNameShort}</a>
        </form>
        <a class="active item">Generate</a>

        <form action="/faqs" method="post">
          <a href="/faqs" class="item">FAQ</a>
        </form>
        <form action="/login" method="post">
          <a href="/login" class="item">Log in</a>
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
          <a class="item"> Log out</a>
          <div class="ui divider"></div>
          <a class="item" href="#">Default</a>
          <a class="item" href="#">Static top</a>
          <a class="active item" href="#">Fixed top</a>
        </div>
      </div>
      <!--End: Mobile Nav-->
    </div>
  </div>

  <br>
  <br>

   <h1>Generate a Pathway</h1>

    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="/logo/t.png"/>

  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css">

  <link rel="stylesheet" href="/css/generate.css">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.js"></script>


</head>
  <body >

  <p> To generate the most accurate pathway, please input as much information as possible!
    <br> For FAQs on how we design pathways, <a href="/faqs" class="link"> click here. </a> </p>

  <br>

  <form action="/mypath" method="post">

    <p> Choose a concentration: </p>

    <div class="ui container">
      <select name="concentration" id="concentration" class = "ui selection dropdown">
        <#list concentrationList as item>
          <option value="${item}">${item}</option>
        </#list>
      </select>
    </div>
    <script>
      $('#concentration').dropdown();
    </script>

    <br>

    <#--    Using an ftl loop, we use the gradeList passed into
    the ImmutableMap to display a Dropdown of grade level-->
    <p> Enter Rising Semester: </p>
    <div class="ui container">
      <select name="grade" id="grade" class = "ui selection dropdown">
        <#list gradeList as item>
          <option value="${item}">${item}</option>
        </#list>
      </select>
    </div>
    <script>
      // Activates the dropdown using jQuery
      $('#grade').dropdown();
    </script>

    <#--    Using an ftl loop, we use the gradeList passed into
    the ImmutableMap to display a Dropdown of semester level-->
    <div class="ui container">
      <select name="year" id="year" key="year" class = "ui selection dropdown">
        <#list yearList as item>
          <option value="${item}">${item}</option>
        </#list>
      </select>
    </div>

    <script>
      // Activates the dropdown using jQuery
      $('#year').dropdown();
    </script>

    <br>
    <br>

    <p> Complete concentration as fast as possible (prefer lower number of semesters): </p>
    <div class="ui checkbox">
      <input id="aggressive" name="aggressive" value="Aggressive" type="checkbox">
      <label for="aggressive"> Prefer Faster Pathways </label>
    </div>

    <br>
    <br>

  <p>Please enter courses you have received credit for: </p>
  <p><note> (Select course ids from the dropdown e.g. ECON 0110, MATH 0100, APMA 350)</note> </p>

    <#--    Using an ftl loop, we use the courseList passed into
    the ImmutableMap to display a Dropdown of all the courses we have in our database-->
    <div class="field">
      <label>Courses: </label>
      <select class="ui selection dropdown search" multiple="" id="multi-select" value.bind="coursesTaken" >
        <#list courseList as item>
          <option value="${item}">${item}</option>
        </#list>
      </select>

    </div>

<#--    We use an input with display none to get information from the html to the backend-->
    <input style="display:none" name="results" id="results" key="results">

    <script>
      // This script is written to activate the multiple select dropdown menu on loading the program.
      // After activating the dropdown, we get the user input from the select by finding the element from
      // the ID, "results", which is the input tag shown above. By doing this, we are able to assign the
      // input to the result of the dropdown menu, and access the value of results in our QueryMap
      // after the user presses submit.

      window.onload = function() {
        $('#multi-select').dropdown();
        $('#multi-select').dropdown('setting', 'onChange', function () {
          var courses = $('#multi-select').dropdown('get values');
          var courseString = courses.toString();
          const results = document.getElementById("results");
          results.value = courseString;
        });
      }
    </script>

  <br>

    <input class = "ui button" id = "btnSubmit" type="submit" value="Generate"/>
    <script>
      function submitForm() {

        //On clicking the submit button, we post the form data to the new page.
        $("#btnSubmit").button().click(function(){
          $.ajax({ type: 'POST', url: '/api/someRestEndpoint', data: formData, success: onFormSubmitted });
        });

      }
    </script>

  </form>

  <br>
  <br>
  <br>
  <br>

<#--  RESOLVE FLASH OF UNSTYLED CONTENT -->
  <script>function js_Load() {
      $(window).on("load", function() {
        document.body.style.visibility = 'visible';
      });
    }
  </script>
  <noscript><style> body { visibility: visible; }</style></noscript>


  </body>

</html>
