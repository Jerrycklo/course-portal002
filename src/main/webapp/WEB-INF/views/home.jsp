<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Course Portal - Home</title>
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
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="${pageContext.request.contextPath}/">Home</a>
                    </li>
                    <c:if test="${pageContext.request.userPrincipal.name != null}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/courses">Courses</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/polls">Polls</a>
                        </li>
                    </c:if>
                </ul>
                <ul class="navbar-nav">
                    <c:choose>
                        <c:when test="${pageContext.request.userPrincipal.name != null}">
                            <li class="nav-item">
                                <span class="nav-link">Welcome, ${pageContext.request.userPrincipal.name}</span>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/logout">Logout</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/login">Login</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/register">Register</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="jumbotron bg-light p-5 rounded">
            <h1 class="display-4">Welcome to Course Portal</h1>
            <p class="lead">Your online learning platform</p>
            <hr class="my-4">
            <p>Explore our courses and participate in polls to enhance your learning experience.</p>
        </div>

        <h2 class="mt-4">Available Courses</h2>
        <div class="row">
            <c:forEach items="${courses}" var="course">
                <div class="col-md-4 mb-4">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">${course.title}</h5>
                            <p class="card-text">${course.description}</p>
                            <a href="${pageContext.request.contextPath}/courses/${course.id}" class="btn btn-primary">View Course</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
            <c:if test="${empty courses}">
                <div class="col-12">
                    <p>No courses available at the moment.</p>
                </div>
            </c:if>
        </div>

        <h2 class="mt-4">Active Polls</h2>
        <div class="row">
            <c:forEach items="${polls}" var="poll">
                <div class="col-md-4 mb-4">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">${poll.question}</h5>
                            <p class="card-text small text-muted">Course: ${poll.course.title}</p>
                            <p class="card-text small">Expires: ${poll.expiresAt}</p>
                            <a href="${pageContext.request.contextPath}/polls/${poll.id}" class="btn btn-secondary">Vote</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
            <c:if test="${empty polls}">
                <div class="col-12">
                    <p>No active polls at the moment.</p>
                </div>
            </c:if>
        </div>
    </div>

    <footer class="bg-dark text-white mt-5 py-3">
        <div class="container text-center">
            <p>&copy; 2023 Course Portal. All rights reserved.</p>
        </div>
    </footer>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 