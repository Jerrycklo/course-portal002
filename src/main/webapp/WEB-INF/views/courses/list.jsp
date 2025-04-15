<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Courses - Course Portal</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="/">Course Portal</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="/">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="/courses">Courses</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/polls">Polls</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/logout">Logout</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <c:if test="${not empty message}">
            <div class="alert alert-success" role="alert">
                ${message}
            </div>
        </c:if>

        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1>All Courses</h1>
            <c:if test="${pageContext.request.userPrincipal.name != null && pageContext.request.isUserInRole('TEACHER')}">
                <a href="/courses/create" class="btn btn-primary">Create New Course</a>
            </c:if>
        </div>

        <div class="row">
            <c:forEach items="${courses}" var="course">
                <div class="col-md-4 mb-4">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title">${course.title}</h5>
                            <p class="card-text text-truncate">${course.description}</p>
                            <p class="text-muted">
                                <small>
                                    Instructor: ${course.teacher.username}<br>
                                    Created: ${course.createdAt.toLocalDate()}
                                </small>
                            </p>
                            <a href="/courses/${course.id}" class="btn btn-primary">View Course</a>
                        </div>
                    </div>
                </div>
            </c:forEach>

            <c:if test="${empty courses}">
                <div class="col-12">
                    <div class="alert alert-info">
                        No courses available at the moment.
                    </div>
                </div>
            </c:if>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 