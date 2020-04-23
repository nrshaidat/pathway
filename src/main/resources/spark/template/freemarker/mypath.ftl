<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <h1>Melissa's Pathway</h1>

    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- In real-world webapps, css is usually minified and
         concatenated. Here, separate normalize from our code, and
         avoid minification for clarity. -->

    <link rel="shortcut icon" href="/logo/t.png"/>
    <link rel="stylesheet" href="/css/mypath.css">
  </head>
  <body>


  <p> To generate the most accurate pathway, please input as much information as possible!
    <br> For FAQs on how we design pathways, <a href="/faqs" class="link"> click here. </a> </p>

  <br>

  <form action="/displaypath" method="post">

  <p> Choose a concentration:</p>
  <div class="dropdown" name="concentration" id="concentration" style = "overflow-y:scroll">
      <select name="concentration" id="concentration" type="dropdown">
          <#list courseList as item>
              <option value="${item}">${item}</option>
          </#list>
      </select>
    <input name="concentration" id="concentration" type="submit">
  </div>
  </form>


  <p> Enter Rising semester number: <input type="text" name="semester" value=""> </p>

  <p> Enter Preferred workload hours: <input type="text" name="workload" value=""> </p>

  <p> Prefer Honors courses when possible: <input type="checkbox" name="honors" value="Honors"> </p>


  <p>Please enter courses you have received credit for: </p>
  <p><note> (e.g. A comma-separated list like: ECON 0110, MATH 0100, APMA 350)</note> </p>


  <textarea name="comments" rows="4" maxlength="200" cols="60"></textarea>


  <br>
  <br>
  <br>

  <button type="submit"> Submit</button>





  <!-- Again, we're serving up the unminified source for clarity. -->
     <script src="js/jquery-2.1.1.js"></script>
     <script src="js/main.js"></script>


  </body>
  <!-- See http://html5boilerplate.com/ for a good place to start
       dealing with real world issues like old browsers.  -->
</html>