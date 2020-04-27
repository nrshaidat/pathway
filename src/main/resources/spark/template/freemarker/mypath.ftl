<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>My Pathway:</title>
    <h1> Pathway

        <#list id as node>
        ${node}
        </#list>
    </h1>
  <div class="results">
      <#list results as semester>
        <div>
          Semester ${semester.semnumber}:
          <#if semester.courses ? has_content>
            <#if semester.courseid1 ? has_content>
              <p>${semester.courseid1}</p>
            <#else>
            </#if>
            <#if semester.courseid2 ? has_content>
              <p>${semester.courseid2}</p>
            <#else>
            </#if>
            <#if semester.courseid3 ? has_content>
              <p>${semester.courseid3}</p>
            <#else>
            </#if>
            <#if semester.courseid4 ? has_content>
              <p>${semester.courseid4}</p>
            <#else>
            </#if>
          <#else>
            Free Semester
          </#if>
        </div>
      </#list>
  </div>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="/logo/t.png"/>
    <link rel="stylesheet" href="/css/mypath.css">
  </head>
  <body>


  <br>

     <script src="js/jquery-2.1.1.js"></script>
     <script src="js/main.js"></script>


  </body>

</html>