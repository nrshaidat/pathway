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
</head>
<body>
<!--Start: Nav  -->
<div class="ui fixed borderless huge menu">
    <div class="ui container grid">
        <!--Start: Desktop Nav-->
        <div class="computer only row">
            <a class="header item">Pathway</a>
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
            <div class="ui vertical accordion borderless fluid menu">
                <!-- Start: Search -->
                <!-- End: Search -->
                <a class="active item"> Home</a>
                <form action="/faqs" method="post">
                    <a class="item">FAQ</a>
                </form>
                <a class="item"> Log out</a>
                <div class="ui divider"></div>
                <a class="item" href="#">Default</a>
                <a class="item" href="#">Static top</a>
                <a class="active item" href="#">Fixed top</a>
            </div>
        </div>
        <!--End: Mobile Nav-->
    </div>
</div>
<title style="text-align: center;">My Pathway:</title>
<br>
<br>
<br>
<h1 style="text-align: center;"> Pathway
    <#list id as node>
        ${node}
    </#list>
</h1>
<p>Click on a semester to customize it! Refresh the page if you wish to revert to the original pathway.</p>
<#list id as node>
    <#if node=="1">
        <p> Number of Semesters: ${stats.numsemesters1}</p>
        <p> Number of Courses: ${stats.totalnumcourses1}</p>
        <p> Average Workload Per Semester (hrs): ${stats.avgavghrs1sem} </p>
        <p> Average Workload Per Course (hrs): ${stats.avgavghrs1path} </p>
    <#elseif node=="2">
        <p> Number of Semesters: ${stats.numsemesters2}</p>
        <p> Number of Courses: ${stats.totalnumcourses2}</p>
        <p> Average Workload Per Semester (hrs): ${stats.avgavghrs2sem} </p>
        <p> Average Workload Per Course (hrs): ${stats.avgavghrs2path} </p>
    <#elseif node=="3">
        <p> Number of Semesters: ${stats.numsemesters3}</p>
        <p> Number of Courses: ${stats.totalnumcourses3}</p>
        <p> Average Workload Per Semester (hrs): ${stats.avgavghrs3sem} </p>
        <p> Average Workload Per Course (hrs): ${stats.avgavghrs3path} </p>
    </#if>
</#list>
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

<#--        Displays the results by semester cards in the /mypath/:id pages. The display results in two-->
<#--        side by side semester "cards", that users can click on and modify. If there is no content in a particular-->
<#--        semester, the semester is defined as a "Free Semester," which is the else condition in the following statement.-->
<#--        If there is content for each of the possible courses, then the course ID is displayed. -->
        <#list results as semester>
            <div class="ui-raised-link-card" id="myBtn${semester.semnumber}">

                <div class="year" id="freshman${semester.semnumber}" style="display: none">Freshman Year</div>
                <div class="year" id="sophomore${semester.semnumber}" style="display: none">Sophomore Year</div>
                <div class="year" id="junior${semester.semnumber}" style="display: none">Junior Year</div>
                <div class="year" id="senior${semester.semnumber}" style="display: none">Senior Year</div>
                <div class="year" id="supersenior${semester.semnumber}" style="display: none">Super Senior Year</div>

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
</div>
<script>
    $('.header-cc_pointer').each(function() {
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

    $('.ui-raised-link-card').each(function() {
        $(this).children('div.year').each(function() {
            if ($(this).css('display') === 'block') {
                if ($(this).text().includes("Freshman")) {
                    $(this).parent().addClass("blue");
                } else if ($(this).text().includes("Sophomore")) {
                    $(this).parent().addClass("bluegreen");
                } else if ($(this).text().includes("Junior")) {
                    $(this).parent().addClass("lavender");
                } else if ($(this).text().includes("Senior")) {
                    $(this).parent().addClass("pink");
                } else {
                    $(this).parent().addClass("terracotta");
                }
            }
        });
    });

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
        const parent = document.getElementById("myBtn" + lastCharacter);
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
        const parent = document.getElementById("myBtn" + lastCharacter);
        const child = parent.querySelectorAll(".description")[0];

        const children = [].slice.call(child.getElementsByTagName('p'), 0);
        for (let i = 0; i < children.length; i++) {
            let name = children[i].getAttribute("id");
            if (name.toLowerCase().replace(/\s/g, '') === moveCourse.value.toLowerCase().replace(/\s/g, '')) {
                child.removeChild(children[i]);

                const parent2 = document.getElementById("myBtn" + destSem.value);
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

<!-- Script for hovering-->
<script>
    $(".ui-raised-link-card").hover(
        function() {
            // item is being hovered over
            $(this).addClass('hover');
            console.log("hovering!!!!!")
        }, function() {
            // item is no longer being hovered over
            $(this).removeClass('hover');
            console.log("not hovering..")
        }
    );
</script>



</body>
<script>
<#--    Script for fixing the flash of unstyled content problem-->
    function js_Load() {
        document.body.style.visibility='visible';
    }
</script>
<noscript><style> body { visibility: visible; }</style></noscript>
</html>
