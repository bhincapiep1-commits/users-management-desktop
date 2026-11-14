package com.jcaa.usersmanagement.application.service.dto.query;

import jakarta.validation.constraints.NotBlank;

public record GetMovieByIdQuery(
    @NotBlank String id) {}