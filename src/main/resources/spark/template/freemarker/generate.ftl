<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>${title}</title>
    <h1>Generate a Pathway</h1>

    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- In real-world webapps, css is usually minified and
         concatenated. Here, separate normalize from our code, and
         avoid minification for clarity. -->

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
      <select name="gender" id="concentration-select" class = "ui selection dropdown">
        <#list concentrationList as item>
          <option value="${item}">${item}</option>
        </#list>
      </select>
    </div>
    <script>
      $('#concentration-select').dropdown();
    </script>

    <br>

  <p> Enter Rising semester number: </p>
    <div class="ui input focus">
      <input type="number" placeholder="Enter semester...">
    </div>

    <br>
    <br>

    <p> Enter workload preferences: </p>
    <div class="ui right labeled input">
      <input type="number" placeholder="Enter workload">
      <div class="ui basic label">
        hours
      </div>
    </div>

    <br>
    <br>

    <div class="ui checkbox">
      <input id="aggressive" type="checkbox">
      <label for="aggressive"> Prefer Aggressive Pathways </label>
    </div>

    <br>

    <br>

  <p>Please enter courses you have received credit for: </p>
  <p><note> (e.g. A comma-separated list like: ECON 0110, MATH 0100, APMA 350)</note> </p>



<#--  <textarea name="comments" rows="4" maxlength="200" cols="60"></textarea>-->

    <form class="ui form segment">
      <div class="field">
        <label>Courses: </label>
        <select name="courses" class="ui selection dropdown" multiple="" id="multi-select">
          <#list courseList as item>
            <option value="${item}">${item}</option>
          </#list>
        </select>
      </div>
    </form>
    <script>
      $('#multi-select').dropdown();
    </script>

  <br>

    <button class="ui button" type="submit">Submit</button>
  </form>

  <br>
  <br>
  <br>

  <!-- Again, we're serving up the unminified source for clarity. -->
<#--     <script src="js/jquery-2.1.1.js"></script>-->
<#--     <script src="js/main.js"></script>-->
<#--      <script src = "dropdown.js"></script>-->
  <script src ="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css"></script>



  </body>
  <!-- See http://html5boilerplate.com/ for a good place to start
       dealing with real world issues like old browsers.  -->
</html>