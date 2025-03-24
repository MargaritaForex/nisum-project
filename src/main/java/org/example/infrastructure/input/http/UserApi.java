package org.example.infrastructure.input.http;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.infrastructure.input.http.dto.request.UserDTO;
import org.example.infrastructure.input.http.dto.response.UserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

public interface UserApi {

    @Operation(summary = "Registrar un usuario", description = "Registra un usuario con correo electrónico y contraseña.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "["
                                    + "{\"mensaje\": \"El formato del correo electrónico es inválido\"},"
                                    + "{\"mensaje\": \"La contraseña debe tener al menos 8 caracteres\"},"
                                    + "{\"mensaje\": \"El correo electrónico ya está en uso\"}"
                                    + "{\"mensaje\": \"Email is required\"}"
                                    + "{\"mensaje\": \"Password is required\"}"
                                    + "{\"mensaje\": \"Name is required\"}"
                                    + "]")))
    })

    @PostMapping
    ResponseEntity<UserResponseDto> registerUser(@RequestBody UserDTO userDto);

    @Operation(
            summary = "Obtener todos los usuarios",
            description = "Recupera una lista de todos los usuarios registrados en el sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserResponseDto.class))))
            }
    )
    @GetMapping
    ResponseEntity<List<UserResponseDto>> getAllUsers();

    @Operation(
            summary = "Busca un usuario por su email",
            description = "Busca un usuario por su email",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(value =
                                            "{\"mensaje\": \"User not found\"},"
                                    )))
            }
    )
    @GetMapping("/{email}")
    ResponseEntity<UserResponseDto> findByEmail(@PathVariable String email);

    @Operation(
            summary = "Actualizar usuario",
            description = "Actualiza un usuario existente identificado por su correo electrónico.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuario actualizado correctamente",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Datos de solicitud inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"mensaje\": \"Datos de solicitud inválidos\"}"))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuario no encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"mensaje\": \"Usuario no encontrado\"}"))
                    )
            }
    )
    @PutMapping("/{email}")
    ResponseEntity<UserResponseDto> updateUser(@PathVariable String email, @RequestBody UserDTO userDto);

    @Operation(
            summary = "Desactivar usuario por su email",
            description = "Desactivar usuario por su email",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(value =
                                            "{\"mensaje\": \"User not found\"},"
                                    )))
            }
    )
    @DeleteMapping("/{email}")
    ResponseEntity<Map<String, String>> deactivateUser(@PathVariable String email);
}