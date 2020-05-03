<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="/logo/t.png"/>
    <link rel="stylesheet" href="/css/main.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.js"></script>
    <script src="js/jquery-2.1.1.js"></script>
    <script src="js/main.js"></script>
</head>
<body style="visibility: hidden;" onload="js_Load()">

<div class="ui fixed borderless huge menu">
    <div class="ui container grid">
        <div class="computer only row">
            <a id="defunct" class="header item">Pathway</a>

<#--            Putting this here moves FAQ to right!-->
            <form id="defunct" action="/faqs" method="post">
                <a href="/faqs" class="item"></a>
            </form>
            <form style="text-align: right" action="/faqs" method="post">
                <a href="/faqs" class="item">FAQ</a>
            </form>
        </div>

        <!--Start: Mobile Nav-->
        <div class="tablet mobile only row">
            <div class="right menu">
                <a class="menu item">
                    <div class="ui basic icon toggle button">
                        <i class="content icon"></i>
                    </div>
                </a>
            </div>
        </div>
        <!--End: Mobile Nav-->
    </div>
</div>
<br>
<br>

<link rel="stylesheet" href="/css/generate.css">

<br>
<br>
<br>

<h1>Welcome to Pathway</h1>

<p> Please enter your University! We currently support Brown University and Cornell University.</p>

<br>


<p> Choose Your University: </p>


<form action="/signin" method="post">
    <#--    Using an ftl loop, we use the universityList passed into
        the ImmutableMap to display a Dropdown of universities offered-->
    <div class="ui container">
        <select name="university" id="university" key="university" class="ui selection dropdown">
            <#list universityList as item>
                <option value="${item}">${item}.</option>
            </#list>
        </select>
    </div>

    <script>
        // Activates the dropdown using jQuery
        $('#university').dropdown();
    </script>

    <input class="ui button" id="btnSubmit" type="submit" value="Submit"/>
</form>
<script>
    <#--    When the document is ready, the dropdown menu is instantiated-->
    $(document).ready(function () {
        $('.computer.only .dropdown.item')
            .popup({
                inline: true,
                hoverable: true,
                position: 'bottom left',
                delay: {
                    show: 300,
                    hide: 800
                }
            })
        ;
        $('.ui.dropdown').dropdown();
        $('.ui.accordion').accordion();

        // bind "hide and show vertical menu" event to top right icon button
        $('.ui.toggle.button').click(function () {
            $('.ui.vertical.menu').toggle("250", "linear")
        });
    });
</script>


<br>
<br>
<br>
<br>

<p><small> For any questions or ideas about how Pathway can be improved, please
        <a href="mailto: melissa_cui@brown.edu" class="link"> send us an email! </a>
    </small>
</p>

</body>

<script>
    <#--    Fixing the flash of unstyled content problem with jquery -->

    function js_Load() {
        document.body.style.visibility = 'visible';
    }
</script>
<noscript>
    <style> body {
            visibility: visible;
        }</style>
</noscript>

</html>
