<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>Pathway</title>
    <h1>My Pathways</h1>

    <p>${header}</p>

    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- In real-world webapps, css is usually minified and
         concatenated. Here, separate normalize from our code, and
         avoid minification for clarity. -->

    <link rel="shortcut icon" href="/logo/t.png"/>
    <link rel="stylesheet" href="/css/pathway.css">
</head>
<body>


<br>

<#--placeholder-->
<a href="mypath/1" class="link" name="pathway" id="pathway" value="one">
    <div id="container1">
        <header> Pathway 1 </header>

        <p> Number of Semesters: ${stats.numsemesters1}</p>
        <p> Number of Courses: ${stats.totalnumcourses1}</p>
        <p> Average Workload Per Semester (hrs): ${stats.avgavghrs1sem} </p>
        <p> Average Workload Per Course (hrs): ${stats.avgavghrs1path} </p>
    </div>
</a>

<a href="mypath/2" class="link">
    <div id="container2">
        <header> Pathway 2 </header>

        <p> Number of Semesters: ${stats.numsemesters2}</p>
        <p> Number of Courses: ${stats.totalnumcourses2}</p>
        <p> Average Workload Per Semester (hrs): ${stats.avgavghrs2sem}  </p>
        <p> Average Workload Per Course (hrs): ${stats.avgavghrs2path} </p>
    </div>
</a>

<a href="mypath/3" class="link">
    <div id="container3">
        <header> Pathway 3 </header>

        <p> Number of Semesters: ${stats.numsemesters3}</p>
        <p> Number of Courses: ${stats.totalnumcourses3}</p>
        <p> Average Workload Per Semester (hrs): ${stats.avgavghrs3sem}  </p>
        <p> Average Workload Per Course (hrs): ${stats.avgavghrs3path} </p>
    </div>
</a>
<br>
<form action="/generate" method="post">
    <button class="regen" type="submit">Generate New Pathways</button>
</form>


<script src="js/jquery-2.1.1.js"></script>
<script src="js/main.js"></script>


</body>

</html>
