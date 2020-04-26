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


<#--<p> <#list content as node>-->
<#--        ${node}-->
<#--    </#list>-->
<#--</p>-->

<#--<div class="results1">-->
<#--    <a href="mypath/1" class="link" name="pathway" id="pathway" value="one">-->
<#--        Pathway 1-->
<#--            <#list results1 as semester>-->
<#--                <div>-->
<#--                    Semester ${semester.semnumber}:-->
<#--                    <#if semester.courses ? has_content>-->
<#--                        <#if semester.courseid1 ? has_content>-->
<#--                            <p>${semester.courseid1}</p>-->
<#--                        <#else>-->
<#--                        </#if>-->
<#--                        <#if semester.courseid2 ? has_content>-->
<#--                            <p>${semester.courseid2}</p>-->
<#--                        <#else>-->
<#--                        </#if>-->
<#--                        <#if semester.courseid3 ? has_content>-->
<#--                            <p>${semester.courseid3}</p>-->
<#--                        <#else>-->
<#--                        </#if>-->
<#--                        <#if semester.courseid4 ? has_content>-->
<#--                            <p>${semester.courseid4}</p>-->
<#--                        <#else>-->
<#--                        </#if>-->
<#--                    <#else>-->
<#--                        Free Semester-->
<#--                    </#if>-->
<#--                </div>-->
<#--            </#list>-->
<#--    </a>-->
<#--</div>-->

<#--<div class="results2">-->
<#--    <a href="mypath/2" class="link" name="pathway" id="pathway" value="two">-->
<#--        Pathway 2-->
<#--        <#list results2 as semester>-->
<#--            <div>-->
<#--                Semester ${semester.semnumber}:-->
<#--                <#if semester.courses ? has_content>-->
<#--                    <#if semester.courseid1 ? has_content>-->
<#--                        <p>${semester.courseid1}</p>-->
<#--                    <#else>-->
<#--                    </#if>-->
<#--                    <#if semester.courseid2 ? has_content>-->
<#--                        <p>${semester.courseid2}</p>-->
<#--                    <#else>-->
<#--                    </#if>-->
<#--                    <#if semester.courseid3 ? has_content>-->
<#--                        <p>${semester.courseid3}</p>-->
<#--                    <#else>-->
<#--                    </#if>-->
<#--                    <#if semester.courseid4 ? has_content>-->
<#--                        <p>${semester.courseid4}</p>-->
<#--                    <#else>-->
<#--                    </#if>-->
<#--                <#else>-->
<#--                    Free Semester-->
<#--                </#if>-->
<#--            </div>-->
<#--        </#list>-->
<#--    </a>-->
<#--</div>-->


<#--<div class="results3">-->
<#--    <a href="mypath/3" class="link" name="pathway" id="pathway" value="three">-->
<#--        Pathway 3-->
<#--        <#list results3 as semester>-->
<#--            <div>-->
<#--                Semester ${semester.semnumber}:-->
<#--                <#if semester.courses ? has_content>-->
<#--                    <#if semester.courseid1 ? has_content>-->
<#--                        <p>${semester.courseid1}</p>-->
<#--                    <#else>-->
<#--                    </#if>-->
<#--                    <#if semester.courseid2 ? has_content>-->
<#--                        <p>${semester.courseid2}</p>-->
<#--                    <#else>-->
<#--                    </#if>-->
<#--                    <#if semester.courseid3 ? has_content>-->
<#--                        <p>${semester.courseid3}</p>-->
<#--                    <#else>-->
<#--                    </#if>-->
<#--                    <#if semester.courseid4 ? has_content>-->
<#--                        <p>${semester.courseid4}</p>-->
<#--                    <#else>-->
<#--                    </#if>-->
<#--                <#else>-->
<#--                    Free Semester-->
<#--                </#if>-->
<#--            </div>-->
<#--        </#list>-->
<#--    </a>-->
<#--</div>-->

<br>

<#--placeholder-->
<a href="mypath/1" class="link" name="pathway" id="pathway" value="one">
    <div id="container">
        <header> Pathway 1 </header>

        <p> # Semesters: 6</p>
        <p> Average Workload (hrs): 23.6 </p>
    </div>
</a>

<a href="mypath/2" class="link">
    <div id="container">
        <header> Pathway 2 </header>

        <p> # Semesters: 6</p>
        <p> Average Workload (hrs): 23.6 </p>
    </div>
</a>

<a href="mypath/3" class="link">
    <div id="container">
        <header> Pathway 3 </header>

        <p> # Semesters: 6</p>
        <p> Average Workload (hrs): 23.6 </p>
    </div>
</a>



<!-- Again, we're serving up the unminified source for clarity. -->
<script src="js/jquery-2.1.1.js"></script>
<script src="js/main.js"></script>


</body>
<!-- See http://html5boilerplate.com/ for a good place to start
     dealing with real world issues like old browsers.  -->
</html>