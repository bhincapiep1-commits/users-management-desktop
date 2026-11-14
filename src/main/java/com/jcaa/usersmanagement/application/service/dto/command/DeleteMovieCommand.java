package com.jcaa.usersmanagement.application.service.dto.command;

import jakarta.validation.constraints.NotBlank;

public record DeleteMovieCommand(
    @NotBlank String id) {}