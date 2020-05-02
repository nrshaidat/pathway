<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="/logo/t.png"/>
    <link rel="stylesheet" href="/css/pathway.css">
    <script src="js/jquery-2.1.1.js"></script>
    <script src="js/main.js"></script>
<body style="visibility: hidden;" onload="js_Load()">
</head>


<!--Start: Nav  -->
<div class="ui fixed borderless huge menu">
    <div class="ui container grid">
        <!--Start: Desktop Nav-->
        <div class="computer only row">
            <form action="/login" method="post">
                <a class="header item">Pathway</a>
            </form>
            <a class="active item">Generate</a>
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
                <a class="item"> Login</a>
                <div class="ui divider"></div>
                <a class="item" href="#">Default</a>
                <a class="item" href="#">Static top</a>
                <a class="active item" href="#">Fixed top</a>
            </div>
        </div>

    </div>
</div>
<br>
<br>
<h1>My Pathways</h1>
<p>${header}</p>

<br>

<a href="mypath/1" class="link" name="pathway" id="pathway" value="one">
    <div id="container1">
        <#--Displays the workload and unique courses for pathway 1-->
        <header> Pathway 1</header>
        <p> Low Workload </p>
        <p> Unique classes: </p>
        <#list uniques1 as u>
            <p> ${u} </p>
        </#list>

    </div>
</a>

<a href="mypath/2" class="link">
    <div id="container2">

        <#--Displays the workload and unique courses for pathway 2-->
        <header> Pathway 2</header>
        <p> Medium Workload </p>
        <p> Unique classes: </p>
        <#list uniques2 as u>
            <p> ${u} </p>
        </#list>

    </div>
</a>

<a href="mypath/3" class="link">
    <div id="container3">

        <#--Displays the workload and unique courses for pathway 3-->
        <header> Pathway 3</header>

        <p> High Workload </p>
        <p> Unique classes: </p>
        <#list uniques3 as u>
            <p> ${u} </p>
        </#list>

    </div>
</a>
<br>
<form action="/generate" method="post">
    <button class="regen" type="submit">Generate New Pathways</button>
</form>

</body>
<script>

<#--    Script to fix the flash of unstyled content problem-->
    function js_Load() {
        document.body.style.visibility = 'visible';
    }
</script>
<noscript>
    <style>
        body {
            visibility: visible;
        }</style>
</noscript>

</html>
