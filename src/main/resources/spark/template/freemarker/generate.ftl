<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
  <!--Start: Nav  -->
  <div class="ui fixed borderless huge menu">
    <div class="ui container grid">
      <!--Start: Desktop Nav-->
      <div class="computer only row">
        <a class="header item">Pathway</a>
        <a class="active item">Log out</a>
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
  <body onload="js_Load()">

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

  <p> Enter Rising Semester: </p>
    <div class="ui container">
      <select name="grade" id="grade" class = "ui selection dropdown">

          <option value="freshman"> Freshman </option>
        <option value="sophomore"> Sophomore </option>
        <option value="junior"> Junior </option>
        <option value="senior"> Senior </option>
      </select>
    </div>
    <div class="ui container">
      <select name="year" id="year" class = "ui selection dropdown">

        <option value="spring"> Spring </option>
        <option value="fall"> Fall </option>
      </select>
    </div>
    <script>
      $('#grade').dropdown();
      $('#year').dropdown();
    </script>

    <br>
    <br>

    <p> Enter workload preferences: </p>

    <form>
      <input type="range" id="vol" name="vol" min="0" max="100">
    </form>

    <small>low  &emsp; medium  &emsp;  high</small>

    <br>
    <br>

  <p>Please enter courses you have received credit for: </p>
  <p><note> (Select course ids from the dropdown e.g. ECON 0110, MATH 0100, APMA 350)</note> </p>

    <div class="field">
      <label>Courses: </label>
      <select class="ui selection dropdown search" multiple="" id="multi-select" value.bind="coursesTaken" >
        <#list courseList as item>
          <option value="${item}">${item}</option>
        </#list>
      </select>

    </div>

    <input style="display:none" name="results" id="results" key="results">

    <script>
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

  <script src ="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css"></script>
  <script src="js/jquery-2.1.1.js"></script>
  <script src="js/main.js"></script>

  <script>function js_Load() {
      document.body.style.visibility='visible';
    }
  </script>
  <noscript><style> body { visibility: visible; }</style></noscript>

  </body>

</html>
