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

        <p> # Semesters: 6</p>
        <p> Average Workload (hrs): 23.6 </p>
    </div>
</a>

<a href="mypath/2" class="link">
    <div id="container2">
        <header> Pathway 2 </header>

        <p> # Semesters: 6</p>
        <p> Average Workload (hrs): 23.6 </p>
    </div>
</a>

<a href="mypath/3" class="link">
    <div id="container3">
        <header> Pathway 3 </header>

        <p> # Semesters: 6</p>
        <p> Average Workload (hrs): 23.6 </p>
    </div>
</a>

<form action="/generate" method="post">
    <button type="submit">Generate Another Pathway</button>
</form>


<script src="js/jquery-2.1.1.js"></script>
<script src="js/main.js"></script>


</body>

</html>
