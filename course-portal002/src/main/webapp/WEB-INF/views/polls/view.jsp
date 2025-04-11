<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Poll - Course Portal</title>
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
        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">
                ${error}
            </div>
        </c:if>

        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h3 class="mb-0">${poll.question}</h3>
                    </div>
                    <div class="card-body">
                        <div class="d-flex justify-content-between mb-3">
                            <span>
                                <strong>Course:</strong> <a href="/courses/${poll.course.id}">${poll.course.title}</a>
                            </span>
                            <span>
                                <strong>Created by:</strong> ${poll.creator.username}
                            </span>
                        </div>
                        <div class="mb-3">
                            <strong>Expires:</strong> ${poll.expiresAt.toLocalDate()}
                        </div>

                        <c:choose>
                            <c:when test="${hasVoted}">
                                <div class="alert alert-info">
                                    You have already voted in this poll. Here are the results:
                                </div>
                                
                                <div class="poll-results mb-4">
                                    <c:forEach items="${poll.options}" var="option">
                                        <c:set var="voteCount" value="${results[option] != null ? results[option] : 0}" />
                                        <c:set var="totalVotes" value="${poll.votes.size()}" />
                                        <c:set var="percentage" value="${totalVotes > 0 ? (voteCount * 100 / totalVotes) : 0}" />
                                        
                                        <div class="mb-2">
                                            <div class="d-flex justify-content-between">
                                                <span>${option}</span>
                                                <span>${voteCount} votes (${percentage}%)</span>
                                            </div>
                                            <div class="progress">
                                                <div class="progress-bar" role="progressbar" style="width: ${percentage}%"
                                                    aria-valuenow="${percentage}" aria-valuemin="0" aria-valuemax="100"></div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                    <div class="mt-2 text-muted">
                                        Total votes: ${totalVotes}
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <form action="/polls/${poll.id}/vote" method="post">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                    <div class="mb-3">
                                        <label class="form-label">Select an option:</label>
                                        <c:forEach items="${poll.options}" var="option">
                                            <div class="form-check">
                                                <input class="form-check-input" type="radio" name="option" 
                                                       id="option-${option}" value="${option}" required>
                                                <label class="form-check-label" for="option-${option}">
                                                    ${option}
                                                </label>
                                            </div>
                                        </c:forEach>
                                    </div>
                                    <button type="submit" class="btn btn-primary">Submit Vote</button>
                                </form>
                            </c:otherwise>
                        </c:choose>

                        <div class="mt-4">
                            <a href="/courses/${poll.course.id}" class="btn btn-outline-secondary">Back to Course</a>
                            <a href="/polls" class="btn btn-outline-secondary">All Polls</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 