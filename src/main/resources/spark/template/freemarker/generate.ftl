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
  <link rel="stylesheet" href="/css/generate.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css">
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
      <select name="gender" id="gender-select" class = "ui selection dropdown">
        <#list courseList as item>
          <option value="${item}">${item}</option>
        </#list>
      </select>
    </div>
    <script>
      $('#gender-select').dropdown();
    </script>

    <br>
    <br>

  <p> Enter Rising semester number: </p>
  <div class="ui right labeled input">
    <input type="number" name="semester" value="">
  </div>

    <br>
  <p> Enter Preferred workload hours: </p>
  <div class="ui right labeled input">
    <input type="number" name="workload" value="">
  </div>

    <br>

  <div class="ui checkbox">
    <input type="checkbox" name="example">
    <label> Prefer aggressive pathways </label>
  </div>

    <br>

    <br>

  <p>Please enter courses you have received credit for: </p>
  <p><note> (e.g. A comma-separated list like: ECON 0110, MATH 0100, APMA 350)</note> </p>


  <textarea name="comments" rows="4" maxlength="200" cols="60"></textarea>




<#--    <select class="ui fluid search dropdown" multiple="">-->
<#--      <option value="">State</option>-->
<#--      <option value="AL">Alabama</option>-->
<#--      <option value="AK">Alaska</option>-->
<#--      <option value="AZ">Arizona</option>-->
<#--      <option value="AR">Arkansas</option>-->
<#--      <option value="CA">California</option>-->
<#--      <option value="CO">Colorado</option>-->
<#--      <option value="CT">Connecticut</option>-->
<#--      <option value="DE">Delaware</option>-->
<#--      <option value="DC">District Of Columbia</option>-->
<#--      <option value="FL">Florida</option>-->
<#--      <option value="GA">Georgia</option>-->
<#--      <option value="HI">Hawaii</option>-->
<#--      <option value="ID">Idaho</option>-->
<#--      <option value="IL">Illinois</option>-->
<#--      <option value="IN">Indiana</option>-->
<#--      <option value="IA">Iowa</option>-->
<#--      <option value="KS">Kansas</option>-->
<#--      <option value="KY">Kentucky</option>-->
<#--      <option value="LA">Louisiana</option>-->
<#--      <option value="ME">Maine</option>-->
<#--      <option value="MD">Maryland</option>-->
<#--      <option value="MA">Massachusetts</option>-->
<#--      <option value="MI">Michigan</option>-->
<#--      <option value="MN">Minnesota</option>-->
<#--      <option value="MS">Mississippi</option>-->
<#--      <option value="MO">Missouri</option>-->
<#--      <option value="MT">Montana</option>-->
<#--      <option value="NE">Nebraska</option>-->
<#--      <option value="NV">Nevada</option>-->
<#--      <option value="NH">New Hampshire</option>-->
<#--      <option value="NJ">New Jersey</option>-->
<#--      <option value="NM">New Mexico</option>-->
<#--      <option value="NY">New York</option>-->
<#--      <option value="NC">North Carolina</option>-->
<#--      <option value="ND">North Dakota</option>-->
<#--      <option value="OH">Ohio</option>-->
<#--      <option value="OK">Oklahoma</option>-->
<#--      <option value="OR">Oregon</option>-->
<#--      <option value="PA">Pennsylvania</option>-->
<#--      <option value="RI">Rhode Island</option>-->
<#--      <option value="SC">South Carolina</option>-->
<#--      <option value="SD">South Dakota</option>-->
<#--      <option value="TN">Tennessee</option>-->
<#--      <option value="TX">Texas</option>-->
<#--      <option value="UT">Utah</option>-->
<#--      <option value="VT">Vermont</option>-->
<#--      <option value="VA">Virginia</option>-->
<#--      <option value="WA">Washington</option>-->
<#--      <option value="WV">West Virginia</option>-->
<#--      <option value="WI">Wisconsin</option>-->
<#--      <option value="WY">Wyoming</option>-->
<#--    </select>-->

<#--    </div>-->

<#--    <br><br>-->


<#--    <div class="ui button">-->
<#--      Clear Filters-->
<#--    </div>-->


  <br>
  <input type="submit">
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