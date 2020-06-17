<html lang="en">
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <script src="${pageContext.request.contextPath}/js/jquery-3.4.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/main.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/profile.js"></script>

    <title>Profile</title>

</head>
<body>
<div id="wrapper">

    <div id="navigationMenuPlaceholder"></div>

    <!-- Page Content -->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-12">
                    <label class="col-md-4 control-label" for="firstname">First Name</label>
                    <div class="col-md-4">
                        <input id="firstname" name="firstname" type="text" class="form-control input-md" required="" readonly="readonly">
                    </div>

                    <label class="col-md-4 control-label" for="lastname">Last Name</label>
                    <div class="col-md-4">
                        <input id="lastname" name="lastname" type="text" class="form-control input-md" required="" readonly="readonly">
                    </div>

                    <label class="col-md-4 control-label" for="email">Email</label>
                    <div class="col-md-4">
                        <input id="email" name="email" type="text" class="form-control input-md" required="" readonly="readonly">
                    </div>

                    <label class="col-md-4 control-label" for="password">Password</label>
                    <div class="col-md-4">
                        <input id="password" name="password" type="password" placeholder="password" class="form-control input-md" required="" readonly="readonly">
                    </div>
                    <br>
                    <div class="col-md-4">
                        <input type="button" value="Become customer" id="becomeCustomerBtn" onclick="becomeCustomer()" class="btn btn-info btn-block invisible">
                        <input type="button" value="Change Data" id="editUserDataBtn" onclick="editUserData()" class="btn btn-info btn-block">
                        <input type="button" value="Save" id="updateUserDataBtn" onclick="validateChangeUserDataFields()" class="btn btn-info btn-block invisible">
                        <input type="button" value="Back" id="lockUserDataBtn" onclick="lockUserData()" class="btn btn-info btn-block invisible">
                    </div>
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
