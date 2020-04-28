<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="/logo/t.png"/>
    <link rel="stylesheet" href="/css/mypath.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.js"></script>
    <style>
        /* The Modal (background) */
        .modal {
            display: none; /* Hidden by default */
            position: fixed; /* Stay in place */
            z-index: 1; /* Sit on top */
            padding-top: 10vh; /* Location of the box */
            left: 0;
            top: 0;
            width: 100%; /* Full width */
            height: 100%; /* Full height */
            overflow: auto; /* Enable scroll if needed */
            background-color: rgb(0, 0, 0); /* Fallback color */
            background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
        }

        /* Modal Content */
        .modal-content {
            background-color: #fefefe;
            margin: auto;
            text-align: center;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
        }

        /* The Close Button */
        .close {
            color: #aaaaaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
            padding-right: 0.6em;
        }

        .close:hover,
        .close:focus {
            color: #000;
            text-decoration: none;
            cursor: pointer;
        }

        .btn-group button {
            border-radius: 4px;
            border-color: #f5ffff;
            color: white; /* White text */
            padding: 10px 24px; /* Some padding */
            cursor: pointer; /* Pointer/hand icon */
            font-family: Lato;
        }

        #btn1 {
            background-color: #4CAF50; /* Green background */
        }

        #btn2 {
            background-color: #35adaf; /* Blue-green background */
        }

        #btn3 {
            background-color: #af3263; /* Magenta-pink background */
        }

        #btn-add-confirm {
            background-color: #4CAF50; /* Green background */
            border: none;
            border-radius: 4px;
            color: white; /* White text */
            padding: 10px 10px; /* Some padding */
            cursor: pointer; /* Pointer/hand icon */
            font-family: Lato;
        }

        #btn-mov-confirm {
            background-color: #4CAF50; /* Green background */
            border: none;
            border-radius: 4px;
            color: white; /* White text */
            padding: 10px 10px; /* Some padding */
            cursor: pointer; /* Pointer/hand icon */
            font-family: Lato;
        }

        #btn-swp-confirm {
            background-color: #4CAF50; /* Green background */
            border: none;
            border-radius: 4px;
            color: white; /* White text */
            padding: 10px 10px; /* Some padding */
            cursor: pointer; /* Pointer/hand icon */
            font-family: Lato;
        }

        /* Clear floats (clearfix hack) */
        .btn-group:after {
            content: "";
            clear: both;
            display: table;
        }

        .btn-group button:not(:last-child) {
            border-right: none; /* Prevent double borders */
        }

        .add-course {
            display: none; /* Hidden by default */
            z-index: 1; /* Sit on top */
            margin: 0 auto;
            width: 60%;
            height: 70%;
            overflow: auto; /* Enable scroll if needed */
            background-color: rgba(87, 149, 117, 0.7); /* Fallback color */
        }

        .move-course {
            display: none; /* Hidden by default */
            z-index: 1; /* Sit on top */
            margin: 0 auto;
            width: 60%;
            height: 70%;
            overflow: auto; /* Enable scroll if needed */
            background-color: rgba(38, 168, 180, 0.7); /* Fallback color */
        }

        .swap-course {
            display: none; /* Hidden by default */
            z-index: 1; /* Sit on top */
            margin: 0 auto;
            width: 60%;
            height: 70%;
            overflow: auto; /* Enable scroll if needed */
            background-color: rgba(235, 74, 115, 0.7); /* Fallback color */
        }

        #course-to-move {
            width: 27%;
        }
        #dest-semester {
            width: 15%;
        }

        .ui-cards {
            display:inline-flex;
            flex-wrap: wrap;
            flex-direction: row;
            margin: 5% auto;
        }

        .ui-raised-link-card {
            display: flex;
            background-color: #fff;
            border-radius: 4px;
            border: medium solid;
            margin-bottom: 3%;
            margin-left: 7.3%;
            margin-right: 3%;
            color: #444;
            cursor: pointer;
            padding-top: 2em;
            padding-bottom: 2em;
            padding-left: 4.8em;
            width: 17em;
            height: 21em;
        }

        .ui-raised-link-card:nth-child(1){
            border-color: #54defa;
        }

        .ui-raised-link-card:nth-child(2) {
            border-color: #3effce;
        }

        .ui-raised-link-card:nth-child(3) {
            border-color: #b4b3ff;
        }

        .ui-raised-link-card:nth-child(4) {
            border-color: #eb6c95;
        }

        .ui-raised-link-card:nth-child(5) {
            border-color: #eb5c48;
        }


        .ui-raised-link-card:nth-child(6) {
            border-color: #3effce;
        }

        .ui-raised-link-card:nth-child(7) {
            border-color: #54defa;
        }

        .ui-raised-link-card:nth-child(8) {
            border-color: #b4b3ff;
        }


        .header-cc_pointer{
            text-align: center;
            color: black;
            font-size: large;
            border-width: 5px;
            border-bottom: dashed;
            border-color: inherit;
            padding-top: 10px;
            padding-bottom: 5px;
        }

        .meta-cc_pointer{
            color: grey;
            padding-top: 3px;
            font-size: small;
            text-align: center;
        }

        .description {
            padding-left: 10px;
            padding-right: 10px;
        }

        .redirect{
            float: left;
            padding-left: 15px;
        }

        #submit {
            color: forestgreen;
            background: none;
            font-size: 40px;
            border: none;
            cursor: pointer;
        }

        .ui.fluid.search.selection.dropdown {
            width: 70%;
            margin: 2% auto 0;
        }

    </style>
</head>
<body>
<title style="text-align: center;">My Pathway:</title>
<h1 style="text-align: center;"> Pathway
    <#list id as node>
        ${node}
    </#list>
</h1>
<p>Click on a semester to customize it! Refresh the page if you wish to revert to the original pathway.</p>
<div class="redirect">
    <form method="POST" action="/mypath">
        <button id="submit" type="submit" value="Go back">
            <i class="fa fa-arrow-circle-left"></i>
        </button>
    </form>
</div>
<div class="results">
    <div id="myModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <h2>Customize Your Pathway!</h2>
            <p id="this-sem"></p>
            <div class="btn-group">
                <button id="btn1">Add Course</button>
                <button id="btn2">Move Course</button>
                <button id="btn3">Swap Course</button>
            </div>
            <br>
            <br>
            <div class="add-course">
                <br>
                <h4>Add A Course To This Semester</h4>
                <div class="field">
                    <label>Courses: </label>
                    <select name="courses" key="courses" id="multi-select"
                            class="ui fluid search selection dropdown" multiple="">
                        <#list courseList as item>
                            <option value="${item}">${item}</option>
                        </#list>
                    </select>
                    <br>
                    <button id="btn-add-confirm">Confirm Changes</button>
                    <br>
                    <br>
                    <br>
                    <p id="add-confirm" style="display:none">Semester Updated!</p>

                </div>
                <script>
                    $('#multi-select').dropdown();
                </script>
            </div>

            <div class="move-course">
                <br>
                <h4>Move A Course From This Semester To Another Semester</h4>
                <div class="field">
                    <form class="ui form">
                        <div class="field">
                            <label>Move Course: </label>
                            <input id="course-to-move" placeholder='e.g. MATH 0090' />
                        </div>
                        <div class="field">
                            <label>to Semester: </label>
                            <input id="dest-semester" placeholder='e.g. 2' />
                        </div>
                    </form>

                    <br>
                    <button id="btn-mov-confirm">Confirm Changes</button>
                    <br>
                    <br>
                    <br>
                    <p id="mov-confirm" style="display:none">Semesters Updated!</p>
                </div>
                <script>
                </script>
            </div>

            <div class="swap-course">
                <br>
                <h4>Swap Out A Course From This Semester</h4>
                <div class="field">
                    <label>Courses: </label>
                    <select name="eq-courses" key="eq-courses" id="multi-select2"
                            class="ui fluid search selection dropdown" multiple="">
                        <#list courseList as item>
                            <option value="${item}">${item}</option>
                        </#list>
                    </select>
                    <br>
                    <button id="btn-swp-confirm">Confirm Changes</button>
                    <br>
                    <br>
                    <br>
                    <p id="swp-confirm" style="display:none">Semester Updated!</p>

                </div>
                <script>
                    $('#multi-select2').dropdown();
                </script>
            </div>


        </div>
    </div>
    <div class="ui-cards">
        <#list results as semester>
            <div class="ui-raised-link-card" id="myBtn${semester.semnumber}">
                <div class="myBtn" id="myBtn${semester.semnumber}" style="cursor:pointer">
                    <div class="header-cc_pointer">Semester ${semester.semnumber}:
                        <div class="meta-cc_pointer">${["Spring", "Fall"][semester.semnumber%2]}</div>
                    </div>
                    <div class="description">
                        <br>
                        <#if semester.courses ? has_content>
                            <#if semester.courseid1 ? has_content>
                                <p id="${semester.courseid1}">${semester.courseid1}</p>
                            </#if>
                            <#if semester.courseid2 ? has_content>
                                <p id="${semester.courseid2}">${semester.courseid2}</p>
                            </#if>
                            <#if semester.courseid3 ? has_content>
                                <p id="${semester.courseid3}">${semester.courseid3}</p>
                            </#if>
                            <#if semester.courseid4 ? has_content>
                                <p id="${semester.courseid4}">${semester.courseid4}</p>
                            </#if>
                        <#else>
                            Free Semester
                        </#if>
                    </div>
                </div>
            </div>
        </#list>
    </div>


    <script>
        // Get the modal
        const modal = document.getElementById("myModal");
        // Get this semester text
        const sem = document.getElementById("this-sem");
        // Get course adder
        const adder = document.getElementsByClassName("add-course")[0];
        // Get course mover
        const mover = document.getElementsByClassName("move-course")[0];
        // Get course swapper
        const swapper = document.getElementsByClassName("swap-course")[0];
        // Get modal content
        const cont = document.getElementsByClassName("modal-content")[0];
        // Get confirm add message
        const addconfirm = document.getElementById("add-confirm");
        // Get confirm add message
        const movconfirm = document.getElementById("mov-confirm");

        // Get add button and make is show adder
        const addbtn = document.getElementById("btn1");
        addbtn.onclick = function () {
            adder.style.display = "block";

            swapper.style.display = "none";
            mover.style.display = "none";
            cont.style.height = '100%';
        }

        // Get mov button and make is show mover
        const movbtn = document.getElementById("btn2");
        movbtn.onclick = function () {
            mover.style.display = "block";

            swapper.style.display = "none";
            adder.style.display = "none";
            cont.style.height = '100%';
        }

        // Get swap button and make is show swapper
        const swapbtn = document.getElementById("btn3");
        swapbtn.onclick = function () {
            swapper.style.display = "block";

            mover.style.display = "none";
            adder.style.display = "none";
            cont.style.height = '100%';
        }

        // Get add confirm button and make it add courses
        const addconfirmbtn = document.getElementById("btn-add-confirm");
        addconfirmbtn.onclick = function () {
            const courses = $('#multi-select').dropdown('get values');
            const sem = document.getElementById("this-sem");
            const text = sem.innerText.trim();
            const lastCharacter = text[text.length - 2];
            const parent = document.getElementById("myBtn"+lastCharacter);
            const child = parent.querySelectorAll(".description")[0];

            for (let i = 0; i < courses.length; i++) {
                const node = document.createElement("P");
                node.innerHTML = courses[i];
                child.appendChild(node);
            }

            addconfirm.style.display = "block";
        }

        // Get mov confirm button and make it move courses
        const movconfirmbtn = document.getElementById("btn-mov-confirm");
        movconfirmbtn.onclick = function () {
            const moveCourse = document.getElementById("course-to-move");
            const destSem = document.getElementById("dest-semester");
            if (moveCourse.value === "" || destSem.value === "") {
                alert("Please enter values for the course to move and its destination semester.");
                return;
            }
            if (!destSem.value.match(/^-{0,1}\d+$/)) {
                alert("The destination semester must be one of the displayed semester numbers.");
                return;
            }

            const sem = document.getElementById("this-sem");
            const text = sem.innerText.trim();
            const lastCharacter = text[text.length - 2];
            const parent = document.getElementById("myBtn"+lastCharacter);
            const child = parent.querySelectorAll(".description")[0];

            const children = [].slice.call(child.getElementsByTagName('p'), 0);
            for (let i = 0; i < children.length; i++) {
                let name = children[i].getAttribute("id");
                if (name.toLowerCase().replace(/\s/g,'') === moveCourse.value.toLowerCase().replace(/\s/g,'')) {
                    child.removeChild(children[i]);

                    const parent2 = document.getElementById("myBtn"+destSem.value);
                    const child2 = parent2.querySelectorAll(".description")[0];
                    child2.appendChild(children[i]);

                    break;
                }
            }

            movconfirm.style.display = "block";
        }

        // Make all semester list items open the modal
        window.onload = function () {
            const btns = document.getElementsByClassName("ui-raised-link-card");
            for (let i = 0; i < btns.length; i++) {
                const btn = btns[i];
                btn.onclick = function () {
                    modal.style.display = "block";
                    sem.innerHTML = "Edit Semester " + btn.id.charAt(btn.id.length - 1) + ":";
                }
            }
        }


        // Get the span element that closes the modal
        const span = document.getElementsByClassName("close")[0];
        // When the user clicks on <span> (x), close the modal
        span.onclick = function () {
            modal.style.display = "none";
            adder.style.display = "none";
            addconfirm.style.display = "none";
            mover.style.display = "none";
            movconfirm.style.display = "none";
            cont.style.height = '40%';
        }

        // When the user clicks anywhere outside of the modal, close it
        window.onclick = function (event) {
            if (event.target === modal) {
                modal.style.display = "none";
                adder.style.display = "none";
                addconfirm.style.display = "none";
                mover.style.display = "none";
                movconfirm.style.display = "none";
                cont.style.height = '40%';
            }
        }


    </script>


    <br>

<#--    <script src="js/jquery-2.1.1.js"></script>-->
<#--    <script src="js/main.js"></script>-->


</body>

</html>