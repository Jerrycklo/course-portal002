<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Polls - Course Portal</title>
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
                        <a class="nav-link" href="/courses">Courses</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="/polls">Polls</a>
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
            <h1>Active Polls</h1>
            <a href="/polls/create" class="btn btn-primary">Create New Poll</a>
        </div>

        <div class="row">
            <c:forEach items="${polls}" var="poll">
                <div class="col-md-6 mb-4">
                    <div class="card h-100">
                        <div class="card-header">
                            <h5 class="card-title mb-0">${poll.question}</h5>
                        </div>
                        <div class="card-body">
                            <p class="card-text">
                                <strong>Course:</strong> <a href="/courses/${poll.course.id}">${poll.course.title}</a><br>
                                <strong>Created by:</strong> ${poll.creator.username}<br>
                                <strong>Options:</strong> ${poll.options.size()}<br>
                                <strong>Expires:</strong> ${poll.expiresAt.toLocalDate()}
                            </p>
                            <a href="/polls/${poll.id}" class="btn btn-primary">View Poll</a>
                        </div>
                    </div>
                </div>
            </c:forEach>

            <c:if test="${empty polls}">
                <div class="col-12">
                    <div class="alert alert-info">
                        No active polls available at the moment.
                    </div>
                </div>
            </c:if>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 