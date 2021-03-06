<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html lang="en">
<head>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/main.css">
    <script src="/js/jquery-3.4.1.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/common/main.js"></script>
    <script type="text/javascript" src="/js/common/login.js"></script>

    <title>Main Page</title>

</head>
<body>
<div id="wrapper">

    <div id="navigationMenuPlaceholder"></div>

    <!-- Page Content -->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-12">
                    <h1>
                    <sec:authorize access="isAuthenticated()">
                        Hello <sec:authentication property="name"/>. Your roles: ${roles}
                    </sec:authorize>
                    </h1>
                </div>
            </div>
        </div>
    </div>
    <div id="footerPlaceholder">
    </div>
    <!-- /#page-content-wrapper -->

</div>
</body>
</html>
