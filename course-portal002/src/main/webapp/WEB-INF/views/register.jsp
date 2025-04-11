<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Course Portal</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">Course Portal</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/login">Login</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="${pageContext.request.contextPath}/register">Register</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6">
                <div class="card shadow">
                    <div class="card-header bg-primary text-white text-center py-3">
                        <h4>Register</h4>
                    </div>
                    <div class="card-body p-4">
                        <form:form method="post" modelAttribute="user" action="${pageContext.request.contextPath}/register">
                            <div class="mb-3">
                                <label for="username" class="form-label">Username</label>
                                <form:input path="username" id="username" class="form-control" required="true" />
                                <form:errors path="username" cssClass="text-danger" />
                            </div>

                            <div class="mb-3">
                                <label for="email" class="form-label">Email</label>
                                <form:input path="email" id="email" type="email" class="form-control" required="true" />
                                <form:errors path="email" cssClass="text-danger" />
                            </div>

                            <div class="mb-3">
                                <label for="password" class="form-label">Password</label>
                                <form:password path="password" id="password" class="form-control" required="true" />
                                <form:errors path="password" cssClass="text-danger" />
                            </div>

                            <div class="d-grid">
                                <button type="submit" class="btn btn-primary">Register</button>
                            </div>
                        </form:form>
                    </div>
                    <div class="card-footer text-center py-3">
                        <div class="text-muted">Already have an account? <a href="${pageContext.request.contextPath}/login">Login</a></div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 