<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="/logo/t.png"/>
    <link rel="stylesheet" href="/css/main.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.js"></script>
    <script src="js/jquery-2.1.1.js"></script>
    <script src="js/main.js"></script>
<body style="visibility: hidden;" onload="js_Load()">
</head>

<div class="ui fixed borderless huge menu">
    <div class="ui container grid">
        <div class="computer only row">
            <a id="defunct" class="header item">Pathway</a>

            <div style="float: right;"
            <form action="/faqs" method="post">
                <a href="/faqs" class="item">FAQ</a>
            </form>
            <form action="/home" method="post">
                <a href="/home" class="item">Home</a>
            </form>
            </div>
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
<br>
<h1>Welcome to Pathway</h1>

<p>Your personalized concentration pathway generator for ${uniName}. <p>
    <p> Pathway generates courses within your specified concentration to help you plan out your next years at ${uniNameShort}. </p>
<h2>Login</h2>

<form action="/mypath" method="post">

    <div class="container">
        <label name="username" id="username" for="uname"><b></b></label>
        <input name="username" id="username"
               type="text" placeholder="Enter Username" name="uname" value="" required>

        <label name="password" id="password" for="psw"><b>Password</b></label>
        <input name="password" id="password"
               type="password" placeholder="Enter Password" name="psw" required>

        <button type="submit">Login</button>
    </div>

</form>
<br>

<form action="/generate" method="post">
    <button id="guest" type="submit">Login as Guest</button>
</form>

<h3><a href="/signup" class="link"> Sign Up </a></h3>

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
<noscript>
    <style> body {
            visibility: visible;
        }</style>
</noscript>

</html>
