<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://java.sun.com/jsf/html"
      xmlns:f="http://java.sun.com/jsf/core">
    <h:head>
        <title>Navigation Bar</title>
        <meta name="viewport" content="width=device-width"/>
        <link rel="stylesheet" type="text/css" href="http://localhost:8080/QuizBuilderProject/style.css"></link>

    </h:head>
    <h:body>
        <div id="navButtons">
            <a id="boxLink" href="http://localhost:8080/QuizBuilderProject/home.faces">HOME</a>
            <a id="boxLink" href="http://localhost:8080/QuizBuilderProject/questionsList.faces">MY QUESTIONS</a>
            <a id="boxLink" href="http://localhost:8080/QuizBuilderProject/quizList.faces">MY QUIZZES</a>
            <a id="adminBoxLink" style="#{userController.getAdmin(loginController.id)}" href="http://localhost:8080/QuizBuilderProject/userPage.faces">USERS</a>
            <div id="logoutButton">
                        <h:form>

                            <h:commandButton id="btnLogOut" value="#{loginController.getLoginValue()}" action ="#{loginController.redirect}"/>
                          <!--  <h:commandButton id="btnLogOut" value="" onclick = "getAction()"/> -->
                        </h:form>
              </div>
        </div>
    </h:body>
</html>