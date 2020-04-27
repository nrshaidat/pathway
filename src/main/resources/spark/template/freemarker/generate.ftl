<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>${title}</title>
    <h1>Generate a Pathway</h1>

    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="/logo/t.png"/>

  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css">

  <link rel="stylesheet" href="/css/generate.css">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.js"></script>
  </head>
  <body>


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

  <p> Enter Rising semester number: </p>
    <div class="ui input focus">
      <input type="number" name ="semester" id ="semester" placeholder="Enter semester...">
    </div>

    <br>
    <br>

    <p> Enter workload preferences: </p>
    <div class="ui right labeled input">
      <input type="number" name = "workload" id= "workload" placeholder="Enter workload">
      <div class="ui basic label">
        hours
      </div>
    </div>

    <br>
    <br>

    <div class="ui checkbox">
      <input id="aggressive" name="aggressive" value="Aggressive" type="checkbox">
      <label for="aggressive"> Prefer Aggressive Pathways </label>
    </div>

    <br>

    <br>

  <p>Please enter courses you have received credit for: </p>
  <p><note> (Select course ids from the dropdown e.g. ECON 0110, MATH 0100, APMA 350)</note> </p>



<#--  <textarea name="comments" rows="4" maxlength="200" cols="60"></textarea>-->

    <div class="field">
      <label>Courses: </label>
      <select name="courses" key="courses" id="multi-select" class="ui selection dropdown" multiple="" value.bind="coursesTaken" >
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

    <input class = "ui button" id = "btnSubmit" type="submit" value="Submit"/>
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

  <script src ="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css"></script>
  <script src="js/jquery-2.1.1.js"></script>
  <script src="js/main.js"></script>


  </body>
</html>