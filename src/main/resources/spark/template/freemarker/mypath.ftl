<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>My Pathway:</title>
    <link rel="shortcut icon" href="/logo/t.png"/>
    <link rel="stylesheet" href="/css/mypath.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.js"></script>
</head>
<body>
<!--Start: Nav  -->
<div class="ui fixed borderless huge menu">
    <div class="ui container grid">
        <!--Start: Desktop Nav-->
        <div class="computer only row">
            <form action="/home" method="post">
                <a href="/home" class="header item">Pathway</a>
            </form>
            <form action="/login" method="post">
                <a href="/login" class="item">Log out</a>
            </form>
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
        </div>
        <!--End: Mobile Nav-->
    </div>
</div>
<br>
<br>
<br>
<h1 style="text-align: center;"> Pathway
    <#list id as node>
        ${node}
    </#list>
</h1>

<p>Customize your pathway here! Refresh the page if you wish to revert to the original pathway.</p>
<div style="position: absolute; right:2.4vw; top:14.5em;
    border-radius: 2px; border: solid darkslategrey; padding: 0.5em; width: 20vw;">
    <h3 style="color:rgba(59,59,90,0.93); font-size: 1.5vw;">Pathway Summary</h3>
    <#list id as node>
        <#if node=="1">
            <div style="font-size: 1.1vw;"> Number of Semesters: ${stats.numsemesters1}</div>
            <div style="font-size: 1.1vw;"> Number of Courses: ${stats.totalnumcourses1}</div>
            <div style="font-size: 1.1vw;"> Average Course Workload: ${stats.avgavghrs1path} hrs/wk</div>
        <#elseif node=="2">
            <div style="font-size: 1.1vw;"> Number of Semesters: ${stats.numsemesters2}</div>
            <div style="font-size: 1.1vw;"> Number of Courses: ${stats.totalnumcourses2}</div>
            <div style="font-size: 1.1vw;"> Average Course Workload: ${stats.avgavghrs2path} hrs/wk</div>
        <#elseif node=="3">
            <div style="font-size: 1.1vw;"> Number of Semesters: ${stats.numsemesters3}</div>
            <div style="font-size: 1.1vw;"> Number of Courses: ${stats.totalnumcourses3}</div>
            <div style="font-size: 1.1vw;"> Average Course Workload: ${stats.avgavghrs3path} hrs/wk</div>
        </#if>
    </#list>
</div>
<br>
<br>

<div class="redirect">
    <form method="POST" action="/mypath">
        <button id="submit" type="submit" value="Go back">
            <i class="fa fa-arrow-circle-left"></i>
        </button>
    </form>
</div>
<div class="results">
    <div id="myModal" class="modal">
        <br>
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
                    <label>Fall Courses: </label>
                    <select name="courses" key="courses" id="multi-selectF"
                            class="ui fluid search selection dropdown" multiple="" data-toggle="dropdown">
                        <#list fallAdd as item>
                            <option value="${item}">${item}</option>
                        </#list>
                    </select>
                    <br>
                    <label>Spring Courses: </label>
                    <select name="courses" key="courses" id="multi-selectS"
                            class="ui fluid search selection dropdown" multiple="" data-toggle="dropdown">
                        <#list springAdd as item>
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
                    $('#multi-selectF').dropdown();
                    $('#multi-selectS').dropdown();
                </script>
            </div>

            <div class="move-course">
                <br>
                <h4>Move A Course From This Semester To Another Semester</h4>
                <div class="field">
                    <form class="ui form">
                        <div class="field">
                            <label>Move Course: </label>
                            <input id="course-to-move" placeholder='e.g. MATH 0090'/>
                        </div>
                        <div class="field">
                            <label>to Semester: </label>
                            <input id="dest-semester" placeholder='e.g. 2'/>
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
                    <form class="ui form">
                        <div class="field">
                            <label style="font-size: 1em">Swap Out Course: </label>
                            <input id="course-to-swap" placeholder='e.g. BIOL 0200'/>
                        </div>
                        <label style="font-family: Lato; font-weight: bold">Swap In Course: </label>
                        <br>
                        <label>Fall Courses: </label>
                        <select name="courses" key="courses" id="multi-selectFS"
                                class="ui fluid search selection dropdown" multiple="" data-toggle="dropdown">
                            <#list fallAdd as item>
                                <option value="${item}">${item}</option>
                            </#list>
                        </select>
                        <br>
                        <label>Spring Courses: </label>
                        <select name="courses" key="courses" id="multi-selectSS"
                                class="ui fluid search selection dropdown" multiple="" data-toggle="dropdown">
                            <#list springAdd as item>
                                <option value="${item}">${item}</option>
                            </#list>
                        </select>
                    </form>

                    <br>
                    <button id="btn-swp-confirm">Confirm Changes</button>
                    <br>
                    <br>
                    <br>
                    <p id="swp-confirm" style="display:none">Semester Updated!</p>

                </div>
                <script>
                    $('#multi-selectFS').dropdown();
                    $('#multi-selectSS').dropdown();
                </script>
            </div>
        </div>
    </div>
    <div class="ui-cards">

        <#--        Displays the results by semester cards in the /mypath/:id pages. The display results in two-->
        <#--        side by side semester "cards", that users can click on and modify. If there is no content in a -->
        <#--        particular semester, the semester is defined as a "Free Semester," which is the else -->
        <#--        condition in the following statement. If there is content for each of the possible courses, -->
        <#--        then the course ID is displayed. -->
        <#list results as semester>
            <div class="ui-raised-link-card" id="myBtn${semester.semnumber}">

                <div class="year" id="freshman${semester.semnumber}" style="display: none">Freshman Year</div>
                <div class="year" id="sophomore${semester.semnumber}" style="display: none">Sophomore Year</div>
                <div class="year" id="junior${semester.semnumber}" style="display: none">Junior Year</div>
                <div class="year" id="senior${semester.semnumber}" style="display: none">Senior Year</div>
                <div class="year" id="supersenior${semester.semnumber}" style="display: none">Super Senior Year</div>

                <div class="myBtn" id="myBtn${semester.semnumber}">
                    <div class="header-cc_pointer">Semester ${semester.semnumber}:
                        <div class="meta-cc_pointer">${["Spring", "Fall"][semester.semnumber%2]}</div>
                    </div>
                    <div class="description">
                        <br>
                        <#if semester.courses ? has_content>
                            <#list semester.courses as course>
                                <div class="course" id="${course.id}" style="cursor:pointer;">
                                    ${course.id}
                                    <div class="more-info">
                                        ${course.id} - ${course.name}
                                        <br>
                                        <br>
                                        Professor: ${course.professor}
                                        <br>
                                        <br>
                                        <#if course.sem == 2>
                                            Offered: Fall & Spring
                                        </#if>
                                        <#if course.sem == 1>
                                            Offered: Fall
                                        </#if>
                                        <#if course.sem == 0>
                                            Offered: Spring
                                        </#if>
                                        <br>
                                        <br>
                                        <a href="${course.ssurl}" target="_blank">More Details</a>
                                    </div>
                                </div>
                            </#list>
                        </#if>
                    </div>
                </div>
                <button class="modify" id="modifyBtn${semester.semnumber}">Modify</button>
            </div>
        </#list>
    </div>
</div>
<script>
    // Add year text according to semester number
    $('.header-cc_pointer').each(function () {
        if ($(this).text().includes("1") || $(this).text().includes("2")) {
            $('#freshman1').css("display", "block");
            $('#freshman2').css("display", "block");
        } else if ($(this).text().includes("3") || $(this).text().includes("4")) {
            $('#sophomore3').css("display", "block");
            $('#sophomore4').css("display", "block");
        } else if ($(this).text().includes("5") || $(this).text().includes("6")) {
            $('#junior5').css("display", "block");
            $('#junior6').css("display", "block");
        } else if ($(this).text().includes("7") || $(this).text().includes("8")) {
            $('#senior7').css("display", "block");
            $('#senior8').css("display", "block");
        } else {
            $('#supersenior9').css("display", "block");
            $('#supersenior10').css("display", "block");
            $('#supersenior11').css("display", "block");
            $('#supersenior12').css("display", "block");
        }
    });

    // Color code cards by year
    $('.ui-raised-link-card').each(function () {
        $(this).children('div.year').each(function () {
            if ($(this).css('display') === 'block') {
                if ($(this).text().includes("Freshman")) {
                    $(this).parent().addClass("blue");
                } else if ($(this).text().includes("Sophomore")) {
                    $(this).parent().addClass("bluegreen");
                } else if ($(this).text().includes("Junior")) {
                    $(this).parent().addClass("lavender");
                } else if (!($(this).text().includes("Super"))) {
                    $(this).parent().addClass("pink");
                } else {
                    $(this).parent().addClass("terracotta");
                }
            }
        });
    });

    // Get the modal
    const modal = document.getElementById("myModal");
    // Get modal content
    const cont = document.getElementsByClassName("modal-content")[0];
    // Get this semester text
    const sem = document.getElementById("this-sem");

    // Get course adder
    const adder = document.getElementsByClassName("add-course")[0];
    // Get course mover
    const mover = document.getElementsByClassName("move-course")[0];
    // Get course swapper
    const swapper = document.getElementsByClassName("swap-course")[0];
    // Get confirm add message
    const addconfirm = document.getElementById("add-confirm");
    // Get confirm mov message
    const movconfirm = document.getElementById("mov-confirm");
    // Get confirm swp message
    const swpconfirm = document.getElementById("swp-confirm");

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
        const sem = document.getElementById("this-sem");
        const text = sem.innerText.trim();
        const lastCharacter = text[text.length - 2];
        let courses;
        if (parseInt(lastCharacter) % 2 === 1) {
            courses = $('#multi-selectF').dropdown('get values');
            $('#multi-selectF :selected').val('').trigger("change");
            $('#multi-selectF :selected').attr("disabled", true);
        } else {
            courses = $('#multi-selectS').dropdown('get values');
            $('#multi-selectS :selected').val('').trigger("change");
            $('#multi-selectS :selected').attr("disabled", true);
        }

        const parent = document.getElementById("myBtn" + lastCharacter);
        const child = parent.querySelectorAll(".description")[0];

        for (let i = 0; i < courses.length; i++) {
            const node = document.createElement("div");
            node.style.cursor = "pointer";
            node.style.fontSize = "1.1em";
            node.style.marginBottom = "1em";
            node.style.position = "relative";
            node.innerHTML = courses[i];
            node.setAttribute("id", courses[i]);
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
        const parent = document.getElementById("myBtn" + lastCharacter);
        const child = parent.querySelectorAll(".description")[0];

        const children = [].slice.call(child.getElementsByTagName('div'), 0);
        for (let i = 0; i < children.length; i++) {

            let name = children[i].getAttribute("id");
            if (name != null && name.toLowerCase().replace(/\s/g, '') === moveCourse.value.toLowerCase().replace(/\s/g, '')) {
                child.removeChild(children[i]);

                const parent2 = document.getElementById("myBtn" + destSem.value);
                const child2 = parent2.querySelectorAll(".description")[0];
                child2.appendChild(children[i]);

                break;
            }
        }

        movconfirm.style.display = "block";
    }

    // Get swp confirm button and make it swap courses
    const swpconfirmbtn = document.getElementById("btn-swp-confirm");
    swpconfirmbtn.onclick = function () {
        const swapCourse = document.getElementById("course-to-swap");
        if (swapCourse.value === "") {
            alert("Please enter a course from this semester to swap out.");
            return;
        }
        const sem = document.getElementById("this-sem");
        const text = sem.innerText.trim();
        const lastCharacter = text[text.length - 2];
        let courses;
        if (parseInt(lastCharacter) % 2 === 1) {
            courses = $('#multi-selectFS').dropdown('get values');
        } else {
            courses = $('#multi-selectSS').dropdown('get values');
        }

        const parent = document.getElementById("myBtn" + lastCharacter);
        const child = parent.querySelectorAll(".description")[0];

        const children = [].slice.call(child.getElementsByTagName('div'), 0);
        for (let i = 0; i < children.length; i++) {
            let name = children[i].getAttribute("id");
            console.log(name);
            if (name != null && name.toLowerCase().replace(/\s/g, '') === swapCourse.value.toLowerCase().replace(/\s/g, '')) {
                child.removeChild(children[i]);
            }
        }

        for (let i = 0; i < courses.length; i++) {
            const node = document.createElement("div");
            node.style.cursor = "pointer";
            node.style.fontSize = "1.1em";
            node.style.marginBottom = "1em";
            node.style.position = "relative";
            node.innerHTML = courses[i];
            node.setAttribute("id", courses[i]);
            child.appendChild(node);
        }

        swpconfirm.style.display = "block";
    }

    // Make modify buttons open the modal
    window.onload = function () {
        const btns = document.getElementsByClassName("modify");
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
        swapper.style.display = "none";
        swpconfirm.style.display = "none";
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
            swapper.style.display = "none";
            swpconfirm.style.display = "none";
            cont.style.height = '40%';
        }
    }
</script>

</body>
<script>
    // Script for fixing the flash of unstyled content problem
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
