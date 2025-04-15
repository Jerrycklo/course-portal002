<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${course.title} - Course Portal</title>
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
        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">
                ${error}
            </div>
        </c:if>

        <div class="row">
            <div class="col-md-8">
                <h1>${course.title}</h1>
                <p class="lead">${course.description}</p>
                <p>
                    <strong>Instructor:</strong> ${course.teacher.username}<br>
                    <strong>Created:</strong> ${course.createdAt.toLocalDate()}
                </p>
                
                <h2 class="mt-4">Lectures</h2>
                <div class="list-group mb-4">
                    <c:forEach items="${lectures}" var="lecture">
                        <div class="list-group-item">
                            <h5 class="mb-1">${lecture.title}</h5>
                            <p class="mb-1">${lecture.description}</p>
                            <small>
                                Uploaded: ${lecture.uploadedAt.toLocalDate()}
                                <c:if test="${not empty lecture.fileUrl}">
                                    | <a href="/lectures/${lecture.id}/download">Download Materials</a>
                                </c:if>
                            </small>
                        </div>
                    </c:forEach>
                    <c:if test="${empty lectures}">
                        <div class="list-group-item">
                            <p class="mb-1">No lectures available for this course yet.</p>
                        </div>
                    </c:if>
                </div>

                <h2 class="mt-4">Comments</h2>
                <div class="card mb-4">
                    <div class="card-body">
                        <form action="/courses/${course.id}/comments" method="post">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                            <div class="mb-3">
                                <label for="content" class="form-label">Add a comment</label>
                                <textarea class="form-control" id="content" name="content" rows="3" required></textarea>
                            </div>
                            <button type="submit" class="btn btn-primary">Post Comment</button>
                        </form>
                    </div>
                </div>

                <div class="list-group">
                    <c:forEach items="${comments}" var="comment">
                        <div class="list-group-item">
                            <div class="d-flex w-100 justify-content-between">
                                <h5 class="mb-1">${comment.user.username}</h5>
                                <small>${comment.createdAt}</small>
                            </div>
                            <p class="mb-1">${comment.content}</p>
                        </div>
                    </c:forEach>
                    <c:if test="${empty comments}">
                        <div class="list-group-item">
                            <p class="mb-1">No comments yet. Be the first to comment!</p>
                        </div>
                    </c:if>
                </div>
            </div>
            
            <div class="col-md-4">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        Course Actions
                    </div>
                    <div class="card-body">
                        <c:if test="${course.teacher.username == pageContext.request.userPrincipal.name}">
                            <a href="/courses/${course.id}/upload" class="btn btn-success w-100 mb-2">Upload Lecture</a>
                            <a href="/polls/create?courseId=${course.id}" class="btn btn-info w-100 mb-2">Create Poll</a>
                            <a href="/courses/${course.id}/edit" class="btn btn-warning w-100">Edit Course</a>
                        </c:if>
                    </div>
                </div>
                
                <div class="card mt-4">
                    <div class="card-header bg-primary text-white">
                        Active Polls
                    </div>
                    <div class="card-body">
                        <c:forEach items="${polls}" var="poll">
                            <div class="mb-3">
                                <h5>${poll.question}</h5>
                                <p><small>Expires: ${poll.expiresAt.toLocalDate()}</small></p>
                                <a href="/polls/${poll.id}" class="btn btn-sm btn-primary">Vote</a>
                            </div>
                        </c:forEach>
                        <c:if test="${empty polls}">
                            <p>No active polls for this course.</p>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 