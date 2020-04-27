<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="/logo/t.png"/>
    <link rel="stylesheet" href="/css/mypath.css">
<#--    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css">-->
<#--    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>-->
<#--    <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.js"></script>-->
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
            background-color: rgb(0,0,0); /* Fallback color */
            background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
        }

        /* Modal Content */
        .modal-content {
            background-color: #fefefe;
            margin: auto;
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
            width: 50%;
            height: 50%;
            overflow: auto; /* Enable scroll if needed */
            background-color: rgb(87, 149, 117, 0.7); /* Fallback color */
        }


    </style>
  </head>
  <body>
      <title>My Pathway:</title>
      <h1> Pathway
          <#list id as node>
              ${node}
          </#list>
      </h1>
      <div class="results">
          <div id="myModal" class="modal">
              <div class="modal-content">
                  <span class="close">&times;</span>
                  <h2>Customize Your Pathway!</h2>
                  <p id="this-sem"> </p>
                  <div class="btn-group">
                      <button id="btn1">Add Course</button>
                      <button id="btn2">Move Course</button>
                      <button id="btn3">Swap Course</button>
                  </div>
                    <br>
                    <br>
                  <div class="add-course">
                      <h4>Add A Course To This Semester</h4>
                      <div class="field">
                          <label>Courses: </label>
                          <select name="courses" class="ui selection dropdown" multiple="" id="multi-select">
                              <#list courseList as item>
                                  <option value="${item}">${item}</option>
                              </#list>
                          </select>
                      </div>
                      <script>
                          $('#multi-select').dropdown();
                      </script>
                  </div>
              </div>

          </div>

          <#list results as semester>
              <div class="myBtn" id="myBtn${semester.semnumber}" style="cursor:pointer">
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

      <script>
          // Get the modal
          const modal = document.getElementById("myModal");
          // Get this semester text
          const sem = document.getElementById("this-sem");
          // Get course adder
          const adder = document.getElementsByClassName("add-course")[0];

          const cont = document.getElementsByClassName("modal-content")[0];

          // Get add button and make is show adder
          const addbtn = document.getElementById("btn1");
          addbtn.onclick = function() {
              adder.style.display = "block";
              cont.style.height = '70%';
          }


          // Make all semester list items open the modal
          window.onload = function() {
              const btns = document.getElementsByClassName("myBtn");
              for (let i = 0; i < btns.length; i++) {
                  const btn = btns[i];
                  btn.onclick = function() {
                      modal.style.display = "block";
                      sem.innerHTML = "Edit Semester " + btn.id.charAt(btn.id.length-1) + ":";
                  }
              }
          }

          // Get the span element that closes the modal
          const span = document.getElementsByClassName("close")[0];
          // When the user clicks on <span> (x), close the modal
          span.onclick = function() {
              modal.style.display = "none";
          }

          // When the user clicks anywhere outside of the modal, close it
          window.onclick = function(event) {
              if (event.target === modal) {
                  modal.style.display = "none";
              }
          }



      </script>


      <br>

      <script src="js/jquery-2.1.1.js"></script>
      <script src="js/main.js"></script>



  </body>

</html>